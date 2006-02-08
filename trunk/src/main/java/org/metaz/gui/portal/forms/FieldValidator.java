/*
 * @(#)FieldValidator.java  2.0, 2001/06
 *
 * Copyright (C) 2001 Ilirjan Ostrovica. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
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
 * Defines the interface for classes that will play the role of value validators
 * for com.codepassion.form.TextBox and com.codepassion.form.PasswordBox fields.
 * The criteria of validation will be implemented through
 * <code>getErrorMessage()</code> method of this interface.
 * A class of type TextBox will register one or more FieldValidator objects via
 * <code>addFieldValidator()</code> method.
 *
 * A FieldValidator object will normally be shared by all client threads of
 * one or even more forms, therefore the implementation has to take care of
 * possible synchronization issues.
 *
 * @see TextBox
 * @see PasswordBox
 *
 * @version   2.0, 2001/06
 * @author  Ilirjan Ostrovica
 */
public interface FieldValidator
    extends java.io.Serializable
{

  /**
   * Gets the error message resulting from the validation.
   * If the supplied value passes the test (or tests) implemented by this method,
   * an empty string must be returned.
   *
   * @param   value   the value to be tested.
   * @return    the error message or an empty string in case the value has successfully passed the test.
   */
  public String getErrorMessage(String value);

}