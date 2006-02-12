// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class SearchBean {

  public final static String SEARCH_PAGE = "search.jsp";
  public final static String ADVANCED_SEARCH_PAGE = "advancedsearch.jsp";
  public final static String RESULTS_PAGE = "searchresults.jsp";

  private static Logger logger = Logger.getLogger(SearchBean.class); // logger instance for this class
  
  // example result list
  
  private ResultList resultList;

  // internal representation of "select" options
  
  private SelectOptionList targetEndUserOptions;
  private SelectOptionList schoolTypeOptions;
  private SelectOptionList schoolDisciplineOptions;
  private SelectOptionList didacticFunctionOptions;
  private SelectOptionList productTypeOptions;
  private SelectOptionList professionalSituationOptions;
  private SelectOptionList competenceOptions;
  
  // text fields
  
  private String keywords;
  
  // a error message we can return to the user;
  
  private String errorMessage;
  
  public SearchBean() {
  
    // anything we need for initialization goes here...

    populateResultList();
    
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

    // Print all form variables
    
    System.out.println(req.getParameterMap());
    
    // Example processing... targetEndUser
    
    String userSelection = req.getParameter("targetEndUser");
    SelectOption targetEndUser = targetEndUserOptions.getOption(userSelection);
    
    if (targetEndUser == null)
      System.out.println("Could not establish target end user");
    else
    {
    
      // Smart screens... remember the last value!
      // First make sure none of the others options are selected...
      // Then mark the matched option as selected
      targetEndUserOptions.setSelected(false);
      targetEndUser.setSelected(true);
      
      System.out.println(targetEndUser);
    }
    
    // Another example... keywords
    System.out.println(req.getParameter("keywords"));
    
  }

  // validate

  public boolean validate() 
  {

    // validation goes here...
    // return false if some error occurs
    // set errorMessage to indicate error type

    return true;
    
  }

  private void populateResultList() {
  
    resultList = new ResultList ();
    resultList.add(new Result("Object 1", "Video", "Get into Learning!"));
    resultList.add(new Result("Object 2", "Picture", "The teacher is cool"));
    resultList.add(new Result("Object 3", "Word document", "Why we all love teachers"));
    
  }

  private void populateTargetEndUserOptions() {
  
    targetEndUserOptions = new SelectOptionList ();
    targetEndUserOptions.add(new SelectOption(true, "*", "Alles"));
    targetEndUserOptions.add(new SelectOption("Docent"));
    targetEndUserOptions.add(new SelectOption("Begeleider"));
    targetEndUserOptions.add(new SelectOption("Manager"));
    
  }

  private void populateSchoolTypeOptions() {
  
    schoolTypeOptions = new SelectOptionList ();
    schoolTypeOptions.add(new SelectOption(true, "*", "Alles"));
    schoolTypeOptions.add(new SelectOption("VMBO"));
    schoolTypeOptions.add(new SelectOption("HAVO"));
    schoolTypeOptions.add(new SelectOption("VWO"));
    
  }

  private void populateSchoolDisciplineOptions() {
  
    schoolDisciplineOptions = new SelectOptionList ();
    schoolDisciplineOptions.add(new SelectOption(true, "*", "Alles"));
    schoolDisciplineOptions.add(new SelectOption("Rekenen"));
    schoolDisciplineOptions.add(new SelectOption("Lezen"));
    schoolDisciplineOptions.add(new SelectOption("Schrijven"));
    
  }

  private void populateDidacticFunctionOptions() {
  
    didacticFunctionOptions = new SelectOptionList ();
    didacticFunctionOptions.add(new SelectOption(true, "*", "Alles"));
    didacticFunctionOptions.add(new SelectOption("Oefening"));
    didacticFunctionOptions.add(new SelectOption("Simulatie"));
    didacticFunctionOptions.add(new SelectOption("Vragenlijst"));
    
  }

  private void populateProductTypeOptions() {
  
    productTypeOptions = new SelectOptionList ();
    productTypeOptions.add(new SelectOption(true, "*", "Alles"));
    productTypeOptions.add(new SelectOption("Document"));
    productTypeOptions.add(new SelectOption("Afbeelding"));
    productTypeOptions.add(new SelectOption("Video"));
    
  }

  private void populateProfessionalSituationOptions() {
  
    professionalSituationOptions = new SelectOptionList ();
    professionalSituationOptions.add(new SelectOption(true, "*", "Alles"));
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
  
    competenceOptions = new SelectOptionList ();
    competenceOptions.add(new SelectOption(true, "*", "Alles"));
    competenceOptions.add(new SelectOption("Interpersoonlijk"));
    competenceOptions.add(new SelectOption("Pedagogisch"));
    competenceOptions.add(new SelectOption("Didactisch"));
    
  }

  public void setResultList(ResultList resultList) {
    this.resultList = resultList;
  }

  public ResultList getResultList() {
    return resultList;
  }

  public void setTargetEndUserOptions(SelectOptionList targetEndUserOptions) {
    this.targetEndUserOptions = targetEndUserOptions;
  }

  public SelectOptionList getTargetEndUserOptions() {
    return targetEndUserOptions;
  }

  public void setSchoolTypeOptions(SelectOptionList schoolTypeOptions) {
    this.schoolTypeOptions = schoolTypeOptions;
  }

  public SelectOptionList getSchoolTypeOptions() {
    return schoolTypeOptions;
  }

  public void setSchoolDisciplineOptions(SelectOptionList schoolDisciplineOptions) {
    this.schoolDisciplineOptions = schoolDisciplineOptions;
  }

  public SelectOptionList getSchoolDisciplineOptions() {
    return schoolDisciplineOptions;
  }

  public void setDidacticFunctionOptions(SelectOptionList didacticFunctionOptions) {
    this.didacticFunctionOptions = didacticFunctionOptions;
  }

  public SelectOptionList getDidacticFunctionOptions() {
    return didacticFunctionOptions;
  }

  public void setProductTypeOptions(SelectOptionList productTypeOptions) {
    this.productTypeOptions = productTypeOptions;
  }

  public SelectOptionList getProductTypeOptions() {
    return productTypeOptions;
  }

  public void setProfessionalSituationOptions(SelectOptionList professionalSituationOptions) {
    this.professionalSituationOptions = professionalSituationOptions;
  }

  public SelectOptionList getProfessionalSituationOptions() {
    return professionalSituationOptions;
  }

  public void setCompetenceOptions(SelectOptionList competenceOptions) {
    this.competenceOptions = competenceOptions;
  }

  public SelectOptionList getCompetenceOptions() {
    return competenceOptions;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getKeywords() {
    return keywords;
  }

  public String getHtmlEscapedKeywords() {
    return PortalUtil.htmlEscape(getKeywords());
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
  
  public String getHtmlEscapedErrorMessage() {
    return PortalUtil.htmlEscape(getErrorMessage());
  
  }

}
