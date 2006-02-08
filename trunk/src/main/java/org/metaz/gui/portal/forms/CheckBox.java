package org.metaz.gui.portal.forms;

 /**
 * This class extends ChoiceBox and represents the check boxes.
 * The instantiation and initialization of a CheckBox object looks like this:
 * <p><blockquote><pre>
 *
 *  CheckBox pet = new CheckBox();
 *  pet.setName("pet");
 *  pet.setRequired(true);
 *  pet.setValue("bird"); // "bird" will be initially selected
 *    
 * </pre></blockquote><p>
 *
 * With a JSP, the HTML representation of the corresponding field looks like:
 *
 * <p><blockquote><pre>
 * 
 * &lt;jsp:useBean id="pet" class="iostrovica.form.CheckBox" scope="session" />
 * Pet:
 * &lt;input type="checkbox"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="dog"  &lt;%= pet.chosen("dog") %>  >dog
 * &lt;input type="checkbox"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="cat"  &lt;%= pet.chosen("cat") %>  >cat
 * &lt;input type="checkbox"
 *  name="&lt;jsp:getProperty name="pet" property="name"/>"
 *  value="bird" &lt;%= pet.chosen("bird") %> >bird
 * &lt;sub>&lt;font color="red">&lt;jsp:getProperty name="pet" property="errorMessage"/>&lt;/font>&lt;/sub>
 * </pre></blockquote><p>
 *
 * @see ChoiceBox
 * @see MenuBox
 *
 * @version 	1.0, 2001/02/27
 * @author	Ilirjan Ostrovica
 */

public class CheckBox extends ChoiceBox {
	
	public CheckBox () {
		setCheckedOrSelected("checked");
	}

}