// @author: Falco Paul
package org.metaz.gui.portal;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import org.metaz.repository.Facade;
import org.metaz.repository.Result;

import org.metaz.util.MetaZ;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Performs searches into the Metaz search index.
 *
 * @author $author$
 * @version $Revision$
 */
public class SearchBean {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  // ~ Static fields/initializers
  // ---------------------------------------------------------------------------------------
  public static final String SEARCH_BEAN_SESSION_KEY = "searchBean";
  public static final String SEARCH_PAGE = "search.jsp";
  public static final String ERROR_PAGE = "error.jsp";
  public static final String ADVANCED_SEARCH_PAGE = "advancedsearch.jsp";
  public static final String RESULTS_PAGE = "searchresults.jsp";
  private static Logger logger = Logger.getLogger(SearchBean.class); // logger

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // instance
  // for
  // this
  // class

  // ~ Instance fields
  // --------------------------------------------------------------------------------------------------

  // Search results
  private List<Result<Record>> metazResults;
  private Facade               facade;

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

  // ~ Constructors
  // -----------------------------------------------------------------------------------------------------
  /**
   * Constructor
   */
  public SearchBean() {

    facade = MetaZ.getRepositoryFacade();

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

  // ~ Methods
  // ----------------------------------------------------------------------------------------------------------
  /**
   * Retrieves the n'th object from the list, where n is the id specified. Used to show a single record in the
   * details view.
   *
   * @param id the index of the object in the list of searchresults
   *
   * @return the Record indexed by id.
   */
  public Record getRecord(int id) {

    return metazResults.get(id).getObject();

  }

  /**
   * Performs a search by collecting user input and submitting this input to the Repository.
   *
   * @param req the request containing user input.
   * @param res the response on which results will be returned to the user.
   * @param advancedMode DOCUMENT ME!
   *
   * @throws ServletException when a servlet exception occurs.
   * @throws IOException when an I/O exception occurs.
   */
  public void search(HttpServletRequest req, HttpServletResponse res, boolean advancedMode)
              throws ServletException,
                     IOException
  {

    HashMap map = (HashMap) processPost(req);

    if (validate()) {

      Facade f = MetaZ.getRepositoryFacade();

      try {

        List<Result<Record>> l = f.doSearch(map);

        setMetazResults(l);

      } catch (Exception e) {

        logger.error("Exception: " + e.getMessage() + " Forwarding to error page");
        req.setAttribute("error", e);
        forward(req, res, ERROR_PAGE);

      }

      forward(req, res, RESULTS_PAGE);

    } else {

      logger.debug("Not a valid search. Returning to search page");

      if (advancedMode) {

        forward(req, res, SEARCH_PAGE);

      } else {

        forward(req, res, SEARCH_PAGE);

      }

    }

  }

  /**
   *   forward... server interal... client sees original URL
   *   flow WILL return to caller!
   *
   * @param req the request
   * @param res the response
   * @param page the page
   *
   * @throws ServletException a servlet problem occurred
   * @throws IOException an IO exception occurred
   */
  private void forward(HttpServletRequest req, HttpServletResponse res, String page)
                throws ServletException,
                       IOException
  {

    RequestDispatcher dispatcher = req.getRequestDispatcher(page);

    dispatcher.forward(req, res);

  }

  /**
   * Processes the request to retrieve user input. Prepares this input to be sent to Repository.
   *
   * @param req the request
   *
   * @return a map with search items
   */
  public Map processPost(HttpServletRequest req) {

    logger.debug("Getting parameter names and values from request");

    Map metazSearchMap = new HashMap();

    HashMap parameterMap = (HashMap) req.getParameterMap();
    Iterator it = parameterMap.keySet().iterator();

    while (it.hasNext()) {

      String   key = (String) it.next();
      String[] value = (String[]) parameterMap.get(key);
      String   metazSearchValue = null;

      // only work on the value if it isn't empty
      if (! ArrayUtils.isEmpty(value) && ! isEmptyStringArray(value)) {

        if (needsMetazSearchEnabling(key)) {

          metazSearchValue = metazSearchEnableStringArray(value);

        } else {

          metazSearchValue = getPlainSearchString(value);

        }

      }

      logger.debug("Found parameter [" + key + "] with value [" + metazSearchValue + "]");

      if (MetaData.KEYWORDS.equals(key)) {

        key = "";

        if (StringUtils.isBlank(metazSearchValue)) {

          logger.debug("No keywords specified. Ignoring Keyword search.");

          continue;

        }

      }

      if (metazSearchValue != null) {

        metazSearchMap.put(key, metazSearchValue);

      }

    }

    // Example processing of SELECT ONE... targetEndUser
    String       formValue = req.getParameter("targetEndUser");
    SelectOption selectedTargetEndUser = targetEndUserOptions.getOption(formValue);

    if (selectedTargetEndUser != null) {

      // Make the interface user friendly... remember the last value!
      // Single select:
      // - First make sure none of the others options are selected...
      // - Then mark the matched option as selected
      targetEndUserOptions.setSelected(false);
      selectedTargetEndUser.setSelected(true);

    }

    // Example processing SELECT MULTIPLE... product type
    String[]         formValues = req.getParameterValues("productType");
    SelectOptionList selectedProductTypes = productTypeOptions.getOptions(formValues);

    if (selectedProductTypes != null) {

      // Make the interface user friendly... remember the last value!
      // Multiple select:
      // - First make sure none of the others options are selected...
      // - Then mark all user selected option as selected
      productTypeOptions.setSelected(false);
      selectedProductTypes.setSelected(true);

    }

    return metazSearchMap;

  }

  /**
   * Returns true if the String array is empty
   *
   * @param values the string array
   *
   * @return a boolean
   */
  private boolean isEmptyStringArray(String[] values) {

    boolean isEmpty = true;

    for (int i = 0; i < values.length; i++) {

      String value = values[i];

      if (StringUtils.isNotBlank(value)) {

        isEmpty = false;

      }

    }

    return isEmpty;

  }

  /**
   * Transforms a string array to a plain string
   *
   * @param  values the string array
   *
   * @return a plain string
   */
  private String getPlainSearchString(String[] values) {

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < values.length; i++) {

      String value = values[i];

      sb.append(value);

    }

    return sb.toString();

  }

