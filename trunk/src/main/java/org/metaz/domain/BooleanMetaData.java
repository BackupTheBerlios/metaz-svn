package org.metaz.domain;

/**
 * @author E.J. Spaans
 * 
 * Represents boolean MetaData.
 *
 */
public class BooleanMetaData extends MetaData{
	private Boolean value;

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (Boolean)value;
	}
	

}