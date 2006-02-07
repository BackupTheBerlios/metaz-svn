package org.metaz.repository;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.metaz.domain.Record;
import org.metaz.util.MetaZ;
import org.metaz.domain.MetaData;


/**
 * Implementation of the Lucene Search Service.
 * NOTE: this version only supports searches on record title
 */
public class SearchServiceImpl implements SearchService {

    private static Logger logger = MetaZ.getLogger(SearchServiceImpl.class);

    private static final String INDEXPATH =
        "repository/searchservice/searchindex";

    private String[] keywords =
        new String[] {MetaData.TARGETENDUSER, MetaData.SCHOOLTYPE, MetaData.SCHOOLDISCIPLINE, MetaData.DIDACTICFUNCTION, MetaData.PRODUCTTYPE, MetaData.PROFESSIONALSITUATION, MetaData.COMPETENCE};

    private static final String TERMDELIMITER = ":";

    private static final String WHITESPACE = " ";

    private static final char DOUBLEQUOTE = '\"';

    private static final String EMPTYSTRING = "";

    private MetaZ app = MetaZ.getInstance();

    private DutchAnalyzer analyzer;

    private static final String STEMDICT =
        "repository/searchservice/wordlists/wordstem.txt";

    private static final String STOPWORDS =
        "repository/searchservice/wordlists/stopwords.txt";

    /**
     * Constructor
     */
    public SearchServiceImpl() {
        File stopwordsFile = app.getRelativeFile(STOPWORDS);
        analyzer = new DutchAnalyzer(stopwordsFile);
        File stemdictFile = app.getRelativeFile(STEMDICT);
        analyzer.setStemDictionary(stemdictFile);
    }

    /**
     * Clears the search index.
     */
    public void doPurge() {
        try {
            File f = app.getRelativeFile(INDEXPATH);
            Directory directory = FSDirectory.getDirectory(f, false);
            IndexReader reader = IndexReader.open(directory);

            for (int i = 0; i < reader.maxDoc(); i++)
                reader.delete(i);

            logger.info("Search Index cleared");

            reader.close();
            directory.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Updates the search index.
     * @param records the records to be added to the search index
     */
    public void doUpdate(List<Record> records) throws Exception {
        try {
            File f = app.getRelativeFile(INDEXPATH);
            IndexWriter writer = new IndexWriter(f, analyzer, true);

            if (records != null) {
                for (int i = 0; i < records.size(); i++) {
                    writer.addDocument(RecordDocument.toDocument(records.get(i)));
                }
            }

            logger.info("" + writer.docCount() + " records added to Search Index");
            writer.optimize();
            writer.close();
            IndexReader reader = IndexReader.open(f);
            // for testing purposes
            //for (int i = 0; i < reader.numDocs(); i++) {
            //    logger.info(((Document)reader.document(i)).toString());
            //}
            reader.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Returns Record instances that match the specified query.
     * <p>The query syntax is:<br>
     * <code>Query ::= (Clause)+<br>
     * Clause ::= [&lt;FULLTEXTSEARCHPHRASE&gt;] || [&lt;TERM&gt;:&lt;VALUE&gt;]+</code>
     * <br><code>&lt;FULLTEXTSEARCHPHRASE&gt;</code> should always precede term-value combinations and shall not contain any
     * semicolon.
     * @param query the search query
     * @return A list of URI's and scores assorted in a Result List
     */
    public List<Result<URI>> doSearch(String query) {
        try {
            HashMap queryHashMap = new HashMap();
            int endOfFullText = query.length();

            for (int i = 0; i < keywords.length; i++) {
                int keyword = query.indexOf(keywords[i] + TERMDELIMITER);
                if (keyword != -1) {
                    if (keyword < endOfFullText)
                        endOfFullText = keyword;
                    int semicolon = query.indexOf(TERMDELIMITER, keyword);
                    char afterSemicolon = query.charAt(semicolon + 1);
                    String keywordValue;
                    if (afterSemicolon == DOUBLEQUOTE) {
                        int nextDoubleQuote =
                            query.indexOf(DOUBLEQUOTE, semicolon + 2);
                        keywordValue = query.substring(semicolon + 2, nextDoubleQuote);
                    } else {
                        int nextWhiteSpace = query.indexOf(WHITESPACE, semicolon+1);
                        if(nextWhiteSpace==-1) {
                            nextWhiteSpace = query.length();
                        }
                        keywordValue = query.substring(semicolon + 1, nextWhiteSpace);
                    }
                    queryHashMap.put(keywords[i], keywordValue);
                }
            }
            if (endOfFullText > 0) {
                String fullTextValue = query.substring(0, endOfFullText);
                queryHashMap.put(EMPTYSTRING, fullTextValue);
            }
            return doSearch(queryHashMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Returns Records that match the specified query
     * <p>The query is a hashmap containing the term-value combinations
     * to search for.</p>
     * @param hmquery the search query hashmap
     * @return A list of URI's and scores assorted in a Result List
     */
    public List<Result<URI>> doSearch(HashMap hmquery) {
        try {
            File f = app.getRelativeFile(INDEXPATH);
            Searcher searcher = new IndexSearcher(f.getCanonicalPath());

            BooleanQuery q = new BooleanQuery();
            String fullText = (String)hmquery.get(EMPTYSTRING);
            if (fullText != null) {
                Query fullTextQuery = QueryParser.parse(fullText, RecordDocument.MERGED, analyzer);
                q.add(fullTextQuery, true, false);
            }
            for (int i = 0; i < keywords.length; i++) {
                String keywordValue = (String)hmquery.get(keywords[i]);
                if (keywordValue != null) {
                    Term keyword = new Term(keywords[i], keywordValue);
                    Query keywordQuery = new TermQuery(keyword);
                    q.add(keywordQuery, false, false);
                }
            }

            logger.info("Searching for: " + q.toString());
            Hits hits = searcher.search(q);
            logger.info(hits.length() + " total matching records");
            List<Result<URI>> resultList = new Vector<Result<URI>>();
            for (int i = 0; i < hits.length(); i++) {
                Document doc = hits.doc(i);
                String suri = doc.get(MetaData.URI);
                URI uri = URI.create(suri);
                float score = hits.score(i);
                if (uri != null) {
                    Result<URI> result = new Result<URI>(uri, score);
                    resultList.add(result);
                    logger.info(i + 1 + ": " + suri + ":" + score);
                }
            }
            searcher.close();
            logger
            .info("resultList contains " + resultList.size() + " elements");
            return resultList;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
