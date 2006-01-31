// A database session class that encapsulates a Hibernate database sessions
// @author Falco Paul

package org.metaz.repository;

import org.hibernate.Session;

public class HibernateDatabaseSession extends DatabaseSession {

  Session hibernateSession = null;

  // constructors
  
  public HibernateDatabaseSession(Session hibernateSession)
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
