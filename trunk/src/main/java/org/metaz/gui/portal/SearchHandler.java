// @author: Falco Paul

package org.metaz.gui.portal;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

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
