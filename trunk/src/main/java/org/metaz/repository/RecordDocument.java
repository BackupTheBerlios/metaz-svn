package org.metaz.repository;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateField;

import org.metaz.domain.Record;

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
     * <p>Only the field <code>title</code> is tokenized.
     * @param r the MetaZ record
     * @return a Lucene document
     */
    public static Document toDocument(Record r) throws NullPointerException {
        Document doc = new Document();
        doc.add(Field.Text("title",(String)r.getTitle().getValue()));
        doc.add(Field.Keyword("secured",r.getSecured().getValue().toString()));
        doc.add(Field.Keyword("fileFormat",(String)r.getFileFormat().getValue()));
        doc.add(Field.Keyword("didacticalFunction",(String)r.getDidacticalFunction().getValue()));
        doc.add(Field.Keyword("productType",(String)r.getProductType().getValue()));
        doc.add(Field.Keyword("uri",(String)r.getUri().getValue()));
        return doc;
    }
    
    /**
     * Empty constructor
     */
    public RecordDocument() {
    }
}
