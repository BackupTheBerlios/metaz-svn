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

  public enum TabPage { SIMPLE_SEARCH, ADVANCED_SEARCH,
                        HELP, INFO, GOOGLE };

  private static Logger logger = Logger.getLogger(PortalTabTag.class); // logger instance for this class

   private TabPage currentTab = TabPage.SIMPLE_SEARCH;
     
   public void setCurrentTab(TabPage currentTab) 
   {
     this.currentTab = currentTab;
   }
   
   public TabPage getCurrentTab() 
   {
     return currentTab;
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
  
  public void addTab(TabPage tabId, String text, String url, String tooltip)
  {
  
    if ((text == null) || (url == null))
      return;

    if (tooltip == null)
      tooltip = new String(text);
      
    String current = "";
    
    if (currentTab == tabId)
      current = "class=\"current\" ";
    
    printHtml("<li><a " + current + 
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

    addTab(TabPage.SIMPLE_SEARCH, "Eenvoudig zoeken", "simple.jsp", "Zoeken naar leerobjecten");
    addTab(TabPage.ADVANCED_SEARCH,"Uitgebreid zoeken", "advanced.jsp", "Uitgebreid zoeken naar leerobjecten");
    addTab(TabPage.HELP, "Help", "help.jsp", "Help");
    addTab(TabPage.INFO, "Informatie", "info.jsp", "Informatie over deze portal en het Rdmc");
    addTab(TabPage.GOOGLE, "Google", "http://www.google.nl", "Naar google");

    return EVAL_PAGE;
    
  }
  
}
