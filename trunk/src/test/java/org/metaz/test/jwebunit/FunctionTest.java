package org.metaz.test.jwebunit;

import net.sourceforge.jwebunit.WebTestCase;

/**
 * Tests the web application by means of HTTP requests.
 *
 * @author Sammy Dalewyn / Erik Schouten
 * @version 1.1
 */
public class FunctionTest extends WebTestCase {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The base URL of the web application */
  public static final String BASE_URL = "http://metaz.dyndns.org:8080/metaz/";
  public static String endUser = ""; // beoogdeEindgebruiker
  public static String schoolType = ""; // schooltype
  public static String schoolDiscipline = ""; // vakleergebied         
  public static String didacticFunction = ""; // didactischeFunctie
  public static String productType = ""; // producttype
  public static String professionalSituation = ""; // beroepssituatie  
  public static String competence = ""; // competentie  
  public static String keywords = "onderging"; // trefwoord
  public static String resultTitel = "gefaseerd koopmans waterdieren verhindert b?ta neervalt";

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Creates a new FunctionTest object.
     *
     * @param name the test name
     */
  public FunctionTest(String name) {
    super(name);

  } // end FunctionTest()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Initialises the test context
   */
  public void setUp() {

    getTestContext().setBaseUrl(BASE_URL);

  } // end setUp()

  /**
   * Tests the connectivity to the website
   */
  public void testConnectivitySite() {

    beginAt("/");

  } // end testConnectivitySite()

  /**
   * Tests the connectivity to the five main tabs of Applicatie Z
   */
  public void testConnectivityMainPages() {

    beginAt("search.jsp");
    beginAt("advancedsearch.jsp");
    beginAt("searchresults.jsp");
    beginAt("help.jsp");
    beginAt("info.jsp");

  } // end testConnectivityMainPages()

  /**
   * Tests the static contents of the main five tabs of Applicatie Z
   */
  public void testStaticContent() {

    // default tab
    beginAt("/");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertLinkPresentWithText("Eenvoudig zoeken");
    assertLinkPresentWithText("Uitgebreid zoeken");
    assertLinkPresentWithText("Zoekresultaat");
    assertLinkPresentWithText("Help");
    assertLinkPresentWithText("Informatie");
    assertLinkPresentWithText("RDMC");

    // simple search
    clickLinkWithText("Eenvoudig zoeken");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertFormPresent();
    assertTextPresent("Rol");
    assertElementPresent("beoogdeEindgebruiker");
    assertTextPresent("Schooltype");
    assertElementPresent("schooltype");
    assertTextPresent("Vakleergebied");
    assertElementPresent("vakleergebied");
    assertTextNotPresent("Didactische functie"); // check not Advanced
    assertTextPresent("Trefwoorden");
    assertElementPresent("sleutelwoorden");

    // advanced search
    clickLinkWithText("Uitgebreid zoeken");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertFormPresent();
    assertTextPresent("Rol");
    assertElementPresent("beoogdeEindgebruiker");
    assertTextPresent("Schooltype");
    assertElementPresent("schooltype");
    assertTextPresent("Vakleergebied");
    assertElementPresent("vakleergebied");
    assertTextPresent("Didactische functie");
    assertElementPresent("didactischeFunctie");
    assertTextPresent("Producttype");
    assertElementPresent("producttype");
    assertTextPresent("Beroepssituatie");
    assertElementPresent("beroepssituatie");
    assertTextPresent("Competentie");
    assertElementPresent("competentie");
    assertTextPresent("Trefwoorden");
    assertElementPresent("sleutelwoorden");
    assertFormPresent();

    // search results
    clickLinkWithText("Zoekresultaat");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertTextPresent("Producten van het Ruud de Moor Centrum");
    assertFormNotPresent();

    // help function (not yet developed)
    clickLinkWithText("Help");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertTextPresent("Quisque blandit sollicitudin nisi");
    assertFormNotPresent();

    // information about Applicatie Z (not yet developed)
    clickLinkWithText("Informatie");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertTextPresent("Lorem ipsum dolor sit amet");
    assertFormNotPresent();

  } // end testStaticContent()

  /**
   * Tests the simple search function
   */
  public void testSimpleSearch() {

    beginAt("search.jsp");
    setFormElement("beoogdeEindgebruiker", "");
    setFormElement("schooltype", "");
    setFormElement("vakleergebied", "");
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextInTable("row", resultTitel);

  } // end testSimpleSearch()

  /**
   * Tests the advanced search function
   */
  public void testAdvancedSearch() {

    beginAt("advancedsearch.jsp");
    setFormElement("beoogdeEindgebruiker", "");
    setFormElement("schooltype", "");
    setFormElement("vakleergebied", "");
    setFormElement("didactischeFunctie", "");
    setFormElement("producttype", "");
    setFormElement("beroepssituatie", "");
    setFormElement("competentie", "");
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextInTable("row", resultTitel);

  }

  /**
   * Tests for correct printing of the search results
   */
  public void testResultScreen() {

    beginAt("search.jsp");
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextPresent("Nr");
    assertTextPresent("Score");
    assertTextPresent("Type product");
    assertTextPresent("Titel");
    assertTextPresent("Meer gegevens");
    // check if results XML contains the records
    clickLinkWithText("XML");
    assertTextPresent(resultTitel);

  }

  /**
   * Tests for correct printing of the record details
   */
  public void testResultDetail() {

    beginAt("search.jsp");
    setFormElement("sleutelwoorden", keywords);
    submit();
    clickLinkWithTextAfterText("Meer...", resultTitel);
    assertTextPresent("Beveiligd");
    assertTextPresent("Sleutelwoorden");
    assertTextPresent("Type product");
    assertTextPresent("Didactisch scenario:");
    assertTextPresent("Benodigde tijd");
    assertTextPresent("Aggregatieniveau");
    assertTextPresent("Type product");
    assertTextPresent("Bestandsformaat");
    assertTextPresent("Bestandsgrootte");
    assertTextPresent("Afspeelduur");
    assertTextPresent("Technische vereisten");
    assertTextPresent("Laatst gewijzgd");

  }

  /**
   * Tests the correctness of the answers a search request returns.
   */
  public void testSearchCorrectness() {

    // not yet implemented
  }

} // end FunctionTest
