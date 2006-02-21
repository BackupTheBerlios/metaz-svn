package org.metaz.gui.portal;

import java.util.UUID;

// @author: Falco Paul


public final class SelectOption {

  private boolean selected;
  private String  value;
  private String  description;
  private Object  object;

  public static String toHtml(SelectOption option) {
  
    StringBuffer html = new StringBuffer();

    html.append("<option value=\"" + option.getValue() + "\"");
    
    if (option.selected)
      html.append(" selected");
      
    html.append(">" + PortalUtil.htmlEscape(option.getDescription()) + "</option>");
      
    return html.toString();

  }

  public String toHtml() {
  
    return toHtml(this);
    
  }

  public SelectOption(boolean selected, String value, 
                      String description, Object object) {
  
    this.selected = selected;
    this.value = value;
    this.description = description;
    this.object = object;
    
  }

  public SelectOption(boolean selected, String value, 
                      String description) {

    this(selected, value, description, null);
    
  }

  public SelectOption(String value, String description) {
  
    this(false, value, description, null);
    
  }

  public SelectOption(String description) {
  
    // warning: hashcode() can be negative!!!
    // to prevent "ugly" values I generate "Long" hexadecimal values

    this(Long.toHexString(description.hashCode()), description);
    
  }

  public String toString() {
  
    return this.isSelected() + ":" +
           this.getValue() + ":" +
           this.getDescription() + ":" +
           this.getObject();
    
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public Object getObject() {
    return object;
  }
  
}

