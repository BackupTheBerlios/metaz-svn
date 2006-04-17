package org.metaz.repository;

import org.metaz.domain.Record;

import java.util.HashMap;
import java.util.List;

import org.metaz.domain.MetaData;

/**
 * The public interface for the Repository package.
 * 
 * @author Jurgen Goelen, Sammy Dalewyn
 * @version 0.3 
 */
public interface Facade {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Searches for results that match the specified query.
   * <p>The query syntax is:<br>
   * <code>Query ::= (Clause)+</br>
   * *  Clause ::= [&lt;FULLTEXTSEARCHPHRASE&gt;] || [&lt;TERM&gt;:&lt;VALUE&gt;]+</code></br>
   * <code>&lt;FULLTEXTSEARCHPHRASE&gt;</code> should always precede term-value combinations and shall not contain any
   * semicolon.</p>
   *  <p>NOTE: In case of multiple selection the values should be seperated by the character '%'.</p>
   *
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
   * Searches for results that match the specified query.
   * <p>The parameter <code>termValuePairs</code> may contain the following
   * keys:</p>
   * <ul>
   * <li>MetaData.TARGETENDUSER</li>
   * <li>MetaData.SCHOOLTYPE</li>
   * <li>MetaData.SCHOOLDISCIPLINE</li>
   * <li>MetaData.DIDACTICFUNCTION</li>
   * <li>MetaData.PRODUCTTYPE</li>
   * <li>MetaData.PROFESSIONALSITUATION</li>
   * <li>MetaData.COMPETENCE</li>
   * </ul>
   * <p>The values of these items must match exactly the values returned by the 
   * methods getXValues(). Multiple search can be implemented by separating the
   * different values with &#37; (i.e. auto&#37;self will search for all occurences
   * of auto OR self for the specified key).</br>
   * For full text search one can add a key to <code>termValuePairs</code> with
   * two doublequotes:</p>
   * <ul>
   * <li>&quot;&quot;</li>
   * </ul>
   * <p>The value belonging to this key may contain any text. (See 
   * <a href="http://lucene.apache.org/java/docs/queryparsersyntax.html"
   * target="_blank">Lucene Query Syntax</a> for further information on wildcards,
   * fuzzy search, proximity search, boosting,...).
   * </p>
   * <p>The parameter <code>termValuePairs</code> may also contain the key 
   * <ul><li>
   * <b>AllTermsRequired</b></ul><p> If the value is set to <code>false</code> the
   * different terms will be combined by a logical OR, if set to <code>true</code>
   * the different terms are combinded by a logical AND.</p>
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
   * Purges and updates all the repository services.
   *
   * @param records Records to updated.
   *
   * @throws Exception Update action failed.
   */
  public void doUpdate(List<Record> records)
                throws Exception;

  /**
   * Returns a sorted array of unique TargetEndUser values that are  stored in the repository.
   * <p>The array contains all sublevel values in a pathlike string representation
   * (e.g. /a/b/c/d and /a/b/c and /a/b and /a)</p>
   *
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getTargetEndUserValues()
                                  throws Exception;

  /**
   * Returns a sorted array of unique SchoolType values that are stored in the repository.
   * <p>The array contains all sublevel values in a pathlike string representation
   * (e.g. /a/b/c/d and /a/b/c and /a/b and /a)</p>
   * 
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getSchoolTypesValues()
                                throws Exception;

  /**
   * Returns a sorted array of unique SchoolDiscipline values that are stored in the repository.
   * <p>The array contains all sublevel values in a pathlike string representation
   * (e.g. /a/b/c/d and /a/b/c and /a/b and /a)</p>
   * 
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getSchoolDisciplineValues()
                                     throws Exception;

  /**
   * Returns a sorted array of unique SchoolDiscipline values that are related to the  provided schoolType value and are
   * stored in the repository.
   * <p>The parameter <code>schooltype</code> should contain the full path, i.e. someting
   * like <code>/a/b/c</code></p>
   * <p>The returned array contains all sublevel values in a pathlike string representation
   * (e.g. /a/b/c/d and /a/b/c and /a/b and /a)</p>
   *
   * @param schooltype the dependent school type
   *
   * @return an array of related SchoolDiscipline values
   *
   * @throws Exception
   */
  public String[] getSchoolDisciplineValues(String schooltype)
                                     throws Exception;

  /**
   * Returns a sorted array of unique DidacticFunction values that are stored in the repository.
   *
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getDidacticFunctionValues()
                                     throws Exception;

  /**
   * Returns a sorted array of unique ProductType values that are stored in the repository.
   *
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getProductTypeValues()
                                throws Exception;

  /**
   * Returns a sorted array of unique ProfessionalSituation values that are stored in the repository.
   * <p>The array contains all sublevel values in a pathlike string representation
   * (e.g. /a/b/c/d and /a/b/c and /a/b and /a)</p>
   * 
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getProfessionalSituationValues()
                                          throws Exception;

  /**
   * Returns a sorted array of unique Competence values that are stored in the repository.
   *
   * @return the sorted array
   *
   * @throws Exception
   */
  public String[] getCompetenceValues()
                               throws Exception;

  /**
   * Sets the SearchService implementation to be used.
   *
   * @param service A SearchService implementation.
   */
  public void setSearchService(RepositoryService service);

  /**
   * Sets the DataService implementation to be used.
   *
   * @param service A DataService implementation.
   */
  public void setDataService(RepositoryService service);

}
