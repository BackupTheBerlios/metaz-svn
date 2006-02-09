package org.metaz.gui.portal;

public final class SelectOption {

  private boolean selected;
  private String  value;
  private String  description;
  private Object  marker;

  public SelectOption(boolean selected, String value, 
                      String description, Object marker) {
  
    this.selected = selected;
    this.value = value;
    this.description = description;
    this.marker = marker;
    
  }

  public SelectOption(String value, String description) {
  
    this(false, value, description, null);
    
  }

  public SelectOption(String description) {
  
    this(Integer.toString(description.hashCode()), description);
    
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

