// This is an abstract class that defines an abstract "database conversation"
// Author: Falco Paul

package org.metaz.repository;

public interface DatabaseInteraction {

  public ActionResult execute(DatabaseSession dbSession) throws Exception;
  
  public void sessionCloseFailed(DatabaseSession dbSession);
   
}
