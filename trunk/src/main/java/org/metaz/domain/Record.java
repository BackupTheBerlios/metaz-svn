package org.metaz.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Record that holds a number of MetaData for a 'LeerObject'.
 * From a Harvester component a Record instance will be created
 * for each 'leerobject' element in the recieved xml.
 */
public class Record {

	// Mandatory MetaData.
	// See document Koppelingsspecificatie.doc
	private TextMetaData title;

	private BooleanMetaData secured;

	private TextMetaData fileFormat;

	private TextMetaData didacticalFunction;

	private TextMetaData productType;

	private HyperlinkMetaData uri;

	// Optional MetaData uses a list for
	// maximum flexibility.
	private List<MetaData> optionalMetaData;

	/**
	 * Creates a new Record. This constructor ensures only valid Records (that
	 * is, Records with all required MetaData) are used. Optional metadata may
	 * be added by adding a List<MetaData> of MetaData, or adding a single
	 * MetaData object to the optionalMetaData list.
	 * 
	 * @param title the mandatory title meta data.
	 * @param isSecured the mandatory isSecured meta data.
	 * @param fileFormat the mandatory fileFormat meta data.
	 * @param didacticalFunction the mandatory didacticalFunction meta data.
	 * @param productType the mandatory productType meta data.
	 * @param uri the mandatory uri meta data.
	 */
	public Record(TextMetaData title, BooleanMetaData isSecured,
			TextMetaData fileFormat, TextMetaData didacticalFunction,
			TextMetaData productType, HyperlinkMetaData uri) {
		this.title = title;
		this.secured = isSecured;
		this.fileFormat = fileFormat;
		this.didacticalFunction = didacticalFunction;
		this.productType = productType;
		this.uri = uri;
		
		this.optionalMetaData = new ArrayList<MetaData>();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public TextMetaData getDidacticalFunction() {
		return didacticalFunction;
	}

	/**
	 * Setter.
	 * 
	 * @param didacticalFunction
	 */
	public void setDidacticalFunction(TextMetaData didacticalFunction) {
		this.didacticalFunction = didacticalFunction;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public TextMetaData getFileFormat() {
		return fileFormat;
	}

	/**
	 * Setter.
	 * 
	 * @param fileFormat
	 */
	public void setFileFormat(TextMetaData fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public BooleanMetaData getSecured() {
		return secured;
	}

	/**
	 * Setter.
	 * 
	 * @param isSecured
	 */
	public void setSecured(BooleanMetaData isSecured) {
		this.secured = isSecured;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public List<MetaData> getOptionalMetaData() {
		return optionalMetaData;
	}

	/**
	 * Setter. Note that adding a List<MetaData> will overwrite any previously
	 * added MetaData using the
	 * 
	 * @see #addOptionalMetaData(MetaData) method!
	 * 
	 * @param optionalMetaData
	 */
	public void setOptionalMetaData(List<MetaData> optionalMetaData) {
		this.optionalMetaData = optionalMetaData;
	}

	/**
	 * Setter.
	 * 
	 * @param metaData
	 */
	public void addOptionalMetaData(MetaData metaData) {
		this.optionalMetaData.add(metaData);
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public TextMetaData getProductType() {
		return productType;
	}

	/**
	 * Setter.
	 * 
	 * @param productType
	 */
	public void setProductType(TextMetaData productType) {
		this.productType = productType;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public TextMetaData getTitle() {
		return title;
	}

	/**
	 * Setter.
	 * 
	 * @param title
	 */
	public void setTitle(TextMetaData title) {
		this.title = title;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public HyperlinkMetaData getUri() {
		return uri;
	}

	/**
	 * Setter.
	 * 
	 * @param uri
	 */
	public void setUri(HyperlinkMetaData uri) {
		this.uri = uri;
	}
}
