/**
 * 
 */
package org.metaz.repository;

import java.net.URI;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.metaz.domain.Record;
import org.metaz.util.MetaZ;

/**
 * @author jurgoe
 * @author E.J. Spaans
 * 
 */
public class DataServiceImpl implements DataService {

    private static Logger LOG;

    public DataServiceImpl() {
        LOG = MetaZ.getLogger(DataServiceImpl.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.DataService#getRecords(java.util.List)
     */
    public List<Record> getRecords(List<URI> ids) throws Exception {
        List<Record> recs = new ArrayList<Record>();
        Iterator<URI> it = ids.iterator();
        while (it.hasNext()) {
            URI uri = it.next();
            Record r = getRecord(uri);
            recs.add(r);
        }
        return recs;
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
        String query = "from Record r where r.uri.value = '" + id.toString()
                + "'";
        return (Record) execQuery(query, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.metaz.repository.DataService#getUniqueValues(java.lang.String)
     */
    public List getUniqueFieldValues(String recordField) throws Exception {

        String query = String.format(
                        "select r.%1$s " +
                        "from Record r " /*+
                        "where not(r.%1$s is null)  " +
                        "order by r.%1$s"*/,
                        recordField);
        
        List results = (List) execQuery(query, false);
        
        //FIXME: use HQL to sort and distinct
        TreeSet sorted = new TreeSet(results);
        results = new Vector(sorted);
        
        return results;
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

    /**
     * @param query
     * @param uniqueResult
     * @return
     * @throws Exception
     */
    private Object execQuery(String query, boolean uniqueResult)
            throws Exception {
        Session sess = null;
        Object result = null;
        LOG.debug(query);
        try {
            sess = MetaZ.getHibernateSessionFactory().openSession();
            if (uniqueResult) {
                result = sess.createQuery(query).uniqueResult();
            } else {
                result = sess.createQuery(query).list();
                                                                
            }
        } catch (HibernateException e) {
            LOG.error("HibernateException", e);
            throw e; // rethrow error
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return result;
    }

}
