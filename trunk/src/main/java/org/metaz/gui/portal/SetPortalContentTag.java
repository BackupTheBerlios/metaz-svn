// @author: Falco Paul
package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SetPortalContentTag extends BodyTagSupport {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger logger = Logger.getLogger(SetPortalContentTag.class); // logger instance for this class

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name = "NoName";

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
   * @return DOCUMENT ME!
   */
  public int doStartTag() {

    pageContext.getRequest().removeAttribute(getName());

    return EVAL_BODY_BUFFERED;

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

    if (getName() != null)

      if (getBodyContent() != null)
        pageContext.getRequest().setAttribute(getName(), getBodyContent().getString());

    return SKIP_BODY;

  }

}
