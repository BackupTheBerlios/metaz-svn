package org.metaz.gui.portal;

import java.util.UUID;

// @author: Falco Paul
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public final class SelectOption {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private boolean selected;
  private String  value;
  private String  description;
  private Object  object;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  public SelectOption(boolean selected, String value, String description, Object object) {

    this.selected = selected;
    this.value = value;
    this.description = description;
    this.object = object;

  }

  public SelectOption(boolean selected, String value, String description) {
    this(selected, value, description, null);

  }

  public SelectOption(String value, String description) {
    this(false, value, description, null);

  }

  public SelectOption(boolean selected, String description) {
    // warning: hashcode() can be negative!!!
    // to prevent "ugly" values I generate "Long" hexadecimal values
    this(selected, defaultValue(description), description);

  }

  public SelectOption(String description) {
    // warning: hashcode() can be negative!!!
    // to prevent "ugly" values I generate "Long" hexadecimal values
    this(defaultValue(description), description);

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param option DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static String toHtml(SelectOption option) {

    StringBuffer html = new StringBuffer();

    html.append("<option value=\"" + option.getValue() + "\"");

    if (option.selected)
      html.append(" selected");

    html.append(">" + PortalUtil.htmlEscape(option.getDescription()) + "</option>");

    return html.toString();

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String toHtml() {

    return toHtml(this);

  }

  /**
   * DOCUMENT ME!
   *
   * @param description DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public static String defaultValue(String description) {

    return Long.toHexString(description.hashCode());

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String toString() {

    return this.isSelected() + ":" + this.getValue() + ":" + this.getDescription() + ":" + this.getObject();

  }

  /**
   * DOCUMENT ME!
   *
   * @param selected DOCUMENT ME!
   */
  public void setSelected(boolean selected) {

    this.selected = selected;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isSelected() {

    return selected;

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
  public String getValue() {

    return value;

  }

  /**
   * DOCUMENT ME!
   *
   * @param description DOCUMENT ME!
   */
  public void setDescription(String description) {

    this.description = description;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getDescription() {

    return description;

  }

  /**
   * DOCUMENT ME!
   *
   * @param object DOCUMENT ME!
   */
  public void setObject(Object object) {

    this.object = object;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Object getObject() {

    return object;

  }

}
