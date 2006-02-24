package org.metaz.repository.alt;

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

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.index.TermEnum;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HtmlTextMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.NumericMetaData;
import org.metaz.domain.TextMetaData;
import org.metaz.repository.RecordDocument;
import org.metaz.repository.SearchServiceImpl;
import org.metaz.repository.Result;


/**
 * Implementation of the Lucene Search Service.
 */
public class SearchServiceImplAlt implements SearchServiceAlt
{
    private static Logger logger = MetaZ.getLogger(SearchServiceImpl.class);
    private static final String INDEXPATH = "repository/searchservice/searchindex";
    private static final String TERMDELIMITER = ":";
    private static final String WHITESPACE = " ";
    private static final char DOUBLEQUOTE = '\"';
    private static final String EMPTYSTRING = "";
    private static final String STEMDICT = "repository/searchservice/wordlists/wordstem.txt";
    private static final String STOPWORDS = "repository/searchservice/wordlists/stopwords.txt";
    private String[] keywords = new String[]
        {
            MetaData.TARGETENDUSER, MetaData.SCHOOLTYPE,
            MetaData.SCHOOLDISCIPLINE, MetaData.DIDACTICFUNCTION,
            MetaData.PRODUCTTYPE, MetaData.PROFESSIONALSITUATION,
            MetaData.COMPETENCE
        };
    private String[] hierarchicalKeywords = new String[] {MetaData.TARGETENDUSER, MetaData.SCHOOLTYPE, MetaData.SCHOOLDISCIPLINE, MetaData.PROFESSIONALSITUATION};
    private MetaZ app = MetaZ.getInstance();
    private DutchAnalyzer analyzer;

/**
     * Constructor
     */
    public SearchServiceImplAlt()
    {
        File stopwordsFile = app.getRelativeFile(STOPWORDS);
        analyzer = new DutchAnalyzer(stopwordsFile);

        File stemdictFile = app.getRelativeFile(STEMDICT);
        analyzer.setStemDictionary(stemdictFile);
    } // end SearchServiceImpl()

    /**
     * Clears the search index.
     */
    public void doPurge()
    {
        try
        {
            File f = app.getRelativeFile(INDEXPATH);
            Directory directory = FSDirectory.getDirectory(f, false);
            IndexReader reader = IndexReader.open(directory);

            for (int i = 0; i < reader.maxDoc(); i++)
                reader.delete(i);

            logger.info("Search Index cleared");

            reader.close();
            directory.close();
        } // end try
        catch (IOException ex)
        {
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
    public void doUpdate(List<Record> records) throws Exception
    {
        try
        {
            File f = app.getRelativeFile(INDEXPATH);
            IndexWriter writer = new IndexWriter(f, analyzer, true);

            if (records != null)
            {
                for (int i = 0; i < records.size(); i++)
                {
                    writer.addDocument(RecordDocumentAlt.toDocument(records.get(i)));
                } // end for
            } // end if

            logger.info("" + writer.docCount() +
                " records added to Search Index");
            writer.optimize();
            writer.close();

            //for testing purposes
            
            IndexReader reader = IndexReader.open(f);
            for (int i = 0; i < reader.numDocs(); i++) {
                logger.info ((reader.document(i)).toString());
            }
            reader.close();
            
        } // end try
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        } // end catch
    } // end doUpdate()

    /**
     * Returns Record instances that match the specified query.<p>The
     * query syntax is:<br>
     * <code>Query ::= (Clause)+<br>
     * *  Clause ::= [&lt;FULLTEXTSEARCHPHRASE&gt;] || [&lt;TERM&gt;:&lt;VALUE&gt;]+</code><br>
     * <code>&lt;FULLTEXTSEARCHPHRASE&gt;</code> should always precede
     * term-value combinations and shall not contain any semicolon.</p>
     *
     * @param query the search query
     *
     * @return A list of URI's and scores assorted in a Result List
     */
    public List<Result<Record>> doSearch(String query)
    {
        try
        {
            HashMap queryHashMap = new HashMap();
            int endOfFullText = query.length();

            for (int i = 0; i < keywords.length; i++)
            {
                int keyword = query.indexOf(keywords[i] + TERMDELIMITER);

                if (keyword != -1)
                {
                    if (keyword < endOfFullText)
                    {
                        endOfFullText = keyword;
                    }

                    int semicolon = query.indexOf(TERMDELIMITER, keyword);
                    char afterSemicolon = query.charAt(semicolon + 1);
                    String keywordValue;

                    if (afterSemicolon == DOUBLEQUOTE)
                    {
                        int nextDoubleQuote = query.indexOf(DOUBLEQUOTE,
                                semicolon + 2);
                        keywordValue = query.substring(semicolon + 2,
                                nextDoubleQuote);
                    } // end if
                    else
                    {
                        int nextWhiteSpace = query.indexOf(WHITESPACE,
                                semicolon + 1);

                        if (nextWhiteSpace == -1)
                        {
                            nextWhiteSpace = query.length();
                        } // end if

                        keywordValue = query.substring(semicolon + 1,
                                nextWhiteSpace);
                    } // end else

                    queryHashMap.put(keywords[i], keywordValue);
                } // end if
            } // end for

            if (endOfFullText > 0)
            {
                String fullTextValue = query.substring(0, endOfFullText);
                queryHashMap.put(EMPTYSTRING, fullTextValue);
            } // end if

            return doSearch(queryHashMap);
        } // end try
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        } // end catch

        return null;
    } // end doSearch()

