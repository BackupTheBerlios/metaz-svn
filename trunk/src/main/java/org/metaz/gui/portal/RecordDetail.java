package org.metaz.gui.portal;


// @author: Falco Paul
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public final class RecordDetail {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name;
  private Object value;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  public RecordDetail(String name, Object value) {

    this.name = name;
    this.value = value;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param name DOCUMENT ME!
   */
  public void setName(String name) {

    this.name = name;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getName() {

    return name;

  }

  /**
   * DOCUMENT ME!
   *
   * @param value DOCUMENT ME!
   */
  public void setValue(String value) {

    this.value = value;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Object getValue() {

    return value;

  }

}
