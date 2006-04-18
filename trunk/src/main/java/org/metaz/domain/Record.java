package org.metaz.domain;

import java.util.*;

/**
 * Record that holds a number of MetaData for a 'LeerObject'. From a Harvester component a Record instance will be
 * created for each 'leerobject' element in the recieved xml.
 */
public class Record {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Long                                  id;
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

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
         * Creates a new Record. This constructor ensures only valid Records (that
         * is, Records with all required MetaData) are used. Optional metadata may
         * be added by using one of the setters.
         * 
         * @param title
         *            the mandatory title meta data.
         * @param isSecured
         *            the mandatory isSecured meta data.
         * @param fileFormat
         *            the mandatory fileFormat meta data.
         * @param didacticFunction
         *            the mandatory didacticFunction meta data.
         * @param productType
         *            the mandatory productType meta data.
         * @param uri
         *            the mandatory uri meta data.
         */
  public Record(TextMetaData title, BooleanMetaData isSecured, TextMetaData fileFormat, TextMetaData didacticFunction,
                TextMetaData productType, HyperlinkMetaData uri) {

    this.title = title;
    this.secured = isSecured;
    this.fileFormat = fileFormat;
    this.didacticFunction = didacticFunction;
    this.productType = productType;
    this.uri = uri;

  } // end Record()

/**
     * Creates a new Record object.
     */
  public Record() {
    super();

  } // end Record()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the title of the record
   *
   * @return the title
   */
  public TextMetaData getTitle() {

    return title;

  } // end getTitle()

  /**
   * Returns the subject of the record
   *
   * @return the subject
   */
  public TextMetaData getSubject() {

    return subject;

  } // end getSubject()

  /**
   * Sets the subject of the record
   *
   * @param subject the subject
   */
  public void setSubject(TextMetaData subject) {

    this.subject = subject;

  } // end setSubject()

  /**
   * Returns the description of the record
   *
   * @return the description
   */
  public HtmlTextMetaData getDescription() {

    return description;

  } // end getDescription()

  /**
   * Sets the description of the record
   *
   * @param description the description
   */
  public void setDescription(HtmlTextMetaData description) {

    this.description = description;

  } // end setDescription()

  /**
   * Returns the keywords of the record
   *
   * @return the keywords
   */
  public TextMetaData getKeywords() {

    return keywords;

  } // end getKeywords()

  /**
   * Sets the keywords of the record
   *
   * @param keywords the keywords
   */
  public void setKeywords(TextMetaData keywords) {

    this.keywords = keywords;

  } // end setKeywords()

  /**
   * Returns the target end users of the record
   *
   * @return the target end users
   */
  public HierarchicalStructuredTextMetaDataSet getTargetEndUser() {

    return targetEndUser;

  } // end getTargetEndUser()

  /**
   * Sets the target end users of the record
   *
   * @param targetEndUser the target end users
   */
  public void setTargetEndUser(HierarchicalStructuredTextMetaDataSet targetEndUser) {

    this.targetEndUser = targetEndUser;

  } // end setTargetEndUser()

  /**
   * Returns the school types of the records
   *
   * @return the school types
   */
  public HierarchicalStructuredTextMetaDataSet getSchoolType() {

    return schoolType;

  } // end getSchoolType()

  /**
   * Sets the school types of the record
   *
   * @param schoolTypes the school types
   */
  public void setSchoolType(HierarchicalStructuredTextMetaDataSet schoolTypes) {

    this.schoolType = schoolTypes;

  } // end setSchoolType()

  /**
   * Returns the school disciplines of the record
   *
   * @return the school disciplines
   */
  public HierarchicalStructuredTextMetaDataSet getSchoolDiscipline() {

    return schoolDiscipline;

  } // end getSchoolDiscipline()

  /**
   * Sets the school disciplines of the record
   *
   * @param schoolDiscipline the school disciplines
   */
  public void setSchoolDiscipline(HierarchicalStructuredTextMetaDataSet schoolDiscipline) {

    this.schoolDiscipline = schoolDiscipline;

  } // end setSchoolDiscipline()

