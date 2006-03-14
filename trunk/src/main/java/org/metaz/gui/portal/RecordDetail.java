package org.metaz.gui.portal;

/**
 * This class represents EXACTLY ONE detail of a retrieved search record
 *
 * @author Falco Paul
 * @version $Revision$
 */
public final class RecordDetail {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name;
  private Object value;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Constructor that accepts the basic "record detail" values
   *
   * @param setName Name
   * @param setValue Value
   */
  public RecordDetail(String setName, Object setValue) {

    this.name = setName;
    this.value = setValue;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Name setter
   *
   * @param setName Name
   */
  public void setName(String setName) {

    this.name = setName;

  }

  /**
   * Name getter
   *
   * @return Name
   */
  public String getName() {

    return name;

  }

  /**
   * Value setter
   *
   * @param setValue Value
   */
  public void setValue(String setValue) {

    this.value = setValue;

  }

  /**
   * Value getter
   *
   * @return Value
   */
  public Object getValue() {

    return value;

  }

}
