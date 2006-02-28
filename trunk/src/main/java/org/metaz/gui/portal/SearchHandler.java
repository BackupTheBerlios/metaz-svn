package org.metaz.gui.portal;

import org.metaz.domain.Record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles advanced search page form submits. 
 * In fact redirects most of the work to the SearchBean...
 *
 * @author Falco Paul
 * @version $Revision$
 */
public class SearchHandler extends HttpServlet {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Empty HttpServlet override
   */
  public void init() {

  }

  // retrieve the Session SearchBean instance, or create one if for some reasons this instance is not available yey
  protected SearchBean getSearchBean(HttpServletRequest req) {

    SearchBean searchBean = (SearchBean) req.getSession(true).getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    if (searchBean == null) {

      searchBean = new SearchBean();
      req.getSession().setAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY, searchBean);

    }

    return searchBean;

  }

  /**
   * Forwards handling to post
   *
   * @param req request
   * @param res response
   *
   * @throws ServletException On servlet error
   * @throws IOException On I/O error
   */
  public void doGet(HttpServletRequest req, HttpServletResponse res)
             throws ServletException,
                    IOException
  {

    doPost(req, res);

  }

  /**
   * Forwards hanlding to SearchBean
   *
   * @param req request
   * @param res response
   *
   * @throws ServletException On servlet error
   * @throws IOException On I/O error
   */
  public void doPost(HttpServletRequest req, HttpServletResponse res)
              throws ServletException,
                     IOException
  {

    getSearchBean(req).search(req, res);

  }

}
