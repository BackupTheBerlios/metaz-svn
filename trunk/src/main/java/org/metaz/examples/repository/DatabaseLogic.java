// This is an abstract helper class that defines a minimal form of database interaction
// Independent from persistence technologies such as Hibernate, etc
// @author Falco Paul

package org.metaz.examples.repository;

public abstract class DatabaseLogic implements DatabaseInteraction {

  public abstract ActionResult execute(DatabaseSession dbSession) throws Exception;
  
  public void sessionCloseFailed(DatabaseSession dbSession)
  {
  }
   
}