  /**
   * Returns the didactic function of the record
   *
   * @return the didactic function
   */
  public TextMetaData getDidacticFunction() {

    return didacticFunction;

  } // end getDidacticFunction()

  /**
   * Sets the didactic function of the record
   *
   * @param didacticFunction the didactic function
   */
  public void setDidacticFunction(TextMetaData didacticFunction) {

    this.didacticFunction = didacticFunction;

  } // end setDidacticFunction()

  /**
   * Returns the product type of the record
   *
   * @return the product type
   */
  public TextMetaData getProductType() {

    return productType;

  } // end getProductType()

  /**
   * Sets the product type of the record
   *
   * @param productType the product type
   */
  public void setProductType(TextMetaData productType) {

    this.productType = productType;

  } // end setProductType()

  /**
   * Returns the professional situation of the record
   *
   * @return the professional situation
   */
  public HierarchicalStructuredTextMetaDataSet getProfessionalSituation() {

    return professionalSituation;

  } // end getProfessionalSituation()

  /**
   * Sets the professional situation of the record
   *
   * @param professionalSituation the professional situation
   */
  public void setProfessionalSituation(HierarchicalStructuredTextMetaDataSet professionalSituation) {

    this.professionalSituation = professionalSituation;

  } // end setProfessionalSituation()

  /**
   * Returns the competence of the record
   *
   * @return the competence
   */
  public TextMetaData getCompetence() {

    return competence;

  } // end getCompetence()

  /**
   * Sets the competence of the record
   *
   * @param competence the competence
   */
  public void setCompetence(TextMetaData competence) {

    this.competence = competence;

  } // end setCompetence()

  /**
   * Returns the secured status of the record
   *
   * @return the secured status
   */
  public BooleanMetaData getSecured() {

    return secured;

  } // end getSecured()

  /**
   * Sets the secured status of the record
   *
   * @param secured the secured status
   */
  public void setSecured(BooleanMetaData secured) {

    this.secured = secured;

  } // end setSecured()

  /**
   * Returns the file format of the record
   *
   * @return the file format
   */
  public TextMetaData getFileFormat() {

    return fileFormat;

  } // end getFileFormat()

  /**
   * Returns the URI of the record
   *
   * @return the URI
   */
  public HyperlinkMetaData getURI() {

    return uri;

  } // end getURI()

  /**
   * Returns the aggregation level of the record
   *
   * @return the aggregation level
   */
  public TextMetaData getAggregationLevel() {

    return aggregationLevel;

  } // end getAggregationLevel()

  /**
   * Sets the aggregation level of the record
   *
   * @param aggregationLevel the aggregation level
   */
  public void setAggregationLevel(TextMetaData aggregationLevel) {

    this.aggregationLevel = aggregationLevel;

  } // end setAggregationLevel()

  /**
   * Returns the didactic scenario of the record
   *
   * @return the didactic scenario
   */
  public TextMetaData getDidacticScenario() {

    return didacticScenario;

  } // end getDidacticScenario()

  /**
   * Sets the didactic scenario of the record
   *
   * @param didacticScenario the didactic scenario
   */
  public void setDidacticScenario(TextMetaData didacticScenario) {

    this.didacticScenario = didacticScenario;

  } // end setDidacticScenario()

  /**
   * Returns the required time of the record
   *
   * @return the required time
   */
  public NumericMetaData getRequiredTime() {

    return requiredTime;

  } // end getRequiredTime()

  /**
   * Sets the required time of the record
   *
   * @param requiredTime the required time
   */
  public void setRequiredTime(NumericMetaData requiredTime) {

    this.requiredTime = requiredTime;

  } // end setRequiredTime()

  /**
   * Returns the right settings of the record
   *
   * @return the right settings
   */
  public TextMetaData getRights() {

    return rights;

  } // end getRights()

  /**
   * Sets the right settings of the record
   *
   * @param rights the right settings
   */
  public void setRights(TextMetaData rights) {

    this.rights = rights;

  } // end setRights()

