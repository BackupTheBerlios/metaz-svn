package org.metaz.gui.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public final class SelectOption {

  private boolean selected;
  private String  value;
  private String  description;
  private Object  marker;

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
                      String description, Object marker) {
  
    this.selected = selected;
    this.value = value;
    this.description = description;
    this.marker = marker;
    
  }

  public SelectOption(boolean selected, String value, 
                      String description) {

    this(selected, value, description, null);
    
  }

  public SelectOption(String value, String description) {
  
    this(false, value, description, null);
    
  }

  public SelectOption(String description) {

    this(UUID.randomUUID().toString(), description);
    
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

  public void setMarker(Object marker) {
    this.marker = marker;
  }

  public Object getMarker() {
    return marker;
  }
}

