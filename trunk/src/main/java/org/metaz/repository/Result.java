package org.metaz.repository;

import java.net.URI;

/**
 * This class represents a search result.
 *
 * @author Jurgen Goelen
 * @version 0.1
 *
 * @param <T> the result object
 */
public class Result<T> {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final T     obj; //the object represented by the result   
  private final float score; //the relevance score

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Creates a new Result instance for an object of type T.
     * @param obj
     *      The object to be represented by this result.
     * @param score
     *      The relevance score for this result.
     */
  public Result(T obj, float score) {

    this.obj = obj;
    this.score = score;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the result object
   *
   * @return Returns the object.
   */
  public T getObject() {

    return obj;

  }

  /**
   * Returns the result score
   *
   * @return Returns the relevance score.
   */
  public float getScore() {

    return score;

  }

}
