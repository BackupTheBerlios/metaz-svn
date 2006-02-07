// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;

public class SetPortalContentTag extends BodyTagSupport {

  private static Logger logger = Logger.getLogger(SetPortalContentTag.class); // logger instance for this class

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
  
    return EVAL_BODY_BUFFERED;
  
  }
  
  public int doEndTag() throws JspException {
  
    if (getName() != null)
      if (getBodyContent() != null)
        pageContext.getRequest().setAttribute(getName(), getBodyContent().getString());
      
    return SKIP_BODY;
    
  }

}
