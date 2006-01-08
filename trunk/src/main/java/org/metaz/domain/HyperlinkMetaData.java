package org.metaz.domain;

/**
 * @author E.J. Spaans
 * 
 * Represents hyperlink MetaData.
 *
 */
public class HyperlinkMetaData extends MetaData {
	
	private String value;

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (String) value;

	}

}
