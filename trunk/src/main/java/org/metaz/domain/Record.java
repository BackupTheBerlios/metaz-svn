package org.metaz.domain;

import java.util.*;

/**
 * Record that holds a number of MetaData for a 'LeerObject'. From a Harvester
 * component a Record instance will be created for each 'leerobject' element in
 * the recieved xml.
 */
public class Record {
	
	private String id;

	private TextMetaData title;
        private TextMetaData subject;
        private HtmlTextMetaData description;
        private TextMetaData keywords;
        private HierarchicalStructuredTextMetaData targetEndUser;
        private HierarchicalStructuredTextMetaDataSet schoolType;
        private HierarchicalStructuredTextMetaData schoolDiscipline;
        private TextMetaData didacticFunction;
        private TextMetaData productType;
        private HierarchicalStructuredTextMetaData professionalSituation;
        private TextMetaData competence;
        private BooleanMetaData secured;
        private TextMetaData fileFormat;
        private HyperlinkMetaData uri;
        private TextMetaData aggregationLevel;
        private TextMetaData didacticScenario;
        private NumericMetaData requiredTime;
        private TextMetaData rights;
        private NumericMetaData fileSize;
        private NumericMetaData playingTime;
        private TextMetaData technicalRequirements;
        private DateMetaData creationDate;
        private DateMetaData lastChangedDate;
        private TextMetaData version;
        private TextMetaData status;
        private TextMetaData roleName;
        
        private List<MetaData> recordMetadata= new Vector<MetaData>();
        
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
	public Record(TextMetaData title, BooleanMetaData isSecured,
			TextMetaData fileFormat, TextMetaData didacticFunction,
			TextMetaData productType, HyperlinkMetaData uri) {
                this.title = title;
		this.secured = isSecured;
		this.fileFormat = fileFormat;
		this.didacticFunction = didacticFunction;
		this.productType = productType;
		this.uri = uri;
	}
	
	public Record(){
		super();
	}

    public TextMetaData getTitle() {
        return title;
    }
    public TextMetaData getSubject() {
        return subject;
    }
    public void setSubject(TextMetaData subject) {
        this.subject = subject;
    }
    public HtmlTextMetaData getDescription() {
        return description;
    }
    public void setDescription (HtmlTextMetaData description) {
        this.description = description;
    }
    public TextMetaData getKeywords() {
        return keywords;
    }
    public void setKeywords(TextMetaData keywords) {
        this.keywords = keywords;
    }
    public HierarchicalStructuredTextMetaData getTargetEndUser() {
        return targetEndUser;
    }
    public void setTargetEndUser(HierarchicalStructuredTextMetaData targetEndUser) {
        this.targetEndUser = targetEndUser;
    }
    public HierarchicalStructuredTextMetaDataSet getSchoolType() {
        return schoolType;
    }
    public void setSchoolType(HierarchicalStructuredTextMetaDataSet schoolTypes) {
        this.schoolType = schoolTypes;
    }
    public HierarchicalStructuredTextMetaData getSchoolDiscipline() {
        return schoolDiscipline;
    }
    public void setSchoolDiscipline(HierarchicalStructuredTextMetaData schoolDiscipline) {
        this.schoolDiscipline = schoolDiscipline;
    }
    public TextMetaData getDidacticFunction() {
        return didacticFunction;
    }
    public void setDidacticFunction(TextMetaData didacticFunction){
    	this.didacticFunction = didacticFunction;
    }
    public TextMetaData getProductType() {
        return productType;
    }
    
