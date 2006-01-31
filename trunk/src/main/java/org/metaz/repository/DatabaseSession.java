// Author: Falco Paul

package org.metaz.repository;

import org.hibernate.Session;

public class DatabaseSession {

  Session hibernateSession = null;

  // constructors

  public DatabaseSession() {

  }
  
  public DatabaseSession(Session hibernateSession)
  {
    this.hibernateSession = hibernateSession;
  }

  public void setHibernateSession(Session hibernateSession)
  {
    this.hibernateSession = hibernateSession;
  }

  public Session getHibernateSession()
  {
    return hibernateSession;
  }

}
