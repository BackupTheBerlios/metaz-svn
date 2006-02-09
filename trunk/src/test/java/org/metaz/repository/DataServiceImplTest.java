package org.metaz.repository;

import junit.framework.TestCase;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;

public class DataServiceImplTest extends TestCase {

	private static Logger LOG = Logger.getLogger(DataServiceImplTest.class);

	/*
	 * Test method for 'org.metaz.repository.DataServiceImpl.getRecords(List<URI>)'
	 */
	public void testAddRecord(){
		BasicConfigurator.configure();
		LOG.debug("Testing");
		TextMetaData title = new TextMetaData();
		title.setName("titel");
		title.setValue("someValue");
		title.setMandatory(true);

		BooleanMetaData secured = new BooleanMetaData();
		secured.setName("beveiligd");
		secured.setValue(false);
		secured.setMandatory(true);

		TextMetaData fileFormat = new TextMetaData();
		fileFormat.setName("bestandsformaat");
		fileFormat.setValue("application/pdf");
		fileFormat.setMandatory(true);

		TextMetaData didacticalFunction = new TextMetaData();
		didacticalFunction.setName("didactischeFunctie");
		didacticalFunction.setValue("bla");
		didacticalFunction.setMandatory(true);

		TextMetaData productType = new TextMetaData();
		productType.setName("producttype");
		productType.setValue("instructie");
		productType.setMandatory(true);

		HyperlinkMetaData uri = new HyperlinkMetaData();
		uri.setName("URI");
		uri.setValue("http://www.ou.nl/stories/ruuddemoor.pdf");

		Record rec = new Record(title, secured, fileFormat, didacticalFunction,
				productType, uri);

		Configuration cfg;
		SessionFactory sf;
		Session sess;
		try {
			cfg = new Configuration().configure();
			sf = cfg.buildSessionFactory();
			sess = sf.openSession();
			Transaction t = sess.beginTransaction();
			sess.save(rec);
			sess.flush();
			t.commit();
			sess.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

}