  /**
   * Returns the file size of the record
   *
   * @return the file size
   */
  public NumericMetaData getFileSize() {

    return fileSize;

  } // end getFileSize()

  /**
   * Sets the file size of the record
   *
   * @param fileSize the file size
   */
  public void setFileSize(NumericMetaData fileSize) {

    this.fileSize = fileSize;

  } // end setFileSize()

  /**
   * Returns the playing time of the record
   *
   * @return the playing time
   */
  public NumericMetaData getPlayingTime() {

    return playingTime;

  } // end getPlayingTime()

  /**
   * Sets the playing time of the record
   *
   * @param playingTime the playing time
   */
  public void setPlayingTime(NumericMetaData playingTime) {

    this.playingTime = playingTime;

  } // end setPlayingTime()

  /**
   * Returns the technical requirements of the record
   *
   * @return the technical requirements
   */
  public TextMetaData getTechnicalRequirements() {

    return technicalRequirements;

  } // end getTechnicalRequirements()

  /**
   * Sets the technical requirements of the record
   *
   * @param technicalRequirements the technical requirements
   */
  public void setTechnicalRequirements(TextMetaData technicalRequirements) {

    this.technicalRequirements = technicalRequirements;

  } // end setTechnicalRequirements()

  /**
   * Returns the creation data of the record
   *
   * @return the creation date
   */
  public DateMetaData getCreationDate() {

    return creationDate;

  } // end getCreationDate()

  /**
   * Sets the creation date of the record
   *
   * @param Date the creation date
   */
  public void setCreationDate(DateMetaData Date) {

    this.creationDate = Date;

  } // end setCreationDate()

  /**
   * Returns the last change date of the record
   *
   * @return the last change date
   */
  public DateMetaData getLastChangedDate() {

    return lastChangedDate;

  } // end getLastChangedDate()

  /**
   * Sets the last change date of the record
   *
   * @param date the last change date
   */
  public void setLastChangedDate(DateMetaData date) {

    this.lastChangedDate = date;

  } // end setLastChangedDate()

  /**
   * Returns the version of the record
   *
   * @return the version
   */
  public TextMetaData getVersion() {

    return version;

  } // end getVersion()

  /**
   * Sets the version of the record
   *
   * @param version the version
   */
  public void setVersion(TextMetaData version) {

    this.version = version;

  } // end setVersion()

  /**
   * Returns the status of the record
   *
   * @return the status
   */
  public TextMetaData getStatus() {

    return status;

  } // end getStatus()

  /**
   * Sets the status of the document
   *
   * @param status the status
   */
  public void setStatus(TextMetaData status) {

    this.status = status;

  } // end setStatus()

  /**
   * Returns the role-name pairs of the record
   *
   * @return the role-name pairs
   */
  public TextMetaData getRoleName() {

    return roleName;

  } // end getRoleName()

  /**
   * Sets the role-name pairs of the document
   *
   * @param roleName the role-name pairs
   */
  public void setRoleName(TextMetaData roleName) {

    this.roleName = roleName;

  } // end setRoleName()

  /**
   * Returns the id of the record
   *
   * @return the id
   */
  public Long getId() {

    return id;

  } // end getId()

  /**
   * Sets the id of the record
   *
   * @param id the id
   */
  public void setId(Long id) {

    this.id = id;

  } // end setId()

  /**
   * Returns the URI of the record
   *
   * @return the URI
   */
  public HyperlinkMetaData getUri() {

    return uri;

  } // end getUri()

  /**
   * Sets the URI of the record
   *
   * @param uri the URI
   */
  public void setUri(HyperlinkMetaData uri) {

    this.uri = uri;

  } // end setUri()

  /**
   * Sets the file format of the record
   *
   * @param fileFormat the file format
   */
  public void setFileFormat(TextMetaData fileFormat) {

    this.fileFormat = fileFormat;

  } // end setFileFormat()

  /**
   * Sets the title of the record
   *
   * @param title the title
   */
  public void setTitle(TextMetaData title) {

    this.title = title;

  } // end setTitle()    

} // end Record
