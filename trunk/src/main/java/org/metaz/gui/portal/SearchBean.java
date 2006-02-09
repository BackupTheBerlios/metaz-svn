// @author: Falco Paul

package org.metaz.gui.portal;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

public class SearchBean {

  private static Logger logger = Logger.getLogger(SearchBean.class); // logger instance for this class
  
  // internal representation of "select" options
  
   private ArrayList <SelectOption> targetEndUserOptions;
   private ArrayList <SelectOption> schoolTypeOptions;
   private ArrayList <SelectOption> schoolDisciplineOptions;
   private ArrayList <SelectOption> didacticFunctionOptions;
   private ArrayList <SelectOption> productTypeOptions;
   private ArrayList <SelectOption> professionalSituationOptions;
   private ArrayList <SelectOption> competenceOptions;
  
  // user entry returned from JSP
  
  private String[] targetEndUser;
  private String[] schoolType;
  private String[] schoolDiscipline;
  private String[] didacticFunction;
  private String[] productType;
  private String[] professionalSituation;
  private String[] competence;
  private String   keywords;
  
  // a error message we can return to the user;
  
  private String errorMessage;
  
  public SearchBean() {
  
    // anything we need for initialization goes here...
    
    populateTargetEndUserOptions();
    populateSchoolTypeOptions();
    populateSchoolDisciplineOptions();
    populateDidacticFunctionOptions();
    populateProductTypeOptions();
    populateProfessionalSituationOptions();
    populateCompetenceOptions();
    
    keywords = new String("");
    
    errorMessage = new String("");
    
  }

  private void populateTargetEndUserOptions() {
  
    targetEndUserOptions = new ArrayList <SelectOption> ();
    targetEndUserOptions.add(new SelectOption("Alles"));
    targetEndUserOptions.add(new SelectOption("Docent"));
    targetEndUserOptions.add(new SelectOption("Begeleider"));
    targetEndUserOptions.add(new SelectOption("Manager"));
    
  }

  private void populateSchoolTypeOptions() {
  
    schoolTypeOptions = new ArrayList <SelectOption> ();
    schoolTypeOptions.add(new SelectOption("Alles"));
    schoolTypeOptions.add(new SelectOption("VMBO"));
    schoolTypeOptions.add(new SelectOption("HAVO"));
    schoolTypeOptions.add(new SelectOption("VWO"));
    
  }

  private void populateSchoolDisciplineOptions() {
  
    schoolDisciplineOptions = new ArrayList <SelectOption> ();
    schoolDisciplineOptions.add(new SelectOption("Alles"));
    schoolDisciplineOptions.add(new SelectOption("Rekenen"));
    schoolDisciplineOptions.add(new SelectOption("Lezen"));
    schoolDisciplineOptions.add(new SelectOption("Schrijven"));
    
  }

  private void populateDidacticFunctionOptions() {
  
    didacticFunctionOptions = new ArrayList <SelectOption> ();
    didacticFunctionOptions.add(new SelectOption("Alles"));
    didacticFunctionOptions.add(new SelectOption("Oefening"));
    didacticFunctionOptions.add(new SelectOption("Simulatie"));
    didacticFunctionOptions.add(new SelectOption("Vragenlijst"));
    
  }

  private void populateProductTypeOptions() {
  
    productTypeOptions = new ArrayList <SelectOption> ();
    productTypeOptions.add(new SelectOption("Alles"));
    productTypeOptions.add(new SelectOption("Document"));
    productTypeOptions.add(new SelectOption("Afbeelding"));
    productTypeOptions.add(new SelectOption("Video"));
    
  }

