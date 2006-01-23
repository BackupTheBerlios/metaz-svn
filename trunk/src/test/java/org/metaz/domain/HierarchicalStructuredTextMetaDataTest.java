package org.metaz.domain;

import java.util.Iterator;
import java.util.Vector;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import junit.swingui.TestRunner;

import org.metaz.domain.HierarchicalStructuredTextMetaData;

public class HierarchicalStructuredTextMetaDataTest extends TestCase {
    TextMetaData level1;
    TextMetaData level2;
    TextMetaData level3;
    
    public HierarchicalStructuredTextMetaDataTest(String sTestName) {
        super(sTestName);
    }

    public static void main(String args[]) {
        String args2[] = {"-noloading", "org.metaz.domain.HierarchicalStructuredTextMetaDataTest"};
        TestRunner.main(args2);
    }

    protected void setUp() throws Exception {
        level1 = new TextMetaData();
        level1.setValue("Omgaan met een groep");
        level2 = new TextMetaData();
        level2.setValue("leiding geven aan groepsprocessen");
        level3 = new TextMetaData();
        level3.setValue("kindniveau");
    }

    /**
     * @see HierarchicalStructuredTextMetaData#setValue(Object)
     */
    public void testSetValue() {
        Vector levels = new Vector();
        levels.add(0,level1);
        levels.add(1,level2);
        levels.add(2,level3);
        HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();
        hstmd.setValue(levels);
        assertNotNull("Expected hierarchical structure",hstmd.getValue());
        Iterator i = ((List)hstmd.getValue()).iterator();
        while(i.hasNext()){
            assertNotNull("Expected textmetadata",i.next());
        }
    }
    
    /**
     * @see HierarchicalStructuredTextMetaData#addChild(MetaData)
     */
    public void testAddChild() {
        HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();
        hstmd.addChild(level1);
        hstmd.addChild(level2);
        hstmd.addChild(level3);
        assertNotNull("Expected hierarchical structure",hstmd.getValue());
        Iterator i = ((List)hstmd.getValue()).iterator();
        while(i.hasNext()){
            assertNotNull("Expected textmetadata",i.next());
        }
    }

    /**
     * @see HierarchicalStructuredTextMetaData#toString()
     */
    public void testToString() {
        HierarchicalStructuredTextMetaData hstmd = new HierarchicalStructuredTextMetaData();
        hstmd.addChild(level1);
        hstmd.addChild(level2);
        hstmd.addChild(level3);
        String treePath = hstmd.toString();
        System.out.println(treePath);
        assertEquals("/"+(String)level1.getValue()+"/"+(String)level2.getValue()+"/"+(String)level3.getValue(),treePath);
    }


}
