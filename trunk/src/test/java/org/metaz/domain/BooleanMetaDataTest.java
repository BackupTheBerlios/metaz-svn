package org.metaz.domain;

import junit.framework.TestCase;

/**
 * In this test class also the methods of the superclass MetaData will be tested.
 *
 * @author E.J. Spaans
 */
public class BooleanMetaDataTest extends TestCase {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private BooleanMetaData boolMetaData;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  @Override
  protected void setUp()
                throws Exception
  {

    boolMetaData = new BooleanMetaData();

  } // end setUp()

  /**
   * Test method for 'org.metaz.domain.BooleanMetaData.getValue()'
   */
  public void testValueGetterAndSetter() {

    boolMetaData.setValue(true);
    assertTrue("Expected value to be a Boolean", boolMetaData.getValue() instanceof Boolean);
    assertEquals("Unexpected value.", true, boolMetaData.getValue());

  } // end testValueGetterAndSetter()

  /**
   * Test method for 'org.metaz.domain.MetaData.getDescription()'
   */
  public void testDescriptionGetterAndSetter() {

    boolMetaData.setDescription("someDescription");
    assertTrue("Expected description to be a String", boolMetaData.getDescription() instanceof String);
    assertEquals("Unexpected value.", "someDescription", boolMetaData.getDescription());

  } // end testDescriptionGetterAndSetter()

  /**
   * Test method for 'org.metaz.domain.MetaData.isMandatory()'
   */
  public void testMandatoryGetterAndSetter() {

    boolMetaData.setMandatory(true);
    assertEquals("Unexpected value.", true, boolMetaData.isMandatory());

  } // end testMandatoryGetterAndSetter()

  /**
   * Test method for 'org.metaz.domain.MetaData.getName()'
   */
  public void testNameGetterAndSetter() {

    boolMetaData.setName("someName");
    assertTrue("Expected name to be a String", boolMetaData.getName() instanceof String);
    assertEquals("Unexpected value.", "someName", boolMetaData.getName());

  } // end testNameGetterAndSetter()

  /**
   * Test method for 'org.metaz.domain.MetaData.isOptional()'
   */
  public void testOptionalGetterAndSetter() {

    boolMetaData.setOptional(false);
    assertEquals("Unexpected value.", false, boolMetaData.isOptional());

  } // end testOptionalGetterAndSetter()

} // end BooleanMetaDataTest
