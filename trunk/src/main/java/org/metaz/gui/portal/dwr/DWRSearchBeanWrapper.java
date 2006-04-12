package org.metaz.gui.portal.dwr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.metaz.gui.portal.SearchBean;

public class DWRSearchBeanWrapper {

	public Map<String, String> getSchoolDisplinesBySchoolType(
			String schoolType, HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		Map<String, String> result;
		if (StringUtils.isNotBlank(schoolType)) {
			result = sb.refreshSchoolDisciplineOptions(schoolType);
		} else {
			result = new HashMap<String, String>();
			result.put("", "Niets gevonden");
		}
		return result;
	}


	public void setSelectedTargetEndUserOption(String optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getTargetEndUserOptions().getOption(optionValue).setSelected(true);
	}

	public void setSelectedSchoolTypeOption(String optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getSchoolTypeOptions().getOption(optionValue).setSelected(true);
		
	}

	public void setSelectedSchoolDisciplineOption(String optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getSchoolDisciplineOptions().getOption(optionValue).setSelected(true);
	}

	public void setSelectedDidacticFunctionOption(String optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getDidacticFunctionOptions().getOption(optionValue).setSelected(true);
	}

	public void setSelectedProductTypeOption(String[] optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getProductTypeOptions().getOptions(optionValue).setSelected(true);
	}

	public void setSelectedProfessionalSituationOption(String[] optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getProfessionalSituationOptions().getOptions(optionValue).setSelected(true);
	}

	public void setSelectedCompetenceOption(String[] optionValue, HttpSession session) {
		SearchBean sb = (SearchBean) session
		.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getCompetenceOptions().getOptions(optionValue).setSelected(true);
	}
}
