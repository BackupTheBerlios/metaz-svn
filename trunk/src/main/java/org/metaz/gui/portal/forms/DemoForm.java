package org.metaz.gui.portal.forms;

/**
* This is an example of a class that extends Form by providing
* all the data needed to construct a form.
*/
public class DemoForm extends Form {

   public DemoForm() {
   }
   	   
   public String getFormName() {
      return "thisform";
   }

   public FormElement[] getFormElements() {
   	
      TextBox login = new TextBox();
      login.setName("login");
      login.setRequired(true);
			
      PasswordBox password = new PasswordBox();
      password.setName("password");

      PasswordBox passwordAgain = new PasswordBox();
      passwordAgain.setName("passwordAgain");

      RadioButton gender = new RadioButton();
      gender.setName("gender");
      gender.setRequired(true);

      CheckBox lookingFor = new CheckBox();
      lookingFor.setName("lookingFor");
      lookingFor.setRequired(true);
      lookingFor.setValue("fulltime");

      MenuBox province = new MenuBox();
      province.setName("province");

      TextBox comments = new TextBox();
      comments.setName("comments");
      comments.setValue("no comments");
      
      return new FormElement[]{login, password, passwordAgain, gender, lookingFor, province, comments};
   }
}