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
    String WHITESPACE = " ";
    String FIELDDELIMITER = ":";

    public SearchServiceImplTest(String sTestName) {
        super(sTestName);
    }

    public static void main(String args[]) {
        String args2[] = {"-noloading", "org.metaz.repository.SearchServiceImplTest"};
        TestRunner.main(args2);
    }

    public void setUp() throws Exception {
    //record 1  (contains some complex hierarchies) 
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
        
    //record 2   (all metadata fields present)
        //mandatory metadata
        TextMetaData title2 = new TextMetaData();
        title2.setValue("het beste van Raymann is laat");
        TextMetaData didacticFunction2 = new TextMetaData();
        didacticFunction2.setValue("Oefening");
        TextMetaData productType2 = new TextMetaData();
        productType2.setValue("Video");
        BooleanMetaData secured2 = new BooleanMetaData();
        secured2.setValue(true);
        TextMetaData fileFormat2 = new TextMetaData();
        fileFormat2.setValue("video/x-ms-wmv");
        HyperlinkMetaData uri2 = new HyperlinkMetaData();
        uri2.setValue("http://cgi.omroep.nl/cgi-bin/streams?/nps/raymannislaat/bb.20051223_tante.wmv");
        Record rec2 = new Record(title2,secured2,fileFormat2,didacticFunction2,productType2,uri2);
        //optional metadata subject
        TextMetaData subject2 = new TextMetaData();
        subject2.setValue("Het Beste van Raymann is Laat - gepresenteerd door Jörgen Raymann ");
        rec2.setSubject(subject2);
        //optional metadata description
        HtmlTextMetaData description2 = new HtmlTextMetaData();
        description2.setValue("<p class=MsoNormal><span style='font-size:6.5pt;font-family:Verdana;color:black'>Tante\n" + 
        "Es heeft een openhartig gesprek met zanger <strong><span style='font-family:\n" + 
        "Verdana'>Xander du Buisonjé </span></strong>en schrijver <strong><span\n" + 
        "style='font-family:Verdana'>Jan Wolkers</span></strong>. <span class=GramE>In <strong><span\n" + 
        "style='font-family:Verdana'>De Groeten uit </span></strong></span></span><st1:City><st1:place><span\n" + 
        "  class=GramE><strong><span style='font-size:6.5pt;font-family:Verdana;\n" + 
        "  color:black'>Paramaribo</span></strong></span></st1:place></st1:City><span\n" + 
        "class=GramE><span style='font-size:6.5pt;font-family:Verdana;color:black'> laat\n" + 
        "Quintis zien dat Surinaamse mannen wél kunnen <a href='something'>koken</a>.</span></span><span\n" + 
        "style='font-size:6.5pt;font-family:Verdana;color:black'> <strong><span\n" + 
        "style='font-family:Verdana'>Stand-up comedy</span></strong> is deze keer van </span><st1:place><st1:PlaceName><span\n" + 
        "  style='font-size:6.5pt;font-family:Verdana;color:black'>Adam</span></st1:PlaceName><span\n" + 
        " style='font-size:6.5pt;font-family:Verdana;color:black'> </span><st1:PlaceName><span\n" + 
        "  style='font-size:6.5pt;font-family:Verdana;color:black'>Hills</span></st1:PlaceName></st1:place><span\n" + 
        "style='font-size:6.5pt;font-family:Verdana;color:black'> en T-Rexx.</span></p>");
        rec2.setDescription(description2);
        //optional metadata keywords
        TextMetaData keywords2 = new TextMetaData();
        keywords2.setValue("muziek, rap, video, uitzending, tv, battel, battle, thebattle, raymann is laat, Jurgen, comedian, young, urban, tante Esselien");
        rec2.setKeywords(keywords2);
        //optional metadata targetEndUser
        HierarchicalStructuredTextMetaData targetEndUser2 = new HierarchicalStructuredTextMetaData();
        TextMetaData docent = new TextMetaData();
        docent.setValue("docent");
        targetEndUser2.addChild(docent);
        rec2.setTargetEndUser(targetEndUser2);
        //optional metadata schooltype
        HierarchicalStructuredTextMetaDataSet schoolType = new HierarchicalStructuredTextMetaDataSet();
        HierarchicalStructuredTextMetaData schoolType1 = new HierarchicalStructuredTextMetaData();
        TextMetaData so = new TextMetaData();
        so.setValue("Speciaal onderwijs");
        schoolType1.addChild(so);
        schoolType.addHierarchy(schoolType1);
        rec2.setSchoolType(schoolType);
        //optional metadata school discipline
        HierarchicalStructuredTextMetaData schoolDiscipline = new HierarchicalStructuredTextMetaData();
        TextMetaData nl = new TextMetaData();
        nl.setValue("Nederlands");
        TextMetaData nl_anders = new TextMetaData();
        nl_anders.setValue("Nederlands voor anderstaligen");
        schoolDiscipline.addChild(nl);
        schoolDiscipline.addChild(nl_anders);
        rec2.setSchoolDiscipline(schoolDiscipline);
        //optional metadata professional situation
        HierarchicalStructuredTextMetaData profSit2 = new HierarchicalStructuredTextMetaData();
        TextMetaData sit1 = new TextMetaData();
        sit1.setValue("Omgaan met verschillen");
        TextMetaData sit2 = new TextMetaData();
        sit2.setValue("omgaan met verschillen in taal, leerstijl, motivatie en tempo");
        profSit2.addChild(sit1);
        profSit2.addChild(sit2);
        rec2.setProfessionalSituation(profSit2);
        //optional metadata competence
        TextMetaData comp2 = new TextMetaData();
        comp2.setValue("Reflectie");
        rec2.setCompetence(comp2);
        //optional metadata aggregation level
        TextMetaData aggLevel2 = new TextMetaData();
        aggLevel2.setValue("lineair");
        rec2.setAggregationLevel(aggLevel2);
        //optional metadata didactic scenario
        TextMetaData didSc = new TextMetaData();
        didSc.setValue("de leerlingen bekijken de video en lossen nadien vragen op");
        rec2.setDidacticScenario(didSc);
        //optional metadata required time
        NumericMetaData time2 = new NumericMetaData();
        time2.setValue(60);
        rec2.setRequiredTime(time2);
        //optional metadata rights
        TextMetaData rights  = new TextMetaData();
        rights.setValue("Copyright 2004 NPS");
        rec2.setRights(rights);
        //optional metadata file size
        NumericMetaData size = new NumericMetaData();
        size.setValue(550);
        rec2.setFileSize(size);
        //optional metadata playing time
        NumericMetaData plTime = new NumericMetaData();
        plTime.setValue(40);
        rec2.setPlayingTime(plTime);
        //optional metadata technical requirements
        TextMetaData tech2 = new TextMetaData();
        tech2.setValue("Windows Media Player 9");
        rec2.setTechnicalRequirements(tech2);
        //optional metadata creation date
        DateMetaData date1 = new DateMetaData();
        date1.setValue(new Date(20051214));
        rec2.setCreationDate(date1);
        //optional metadata last changed date
        DateMetaData date2 = new DateMetaData();
        date2.setValue(new Date(20060129));
        rec2.setLastChangedDate(date2);
        //optional metadata version
        TextMetaData version = new TextMetaData();
        version.setValue("compilatie");
        rec2.setVersion(version);
        //optional metadata status
        TextMetaData status = new TextMetaData();
        status.setValue("Gereed");
        rec2.setStatus(status);
        //optional metadata role and name
        TextMetaData roleName = new TextMetaData();
        roleName.setValue("geen idee wat hier in te vullen");
        rec2.setRoleName(roleName);
        
    // record 3 (only mandatory metadata fields present)
        TextMetaData title3 = new TextMetaData();
        title3.setValue("nederlands.nl");
        TextMetaData didacticFunction3 = new TextMetaData();
        didacticFunction3.setValue("Leestekst");
        TextMetaData productType3 = new TextMetaData();
        productType3.setValue("Document");
        BooleanMetaData secured3 = new BooleanMetaData();
        secured3.setValue(false);
        TextMetaData fileFormat3 = new TextMetaData();
        fileFormat3.setValue("text/html");
        HyperlinkMetaData uri3 = new HyperlinkMetaData();
        uri3.setValue("http://www.nederlands.nl/nedermap");
        Record rec3 = new Record(title3,secured3,fileFormat3,didacticFunction3,productType3,uri3);
        
    //index creation
        l = new Vector();
        l.add(rec1);
        l.add(rec2);
        l.add(rec3);
        ssi.doUpdate(l);
    }

    public void tearDown() throws Exception {
        
    }
    
    public void testDoSearch2() throws Exception {
        HashMap hm = new HashMap();
        hm.put(MetaData.PRODUCTTYPE,"Document");
        hm.put("","nederlands");
        assertEquals(1,((List)ssi.doSearch(hm)).size());
    }
    
    public void testDoSearch() throws Exception {
        assertEquals(2,((List)ssi.doSearch(MetaData.PRODUCTTYPE+FIELDDELIMITER+"Document")).size());
        assertEquals(1,((List)ssi.doSearch("nederlands")).size());
        assertEquals(1,((List)ssi.doSearch(MetaData.SCHOOLDISCIPLINE+FIELDDELIMITER+"Nederlands")).size());
        assertEquals(1,((List)ssi.doSearch("nederlands"+WHITESPACE+MetaData.PRODUCTTYPE+FIELDDELIMITER+"Document")).size());
        assertEquals(1,((List)ssi.doSearch(MetaData.SCHOOLTYPE+FIELDDELIMITER+"VMBO")).size());
        assertEquals(1,((List)ssi.doSearch(MetaData.SCHOOLTYPE+FIELDDELIMITER+"\"Voortgezet onderwijs\"")).size());
        assertEquals(1,((List)ssi.doSearch("nederlands"+WHITESPACE+MetaData.PRODUCTTYPE+FIELDDELIMITER+"Document"+WHITESPACE+MetaData.SCHOOLTYPE+FIELDDELIMITER+"\"Voortgezet onderwijs\"")).size());
        assertEquals(1,((List)ssi.doSearch("Buisonjé")).size());
        assertEquals(1,((List)ssi.doSearch(MetaData.DIDACTICFUNCTION+FIELDDELIMITER+"Leestekst"+WHITESPACE+MetaData.PRODUCTTYPE+FIELDDELIMITER+"Document"+WHITESPACE+MetaData.PROFESSIONALSITUATION+FIELDDELIMITER+"\"Leiding geven aan groepsprocessen\"")).size());
        assertEquals(0,((List)ssi.doSearch(MetaData.SCHOOLTYPE+FIELDDELIMITER+"onbestaand")).size());
        assertEquals(1,((List)ssi.doSearch("Ray*")).size()); //raymann //wildcard search
        assertEquals(2,((List)ssi.doSearch("h?ef")).size()); //href //wildcard search
        assertEquals(2,((List)ssi.doSearch("vaat~0.2")).size()); //fuzzy search 
        assertEquals(2,((List)ssi.doSearch("{nectar TO negen}")).size()); // range search
        assertEquals(2,((List)ssi.doSearch("mannen^4 koning")).size()); //boost
        assertEquals(3,((List)ssi.doSearch("monarchie OR uitzending OR nederlands.nl")).size()); //boolean
        assertEquals(1,((List)ssi.doSearch("\"Surinaamse mannen\"")).size()); //phrase
        assertEquals(1,((List)ssi.doSearch("(monarchie OR uitzending) AND nederlands")).size()); //grouping
    }
}
