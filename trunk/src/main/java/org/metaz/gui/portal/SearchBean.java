// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

public class SearchBean {

  public final static String SEARCH_PAGE = "search.jsp";
  public final static String ADVANCED_SEARCH_PAGE = "advancedsearch.jsp";
  public final static String RESULTS_PAGE = "searchresults.jsp";

  private static Logger logger = Logger.getLogger(SearchBean.class); // logger instance for this class

  // internal representation of "select" options
  
  private ArrayList <SelectOption> targetEndUserOptions;
  private ArrayList <SelectOption> schoolTypeOptions;
  private ArrayList <SelectOption> schoolDisciplineOptions;
  private ArrayList <SelectOption> didacticFunctionOptions;
  private ArrayList <SelectOption> productTypeOptions;
  private ArrayList <SelectOption> professionalSituationOptions;
  private ArrayList <SelectOption> competenceOptions;
  
  // text fields
  
  private String keywords;
  
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

  // search

  public void search(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException
  {
  
    processPost(req);
    
    if (validate())
      forward(req, res, RESULTS_PAGE);
    else
      forward(req, res, SEARCH_PAGE);
    
  }

  // advanced search

  public void advancedSearch(HttpServletRequest req, HttpServletResponse res) 
        throws ServletException, IOException
  {
  
   processPost(req);
   
   if (validate())
     forward(req, res, RESULTS_PAGE);
   else
     forward(req, res, ADVANCED_SEARCH_PAGE);
   
  }
  
  // redirect ... does a roundtrip to the client... client will request
  // and see the new URL
  // flow WILL return to caller!

  private void redirect(HttpServletResponse res, String page) 
          throws IOException {
      
      String urlWithSessionID = res.encodeRedirectURL(page);
      res.sendRedirect( urlWithSessionID );
      
  }
  
  // forward... server interal... client sees original URL
  // flow WILL return to caller!

  private void forward(HttpServletRequest req,  HttpServletResponse res, String page) 
          throws ServletException, IOException {
    
    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
    dispatcher.forward(req, res);
    
  }
  
  // process post

  public void processPost(HttpServletRequest req) 
  {

    System.out.println(req.getParameterMap());
    System.out.println(req.getParameter("keywords"));

    // validation goes here...
    // return false if some error occurs
    // set errorMessage to indicate error type
    
  }

  // validate

  public boolean validate() 
  {

    // validation goes here...
    // return false if some error occurs
    // set errorMessage to indicate error type

    return true;
    
  }

  private void populateTargetEndUserOptions() {
  
    targetEndUserOptions = new ArrayList <SelectOption> ();
    targetEndUserOptions.add(new SelectOption("*", "Alles"));
    targetEndUserOptions.add(new SelectOption("Docent"));
    targetEndUserOptions.add(new SelectOption("Begeleider"));
    targetEndUserOptions.add(new SelectOption("Manager"));
    
  }

  private void populateSchoolTypeOptions() {
  
    schoolTypeOptions = new ArrayList <SelectOption> ();
    schoolTypeOptions.add(new SelectOption("*", "Alles"));
    schoolTypeOptions.add(new SelectOption("VMBO"));
    schoolTypeOptions.add(new SelectOption("HAVO"));
    schoolTypeOptions.add(new SelectOption("VWO"));
    
  }

  private void populateSchoolDisciplineOptions() {
  
    schoolDisciplineOptions = new ArrayList <SelectOption> ();
    schoolDisciplineOptions.add(new SelectOption("*", "Alles"));
    schoolDisciplineOptions.add(new SelectOption("Rekenen"));
    schoolDisciplineOptions.add(new SelectOption("Lezen"));
    schoolDisciplineOptions.add(new SelectOption("Schrijven"));
    
  }

  private void populateDidacticFunctionOptions() {
  
    didacticFunctionOptions = new ArrayList <SelectOption> ();
    didacticFunctionOptions.add(new SelectOption("*", "Alles"));
    didacticFunctionOptions.add(new SelectOption("Oefening"));
    didacticFunctionOptions.add(new SelectOption("Simulatie"));
    didacticFunctionOptions.add(new SelectOption("Vragenlijst"));
    
  }

  private void populateProductTypeOptions() {
  
    productTypeOptions = new ArrayList <SelectOption> ();
    productTypeOptions.add(new SelectOption("*", "Alles"));
    productTypeOptions.add(new SelectOption("Document"));
    productTypeOptions.add(new SelectOption("Afbeelding"));
    productTypeOptions.add(new SelectOption("Video"));
    
  }

  private void populateProfessionalSituationOptions() {
  
    professionalSituationOptions = new ArrayList <SelectOption> ();
    professionalSituationOptions.add(new SelectOption("*", "Alles"));
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
    competenceOptions.add(new SelectOption("*", "Alles"));
    competenceOptions.add(new SelectOption("Interpersoonlijk"));
    competenceOptions.add(new SelectOption("Pedagogisch"));
    competenceOptions.add(new SelectOption("Didactisch"));
    
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

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getKeywords() {
    return keywords;
  }

  public String getHtmlEscapedKeywords() {
    return PortalUtil.htmlEscape(keywords);
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
