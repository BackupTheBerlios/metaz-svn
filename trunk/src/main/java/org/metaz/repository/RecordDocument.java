package org.metaz.repository;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import java.util.Iterator;
import java.util.Set;


/**
 * Represents a Lucene Document containing a MetaZ Record
 *
 * @author Sammy Dalewyn
 * @version 1.0
  */
public class RecordDocument
{
    /**
     * The name of the Document field containing the merged text fields that 
     * will be full-text searched
     */
    public final static String MERGED = "merged";
    private final static String LEVELSEPARATOR = "/";

/**
     * Empty constructor
     */
    public RecordDocument()
    {
    } // end RecordDocument()

    /**
     * Makes a Lucene document for a MetaZ record.<p>The document
     * contains the searchable metadata elements plus the uri.</p>
     *
     * @param r the MetaZ record
     *
     * @return a Lucene document
     *
     * @throws Exception when the Lucene Document can not be instantiated
     */
    public static Document toDocument(Record r) throws Exception
    {
        Document doc = new Document();
        String merged = "";

        //full text searchable metadata
        doc.add(Field.Text(MetaData.TITLE, (String) r.getTitle().getValue()));
        merged = merged + (String) r.getTitle().getValue();

        MetaData subject = r.getSubject();

        if (subject != null)
        {
            doc.add(Field.Text(MetaData.SUBJECT, (String) subject.getValue()));
        } // end if

        MetaData description = r.getDescription();

        if (description != null)
        {
            doc.add(Field.Text(MetaData.DESCRIPTION,
                    (String) description.getValue()));
            merged = merged + (String) description.getValue();
        } // end if

        MetaData keywords = r.getKeywords();

        if (keywords != null)
        {
            doc.add(Field.Text(MetaData.KEYWORDS, (String) keywords.getValue()));
            merged = merged + (String) keywords.getValue();
        } // end if

        doc.add(Field.UnStored(MERGED, merged));

        //keyword searchable metadata
        MetaData targetEndUser = r.getTargetEndUser();

        if (targetEndUser != null)
        {
            String[] targetEndUserLevels = targetEndUser.toString()
                                                        .split(LEVELSEPARATOR);

            for (int i = 0; i < targetEndUserLevels.length; i++)
            {
                doc.add(Field.Keyword(MetaData.TARGETENDUSER,
                        targetEndUserLevels[i]));
            } // end for
        } // end if

        MetaData schoolType = r.getSchoolType();

        if (schoolType != null)
        {
            Set schoolTypes = (Set) schoolType.getValue();
            Iterator it = schoolTypes.iterator();

            while (it.hasNext())
            {
                String schType = it.next().toString();
                String[] schoolTypeLevels = schType.split(LEVELSEPARATOR);

                for (int j = 1; j < schoolTypeLevels.length; j++)
                {
                    doc.add(Field.Keyword(MetaData.SCHOOLTYPE,
                            schoolTypeLevels[j]));
                } // end for
            } // end while
        } // end if

        MetaData schoolDiscipline = r.getSchoolDiscipline();

        if (schoolDiscipline != null)
        {
            String[] schoolDisciplineLevels = schoolDiscipline.toString()
                                                              .split(LEVELSEPARATOR);

            for (int i = 0; i < schoolDisciplineLevels.length; i++)
            {
                doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE,
                        schoolDisciplineLevels[i]));
            } // end for
        } // end if

        String didacticFunction = (String) r.getDidacticFunction().getValue();
        doc.add(Field.Keyword(MetaData.DIDACTICFUNCTION, didacticFunction));

        String productType = (String) r.getProductType().getValue();
        doc.add(Field.Keyword(MetaData.PRODUCTTYPE, productType));

        MetaData professionalSituation = r.getProfessionalSituation();

        if (professionalSituation != null)
        {
            String[] professionalSituationLevels = professionalSituation.toString()
                                                                        .split(LEVELSEPARATOR);

            for (int i = 0; i < professionalSituationLevels.length; i++)
            {
                doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION,
                        professionalSituationLevels[i]));
            } // end for
        } // end if

        MetaData competence = r.getCompetence();

        if (competence != null)
        {
            doc.add(Field.Keyword(MetaData.COMPETENCE,
                    (String) competence.getValue()));
        } // end if

        //stored metadata (not searchable)
        doc.add(Field.UnIndexed(MetaData.URI, (String) r.getURI().getValue()));

        return doc;
    } // end toDocument()
} // end RecordDocument
