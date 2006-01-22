package org.metaz.repository;

import java.util.List;

import org.metaz.domain.Record;


/**
 * The public interface for the Repository package.
 * 
 * @authors Jurgen Goelen
 * @version 0.2 
 */
public interface Facade {
        
    /**
     * Search for results that match the specified query.
     * @param query
     *      The query to be satisfied.
     * @return
     *      Returns a List of Record objects.
     * @throws Exception
     */
    public List<Result<Record>> doSearch(String query)throws Exception;
        
    /**
     * Purges and updates all the repositories services.
     * @param records
     *      Records to updated.
     * @throws Exception
     *      Update action failed.
     */
    public void doUpdate(List<Record> records) throws Exception; 
        
    /**
     * Sets the SearchService implementation to be used.
     * @param service A SearchService implementation.
     */
    public void setSearchService(SearchService service);
    
    /**
     * Sets the DataService implementation to be used.
     * @param service A DataService implementation.
     */
    public void setDataService(DataService service);
    
    
}
