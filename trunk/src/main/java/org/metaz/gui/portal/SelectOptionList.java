package org.metaz.gui.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

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

}

