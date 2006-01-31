// A class to describe a row list result that has been retrieved
// Author: Falco Paul

package org.metaz.repository;

import java.util.List;

public class RowListResult extends SuccessResult  {

  private List result = null;

  // empty constructor

  public RowListResult() {
  }

  // constructor
  
  public RowListResult(List result) {
  
    this.result = result;
    
  }

  // toString() implementation
  
  public String toString() {
  
    return result.toString();
    
  }
  
  // getter

  public void setResult(List result)
  {
    this.result = result;
  }

  // setter

  public List getResult()
  {
    return result;
  }

}
