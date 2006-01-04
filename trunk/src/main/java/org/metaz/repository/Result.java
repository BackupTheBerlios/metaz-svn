package org.metaz.repository;

import java.net.URI;

/**
 * 
 * This class represents a search result.
 * 
 * @authors Jurgen Goelen
 * @version 0.1 
 */
public class Result<T> {
    
    private final T obj; //the object represented by the result   
    private final float score; //the relevance score
    
    /**
     * Creates a new Result instance for an object of type T.
     * @param obj
     *      The object to be represented by this result.
     * @param score
     *      The relevance score for this result.
     */
    public Result(T obj,float score){
        this.obj = obj;
        this.score = score;
    }

    /**
     * @return
     *      Returns the object.
     */
    public T getObject() {
        return obj;
    }

    /**
     * @return
     *      Returns the relevance score.
     */
    public float getScore() {
        return score;
    }
}
