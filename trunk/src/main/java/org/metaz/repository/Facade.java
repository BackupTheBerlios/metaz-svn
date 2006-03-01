package org.metaz.repository;

import org.metaz.domain.Record;

import java.util.HashMap;
import java.util.List;

/**
 * The public interface for the Repository package.
 * 
 * @authors Jurgen Goelen
 * @version 0.3 
 */
public interface Facade {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Search for results that match the specified query.
   *
   * @param query The query to be satisfied.
   *
   * @return Returns a List of Record objects.
   *
   * @throws Exception
   */
  public List<Result<Record>> doSearch(String query)
                                throws Exception;

  /**
   * Search for results that match the specified query.
   *
   * @param termValuePairs The query to be satisfied.
   *
   * @return Returns a List of Record objects.
   *
   * @throws Exception
   */
  public List<Result<Record>> doSearch(HashMap termValuePairs)
                                throws Exception;

  /**
   * Purges and updates all the repositories services.
   *
   * @param records Records to updated.
   *
   * @throws Exception Update action failed.
   */
  public void doUpdate(List<Record> records)
                throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique TargetEndUser values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getTargetEndUserValues()
                              throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique SchoolType values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getSchoolTypesValues()
                            throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a unique list of SchoolDiscipline values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getSchoolDisciplineValues()
                                 throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique DidacticFunction values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getDidacticFunctionValues()
                                 throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique ProductType values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getProductTypeValues()
                            throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique ProfessionalSituation values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getProfessionalSituationValues()
                                      throws Exception;

  /**
   * 
  DOCUMENT ME!
   *
   * @return Returns a list of unique Competence values that are  stored in the repository.
   *
   * @throws Exception
   */
  public List getCompetenceValues()
                           throws Exception;

  /**
   * Sets the SearchService implementation to be used.
   *
   * @param service A SearchService implementation.
   */
  public void setSearchService(SearchService service);

  /**
   * Sets the DataService implementation to be used.
   *
   * @param service A DataService implementation.
   */
  public void setDataService(DataService service);

}
