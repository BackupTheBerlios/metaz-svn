package org.metaz.repository;

import java.util.List;
import java.util.Vector;
import java.util.Date;

import junit.framework.TestCase;
import junit.swingui.TestRunner;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.NumericMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;

public class SearchServiceImplTest extends TestCase {
    List l;
    SearchServiceImpl ssi = new SearchServiceImpl();

    public SearchServiceImplTest(String sTestName) {
        super(sTestName);
    }

    public static void main(String args[]) {
        String args2[] = {"-noloading", "org.metaz.repository.SearchServiceImplTest"};
        TestRunner.main(args2);
    }

    public void setUp() throws Exception {
        // record 1
        TextMetaData title = new TextMetaData();
        title.setName("titel");
        title.setValue("someValue");
        title.setMandatory(true);

        BooleanMetaData secured = new BooleanMetaData();
        secured.setName("beveiligd");
        secured.setValue(false);
        secured.setMandatory(true);

        TextMetaData fileFormat = new TextMetaData();
        fileFormat.setName("bestandsformaat");
        fileFormat.setValue("application/pdf");
        fileFormat.setMandatory(true);

        TextMetaData didacticalFunction = new TextMetaData();
        didacticalFunction.setName("didactischeFunctie");
        didacticalFunction.setValue("bla");
        didacticalFunction.setMandatory(true);

        TextMetaData productType = new TextMetaData();
        productType.setName("producttype");
        productType.setValue("instructie");
        productType.setMandatory(true);

        HyperlinkMetaData uri = new HyperlinkMetaData();
        uri.setName("URI");
        uri.setValue("http://www.ou.nl/stories/ruuddemoor.pdf");
        
        DateMetaData creationDate = new DateMetaData();
        creationDate.setName("creationdate");
        Date date = new Date(20060112);
        creationDate.setValue(date);

        Record rec1 = new Record(title, secured, fileFormat, didacticalFunction, productType, uri);
        rec1.addOptionalMetaData(creationDate);
                        
        //record 2
        title = new TextMetaData();
        title.setName("titel");
        title.setValue("anotherValue");
        title.setMandatory(true);

        secured = new BooleanMetaData();
        secured.setName("beveiligd");
        secured.setValue(true);
        secured.setMandatory(true);

        fileFormat = new TextMetaData();
        fileFormat.setName("bestandsformaat");
        fileFormat.setValue("jpg");
        fileFormat.setMandatory(true);

        didacticalFunction = new TextMetaData();
        didacticalFunction.setName("didactischeFunctie");
        didacticalFunction.setValue("noIdea");
        didacticalFunction.setMandatory(true);

        productType = new TextMetaData();
        productType.setName("producttype");
        productType.setValue("plaatje");
        productType.setMandatory(true);

        uri = new HyperlinkMetaData();
        uri.setName("URI");
        uri.setValue("http://www.ou.nl/logo.jpg");
        
        NumericMetaData playTime = new NumericMetaData();
        playTime.setName("playtime");
        playTime.setValue(360);

        Record rec2 = new Record(title, secured, fileFormat, didacticalFunction, productType, uri);
        rec2.addOptionalMetaData(playTime);
        
        // record 3
        title = new TextMetaData();
        title.setName("titel");
        title.setValue("another one bites the dust");
        title.setMandatory(true);

        secured = new BooleanMetaData();
        secured.setName("beveiligd");
        secured.setValue(true);
        secured.setMandatory(true);

        fileFormat = new TextMetaData();
        fileFormat.setName("bestandsformaat");
        fileFormat.setValue("mp3");
        fileFormat.setMandatory(true);

        didacticalFunction = new TextMetaData();
        didacticalFunction.setName("didactischeFunctie");
        didacticalFunction.setValue("none");
        didacticalFunction.setMandatory(true);

        productType = new TextMetaData();
        productType.setName("producttype");
        productType.setValue("audio");
        productType.setMandatory(true);

        uri = new HyperlinkMetaData();
        uri.setName("URI");
        uri.setValue("http://www.ou.nl/song.mp3");
        
        playTime = new NumericMetaData();
        playTime.setName("playtime");
        playTime.setValue(120);

        Record rec3 = new Record(title, secured, fileFormat, didacticalFunction, productType, uri);
        rec3.addOptionalMetaData(playTime);
        rec3.addOptionalMetaData(creationDate);
                        
        // record list
        l = new Vector();
        l.add(rec1);
        l.add(rec2);
        l.add(rec3);
    }

    public void tearDown() throws Exception {
        
    }

    /**
     * @see SearchServiceImpl#doUpdate(List)
     */
    public void testDoUpdate() throws Exception {
        ssi.doUpdate(l);
    }

    /**
     * @see SearchServiceImpl#doPurge()
     */
    public void testDoPurge() {
        ssi.doPurge();
    }
    
    public void testCombined() throws Exception {
        ssi.doUpdate(l);
        ssi.doPurge();
        ssi.doUpdate(l);
        ssi.doUpdate(l);
    }
    
    public void testDoSearch() throws Exception {
        ssi.doUpdate(l);
        assertEquals(1,(ssi.doSearch("another")).size());
        assertEquals(1,(ssi.doSearch("ANOTHER")).size());
        assertEquals(2,(ssi.doSearch("another*")).size());
        assertEquals(1,(ssi.doSearch("anotherValue")).size());
        assertEquals(0,(ssi.doSearch("value")).size());
        assertEquals(1,(ssi.doSearch("a*e")).size());
        assertEquals(3,(ssi.doSearch("anotherValue OR someValue OR 'another one bites the dust'")).size());
    }
}
