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
        
        public void testgetAllHierarchicalPaths() {
            String testValue = "/this/is/a/hierarchical/level/value/string";
            String[] components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);
            assertNotNull(components);
            for(int i=0; i<components.length; i++){
                System.out.println(components[i]);
            }
            testValue = "/this";
            components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);
            assertNotNull(components);
            for(int i=0; i<components.length; i++){
                System.out.println(components[i]);
            }
            testValue = null;
            components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);
            assertNull(components);
        }

}