  /**
   * Returns true if the metadata field needs string transformation
   *
   * @param key the field name
   *
   * @return true or false
   */
  private boolean needsMetazSearchEnabling(String key) {

    return (MetaData.TARGETENDUSER.equals(key) || MetaData.SCHOOLTYPE.equals(key) ||
        MetaData.SCHOOLDISCIPLINE.equals(key) ||
        MetaData.DIDACTICFUNCTION.equals(key) || MetaData.PRODUCTTYPE.equals(key) ||
        MetaData.PROFESSIONALSITUATION.equals(key) || MetaData.COMPETENCE.equals(key));

  }

  /**
   * Transforms a string array to a string with all components separated
   * by a percent sign
   *
   * @param value the string array
   *
   * @return a string
   */
  private String metazSearchEnableStringArray(String[] value) {

    if (value == null) {

      return null;

    } else {

      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < value.length; i++) {

        String v = value[i];

        sb.append(v);
        sb.append("%");

      }

      return sb.toString();

    }

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
   * Sets the logger.
   *
   * @param logger the logger
   */
  public void setLogger(Logger logger) {

    this.logger = logger;

  }

  /**
   * Getter
   *
   * @return the logger
   */
  public Logger getLogger() {

    return logger;

  }

  /**
   * Setter.
   *
   * @param targetEndUserOptions the options
   */
  public void setTargetEndUserOptions(SelectOptionList targetEndUserOptions) {

    this.targetEndUserOptions = targetEndUserOptions;

  }

  /**
   * Getter.
   *
   * @return the options
   */
  public SelectOptionList getTargetEndUserOptions() {

    return targetEndUserOptions;

  }

