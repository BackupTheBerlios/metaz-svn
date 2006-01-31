// This is an abstract helper class that supplies a mimimum implementation of DatabaseInteraction
// Author: Falco Paul

package org.metaz.repository;

public abstract class DatabaseLogic implements DatabaseInteraction {

  public abstract ActionResult execute(DatabaseSession dbSession) throws Exception;
  
  public void sessionCloseFailed(DatabaseSession dbSession)
  {
  }
   
}
