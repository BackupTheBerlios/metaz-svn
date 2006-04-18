package org.metaz.gui.portal.dwr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.metaz.gui.portal.KeyValuePair;
import org.metaz.gui.portal.SearchBean;

public class DWRSearchBeanWrapper {

	public List<KeyValuePair> getSchoolDisplinesBySchoolType(
			String schoolType, HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		if("[Kies]".equals(schoolType)){
			schoolType = null;
		}
		return sb.refreshSchoolDisciplineOptions(schoolType);
	}

	public void setSelectedTargetEndUserOption(String optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getTargetEndUserOptions().getOption(optionValue).setSelected(true);
	}

	public void setSelectedSchoolTypeOption(String optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getSchoolTypeOptions().getOption(optionValue).setSelected(true);

	}

	public void setSelectedSchoolDisciplineOption(String optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getSchoolDisciplineOptions().getOption(optionValue)
				.setSelected(true);
	}

	public void setSelectedDidacticFunctionOption(String optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getDidacticFunctionOptions().getOption(optionValue)
				.setSelected(true);
	}

	public void setSelectedProductTypeOption(String[] optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getProductTypeOptions().getOptions(optionValue).setSelected(true);
	}

	public void setSelectedProfessionalSituationOption(String[] optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getProfessionalSituationOptions().getOptions(optionValue)
				.setSelected(true);
	}

	public void setSelectedCompetenceOption(String[] optionValue,
			HttpSession session) {
		SearchBean sb = (SearchBean) session
				.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);
		sb.getCompetenceOptions().getOptions(optionValue).setSelected(true);
	}
}
