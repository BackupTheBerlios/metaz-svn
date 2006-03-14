package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Tag class that allows "forward" definition of content in JSP pages ('setter')
 *
 * @author Falco Paul
 * @version $Revision$
  */
public class SetPortalContentTag extends BodyTagSupport {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String name = "NoName";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Name property setter
   *
   * @param setName Name of the injection
   */
  public void setName(String setName) {

    name = setName;

  }

  /**
   * Name property getter
   *
   * @return name property
   */
  public String getName() {

    return name;

  }

  /**
   * TagSupport contract method ... 
   * 
   * Used to mark the start of an injectable source
   *
   * @return EVAL_BODY_BUFFERED constant
   */
  public int doStartTag() {

    pageContext.getRequest().removeAttribute(getName());

    return EVAL_BODY_BUFFERED;

  }

  /**
   * TagSupport contract method ... 
   * 
   * Used to mark the end of an injectable source
   * Stores the injectable source as a request attribute
   *
   * @return SKIP_BODY constant
   *
   * @throws JspException thrown on error
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
