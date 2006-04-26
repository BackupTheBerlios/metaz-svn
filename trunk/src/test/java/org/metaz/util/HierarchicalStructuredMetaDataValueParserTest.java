package org.metaz.util;

import junit.framework.TestCase;

/**
 * Class for testing HierarchicalStructuredMetaDataValueParser
 *
 * @author Erik-Jan Spaans, Sammy Dalewyn
 * @version 1.0
  */
public class HierarchicalStructuredMetaDataValueParserTest extends TestCase {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Test method for 'org.metaz.util.HierarchicalStructuredMetaDataValueParser.getLowestHierarchicalLevelValue(String)'
   */
  public void testGetLowestHierarchicalLevelValue() {

    String testValue = "this/is/a/hierarchical/level/value/string";
    String result = HierarchicalStructuredMetaDataValueParser.getLowestHierarchicalLevelValue(testValue);

    assertEquals("string", result);

  }

  /**
   * Test method for 'org.metaz.util.HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(String)'
   */
  public void testgetAllHierarchicalPaths() {

    String   testValue = "/this/is/a/hierarchical/level/value/string";
    String[] components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);

    assertNotNull(components);

    for (int i = 0; i < components.length; i++) {

      System.out.println(components[i]);

    }

    testValue = "/this";
    components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);
    assertNotNull(components);

    for (int i = 0; i < components.length; i++) {

      System.out.println(components[i]);

    }

    testValue = null;
    components = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(testValue);
    assertNull(components);

  }

}
