// @author: Falco Paul
package org.metaz.gui.portal;

import org.metaz.domain.Record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SearchHandler extends HttpServlet {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void init() {

  }

  protected SearchBean getSearchBean(HttpServletRequest req) {

    SearchBean searchBean = (SearchBean) req.getSession().getAttribute("searchBean");

    if (searchBean == null)
      searchBean = new SearchBean();

    return searchBean;

  }

  /**
   * DOCUMENT ME!
   *
   * @param req DOCUMENT ME!
   * @param res DOCUMENT ME!
   *
   * @throws ServletException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public void doGet(HttpServletRequest req, HttpServletResponse res)
             throws ServletException,
                    IOException
  {

    doPost(req, res);

  }

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

    getSearchBean(req).search(req, res);

  }

}
