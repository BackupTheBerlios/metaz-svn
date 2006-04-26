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

  private Long value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the long value
   *
   * @return the long value
   */
  @Override
  public Object getValue()
  {

    return this.value;

  } // end getValue()

  /**
   * Sets the long value
   *
   * @param value the long value
   */
  @Override
  public void setValue(Object value)
  {

    this.value = (Long) value;

  } // end setValue()

} // end NumericMetaData
