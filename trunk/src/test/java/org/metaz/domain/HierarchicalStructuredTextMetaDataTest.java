package org.metaz.domain;

import junit.framework.TestCase;

import junit.swingui.TestRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Tests the metadata type HierarchicalStructuredTextMetaData
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class HierarchicalStructuredTextMetaDataTest extends TestCase {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private TextMetaData level1;
  private TextMetaData level2;
  private TextMetaData level3;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Creates a new HierarchicalStructuredTextMetaDataTest object.
     *
     * @param sTestName the name of the test object
     */
  public HierarchicalStructuredTextMetaDataTest(String sTestName) {
    super(sTestName);

  } // end HierarchicalStructuredTextMetaDataTest()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * the main function
   *
   * @param args empty
   */
  public static void main(String[] args) {

    String[] args2 = {"-noloading", "org.metaz.domain.HierarchicalStructuredTextMetaDataTest"
                     };

    TestRunner.main(args2);

  } // end main()

  /**
   * Primes the testing environment
   *
   * @throws Exception Setup failed
   */
  protected void setUp()
                throws Exception
  {

    level1 = new TextMetaData();
    level1.setValue("Omgaan met een groep");
    level2 = new TextMetaData();
    level2.setValue("leiding geven aan groepsprocessen");
    level3 = new TextMetaData();
    level3.setValue("kindniveau");

  } // end setUp()

  /**
   *
   * @see HierarchicalStructuredTextMetaData#setValue(Object)
   */
  public void testSetValue() {

    Vector levels = new Vector();

    levels.add(0, level1);
    levels.add(1, level2);
    levels.add(2, level3);

    HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();

    hstmd.setValue(levels);
    assertNotNull("Expected hierarchical structure", hstmd.getValue());

    Iterator i = ((List) hstmd.getValue()).iterator();

    while (i.hasNext()) {

      assertNotNull("Expected textmetadata", i.next());

    } // end while

  } // end testSetValue()

  /**
   *
   * @see HierarchicalStructuredTextMetaData#addChild(MetaData)
   */
  public void testAddChild() {

    HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();

    hstmd.addChild(level1);
    hstmd.addChild(level2);
    hstmd.addChild(level3);
    assertNotNull("Expected hierarchical structure", hstmd.getValue());

    Iterator i = ((List) hstmd.getValue()).iterator();

    while (i.hasNext()) {

      assertNotNull("Expected textmetadata", i.next());

    } // end while

  } // end testAddChild()

  /**
   *
   * @see HierarchicalStructuredTextMetaData#toString()
   */
  public void testToString() {

    HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();

    hstmd.addChild(level1);
    hstmd.addChild(level2);
    hstmd.addChild(level3);

    String treePath = hstmd.toString();

    System.out.println(treePath);
    assertEquals("/" + (String) level1.getValue() + "/" + (String) level2.getValue() + "/" +
                 (String) level3.getValue(), treePath);

  } // end testToString()

} // end HierarchicalStructuredTextMetaDataTest