  /**
   * Setter.
   *
   * @param schoolTypeOptions the options
   */
  public void setSchoolTypeOptions(SelectOptionList schoolTypeOptions) {

    this.schoolTypeOptions = schoolTypeOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getSchoolTypeOptions() {

    return schoolTypeOptions;

  }

  /**
   * Setter.
   *
   * @param schoolDisciplineOptions the options
   */
  public void setSchoolDisciplineOptions(SelectOptionList schoolDisciplineOptions) {

    this.schoolDisciplineOptions = schoolDisciplineOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getSchoolDisciplineOptions() {

    return schoolDisciplineOptions;

  }

  /**
   * Setter.
   *
   * @param didacticFunctionOptions the options
   */
  public void setDidacticFunctionOptions(SelectOptionList didacticFunctionOptions) {

    this.didacticFunctionOptions = didacticFunctionOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getDidacticFunctionOptions() {

    return didacticFunctionOptions;

  }

  /**
   * Setter.
   *
   * @param productTypeOptions the options.
   */
  public void setProductTypeOptions(SelectOptionList productTypeOptions) {

    this.productTypeOptions = productTypeOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getProductTypeOptions() {

    return productTypeOptions;

  }

  /**
   * Setter.
   *
   * @param professionalSituationOptions the options.
   */
  public void setProfessionalSituationOptions(SelectOptionList professionalSituationOptions) {

    this.professionalSituationOptions = professionalSituationOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getProfessionalSituationOptions() {

    return professionalSituationOptions;

  }

  /**
   * Setter.
   *
   * @param competenceOptions the options.
   */
  public void setCompetenceOptions(SelectOptionList competenceOptions) {

    this.competenceOptions = competenceOptions;

  }

  /**
   * Getter.
   *
   * @return the options.
   */
  public SelectOptionList getCompetenceOptions() {

    return competenceOptions;

  }

  /**
   * Setter.
   *
   * @param keywords the keywords
   */
  public void setKeywords(String keywords) {

    this.keywords = keywords;

  }

  /**
   * Getter.
   *
   * @return the keywords
   */
  public String getKeywords() {

    return keywords;

  }

  /**
   * Getter.
   *
   * @return escaped keywords
   */
  public String getHtmlEscapedKeywords() {

    return PortalUtil.htmlEscape(getKeywords());

  }

  /**
   * Setter.
   *
   * @param errorMessage the errormessage.
   */
  public void setErrorMessage(String errorMessage) {

    this.errorMessage = errorMessage;

  }

  /**
   * Getter.
   *
   * @return the error message
   */
  public String getErrorMessage() {

    return errorMessage;

  }

  /**
   * Getter.
   *
   * @return the error message
   */
  public String getHtmlEscapedErrorMessage() {

    return PortalUtil.htmlEscape(getErrorMessage());

  }

  /**
   * Transforms a pathlike hierarchical string to a
   * folderlike string containing plus signs instead of the parent levels
   *
   * @param value the pathlike string
   *
   * @return a folderlike string
   */
  private String displayHierarchy(String value) {

    int levels = StringUtils.split(value, '/').length;

    // No '+' before first level
    String levelIndicator = StringUtils.repeat("+", levels - 1);
    int    lastIndex = StringUtils.lastIndexOf(value, "/");
    int    lastPos = value.length();
    int    stuffToGet = lastPos - lastIndex;
    String displayPart = StringUtils.right(value, stuffToGet);

    displayPart = StringUtils.remove(displayPart, "/");

    StringBuffer hierarchifiedString = new StringBuffer();

    hierarchifiedString.append(levelIndicator).append(" ").append(displayPart);

    return hierarchifiedString.toString();

  }

  /**
   * Fills the selectOptionList
   */
  private void populateTargetEndUserOptions() {

    targetEndUserOptions = new SelectOptionList();
    targetEndUserOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getTargetEndUserValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          targetEndUserOptions.add(new SelectOption(value, displayHierarchy(value)));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Fills the selectOptionList
   */
  private void populateSchoolTypeOptions() {

    schoolTypeOptions = new SelectOptionList();
    schoolTypeOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getSchoolTypesValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          schoolTypeOptions.add(new SelectOption(value, displayHierarchy(value)));

        }

      }

      populateSchoolDisciplineOptions();

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Fills the selectOptionList
   */
  private void populateSchoolDisciplineOptions() {

    schoolDisciplineOptions = new SelectOptionList();
    schoolDisciplineOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getSchoolDisciplineValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          schoolDisciplineOptions.add(new SelectOption(value, displayHierarchy(value)));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Creates a Map containing option/displayvalue pairs to fill the schoolDiscipline dropDown list. This method
   * is called from javascript (an Ajax call). The HashMap can be interpreted client side.
   *
   * @param schoolType the key to find disciplines for.
   *
   * @return A map with option/displayvalue pairs.
   */
  public List<KeyValuePair> refreshSchoolDisciplineOptions(String schoolType) {

    String[]           values;
    List<KeyValuePair> kvPairs = new ArrayList();

    try {

      if ((schoolType == null) || ("".equals(schoolType))) {

        values = facade.getSchoolDisciplineValues();

        // extra option for "[Kies]"
        KeyValuePair firstKvp = new KeyValuePair();

        firstKvp.setKey("");
        firstKvp.setValue("[Kies]");
        kvPairs.add(firstKvp);

      } else {

        values = facade.getSchoolDisciplineValues(schoolType);

      }

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          KeyValuePair kvp = new KeyValuePair();

          kvp.setKey(value);
          kvp.setValue(displayHierarchy(value));
          kvPairs.add(kvp);

        }

      }

    } catch (Exception e) {

      e.printStackTrace();

    }

    return kvPairs;

  }

  /**
   * Fills the selectOptionList
   */
  private void populateDidacticFunctionOptions() {

    didacticFunctionOptions = new SelectOptionList();
    didacticFunctionOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getDidacticFunctionValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i]; // set both value and description to
                                  // metadata value

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          didacticFunctionOptions.add(new SelectOption(value, value));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Fills the selectOptionList
   */
  private void populateProductTypeOptions() {

    productTypeOptions = new SelectOptionList();
    productTypeOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getProductTypeValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          productTypeOptions.add(new SelectOption(value, value));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Fills the selectOptionList
   */
  private void populateProfessionalSituationOptions() {

    professionalSituationOptions = new SelectOptionList();
    professionalSituationOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getProfessionalSituationValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          professionalSituationOptions.add(new SelectOption(value, displayHierarchy(value)));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * Fills the selectOptionList
   */
  private void populateCompetenceOptions() {

    competenceOptions = new SelectOptionList();
    competenceOptions.add(new SelectOption(true, "", "[Kies]"));

    try {

      String[] values = facade.getCompetenceValues();

      for (int i = 0; i < values.length; i++) {

        String value = values[i];

        if (StringUtils.isNotBlank(value) && (! "/".equals(value))) {

          competenceOptions.add(new SelectOption(value, value));

        }

      }

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<Result<Record>> getMetazResults() {

    return metazResults;

  }

  /**
   * DOCUMENT ME!
   *
   * @param metazResults DOCUMENT ME!
   */
  public void setMetazResults(List<Result<Record>> metazResults) {

    this.metazResults = metazResults;

  }

}
