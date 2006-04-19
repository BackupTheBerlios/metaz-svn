package org.metaz.repository;

import org.metaz.domain.Record;

import java.net.URI;

import java.util.List;

/**
 * @author Jurgen Goelen
 * @version 0.3
 */
public interface DataService extends RepositoryService {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Retrieve the records that match the specified URI's
   *
   * @param ids List of URI's
   *
   * @return Returns a list of records.
   *
   * @throws Exception The records could not be retrieved.
   */
  List<Record> getRecords(List<URI> ids)
                          throws Exception;

  /**
   * Returns the record with the given uri attribute value.
   *
   * @param id the uri of the requested record
   *
   * @return Returns a record
   *
   * @throws Exception The record can not be retrieved.
   */
  Record getRecord(URI id)
                   throws Exception;

  /**
   * Retrieve a list of unique values for a specific record field.
   *
   * @param recordField Field name
   *
   * @return Returns a list of unique values. (sorted ascending)
   *
   * @throws Exception The list can not be retrieved.
   */
  List getUniqueFieldValues(String recordField)
                            throws Exception;

}
