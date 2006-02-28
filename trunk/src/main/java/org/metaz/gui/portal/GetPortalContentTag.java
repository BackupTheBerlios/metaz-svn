// This is an abstract class that is used to set "forward" content in a JSP page
// Author: Falco Paul
package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class GetPortalContentTag extends TagSupport {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger logger = Logger.getLogger(GetPortalContentTag.class); // logger instance for this class

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name = "NoName";
  private String defaultValue = "";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param setName DOCUMENT ME!
   */
  public void setName(String setName) {

    name = setName;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getName() {

    return name;

  }

  /**
   * DOCUMENT ME!
   *
   * @param setDefaultValue DOCUMENT ME!
   */
  public void setDefault(String setDefaultValue) {

    if (setDefaultValue == null)
      defaultValue = "";
    else
      defaultValue = setDefaultValue;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getDefault() {

    return defaultValue;

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
   * @return DOCUMENT ME!
   *
   * @throws JspException DOCUMENT ME!
   */
  public int doEndTag()
               throws JspException
  {

    String value = defaultValue;

    Object content = pageContext.getRequest().getAttribute(getName());

    if (content != null)

      if (content instanceof String)
        value = (String) content;

    try {

      pageContext.getOut().print(value);

    } catch (IOException e) {

      // ignore
    }

    return EVAL_PAGE;

  }

}
