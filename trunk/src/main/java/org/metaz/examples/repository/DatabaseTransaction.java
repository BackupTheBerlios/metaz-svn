// A database transaction abstraction, independend from any persistence engine
// Author: Falco Paul

package org.metaz.examples.repository;

public interface DatabaseTransaction {

  public void rolledBack();

  public void rollBackFailed();
   
}
