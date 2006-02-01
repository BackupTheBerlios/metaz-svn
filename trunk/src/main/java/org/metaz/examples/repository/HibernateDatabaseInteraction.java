// A Hibernate interaction (transaction, query, etc)
// Author: Falco Paul

package org.metaz.repository;

public abstract class HibernateDatabaseInteraction extends HibernateDatabaseSession implements DatabaseInteraction {
 
  public void sessionCloseFailed()
  {
    // default empty handler
  }
   
}
