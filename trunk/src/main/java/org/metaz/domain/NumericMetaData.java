package org.metaz.domain;

/**
 * @author E.J. Spaans
 * 
 * Represents numeric MetaData.
 * No assumptions are made about the numeric values
 * that might be used in the application. Note that
 * this implementation is constrained by the use of
 * an Integer value. When using values larger than 
 * Integer.MAX_VALUE or smaller than 
 * Integer.MIN_VALUE add a custom type for those
 * values, e.g. a LargeNumericMetaData class.
 *
 */
public class NumericMetaData extends MetaData {
	
	private Integer value;

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (Integer) value;
	}

}
