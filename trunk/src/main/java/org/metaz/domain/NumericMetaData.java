package org.metaz.domain;

/**
 * Represents numeric MetaData. No assumptions are made about the numeric values that might be used in the
 * application. Note that this implementation is constrained by the use of an Integer value. When using values larger
 * than  Integer.MAX_VALUE or smaller than  Integer.MIN_VALUE add a custom type for those values, e.g. a
 * LargeNumericMetaData class.
 *
 * @author E.J. Spaans
 */
public class NumericMetaData extends MetaData {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Integer value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the integer value
   *
   * @return the integer value
   */
  @Override
  public Object getValue() {

    return this.value;

  } // end getValue()

  /**
   * Sets the integer value
   *
   * @param value the integer value
   */
  @Override
  public void setValue(Object value) {

    this.value = (Integer) value;

  } // end setValue()

} // end NumericMetaData
