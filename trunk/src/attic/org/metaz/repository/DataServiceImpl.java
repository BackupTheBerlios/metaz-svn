/**
 * 
 */
package org.metaz.repository;

import java.net.URI;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.MetaData;
import org.metaz.domain.Record;
import org.metaz.util.MetaZ;



/**
 * @author J. Goelen
 * @author E.J. Spaans
 * 
 */
public class DataServiceImpl implements DataService {

    private static Logger LOG;
    
            
    public DataServiceImpl() {
        LOG = MetaZ.getLogger(DataServiceImpl.class);
        MetaZ.getHibernateSessionFactory(); //eager loading
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.DataService#getRecords(java.util.List)
     */
    public List<Record> getRecords(List<URI> ids) throws Exception {
                
        Session sess = null;
        List<Record> records = null;
        
        try {
                                    
            sess = MetaZ.getHibernateSessionFactory().openSession();

            //convert URI list into String list            
            List<String> sIds = new Vector<String>();
            for(URI uri:ids){
                sIds.add(uri.toString());
            }
            
            Criteria crit = sess.createCriteria(HyperlinkMetaData.class);
            crit.add( Expression.in("value", sIds ) );                        
            List hmd = crit.list();
            
            if(hmd.size()>0){
                crit = sess.createCriteria(Record.class);            
                crit.add( Expression.in( "uri", hmd ) );                                 
                records = (List<Record>)crit.list();
            }
                                    
        } catch (HibernateException e) {
            LOG.error("HibernateException", e);
            throw e; // rethrow error
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
                                        
        return records;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.RepositoryService#doUpdate(java.util.List)
     */
    public void doUpdate(List<Record> records) throws Exception {
        Session sess = null;
        Transaction t = null;
        try {
            //clear 2nd-level cache
            MetaZ.getHibernateSessionFactory().evict(MetaData.class);
            MetaZ.getHibernateSessionFactory().evict(HierarchicalStructuredTextMetaData.class);
            MetaZ.getHibernateSessionFactory().evict(HierarchicalStructuredTextMetaDataSet.class);
            MetaZ.getHibernateSessionFactory().evict(Record.class);
            MetaZ.getHibernateSessionFactory().evictQueries();
            sess = MetaZ.getHibernateSessionFactory().openSession();
            t = sess.beginTransaction();
            Iterator<Record> it = records.iterator();
            while (it.hasNext()) {
                Record r = it.next();
                sess.save(r);
            }
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.RepositoryService#doPurge()
     */
    public void doPurge() throws Exception {
        Session sess = null;
        Transaction t = null;
        try {                                    
            sess = MetaZ.getHibernateSessionFactory().openSession();
            t = sess.beginTransaction();
            int recordRowsDeleted = sess.createQuery("delete Record")
                    .executeUpdate();
            LOG.info("Deleted [" + recordRowsDeleted + "] Record rows");
            int metaDataRowsDeleted = sess.createQuery("delete MetaData")
                    .executeUpdate();
            LOG.info("Deleted [" + metaDataRowsDeleted + "] MetaData rows");

            // FIXME cascade="all" doesn't clean association tables

            // going to jdbc mode
            Connection conn = sess.connection();

            Statement statm = conn.createStatement();

            int hierSetRowsDeleted = statm
                    .executeUpdate("delete from HIERARCHICAL_METADATA_SET_ITEMS");

            LOG.info("Deleted [" + hierSetRowsDeleted
                    + "] HierarchicalStructuredTextMetaDataSet rows");
            int hierRowsDeleted = conn.createStatement().executeUpdate(
                    "delete from HIERARCHICAL_METADATA_LIST_ITEMS");

            LOG.info("Deleted [" + hierRowsDeleted
                    + "] HierarchicalStructuredTextMetaData rows");

            // leave JDBC mode
            statm.close();

            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.DataService#getUniqueValues(java.lang.String)
     */
    public Record getRecord(URI id) throws Exception {
                                        
        List<URI> uris = new Vector<URI>();
        uris.add(id);
        
        List<Record> records = getRecords(uris);
        if(records!=null && records.size()>0){
            return records.get(0);
        } else {
            return null;
        }                                                
    }

    
    /* (non-Javadoc)
     * @see org.metaz.repository.DataService#getUniqueFieldValues(java.lang.String)
     */
    public List getUniqueFieldValues(String recordField) throws Exception {
                        
        Session sess = null;
        List mdList = null;
                
        try {
                                    
            sess = MetaZ.getHibernateSessionFactory().openSession();

            Criteria crit = sess.createCriteria(Record.class)
            .setProjection(Projections.property(recordField))                        
            .setFetchMode(recordField,FetchMode.JOIN);
            mdList = crit.list();
                                                
        } catch (HibernateException e) {
            LOG.error("HibernateException", e);
            throw e; // rethrow error
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
                                                                                               
        return mdList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.DataService#getUniqueValues(java.lang.String)
     */
    public List getUniqueHierarchicalFieldValues(String recordField)
            throws Exception {

        String query = "select distinct r." + recordField + ".value"
                + " from Record r";
        return null;
    }

   

}
