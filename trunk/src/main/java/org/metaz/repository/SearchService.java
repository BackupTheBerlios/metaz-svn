package org.metaz.repository;

import java.util.List;
import java.net.URI;

/**
 * Service for searching the index of the search engine.
 * 
 * @authors Jurgen Goelen
 * @version 0.1
 */
public interface SearchService  extends RepositoryService  {

     /**
      * Search for results that match the specified query.
      * @param query 
      *      The query to be satisfied.
      * @return 
      *       Returns a List of results (URI's) sorted by their relevance score.
      * @throws Exception 
      *       The search action could not be executed.
      */
    public List<Result<URI>> doSearch(String query);
           
}
