package org.metaz.repository;

import org.apache.log4j.Logger;

import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import org.metaz.util.MetaZ;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Implementation of the Lucene Search Service.
 */
public class SearchServiceImpl
  implements SearchService
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger       logger = MetaZ.getLogger(SearchServiceImpl.class);
  private static final String INDEXPATH = "repository/searchservice/searchindex";
  private static final String TERMDELIMITER = ":";
  private static final String WHITESPACE = " ";
  private static final char   DOUBLEQUOTE = '\"';
  private static final String EMPTYSTRING = "";
  private static final String STEMDICT = "repository/searchservice/wordlists/wordstem.txt";
  private static final String STOPWORDS = "repository/searchservice/wordlists/stopwords.txt";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String[]      keywords = new String[] {
                                     MetaData.TARGETENDUSER, MetaData.SCHOOLTYPE, MetaData.SCHOOLDISCIPLINE,
                                     MetaData.DIDACTICFUNCTION, MetaData.PRODUCTTYPE, MetaData.PROFESSIONALSITUATION,
                                     MetaData.COMPETENCE
                                   };
  private MetaZ         app = MetaZ.getInstance();
  private DutchAnalyzer analyzer;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Constructor
     */
  public SearchServiceImpl() {

    File stopwordsFile = app.getRelativeFile(STOPWORDS);

    analyzer = new DutchAnalyzer(stopwordsFile);

    File stemdictFile = app.getRelativeFile(STEMDICT);

    analyzer.setStemDictionary(stemdictFile);

  } // end SearchServiceImpl()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Clears the search index.
   */
  public void doPurge() {

    try {

      File        f = app.getRelativeFile(INDEXPATH);
      Directory   directory = FSDirectory.getDirectory(f, false);
      IndexReader reader = IndexReader.open(directory);

      for (int i = 0; i < reader.maxDoc(); i++) {

        reader.delete(i);

      }

      logger.info("Search Index cleared");

      reader.close();
      directory.close();

    } // end try
    catch (IOException ex) {

      logger.error(ex.getMessage());

    } // end catch

  } // end doPurge()

  /**
   * Updates the search index.
   *
   * @param records the records to be added to the search index
   *
   * @throws Exception when the search index cannot be updated
   */
  public void doUpdate(List<Record> records)
                throws Exception
  {

    try {

      File        f = app.getRelativeFile(INDEXPATH);
      IndexWriter writer = new IndexWriter(f, analyzer, true);

      if (records != null) {

        for (int i = 0; i < records.size(); i++) {

          writer.addDocument(RecordDocument.toDocument(records.get(i)));

        } // end for

      } // end if

      logger.info("" + writer.docCount() + " records added to Search Index");
      writer.optimize();
      writer.close();

      /*
         //for testing purposes
         IndexReader reader = IndexReader.open(f);
         for (int i = 0; i < reader.numDocs(); i++) {
             logger.info ((reader.document(i)).toString());
         }
         reader.close();
       */
    } // end try
    catch (Exception ex) {

      logger.error(ex.getMessage());

    } // end catch

  } // end doUpdate()

  /**
   * Returns Record instances that match the specified query.<p>The query syntax is:<br>
   * <code>Query ::= (Clause)+<br>
   * *  Clause ::= [&lt;FULLTEXTSEARCHPHRASE&gt;] || [&lt;TERM&gt;:&lt;VALUE&gt;]+</code><br>
   * <code>&lt;FULLTEXTSEARCHPHRASE&gt;</code> should always precede term-value combinations and shall not contain any
   * semicolon.</p>
   *
   * @param query the search query
   *
   * @return A list of URI's and scores assorted in a Result List
   */
  public List<Result<URI>> doSearch(String query) {

    try {

      HashMap queryHashMap = new HashMap();
      int     endOfFullText = query.length();

      for (int i = 0; i < keywords.length; i++) {

        int keyword = query.indexOf(keywords[i] + TERMDELIMITER);

        if (keyword != -1) {

          if (keyword < endOfFullText) {

            endOfFullText = keyword;

          }

          int    semicolon = query.indexOf(TERMDELIMITER, keyword);
          char   afterSemicolon = query.charAt(semicolon + 1);
          String keywordValue;

          if (afterSemicolon == DOUBLEQUOTE) {

            int nextDoubleQuote = query.indexOf(DOUBLEQUOTE, semicolon + 2);

            keywordValue = query.substring(semicolon + 2, nextDoubleQuote);

          } // end if
          else {

            int nextWhiteSpace = query.indexOf(WHITESPACE, semicolon + 1);

            if (nextWhiteSpace == -1) {

              nextWhiteSpace = query.length();

            } // end if

            keywordValue = query.substring(semicolon + 1, nextWhiteSpace);

          } // end else

          queryHashMap.put(keywords[i], keywordValue);

        } // end if

      } // end for

      if (endOfFullText > 0) {

        String fullTextValue = query.substring(0, endOfFullText);

        queryHashMap.put(EMPTYSTRING, fullTextValue);

      } // end if

      return doSearch(queryHashMap);

    } // end try
    catch (Exception ex) {

      logger.error(ex.getMessage());

    } // end catch

    return null;

  } // end doSearch()

  /**
   * Returns Records that match the specified query<p>The query is a hashmap containing the term-value
   * combinations to search for.</p>
   *
   * @param hmquery the search query hashmap
   *
   * @return A list of URI's and scores assorted in a Result List
   */
  public List<Result<URI>> doSearch(HashMap hmquery) {

    try {

      File     f = app.getRelativeFile(INDEXPATH);
      Searcher searcher = new IndexSearcher(f.getCanonicalPath());

      BooleanQuery q = new BooleanQuery();
      String       fullText = (String) hmquery.get(EMPTYSTRING);

      if (fullText != null) {

        Query fullTextQuery = QueryParser.parse(fullText, RecordDocument.MERGED, analyzer);

        q.add(fullTextQuery, true, false); //logische OR
                                           //q.add(fullTextQuery, false, true); //logische AND

      } // end if

      for (int i = 0; i < keywords.length; i++) {

        String keywordValue = (String) hmquery.get(keywords[i]);

        if (keywordValue != null) {

          Term  keyword = new Term(keywords[i], keywordValue);
          Query keywordQuery = new TermQuery(keyword);

          q.add(keywordQuery, false, false);

        } // end if

      } // end for

      logger.info("Searching for: " + q.toString());

      Hits hits = searcher.search(q);

      logger.info(hits.length() + " total matching records");

      List<Result<URI>> resultList = new Vector<Result<URI>>();

      for (int i = 0; i < hits.length(); i++) {

        Document doc = hits.doc(i);
        String   suri = doc.get(MetaData.URI);
        URI      uri = URI.create(suri);
        float    score = hits.score(i);

        if (uri != null) {

          Result<URI> result = new Result<URI>(uri, score);

          resultList.add(result);
          logger.info(i + 1 + ": " + suri + ":" + score);

        } // end if

      } // end for

      searcher.close();
      logger.info("resultList contains " + resultList.size() + " elements");

      return resultList;

    } // end try
    catch (Exception ex) {

      logger.error(ex.getMessage());

    } // end catch

    return null;

  } // end doSearch()

} // end SearchServiceImpl
