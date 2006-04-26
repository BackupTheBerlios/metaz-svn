package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * JSP tag file to support 'tabbed' interface
 *
 * @author Falco Paul
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

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Retrieves the "active" tab from the request object
   *
   * @return active tab ID
   */
  public String getActiveTabId() {

    return (String) pageContext.getRequest().getAttribute(ACTIVE_TAG_ATTRIBUTE);

  }

  /**
   * TagSupport contract method ...
   *
   * @return SKIP_BODY constant
   */
  public int doStartTag() {

    return SKIP_BODY;

  }

  /**
   * Helper function... print html to the page context
   *
   * @param html the HTML to print
   */
  private void printHtml(String html) {

    try {

      pageContext.getOut().println(html);

    } catch (IOException e) {

      return; // no special handling

    }

  }

  /**
   * Helper function... 'adds' a tab to the output
   *
   * @param tabId the tab ID that's being added
   * @param text the text to appear on the tab
   * @param url URL that the tab is pointing to
   * @param tooltip the tab's tooltip
   * @param target DOCUMENT ME!
   */
  private void addTab(String tabId, String text, String url, String tooltip, String target) {

    if ((text == null) || (url == null))

      return;

    if (tooltip == null)
      tooltip = new String(text);

    String active = "";

    if (getActiveTabId().trim().equals(tabId))
      active = "class=\"current\" ";

    printHtml("<li><a " + active + "href=\"" + url + "\" " + target + "title=\"" + tooltip + "\"" + ">" + text +
              "</a></li>");

  }

  /**
   * TagSupport contract method ...   Injects the tabbed interface into the page
   *
   * @return EVAL_PAGE constant
   *
   * @throws JspException Thrown on error
   */
  public int doEndTag()
               throws JspException
  {

    addTab(TAB_SIMPLE_SEARCH, "Eenvoudig zoeken", "search.jsp", "Zoeken naar leerobjecten", "");
    addTab(TAB_ADVANCED_SEARCH, "Uitgebreid zoeken", "advancedsearch.jsp", "Uitgebreid zoeken naar leerobjecten", "");
    addTab(TAB_SEARCH_RESULTS, "Zoekresultaat", "searchresults.jsp", "My last search results", "");
    addTab(TAB_HELP, "Help", "help.jsp", "Help", "");
    addTab(TAB_INFO, "Informatie", "info.jsp", "Informatie over deze website", "");
    addTab(TAB_RDMC, "RDMC", "http://www.ou.nl/eCache/DEF/4/991.html", "Ruud de Moor Centrum website",
           "target=\"_blank\"");

    return EVAL_PAGE;

  }

}
