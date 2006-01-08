package org.metaz.domain;

import java.util.Date;

import junit.framework.TestCase;

public class DateMetaDataTest extends TestCase {

	/*
	 * Test method for 'org.metaz.domain.DateMetaData.getValue()'
	 */
	public void testValueGetterAndSetter() {
		DateMetaData dateMetaData = new DateMetaData();
		Date date = new Date();
		dateMetaData.setValue(date);
		assertTrue("Expected value to be a date", dateMetaData.getValue() instanceof Date);
		assertEquals("Expected equal dates", date, dateMetaData.getValue());
	}

}
