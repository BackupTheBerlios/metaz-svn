// @author: Falco Paul
 
package org.metaz.gui.portal;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

public class PortalTabTag extends TagSupport {

  // Active tab determination ID to be used with setPortalContent JSP tag

  public static final String ACTIVE_TAG_ATTRIBUTE = "ActiveTab";

  // Tab ID's... explicitly not done using enums...
  //             we need to refer to these ID's from a JSP page

  public static final String TAB_SIMPLE_SEARCH   = "SIMPLE";
  public static final String TAB_ADVANCED_SEARCH = "ADVANCED";
  public static final String TAB_HELP            = "HELP";
  public static final String TAB_INFO            = "INFO";
  public static final String TAB_GOOGLE          = "GOOGLE";
  

  private static Logger logger = Logger.getLogger(PortalTabTag.class); // logger instance for this class
  
  public String getActiveTabId() 
  {
    return (String) pageContext.getRequest().getAttribute(ACTIVE_TAG_ATTRIBUTE);
  }
    
  public int doStartTag() {
  
    return SKIP_BODY;
  
  }

  public void printHtml(String html)
  {

    try 
    {
      pageContext.getOut().println(html);
    }
    catch (IOException e)
    {
      // ignore
    }
    
  }
  
  public void addTab(String tabId, String text, String url, String tooltip)
  {
  
    if ((text == null) || (url == null))
      return;

    if (tooltip == null)
      tooltip = new String(text);
      
    String active = "";
    
    if (getActiveTabId().equals(tabId))
      active = "class=\"current\" ";
    
    printHtml("<li><a " + active + 
             "href=\"" + url + "\" " +
             "title=\"" + tooltip + "\"" +
             ">" + text +
             "</a></li>");
    
  }

  public String url (String url)
  {
    return PortalContent.getPortalBaseUrl ( (HttpServletRequest) pageContext.getRequest() ) + "/" + url;
  }
 
  public int doEndTag() throws JspException {

    addTab(TAB_SIMPLE_SEARCH, "Eenvoudig zoeken", "simple.jsp", "Zoeken naar leerobjecten");
    addTab(TAB_ADVANCED_SEARCH,"Uitgebreid zoeken", "advanced.jsp", "Uitgebreid zoeken naar leerobjecten");
    addTab(TAB_HELP, "Help", "help.jsp", "Help");
    addTab(TAB_INFO, "Informatie", "info.jsp", "Informatie over deze portal en het Rdmc");
    addTab(TAB_GOOGLE, "Google", "http://www.google.nl", "Naar google");

    return EVAL_PAGE;
    
  }
  
}
