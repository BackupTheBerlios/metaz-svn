package org.metaz.repository;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;
import org.metaz.util.MetaZ;

/**
 * @author Jurgen Goelen
 */
public class RepositoryFacadeTest extends TestCase {

    Facade facade;

    public RepositoryFacadeTest() {
        facade = MetaZ.getRepositoryFacade();
    }

    @Override
    protected void setUp() throws Exception {
        // clean repository
        facade.doUpdate(new Vector<Record>());
    }

    private Record createRecord(String title, boolean secured,
            String fileFormat, String dictaticFunc, String productType,
            String uri,String schoolType) {
        Record record = null;

        TextMetaData metaTitle = new TextMetaData();
        metaTitle.setName("titel");
        metaTitle.setValue(title);
        metaTitle.setMandatory(true);

        BooleanMetaData metaSecured = new BooleanMetaData();
        metaSecured.setName("beveiligd");
        metaSecured.setValue(secured);
        metaSecured.setMandatory(true);

        TextMetaData metaFileFormat = new TextMetaData();
        metaFileFormat.setName("bestandsformaat");
        metaFileFormat.setValue(fileFormat);
        metaFileFormat.setMandatory(true);

        TextMetaData metaDidactiFunc = new TextMetaData();
        metaDidactiFunc.setName("didactischeFunctie");
        metaDidactiFunc.setValue(dictaticFunc);
        metaDidactiFunc.setMandatory(true);

        TextMetaData metaProductType = new TextMetaData();
        metaProductType.setName("producttype");
        metaProductType.setValue(productType);
        metaProductType.setMandatory(true);

        HyperlinkMetaData metaUri = new HyperlinkMetaData();
        metaUri.setName("URI");
        metaUri.setValue(uri);
        
        
        HierarchicalStructuredTextMetaData hv1 = new HierarchicalStructuredTextMetaData();
        for (int i = 0; i < 4; i++) {
            TextMetaData tm = new TextMetaData();
            tm.setValue(schoolType + i);
            hv1.addChild(tm);            
        }
        HierarchicalStructuredTextMetaDataSet metaSchoolType = new HierarchicalStructuredTextMetaDataSet();
        metaSchoolType.addHierarchy(hv1);
        
        

        record = new Record(metaTitle, metaSecured, metaFileFormat,
                metaDidactiFunc, metaProductType, metaUri);
        
        record.setSchoolType(metaSchoolType);

        return record;
    }

    private List<Record> createSamples(){
        
        List<Record> records = new Vector<Record>();

        records.add(createRecord("title1", true, "pdf", "didac1", "type1",
                "uri1","type_a"));
        records.add(createRecord("title2", true, "txt", "didac2", "type4",
                "uri2","type_b"));
        records.add(createRecord("title3", true, "htm", "didac4", "type5",
                "uri3","type_c"));
        records.add(createRecord("title4", true, "xml", "didac1", "type4",
                "uri4","type_d"));
        records.add(createRecord("title5", true, "pdf", "didac2", "type2",
                "uri5","type_e"));
        records.add(createRecord("title6", true, "doc", "didac3", "type1",
                "uri6","type_f"));
        records.add(createRecord("title7", true, "xls", "didac1", "type3",
                "uri7","type_a"));
        records.add(createRecord("title8", true, "htm", "didac2", "type1",
                "uri8","type_b"));
        
        return records;
        
    }
    
    /*
     * Test method for 'org.metaz.repository.FacadeImpl.doUpdate(List<Record>)'
     */
    public void testDoUpdate() {
        // FIXME: extend test criteria
        List<Record> records = createSamples();
        
        try {
            facade.doUpdate(records);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /*
     * Test method for 'org.metaz.repository.FacadeImpl.doSearch(String)'
     */
    public void testDoSearch() {
        // FIXME: extend test criteria
        try {

            List<Record> records =  createSamples();            
            
            facade.doUpdate(records);

            List<Result<Record>> hits = facade.doSearch("titel:title1");
            assertNotNull(hits.get(0));

            hits = facade.doSearch("producttype:type1");
            assertNotNull(hits.get(0));

            hits = facade.doSearch("producttype:jjjjjjjj");
            assertEquals(0, hits.size());

            hits = facade.doSearch("title?");
            assertEquals(8, hits.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetCompetenceValues() throws Exception {

        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getCompetenceValues();
            assertEquals(0, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    public void testGetDidacticFunctionValues() throws Exception {
        
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getDidacticFunctionValues();
            assertEquals(4, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testGetProductTypeValues() throws Exception {
    
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getProductTypeValues();
            assertEquals(5, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        
    }
    
    public void testGetProfessionalSituationValues() throws Exception {
        
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getProfessionalSituationValues();
            assertEquals(0, values.size());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testGetSchoolDisciplineValues() throws Exception {
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getSchoolDisciplineValues();
            assertEquals(0, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }    
    }
    
    public void testGetSchoolTypesValues() throws Exception {
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getSchoolTypesValues();
            assertEquals(6, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }    
    }

    public void testGetTargetEndUserValues() throws Exception {
        try {

            List<Record> records =  createSamples();

            facade.doUpdate(records);

            List values = null;

            values = facade.getTargetEndUserValues();
            assertEquals(0, values.size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
}
