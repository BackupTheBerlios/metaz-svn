// @author: Falco Paul
package org.metaz.gui.portal;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.MetaData;
import org.metaz.domain.Record;
import org.metaz.domain.TextMetaData;
import org.metaz.repository.Result;
import org.metaz.repository.Facade;
import org.metaz.util.HierarchicalStructuredMetaDataValueParser;
import org.metaz.util.MetaZ;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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

	// ~ Static fields/initializers
	// ---------------------------------------------------------------------------------------

	public final static String SEARCH_BEAN_SESSION_KEY = "searchBean";

	public final static String SEARCH_PAGE = "search.jsp";

	public final static String ERROR_PAGE = "error.jsp";

	public final static String ADVANCED_SEARCH_PAGE = "advancedsearch.jsp";

	public final static String RESULTS_PAGE = "searchresults.jsp";

	private static Logger logger = Logger.getLogger(SearchBean.class); // logger

	// instance
	// for
	// this
	// class

	// ~ Instance fields
	// --------------------------------------------------------------------------------------------------

	// Search results
	private List<Result<Record>> metazResults;

	private Facade facade;

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

	// ~ Constructors
	// -----------------------------------------------------------------------------------------------------

	public SearchBean() {

		facade = MetaZ.getRepositoryFacade();

		populateTargetEndUserOptions();
		populateSchoolTypeOptions();
		populateDidacticFunctionOptions();
		populateProductTypeOptions();
		populateProfessionalSituationOptions();
		populateCompetenceOptions();

		keywords = new String("");
		errorMessage = new String("");

	}

	// ~ Methods
	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Retrieves the n'th object from the list, where n is the id specified.
	 * Used to show a single record in the details view.
	 * 
	 * @param id
	 *            the index of the object in the list of searchresults
	 * @return the Record indexed by id.
	 */
	public Record getRecord(int id) {
		return metazResults.get(id).getObject();
	}

	/**
	 * Performs a search by collecting user input and submitting this input to
	 * the Repository.
	 * 
	 * @param req
	 *            the request containing user input.
	 * @param res
	 *            the response on which results will be returned to the user.
	 * @throws ServletException
	 *             when a servlet exception occurs.
	 * @throws IOException
	 *             when an I/O exception occurs.
	 */
	public void search(HttpServletRequest req, HttpServletResponse res,
			boolean advancedMode) throws ServletException, IOException {

		HashMap map = (HashMap) processPost(req);

		if (validate()) {
			Facade f = MetaZ.getRepositoryFacade();
			try {
				List<Result<Record>> l = f.doSearch(map);
				setMetazResults(l);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage()
						+ " Forwarding to error page");
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

	// forward... server interal... client sees original URL
	// flow WILL return to caller!
	private void forward(HttpServletRequest req, HttpServletResponse res,
			String page) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher(page);

		dispatcher.forward(req, res);

	}

	/**
	 * Processes the request to retrieve user input. Prepares this input to be
	 * sent to Repository.
	 * 
	 * @param req
	 * @return
	 */
	public Map processPost(HttpServletRequest req) {

		logger.debug("Getting parameter names and values from request");

		Map metazSearchMap = new HashMap();

		HashMap parameterMap = (HashMap) req.getParameterMap();
		Iterator it = parameterMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] value = (String[]) parameterMap.get(key);
			String metazSearchValue = null;
			// only work on the value if it isn't empty
			if (!ArrayUtils.isEmpty(value) && !isEmptyStringArray(value)) {
				if (needsMetazSearchEnabling(key)) {
					metazSearchValue = metazSearchEnableStringArray(value);
				} else {
					metazSearchValue = getPlainSearchString(value);
				}
			}
			logger.debug("Found parameter [" + key + "] with value ["
					+ metazSearchValue + "]");
			if (MetaData.KEYWORDS.equals(key)) {
				key = "";
				if (StringUtils.isBlank(metazSearchValue)) {
					logger
							.debug("No keywords specified. Ignoring Keyword search.");
					continue;
				}
			}
			if (metazSearchValue != null) {
				metazSearchMap.put(key, metazSearchValue);
			}
		}

		// Example processing of SELECT ONE... targetEndUser
		String formValue = req.getParameter("targetEndUser");
		SelectOption selectedTargetEndUser = targetEndUserOptions
				.getOption(formValue);

		if (selectedTargetEndUser != null) {

			// Make the interface user friendly... remember the last value!
			// Single select:
			// - First make sure none of the others options are selected...
			// - Then mark the matched option as selected
			targetEndUserOptions.setSelected(false);
			selectedTargetEndUser.setSelected(true);

		}

		// Example processing SELECT MULTIPLE... product type
		String[] formValues = req.getParameterValues("productType");
		SelectOptionList selectedProductTypes = productTypeOptions
				.getOptions(formValues);

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

	private String getPlainSearchString(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			sb.append(value);
		}
		return sb.toString();
	}

	private boolean needsMetazSearchEnabling(String key) {
		if (MetaData.TARGETENDUSER.equals(key)
				|| MetaData.SCHOOLTYPE.equals(key)
				|| MetaData.SCHOOLDISCIPLINE.equals(key)
				|| MetaData.DIDACTICFUNCTION.equals(key)
				|| MetaData.PRODUCTTYPE.equals(key)
				|| MetaData.PROFESSIONALSITUATION.equals(key)
				|| MetaData.COMPETENCE.equals(key)) {
			return true;
		} else {
			return false;
		}
	}

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
	 * DOCUMENT ME!
	 * 
	 * @param logger
	 *            DOCUMENT ME!
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
	 * @param targetEndUserOptions
	 *            DOCUMENT ME!
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
	 * @param schoolTypeOptions
	 *            DOCUMENT ME!
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
	 * @param schoolDisciplineOptions
	 *            DOCUMENT ME!
	 */
	public void setSchoolDisciplineOptions(
			SelectOptionList schoolDisciplineOptions) {

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
	 * @param didacticFunctionOptions
	 *            DOCUMENT ME!
	 */
	public void setDidacticFunctionOptions(
			SelectOptionList didacticFunctionOptions) {

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
	 * @param productTypeOptions
	 *            DOCUMENT ME!
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
	 * @param professionalSituationOptions
	 *            DOCUMENT ME!
	 */
	public void setProfessionalSituationOptions(
			SelectOptionList professionalSituationOptions) {

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
	 * @param competenceOptions
	 *            DOCUMENT ME!
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
	 * @param keywords
	 *            DOCUMENT ME!
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
	 * @param errorMessage
	 *            DOCUMENT ME!
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

	private String displayHierarchy(String value) {
		int levels = StringUtils.split(value, '/').length;
		// No '+' before first level
		String levelIndicator = StringUtils.repeat("+", levels - 1);
		int lastIndex = StringUtils.lastIndexOf(value, "/");
		int lastPos = value.length();
		int stuffToGet = lastPos - lastIndex;
		String displayPart = StringUtils.right(value, stuffToGet);
		displayPart = StringUtils.remove(displayPart, "/");
		StringBuffer hierarchifiedString = new StringBuffer();
		hierarchifiedString.append(levelIndicator).append(" ").append(
				displayPart);
		return hierarchifiedString.toString();
	}

	private void populateTargetEndUserOptions() {
		targetEndUserOptions = new SelectOptionList();
		targetEndUserOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String[] values = facade.getTargetEndUserValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					targetEndUserOptions.add(new SelectOption(value,
							displayHierarchy(value)));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateSchoolTypeOptions() {

		schoolTypeOptions = new SelectOptionList();
		schoolTypeOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String[] values = facade.getSchoolTypesValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					schoolTypeOptions.add(new SelectOption(value,
							displayHierarchy(value)));
				}
			}
			populateSchoolDisciplineOptions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateSchoolDisciplineOptions() {
		schoolDisciplineOptions = new SelectOptionList();
		schoolDisciplineOptions.add(new SelectOption(true, "",
				"[Kies een schooltype]"));
	}

	/**
	 * Creates a Map containing option/displayvalue pairs to fill the
	 * schoolDiscipline dropDown list. This method is called from javascript (an
	 * Ajax call). The HashMap can be interpreted client side.
	 * 
	 * @param schoolType
	 *            the key to find disciplines for.
	 * @return A map with option/displayvalue pairs.
	 */
	public Map<String, String> refreshSchoolDisciplineOptions(String schoolType) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] values = facade.getSchoolDisciplineValues(schoolType);
			for (int i = 0; i < values.length; i++) {
				map.put(values[i], (displayHierarchy(values[i])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private void populateDidacticFunctionOptions() {

		didacticFunctionOptions = new SelectOptionList();
		didacticFunctionOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String[] values = facade.getDidacticFunctionValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i]; // set both value and description to
				// metadata value
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					didacticFunctionOptions.add(new SelectOption(value, value));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateProductTypeOptions() {

		productTypeOptions = new SelectOptionList();
		productTypeOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String[] values = facade.getProductTypeValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					productTypeOptions.add(new SelectOption(value, value));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateProfessionalSituationOptions() {

		professionalSituationOptions = new SelectOptionList();
		professionalSituationOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String[] values = facade.getProfessionalSituationValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					professionalSituationOptions.add(new SelectOption(value,
							displayHierarchy(value)));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateCompetenceOptions() {

		competenceOptions = new SelectOptionList();
		competenceOptions.add(new SelectOption(true, "", "[Kies]"));
		try {
			String values[] = facade.getCompetenceValues();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotBlank(value) && (!"/".equals(value))) {
					competenceOptions.add(new SelectOption(value, value));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Result<Record>> getMetazResults() {
		return metazResults;
	}

	public void setMetazResults(List<Result<Record>> metazResults) {
		this.metazResults = metazResults;
	}

}
