// A class that describes a single row result that has been retrieved
// @author Falco Paul

package org.metaz.examples.repository;

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
