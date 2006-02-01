// This is a class that encapsulates a lot of the nasty Hibernate transaction details
// Author: Falco Paul

package org.metaz.examples.repository;

import java.io.Serializable;

import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUtil implements Serializable {

  // constructor

  public HibernateUtil() {
    
  }

  // conveinience method that aquires a factory object prior to calling the transaction method
  
  public static ActionResult managedSession(HibernateDatabaseInteraction interaction) {
   
    SessionFactory factory = HibernateEnvironment.getInstance().getSessionFactory();
  
    if (factory == null)
      return new ErrorResult("Could not aquire a Hibernate session factory instance");
      
    return managedSession(factory, interaction);
        
  }
  
  // transaction abstraction

  public static ActionResult managedSession(SessionFactory factory, HibernateDatabaseInteraction interaction) {

    Session hibernateSession = null;
    Transaction hibernateTransaction = null;
    ActionResult result = new SuccessResult();
 
    try {
    
      hibernateSession = factory.openSession();
      hibernateTransaction = hibernateSession.beginTransaction();
      interaction.setHibernateSession(hibernateSession);
      
      result = interaction.execute();
      if (result instanceof ErrorResult)
        throw new HibernateException( ((ErrorResult) result).getMessage());

      hibernateSession.flush();
      
      if (interaction instanceof DatabaseTransaction)
        hibernateTransaction.commit();
      
    } catch (Exception e) {

      result = new ErrorResult(e.getClass().toString() + " : " + e.getMessage());
      
      e.printStackTrace();
      
      if (interaction instanceof DatabaseTransaction)
      try {

        if (hibernateTransaction != null)        
          hibernateTransaction.rollback();

        ((DatabaseTransaction) interaction).rolledBack();
        
        
      } catch (Exception e2) {
        
        // already fatal now...

        ((DatabaseTransaction) interaction).rollBackFailed();
        
      }
    
    }
    finally {

      try {
        
        if (hibernateSession != null)
          hibernateSession.close();
        
      } catch (Exception e2) {

        // not a fatal error, so we don't change dbResult

        interaction.sessionCloseFailed();

      }
      
    }
    
    return result;
    
  }

  // conveinience method that returns a CRD object for a given UUID
  
  public static ActionResult objectForUuId(HibernateDatabaseQuery query, String objectName, String uuId) throws Exception {

    Object obj;
    Iterator i;
    
    i = query.getHibernateSession()
        .createQuery("from " + objectName +  " as object where object.uuID = :oid")
        .setParameter("oid", uuId)
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
