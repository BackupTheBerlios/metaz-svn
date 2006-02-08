/*
 * @(#)PasswordBox.java	1.0 2001/02/27
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
 * This class extends TextBox and represents the password field. 
 * 
 * @see FormElement
 * @see TextBox
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public class PasswordBox extends TextBox {
	
  private String messageForPassword = "(passwords must be the same)";

	public PasswordBox() {
		setRequired(true);
	}

  /**
   * Sets the message which informs the user that the password fields
   * were not equal.
   * The default message is <code>"(passwords must be the same)"</code>
   *
   * @param   messageForRequired   the message.
   */
	public void setMessageForPassword(String messageForPassword) {
		this.messageForPassword = messageForPassword;
	}
	
	String getMessageForPassword() {
		return messageForPassword;
	}
	
	void putEqualsMessage() {
		setErrorMessage(messageForPassword);
	}
}