// This is an abstract class that is used to set "forward" content in a JSP page
// Author: Falco Paul

package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


public class GetPortalContentTag extends TagSupport {

  private static Logger logger = Logger.getLogger(GetPortalContentTag.class); // logger instance for this class

  private String name = "NoName";
  private String defaultValue = "";
    
  public void setName(String setName) 
  {
    name = setName;
  }
  
  public String getName() 
  {
    return name;
  }

  public void setDefault(String setDefaultValue) 
  {
    if (setDefaultValue == null)
      defaultValue = "";
    else
      defaultValue = setDefaultValue;
  }
  
  public String getDefault() 
  {
    return defaultValue;
  }
    
    
  public int doStartTag() {
  
    return SKIP_BODY;
  
  }
  
  public int doEndTag() throws JspException {
  
    String value = defaultValue;
    
    Object content = pageContext.getRequest().getAttribute(getName());
    
    if (content != null)
      if (content instanceof String)
        value = (String) content;
    
    try {
      pageContext.getOut().print(value);
    }
    catch (IOException e)
    {
      // ignore
    }
  
    return EVAL_PAGE;
    
  }
  
}
