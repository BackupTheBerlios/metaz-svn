// A database interaction (transaction, query, etc) abstraction, independend from any persistence engine
// Author: Falco Paul

package org.metaz.repository;

public interface DatabaseInteraction {

  public ActionResult execute() throws Exception;
  
  public void sessionCloseFailed();
   
}
