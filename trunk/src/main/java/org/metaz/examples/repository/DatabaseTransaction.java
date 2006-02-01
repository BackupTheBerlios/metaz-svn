// This is an abstract class that defines an abstract "transaction"
// Independent from persistence technologies such as Hibernate, etc
// @author Falco Paul

package org.metaz.examples.repository;

public interface DatabaseTransaction extends DatabaseInteraction {

  public void rolledBack(DatabaseSession dbSession);

  public void rollBackFailed(DatabaseSession dbSession);
   
}
