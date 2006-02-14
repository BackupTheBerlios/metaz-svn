package org.metaz.gui.portal;

// @author: Falco Paul
// Each record detail represents a name/value pair (from some Record)
// that we wish display to the user... it may seem a bit trivial,
// but we may actually add all kind of attributes here so the displaytag library
// may gather additional display knowdlege from a RecordDetail instance.
// Think about things like additional style attributes such as "show bold", etc.

public final class RecordDetail {

  private String name;
  private Object value;

  public RecordDetail(String name, Object value) {
  
    this.name = name;
    this.value = value;
    
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Object getValue() {
    return value;
  }
  
}

