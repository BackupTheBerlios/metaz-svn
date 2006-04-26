package org.metaz.domain;

/**
 * Represents boolean MetaData.
 *
 * @author E.J. Spaans
 */
public class BooleanMetaData extends MetaData {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Boolean value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the value
   *
   * @return boolean value
   */
  @Override
  public Object getValue()
  {

    return this.value;

  } // end getValue()

  /**
   * Sets the value
   *
   * @param value the boolean value
   */
  @Override
  public void setValue(Object value)
  {

    this.value = (Boolean) value;

  } // end setValue()

} // end BooleanMetaData
