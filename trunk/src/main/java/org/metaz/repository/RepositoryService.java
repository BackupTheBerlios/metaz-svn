package org.metaz.repository;

import org.metaz.domain.Record;

import java.util.List;

/**
 * Interface to be extended by all services in the repository.
 *
 * @author Jurgen Goelen
 * @version 0.1
 */
public interface RepositoryService {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Updates the service.
   *
   * @param records Records to updated by the service.
   *
   * @throws Exception Update action failed.
   */
  void doUpdate(List<Record> records)
                throws Exception;

  /**
   * Purges the service.
   *
   * @throws Exception Purge action failed.
   */
  void doPurge()
               throws Exception;

}
