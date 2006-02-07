// Author: Falco Paul

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

public class PortalMenuTag extends TagSupport {

  private static Logger logger = Logger.getLogger(MetaZ.class); // logger instance for this class
    
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
  
  public void addTab(String text, String url, String tooltip)
  {

    printHtml("<tr>");
    printHtml("  <td height=\"1\" align=\"left\" valign=\"top\" bgcolor=\"#002C28\" class=\"NavBg1\">");
    
    if ((text != null) && (url != null))
    {
  
	    if (tooltip == null)
    		tooltip = new String(text);
                              
      try {
      
        printHtml(PortalContent.linkHtml("&nbsp;" + text, tooltip, 
                                         "Redirect" + 
                                         "?target=" + URLEncoder.encode(url, "UTF-8") +
                                         "?trail=reset", "MenuText", null));
        
      }
      catch (Exception e) {

        logger.error(e);

      }
      
    } else
    
      printHtml("&nbsp;");
      
    printHtml("  </td>");
    printHtml("</tr>");
    
  }

  public String url (String url)
  {
    return PortalContent.getPortalBaseUrl ( (HttpServletRequest) pageContext.getRequest() ) + "/" + url;
  }
 
  public int doEndTag() throws JspException {
    
    addTab("Eenvoudig zoeken", "simple.jsp", "Zoeken naar leerobjecten");
    addTab("Uitgebreid zoeken", "advanced.jsp", "Uitgebreid zoeken naar leerobjecten");
    addTab("Help", "help.jsp", "Help");
    addTab("Informatie", "info.jsp", "Informatie over deze portal en het Rdmc");
    addTab("Google", "http://www.google.nl", "Naar google");

    return EVAL_PAGE;
    
  }
  
}
