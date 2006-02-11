// @author: Falco Paul

package org.metaz.gui.portal;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class AdvancedSearchHandler extends SearchHandler {

  public void doPost(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException
  {
    getSearchBean(req).advancedSearch(req, res);
  }
  
}
