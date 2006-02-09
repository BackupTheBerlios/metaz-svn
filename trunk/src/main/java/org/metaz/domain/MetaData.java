package org.metaz.domain;

/**
 * @author E.J. Spaans
 * Base class for MetaData used in @see org.metaz.domain.Record
 * See document Koppelingsspecificatie.doc
 *
 */
public abstract class MetaData {
	
	/**
         * Metadata field name constant
         */
        public final static String TITLE = "titel";
        /**
         * Metadata field name constant
         */
        public final static String SUBJECT = "onderwerp";
        /**
        * Metadata field name constant
        */
        public final static String DESCRIPTION = "omschrijving";
        /**
        * Metadata field name constant
        */
        public final static String KEYWORDS ="sleutelwoorden";
        /**
        * Metadata field name constant
        */
        public final static String TARGETENDUSER = "beoogdeEindgebruiker";
    /**
    * Metadata field name constant
    */
        public final static String SCHOOLTYPE = "schooltype";
    /**
    * Metadata field name constant
    */
        public final static String SCHOOLDISCIPLINE = "vakleergebied";
    /**
    * Metadata field name constant
    */
        public final static String DIDACTICFUNCTION = "didactischeFunctie";
    /**
     * Metadata field name constant
     */
        public final static String PRODUCTTYPE = "producttype";
    /**
    * Metadata field name constant
    */
        public final static String PROFESSIONALSITUATION = "beroepssituatie";
    /**
    * Metadata field name constant
    */
        public final static String COMPETENCE = "competentie";
    /**
    * Metadata field name constant
    */
        public final static String SECURED = "beveiligd";
    /**
    * Metadata field name constant
    */
        public final static String FILEFORMAT ="bestandsformaat";
    /**
    * Metadata field name constant
    */
        public final static String URI = "uri";
    /**
    * Metadata field name constant
    */
        public final static String AGGREGATIONLEVEL = "aggregatieniveau";
    /**
    * Metadata field name constant
    */
        public final static String DIDACTICSCENARIO = "didactischScenario";
    /**
    * Metadata field name constant
    */
        public final static String REQUIREDTIME = "benodigdeTijd";
    /**
    * Metadata field name constant
    */
        public final static String RIGHTS = "rechten";
    /**
    * Metadata field name constant
    */
        public final static String FILESIZE = "bestandsgrootte";
    /**
    * Metadata field name constant
    */
        public final static String PLAYINGTIME = "afspeelduur";
    /**
    * Metadata field name constant
    */
        public final static String TECHNICALREQUIREMENTS = "technische vereiste";
    /**
    * Metadata field name constant
    */
        public final static String CREATIONDATE = "datumCreatie";
    /**
    * Metadata field name constant
    */
        public final static String LASTCHANGEDDATE = "datumLaatsteWijziging";
    /**
    * Metadata field name constant
    */
        public final static String VERSION = "versie";
    /**
    * Metadata field name constant
    */
        public final static String STATUS = "status";
    /**
    * Metadata field name constant
    */
        public final static String ROLENAME = "rolEnNaam";
        
    private String id;
        
    private String name;
    private String xmltagname;
	private String description;
	private String metadatatype;
	private boolean mandatory;
	private boolean optional;
	
	/**
	 * Gets the value of this MetaData.
	 * Types inheriting from MetaData will return
	 * an appropriate type. Callers then have to
	 * cast the value to the type of the returned value.
	 * @return
	 */
	public abstract Object getValue();
	
	/**
	 * Sets the value of this MetaData.
	 * @param value
	 */
	public abstract void setValue(Object value);
	
	/**
	 * Gets the description of this MetaData.
	 * @return the description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of this MetaData.
	 * @param description the description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns wether this MetaData is mandatory.
	 * @return true or false;
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	
	/**
	 * Sets wether this MetaData is mandatory.
	 * @param mandatory, true or false
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * Gets the name of this MetaData.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this MetaData.
	 * @param name the name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns wether this MetaData is optional.
	 * @return true or false.
	 */
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * Sets wether this MetaData is mandatory.
	 * @param optional true or false.
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the XML tagname of this MetaData.
	 * @return the xmltagname.
	 */
	public String getXMLTagName() {
		return xmltagname;
	}
	
	/**
	 * Sets the xml tagname of this MetaData.
	 * @param xmltagname the xml tagname.
	 */
	public void setXMLTagName(String xmltagname) {
		this.xmltagname = xmltagname;
	}

	/**
	 * Gets the XML tagname of this MetaData.
	 * @return the xmltagname.
	 */
	public String getMetaDataType() {
		return metadatatype;
	}
	
	/**
	 * Sets the xml tagname of this MetaData.
	 * @param xmltagname the xml tagname.
	 */
	public void setMetaDataType(String metadatatype) {
		this.metadatatype = metadatatype;
	}

}