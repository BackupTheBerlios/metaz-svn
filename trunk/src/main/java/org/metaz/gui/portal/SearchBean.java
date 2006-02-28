// @author: Falco Paul
package org.metaz.gui.portal;

import org.apache.log4j.Logger;

import org.metaz.domain.Record;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SearchBean {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  public final static String SEARCH_PAGE = "search.jsp";
  public final static String ADVANCED_SEARCH_PAGE = "advancedsearch.jsp";
  public final static String RESULTS_PAGE = "searchresults.jsp";
  private static Logger      logger = Logger.getLogger(SearchBean.class); // logger instance for this class

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // current available result list
  private ResultList resultList;

  // current available record details
  private RecordDetailsList recordDetailsList;

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

  //~ Constructors -----------------------------------------------------------------------------------------------------

  public SearchBean() {

    resultList = new ResultList();
    recordDetailsList = new RecordDetailsList();

    // anything we need for initialization goes here...
    demoResultList();
    demoRecordDetailsList();

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

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param req DOCUMENT ME!
   * @param res DOCUMENT ME!
   *
   * @throws ServletException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public void search(HttpServletRequest req, HttpServletResponse res)
              throws ServletException,
                     IOException
  {

    processPost(req);

    if (validate())
      forward(req, res, RESULTS_PAGE);
    else
      forward(req, res, SEARCH_PAGE);

  }

  // advanced search
  /**
   * DOCUMENT ME!
   *
   * @param req DOCUMENT ME!
   * @param res DOCUMENT ME!
   *
   * @throws ServletException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public void advancedSearch(HttpServletRequest req, HttpServletResponse res)
                      throws ServletException,
                             IOException
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
                 throws IOException
  {

    String urlWithSessionID = res.encodeRedirectURL(page);

    res.sendRedirect(urlWithSessionID);

  }

  // forward... server interal... client sees original URL
  // flow WILL return to caller!
  private void forward(HttpServletRequest req, HttpServletResponse res, String page)
                throws ServletException,
                       IOException
  {

    RequestDispatcher dispatcher = req.getRequestDispatcher(page);

    dispatcher.forward(req, res);

  }

  // process post
  /**
   * DOCUMENT ME!
   *
   * @param req DOCUMENT ME!
   */
  public void processPost(HttpServletRequest req) {

    // Print all form variables
    System.out.println(req.getParameterMap());

    // Example processing of SELECT ONE... targetEndUser
    String       formValue = req.getParameter("targetEndUser");
    SelectOption selectedTargetEndUser = targetEndUserOptions.getOption(formValue);

    if (selectedTargetEndUser == null)
      System.out.println("Could not establish target end user");

    else {

      // Make the interface user friendly... remember the last value!
      // Single select:
      // - First make sure none of the others options are selected...
      // - Then mark the matched option as selected
      targetEndUserOptions.setSelected(false);
      selectedTargetEndUser.setSelected(true);

      System.out.println(selectedTargetEndUser);

    }

    // Example processing SELECT MULTIPLE... product type
    String[]         formValues = req.getParameterValues("productType");
    SelectOptionList selectedProductTypes = productTypeOptions.getOptions(formValues);

    if (selectedProductTypes == null)
      System.out.println("Could not establish product type");

    else {

      // Make the interface user friendly... remember the last value!
      // Multiple select:
      // - First make sure none of the others options are selected...
      // - Then mark all user selected option as selected
      productTypeOptions.setSelected(false);
      selectedProductTypes.setSelected(true);

      System.out.println(selectedProductTypes);

    }

    // Example processing INPUTTEXT... keywords
    formValue = req.getParameter("keywords");
    System.out.println(formValue);

  }

  // validate
  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean validate() {

    // validation goes here...
    // return false if some error occurs
    // set errorMessage to indicate error type
    return true;

  }

  /**
   * DOCUMENT ME!
   *
   * @param logger DOCUMENT ME!
   */
  public void setLogger(Logger logger) {

    this.logger = logger;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Logger getLogger() {

    return logger;

  }

  /**
   * DOCUMENT ME!
   *
   * @param resultList DOCUMENT ME!
   */
  public void setResultList(ResultList resultList) {

    this.resultList = resultList;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public ResultList getResultList() {

    return resultList;

  }

  /**
   * DOCUMENT ME!
   *
   * @param recordDetailsList DOCUMENT ME!
   */
  public void setRecordDetailsList(RecordDetailsList recordDetailsList) {

    this.recordDetailsList = recordDetailsList;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public RecordDetailsList getRecordDetailsList() {

    return recordDetailsList;

  }

  /**
   * DOCUMENT ME!
   *
   * @param targetEndUserOptions DOCUMENT ME!
   */
  public void setTargetEndUserOptions(SelectOptionList targetEndUserOptions) {

    this.targetEndUserOptions = targetEndUserOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getTargetEndUserOptions() {

    return targetEndUserOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param schoolTypeOptions DOCUMENT ME!
   */
  public void setSchoolTypeOptions(SelectOptionList schoolTypeOptions) {

    this.schoolTypeOptions = schoolTypeOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getSchoolTypeOptions() {

    return schoolTypeOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param schoolDisciplineOptions DOCUMENT ME!
   */
  public void setSchoolDisciplineOptions(SelectOptionList schoolDisciplineOptions) {

    this.schoolDisciplineOptions = schoolDisciplineOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getSchoolDisciplineOptions() {

    return schoolDisciplineOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param didacticFunctionOptions DOCUMENT ME!
   */
  public void setDidacticFunctionOptions(SelectOptionList didacticFunctionOptions) {

    this.didacticFunctionOptions = didacticFunctionOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getDidacticFunctionOptions() {

    return didacticFunctionOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param productTypeOptions DOCUMENT ME!
   */
  public void setProductTypeOptions(SelectOptionList productTypeOptions) {

    this.productTypeOptions = productTypeOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getProductTypeOptions() {

    return productTypeOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param professionalSituationOptions DOCUMENT ME!
   */
  public void setProfessionalSituationOptions(SelectOptionList professionalSituationOptions) {

    this.professionalSituationOptions = professionalSituationOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getProfessionalSituationOptions() {

    return professionalSituationOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param competenceOptions DOCUMENT ME!
   */
  public void setCompetenceOptions(SelectOptionList competenceOptions) {

    this.competenceOptions = competenceOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public SelectOptionList getCompetenceOptions() {

    return competenceOptions;

  }

  /**
   * DOCUMENT ME!
   *
   * @param keywords DOCUMENT ME!
   */
  public void setKeywords(String keywords) {

    this.keywords = keywords;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getKeywords() {

    return keywords;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getHtmlEscapedKeywords() {

    return PortalUtil.htmlEscape(getKeywords());

  }

  /**
   * DOCUMENT ME!
   *
   * @param errorMessage DOCUMENT ME!
   */
  public void setErrorMessage(String errorMessage) {

    this.errorMessage = errorMessage;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getErrorMessage() {

    return errorMessage;

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getHtmlEscapedErrorMessage() {

    return PortalUtil.htmlEscape(getErrorMessage());

  }

  // just some fake data here to demonstrate usage
  private void demoResultList() {

    resultList = new ResultList();

    // some example dummy records to see the displaytag library in action
    resultList.add(new Result("Object 01", "Video", "Het nieuwe leren"));
    resultList.add(new Result("Object 02", "Video", "Rekenen"));
    resultList.add(new Result("Object 03", "Video", "De nieuwe universiteit"));
    resultList.add(new Result("Object 04", "Video", "De schoolorganisatie"));
    resultList.add(new Result("Object 05", "Video", "Aardrijkskunde"));
    resultList.add(new Result("Object 06", "Picture", "Biologie 1"));
    resultList.add(new Result("Object 07", "Picture", "Op afstand leren"));
    resultList.add(new Result("Object 08", "Picture", "Leren is weten, weten is leren"));
    resultList.add(new Result("Object 09", "Picture", "De meester weet het beter"));
    resultList.add(new Result("Object 10", "Word document", "Wiskunde voor de wereld"));
    resultList.add(new Result("Object 11", "Word document", "Rekenonderwijs"));
    resultList.add(new Result("Object 12", "Word document", "Orde houden in de klas"));

  }

  // just some fake data here to demonstrate usage
  private void demoRecordDetailsList() {

    recordDetailsList = new RecordDetailsList(new Record());

  }

  private void populateTargetEndUserOptions() {

    targetEndUserOptions = new SelectOptionList();
    targetEndUserOptions.add(new SelectOption(true, "*", "Alles"));
    targetEndUserOptions.add(new SelectOption("Docent"));
    targetEndUserOptions.add(new SelectOption("Begeleider"));
    targetEndUserOptions.add(new SelectOption("Manager"));

  }

  private void populateSchoolTypeOptions() {

    schoolTypeOptions = new SelectOptionList();
    schoolTypeOptions.add(new SelectOption(true, "*", "Alles"));
    schoolTypeOptions.add(new SelectOption("VMBO"));
    schoolTypeOptions.add(new SelectOption("HAVO"));
    schoolTypeOptions.add(new SelectOption("VWO"));

  }

  private void populateSchoolDisciplineOptions() {

    schoolDisciplineOptions = new SelectOptionList();
    schoolDisciplineOptions.add(new SelectOption(true, "*", "Alles"));
    schoolDisciplineOptions.add(new SelectOption("Rekenen"));
    schoolDisciplineOptions.add(new SelectOption("Lezen"));
    schoolDisciplineOptions.add(new SelectOption("Schrijven"));

  }

  private void populateDidacticFunctionOptions() {

    didacticFunctionOptions = new SelectOptionList();
    didacticFunctionOptions.add(new SelectOption(true, "*", "Alles"));
    didacticFunctionOptions.add(new SelectOption("Oefening"));
    didacticFunctionOptions.add(new SelectOption("Simulatie"));
    didacticFunctionOptions.add(new SelectOption("Vragenlijst"));

  }

  private void populateProductTypeOptions() {

    productTypeOptions = new SelectOptionList();
    productTypeOptions.add(new SelectOption(true, "Document"));
    productTypeOptions.add(new SelectOption("Afbeelding"));
    productTypeOptions.add(new SelectOption("Video"));

  }

  private void populateProfessionalSituationOptions() {

    professionalSituationOptions = new SelectOptionList();
    professionalSituationOptions.add(new SelectOption(true, "Groep: omgaan met een grote groep"));
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

    competenceOptions = new SelectOptionList();
    competenceOptions.add(new SelectOption(true, "Interpersoonlijk"));
    competenceOptions.add(new SelectOption("Pedagogisch"));
    competenceOptions.add(new SelectOption("Didactisch"));

  }

}
