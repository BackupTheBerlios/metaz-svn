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

	private static final boolean ADVANCED_SEARCH = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public final void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		getSearchBean(req).search(req, res, ADVANCED_SEARCH);
	}

}