    /**
     * Returns Records that match the specified query<p>The query is a
     * hashmap containing the term-value combinations to search for.</p>
     *
     * @param hmquery the search query hashmap
     *
     * @return A list of URI's and scores assorted in a Result List
     */
    public List<Result<Record>> doSearch(HashMap hmquery)
    {
        try
        {
            File f = app.getRelativeFile(INDEXPATH);
            Searcher searcher = new IndexSearcher(f.getCanonicalPath());

            BooleanQuery q = new BooleanQuery();
            String fullText = (String) hmquery.get(EMPTYSTRING);

            if (fullText != null)
            {
                Query fullTextQuery = QueryParser.parse(fullText,
                        RecordDocument.MERGED, analyzer);
                q.add(fullTextQuery, true, false);
            } // end if

            for (int i = 0; i < keywords.length; i++)
            {
                String keywordValue = (String) hmquery.get(keywords[i]);

                if (keywordValue != null)
                {
                    Term keyword = new Term(keywords[i], keywordValue);
                    Query keywordQuery = new TermQuery(keyword);
                    q.add(keywordQuery, false, false);
                } // end if
            } // end for

            logger.info("Searching for: " + q.toString());

            Hits hits = searcher.search(q);
            logger.info(hits.length() + " total matching records");

            List<Result<Record>> resultList = new Vector<Result<Record>>();

            for (int i = 0; i < hits.length(); i++)
            {
                Document doc = hits.doc(i);
                Record rec = new Record();
                
                if(doc.get(MetaData.TITLE)!=null){
                    TextMetaData title = new TextMetaData();
                    title.setValue(doc.get(MetaData.TITLE));
                    rec.setTitle(title);
                }
                if(doc.get(MetaData.DESCRIPTION)!=null){
                    HtmlTextMetaData description = new HtmlTextMetaData();
                    description.setValue(doc.get(MetaData.DESCRIPTION));
                    rec.setDescription(description);
                }
                if(doc.get(MetaData.KEYWORDS)!=null){
                    TextMetaData keywords = new TextMetaData();
                    keywords.setValue(doc.get(MetaData.KEYWORDS));
                    rec.setKeywords(keywords);
                }
                if(doc.get(MetaData.PRODUCTTYPE)!=null){
                    TextMetaData productType = new TextMetaData();
                    productType.setValue(doc.get(MetaData.PRODUCTTYPE));
                    rec.setProductType(productType);
                }
                if(doc.get(MetaData.SECURED)!=null){
                    BooleanMetaData secured = new BooleanMetaData();
                    secured.setValue(new Boolean(doc.get(MetaData.SECURED)));
                    rec.setSecured(secured);
                }
                if(doc.get(MetaData.FILEFORMAT)!=null){
                    TextMetaData fileFormat = new TextMetaData();
                    fileFormat.setValue(doc.get(MetaData.FILEFORMAT));
                    rec.setFileFormat(fileFormat);
                }
                if(doc.get(MetaData.URI)!=null){
                    HyperlinkMetaData uri = new HyperlinkMetaData();
                    uri.setValue(doc.get(MetaData.URI));
                    rec.setUri(uri);
                }
                if(doc.get(MetaData.AGGREGATIONLEVEL)!=null){
                    TextMetaData aggregationLevel = new TextMetaData();
                    aggregationLevel.setValue(doc.get(MetaData.AGGREGATIONLEVEL));
                    rec.setAggregationLevel(aggregationLevel);
                }
                if(doc.get(MetaData.DIDACTICSCENARIO)!=null){
                    TextMetaData didacticScenario = new TextMetaData();
                    didacticScenario.setValue(doc.get(MetaData.DIDACTICSCENARIO));
                    rec.setDidacticScenario(didacticScenario);
                }
                if(doc.get(MetaData.REQUIREDTIME)!=null){
                    NumericMetaData requiredTime = new NumericMetaData();
                    requiredTime.setValue(new Integer(doc.get(MetaData.REQUIREDTIME)));
                    rec.setRequiredTime(requiredTime);
                }
                if(doc.get(MetaData.RIGHTS)!=null){
                    TextMetaData rights = new TextMetaData();
                    rights.setValue(doc.get(MetaData.RIGHTS));
                    rec.setRights(rights);
                }
                if(doc.get(MetaData.FILESIZE)!=null){
                    NumericMetaData fileSize = new NumericMetaData();
                    fileSize.setValue(new Integer(doc.get(MetaData.FILESIZE)));
                    rec.setFileSize(fileSize);
                }
                if(doc.get(MetaData.PLAYINGTIME)!=null){
                    NumericMetaData playingTime = new NumericMetaData();
                    playingTime.setValue(new Integer(doc.get(MetaData.PLAYINGTIME)));
                    rec.setPlayingTime(playingTime);
                }
                if(doc.get(MetaData.TECHNICALREQUIREMENTS)!=null){
                    TextMetaData technicalRequirements = new TextMetaData();
                    technicalRequirements.setValue(doc.get(MetaData.TECHNICALREQUIREMENTS));
                    rec.setTechnicalRequirements(technicalRequirements);
                }
                if(doc.get(MetaData.CREATIONDATE)!=null){
                    DateMetaData creationDate = new DateMetaData();
                    creationDate.setValue(new Date((Integer.valueOf(doc.get(MetaData.CREATIONDATE)))));
                    rec.setCreationDate(creationDate);
                }
                if(doc.get(MetaData.LASTCHANGEDDATE)!=null){
                    DateMetaData lastChangedDate = new DateMetaData();
                    lastChangedDate.setValue(new Date((Integer.valueOf(doc.get(MetaData.LASTCHANGEDDATE)))));
                    rec.setLastChangedDate(lastChangedDate);
                }
                if(doc.get(MetaData.VERSION)!=null){
                    TextMetaData version = new TextMetaData();
                    version.setValue(doc.get(MetaData.VERSION));
                    rec.setVersion(version);
                }
                if(doc.get(MetaData.STATUS)!=null){
                    TextMetaData status = new TextMetaData();
                    status.setValue(doc.get(MetaData.STATUS));
                    rec.setStatus(status);
                }
                if(doc.get(MetaData.ROLENAME)!=null){
                    TextMetaData roleName = new TextMetaData();
                    roleName.setValue(doc.get(MetaData.ROLENAME));
                    rec.setRoleName(roleName);
                }
                
                String suri = doc.get(MetaData.URI);
                float score = hits.score(i);
                logger.info(i + 1 + ": " + suri + ":" + score);
                resultList.add(new Result<Record>(rec, hits.score(i)));

                
            } // end for

            searcher.close();
            logger.info("resultList contains " + resultList.size() +
                " elements");

            return resultList;
        } // end try
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        } // end catch

