package org.metaz.gui.portal.forms;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* This is an utility servlet that helps building a form processing solution
* based on Form Procession API.
* A subclass must provide implementations for it's abstract methods.
*/
public abstract class FormServlet extends HttpServlet {

	private Form form;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (form == null)	form = getForm();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		 throws IOException, ServletException {
		if ( !form.process(req) ) 
			gotoPage(getFormPage(), req, resp);
		else {
			form.discard(req);
			gotoPage(getWelcomePage(), req, resp);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		 doGet(req, resp);
	}
	
	private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	public abstract Form getForm();
	public abstract String getFormPage();
	public abstract String getWelcomePage();
}