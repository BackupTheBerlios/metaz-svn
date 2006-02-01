// A database transaction abstraction, independend from any persistence engine
// Author: Falco Paul

package org.metaz.repository;

public interface DatabaseTransaction {

  public void rolledBack();

  public void rollBackFailed();
   
}
