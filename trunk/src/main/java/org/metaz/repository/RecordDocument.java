package org.metaz.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HtmlTextMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.MetaData;

import org.metaz.domain.NumericMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;

public class RecordDocument {
    /**
     * Makes a Lucene document for a MetaZ record.
     * <p>The document contains the mandatory metadata elements:
     * <li>title,
     * <li>secured,
     * <li>fileFormat,
     * <li>didacticalFunction,
     * <li>productType,
     * <li>uri.
     * </p>
     * <p>Some may contain some optional metadata as well 
     * @param r the MetaZ record
     * @return a Lucene document
     */
    public static Document toDocument(Record r) throws Exception{
        Document doc = new Document();
        //mandatory metadata
        doc.add(Field.Text("title",(String)r.getTitle().getValue()));//full text searchable
        doc.add(Field.UnIndexed("secured",r.getSecured().getValue().toString()));//not searchable
        doc.add(Field.UnIndexed("fileFormat",(String)r.getFileFormat().getValue()));//not searchable
        doc.add(Field.Keyword("didacticalFunction",(String)r.getDidacticalFunction().getValue()));//searchable keyword
        doc.add(Field.Keyword("productType",(String)r.getProductType().getValue()));//searchable keyword
        doc.add(Field.UnIndexed("uri",(String)r.getUri().getValue()));//not searchable
        //optional metadata
        List<MetaData> l = r.getOptionalMetaData();
        Iterator<MetaData> it = l.iterator();
        while(it.hasNext()) {
            MetaData m = it.next();
            //Object o = m.getValue();
            String value = "";
            String name = m.getName();
            if (m instanceof TextMetaData) {
                value = (String) m.getValue();
            }
            else if (m instanceof BooleanMetaData){
                Boolean bool = (Boolean) m.getValue();
                value = bool.toString();
            }
            else if (m instanceof DateMetaData) {
                Date date = (Date) m.getValue();
                value = date.toString();
            }
            else if (m instanceof HierarchicalStructuredTextMetaData) {
                value = (String) m.getValue();
            }
            else if (m instanceof HyperlinkMetaData) {
                value = (String) m.getValue();
            }
            else if (m instanceof NumericMetaData) {
                Integer integer = (Integer) m.getValue();
                value = integer.toString();
            }
            else if (m instanceof HtmlTextMetaData) {
                value = (String) m.getValue();
            }
            else {
                throw new Exception("No valid MetaData type found.");
            }
            // UPDATE WITH ENGLISH METADATA NAMES
            // full text searchable
            if (name.equalsIgnoreCase("onderwerp") || name.equalsIgnoreCase("omschrijving") || name.equalsIgnoreCase("sleutelwoorden")) {
                doc.add(Field.Text(name,value));
            }
            // searchable keyword
            else if (name.equalsIgnoreCase("beoogde eindgebruiker") || name.equalsIgnoreCase("schooltype") || name.equalsIgnoreCase("vakleergebied")
                    || name.equalsIgnoreCase("beroepssituatie") || name.equalsIgnoreCase("competentie")) {
                doc.add(Field.Keyword(name,value));        
            }
            else {
                doc.add(Field.UnIndexed(name,value));
            }
        }
        return doc;
    }
    
    /**
     * Empty constructor
     */
    public RecordDocument() {
    }
}
