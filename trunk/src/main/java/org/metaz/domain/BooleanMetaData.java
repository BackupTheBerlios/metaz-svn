package org.metaz.domain;

public class BooleanMetaData extends MetaData{
	private Boolean value;

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (Boolean)value;
	}
	

}