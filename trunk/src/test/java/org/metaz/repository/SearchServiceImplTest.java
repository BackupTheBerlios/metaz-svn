package org.metaz.repository;

import java.util.List;
import java.util.Vector;
import java.util.Date;

import java.util.HashMap;

import junit.framework.TestCase;
import junit.swingui.TestRunner;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;
import org.metaz.domain.HtmlTextMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.NumericMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;
import org.metaz.domain.MetaData;

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
    //record 1   
        //mandatory metadata
        TextMetaData title1 = new TextMetaData();
        title1.setValue("Geschiedenis van het Koninkrijk der Nederlanden");
        TextMetaData didacticFunction1 = new TextMetaData();
        didacticFunction1.setValue("Leestekst");
        TextMetaData productType1 = new TextMetaData();
        productType1.setValue("Document");
        BooleanMetaData secured1 = new BooleanMetaData();
        secured1.setValue(false);
        TextMetaData fileFormat1 = new TextMetaData();
        fileFormat1.setValue("text/plain");
        HyperlinkMetaData uri1 = new HyperlinkMetaData();
        uri1.setValue("http://nl.wikipedia.org/wiki/Geschiedenis_van_Nederland");
        Record rec1 = new Record(title1,secured1,fileFormat1,didacticFunction1,productType1,uri1);
        // optional metadata profession situation
        TextMetaData profSit1Level1 = new TextMetaData();
        profSit1Level1.setValue("Omgaan met een groep");
        TextMetaData profSit1Level2 = new TextMetaData();
        profSit1Level2.setValue("Leiding geven aan groepsprocessen");
        HierarchicalStructuredTextMetaData hstmd1 = new HierarchicalStructuredTextMetaData();
        hstmd1.addChild(profSit1Level1);
        hstmd1.addChild(profSit1Level2);
        rec1.setProfessionalSituation(hstmd1);
        // optional metadata school type
        TextMetaData vo = new TextMetaData();
        vo.setValue("Voortgezet onderwijs");
        TextMetaData havo = new TextMetaData();
        havo.setValue("HAVO");
        TextMetaData vbo = new TextMetaData();
        vbo.setValue("VBO");
        TextMetaData vmbo = new TextMetaData();
        vmbo.setValue("VMBO");
        HierarchicalStructuredTextMetaData hstmd2 = new HierarchicalStructuredTextMetaData();
        hstmd2.addChild(vo);
        hstmd2.addChild(havo);
        HierarchicalStructuredTextMetaData hstmd3 = new HierarchicalStructuredTextMetaData();
        hstmd3.addChild(vo);
        hstmd3.addChild(vbo);
        hstmd3.addChild(vmbo);
        HierarchicalStructuredTextMetaDataSet hstmds1 = new HierarchicalStructuredTextMetaDataSet();
        hstmds1.addHierarchy(hstmd2);
        hstmds1.addHierarchy(hstmd3);
        rec1.setSchoolType(hstmds1);
        // optional metadata keywords
        TextMetaData keywords1 = new TextMetaData();
        keywords1.setValue("19de eeuw, Willem I, Nederland, België, Luxemburg");
        rec1.setKeywords(keywords1);
        // optional metadata description
        HtmlTextMetaData description1 = new HtmlTextMetaData();
        description1.setValue("<p>Nederland werd een <a href=\"/wiki/Monarchie\" title=\"Monarchie\">monarchie</a> met Willem Frederik als koning <a href=\"/wiki/Willem_I_der_Nederlanden\" title=\"Willem I der Nederlanden\">Willem I</a>. Zijn <a href=\"/wiki/Verenigd_Koninkrijk_der_Nederlanden\" title=\"Verenigd Koninkrijk der Nederlanden\">Verenigd Koninkrijk der Nederlanden</a> omvatte oorspronkelijk het huidige Nederland, België en <a href=\"/wiki/Groothertogdom_Luxemburg\" title=\"Groothertogdom Luxemburg\">Luxemburg</a>. Willem pakte de 'wederopbouw' krachtig aan en stimuleerde vooral handel en industrie. Zo liet hij talrijke kanalen graven en wegen verbeteren. Vooral de zware industrie in België profiteerde hiervan. Maar de Belgen voelden zich spoedig tweedeklas burgers: in bestuur en hoge legerposten waren de Belgen zwaar ondervertegenwoordigd hoewel ze een veel groter deel van de bevolking en het leger uitmaakten. Bovendien waren er grote religieuze verschillen (het <a href=\"/wiki/Katholicisme\" title=\"Katholicisme\">katholieke</a> zuiden tegenover het <a href=\"/wiki/Protestantisme\" title=\"Protestantisme\">protestantse</a> noorden), economische verschillen (het zuiden industrialiseerde, het noorden bleef een handelsnatie) en taalkundige verschillen (niet alleen <a href=\"/wiki/Wallonie\" title=\"Wallonie\">Wallonië</a> was in die tijd <a href=\"/wiki/Frans\" title=\"Frans\">Franssprekend</a>, maar ook de <a href=\"/wiki/Bourgeoisie\" title=\"Bourgeoisie\">bourgeoisie</a> in <a href=\"/wiki/Vlaanderen\" title=\"Vlaanderen\">Vlaanderen</a>) . Bovendien werden deze verschillen geaccentueerd en aangewakkerd door Frankrijk, dat moest immers niets hebben van de sterke bufferstaat aan zijn grens, en meende bovendien recht te hebben op de <a href=\"/wiki/Zuidelijke_Nederlanden\" title=\"Zuidelijke Nederlanden\">Zuidelijke Nederlanden</a>. En ook de Katholieke kerk wilde haar bevoorrechte positie in het katholieke zuiden behouden zodat de katholieke prelaten eveneens eerder tegenwerkten dan samenwerkten met Willem.</p>\n" + 
        "<div class=\"editsection\" style=\"float:right;margin-left:5px;\">[<a href=\"/w/index.php?title=Geschiedenis_van_Nederland&amp;action=edit&amp;section=13\" title=\"Geschiedenis van Nederland\">bewerk</a>]</div>\n" + 
        "<p><a name=\"De_Belgische_opstand\" id=\"De_Belgische_opstand\"></a></p>");
        rec1.setDescription(description1);
        
    //record 2   
    
    //index creation
        l = new Vector();
        l.add(rec1);
        ssi.doUpdate(l);
    }

    public void tearDown() throws Exception {
        
    }
    
    public void testDoSearch() throws Exception {
        
    }
}
