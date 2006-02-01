// A Hibernate transaction
// Author: Falco Paul

package org.metaz.repository;

public abstract class HibernateDatabaseTransaction extends HibernateDatabaseInteraction implements DatabaseTransaction {

  public void rolledBack()
  {
    // default empty handler
  }

  public void rollBackFailed()
  {
    // default empty handler
  }      
  
}
