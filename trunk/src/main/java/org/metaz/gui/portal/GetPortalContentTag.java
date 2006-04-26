package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag class that allows "forward" definition of content in JSP pages ('getter') This class will inject previously
 * 'set' forward content
 *
 * @author Falco Paul
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
   * Setter for name
   *
   * @param setName DOCUMENT ME!
   */
  public void setName(String setName) {

    name = setName;

  }

  /**
   * Getter for name
   *
   * @return DOCUMENT ME!
   */
  public String getName() {

    return name;

  }

  /**
   * Sets default value
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
   * Gets default value
   *
   * @return DOCUMENT ME!
   */
  public String getDefault() {

    return defaultValue;

  }

  /**
   * Tag contract method: starts tag
   *
   * @return DOCUMENT ME!
   */
  public int doStartTag() {

    return SKIP_BODY;

  }

  /**
   * Tag contract method: ends tag
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

      logger.info("I/O error on tag output", e);

    }

    return EVAL_PAGE;

  }

}
