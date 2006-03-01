package org.metaz.domain;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Tests the MetaData of type Date
 *
 * @author Erik-Jan Spaans
 * @version 1.0
 */
public class DateMetaDataTest extends TestCase {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Test method for 'org.metaz.domain.DateMetaData.getValue()'
   */
  public void testValueGetterAndSetter() {

    DateMetaData dateMetaData = new DateMetaData();
    Date         date = new Date();

    dateMetaData.setValue(date);
    assertTrue("Expected value to be a date", dateMetaData.getValue() instanceof Date);
    assertEquals("Expected equal dates", date, dateMetaData.getValue());

  } // end testValueGetterAndSetter()

} // end DateMetaDataTest
