package org.metaz.repository;

import java.util.List;


/**
 * The public interface for the Repository package.
 * 
 * @authors Jurgen Goelen
 * @version 0.1 
 */
public interface Facade {
        
    /**
     * Search for results that match the specified query.
     * @param query
     *      The query to be satisfied.
     * @param startAt
     *      The first match to return (start counting from 0)
     * @param numberMatches
     *      The maximum number of matches to return
     * @return
     *      Returns a SearchResult
     * @throws Exception
     */
    public SearchResult doSearch(String query,int startAt,int numberMatches)throws Exception;
    
    
    /**
     * Purges and updates all the repositories services.
     * @param records
     *      Records to updated.
     * @throws Exception
     *      Update action failed.
     */
    public void doUpdate(List<Record> records) throws Exception; 
}
