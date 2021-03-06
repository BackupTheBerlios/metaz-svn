package org.metaz.repository;

import org.metaz.domain.Record;

import java.util.HashMap;
import java.util.List;

/**
 * Service for searching the index of the search engine.
 *
 * @author Sammy Dalewyn
 * @version 0.1
 */
public interface SearchServiceAlt extends RepositoryService {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Search for results that match the specified query.
   *
   * @param query The query to be satisfied.
   *
   * @return Returns a List of records sorted by their relevance score.
   */
  List<Result<Record>> doSearch(String query);

  /**
   * Searches for results that match the specified query.
   *
   * @param termValuePairs The query to be satisfied.
   *
   * @return Returns a List of results (URI's) sorted by their relevance score.
   */
  List<Result<Record>> doSearch(HashMap termValuePairs);

  /**
   * Returns an alphabetical sorted array of distinct values of an index field NOTE: the index field has to be
   * one of the keyword fields (targetEndUser, schoolType, schoolDiscipline, didacticFunction, productType,
   * professionalSituation or competence).
   *
   * @param fieldName the index field name
   *
   * @return the sorted array
   */
  String[] getDistinctValues(String fieldName);

  /**
   * Returns an alphabetical sorted array of distinct values of the field SchoolDiscipline that are related to
   * the field SchoolType with the provided parameter value
   *
   * @param schooltype the SchoolType value
   *
   * @return the sorted array
   */
  String[] getDistinctRelatedSchoolDisciplineValues(String schooltype);

} // end SearchService
