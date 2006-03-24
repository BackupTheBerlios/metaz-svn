package org.metaz.repository;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

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
  private final static String LEVELSEPARATOR = "/";

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
    merged = merged + (String) r.getTitle().getValue();

    MetaData subject = r.getSubject();

    if (subject != null) {

      doc.add(Field.Text(MetaData.SUBJECT, (String) subject.getValue()));

    } // end if

    MetaData description = r.getDescription();

    if (description != null) {

      doc.add(Field.Text(MetaData.DESCRIPTION, (String) description.getValue()));
      merged = merged + (String) description.getValue();

    } // end if

    MetaData keywords = r.getKeywords();

    if (keywords != null) {

      doc.add(Field.Text(MetaData.KEYWORDS, (String) keywords.getValue()));
      merged = merged + (String) keywords.getValue();

    } // end if

    doc.add(Field.UnStored(MERGED, merged));

    //keyword searchable metadata
    MetaData targetEndUser = r.getTargetEndUser();

    if (targetEndUser != null) {

      String targetEndUserString = targetEndUser.toString();

      doc.add(Field.Keyword(MetaData.TARGETENDUSER + "_orig", targetEndUserString));

      String[] targetEndUserLevels = targetEndUser.toString().split(LEVELSEPARATOR);

      for (int i = 1; i < targetEndUserLevels.length; i++) {

        doc.add(Field.Keyword(MetaData.TARGETENDUSER, targetEndUserLevels[i]));

      } // end for

    } // end if

    MetaData schoolType = r.getSchoolType();

    if (schoolType != null) {

      Set      schoolTypes = (Set) schoolType.getValue();
      Iterator it = schoolTypes.iterator();

      while (it.hasNext()) {

        String schType = it.next().toString();

        doc.add(Field.Keyword(MetaData.SCHOOLTYPE + "_orig", schType));

        String[] schoolTypeLevels = schType.split(LEVELSEPARATOR);

        for (int j = 1; j < schoolTypeLevels.length; j++) {

          doc.add(Field.Keyword(MetaData.SCHOOLTYPE, schoolTypeLevels[j]));

        } // end for

      } // end while

    } // end if

    MetaData schoolDiscipline = r.getSchoolDiscipline();

    if (schoolDiscipline != null) {

      String schoolDisciplineString = schoolDiscipline.toString();

      doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE + "_orig", schoolDisciplineString));

      String[] schoolDisciplineLevels = schoolDiscipline.toString().split(LEVELSEPARATOR);

      for (int i = 1; i < schoolDisciplineLevels.length; i++) {

        doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE, schoolDisciplineLevels[i]));

      } // end for

    } // end if

    String didacticFunction = (String) r.getDidacticFunction().getValue();

    doc.add(Field.Keyword(MetaData.DIDACTICFUNCTION, didacticFunction));

    String productType = (String) r.getProductType().getValue();

    doc.add(Field.Keyword(MetaData.PRODUCTTYPE, productType));

    MetaData professionalSituation = r.getProfessionalSituation();

    if (professionalSituation != null) {

      String professionalSituationString = professionalSituation.toString();

      doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION + "_orig", professionalSituationString));

      String[] professionalSituationLevels = professionalSituation.toString().split(LEVELSEPARATOR);

      for (int i = 1; i < professionalSituationLevels.length; i++) {

        doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION, professionalSituationLevels[i]));

      } // end for

    } // end if

    MetaData competence = r.getCompetence();

    if (competence != null) {

      doc.add(Field.Keyword(MetaData.COMPETENCE, (String) competence.getValue()));

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
                              (Integer.toString((int) ((Date) creationDate.getValue()).getTime()))));

    }

    MetaData lastChangedDate = r.getLastChangedDate();

    if (lastChangedDate != null) {

      doc.add(Field.UnIndexed(MetaData.LASTCHANGEDDATE,
                              (Integer.toString((int) ((Date) lastChangedDate.getValue()).getTime()))));

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
