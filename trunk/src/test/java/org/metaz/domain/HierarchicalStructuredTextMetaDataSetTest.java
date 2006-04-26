package org.metaz.domain;

import junit.framework.TestCase;

import junit.swingui.TestRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests the metadata type HierarchicalStructurdTextMetaDataSet
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class HierarchicalStructuredTextMetaDataSetTest extends TestCase {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private TextMetaData                       level1a;
  private TextMetaData                       level2a;
  private TextMetaData                       level2b;
  private TextMetaData                       level3b;
  private HierarchicalStructuredTextMetaData hstmd1;
  private HierarchicalStructuredTextMetaData hstmd2;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Creates a new HierarchicalStructuredTextMetaDataSetTest object.
     *
     * @param sTestName the name of the test object
     */
  public HierarchicalStructuredTextMetaDataSetTest(String sTestName) {
    super(sTestName);

  } // end HierarchicalStructuredTextMetaDataSetTest()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * the main function
   *
   * @param args empty
   */
  public static void main(String[] args) {

    String[] args2 = {"-noloading", "org.metaz.domain.HierarchicalStructuredTextMetaDataSetTest"
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

    level1a = new TextMetaData();
    level1a.setValue("Voortgezet onderwijs");
    level2a = new TextMetaData();
    level2a.setValue("HAVO");
    level2b = new TextMetaData();
    level2b.setValue("VBO");
    level3b = new TextMetaData();
    level3b.setValue("VMBO");
    hstmd1 = new HierarchicalStructuredTextMetaData();
    hstmd1.addChild(level1a);
    hstmd1.addChild(level2a);
    hstmd2 = new HierarchicalStructuredTextMetaData();
    hstmd2.addChild(level1a);
    hstmd2.addChild(level2b);
    hstmd2.addChild(level3b);

  } // end setUp()

  /**
   *
   * @see HierarchicalStructuredTextMetaDataSet#setValue(Object)
   */
  public void testSetValue() {

    HashSet hierarchies = new HashSet();

    hierarchies.add(hstmd1);
    hierarchies.add(hstmd2);

    HierarchicalStructuredTextMetaDataSet hstmdSet = new HierarchicalStructuredTextMetaDataSet();

    hstmdSet.setValue(hierarchies);
    assertEquals("Two hierarchies expected", 2, ((Set) hstmdSet.getValue()).size());

  } // end testSetValue()

  /**
   *
   * @see HierarchicalStructuredTextMetaDataSet#addHierarchy(HierarchicalStructuredTextMetaData)
   */
  public void testAddHierarchy() {

    HierarchicalStructuredTextMetaDataSet hstmdSet = new HierarchicalStructuredTextMetaDataSet();

    hstmdSet.addHierarchy(hstmd1);
    hstmdSet.addHierarchy(hstmd2);
    assertEquals("Two hierarchies expected", 2, ((Set) hstmdSet.getValue()).size());

  } // end testAddHierarchy()

} // end HierarchicalStructuredTextMetaDataSetTest
