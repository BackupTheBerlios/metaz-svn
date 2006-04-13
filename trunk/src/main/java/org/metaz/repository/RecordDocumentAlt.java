package org.metaz.repository;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import org.metaz.util.HierarchicalStructuredMetaDataValueParser;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a Lucene Document containing a MetaZ Record
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class RecordDocumentAlt {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The name of the Document field containing the merged text fields that  will be full-text searched */
  public final static String MERGED = "merged";
  private final static char KEYWORDSEPARATOR = ';';
  private final static String ORIG = "_orig";
  private final static String WHITESPACE = " ";

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Empty constructor
     */
  public RecordDocumentAlt() {

  } // end RecordDocument()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Makes a Lucene document for a MetaZ record.<p>The document contains the searchable metadata elements plus
   * the uri.</p>
   *
   * @param r the MetaZ record
   *
   * @return a Lucene document
   *
   * @throws Exception when the Lucene Document can not be instantiated
   */
  public static Document toDocument(Record r)
                             throws Exception
  {

    Document doc = new Document();
    String   merged = "";

    //full text searchable metadata
    doc.add(Field.Text(MetaData.TITLE, (String) r.getTitle().getValue()));
    merged = merged + r.getTitle().getValue().toString() + WHITESPACE;

    MetaData subject = r.getSubject();

    if (subject != null) {

      doc.add(Field.Text(MetaData.SUBJECT, (String) subject.getValue()));
      merged = merged + subject.getValue() + WHITESPACE;

    } // end if

    MetaData description = r.getDescription();

    if (description != null) {

      doc.add(Field.Text(MetaData.DESCRIPTION, (String) description.getValue()));
      merged = merged + description.getValue().toString() + WHITESPACE;

    } // end if

    MetaData keywords = r.getKeywords();

    if (keywords != null) {
      String kwords = ((String) keywords.getValue()).replace(KEYWORDSEPARATOR,' ');
      doc.add(Field.Text(MetaData.KEYWORDS, kwords));
      merged = merged + kwords;

    } // end if

    doc.add(Field.UnIndexed(MERGED, merged));

    //keyword searchable metadata
    MetaData targetEndUser = r.getTargetEndUser();

    if (targetEndUser != null) {
        Set targetEndUsers = (Set) targetEndUser.getValue();
        Iterator it = targetEndUsers.iterator();
        while (it.hasNext()){
            String tgeu = it.next().toString();
            doc.add(Field.Keyword(MetaData.TARGETENDUSER + ORIG, tgeu));
            String[] targetEndUserLevels = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(tgeu);
            for(int i=0; i<targetEndUserLevels.length; i++) {
                doc.add(Field.Keyword(MetaData.TARGETENDUSER, targetEndUserLevels[i]));
            } // end for
        } // end while

    } // end if

    MetaData schoolType = r.getSchoolType();

    if (schoolType != null) {

      Set      schoolTypes = (Set) schoolType.getValue();
      Iterator it = schoolTypes.iterator();

      while (it.hasNext()) {

        String schType = it.next().toString();

        doc.add(Field.Keyword(MetaData.SCHOOLTYPE + ORIG, schType));

        String[] schoolTypeLevels = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(schType);

        for (int j = 0; j < schoolTypeLevels.length; j++) {

          doc.add(Field.Keyword(MetaData.SCHOOLTYPE, schoolTypeLevels[j]));

        } // end for

      } // end while

    } // end if

    MetaData schoolDiscipline = r.getSchoolDiscipline();

    if (schoolDiscipline != null) {
        Set schoolDisciplines = (Set) schoolDiscipline.getValue();
        Iterator it = schoolDisciplines.iterator();
        while(it.hasNext()){
            String schDiscipline = it.next().toString();
            doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE + ORIG, schDiscipline));
            String[] schoolDisciplineLevels = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(schDiscipline);
            for(int i=0; i<schoolDisciplineLevels.length; i++){
                doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE, schoolDisciplineLevels[i]));
            }// end for
        } // end while

    } // end if

    String didacticFunction = (String) r.getDidacticFunction().getValue();

    doc.add(Field.Keyword(MetaData.DIDACTICFUNCTION, didacticFunction));

    String productType = (String) r.getProductType().getValue();

    doc.add(Field.Keyword(MetaData.PRODUCTTYPE, productType));

    MetaData professionalSituation = r.getProfessionalSituation();

    if (professionalSituation != null) {
        Set profSituations = (Set) professionalSituation.getValue();
        Iterator it = profSituations.iterator();
        while (it.hasNext()){
            String profSituation = it.next().toString();
            doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION + ORIG, profSituation));
            String[] professionalSituationLevels = HierarchicalStructuredMetaDataValueParser.getAllHierarchicalPaths(profSituation);
            for(int i=0; i<professionalSituationLevels.length; i++){
                doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION, professionalSituationLevels[i]));
            } // end for
        } // end while

    } // end if

    MetaData competence = r.getCompetence();

    if (competence != null) {

        String competences = ((String) competence.getValue()).replace(KEYWORDSEPARATOR,' ');
      doc.add(Field.Keyword(MetaData.COMPETENCE, competences));

    } // end if

    //stored metadata (not searchable)
    doc.add(Field.UnIndexed(MetaData.URI, (String) r.getURI().getValue()));
    doc.add(Field.UnIndexed(MetaData.FILEFORMAT, (String) r.getFileFormat().getValue()));
    doc.add(Field.UnIndexed(MetaData.SECURED, r.getSecured().getValue().toString()));

    MetaData aggregationLevel = r.getAggregationLevel();

    if (aggregationLevel != null) {

      doc.add(Field.UnIndexed(MetaData.AGGREGATIONLEVEL, (String) aggregationLevel.getValue()));

    }

    MetaData didacticScenario = r.getDidacticScenario();

    if (didacticScenario != null) {

      doc.add(Field.UnIndexed(MetaData.DIDACTICSCENARIO, (String) didacticScenario.getValue()));

    }

    MetaData requiredTime = r.getRequiredTime();

    if (requiredTime != null) {

      doc.add(Field.UnIndexed(MetaData.REQUIREDTIME, ((Long) requiredTime.getValue()).toString()));

    }

    MetaData rights = r.getRights();

    if (rights != null) {

      doc.add(Field.UnIndexed(MetaData.RIGHTS, (String) rights.getValue()));

    }

    MetaData fileSize = r.getFileSize();

    if (fileSize != null) {

      doc.add(Field.UnIndexed(MetaData.FILESIZE, ((Long) fileSize.getValue()).toString()));

    }

    MetaData playingTime = r.getPlayingTime();

    if (playingTime != null) {

      doc.add(Field.UnIndexed(MetaData.PLAYINGTIME, ((Long) playingTime.getValue()).toString()));

    }

    MetaData technicalRequirements = r.getTechnicalRequirements();

    if (technicalRequirements != null) {

      doc.add(Field.UnIndexed(MetaData.TECHNICALREQUIREMENTS, (String) technicalRequirements.getValue()));

    }

    MetaData creationDate = r.getCreationDate();

    if (creationDate != null) {

      doc.add(Field.UnIndexed(MetaData.CREATIONDATE,
                              (Long.toString(((Date) creationDate.getValue()).getTime()))));

    }

    MetaData lastChangedDate = r.getLastChangedDate();

    if (lastChangedDate != null) {

      doc.add(Field.UnIndexed(MetaData.LASTCHANGEDDATE,
                              (Long.toString(((Date) lastChangedDate.getValue()).getTime()))));

    }

    MetaData version = r.getVersion();

    if (version != null) {

      doc.add(Field.UnIndexed(MetaData.VERSION, (String) version.getValue()));

    }

    MetaData status = r.getStatus();

    if (status != null) {

      doc.add(Field.UnIndexed(MetaData.STATUS, (String) status.getValue()));

    }

    MetaData roleName = r.getRoleName();

    if (roleName != null) {

      doc.add(Field.UnIndexed(MetaData.ROLENAME, (String) roleName.getValue()));

    }

    return doc;

  } // end toDocument()

} // end RecordDocument
