package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles advanced search page form submits. 
 * In fact redirects most of the work to the SearchBean...
 *
 * @author Falco Paul
 * @version $Revision$
  */
public class AdvancedSearchHandler extends SearchHandler {

  //~ Methods ----------------------------------------------------------------------------------------------------------

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

    getSearchBean(req).advancedSearch(req, res);

  }

}
