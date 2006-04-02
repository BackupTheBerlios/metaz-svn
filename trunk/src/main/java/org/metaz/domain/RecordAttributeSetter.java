package org.metaz.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The class RecordAttributeSetter is used to collect attributes around record metadata attributes so  it can be
 * used to iterate through the collection of record attributes and retrieve metadata on the attributes
 *
 * @author Lars Oosterloo
 * @version 1.0
 */
public class RecordAttributeSetter {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private TextMetaData                          title;
  private TextMetaData                          subject;
  private HtmlTextMetaData                      description;
  private TextMetaData                          keywords;
  private HierarchicalStructuredTextMetaData    targetEndUser;
  private HierarchicalStructuredTextMetaDataSet schoolType;
  private HierarchicalStructuredTextMetaData    schoolDiscipline;
  private TextMetaData                          didacticFunction;
  private TextMetaData                          productType;
  private HierarchicalStructuredTextMetaData    professionalSituation;
  private TextMetaData                          competence;
  private BooleanMetaData                       secured;
  private TextMetaData                          fileFormat;
  private HyperlinkMetaData                     uri;
  private TextMetaData                          aggregationLevel;
  private TextMetaData                          didacticScenario;
  private NumericMetaData                       requiredTime;
  private TextMetaData                          rights;
  private NumericMetaData                       fileSize;
  private NumericMetaData                       playingTime;
  private TextMetaData                          technicalRequirements;
  private DateMetaData                          creationDate;
  private DateMetaData                          lastChangedDate;
  private TextMetaData                          version;
  private TextMetaData                          status;
  private TextMetaData                          roleName;
  private List<MetaData>                        recordMetadata = new Vector<MetaData>();
  private Record                                record;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
  * Creates a new RecordAttributeSetter. This constructor ensures a record exists that can
  * receive data.
  *
  * @param record that receives data
  */
  public RecordAttributeSetter(Record record) {

    this.record = record;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns an iterator containing all metadata fields of the record object
   *
   * @return the record iterator
   */
  public Iterator iterator() {

    if (recordMetadata.isEmpty()) {

      title = new TextMetaData();
      title.setMandatory(true);
      title.setXMLTagName(MetaData.TITLE);
      title.setName("title");
      title.setMetaDataType("TextMetaData");
      recordMetadata.add(title);
      subject = new TextMetaData();
      subject.setXMLTagName(MetaData.SUBJECT);
      subject.setName("subject");
      subject.setMetaDataType("TextMetaData");
      subject.setOptional(true);
      recordMetadata.add(subject);
      description = new HtmlTextMetaData();
      description.setXMLTagName(MetaData.DESCRIPTION);
      description.setName("description");
      description.setMetaDataType("HtmlTextMetaData");
      description.setOptional(true);
      recordMetadata.add(description);
      keywords = new TextMetaData();
      keywords.setXMLTagName(MetaData.KEYWORDS);
      keywords.setName("keywords");
      keywords.setMetaDataType("TextMetaData");
      keywords.setOptional(true);
      recordMetadata.add(keywords);
      targetEndUser = new HierarchicalStructuredTextMetaData();
      targetEndUser.setXMLTagName(MetaData.TARGETENDUSER);
      targetEndUser.setName("targetEndUser");
      targetEndUser.setMetaDataType("HierarchicalStructuredTextMetaData");
      targetEndUser.setOptional(true);
      recordMetadata.add(targetEndUser);
      schoolType = new HierarchicalStructuredTextMetaDataSet();
      schoolType.setXMLTagName(MetaData.SCHOOLTYPE);
      schoolType.setName("schoolType");
      schoolType.setMetaDataType("HierarchicalStructuredTextMetaDataSet");
      schoolType.setOptional(true);
      recordMetadata.add(schoolType);
      schoolDiscipline = new HierarchicalStructuredTextMetaData();
      schoolDiscipline.setXMLTagName(MetaData.SCHOOLDISCIPLINE);
      schoolDiscipline.setName("schoolDiscipline");
      schoolDiscipline.setMetaDataType("HierarchicalStructuredTextMetaData");
      schoolDiscipline.setOptional(true);
      recordMetadata.add(schoolDiscipline);
      didacticFunction = new TextMetaData();
      didacticFunction.setMandatory(true);
      didacticFunction.setXMLTagName(MetaData.DIDACTICFUNCTION);
      didacticFunction.setName("didacticFunction");
      didacticFunction.setMetaDataType("TextMetaData");
      recordMetadata.add(didacticFunction);
      productType = new TextMetaData();
      productType.setMandatory(true);
      productType.setXMLTagName(MetaData.PRODUCTTYPE);
      productType.setName("productType");
      productType.setMetaDataType("TextMetaData");
      recordMetadata.add(productType);
      professionalSituation = new HierarchicalStructuredTextMetaData();
      professionalSituation.setXMLTagName(MetaData.PROFESSIONALSITUATION);
      professionalSituation.setName("professionalSituation");
      professionalSituation.setMetaDataType("HierarchicalStructuredTextMetaData");
      professionalSituation.setOptional(true);
      recordMetadata.add(professionalSituation);
      competence = new TextMetaData();
      competence.setXMLTagName(MetaData.COMPETENCE);
      competence.setName("competence");
      competence.setMetaDataType("TextMetaData");
      competence.setOptional(true);
      recordMetadata.add(competence);
      secured = new BooleanMetaData();
      secured.setMandatory(true);
      secured.setXMLTagName(MetaData.SECURED);
      secured.setName("secured");
      secured.setMetaDataType("BooleanMetaData");
      recordMetadata.add(secured);
      fileFormat = new TextMetaData();
      fileFormat.setMandatory(true);
      fileFormat.setXMLTagName(MetaData.FILEFORMAT);
      fileFormat.setName("fileFormat");
      fileFormat.setMetaDataType("TextMetaData");
      recordMetadata.add(fileFormat);
      uri = new HyperlinkMetaData();
      uri.setMandatory(true);
      uri.setXMLTagName(MetaData.URI);
      uri.setName("uri");
      uri.setMetaDataType("HyperlinkMetaData");
      recordMetadata.add(uri);
      aggregationLevel = new TextMetaData();
      aggregationLevel.setXMLTagName(MetaData.AGGREGATIONLEVEL);
      aggregationLevel.setName("aggregationLevel");
      aggregationLevel.setMetaDataType("TextMetaData");
      aggregationLevel.setOptional(true);
      recordMetadata.add(aggregationLevel);
      didacticScenario = new TextMetaData();
      didacticScenario.setXMLTagName(MetaData.DIDACTICSCENARIO);
      didacticScenario.setName("didacticScenario");
      didacticScenario.setMetaDataType("TextMetaData");
      didacticScenario.setOptional(true);
      recordMetadata.add(didacticScenario);
      requiredTime = new NumericMetaData();
      requiredTime.setXMLTagName(MetaData.REQUIREDTIME);
      requiredTime.setName("requiredTime");
      requiredTime.setMetaDataType("NumericMetaData");
      requiredTime.setOptional(true);
      recordMetadata.add(requiredTime);
      rights = new TextMetaData();
      rights.setXMLTagName(MetaData.RIGHTS);
      rights.setName("rights");
      rights.setMetaDataType("TextMetaData");
      rights.setOptional(true);
      recordMetadata.add(rights);
      fileSize = new NumericMetaData();
      fileSize.setXMLTagName(MetaData.FILESIZE);
      fileSize.setName("fileSize");
      fileSize.setMetaDataType("NumericMetaData");
      fileSize.setOptional(true);
      recordMetadata.add(fileSize);
      playingTime = new NumericMetaData();
      playingTime.setXMLTagName(MetaData.PLAYINGTIME);
      playingTime.setName("playingTime");
      playingTime.setMetaDataType("NumericMetaData");
      playingTime.setOptional(true);
      recordMetadata.add(playingTime);
      technicalRequirements = new TextMetaData();
      technicalRequirements.setXMLTagName(MetaData.TECHNICALREQUIREMENTS);
      technicalRequirements.setName("technicalRequirements");
      technicalRequirements.setMetaDataType("TextMetaData");
      technicalRequirements.setOptional(true);
      recordMetadata.add(technicalRequirements);
      creationDate = new DateMetaData();
      creationDate.setXMLTagName(MetaData.CREATIONDATE);
      creationDate.setName("creationDate");
      creationDate.setMetaDataType("DateMetaData");
      creationDate.setOptional(true);
      recordMetadata.add(creationDate);
      lastChangedDate = new DateMetaData();
      lastChangedDate.setXMLTagName(MetaData.LASTCHANGEDDATE);
      lastChangedDate.setName("lastChangedDate");
      lastChangedDate.setMetaDataType("DateMetaData");
      lastChangedDate.setOptional(true);
      recordMetadata.add(lastChangedDate);
      version = new TextMetaData();
      version.setXMLTagName(MetaData.VERSION);
      version.setName("version");
      version.setMetaDataType("TextMetaData");
      version.setOptional(true);
      recordMetadata.add(version);
      status = new TextMetaData();
      status.setXMLTagName(MetaData.STATUS);
      status.setName("status");
      status.setMetaDataType("TextMetaData");
      status.setOptional(true);
      recordMetadata.add(status);
      roleName = new TextMetaData();
      roleName.setXMLTagName(MetaData.ROLENAME);
      roleName.setName("roleName");
      roleName.setMetaDataType("TextMetaData");
      roleName.setOptional(true);
      recordMetadata.add(roleName);

    } // end if

    return recordMetadata.iterator();

  } // end iterator()

  /**
   * Sets the value of the specified metadata field
   *
   * @param metadatafield the record metadata field
   * @param value the value
   */
  public void setValue(String metadatafield, Object value)
  {

    if (metadatafield.equals(MetaData.AGGREGATIONLEVEL)) {

      record.setAggregationLevel((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.COMPETENCE)) {

      record.setCompetence((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.CREATIONDATE)) {

      record.setCreationDate((DateMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.DESCRIPTION)) {

      record.setDescription((HtmlTextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.DIDACTICFUNCTION)) {

      record.setDidacticFunction((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.DIDACTICSCENARIO)) {

      record.setDidacticScenario((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.FILEFORMAT)) {

      record.setFileFormat((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.FILESIZE)) {

      record.setFileSize((NumericMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.KEYWORDS)) {

      record.setKeywords((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.LASTCHANGEDDATE)) {

      record.setLastChangedDate((DateMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.PLAYINGTIME)) {

      record.setPlayingTime((NumericMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.PRODUCTTYPE)) {

      record.setProductType((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.PROFESSIONALSITUATION)) {

      record.setProfessionalSituation((HierarchicalStructuredTextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.REQUIREDTIME)) {

      record.setRequiredTime((NumericMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.RIGHTS)) {

      record.setRights((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.ROLENAME)) {

      record.setRoleName((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.SCHOOLDISCIPLINE)) {

      record.setSchoolDiscipline((HierarchicalStructuredTextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.SCHOOLTYPE)) {

      record.setSchoolType((HierarchicalStructuredTextMetaDataSet) value);

    } // end if

    if (metadatafield.equals(MetaData.SECURED)) {

      record.setSecured((BooleanMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.STATUS)) {

      record.setStatus((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.SUBJECT)) {

      record.setSubject((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.TARGETENDUSER)) {

      record.setTargetEndUser((HierarchicalStructuredTextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.TECHNICALREQUIREMENTS)) {

      record.setTechnicalRequirements((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.TITLE)) {

      record.setTitle((TextMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.URI)) {

      record.setUri((HyperlinkMetaData) value);

    } // end if

    if (metadatafield.equals(MetaData.VERSION)) {

      record.setVersion((TextMetaData) value);

    } // end if

  } // end setValue()

}
