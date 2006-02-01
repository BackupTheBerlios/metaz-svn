// A Hibernate session
// Author: Falco Paul

package org.metaz.examples.repository;

import org.hibernate.Session;

public class HibernateDatabaseSession implements DatabaseSession {

  Session hibernateSession = null;

  public void setHibernateSession(Session hibernateSession)
  {
    this.hibernateSession = hibernateSession;
  }

  public Session getHibernateSession()
  {
    return hibernateSession;
  }
   
}
