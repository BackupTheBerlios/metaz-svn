package org.metaz.gui.portal.forms;

/**
* This is an example of a servlet that extends FormServlet by providing
* all the data needed to process a specific form.
* Calling this servlet will display the form page entered with getFormPage() method.
*/
public class DemoServlet extends FormServlet{

   public Form getForm() {
      return new DemoForm();
   }
	
   public String getFormPage() {
      return "/jsp/forms/form.jsp";
   }
	
   public String getWelcomePage() {
      return "/jsp/forms/welcome.jsp";
   }
}