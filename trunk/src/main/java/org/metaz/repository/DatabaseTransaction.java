// This is an abstract class that defines an abstract "transaction"
// Works for both BpServer and Hibernate
// Author: Falco Paul

package org.metaz.repository;

public interface DatabaseTransaction extends DatabaseInteraction {

  public void rolledBack(DatabaseSession dbSession);

  public void rollBackFailed(DatabaseSession dbSession);
   
}
