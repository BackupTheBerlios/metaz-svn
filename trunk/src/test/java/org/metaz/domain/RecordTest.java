package org.metaz.domain;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;

public class RecordTest extends TestCase {

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
	}

	/*
	 * Test method for 'org.metaz.domain.Record.getDidacticalFunction()'
	 */
	public void testMandatoryStuff() {
		assertNotNull("Expected title metadata", rec.getTitle());
		assertNotNull("Expected isSecured metadata", rec.getSecured());
		assertNotNull("Expected fileFormat metadata", rec.getFileFormat());
		assertNotNull("Expected didacticalFunction metadata", rec
				.getDidacticFunction());
		assertNotNull("Expected productType metadata", rec.getProductType());
		assertNotNull("Expected uri metadata", rec.getURI());
	}

	public void testAddingOptionalMetaData() {
		DateMetaData lastChangeDate = new DateMetaData();
		lastChangeDate.setName(MetaData.LASTCHANGEDDATE);
		try {
			lastChangeDate.setDateValue("28-01-2005");
		} catch (ParseException e) {
			lastChangeDate.setValue(new Date());
		}
		rec.setLastChangedDate(lastChangeDate);
		assertNotNull("Expected lastChangedDate metadata",rec.getLastChangedDate());
		assertEquals("Wrong name optional metadata", MetaData.LASTCHANGEDDATE,
				rec.getLastChangedDate().getName());
	}

}