    public void setProductType(TextMetaData productType){
    	this.productType = productType;
    }
    public HierarchicalStructuredTextMetaData getProfessionalSituation() {
        return professionalSituation;
    }
    public void setProfessionalSituation(HierarchicalStructuredTextMetaData professionalSituation) {
        this.professionalSituation = professionalSituation;
    }
    public TextMetaData getCompetence(){
        return competence;
    }
    public void setCompetence(TextMetaData competence) {
        this.competence = competence;
    }
    public BooleanMetaData getSecured(){
        return secured;
    }
    public void setSecured(BooleanMetaData secured){
    	this.secured = secured;
    }
    public TextMetaData getFileFormat() {
        return fileFormat;
    }
    public HyperlinkMetaData getURI() {
        return uri;
    }
    public TextMetaData getAggregationLevel() {
        return aggregationLevel;
    }
    public void setAggregationLevel(TextMetaData aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }
    public TextMetaData getDidacticScenario(){
        return didacticScenario;
    }
    public void setDidacticScenario(TextMetaData didacticScenario) {
        this.didacticScenario = didacticScenario;
    }
    public NumericMetaData getRequiredTime() {
        return requiredTime;
    }
    public void setRequiredTime(NumericMetaData requiredTime) {
        this.requiredTime = requiredTime;
    }
    public TextMetaData getRights () {
        return rights;
    }
    public void setRights(TextMetaData rights) {
        this.rights = rights;
    }
    public NumericMetaData getFileSize() {
        return fileSize;
    }
    public void setFileSize(NumericMetaData fileSize) {
        this.fileSize = fileSize;
    }
    public NumericMetaData getPlayingTime() {
        return playingTime;
    }
    public void setPlayingTime(NumericMetaData playingTime) {
        this.playingTime = playingTime;
    }
    public TextMetaData getTechnicalRequirements(){
        return technicalRequirements;
    }
    public void setTechnicalRequirements(TextMetaData technicalRequirements){
        this.technicalRequirements = technicalRequirements;
    }
    public DateMetaData getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(DateMetaData Date) {
        this.creationDate = Date;
    }
    public DateMetaData getLastChangedDate(){
        return lastChangedDate;
    }
    public void setLastChangedDate(DateMetaData date) {
        this.lastChangedDate = date;
    }
    public TextMetaData getVersion() {
        return version;
    }
    public void setVersion(TextMetaData version){
        this.version = version;
    }
    public TextMetaData getStatus() {
        return status;
    }
    public void setStatus(TextMetaData status){
        this.status = status;
    }
    public TextMetaData getRoleName() {
        return roleName;
    }
    public void setRoleName(TextMetaData roleName) {
        this.roleName = roleName;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HyperlinkMetaData getUri() {
		return uri;
	}

	public void setUri(HyperlinkMetaData uri) {
		this.uri = uri;
	}

	public void setFileFormat(TextMetaData fileFormat) {
		this.fileFormat = fileFormat;
	}

	public void setTitle(TextMetaData title) {
		this.title = title;
	}

	public Iterator iterator(){
		if (recordMetadata.size()==0){
			title.setMandatory(true);
			title.setXMLTagName(MetaData.TITLE);
			title.setName("title");
			title.setMetaDataType("TextMetaData");
			recordMetadata.add(1,title);
			subject.setXMLTagName(MetaData.SUBJECT);
			subject.setName("subject");
			subject.setMetaDataType("TextMetaData");
			subject.setOptional(true);
			recordMetadata.add(1,subject);
			description.setXMLTagName(MetaData.DESCRIPTION);
			description.setName("description");
			description.setMetaDataType("HtmlTextMetaData ");
			description.setOptional(true);
			recordMetadata.add(1,description);
			keywords.setXMLTagName(MetaData.KEYWORDS);
			keywords.setName("keywords");
			keywords.setMetaDataType("TextMetaData");
			keywords.setOptional(true);
			recordMetadata.add(1,keywords);
			targetEndUser.setXMLTagName(MetaData.TARGETENDUSER);
			targetEndUser.setName("targetEndUser");
			targetEndUser.setMetaDataType("HierarchicalStructuredTextMetaData");
			targetEndUser.setOptional(true);
			recordMetadata.add(1,targetEndUser);
			schoolType.setXMLTagName(MetaData.SCHOOLTYPE);
			schoolType.setName("schoolType");
			schoolType.setMetaDataType("HierarchicalStructuredTextMetaDataSet");
			schoolType.setOptional(true);
			recordMetadata.add(1,schoolType);
			schoolDiscipline.setXMLTagName(MetaData.SCHOOLDISCIPLINE);
			schoolDiscipline.setName("schoolDiscipline");
			schoolDiscipline.setMetaDataType("HierarchicalStructuredTextMetaData");
			schoolDiscipline.setOptional(true);
			recordMetadata.add(1,schoolDiscipline);
			didacticFunction.setMandatory(true);
			didacticFunction.setXMLTagName(MetaData.DIDACTICFUNCTION);
			didacticFunction.setName("didacticFunction");
			didacticFunction.setMetaDataType("TextMetaData");
			recordMetadata.add(1,didacticFunction);
			productType.setMandatory(true);
			productType.setXMLTagName(MetaData.PRODUCTTYPE);
			productType.setName("productType");
			productType.setMetaDataType("TextMetaData");
			recordMetadata.add(1,productType);
			professionalSituation.setXMLTagName(MetaData.PROFESSIONALSITUATION);
			professionalSituation.setName("professionalSituation");
			professionalSituation.setMetaDataType("HierarchicalStructuredTextMetaData");
			professionalSituation.setOptional(true);
			recordMetadata.add(1,professionalSituation);
			competence.setXMLTagName(MetaData.COMPETENCE);
			competence.setName("competence");
			competence.setMetaDataType("TextMetaData");
			competence.setOptional(true);
			recordMetadata.add(1,competence);
			secured.setMandatory(true);
			secured.setXMLTagName(MetaData.SECURED);
			secured.setName("secured");
			secured.setMetaDataType("BooleanMetaData");
			recordMetadata.add(1,secured);
			fileFormat.setMandatory(true);
			fileFormat.setXMLTagName(MetaData.FILEFORMAT);
			fileFormat.setName("fileFormat");
			fileFormat.setMetaDataType("TextMetaData");
			recordMetadata.add(1,fileFormat);
			uri.setMandatory(true);
			uri.setXMLTagName(MetaData.URI);
			uri.setName("uri");
			uri.setMetaDataType("HyperlinkMetaData");
			recordMetadata.add(1,uri);
			aggregationLevel.setXMLTagName(MetaData.AGGREGATIONLEVEL);
			aggregationLevel.setName("aggregationLevel");
			aggregationLevel.setMetaDataType("TextMetaData");
			aggregationLevel.setOptional(true);
			recordMetadata.add(1,aggregationLevel);
			didacticScenario.setXMLTagName(MetaData.DIDACTICSCENARIO);
			didacticScenario.setName("didacticScenario");
			didacticScenario.setMetaDataType("TextMetaData");
			didacticScenario.setOptional(true);
			recordMetadata.add(1,didacticScenario);
			requiredTime.setXMLTagName(MetaData.REQUIREDTIME);
			requiredTime.setName("requiredTime");
			requiredTime.setMetaDataType("NumericMetaData");
			requiredTime.setOptional(true);
			recordMetadata.add(1,requiredTime);
			rights.setXMLTagName(MetaData.RIGHTS);
			rights.setName("rights");
			rights.setMetaDataType("TextMetaData");
			rights.setOptional(true);
			recordMetadata.add(1,rights);
			fileSize.setXMLTagName(MetaData.FILESIZE);
			fileSize.setName("fileSize");
			fileSize.setMetaDataType("NumericMetaData");
			fileSize.setOptional(true);
			recordMetadata.add(1,fileSize);
			playingTime.setXMLTagName(MetaData.PLAYINGTIME);
			playingTime.setName("playingTime");
			playingTime.setMetaDataType("NumericMetaData");
			playingTime.setOptional(true);
			recordMetadata.add(1,playingTime);
			technicalRequirements.setXMLTagName(MetaData.TECHNICALREQUIREMENTS);
			technicalRequirements.setName("technicalRequirements");
			technicalRequirements.setMetaDataType("TextMetaData");
			technicalRequirements.setOptional(true);
			recordMetadata.add(1,technicalRequirements);
			creationDate.setXMLTagName(MetaData.CREATIONDATE);
			creationDate.setName("creationDate");
			creationDate.setMetaDataType("DateMetaData");
			creationDate.setOptional(true);
			recordMetadata.add(1,creationDate);
			lastChangedDate.setXMLTagName(MetaData.LASTCHANGEDDATE);
			lastChangedDate.setName("lastChangedDate");
			lastChangedDate.setMetaDataType("DateMetaData");
			lastChangedDate.setOptional(true);
			recordMetadata.add(1,lastChangedDate);
			version.setXMLTagName(MetaData.VERSION);
			version.setName("version");
			version.setMetaDataType("TextMetaData");
			version.setOptional(true);
			recordMetadata.add(1,version);
			status.setXMLTagName(MetaData.STATUS);
			status.setName("status");
			status.setMetaDataType("TextMetaData");
			status.setOptional(true);
			recordMetadata.add(1,status);
			roleName.setXMLTagName(MetaData.ROLENAME);
			roleName.setName("roleName");
			roleName.setMetaDataType("TextMetaData");
			roleName.setOptional(true);
			recordMetadata.add(1,roleName);
		}
		return recordMetadata.iterator();
	}
}
