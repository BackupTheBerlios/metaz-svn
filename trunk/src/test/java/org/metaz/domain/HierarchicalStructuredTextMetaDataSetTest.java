package org.metaz.domain;

import java.util.HashSet;

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import junit.swingui.TestRunner;

import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;

public class HierarchicalStructuredTextMetaDataSetTest extends TestCase {
    TextMetaData level1a;
    TextMetaData level2a;
    TextMetaData level2b;
    TextMetaData level3b;
    HierarchicalStructuredTextMetaData hstmd1;
    HierarchicalStructuredTextMetaData hstmd2;
    
    public HierarchicalStructuredTextMetaDataSetTest(String sTestName) {
        super(sTestName);
    }

    public static void main(String args[]) {
        String args2[] = {"-noloading", "org.metaz.domain.HierarchicalStructuredTextMetaDataSetTest"};
        TestRunner.main(args2);
    }

    protected void setUp() throws Exception {
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
    }

    /**
     * @see HierarchicalStructuredTextMetaDataSet#setValue(Object)
     */
    public void testSetValue() {
        HashSet hierarchies = new HashSet();
        hierarchies.add(hstmd1);
        hierarchies.add(hstmd2);
        HierarchicalStructuredTextMetaDataSet hstmdSet = new HierarchicalStructuredTextMetaDataSet();
        hstmdSet.setValue(hierarchies);
        assertEquals("Two hierarchies expected",2,((Set)hstmdSet.getValue()).size());
    }
    
    /**
     * @see HierarchicalStructuredTextMetaDataSet#addHierarchy(HierarchicalStructuredTextMetaData)
     */
    public void testAddHierarchy() {
        HierarchicalStructuredTextMetaDataSet hstmdSet = new HierarchicalStructuredTextMetaDataSet();
        hstmdSet.addHierarchy(hstmd1);
        hstmdSet.addHierarchy(hstmd2);
        assertEquals("Two hierarchies expected",2,((Set)hstmdSet.getValue()).size());
    }
}
