package org.metaz.test;

import java.util.HashMap;

import java.util.List;

import java.util.Properties;

import junit.framework.TestCase;

import org.metaz.repository.Facade;
import org.metaz.util.MetaZ;

public class RepositoryPerformanceTest extends TestCase{

    private static final String FACADE_PROP = "org.metaz.repository.facade.impl";

    private static final String FACADE_DEF_PROP = "org.metaz.repository.FacadeImpl";
    
    public RepositoryPerformanceTest() {
    }

    public void testPerformance() {
                
        //TO-DO: add items to this list
        String[] keywords = new String[] {"aanbevelen","commercieel","oplosbaar","rapport","nederlands","trefbal","beweging","termijn","ondergaan","onderging"};
        
        try{
            MetaZ app = MetaZ.getInstance();
            Facade facade = app.getRepositoryFacade();
            assertNotNull(facade);
            Properties props = app.getProperties();
            //primer
            facade.doSearch("niks");
            
            int numberOfSearches = 0;
            long totalFacadeTime = 0;
            int numberOfResults = 0;
            
            for (int i=0; i<keywords.length; i++){
                HashMap hashMap = new HashMap();
                hashMap.put("",keywords[i]);
                SearchMetrics sm = timeSearch(hashMap,facade);
                long facadeSearchTime = sm.getElapsedTime();
                totalFacadeTime = totalFacadeTime + facadeSearchTime;
                int number = sm.getNumberOfResults();
                numberOfResults = numberOfResults + number;
                numberOfSearches++;
            }
            System.out.println("Performed a number of "+numberOfSearches+" full text searches.");
            String cls = props.getProperty(FACADE_PROP, FACADE_DEF_PROP);
            System.out.println("Using "+cls);
            int averageResults = numberOfResults/numberOfSearches;
            System.out.println("Found on average "+averageResults+ " results.");
            long averageFacadeTime = totalFacadeTime/numberOfSearches;
            long averageResultFacadeTime = averageFacadeTime/averageResults;
            System.out.println("Average Facade search time: "+averageFacadeTime+ " ms ("+averageResultFacadeTime+ " ms per result)");
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private SearchMetrics timeSearch(HashMap hm, Facade f) throws Exception{
        SearchMetrics sm = new SearchMetrics();
        long startTime = System.currentTimeMillis();
        List list = f.doSearch(hm);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        sm.setElapsedTime(elapsedTime);
        sm.setNumberOfResults(list.size());
        return sm;
    }
    
    class SearchMetrics {
        long elapsedTime;
        int numberOfResults;
        
        void setElapsedTime(long time){
            elapsedTime = time;
        }
        
        void setNumberOfResults(int number){
            numberOfResults = number;
        }
        
        long getElapsedTime(){
            return elapsedTime;
        }
        
        int getNumberOfResults(){
            return numberOfResults;
        }
    }
}
