// @author: Falco Paul
package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class PortalTabTag extends TagSupport {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  // Active tab determination ID to be used with setPortalContent JSP tag
  public static final String ACTIVE_TAG_ATTRIBUTE = "ActiveTab";

  // Tab ID's... explicitly not done using enums...
  //             we need to refer to these ID's from a JSP page
  public static final String TAB_SIMPLE_SEARCH = "SIMPLE";
  public static final String TAB_ADVANCED_SEARCH = "ADVANCED";
  public static final String TAB_SEARCH_RESULTS = "RESULTS";
  public static final String TAB_HELP = "HELP";
  public static final String TAB_INFO = "INFO";
  public static final String TAB_RDMC = "RDMC";
  private static Logger      logger = Logger.getLogger(PortalTabTag.class); // logger instance for this class

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getActiveTabId() {

    return (String) pageContext.getRequest().getAttribute(ACTIVE_TAG_ATTRIBUTE);

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int doStartTag() {

    return SKIP_BODY;

  }

  /**
   * DOCUMENT ME!
   *
   * @param html DOCUMENT ME!
   */
  public void printHtml(String html) {

    try {

      pageContext.getOut().println(html);

    } catch (IOException e) {

      // ignore
    }

  }

  /**
   * DOCUMENT ME!
   *
   * @param tabId DOCUMENT ME!
   * @param text DOCUMENT ME!
   * @param url DOCUMENT ME!
   * @param tooltip DOCUMENT ME!
   */
  public void addTab(String tabId, String text, String url, String tooltip) {

    if ((text == null) || (url == null))

      return;

    if (tooltip == null)
      tooltip = new String(text);

    String active = "";

    if (getActiveTabId().trim().equals(tabId))
      active = "class=\"current\" ";

    printHtml("<li><a " + active + "href=\"" + url + "\" " + "title=\"" + tooltip + "\"" + ">" + text + "</a></li>");

  }

  /**
   * DOCUMENT ME!
   *
   * @param url DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String url(String url) {

    return PortalUtil.getPortalBaseUrl((HttpServletRequest) pageContext.getRequest()) + "/" + url;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws JspException DOCUMENT ME!
   */
  public int doEndTag()
               throws JspException
  {

    addTab(TAB_SIMPLE_SEARCH, "Eenvoudig zoeken", "search.jsp", "Zoeken naar leerobjecten");
    addTab(TAB_ADVANCED_SEARCH, "Uitgebreid zoeken", "advancedsearch.jsp", "Uitgebreid zoeken naar leerobjecten");
    addTab(TAB_SEARCH_RESULTS, "Zoekresultaat", "searchresults.jsp", "My last search results");
    addTab(TAB_HELP, "Help", "help.jsp", "Help");
    addTab(TAB_INFO, "Informatie", "info.jsp", "Informatie over deze website");
    addTab(TAB_RDMC, "RDMC", "http://www.ou.nl/eCache/DEF/4/991.html", "Ruud de Moor Centrum website");

    return EVAL_PAGE;

  }

}
