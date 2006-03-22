package org.metaz.test;

import java.io.File;

import java.util.HashMap;

import java.util.List;

import org.apache.lucene.index.IndexReader;

import org.metaz.repository.Facade;
import org.metaz.repository.FacadeFactory;
import org.metaz.repository.SearchService;
import org.metaz.repository.SearchServiceImpl;
import org.metaz.repository.alt.FacadeFactoryAlt;
import org.metaz.util.MetaZ;

public class RepositoryPerformanceTest {
    public RepositoryPerformanceTest() {
    }

    public static void main(String[] args) {
        RepositoryPerformanceTest repositoryPerformanceTest = new RepositoryPerformanceTest();
        
        //TO-DO: add items to this list
        String[] keywords = new String[] {"aanbevelen","commercieel","oplosbaar","rapport","nederlands","trefbal","beweging","termijn","ondergaan","onderging"};
        
        try{
            MetaZ app = MetaZ.getInstance();
            Facade facade = FacadeFactory.createFacade();
            SearchService ssi = new SearchServiceImpl();
            Facade facadeAlt = FacadeFactoryAlt.createFacade();
            File f = app.getRelativeFile("repository/searchservice/searchindex");
            IndexReader reader = IndexReader.open(f);
            int records = reader.numDocs();
            reader.close();
            System.out.println("Number of records present in Application Z: "+ records);
            
            long fileSize = f.length();
            System.out.println("Size of search index is: "+fileSize);
            if(records<100){
                System.out.println("Insufficient records to run test !");
                System.exit(0);
            }
            //primer
            facade.doSearch("niks");
            ssi.doSearch("niks");
            facadeAlt.doSearch("niks");
            int numberOfSearches = 0;
            long totalFacadeTime = 0;
            long totalSearchServiceTime = 0;
            long totalFacadeAltTime = 0;
            int numberOfResults = 0;
            for (int i=0; i<keywords.length; i++){
                HashMap hashMap = new HashMap();
                hashMap.put("",keywords[i]);
                long facadeSearchTime = timeSearch(hashMap,facade);
                totalFacadeTime = totalFacadeTime + facadeSearchTime;
                long searchServiceSearchTime = timeSearch(hashMap,ssi);
                totalSearchServiceTime = totalSearchServiceTime + searchServiceSearchTime;
                long facadeAltSearchTime = timeSearch(hashMap,facadeAlt);
                totalFacadeAltTime = totalFacadeAltTime + facadeAltSearchTime;
                List list = ssi.doSearch(hashMap);
                numberOfResults = numberOfResults + list.size();
                numberOfSearches++;
            }
            System.out.println("Performed a number of "+numberOfSearches+" full text searches.");
            int averageResults = numberOfResults/numberOfSearches;
            System.out.println("Found on average "+averageResults+ " results.");
            long averageFacadeTime = totalFacadeTime/numberOfSearches;
            long averageResultFacadeTime = averageFacadeTime/averageResults;
            System.out.println("Average Facade search time: "+averageFacadeTime+ " ms ("+averageResultFacadeTime+ " ms per result)");
            long averageSearchServiceTime = totalSearchServiceTime/numberOfSearches;
            long averageResultSearchServiceTime = averageSearchServiceTime/averageResults;
            System.out.println("Average SearchService search time: "+averageSearchServiceTime+" ms ("+averageResultSearchServiceTime+ " ms per result");
            long percent = averageSearchServiceTime*100/averageFacadeTime;
            System.out.println("SearchService seek takes "+percent+ "% of total search time");
            long averageFacadeAltTime = totalFacadeAltTime/numberOfSearches;
            long averageResultFacadeAltTime = averageFacadeAltTime/averageResults;
            System.out.println("Average FacadeAlt search time: "+averageFacadeAltTime+ " ms ("+averageResultFacadeAltTime+ " ms per record)");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private static long timeSearch(HashMap hm, Facade f) throws Exception{
        long startTime = System.currentTimeMillis();
        f.doSearch(hm);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        return elapsedTime;
    }
    
    private static long timeSearch(HashMap hm, SearchService ssi){
        long startTime = System.currentTimeMillis();
        ssi.doSearch(hm);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        return elapsedTime;
    }
}
