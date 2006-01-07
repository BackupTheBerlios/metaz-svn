package org.metaz.domain;

/**
 * @author E.J. Spaans
 * Base class for MetaData used in @see org.metaz.domain.Record
 *
 */
public abstract class MetaData {
	
	private String name;
	private String description;
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
}
