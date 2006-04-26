package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles advanced search page form submits.
 *
 * @author Falco Paul
 * @author Erik-Jan Spaans
 * @version $Revision$
 */
public class AdvancedSearchHandler extends SearchHandler {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final boolean ADVANCED_SEARCH = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param req DOCUMENT ME!
   * @param res DOCUMENT ME!
   *
   * @throws ServletException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public final void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException,
                           IOException
  {

    getSearchBean(req).search(req, res, ADVANCED_SEARCH);

  }

}
