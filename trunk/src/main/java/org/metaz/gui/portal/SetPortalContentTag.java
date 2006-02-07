// This is an abstract class that is used to set "forward" content in a JSP page
// Author: Falco Paul

package org.metaz.gui.portal;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

public class SetPortalContentTag extends BodyTagSupport {

  private String name = "NoName";
  
  public void setName(String setName) 
  {
    name = setName;
  }
  
  public String getName() 
  {
    return name;
  }
      
  public int doStartTag() {
  
    pageContext.getRequest().removeAttribute(getName()); 
  
    return EVAL_BODY_TAG;
  
  }
  
  public int doEndTag() throws JspException {
  
    if (getName() != null)
      if (getBodyContent() != null)
        pageContext.getRequest().setAttribute(getName(), getBodyContent().getString());
      
    return SKIP_BODY;
    
  }

}
