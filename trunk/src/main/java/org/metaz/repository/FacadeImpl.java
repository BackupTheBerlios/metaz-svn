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

  /**
   * Sets the searchService to use in the repository
   *
   * @param service the searchService
   */
  public void setSearchService(RepositoryService service) {

    searchService = (SearchService) service;

  }

  /**
   * Sets the dataService to use in the repository
   *
   * @param service the dataService
   */
  public void setDataService(RepositoryService service) {

    dataService = (DataService) service;

  }

  /**
   * Returns a sorted array of unique TargetEndUser values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getTargetEndUserValues()
                                  throws Exception
  {

    List     values = dataService.getUniqueFieldValues("targetEndUser");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a sorted array of unique SchoolType values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getSchoolTypesValues()
                                throws Exception
  {

    List     values = dataService.getUniqueFieldValues("schoolType");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a sorted array of unique SchoolDiscipline values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getSchoolDisciplineValues()
                                     throws Exception
  {

    List     values = dataService.getUniqueFieldValues("schoolDiscipline");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a sorted array of unique DidacticFunction values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getDidacticFunctionValues()
                                     throws Exception
  {

    List     values = dataService.getUniqueFieldValues("didacticFunction");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a sorted array of unique ProductType values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getProductTypeValues()
                                throws Exception
  {

    List     values = dataService.getUniqueFieldValues("productType");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }


  /**
   * Returns a sorted array of unique ProfessionalSituation values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getProfessionalSituationValues()
                                          throws Exception
  {

    List     values = dataService.getUniqueFieldValues("professionalSituation");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a sorted array of unique Competence values
   *
   * @return the sorted array
   *
   * @throws Exception DOCUMENT ME!
   */
  public String[] getCompetenceValues()
                               throws Exception
  {

    List     values = dataService.getUniqueFieldValues("competence");
    String[] svalues = new String[0];

    values.toArray(svalues);

    return svalues;

  }

  /**
   * Returns a list of unique SchoolDiscipline values that are related  to the provided schooltype
   *
   * @param schooltype the schooltype dependency
   *
   * @return the list of related school disciplines
   */
  public String[] getSchoolDisciplineValues(String schooltype) {

    return new String[0];

  }

}
