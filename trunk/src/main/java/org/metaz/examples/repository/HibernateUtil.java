// This is a class that encapsulates a lot of the nasty Hibernate transaction details
// @author Falco Paul

package org.metaz.examples.repository;

import java.io.Serializable;

import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class HibernateUtil implements Serializable {

  // constructor

  public HibernateUtil() {
    
  }

  // conveinience method that aquires a factory object prior to calling the transaction method
  
  public static ActionResult managedSession(DatabaseInteraction interaction) {
   
    SessionFactory factory = HibernateEnvironment.getInstance().getSessionFactory();
  
    if (factory == null)
      return new ErrorResult("Could not aquire a Hibernate session factory instance");
      
    return managedSession(factory, interaction);
        
  }
  
  // transaction abstraction

  public static ActionResult managedSession(SessionFactory factory, DatabaseInteraction interaction) {

    Session hibernateSession = null;
    DatabaseSession dbSession = null;
    Transaction hibernateTransaction = null;
    ActionResult dbResult = new SuccessResult();
 
    try {
    
      hibernateSession = factory.openSession();
      hibernateTransaction = hibernateSession.beginTransaction();
      dbSession = new HibernateDatabaseSession(hibernateSession);
      
      ActionResult result = interaction.execute(dbSession);
      if (dbResult instanceof ErrorResult)
        throw new HibernateException( ((ErrorResult) dbResult).getMessage());

      hibernateSession.flush();
      
      if (interaction instanceof DatabaseTransaction)
        hibernateTransaction.commit();
      
    } catch (Exception e) {

      dbResult = new ErrorResult(e.getClass().toString() + " : " + e.getMessage());
      
      e.printStackTrace();
      
      if (interaction instanceof DatabaseTransaction)
      try {

        if (hibernateTransaction != null)        
          hibernateTransaction.rollback();

        ((DatabaseTransaction) interaction).rolledBack(dbSession);
        
        
      } catch (Exception e2) {
        
        // already fatal now...

        ((DatabaseTransaction) interaction).rollBackFailed(dbSession);
        
      }
    
    }
    finally {

      try {
        
        if (hibernateSession != null)
          hibernateSession.close();
        
      } catch (Exception e2) {

        // not a fatal error, so we don't change dbResult

        interaction.sessionCloseFailed(dbSession);

      }
      
    }
    
    return dbResult;
    
  }

  public static ActionResult objectForId(HibernateDatabaseSession dbSession, 
                                         String objectName, String id) throws Exception {

    Object obj;
    Iterator i;
    
    i = dbSession.getHibernateSession()
        .createQuery ("from " + objectName +  " as object where object.id = :id")
        .setParameter("id", id)
        .iterate();
    
    if (i.hasNext())
      obj = (Object) i.next();
    else
      return new NoDataErrorResult();
      
    if (i.hasNext())
      return new ErrorResult("More than one row found");
      
    return new SingleRowResult(obj);
        
  }
 
}
