package org.metaz.repository;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.metaz.domain.Record;
import org.metaz.util.MetaZ;


/**
 * Implementation of the Lucene Search Service.
 * NOTE: this version only supports searches on record title
 */
public class SearchServiceImpl implements SearchService{

    private static Logger logger = MetaZ.getLogger(SearchServiceImpl.class); 
    private final static String INDEXPATH = "repository/searchindex"; 
    
    /**
     * Empty constructor
     */
    public SearchServiceImpl(){
        
    }
    /**
     * Clears the search index.
     */
    public void doPurge() {
       try{
           MetaZ app = MetaZ.getInstance();
           File f = app.getRelativeFile(INDEXPATH);
           Directory directory = FSDirectory.getDirectory(f, false);
           IndexReader reader = IndexReader.open(directory);
           
           for (int i = 0; i < reader.maxDoc(); i++)
             reader.delete(i);
             
           logger.info("Search Index cleared");
           
           reader.close();
           directory.close();
       }
       catch(IOException ex){
           logger.error(ex.getMessage());
       }
    }
    
    /**
     * Updates the search index.
     * @param records the records to be added to the search index
     */
    public void doUpdate(List<Record> records) throws Exception {
        try{
            MetaZ app = MetaZ.getInstance();
            File f = app.getRelativeFile(INDEXPATH);
            IndexWriter writer = new IndexWriter(f, new StandardAnalyzer(), true);
            
            if (records!=null){
                for(int i=0; i<records.size(); i++)
                  writer.addDocument(RecordDocument.toDocument(records.get(i)));
            }
            
            logger.info("" + writer.docCount() + " records added to Search Index");
            writer.optimize();
            writer.close();
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }
    
    /**
     * Returns Record instances that match the specified query.
     * @param query the search query
     * @return A list of URI's assorted in a Result List
     */
    public List<Result<URI>> doSearch(String query) {
        try {
            MetaZ app = MetaZ.getInstance();
            File f = app.getRelativeFile(INDEXPATH);
            Searcher searcher = new IndexSearcher(f.getCanonicalPath());
            Analyzer analyzer = new StandardAnalyzer();
            Query q = QueryParser.parse(query,"title",analyzer);
            logger.info("Searching for: " + q.toString("title"));
            Hits hits = searcher.search(q);
            logger.info(hits.length() + " total matching records");
            List<Result<URI>> resultList = new Vector<Result<URI>> ();
            for (int i=0; i<hits.length(); i++) {
                Document doc = hits.doc(i);
                String suri = doc.get("uri");
                URI uri = URI.create(suri);
                float score = hits.score(i);
                if(uri!=null) {
                    Result<URI> result = new Result<URI>(uri,score);
                    resultList.add(result);
                    logger.info(i+1 + ": " + suri +":" + score);
                }
            }
            searcher.close();
            logger.info("resultList contains "+resultList.size()+" elements");
            return resultList;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
