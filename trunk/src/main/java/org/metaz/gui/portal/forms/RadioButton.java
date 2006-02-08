/*
 * @(#)RadioButton.java	1.0 2001/02/27
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
 * This class extends FormElement and represents the radiobutton field.
 * The instantiation and initialization of a RadioButton object looks like this :
 * <p><blockquote><pre>
 *
 *  RadioButton pet = new RadioButton();
 *  pet.setName("pet");
 *  pet.setRequired(true);
 *    
 * </pre></blockquote><p>
 *
 * With a JSP, the HTML representation of the corresponding field looks like:
 *
 * <p><blockquote><pre>
 * &lt;jsp:useBean id="pet" class="iostrovica.form.RadioButton" scope="session" />
 * Pet:
 * &lt;input type="radio"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="dog"  &lt;%= pet.chosen("dog") %>  >dog
 * &lt;input type="radio"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="cat"  &lt;%= pet.chosen("cat") %>  >cat
 * &lt;input type="radio"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="bird" &lt;%= pet.chosen("bird") %> >bird
 * &lt;sub>&lt;font color="red">&lt;jsp:getProperty name="pet" property="errorMessage"/>&lt;/font>&lt;/sub>
 * </pre></blockquote><p>
 *
 * @see FormElement
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public class RadioButton  extends FormElement {
	
	String getError()	{
		if ( isRequired() && getValue() == null ) return getErrorMessageForRequired();
		return "";
	}
	
  /**
   * Will return "checked" or "".
	 * In case the choice represented by <code>value</code> is unchecked, the empty string
	 * will be returned. 
	 *
   * @param   value   the value of the RadioButton item whose status is returned by this method.
   * @return    "checked" or "".
   */
	public String chosen(String value) {
    if ( value == null ) return "";
    if (value.equals(getValue()) ) return "checked";
		return "";
	}

}