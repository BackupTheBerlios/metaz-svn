package org.metaz.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.MetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;

/**
 * Tests methods of the DataServiceImpl.
 * Note this test is actually an integration test.
 * Note also that the order of methods matters (hmmm)
 * because you can't get a record before it's added.
 * Note that having too many Records in the db with
 * the same uri will fail the test.
 * But at least it's some test!
 * @author E.J. Spaans
 *
 */
public class DataServiceImplTest extends TestCase {

	private static Logger LOG = Logger.getLogger(DataServiceImplTest.class);
	private DataServiceImpl dataService;
	
	@Override
	protected void setUp() throws Exception {
		dataService = new DataServiceImpl();
	}

	/*
	 * Test method for 'org.metaz.repository.DataServiceImpl.getRecords(List<URI>)'
	 */
	public void testAddRecord() throws Exception{
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
		List<Record> l = new ArrayList<Record>();
		l.add(rec);
		dataService.doUpdate(l);
	}

	public void testGetRecord() throws Exception{
		URI uri = new URI("http://www.ou.nl/stories/ruuddemoor.pdf");
		Record rec = dataService.getRecord(uri);
		assertNotNull(rec);
		MetaData m = rec.getUri();
		assertNotNull(m);
		// TODO: [EJS] figure out how to deal with detachment after session closed.
		assertEquals("Wrong value", "http://www.ou.nl/stories/ruuddemoor.pdf", rec.getUri().getValue());
	}
	
	public void testDoPurge() throws Exception {
		dataService.doPurge();
		URI uri = new URI("http://www.ou.nl/stories/ruuddemoor.pdf");
		Record rec = dataService.getRecord(uri);
		assertNull(rec);
	}

}
