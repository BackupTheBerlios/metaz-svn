// @author: Falco Paul
package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class AdvancedSearchHandler extends SearchHandler {

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
  public void doPost(HttpServletRequest req, HttpServletResponse res)
              throws ServletException,
                     IOException
  {

    getSearchBean(req).advancedSearch(req, res);

  }

}
