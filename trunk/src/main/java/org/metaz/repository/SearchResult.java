package org.metaz.repository;

import java.util.List;

/**
 * The SearchResult represents the result of a search action. It contains:
 * <li>the number of hits</li>
 * <li>subset of the actual results</li>
 * <li>start and end number of the results</li>
 * 
 * @authors Jurgen Goelen
 * @version 0.1
 */
public class SearchResult {
    /**
     * @associates <{org.metaz.repository.Result}>
     */
    private List results; //list of results sorted by relevance
    
    private int totalNumResults; //the total number of available results
    
    private int start;
    
    private int end;
        
    //FIXME additional methods to be defined
    
}
