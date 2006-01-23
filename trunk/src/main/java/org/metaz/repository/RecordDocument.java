package org.metaz.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Date;

import java.util.Set;

import org.apache.lucene.document.DateField;
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

    public final static String MERGED = "merged";
    private final static String LEVELSEPARATOR = "/";
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
     * <p>A document may contain some optional metadata as well 
     * @param r the MetaZ record
     * @param light flag indicating light document (no stored fields except for uri)
     * @return a Lucene document
     */
    public static Document toDocument(Record r, boolean light) throws Exception{
        Document doc = new Document();
        String merged = "";
        
    //full text searchable metadata
        doc.add(Field.Text(MetaData.TITLE,(String)r.getTitle().getValue()));
        merged = merged + (String)r.getTitle().getValue();
        MetaData subject = r.getSubject();
        if(subject!=null) {
            doc.add(Field.Text(MetaData.SUBJECT,(String)subject.getValue()));
        }
        MetaData description = r.getDescription();
        if(description!=null) {
            doc.add(Field.Text(MetaData.DESCRIPTION,(String)description.getValue()));
            merged = merged+(String)description.getValue();
        }
        MetaData keywords = r.getKeywords();
        if(keywords!=null) {
            doc.add(Field.Text(MetaData.KEYWORDS,(String)keywords.getValue()));
            merged = merged+(String)keywords.getValue();
        }
        doc.add(Field.UnStored(MERGED,merged));
        
    //keyword searchable metadata
        MetaData targetEndUser = r.getTargetEndUser();
        if(targetEndUser!=null){
            String[] targetEndUserLevels = targetEndUser.toString().split(LEVELSEPARATOR);
            for(int i=0; i<targetEndUserLevels.length; i++) {
                doc.add(Field.Keyword(MetaData.TARGETENDUSER,targetEndUserLevels[i]));
            }
        }
        MetaData schoolType = r.getSchoolType();
        if(schoolType!=null) {
            Set schoolTypes = (Set)schoolType.getValue();
            Iterator it = schoolTypes.iterator();
            while(it.hasNext()){
                String schType = it.next().toString();
                String[] schoolTypeLevels = schType.split(LEVELSEPARATOR);
                for(int j=1; j<schoolTypeLevels.length; j++) {
                    doc.add(Field.Keyword(MetaData.SCHOOLTYPE,schoolTypeLevels[j]));
                }
            }
        }
        MetaData schoolDiscipline = r.getSchoolDiscipline();
        if(schoolDiscipline!=null) {
            String[] schoolDisciplineLevels = schoolDiscipline.toString().split(LEVELSEPARATOR);
            for(int i=0; i<schoolDisciplineLevels.length; i++) {
                doc.add(Field.Keyword(MetaData.SCHOOLDISCIPLINE,schoolDisciplineLevels[i]));
            }
        }
        String didacticFunction = (String)r.getDidacticFunction().getValue();
        doc.add(Field.Keyword(MetaData.DIDACTICFUNCTION,didacticFunction));
        String productType = (String)r.getProductType().getValue();
        doc.add(Field.Keyword(MetaData.PRODUCTTYPE,productType));
        MetaData professionalSituation = r.getProfessionalSituation();
        if(professionalSituation!=null) {
            String[] professionalSituationLevels = professionalSituation.toString().split(LEVELSEPARATOR);
            for(int i=0; i<professionalSituationLevels.length; i++) {
                doc.add(Field.Keyword(MetaData.PROFESSIONALSITUATION,professionalSituationLevels[i]));
            }
        }
        MetaData competence = r.getCompetence();
        if(competence!=null) {
            doc.add(Field.Keyword(MetaData.COMPETENCE,(String)competence.getValue()));
        }
        
    //stored metadata (not searchable)
        doc.add(Field.UnIndexed(MetaData.URI,(String)r.getURI().getValue()));
        if(!light){
            doc.add(Field.UnIndexed(MetaData.SECURED,r.getSecured().getValue().toString()));
            doc.add(Field.UnIndexed(MetaData.FILEFORMAT,(String)r.getFileFormat().getValue()));
            MetaData aggregationLevel = r.getAggregationLevel();
            if(aggregationLevel!=null){
                doc.add(Field.UnIndexed(MetaData.AGGREGATIONLEVEL,(String)aggregationLevel.getValue()));
            }
            MetaData didacticScenario = r.getDidacticScenario();
            if(didacticScenario!=null){
                doc.add(Field.UnIndexed(MetaData.DIDACTICSCENARIO,(String)didacticScenario.getValue()));
            }
            MetaData requiredTime = r.getRequiredTime();
            if(requiredTime!=null){
                doc.add(Field.UnIndexed(MetaData.REQUIREDTIME,requiredTime.getValue().toString()));
            }
            MetaData rights = r.getRights();
            if(rights!=null){
                doc.add(Field.UnIndexed(MetaData.RIGHTS,(String)rights.getValue()));
            }
            MetaData fileSize = r.getFileSize();
            if(fileSize!=null){
                doc.add(Field.UnIndexed(MetaData.FILESIZE,fileSize.getValue().toString()));
            }
            MetaData playingTime = r.getPlayingTime();
            if(playingTime!=null){
                doc.add(Field.UnIndexed(MetaData.PLAYINGTIME,playingTime.getValue().toString()));
            }
            MetaData technicalRequirements = r.getTechnicalRequirements();
            if(technicalRequirements!=null){
                doc.add(Field.UnIndexed(MetaData.TECHNICALREQUIREMENTS,(String)technicalRequirements.getValue()));
            }
            MetaData creationDate = r.getCreationDate();
            if(creationDate!=null){
                doc.add(Field.UnIndexed(MetaData.CREATIONDATE,DateField.dateToString((Date)creationDate.getValue())));
            }
            MetaData lastChangedDate = r.getLastChangedDate();
            if(lastChangedDate!=null){
                doc.add(Field.UnIndexed(MetaData.LASTCHANGEDDATE,DateField.dateToString((Date)lastChangedDate.getValue())));
            }
            MetaData version = r.getVersion();
            if(version!=null){
                doc.add(Field.UnIndexed(MetaData.VERSION,(String)version.getValue()));
            }
            MetaData status = r.getStatus();
            if(status!=null){
                doc.add(Field.UnIndexed(MetaData.STATUS,(String)version.getValue()));
            }
            MetaData roleName = r.getRoleName();
            if(roleName!=null){
                doc.add(Field.UnIndexed(MetaData.ROLENAME,(String)roleName.getValue()));
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
