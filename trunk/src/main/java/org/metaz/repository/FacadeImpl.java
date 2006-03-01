/**
 * 
 */
package org.metaz.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.metaz.domain.Record;

import org.metaz.util.MetaZ;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Concrete implementation for the Facade interface.
 *
 * @author Jurgen Goelen
 */

public class FacadeImpl
  implements Facade
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  //logger instance
  private static Logger logger = MetaZ.getLogger(FacadeImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /* The SearchService implementation to be used
   */
  private SearchService searchService;

  /* The DataService implementation to be used
   */
  private DataService dataService;

  /* Read-write lock  to avoid incosistent search results
   * during the update of the Repository
   */
  private ReadWriteLock rwLock;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /*
   * Default constructor
   */
  public FacadeImpl() {

    rwLock = new ReentrantReadWriteLock();

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a list of records that match the specified query
   *
   * @param query the query
   *
   * @return the records that matcht the query
   *
   * @throws Exception DOCUMENT ME!
   */
  public List<Result<Record>> doSearch(String query)
                                throws Exception
  {

    List<Result<Record>> records = new Vector<Result<Record>>();

    logger.debug("query: " + query);

    //lock for reading
    rwLock.readLock().lock();
    logger.debug("acquired read lock");

    try {

      //FIXME insert query validation mechanism
      List<Result<URI>> hits = searchService.doSearch(query);

      //transform list
      for (Result<URI> hit : hits) {

        try {

          //get record by its URI
          Record record = dataService.getRecord(hit.getObject());

          //add a new result instance
          records.add(new Result<Record>(record, hit.getScore()));

        } catch (Exception e) {

          //FIXME: the action to be taken depends on the behaviour of the DataService
          logger.log(Priority.ERROR, e);

        }

      }

    } finally {

      logger.debug("released read lock");
      rwLock.readLock().unlock();

    }

    return records;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#doSearch(java.util.HashMap)
   */
  /**
   * Returns a list of records that match the specified query
   *
   * @param termValuePairs a map of terms and the values to search for
   *
   * @return the records that match the query
   *
   * @throws Exception DOCUMENT ME!
   */
  public List<Result<Record>> doSearch(HashMap termValuePairs)
                                throws Exception
  {

    List<Result<Record>> records = new Vector<Result<Record>>();

    //lock for reading
    rwLock.readLock().lock();
    logger.debug("acquired read lock");

    try {

      //FIXME insert query validation mechanism
      List<Result<URI>> hits = searchService.doSearch(termValuePairs);

      //transform list
      for (Result<URI> hit : hits) {

        try {

          //get record by its URI
          Record record = dataService.getRecord(hit.getObject());

          //add a new result instance
          records.add(new Result<Record>(record, hit.getScore()));

        } catch (Exception e) {

          //FIXME: the action to be taken depends on the behaviour of the DataService
          logger.log(Priority.ERROR, e);

        }

      }

    } finally {

      logger.debug("released read lock");
      rwLock.readLock().unlock();

    }

    return records;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#doUpdate(java.util.List)
   */
  /**
   * DOCUMENT ME!
   *
   * @param records DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public void doUpdate(List<Record> records)
                throws Exception
  {

    rwLock.writeLock().lock();
    logger.debug("acquired write lock");

    try {

      //clear the indexed
      searchService.doPurge();
      //clear the database
      dataService.doPurge();
      //update index
      searchService.doUpdate(records);
      //update database
      dataService.doUpdate(records);

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

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#setSearchService(org.metaz.repository.SearchService)
   */
  /**
   * DOCUMENT ME!
   *
   * @param service DOCUMENT ME!
   */
  public void setSearchService(SearchService service) {

    searchService = service;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#setDataService(org.metaz.repository.DataService)
   */
  /**
   * DOCUMENT ME!
   *
   * @param service DOCUMENT ME!
   */
  public void setDataService(DataService service) {

    dataService = service;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getTargetEndUserValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getTargetEndUserValues()
                              throws Exception
  {

    List values = dataService.getUniqueFieldValues("targetEndUser.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getSchoolTypesValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getSchoolTypesValues()
                            throws Exception
  {

    List values = dataService.getUniqueFieldValues("schoolType.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getSchoolDisciplineValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getSchoolDisciplineValues()
                                 throws Exception
  {

    List values = dataService.getUniqueFieldValues("schoolDiscipline.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getDidacticFunctionValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getDidacticFunctionValues()
                                 throws Exception
  {

    List values = dataService.getUniqueFieldValues("didacticFunction.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getProductTypeValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getProductTypeValues()
                            throws Exception
  {

    List values = dataService.getUniqueFieldValues("productType.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getProfessionalSituationValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getProfessionalSituationValues()
                                      throws Exception
  {

    List values = dataService.getUniqueFieldValues("professionalSituation.value");

    return values;

  }

  /* (non-Javadoc)
   * @see org.metaz.repository.Facade#getCompetenceValues()
   */
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws Exception DOCUMENT ME!
   */
  public List getCompetenceValues()
                           throws Exception
  {

    List values = dataService.getUniqueFieldValues("competence.value");

    return values;

  }

}