        return null;
    } // end doSearch()
    
   
    /**
     * Returns an alphabetical sorted array of distinct values of an index field
     * NOTE: the index field has to be one of the keyword fields (targetEndUser,
     * schoolType, schoolDiscipline, didacticFunction, productType, 
     * professionalSituation or competence).
     * 
     * @param fieldName the index field name
     * @return the sorted array
     */
    public String[] getDistinctValues(String fieldName){
        try{
            boolean valuable = false;
            for(int i=0; i<keywords.length; i++){
                if(fieldName.equals(keywords[i])){
                    valuable = true;
                }
            }
            if(!valuable){
                return null;
            }
            File f = app.getRelativeFile(INDEXPATH);
            IndexReader reader = IndexReader.open(f);
            TermEnum terms = reader.terms(new Term(fieldName, ""));
            Vector values = new Vector(); 
            while (fieldName.equals(terms.term().field())){
                values.add(terms.term().text());
                if(!terms.next()){
                    break;
                }
            }
            terms.close();
            reader.close();
            String[] distinctValues = new String[] {""};
            if(values.size()!=0){
                 distinctValues = new String[values.size()];
                 for(int i=0; i<values.size();i++){
                     distinctValues[i] = (String) values.elementAt(i);
                 }
            }
            Arrays.sort(distinctValues, String.CASE_INSENSITIVE_ORDER);
            return distinctValues;
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
        }
        return null;
    }
    
    /**
     * Returns an alphabetical sorted array of distinct path strings of a hierarchical index field
     * NOTE: the index field has to be one of the hierarchical keyword fields (targetEndUser,
     * schoolType, schoolDiscipline, or professionalSituation).
     * 
     * @param fieldName the index field name
     * @return the sorted array
     */
    public String[] getDistinctHierarchicalPaths(String fieldName){
        try{
            boolean valuable = false;
            for(int i=0; i<hierarchicalKeywords.length; i++){
                if(fieldName.equals(hierarchicalKeywords[i])){
                    valuable = true;
                }
            }
            if(!valuable){
                return null;
            }
            fieldName = fieldName + "_orig";
            File f = app.getRelativeFile(INDEXPATH);
            IndexReader reader = IndexReader.open(f);
            TermEnum terms = reader.terms(new Term(fieldName, ""));
            Vector values = new Vector(); 
            while (fieldName.equals(terms.term().field())){
                values.add(terms.term().text());
                if(!terms.next()){
                    break;
                }
            }
            terms.close();
            reader.close();
            String[] distinctValues = new String[] {""};
            if(values.size()!=0){
                 distinctValues = new String[values.size()];
                 for(int i=0; i<values.size();i++){
                     distinctValues[i] = (String) values.elementAt(i);
                 }
            }
            Arrays.sort(distinctValues, String.CASE_INSENSITIVE_ORDER);
            return distinctValues;
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
        }
        return null;
    }
    
} // end SearchServiceImpl
