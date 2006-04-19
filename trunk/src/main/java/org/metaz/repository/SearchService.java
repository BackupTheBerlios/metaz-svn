package org.metaz.repository;

import java.net.URI;

import java.util.HashMap;
import java.util.List;

/**
 * Service for searching the index of the search engine.
 *
 * @author Jurgen Goelen
 * @version 0.1
 */
public interface SearchService extends RepositoryService {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Searches for results that match the specified query.
   *
   * @param query The query to be satisfied.
   *
   * @return Returns a List of results (URI's) sorted by their relevance score.
   */
  List<Result<URI>> doSearch(String query);

  /**
   * Searches for results that match the specified query.
   *
   * @param termValuePairs The query to be satisfied.
   *
   * @return Returns a List of results (URI's) sorted by their relevance score.
   */
  List<Result<URI>> doSearch(HashMap termValuePairs);

} // end SearchService
