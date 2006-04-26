package org.metaz.domain;

/**
 * Represents hyperlink MetaData.
 *
 * @author E.J. Spaans
 */
public class HyperlinkMetaData extends MetaData {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the string value
   *
   * @return the string value
   */
  @Override
  public Object getValue()
  {

    return this.value;

  } // end getValue()

  /**
   * Sets the string value
   *
   * @param value the string value
   */
  @Override
  public void setValue(Object value)
  {

    this.value = (String) value;

  } // end setValue()

} // end HyperlinkMetaData
