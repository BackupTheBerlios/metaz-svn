// This is an abstract class that defines an abstract "database conversation"
// Independent from persistence technologies such as Hibernate, etc
// @author Falco Paul

package org.metaz.repository;

public interface DatabaseInteraction {

  public ActionResult execute(DatabaseSession dbSession) throws Exception;
  
  public void sessionCloseFailed(DatabaseSession dbSession);
   
}