  private void populateProfessionalSituationOptions() {
  
    professionalSituationOptions = new ArrayList <SelectOption> ();
    professionalSituationOptions.add(new SelectOption("Alles"));
    professionalSituationOptions.add(new SelectOption("Groep: omgaan met een grote groep"));
    professionalSituationOptions.add(new SelectOption("Groep: omgaan met een kleine groep"));
    professionalSituationOptions.add(new SelectOption("Groep: orde handhaven"));
    professionalSituationOptions.add(new SelectOption("Lessen: voorbereiden van een les"));
    professionalSituationOptions.add(new SelectOption("Lessen: start van de les"));
    professionalSituationOptions.add(new SelectOption("Lessen: uitvoering"));
    professionalSituationOptions.add(new SelectOption("Opvoeden op school: schoolregels"));
    professionalSituationOptions.add(new SelectOption("Opvoeden op school: toezicht in de gangen en pauzes"));
    professionalSituationOptions.add(new SelectOption("Opvoeden op school: veiligheid op school"));
    
  }

  private void populateCompetenceOptions() {
  
    competenceOptions = new ArrayList <SelectOption> ();
    competenceOptions.add(new SelectOption("Alles"));
    competenceOptions.add(new SelectOption("Interpersoonlijk"));
    competenceOptions.add(new SelectOption("Pedagogisch"));
    competenceOptions.add(new SelectOption("Didactisch"));
    
  }
  
  // process

  public boolean process() 
  {
  
    // validation goes here...
    // return false if some error occurs
    // set errorMessage to indicate error type

    return true;
    
  }


  public void setTargetEndUserOptions(ArrayList<SelectOption> targetEndUserOptions) {
    this.targetEndUserOptions = targetEndUserOptions;
  }

  public ArrayList<SelectOption> getTargetEndUserOptions() {
    return targetEndUserOptions;
  }

  public void setSchoolTypeOptions(ArrayList<SelectOption> schoolTypeOptions) {
    this.schoolTypeOptions = schoolTypeOptions;
  }

  public ArrayList<SelectOption> getSchoolTypeOptions() {
    return schoolTypeOptions;
  }

  public void setSchoolDisciplineOptions(ArrayList<SelectOption> schoolDisciplineOptions) {
    this.schoolDisciplineOptions = schoolDisciplineOptions;
  }

  public ArrayList<SelectOption> getSchoolDisciplineOptions() {
    return schoolDisciplineOptions;
  }

  public void setDidacticFunctionOptions(ArrayList<SelectOption> didacticFunctionOptions) {
    this.didacticFunctionOptions = didacticFunctionOptions;
  }

  public ArrayList<SelectOption> getDidacticFunctionOptions() {
    return didacticFunctionOptions;
  }

  public void setProductTypeOptions(ArrayList<SelectOption> productTypeOptions) {
    this.productTypeOptions = productTypeOptions;
  }

  public ArrayList<SelectOption> getProductTypeOptions() {
    return productTypeOptions;
  }

  public void setProfessionalSituationOptions(ArrayList<SelectOption> professionalSituationOptions) {
    this.professionalSituationOptions = professionalSituationOptions;
  }

  public ArrayList<SelectOption> getProfessionalSituationOptions() {
    return professionalSituationOptions;
  }

  public void setCompetenceOptions(ArrayList<SelectOption> competenceOptions) {
    this.competenceOptions = competenceOptions;
  }

  public ArrayList<SelectOption> getCompetenceOptions() {
    return competenceOptions;
  }

  public void setTargetEndUser(String[] targetEndUser) {
    this.targetEndUser = targetEndUser;
  }

  public String[] getTargetEndUser() {
    return targetEndUser;
  }

  public void setSchoolType(String[] schoolType) {
    this.schoolType = schoolType;
  }

  public String[] getSchoolType() {
    return schoolType;
  }

  public void setSchoolDiscipline(String[] schoolDiscipline) {
    this.schoolDiscipline = schoolDiscipline;
  }

  public String[] getSchoolDiscipline() {
    return schoolDiscipline;
  }

  public void setDidacticFunction(String[] didacticFunction) {
    this.didacticFunction = didacticFunction;
  }

  public String[] getDidacticFunction() {
    return didacticFunction;
  }

  public void setProfessionalSituation(String[] professionalSituation) {
    this.professionalSituation = professionalSituation;
  }

  public String[] getProfessionalSituation() {
    return professionalSituation;
  }

  public void setCompetence(String[] competence) {
    this.competence = competence;
  }

  public String[] getCompetence() {
    return competence;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
