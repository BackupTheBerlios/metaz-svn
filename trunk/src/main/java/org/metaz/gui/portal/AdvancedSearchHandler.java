// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdvancedSearchHandler extends SearchHandler {

  public void doPost(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException
  {
    getSearchBean(req).advancedSearch(req, res);
  }
  
}
