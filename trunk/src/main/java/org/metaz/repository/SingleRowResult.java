// A class that describers a single row result that has been retrieved
// Author: Falco Paul

package org.metaz.repository;

public class SingleRowResult extends SuccessResult  {

  private Object result = null;

  // empty constructor

  public SingleRowResult() {
  }

  // constructor
  
  public SingleRowResult(Object result) {
  
    this.result = result;
    
  }

  // toString() implementation
  
  public String toString() {
  
    return result.toString();
    
  }
  
  // getter

  public void setResult(Object result)
  {
    this.result = result;
  }

  // setter

  public Object getResult()
  {
    return result;
  }

}