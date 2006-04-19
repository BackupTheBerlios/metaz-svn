package org.metaz.repository;

import org.apache.log4j.Logger;

import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;
import org.metaz.domain.HtmlTextMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.MetaData;
import org.metaz.domain.NumericMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;

import org.metaz.util.MetaZ;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Implementation of the Lucene Search Service.
 */
public class SearchServiceImplAlt
  implements SearchServiceAlt
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger       logger = MetaZ.getLogger(SearchServiceImpl.class);
  private static final String INDEXPATH = "repository/searchservice/altsearchindex";
  private static final String TERMDELIMITER = ":";
  private static final String WHITESPACE = " ";
  private static final String VALUESEPARATOR = "%";
  private static final char   DOUBLEQUOTE = '\"';
  private static final String EMPTYSTRING = "";
  private static final String ORIG = "_orig";
  private static final String LEVELSEPARATOR = "/";
  private static final String STEMDICT = "repository/searchservice/wordlists/wordstem.txt";
  private static final String STOPWORDS = "repository/searchservice/wordlists/stopwords.txt";
  private static final String TERMREQUIREDPROP = "applicationz.search.term.required";
  private static final String TERMPROHIBITEDPROP = "applicationz.search.term.prohibited";
  private static final String ALLTERMSREQUIRED = "AllTermsRequired";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String[]      keywords = new String[] {
                                     MetaData.TARGETENDUSER, MetaData.SCHOOLTYPE, MetaData.SCHOOLDISCIPLINE,
                                     MetaData.DIDACTICFUNCTION, MetaData.PRODUCTTYPE, MetaData.PROFESSIONALSITUATION,
                                     MetaData.COMPETENCE
                                   };
  private MetaZ         app = MetaZ.getInstance();
  private DutchAnalyzer analyzer;
  private boolean       required;
  private boolean       prohibited;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Constructor
     */
  public SearchServiceImplAlt() {

    File stopwordsFile = app.getRelativeFile(STOPWORDS);

    analyzer = new DutchAnalyzer(stopwordsFile);

    File stemdictFile = app.getRelativeFile(STEMDICT);

    analyzer.setStemDictionary(stemdictFile);

    Properties props = app.getProperties();

    required = Boolean.valueOf(props.getProperty(TERMREQUIREDPROP, "false"));

    prohibited = Boolean.valueOf(props.getProperty(TERMPROHIBITEDPROP, "false"));

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

    } catch (IOException ex) {

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

          writer.addDocument(RecordDocumentAlt.toDocument(records.get(i)));

        } // end for

      } // end if

      logger.info("" + writer.docCount() + " records added to Search Index");
      writer.optimize();
      writer.close();

      //for testing purposes
      /*
         IndexReader reader = IndexReader.open(f);
         for (int i = 0; i < reader.numDocs(); i++) {
           logger.debug((reader.document(i)).toString());
         }
         reader.close();*/
    } catch (Exception ex) {

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
  public List<Result<Record>> doSearch(String query) {

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

          } else {

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

    } catch (Exception ex) {

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
  public List<Result<Record>> doSearch(HashMap hmquery) {

    try {

      File     f = app.getRelativeFile(INDEXPATH);
      Searcher searcher = new IndexSearcher(f.getCanonicalPath());

      String allTermsRequired = (String) hmquery.get(ALLTERMSREQUIRED);

      if (allTermsRequired != null) {

        required = Boolean.valueOf(allTermsRequired);

      }

      BooleanQuery q = new BooleanQuery();
      String       fullText = (String) hmquery.get(EMPTYSTRING);

      if (fullText != null) {

        Query fullTextQuery = QueryParser.parse(fullText, RecordDocument.MERGED, analyzer);

        q.add(fullTextQuery, required, prohibited); // set with metaz.props

      } // end if

      for (int i = 0; i < keywords.length; i++) {

        String keywordValue = (String) hmquery.get(keywords[i]);

        if (keywordValue != null) {

          String[] terms = keywordValue.split(VALUESEPARATOR);

          for (int j = 0; j < terms.length; j++) {

            Term  keyword = new Term(keywords[i], terms[j]);
            Query keywordQuery = new TermQuery(keyword);

            q.add(keywordQuery, required, prohibited); // set with metaz.props

          }

        } // end if

      } // end for

      logger.info("Searching for: " + q.toString());

      Hits hits = searcher.search(q);

      logger.info(hits.length() + " total matching records");

      List<Result<Record>> resultList = new Vector<Result<Record>>();

      for (int i = 0; i < hits.length(); i++) {

        Document doc = hits.doc(i);

        Record   rec = createRecord(doc);

        String suri = doc.get(MetaData.URI);
        float  score = hits.score(i);

        logger.debug(i + 1 + ": " + suri + ":" + score);
        resultList.add(new Result<Record>(rec, hits.score(i)));

      } // end for

      searcher.close();
      logger.info("resultList contains " + resultList.size() + " elements");

      return resultList;

    } catch (Exception ex) {

      logger.error(ex.getMessage());

    } // end catch

    return null;

  } // end doSearch()

  /**
   * Returns an alphabetical sorted array of distinct values of an index field NOTE: the index field has to be
   * one of the keyword fields (targetEndUser, schoolType, schoolDiscipline, didacticFunction, productType,
   * professionalSituation or competence).
   *
   * @param fieldName the index field name
   *
   * @return the sorted array
   */
  public String[] getDistinctValues(String fieldName) {

    try {

      boolean valuable = false;

      for (int i = 0; i < keywords.length; i++) {

        if (fieldName.equals(keywords[i])) {

          valuable = true;

        }

      }

      if (! valuable) {

        return null;

      }

      File        f = app.getRelativeFile(INDEXPATH);
      IndexReader reader = IndexReader.open(f);
      TermEnum    terms = reader.terms(new Term(fieldName, EMPTYSTRING));
      String[]    distinctValues = new String[0];

      if (terms.term() != null) {

        Vector values = new Vector();

        while (fieldName.equals(terms.term().field())) {

          values.add(terms.term().text());

          if (! terms.next()) {

            break;

          }

        }

        if (values.size() != 0) {

          distinctValues = new String[values.size()];

          for (int i = 0; i < values.size(); i++) {

            distinctValues[i] = (String) values.elementAt(i);

          }

        }

        Arrays.sort(distinctValues, String.CASE_INSENSITIVE_ORDER);

      }

      terms.close();
      reader.close();

      return distinctValues;

    } catch (Exception ex) {

      logger.error(ex.getMessage());

    }

    return null;

  }

  /**
   * Returns an alphabetical sorted array of distinct values of the field SchoolDiscipline that are related to
   * the field SchoolType with the provided parameter value
   *
   * @param schooltype the SchoolType value
   *
   * @return the sorted array
   */
  public String[] getDistinctRelatedSchoolDisciplineValues(String schooltype) {

    try {

      String[] values = new String[0];
      File     f = app.getRelativeFile(INDEXPATH);
      Searcher searcher = new IndexSearcher(f.getCanonicalPath());

      Term      term = new Term(MetaData.SCHOOLTYPE, schooltype);
      TermQuery query = new TermQuery(term);
      Hits      hits = searcher.search(query);

      if (hits.length() != 0) {

        TreeSet ts = new TreeSet();

        for (int i = 0; i < hits.length(); i++) {

          String[] origValues = hits.doc(i).getValues(MetaData.SCHOOLDISCIPLINE);

          if (origValues != null) {

            for (int j = 0; j < origValues.length; j++) {

              ts.add(origValues[j]);

            }

          }

        }

        values = (String[]) ts.toArray(values);
        Arrays.sort(values, String.CASE_INSENSITIVE_ORDER);

      }

      searcher.close();

      return values;

    } catch (Exception ex) {

      logger.error(ex.getMessage());

    }

    return null;

  }

  /**
   * Creates a record based on the values of the Lucene document
   *
   * @param doc the Lucene document
   *
   * @return a MetaZ record
   */
  private Record createRecord(Document doc) {

    Record rec = new Record();

    rec = addRequiredFields(doc, rec);
    rec = addOptionalFields(doc, rec);
    rec = addHierarchicalFields(doc, rec);

    return rec;

  }

  /**
   * Adds the required field to the record.
   *
   * @param doc the Lucene document
   * @param rec the record
   *
   * @return the record
   */
  private Record addRequiredFields(Document doc, Record rec) {

    if (doc.get(MetaData.TITLE) != null) {

      TextMetaData title = new TextMetaData();

      title.setValue(doc.get(MetaData.TITLE));
      rec.setTitle(title);

    }

    if (doc.get(MetaData.PRODUCTTYPE) != null) {

      TextMetaData productType = new TextMetaData();

      productType.setValue(doc.get(MetaData.PRODUCTTYPE));
      rec.setProductType(productType);

    }

    if (doc.get(MetaData.SECURED) != null) {

      BooleanMetaData secured = new BooleanMetaData();

      secured.setValue(new Boolean(doc.get(MetaData.SECURED)));
      rec.setSecured(secured);

    }

    if (doc.get(MetaData.FILEFORMAT) != null) {

      TextMetaData fileFormat = new TextMetaData();

      fileFormat.setValue(doc.get(MetaData.FILEFORMAT));
      rec.setFileFormat(fileFormat);

    }

    if (doc.get(MetaData.URI) != null) {

      HyperlinkMetaData uri = new HyperlinkMetaData();

      uri.setValue(doc.get(MetaData.URI));
      rec.setUri(uri);

    }

    if (doc.get(MetaData.DIDACTICFUNCTION) != null) {

      TextMetaData didacticFunction = new TextMetaData();

      didacticFunction.setValue(doc.get(MetaData.DIDACTICFUNCTION));
      rec.setDidacticFunction(didacticFunction);

    }

    return rec;

  }

  /**
   * Adds the optional fields to the record
   *
   * @param doc the Lucene document
   * @param rec the record
   *
   * @return the record
   */
  private Record addOptionalFields(Document doc, Record rec) {

    if (doc.get(MetaData.DESCRIPTION) != null) {

      HtmlTextMetaData description = new HtmlTextMetaData();

      description.setValue(doc.get(MetaData.DESCRIPTION));
      rec.setDescription(description);

    }

    if (doc.get(MetaData.KEYWORDS) != null) {

      TextMetaData keywordset = new TextMetaData();

      keywordset.setValue(doc.get(MetaData.KEYWORDS));
      rec.setKeywords(keywordset);

    }

    if (doc.get(MetaData.AGGREGATIONLEVEL) != null) {

      TextMetaData aggregationLevel = new TextMetaData();

      aggregationLevel.setValue(doc.get(MetaData.AGGREGATIONLEVEL));
      rec.setAggregationLevel(aggregationLevel);

    }

    if (doc.get(MetaData.DIDACTICSCENARIO) != null) {

      TextMetaData didacticScenario = new TextMetaData();

      didacticScenario.setValue(doc.get(MetaData.DIDACTICSCENARIO));
      rec.setDidacticScenario(didacticScenario);

    }

    if (doc.get(MetaData.REQUIREDTIME) != null) {

      NumericMetaData requiredTime = new NumericMetaData();

      requiredTime.setValue(new Long(doc.get(MetaData.REQUIREDTIME)));
      rec.setRequiredTime(requiredTime);

    }

    if (doc.get(MetaData.RIGHTS) != null) {

      TextMetaData rights = new TextMetaData();

      rights.setValue(doc.get(MetaData.RIGHTS));
      rec.setRights(rights);

    }

    if (doc.get(MetaData.FILESIZE) != null) {

      NumericMetaData fileSize = new NumericMetaData();

      fileSize.setValue(new Long(doc.get(MetaData.FILESIZE)));
      rec.setFileSize(fileSize);

    }

    if (doc.get(MetaData.PLAYINGTIME) != null) {

      NumericMetaData playingTime = new NumericMetaData();

      playingTime.setValue(new Long(doc.get(MetaData.PLAYINGTIME)));
      rec.setPlayingTime(playingTime);

    }

    if (doc.get(MetaData.TECHNICALREQUIREMENTS) != null) {

      TextMetaData technicalRequirements = new TextMetaData();

      technicalRequirements.setValue(doc.get(MetaData.TECHNICALREQUIREMENTS));
      rec.setTechnicalRequirements(technicalRequirements);

    }

    if (doc.get(MetaData.CREATIONDATE) != null) {

      DateMetaData creationDate = new DateMetaData();

      creationDate.setValue(new Date((Long.valueOf(doc.get(MetaData.CREATIONDATE)))));
      rec.setCreationDate(creationDate);

    }

    if (doc.get(MetaData.LASTCHANGEDDATE) != null) {

      DateMetaData lastChangedDate = new DateMetaData();

      lastChangedDate.setValue(new Date((Long.valueOf(doc.get(MetaData.LASTCHANGEDDATE)))));
      rec.setLastChangedDate(lastChangedDate);

    }

    if (doc.get(MetaData.VERSION) != null) {

      TextMetaData version = new TextMetaData();

      version.setValue(doc.get(MetaData.VERSION));
      rec.setVersion(version);

    }

    if (doc.get(MetaData.STATUS) != null) {

      TextMetaData status = new TextMetaData();

      status.setValue(doc.get(MetaData.STATUS));
      rec.setStatus(status);

    }

    if (doc.get(MetaData.ROLENAME) != null) {

      TextMetaData roleName = new TextMetaData();

      roleName.setValue(doc.get(MetaData.ROLENAME));
      rec.setRoleName(roleName);

    }

    if (doc.get(MetaData.SUBJECT) != null) {

      TextMetaData subject = new TextMetaData();

      subject.setValue(doc.get(MetaData.SUBJECT));
      rec.setSubject(subject);

    }

    if (doc.get(MetaData.COMPETENCE) != null) {

      TextMetaData competence = new TextMetaData();

      competence.setValue(doc.get(MetaData.COMPETENCE));
      rec.setCompetence(competence);

    }

    return rec;

  }

  /**
   * Adds the hierarchical fields to the record
   *
   * @param doc the Lucene document
   * @param rec the record
   *
   * @return the record
   */
  private Record addHierarchicalFields(Document doc, Record rec) {

    if (doc.get(MetaData.SCHOOLTYPE) != null) {

      HierarchicalStructuredTextMetaDataSet schoolType = new HierarchicalStructuredTextMetaDataSet();
      String[]                              schoolTypeLevelSet = doc.getValues(MetaData.SCHOOLTYPE + ORIG);

      for (int j = 0; j < schoolTypeLevelSet.length; j++) {

        HierarchicalStructuredTextMetaData schoolTypeSet = new HierarchicalStructuredTextMetaData();
        String[]                           schoolTypeLevels = schoolTypeLevelSet[j].split(LEVELSEPARATOR);

        for (int k = 1; k < schoolTypeLevels.length; k++) {

          TextMetaData level = new TextMetaData();

          level.setValue(schoolTypeLevels[k]);
          schoolTypeSet.addChild(level);

        }

        schoolType.addHierarchy(schoolTypeSet);

      }

      rec.setSchoolType(schoolType);

    }

    if (doc.get(MetaData.SCHOOLDISCIPLINE) != null) {

      HierarchicalStructuredTextMetaDataSet schoolDiscipline = new HierarchicalStructuredTextMetaDataSet();
      String[]                              schoolDisciplineLevelSet = doc.getValues(MetaData.SCHOOLDISCIPLINE + ORIG);

      for (int j = 0; j < schoolDisciplineLevelSet.length; j++) {

        HierarchicalStructuredTextMetaData schoolDisciplineSet = new HierarchicalStructuredTextMetaData();
        String[]                           schoolDisciplineLevels = schoolDisciplineLevelSet[j].split(LEVELSEPARATOR);

        for (int k = 1; k < schoolDisciplineLevels.length; k++) {

          TextMetaData level = new TextMetaData();

          level.setValue(schoolDisciplineLevels[k]);
          schoolDisciplineSet.addChild(level);

        }

        schoolDiscipline.addHierarchy(schoolDisciplineSet);

      }

      rec.setSchoolDiscipline(schoolDiscipline);

    }

    if (doc.get(MetaData.PROFESSIONALSITUATION) != null) {

      HierarchicalStructuredTextMetaDataSet professionalSituation = new HierarchicalStructuredTextMetaDataSet();
      String[] professionalSituationLevelSet = doc.getValues(MetaData.PROFESSIONALSITUATION + ORIG);

      for (int j = 0; j < professionalSituationLevelSet.length; j++) {

        HierarchicalStructuredTextMetaData professionalSituationSet = new HierarchicalStructuredTextMetaData();
        String[] professionalSituationLevels = professionalSituationLevelSet[j].split(LEVELSEPARATOR);

        for (int k = 1; k < professionalSituationLevels.length; k++) {

          TextMetaData level = new TextMetaData();

          level.setValue(professionalSituationLevels[k]);
          professionalSituationSet.addChild(level);

        }

        professionalSituation.addHierarchy(professionalSituationSet);

      }

      rec.setProfessionalSituation(professionalSituation);

    }

    if (doc.get(MetaData.TARGETENDUSER) != null) {

      HierarchicalStructuredTextMetaDataSet targetEndUser = new HierarchicalStructuredTextMetaDataSet();
      String[]                              targetEndUserLevelSet = doc.getValues(MetaData.TARGETENDUSER + ORIG);

      for (int j = 0; j < targetEndUserLevelSet.length; j++) {

        HierarchicalStructuredTextMetaData targetEndUserSet = new HierarchicalStructuredTextMetaData();
        String[]                           targetEndUserLevels = targetEndUserLevelSet[j].split(LEVELSEPARATOR);

        for (int k = 1; k < targetEndUserLevels.length; k++) {

          TextMetaData level = new TextMetaData();

          level.setValue(targetEndUserLevels[k]);
          targetEndUserSet.addChild(level);

        }

        targetEndUser.addHierarchy(targetEndUserSet);

      }

      rec.setTargetEndUser(targetEndUser);

    }

    return rec;

  }

}
// end SearchServiceImpl
