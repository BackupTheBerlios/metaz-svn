package org.metaz.util;

import junit.framework.TestCase;

public class HierarchicalStructuredMetaDataValueParserTest extends TestCase {

	/*
	 * Test method for 'org.metaz.util.HierarchicalStructuredMetaDataValueParser.getLowestHierarchicalLevelValue(String)'
	 */
	public void testGetLowestHierarchicalLevelValue() {
		
		String testValue = "this/is/a/hierarchical/level/value/string";
		String result = HierarchicalStructuredMetaDataValueParser.getLowestHierarchicalLevelValue(testValue);
		assertEquals("string", result);
	}

}
