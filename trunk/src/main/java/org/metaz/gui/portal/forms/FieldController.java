/*
 * @(#)FieldController.java	1.0 2001/02/27
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
 * Defines the interface for classes that will play the role of value controllers
 * for TextBox fields.
 * The criteria(s) of controll will be implemented through 
 * <code>getErrorMessage()</code> method of this interface.
 * A class of type TextBox will register one or more FieldController objects via 
 * <code>addFieldController()</code> or <code>setFieldControlls()</code> methods.
 * <p>
 * A FieldController object will normally be shared by all client threads,
 * or by the whole application. Therefore you have to take care of the
 * possible synchronization issues.
 *
 * @see TextBox
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public interface FieldController {

  /**
   * Gets the error message that results from the test(s) implemented
   * by this method.
   * If the supplied value passes that test(s), an empty string must be 
   * returned.
	 *
   * @param   value   the value to be tested.
   * @return    the error message or an empty string in case the value has successfully passed the test.
   */
	public String getErrorMessage(String value);
	
}
	