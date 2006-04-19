package org.metaz.test;

import junit.framework.TestCase;

import org.metaz.repository.Facade;

import org.metaz.util.MetaZ;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Class for testing the search performance of different repository implementations
 *
 * @author Sammy Dalewyn
 * @version 1.0
  */
public class RepositoryPerformanceTest extends TestCase {

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  /**
   * Class representing the performance metrics of a search
   */
  class SearchMetrics {

    private long elapsedTime;
    private int  numberOfResults;

    /**
     * Sets the elapsed time
     *
     * @param time the elapsed time
     */
    void setElapsedTime(long time) {

      elapsedTime = time;

    }

    /**
     * Sets the number of results
     *
     * @param number the number of results
     */
    void setNumberOfResults(int number) {

      numberOfResults = number;

    }

    /**
     * Returns the elapsed time
     *
     * @return the elapsed time
     */
    long getElapsedTime() {

      return elapsedTime;

    }

    /**
     * Returns the number of results
     *
     * @return the number of results
     */
    int getNumberOfResults() {

      return numberOfResults;

    }

  }

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final String FACADE_PROP = "org.metaz.repository.facade.impl";
  private static final String FACADE_DEF_PROP = "org.metaz.repository.FacadeImpl";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Constructor
   */
  public RepositoryPerformanceTest() {

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Tests the search performance by measuring the time needed.
   */
  public void testPerformance() {

    //TO-DO: add items to this list
    String[] keywords = new String[] {
                          "aanbevelen", "commercieel", "oplosbaar", "rapport", "nederlands", "trefbal", "beweging",
                          "termijn", "ondergaan", "onderging"
                        };

    try {

      MetaZ  app = MetaZ.getInstance();
      Facade facade = app.getRepositoryFacade();

      assertNotNull(facade);

      Properties props = app.getProperties();

      //primer
      facade.doSearch("niks");

      int  numberOfSearches = 0;
      long totalFacadeTime = 0;
      int  numberOfResults = 0;

      for (int i = 0; i < keywords.length; i++) {

        HashMap hashMap = new HashMap();

        hashMap.put("", keywords[i]);

        SearchMetrics sm = timeSearch(hashMap, facade);
        long          facadeSearchTime = sm.getElapsedTime();

        totalFacadeTime = totalFacadeTime + facadeSearchTime;

        int number = sm.getNumberOfResults();

        numberOfResults = numberOfResults + number;
        numberOfSearches++;

      }

      System.out.println("Performed a number of " + numberOfSearches + " full text searches.");

      String cls = props.getProperty(FACADE_PROP, FACADE_DEF_PROP);

      System.out.println("Using " + cls);

      int averageResults = numberOfResults / numberOfSearches;

      System.out.println("Found on average " + averageResults + " results.");

      long averageFacadeTime = totalFacadeTime / numberOfSearches;
      long averageResultFacadeTime = averageFacadeTime / averageResults;

      System.out.println("Average Facade search time: " + averageFacadeTime + " ms (" + averageResultFacadeTime +
                         " ms per result)");

    } catch (Exception ex) {

      System.out.println(ex.getMessage());

    }

  }

  /**
   * Returns the time a search takes and the number of results.
   *
   * @param hm the search values
   * @param f the facade implementation
   *
   * @return the search metrics
   *
   * @throws Exception the search could not be completed
   */
  private SearchMetrics timeSearch(HashMap hm, Facade f)
                            throws Exception
  {

    SearchMetrics sm = new SearchMetrics();
    long          startTime = System.currentTimeMillis();
    List          list = f.doSearch(hm);
    long          stopTime = System.currentTimeMillis();
    long          elapsedTime = stopTime - startTime;

    sm.setElapsedTime(elapsedTime);
    sm.setNumberOfResults(list.size());

    return sm;

  }

}
