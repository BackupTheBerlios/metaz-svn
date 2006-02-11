// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchHandler extends HttpServlet {

  public void init() 
  {
  }

  protected SearchBean getSearchBean(HttpServletRequest req) 
  {

    SearchBean searchBean = (SearchBean) req.getSession().getAttribute("searchBean");
    
    if (searchBean == null)
      searchBean = new SearchBean();
      
    return searchBean;
    
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException
  {
    doPost(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException
  {
    getSearchBean(req).search(req, res);
  }
  
}
