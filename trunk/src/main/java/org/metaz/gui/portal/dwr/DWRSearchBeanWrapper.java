package org.metaz.gui.portal.dwr;

import org.metaz.gui.portal.KeyValuePair;
import org.metaz.gui.portal.SearchBean;

import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class DWRSearchBeanWrapper {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param schoolType DOCUMENT ME!
   * @param session DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List<KeyValuePair> getSchoolDisplinesBySchoolType(String schoolType, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    if ("[Kies]".equals(schoolType)) {

      schoolType = null;

    }

    return sb.refreshSchoolDisciplineOptions(schoolType);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedTargetEndUserOption(String optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getTargetEndUserOptions().getOption(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedSchoolTypeOption(String optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getSchoolTypeOptions().getOption(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedSchoolDisciplineOption(String optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getSchoolDisciplineOptions().getOption(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedDidacticFunctionOption(String optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getDidacticFunctionOptions().getOption(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedProductTypeOption(String[] optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getProductTypeOptions().getOptions(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedProfessionalSituationOption(String[] optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getProfessionalSituationOptions().getOptions(optionValue).setSelected(true);

  }

  /**
   * DOCUMENT ME!
   *
   * @param optionValue DOCUMENT ME!
   * @param session DOCUMENT ME!
   */
  public void setSelectedCompetenceOption(String[] optionValue, HttpSession session) {

    SearchBean sb = (SearchBean) session.getAttribute(SearchBean.SEARCH_BEAN_SESSION_KEY);

    sb.getCompetenceOptions().getOptions(optionValue).setSelected(true);

  }

}
