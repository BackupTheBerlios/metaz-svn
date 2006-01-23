package org.metaz.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Record that holds a number of MetaData for a 'LeerObject'.
 * From a Harvester component a Record instance will be created
 * for each 'leerobject' element in the recieved xml.
 */
public class Record {

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
        
	/**
	 * Creates a new Record. This constructor ensures only valid Records (that
	 * is, Records with all required MetaData) are used. Optional metadata may
	 * be added by using one of the setters.
	 * 
	 * @param title the mandatory title meta data.
	 * @param isSecured the mandatory isSecured meta data.
	 * @param fileFormat the mandatory fileFormat meta data.
	 * @param didacticFunction the mandatory didacticFunction meta data.
	 * @param productType the mandatory productType meta data.
	 * @param uri the mandatory uri meta data.
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
    public TextMetaData getProductType() {
        return productType;
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
    public void setCreationData(DateMetaData Date) {
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
}
