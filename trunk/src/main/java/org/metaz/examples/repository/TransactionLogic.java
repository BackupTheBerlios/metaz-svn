// This is an abstract helper class that supplies a mimimum implementation of DatabaseTransaction
// @author Falco Paul

package org.metaz.examples.repository;

public abstract class TransactionLogic extends DatabaseLogic implements DatabaseTransaction {

  public void rolledBack(DatabaseSession dbSession)
  {
  }

  public void rollBackFailed(DatabaseSession dbSession)
  {
  }
   
}
