package org.metaz.repository.alt;

import org.metaz.domain.Record;

import java.util.List;

import org.metaz.repository.RepositoryService;
import org.metaz.repository.Result;


/**
 * Service for searching the index of the search engine.
 * 
 * @authors Sammy Dalewyn
 * @version 0.1
 */
public interface SearchServiceAlt extends RepositoryService
{
    /**
     * Search for results that match the specified query.
     *
     * @param query The query to be satisfied.
     *
     * @return Returns a List of records sorted by their relevance
     *         score.
     */
    public List<Result<Record>> doSearch(String query);
} // end SearchService
