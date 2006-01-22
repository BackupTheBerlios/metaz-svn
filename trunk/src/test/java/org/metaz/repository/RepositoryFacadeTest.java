package org.metaz.repository;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;
import org.metaz.util.MetaZ;

/**
 * @author Jurgen Goelen
 */
public class RepositoryFacadeTest extends TestCase {

	Facade facade;
	
	
	Record rec;

	@Override
	protected void setUp() throws Exception {
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

		rec = new Record(title, secured, fileFormat, didacticalFunction,
				productType, uri);
		
		facade = MetaZ.getInstance().getRepositoryFacade();
	}
				
	

	/*
	 * Test method for 'org.metaz.repository.FacadeImpl.doUpdate(List<Record>)'
	 */
	public void testDoUpdate() {
		//FIXME: extend test criteria
		List<Record> records= new Vector<Record>();
		records.add(rec);
		try {
			facade.doUpdate(records);							
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}	
	}

	/*
	 * Test method for 'org.metaz.repository.FacadeImpl.doSearch(String)'
	 */
	public void testDoSearch() {
		//FIXME: extend test criteria
		try {
			List<Result<Record>> hits = facade.doSearch("*");
			assertNotNull(hits.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}					
	}
	
}
