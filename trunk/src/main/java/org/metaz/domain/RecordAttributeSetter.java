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
  private HierarchicalStructuredTextMetaDataSet targetEndUser;
  private HierarchicalStructuredTextMetaDataSet schoolType;
  private HierarchicalStructuredTextMetaDataSet schoolDiscipline;
  private TextMetaData                          didacticFunction;
  private TextMetaData                          productType;
  private HierarchicalStructuredTextMetaDataSet professionalSituation;
  private TextMetaData                          competences;
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

      //mandatory metadata fields
      title = new TextMetaData();
      setMetaDataMetaData(title, true, MetaData.TITLE, "title", "TextMetaData");
      recordMetadata.add(title);
      didacticFunction = new TextMetaData();
      setMetaDataMetaData(didacticFunction, true, MetaData.DIDACTICFUNCTION, "didacticFunction", "TextMetaData");
      recordMetadata.add(didacticFunction);
      productType = new TextMetaData();
      setMetaDataMetaData(productType, true, MetaData.PRODUCTTYPE, "productType", "TextMetaData");
      recordMetadata.add(productType);
      secured = new BooleanMetaData();
      setMetaDataMetaData(secured, true, MetaData.SECURED, "secured", "BooleanMetaData");
      recordMetadata.add(secured);
      fileFormat = new TextMetaData();
      setMetaDataMetaData(fileFormat, true, MetaData.FILEFORMAT, "fileFormat", "TextMetaData");
      recordMetadata.add(fileFormat);
      uri = new HyperlinkMetaData();
      setMetaDataMetaData(uri, true, MetaData.URI, "uri", "HyperlinkMetaData");
      recordMetadata.add(uri);

      //optional metadata elements
      subject = new TextMetaData();
      setMetaDataMetaData(subject, false, MetaData.SUBJECT, "subject", "TextMetaData");
      recordMetadata.add(subject);
      description = new HtmlTextMetaData();
      setMetaDataMetaData(description, false, MetaData.DESCRIPTION, "description", "HtmlTextMetaData");
      recordMetadata.add(description);
      keywords = new TextMetaData();
      setMetaDataMetaData(keywords, false, MetaData.KEYWORDS, "keywords", "TextMetaData");
      recordMetadata.add(keywords);
      targetEndUser = new HierarchicalStructuredTextMetaDataSet();
      setMetaDataMetaData(targetEndUser, false, MetaData.TARGETENDUSER, "targetEndUser",
                          "HierarchicalStructuredTextMetaDataSet");
      recordMetadata.add(targetEndUser);
      schoolType = new HierarchicalStructuredTextMetaDataSet();
      setMetaDataMetaData(schoolType, false, MetaData.SCHOOLTYPE, "schoolType", "HierarchicalStructuredTextMetaDataSet");
      recordMetadata.add(schoolType);
      schoolDiscipline = new HierarchicalStructuredTextMetaDataSet();
      setMetaDataMetaData(schoolDiscipline, false, MetaData.SCHOOLDISCIPLINE, "schoolDiscipline",
                          "HierarchicalStructuredTextMetaDataSet");
      recordMetadata.add(schoolDiscipline);
      professionalSituation = new HierarchicalStructuredTextMetaDataSet();
      setMetaDataMetaData(professionalSituation, false, MetaData.PROFESSIONALSITUATION, "professionalSituation",
                          "HierarchicalStructuredTextMetaDataSet");
      recordMetadata.add(professionalSituation);
      competences = new TextMetaData();
      setMetaDataMetaData(competences, false, MetaData.COMPETENCE, "competence", "TextMetaData");
      recordMetadata.add(competences);
      aggregationLevel = new TextMetaData();
      setMetaDataMetaData(aggregationLevel, false, MetaData.AGGREGATIONLEVEL, "aggregationLevel", "TextMetaData");
      recordMetadata.add(aggregationLevel);
      didacticScenario = new TextMetaData();
      setMetaDataMetaData(didacticScenario, false, MetaData.DIDACTICSCENARIO, "didacticScenario", "TextMetaData");
      recordMetadata.add(didacticScenario);
      requiredTime = new NumericMetaData();
      setMetaDataMetaData(requiredTime, false, MetaData.REQUIREDTIME, "requiredTime", "NumericMetaData");
      recordMetadata.add(requiredTime);
      rights = new TextMetaData();
      setMetaDataMetaData(rights, false, MetaData.RIGHTS, "rights", "TextMetaData");
      recordMetadata.add(rights);
      fileSize = new NumericMetaData();
      setMetaDataMetaData(fileSize, false, MetaData.FILESIZE, "fileSize", "NumericMetaData");
      recordMetadata.add(fileSize);
      playingTime = new NumericMetaData();
      setMetaDataMetaData(playingTime, false, MetaData.PLAYINGTIME, "playingTime", "NumericMetaData");
      recordMetadata.add(playingTime);
      technicalRequirements = new TextMetaData();
      setMetaDataMetaData(technicalRequirements, false, MetaData.TECHNICALREQUIREMENTS, "technicalRequirements",
                          "TextMetaData");
      recordMetadata.add(technicalRequirements);
      creationDate = new DateMetaData();
      setMetaDataMetaData(creationDate, false, MetaData.CREATIONDATE, "creationDate", "DateMetaData");
      recordMetadata.add(creationDate);
      lastChangedDate = new DateMetaData();
      setMetaDataMetaData(lastChangedDate, false, MetaData.LASTCHANGEDDATE, "lastChangedDate", "DateMetaData");
      recordMetadata.add(lastChangedDate);
      version = new TextMetaData();
      setMetaDataMetaData(version, false, MetaData.VERSION, "version", "TextMetaData");
      recordMetadata.add(version);
      status = new TextMetaData();
      setMetaDataMetaData(status, false, MetaData.STATUS, "status", "TextMetaData");
      recordMetadata.add(status);
      roleName = new TextMetaData();
      setMetaDataMetaData(roleName, false, MetaData.ROLENAME, "roleName", "TextMetaData");
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
  public void setValue(String metadatafield, Object value) {

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

      record.setProfessionalSituation((HierarchicalStructuredTextMetaDataSet) value);

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

    setValue2(metadatafield, value);

  } // end setValue()

  /**
   * Sets the value of the specified metadata field second part due to checkstyle restrictions
   *
   * @param metadatafield the record metadata field
   * @param value the value
   */
  private void setValue2(String metadatafield, Object value) {

    if (metadatafield.equals(MetaData.SCHOOLDISCIPLINE)) {

      record.setSchoolDiscipline((HierarchicalStructuredTextMetaDataSet) value);

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

      record.setTargetEndUser((HierarchicalStructuredTextMetaDataSet) value);

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

  }

  /**
   * DOCUMENT ME!
   *
   * @param m metadata object
   * @param mandatory set to true if mandatory, if optional set to false
   * @param xmltag name of the xml tag
   * @param name name of the metadata object
   * @param metadatatype type of metadata
   */
  private void setMetaDataMetaData(MetaData m, boolean mandatory, String xmltag, String name, String metadatatype) {

    m.setMandatory(mandatory);
    m.setOptional(! mandatory);
    m.setXMLTagName(xmltag);
    m.setName(name);
    m.setMetaDataType(metadatatype);

  }

}
