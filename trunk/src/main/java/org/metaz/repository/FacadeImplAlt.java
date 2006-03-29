package org.metaz.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import org.metaz.repository.Facade;
import org.metaz.repository.FacadeImpl;
import org.metaz.repository.RepositoryService;
import org.metaz.repository.Result;

import org.metaz.util.MetaZ;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of the Facade. NOTE: this Facade implementation only has a Lucene layer underneath.
 */
class FacadeImplAlt
  implements Facade
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  //logger instance
  private static Logger logger = MetaZ.getLogger(FacadeImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** The SearchService implementation to be used */
  private SearchServiceAlt searchServiceAlt;

  /** Read-write lock  to avoid incosistent search results during the update of the Repository */
  private ReadWriteLock rwLock;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Default constructor
     */
  public FacadeImplAlt() {

    rwLock = new ReentrantReadWriteLock();

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a list of records that match the specified query
   *
   * @param query the search query
   *
   * @return the records that match the query
   *
   * @throws Exception Search failed
   */
  public List<Result<Record>> doSearch(String query)
                                throws Exception
  {

    List<Result<Record>> records;

    logger.debug("query: " + query);

    //lock for reading
    rwLock.readLock().lock();
    logger.debug("acquired read lock");

    try {

      records = searchServiceAlt.doSearch(query);

    }
    finally {

      logger.debug("released read lock");
      rwLock.readLock().unlock();

    }

    return records;

  }

  /**
   * Returns a list of records that match the specified query
   *
   * @param termValuePairs a map of terms and the values to search for
   *
   * @return the records that match the query
   *
   * @throws Exception Search failed
   */
  public List<Result<Record>> doSearch(HashMap termValuePairs)
                                throws Exception
  {

    List<Result<Record>> records;

    //lock for reading
    rwLock.readLock().lock();
    logger.debug("acquired read lock");

    try {

      records = searchServiceAlt.doSearch(termValuePairs);

    }
    finally {

      logger.debug("released read lock");
      rwLock.readLock().unlock();

    }

    return records;

  }

  /**
   * Updates the repository.
   *
   * @param records the records to be added to the repository
   *
   * @throws Exception Update failed
   */
  public void doUpdate(List<Record> records)
                throws Exception
  {

    rwLock.writeLock().lock();
    logger.debug("acquired write lock");

    try {

      searchServiceAlt.doUpdate(records);

    } catch (Exception e) {

      //log and re-throw
      String msg = "Error updating repository";

      logger.log(Priority.ERROR, e);
      throw new Exception("Error updating repository: " + e.getMessage());

    } finally {

      logger.debug("released write lock");
      rwLock.writeLock().unlock();

    }

  }

  /**
   * Sets the searchService implementation to use
   *
   * @param service DOCUMENT ME!
   */
  public void setSearchService(RepositoryService service) {

    searchServiceAlt = (SearchServiceAlt) service;

  }

  /**
   * Sets the dataService implementation to use.
   *
   * @param service DOCUMENT ME!
   */
  public void setDataService(RepositoryService service) {

  }

  /**
   * Returns distinct values for the field targetEndUser
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getTargetEndUserValues()
                                  throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.TARGETENDUSER);

  }

  /**
   * Returns distinct values for the field schoolType
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getSchoolTypesValues()
                                throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.SCHOOLTYPE);

  }

  /**
   * Returns distinct values for the field schoolDiscipline
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getSchoolDisciplineValues()
                                     throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.SCHOOLDISCIPLINE);

  }

  /**
   * Returns distinct values for the field didacticFunction
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getDidacticFunctionValues()
                                     throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.DIDACTICFUNCTION);

  }

  /**
   * Returns distinct values for the field productType
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getProductTypeValues()
                                throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.PRODUCTTYPE);

  }

  /**
   * Returns distinct values for the field professionalSituation
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getProfessionalSituationValues()
                                          throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.PROFESSIONALSITUATION);

  }

  /**
   * Returns distinct values for the field competence
   *
   * @return The list of distinct values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getCompetenceValues()
                               throws Exception
  {

    return searchServiceAlt.getDistinctValues(MetaData.COMPETENCE);

  }

  /**
   * Returns a list of unique SchoolDiscipline values stored in the repository that are related to the provided
   * SchoolType
   *
   * @param schooltype the SchoolType dependency
   *
   * @return a list of unique related SchoolDiscipline values
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getSchoolDisciplineValues(String schooltype)
                                     throws Exception
  {

    return searchServiceAlt.getDistinctRelatedSchoolDisciplineValues(schooltype);

  }

}
