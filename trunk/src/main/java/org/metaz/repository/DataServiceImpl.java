/**
 * 
 */
package org.metaz.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			sess = MetaZ.getHibernateSessionFactory()
					.openSession();
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
		} 
		finally {
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
			sess = MetaZ.getHibernateSessionFactory()
					.openSession();
			t = sess.beginTransaction();
			int recordRowsDeleted = sess.createQuery("delete Record")
					.executeUpdate();
			LOG.info("Deleted [" + recordRowsDeleted + "] Record rows");
			int metaDataRowsDeleted = sess.createQuery("delete MetaData")
					.executeUpdate();
			LOG.info("Deleted [" + metaDataRowsDeleted + "] MetaData rows");
			int hierSetRowsDeleted = sess.createQuery(
					"delete HierarchicalStructuredTextMetaDataSet")
					.executeUpdate();
			LOG.info("Deleted [" + hierSetRowsDeleted
					+ "] HierarchicalStructuredTextMetaDataSet rows");
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

	public Record getRecord(URI id) throws Exception {
		Record rec = null;
		Session sess = null;
		try {
			sess = MetaZ.getInstance().getHibernateSessionFactory()
					.openSession();
			rec = (Record) sess.createQuery(
					"from Record r where r.uri.value = ?").setString(0,
					id.toString()).uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			
			if (sess != null) {
				sess.close();
			}
			
		}
		return rec;
	}

}
