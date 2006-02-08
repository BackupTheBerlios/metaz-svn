/*
 * @(#)TextBox.java	1.0 2001/02/27
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

import java.util.Vector;

 /**
 * This class represents textfields and textareas, as well as serves as 
 * the superclass of PasswordBox class. 
 * The instantiation and initialization of a TextBox object looks like this:
 * <p><blockquote><pre>
 *
 *  TextBox myTextBox = new TextBox();
 *  myTextBox.setName("myTextBox");
 *  myTextBox.setRequired(true);
 *  myTextBox.addFieldController(aFieldController);
 *  myTextBox.addFieldController(anotherFieldController);
 *
 * </pre></blockquote><p>
 *
 * With a JSP, the HTML representation of the corresponding field looks like:
 *
 * <p><blockquote><pre>
 *
 *  &lt;jsp:useBean id="myTextBox" class="iostrovica.form.TextBox" scope="session" />
 *  &lt;input type="text"
 *   name="&lt;jsp:getProperty name="myTextBox" property="name"/>"
 *   value="&lt;%= myTextBox.getValue() %>">
 *  &lt;sub>&lt;font color="red">&lt;jsp:getProperty name="myTextBox" property="errorMessage"/>&lt;/font>&lt;/sub>
 *
 * </pre></blockquote><p>
 *
 * @see FormElement
 * @see FieldController
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public class TextBox extends FormElement {
	
	private Vector fieldControlls;
	
	public TextBox() {
		setValue("");
	}
	
  /**
   * Adds a FieldController object to the list of controllers already registered with this TextBox.
   * No adding will take place in case <code>fieldController</code> is already registered.
   *
   * @param    fieldController   FieldController object to be registered with this TextBox.
   */
	public void addFieldController(FieldController fieldController) {
		if (fieldControlls == null) fieldControlls = new Vector();
  	if (fieldControlls.contains(fieldController)) return;
    fieldControlls.addElement(fieldController);
	}
	
  /**
   * Sets the FieldController objects of this TextBox.
   * This method overrides any preexisting list of FieldController objects
   * already registered with this TextBox.
   * However more FieldController objects can be added by using <code>addFieldController()</code> method.
   *
   * @param    fieldControlls   array of FieldController objects to be registered with this TextBox.
   */
	public void setFieldControlls(FieldController[] fieldControlls) {
		this.fieldControlls = new Vector();
		for (int i=0;i<fieldControlls.length;i++) this.fieldControlls.addElement(fieldControlls[i]);
	}

	String getError()	{
		if ( isRequired() && getValue().equals("") ) return getErrorMessageForRequired();
 		String err = getErrMess();
		if ( !err.equals("") ) return err;
		return "";
	}

	private String getErrMess() {
		if ( getValue().equals("") && !isRequired() ) return "";
		if (fieldControlls != null) {
			FieldController fc = null;
			String err = null;
			for (int i=0;i<fieldControlls.size();i++) {
				fc = (FieldController)fieldControlls.elementAt(i);
				err = fc.getErrorMessage(getValue());
				if ( !err.equals("") ) return err;
			}
		}
		return "";
	}
}