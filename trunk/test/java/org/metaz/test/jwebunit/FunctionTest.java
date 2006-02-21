package org.metaz.test.jwebunit;

import net.sourceforge.jwebunit.WebTestCase;


/**
 * Tests the web application by means of HTTP requests.
 *
 * @author Sammy Dalewyn
 * @version 1.0
  */
public class FunctionTest extends WebTestCase
{
    /**
     * The base URL of the web application
     */
    public static final String BASE_URL = "http://localhost:8080/appz/";
    
    /**
     * Creates a new FunctionTest object.
     *
     * @param name the test name
     */
    public FunctionTest(String name)
    {
        super(name);
    } // end FunctionTest()

    /**
     * Initialises the test context
     */
    public void setUp()
    {
        getTestContext().setBaseUrl(BASE_URL);
    } // end setUp()

    /**
     * Tests the main page (simple search) on static content
     */
    public void testMainPage()
    {
        beginAt("/");
        assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    } // end testMainPage()
    
    /**
     * Tests the advanced search page on static content
     */
    public void testAdvancedSearchPage() {
        
    }
    
    /**
     * Tests the result list page on static content (ie. without search request)
     */
    public void testResultListPage() {
        
    }
    
    /**
     * Tests the result detail page on static content (ie. without search request)
     */
     public void testResultDetailPage() {
         
     }

    /**
     * Tests the main page links (i.e. are all pages attainable?)
     */
    public void testMainPageTabs()
    {
        beginAt("/");
        assertLinkPresentWithText("Eenvoudig zoeken");
        assertLinkPresentWithText("Uitgebreid zoeken");
        clickLinkWithText("Uitgebreid zoeken");
        assertTextPresent("Beroepssituatie");
        beginAt("/");
        assertLinkPresentWithText("Zoekresultaat");
        clickLinkWithText("Zoekresultaat");
        assertTextPresent("ProductType");
        beginAt("/");
        assertLinkPresentWithText("Help");
        clickLinkWithText("Help");
        assertTextNotPresent("Beroepssituatie");
        beginAt("/");
        assertLinkPresentWithText("Informatie");
        clickLinkWithText("Informatie");
        assertTextPresent("Nam eget nibh sit amet turpis ullamcorper euismod.");
    } // end testMainPageTabs()

    /**
     * Tests the simple search function
     */
    public void testSimpleSearch()
    {
        beginAt("/");
        //assertFormPresent("searchhandler.jsp"); // form element has no id or name specified
        assertFormElementPresent("targetEndUser");
        assertFormElementEquals("targetEndUser", "*");
        //setFormElement("targetEndUser", "b504524c-74e5-403e-94c9-38525c7046c2"); // option value is not static
        //setFormElementWithLabel("Ik ben een:", "b504524c-74e5-403e-94c9-38525c7046c2"); //option value is not static
        setFormElement("keywords", "ik zoek een leerobject");
        submit();
        assertTextPresent("The teacher is cool");
    } // end testSimpleSearch()
    
    /**
     * Tests the advanced search function
     */
    public void testAdvancedSearch() {
        
    }
    
    /**
     * Tests the correctness of the answers a search request returns.
     */
    public void testSearchCorrectness() {
        
    }
} // end FunctionTest
