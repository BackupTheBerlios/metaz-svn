/*
 * @(#)Form.java	1.0 2001/02/27
 *
 * Copyright (C) 2001 Ilirjan Ostrovica. All rights reserved. 
 *
 * If you are interested in using Form Processing API to benefit an open-source
 * software project or any other kind of non-commercial project
 * (non-copylefted projects included), redistribution and use, in source and
 * binary forms, with or without modification, are permitted provided that
 * the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, in the documentation and/or other materials provided with the
 *    distribution.
 * 
 * If you are interested in using Form Processing API to benefit a commercial project,
 * you must contact Ilirjan Ostrovica <iostro@sympatico.ca>, for a commercial
 * license.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package org.metaz.gui.portal.forms;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

 /**
 * This class represents a set of data input elements.
 * A subclass must provide implementations for it's abstract methods.
 *
 * @see FormElement
 *
 * @version 	1.0 2001/02/27
 * @author	Ilirjan Ostrovica
 */
 
public abstract class Form {
	
  /**
   * This method handles the processing of all field values and generation of error messages.
   * When the processing is successful it returns <code>true</code>, or <code>false</code> 
   * otherwise.
   *
   * @param   req   the HttpServletRequest object that contains the client's request.
   * @return    <code>true</code> if processing is successful, or <code>false</code> otherwise.
   */
	public boolean process(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		FormElement[] formElements = (FormElement[])session.getAttribute(getFormName());
		if (formElements == null) { // first time
			formElements = getFormElements();
			bound(session, formElements);
			return false;
		}
		return validate(req, formElements);
	}
	
  /**
   * This method unbounds from the session, all the attributes related
   * to this form.
   *
   * @param   req   the HttpServletRequest object that contains the client's request.
   */
	public void discard(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) throw new IllegalStateException("No session exists");
		
		FormElement[] formElements = (FormElement[])session.getAttribute(getFormName());
		if (formElements == null) throw new IllegalStateException("No form data in session");

		unbound(session, formElements);
	}

  /**
   * Gets the name of this <code>Form</code> object.
   * A subclass must provide an implementation of this method.
   *
   * @return    the name of this <code>Form</code> object.
   */
	public abstract String getFormName();
	
  /**
   * Gets an array of all <code>FormElement</code> objects that
   * belong to this form.
   * A subclass must provide an implementation of this method.
   *
   * @return    the array of <code>FormElement</code>-s.
   */
	public abstract FormElement[] getFormElements();


	private boolean validate (HttpServletRequest req, FormElement[] formElements) {
		if (! req.getParameterNames().hasMoreElements() )	return false;	
		
		boolean toReturn = true;
		PasswordBox firstPassword = null;
		PasswordBox secondPassword = null;
		
		String[] val = null;
		String errorMess = null;
		
		for (int i=0;i<formElements.length;i++) {
			FormElement fe = formElements[i];
			fe.clear();
			val = req.getParameterValues(fe.getName());
			if (val != null) {
				for ( int j = 0;j<val.length;j++) fe.setValue(val[j]);	
			}
			if (fe instanceof PasswordBox) {
				if (firstPassword == null) firstPassword = (PasswordBox)fe;
				else { // fe is the second password
					secondPassword = (PasswordBox)fe;
					if ( !(firstPassword.getValue()).equals(secondPassword.getValue()) ) {
						firstPassword.putEqualsMessage();
						secondPassword.putEqualsMessage();
						toReturn = false;
						continue;
					}
				}
			}
			errorMess = fe.getError();
			if (! errorMess.equals("")) toReturn = false;
			fe.setErrorMessage(errorMess);
		}
		return toReturn;
	}
	
	private void bound(HttpSession session, FormElement[] formElements) {
		for (int i=0;i<formElements.length;i++) {
			session.setAttribute(formElements[i].getName(), formElements[i]);
		}
		session.setAttribute(getFormName(), formElements);
	}
	
	private void unbound(HttpSession session, FormElement[] formElements) {
		for (int i=0;i<formElements.length;i++) {
			session.removeAttribute(formElements[i].getName());
		}
		session.removeAttribute(getFormName());
	}
}