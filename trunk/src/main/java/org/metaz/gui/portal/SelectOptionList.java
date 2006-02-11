package org.metaz.gui.portal;

import java.util.ArrayList;
import java.util.Iterator;

// @author: Falco Paul


// Represents HTML (multi) select options

public final class SelectOptionList extends ArrayList <SelectOption> {

  public static String toHtml(SelectOptionList options) {
  
    StringBuffer html = new StringBuffer();
    Iterator<SelectOption> i = options.iterator();
    
    while (i.hasNext()) {
    
      SelectOption option = i.next();
      html.append(option.toHtml() + "\n");
      
    }
    
    return html.toString();

  }

  public String toHtml() {
  
    return toHtml(this);

  }

  public SelectOption getOption(String value) {
  
    if (value == null)
      return null;
  
    Iterator<SelectOption> i = this.iterator();
    
    while (i.hasNext()) {
    
      SelectOption option = i.next();
      
      if (option.getValue().equals(value))
        return option;
      
    }
    
    return null;

  }

  public void setSelected(boolean value) {
  
    Iterator<SelectOption> i = this.iterator();
    
    while (i.hasNext()) {
    
      SelectOption option = i.next();
      option.setSelected(value);
      
    }

  }

}

