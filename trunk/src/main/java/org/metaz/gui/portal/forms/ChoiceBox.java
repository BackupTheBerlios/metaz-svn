/*
 * @(#)ChoiceBox.java	1.0 2001/02/27
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
 * This abstract class is the superclass of classes representing the
 * multiple choice form input elements like Checkboxes, Combo Boxes
 * and List Boxes.
 *
 * @see MenuBox
 * @see CheckBox
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public abstract class ChoiceBox  extends FormElement {
	
	private Vector checkedValues = new Vector();
	private String checkedOrSelected;    
	
  /**
   * Sets the initially selected value of this field.
   * If you want more than one value initially selected, 
   * then you should call this method for each of them.
   *
   * @param     value   the initially selected value.
   */
	public void setValue(String value)	{
		checkedValues.addElement(value);
	}

	void setCheckedOrSelected(String checkedOrSelected)	{
		this.checkedOrSelected = checkedOrSelected;
	}
	
	String getCheckedOrSelected() {
		return checkedOrSelected;
	}
	
	String getError()	{
		if ( isRequired() && checkedValues.size() == 0 ) return getErrorMessageForRequired();
		return "";
	}

  /**
   * Will return "selected", "checked" or "".
	 * In case the choice represented by <code>value</code> is unselected, the empty string
	 * will be returned. 
	 *
   * @param   value   the value of the ChoiceBox item whose status is returned by this method.
   * @return    "selected", "checked" or "".
   */
	public String chosen(String value) { 
		for ( int i = 0;i<checkedValues.size();i++ ) { // looping through all selected values
			String checkedValue = (String)checkedValues.elementAt(i);
			if ( checkedValue.equals(value) ) return getCheckedOrSelected();
		}
		return "";
	}
	
	void clear() {
		checkedValues = new Vector();
	}

}