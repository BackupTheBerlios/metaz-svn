package org.metaz.domain;

/**
 * Base class for MetaData used in Application Z
 *
 * @author E.J. Spaans
 *
 * @see org.metaz.domain.Record See document Koppelingsspecificatie.doc
 */
public abstract class MetaData
  implements Comparable
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** Metadata field name constant */
  public final static String TITLE = "titel";

  /** Metadata field name constant */
  public final static String SUBJECT = "onderwerp";

  /** Metadata field name constant */
  public final static String DESCRIPTION = "omschrijving";

  /** Metadata field name constant */
  public final static String KEYWORDS = "sleutelwoorden";

  /** Metadata field name constant */
  public final static String TARGETENDUSER = "beoogdeEindgebruiker";

  /** Metadata field name constant */
  public final static String SCHOOLTYPE = "schooltype";

  /** Metadata field name constant */
  public final static String SCHOOLDISCIPLINE = "vakleergebied";

  /** Metadata field name constant */
  public final static String DIDACTICFUNCTION = "didactischeFunctie";

  /** Metadata field name constant */
  public final static String PRODUCTTYPE = "producttype";

  /** Metadata field name constant */
  public final static String PROFESSIONALSITUATION = "beroepssituatie";

  /** Metadata field name constant */
  public final static String COMPETENCE = "competentie";

  /** Metadata field name constant */
  public final static String SECURED = "beveiligd";

  /** Metadata field name constant */
  public final static String FILEFORMAT = "bestandsformaat";

  /** Metadata field name constant */
  public final static String URI = "uri";

  /** Metadata field name constant */
  public final static String AGGREGATIONLEVEL = "aggregatieniveau";

  /** Metadata field name constant */
  public final static String DIDACTICSCENARIO = "didactischScenario";

  /** Metadata field name constant */
  public final static String REQUIREDTIME = "benodigdeTijd";

  /** Metadata field name constant */
  public final static String RIGHTS = "rechten";

  /** Metadata field name constant */
  public final static String FILESIZE = "bestandsgrootte";

  /** Metadata field name constant */
  public final static String PLAYINGTIME = "afspeelduur";

  /** Metadata field name constant */
  public final static String TECHNICALREQUIREMENTS = "technischeVereiste";

  /** Metadata field name constant */
  public final static String CREATIONDATE = "datumCreatie";

  /** Metadata field name constant */
  public final static String LASTCHANGEDDATE = "datumLaatsteWijziging";

  /** Metadata field name constant */
  public final static String VERSION = "versie";

  /** Metadata field name constant */
  public final static String STATUS = "status";

  /** Metadata field name constant */
  public final static String ROLENAME = "rolEnNaam";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Long    id;
  private String  name;
  private String  xmltagname;
  private String  description;
  private String  metadatatype;
  private boolean mandatory;
  private boolean optional;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Gets the value of this MetaData. Types inheriting from MetaData will return an appropriate type. Callers
   * then have to cast the value to the type of the returned value.
   *
   * @return the value
   */
  public abstract Object getValue();

  /**
   * Sets the value of this MetaData.
   *
   * @param value the value
   */
  public abstract void setValue(Object value);

  /**
   * Gets the description of this MetaData.
   *
   * @return the description.
   */
  public String getDescription() {

    return description;

  } // end getDescription()

  /**
   * Sets the description of this MetaData.
   *
   * @param description the description.
   */
  public void setDescription(String description) {

    this.description = description;

  } // end setDescription()

  /**
   * Returns wether this MetaData is mandatory.
   *
   * @return true or false;
   */
  public boolean isMandatory() {

    return mandatory;

  } // end isMandatory()

  /**
   * Sets wether this MetaData is mandatory.
   *
   * @param mandatory true or false
   */
  public void setMandatory(boolean mandatory) {

    this.mandatory = mandatory;

  } // end setMandatory()

  /**
   * Gets the name of this MetaData.
   *
   * @return the name.
   */
  public String getName() {

    return name;

  } // end getName()

  /**
   * Sets the name of this MetaData.
   *
   * @param name the name.
   */
  public void setName(String name) {

    this.name = name;

  } // end setName()

  /**
   * Returns wether this MetaData is optional.
   *
   * @return true or false.
   */
  public boolean isOptional() {

    return optional;

  } // end isOptional()

  /**
   * Sets wether this MetaData is mandatory.
   *
   * @param optional true or false.
   */
  public void setOptional(boolean optional) {

    this.optional = optional;

  } // end setOptional()

  /**
   * Returns the metadata id
   *
   * @return the metadata id
   */
  public Long getId() {

    return id;

  } // end getId()

  /**
   * Sets the metadata id
   *
   * @param id the metadata id
   */
  public void setId(Long id) {

    this.id = id;

  } // end setId()

  /**
   * Gets the XML tagname of this MetaData.
   *
   * @return the xmltagname.
   */
  public String getXMLTagName() {

    return xmltagname;

  } // end getXMLTagName()

  /**
   * Sets the xml tagname of this MetaData.
   *
   * @param xmltagname the xml tagname.
   */
  public void setXMLTagName(String xmltagname) {

    this.xmltagname = xmltagname;

  } // end setXMLTagName()

  /**
   * Gets the XML tagname of this MetaData.
   *
   * @return the xmltagname.
   */
  public String getMetaDataType() {

    return metadatatype;

  } // end getMetaDataType()

  /**
   * Sets the xml tagname of this MetaData.
   *
   * @param metadatatype the xml tagname.
   */
  public void setMetaDataType(String metadatatype) {

    this.metadatatype = metadatatype;

  } // end setMetaDataType()

  /**
   * Returns a representation of this MetaData instance.
   *
   * @return string representation.
   */
  public String toString() {

    if (this.getValue() != null) {

      return this.getValue().toString();

    } else {

      return "";

    } //end if

  } // end toString()

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  /**
   * DOCUMENT ME!
   *
   * @param other DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int compareTo(Object other) {

    // reuse string comparator
    return toString().compareTo(other.toString());

  }

} // end MetaData
