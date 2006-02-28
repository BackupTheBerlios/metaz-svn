package org.metaz.repository;

import java.util.List;

import org.metaz.domain.Record;


/**
 * The public interface for the Repository package.
 * 
 * @authors Jurgen Goelen
 * @version 0.3 
 */
public interface Facade {
        
    /**
     * Search for results that match the specified query.
     * @param query
     *      The query to be satisfied.
     * @return
     *      Returns a List of Record objects.
     * @throws Exception
     */
    public List<Result<Record>> doSearch(String query)throws Exception;
        
    /**
     * Purges and updates all the repositories services.
     * @param records
     *      Records to updated.
     * @throws Exception
     *      Update action failed.
     */
    public void doUpdate(List<Record> records) throws Exception; 
    
    
    /**
     * @return
     *      Returns a list of unique TargetEndUser values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getTargetEndUserValues() throws Exception;
    
    /**
     * @return
     *      Returns a list of unique SchoolType values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getSchoolTypesValues() throws Exception;
    
    
    /**
     * @return
     *      Returns a unique list of SchoolDiscipline values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getSchoolDisciplineValues() throws Exception;
        
    /**
     * @return
     *      Returns a list of unique DidacticFunction values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getDidacticFunctionValues() throws Exception;
    
    /**
     * @return
     *      Returns a list of unique ProductType values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getProductTypeValues() throws Exception;
    
    /**
     * @return
     *      Returns a list of unique ProfessionalSituation values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getProfessionalSituationValues() throws Exception;
    
    /**
     * @return
     *      Returns a list of unique Competence values that are 
     *      stored in the repository. 
     * @throws Exception
     */
    public List getCompetenceValues() throws Exception;
    
        
    /**
     * Sets the SearchService implementation to be used.
     * @param service A SearchService implementation.
     */
    public void setSearchService(SearchService service);
    
    /**
     * Sets the DataService implementation to be used.
     * @param service A DataService implementation.
     */
    public void setDataService(DataService service);
    
    
}
