package org.metaz.repository.alt;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.metaz.domain.Record;
import org.metaz.domain.MetaData;

import org.metaz.util.MetaZ;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.metaz.repository.DataService;
import org.metaz.repository.Facade;
import org.metaz.repository.FacadeImpl;
import org.metaz.repository.RepositoryService;
import org.metaz.repository.Result;
import org.metaz.repository.SearchService;

/**
 * Implementation of the Facade.
 * NOTE: this Facade implementation only has a Lucene layer underneath.
 */
class FacadeImplAlt implements Facade {
    //logger instance
    private static Logger logger = MetaZ.getLogger(FacadeImpl.class);

    //~ Instance fields --------------------------------------------------------------------------------------------------

    /* The SearchService implementation to be used
     */
    private SearchServiceAlt searchServiceAlt;

    /* Read-write lock  to avoid incosistent search results
     * during the update of the Repository
     */
    private ReadWriteLock rwLock;

    //~ Constructors -----------------------------------------------------------------------------------------------------

    /*
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
     * @throws Exception DOCUMENT ME!
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
     * @throws Exception DOCUMENT ME!
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
     * @throws Exception DOCUMENT ME!
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
    public List getTargetEndUserValues()
                                throws Exception
    {

      String[] svalues = searchServiceAlt.getDistinctValues(MetaData.TARGETENDUSER);
      Vector values = new Vector();
      for(int i=0; i<svalues.length; i++){
          values.add(svalues[i]);
      }

      return values;

    }

    /**
     * Returns distinct values for the field schoolType
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getSchoolTypesValues()
                              throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.SCHOOLTYPE);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }

    /**
     * Returns distinct values for the field schoolDiscipline
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getSchoolDisciplineValues()
                                   throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.SCHOOLDISCIPLINE);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }

    /**
     * Returns distinct values for the field didacticFunction
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getDidacticFunctionValues()
                                   throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.DIDACTICFUNCTION);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }

    /**
     * Returns distinct values for the field productType
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getProductTypeValues()
                              throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.PRODUCTTYPE);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }

    /**
     * Returns distinct values for the field professionalSituation
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getProfessionalSituationValues()
                                        throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.PROFESSIONALSITUATION);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }

    /**
     * Returns distinct values for the field competence
     *
     * @return The list of distinct values
     *
     * @throws Exception DOCUMENT ME!
     */
    public List getCompetenceValues()
                             throws Exception
    {

        String[] svalues = searchServiceAlt.getDistinctValues(MetaData.TARGETENDUSER);
        Vector values = new Vector();
        for(int i=0; i<svalues.length; i++){
            values.add(svalues[i]);
        }

      return values;

    }
    
}
