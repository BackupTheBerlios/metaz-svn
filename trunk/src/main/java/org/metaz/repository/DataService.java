package org.metaz.repository;

import java.net.URI;

import java.util.List;

import org.metaz.domain.Record;

/**
 * @authors Jurgen Goelen
 * @version 0.3 
 */
public interface DataService extends RepositoryService {
    
     /**
      * Retrieve the records that match the specified URI's
      * @param ids
      *      List of URI's
      * @return
      *      Returns a list of records.
      * @throws Exception
      *      The records could not be retrieved.
      */
    public List<Record> getRecords(List<URI> ids) throws Exception;
    
    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Record getRecord(URI id) throws Exception;
           
}
