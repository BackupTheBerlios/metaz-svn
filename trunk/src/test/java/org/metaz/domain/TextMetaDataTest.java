package org.metaz.domain;

import junit.framework.TestCase;

public class TextMetaDataTest extends TestCase {

	/*
	 * Test method for 'org.metaz.domain.TextMetaData.getValue()'
	 */
	public void testValueGetterAndSetter() {
		TextMetaData textMetaData = new TextMetaData();
		textMetaData.setValue("MyText");
		assertTrue("Expected value to be a String", textMetaData.getValue() instanceof String);
		assertEquals("Unexpected value", "MyText", textMetaData.getValue());
	}

}
