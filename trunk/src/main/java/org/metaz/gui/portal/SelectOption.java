package org.metaz.gui.portal;

/**
 * This class represents EXACTLY ONE "select option"
 * Very usefull to represent HTML select options
 *
 * @author Falco Paul
 * @version $Revision$
 */
public final class SelectOption {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private boolean selected;
  private String  value;
  private String  description;
  private Object  object;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Contructor
   *
   * @param selected Is option selected?
   * @param value Option value
   * @param description Option description
   * @param object Optional payload
   */
  public SelectOption(boolean selected, String value, String description, Object object) {

    this.selected = selected;
    this.value = value;
    this.description = description;
    this.object = object;

  }

/**
   * Contructor
   *
   * @param selected Is option selected?
   * @param value Option value
   * @param description Option description
   */
  public SelectOption(boolean selected, String value, String description) {
    this(selected, value, description, null);

  }

/**
   * Contructor
   *
   * @param value Option value
   * @param description Option description
   */
  public SelectOption(String value, String description) {
    this(false, value, description, null);

  }

/**
   * Contructor
   *
   * @param selected Is option selected?
   * @param description Option description
   */
  public SelectOption(boolean selected, String description) {
    // warning: hashcode() can be negative!!!
    // to prevent "ugly" values I generate "Long" hexadecimal values
    this(selected, defaultValue(description), description);

  }

/**
   * Contructor
   *
   * @param description Option description
   */
  public SelectOption(String description) {
    // warning: hashcode() can be negative!!!
    // to prevent "ugly" values I generate "Long" hexadecimal values
    this(defaultValue(description), description);

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a HMTL representation of an option
   *
   * @param option option to HTML-ify
   *
   * @return the HTML string
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
   * Wrapper for toHtml(SelectOption option)
   *
   * @return HMTL representation of the invoking instance
   */
  public String toHtml() {

    return toHtml(this);

  }

  /**
   * Generates a "default" HTML select option value
   *
   * @param description Description to base default value on
   *
   * @return a "value" for a HTML select option
   */
  public static String defaultValue(String description) {

    return Long.toHexString(description.hashCode());

  }

  /**
   * Generates a nice String representation
   *
   * @return String representation
   */
  public String toString() {

    return this.isSelected() + ":" + this.getValue() + ":" + this.getDescription() + ":" + this.getObject();

  }

  /**
   * Setter for selected
   *
   * @param selected State
   */
  public void setSelected(boolean selected) {

    this.selected = selected;

  }

  /**
   * Getter for selected
   *
   * @return state
   */
  public boolean isSelected() {

    return selected;

  }

  /**
   * Getter for value
   *
   * @param value value
   */
  public void setValue(String value) {

    this.value = value;

  }

  /**
   * Setter for value
   *
   * @return value
   */
  public String getValue() {

    return value;

  }

  /**
   * Setter for description
   *
   * @param description description
   */
  public void setDescription(String description) {

    this.description = description;

  }

  /**
   * Getter for description
   *
   * @return description
   */
  public String getDescription() {

    return description;

  }

  /**
   * Setter for additional (and optional) payload object
   *
   * @param object payload object
   */
  public void setObject(Object object) {

    this.object = object;

  }

  /**
   * Getter for additional (and optional) payload object
   *
   * @return payload object
   */
  public Object getObject() {

    return object;

  }

}
