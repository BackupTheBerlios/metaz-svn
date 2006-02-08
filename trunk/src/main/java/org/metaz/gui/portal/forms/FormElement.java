/*
 * @(#)FormElement.java	1.0 2001/02/27
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

 /**
 * This abstract class is the superclass of all classes which represent
 * data input elements.
 * A FormElement object's life cycle is to be viewed as bounded to the HttpSession.
 *
 * @see ChoiceBox
 * @see TextBox
 * @see MenuBox
 * @see RadioButton
 * @see CheckBox
 * @see PasswordBox
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public abstract class FormElement {

	private String name;
	private String value;
	private String errorMessage;
  private boolean required;
  private boolean firstime = true;
	private String messageForRequired = "(required)";
	private String errorMessageForRequired = "(required field)";

  /**
   * Sets the initial value of this element.
   * By default this value is set to an empty string.
   * <p>
   * ChoiceBox subclass overrides this method. 
   *
   * @param     value   the initial value for this element.
   */
	public void setValue(String value)	{
		this.value = value.trim();
	}
	
  /**
   * Sets the name of this element. This name is used for bounding
   * this FormElement object to the HttpSession, as well as for naming
   * this field in it's graphical presentation (which is the name of
   * the corresponding request parameter).
   *
   * @param     name   the name of this element.
   */
	public void setName(String name) {
		this.name = name;
	}
	
  /**
   * Sets the <code>required</code> boolean state of this element. The value <code>true</code>
   * indicates that user input is required. Otherwise an error message 
   * will be generated, and the form will be returned to the client.
   * The default value is <code>false</code> 
   *
   * @param     required   the new required state for this element.
   */
  public void setRequired(boolean required) {
	 this.required = required;
  }
  	
  void setFirstime(boolean firstime) {
	 this.firstime = firstime;
  }	

  void setErrorMessage(String errorMessage) {
	 this.errorMessage = errorMessage;
  }	

  /**
   * Sets the message which lets the client know that this field is required.
   * The default value is <code>"(required)"</code>
   *
   * @param   messageForRequired   the message.
   */
	public void setMessageForRequired(String messageForRequired) {
		this.messageForRequired = messageForRequired;
	}
	
  /**
   * Sets the error message which lets the client know that he should have provided
   * some input for this element. 
   * By default this message is set to <code>"(required field)"</code>
   *
   * @param   errorMessageForRequired   the error message.
   */
	public void setErrorMessageForRequired(String errorMessageForRequired) {
		this.errorMessageForRequired = errorMessageForRequired;
	}
	
  /**
   * Gets the name of this element. This name is used for bounding
   * this FormElement object to the HttpSession, as well as the name
   * of the corresponding request parameter.
   * 
   * @return    the name.
   */
	public String getName() {
		return name;
	}
	
  /**
   * Gets the value of this element.
	 *
   * @return    the value.
   */
	public String getValue() {
		return value;
	}

	boolean isRequired() {
		return required;
	}
	
	String getMessageForRequired() {
		return messageForRequired;
	}
	
	String getErrorMessageForRequired() {
		return errorMessageForRequired;
	}

  /**
   * Gets the error message generated by this object.
   * This error message displays what was wrong with the client's last input.
	 *
   * @return    the error message.
   */
	public String getErrorMessage() {
    if (firstime)	{
			firstime = false;
			if (isRequired())	return getMessageForRequired();
			else return "";
		}
		return errorMessage;
	}

	abstract String getError();

	void clear() {};
}