package org.metaz.test;

import java.io.File;

import java.util.HashMap;

import org.apache.lucene.index.IndexReader;

import org.metaz.repository.Facade;
import org.metaz.repository.SearchService;
import org.metaz.repository.SearchServiceImpl;
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
            Facade facade = app.getRepositoryFacade();
            SearchService ssi = new SearchServiceImpl();
            File f = app.getRelativeFile("repository/searchservice/searchindex");
            IndexReader reader = IndexReader.open(f);
            int records = reader.numDocs();
            System.out.println("Number of records present in Application Z: "+ records);
            long fileSize = f.length();
            System.out.println("Size of search index is: "+fileSize);
            if(records<100){
                System.out.println("Insufficient records to run test !");
                System.exit(0);
            }
            int numberOfSearches = 0;
            long totalFacadeTime = 0;
            long totalSearchServiceTime = 0;
            for (int i=0; i<keywords.length; i++){
                HashMap hashMap = new HashMap();
                hashMap.put("",keywords[i]);
                long facadeSearchTime = timeSearch(hashMap,facade);
                totalFacadeTime = totalFacadeTime + facadeSearchTime;
                long searchServiceSearchTime = timeSearch(hashMap,ssi);
                totalSearchServiceTime = totalSearchServiceTime + searchServiceSearchTime;
                numberOfSearches++;
            }
            System.out.println("Performed a number of "+numberOfSearches+" full text searches.");
            long averageFacadeTime = totalFacadeTime/numberOfSearches;
            System.out.println("Average Facade search time: "+averageFacadeTime+ " ms");
            long averageSearchServiceTime = totalSearchServiceTime/numberOfSearches;
            System.out.println("Average SearchService search time: "+averageSearchServiceTime+" ms");
            long percent = averageSearchServiceTime*100/averageFacadeTime;
            System.out.println("SearchService seek takes "+percent+ "% of total search time");
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
