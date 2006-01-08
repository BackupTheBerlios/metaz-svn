package org.metaz.repository;

import java.util.List;

import org.metaz.domain.Record;

/**
 * Interface to be extended by all services in the repository.
 * 
 * @authors Jurgen Goelen
 * @version 0.1
 */
public interface RepositoryService {

    /**
     * Updates the service.
     * @param records
     *      Records to updated by the service.
     */
    
    /**
     * Updates the service.
     * @param records
     *      Records to updated by the service.
     * @throws Exception
     *      Update action failed.
     */
    public void doUpdate(List<Record> records) throws Exception;
    
    
    /**
     * Purges the service.
     * @throws Exception
     *      Purge action failed.
     */
    public void doPurge() throws Exception;

}