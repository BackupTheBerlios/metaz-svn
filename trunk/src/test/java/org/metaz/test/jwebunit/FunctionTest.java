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

  //private static final String BASE_URL = "http://localhost:8080/metaz/";
  private static String endUser = ""; // beoogdeEindgebruiker
  private static String schoolType = ""; // schooltype
  private static String schoolDiscipline = ""; // vakleergebied
  private static String didacticFunction = ""; // didactischeFunctie
  private static String productType = ""; // producttype
  private static String professionalSituation = ""; // beroepssituatie
  private static String competence = ""; // competentie
  private static String keywords = "onderwijs"; // trefwoord
  private static String resultTitel = "mengelt verbinden energiegebruikers";
  private static String NoDataFound = "Geen data gevonden ";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  //private static String keywords = "lesvoorbereiding"; // trefwoord
  //private static String resultTitel = "Kennisbank wiskunde";
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
    assertTextPresent("gebruikershandleiding");
    assertFormNotPresent();

    // information about Applicatie Z (not yet developed)
    clickLinkWithText("Informatie");
    assertTitleEquals("Ruud de Moor Centrum - Applicatie Z");
    assertTextPresent("zoekalgoritme van Applicatie Z");
    assertFormNotPresent();

  } // end testStaticContent()

  /**
   * Tests the simple search function
   */
  public void testSimpleSearch() {

    beginAt("search.jsp");
    setFormElement("beoogdeEindgebruiker", endUser);
    setFormElement("schooltype", schoolType);
    setFormElement("vakleergebied", schoolDiscipline);
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextNotPresent(NoDataFound);

  } // end testSimpleSearch()

  /**
   * Tests the advanced search function
   */
  public void testAdvancedSearch() {

    beginAt("advancedsearch.jsp");
    setFormElement("beoogdeEindgebruiker", endUser);
    setFormElement("schooltype", schoolType);
    setFormElement("vakleergebied", schoolDiscipline);
    setFormElement("didactischeFunctie", didacticFunction);
    setFormElement("producttype", productType);
    setFormElement("beroepssituatie", professionalSituation);
    setFormElement("competentie", competence);
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextNotPresent(NoDataFound);

  }

  /**
   * Tests for correct printing of the search results
   */
  public void testResultScreen() {

    beginAt("search.jsp");
    setFormElement("sleutelwoorden", keywords);
    submit();
    assertTextPresent("Nr");
    assertTextPresent("Type product");
    assertTextPresent("Titel");
    assertTextPresent("Meer gegevens");
    // check if results XML contains the records
    clickLinkWithText("XML");
    assertTextPresent("recorddetails.jsp?record=0");

  }

  /**
   * Tests for correct printing of the record details
   */
  public void testResultDetail() {

    beginAt("search.jsp");
    setFormElement("sleutelwoorden", keywords);
    submit();
    clickLinkWithText("Meer...");
    assertTextPresent("Sleutelwoorden");
    assertTextPresent("Didactische functie");
    assertTextPresent("Didactisch scenario:");
    assertTextPresent("Schooltype:");
    assertTextPresent("Vakleergebied");
    assertTextPresent("Beroepssituatie");
    assertTextPresent("Competentie");
    assertTextPresent("Aggregatieniveau");
    assertTextPresent("Beoogde eindgebruiker");
    assertTextPresent("Rechten");
    assertTextPresent("Producttype");
    assertTextPresent("Benodigde tijd");
    assertTextPresent("Afspeelduur");
    assertTextPresent("Bestandsformaat");
    assertTextPresent("Bestandsgrootte");
    assertTextPresent("Technische vereisten");
    assertTextPresent("Creatie datum");
    assertTextPresent("Laatst gewijzigd");
    assertTextPresent("Versie");
    assertTextPresent("Status");
    assertTextPresent("Rol en naam");

  }

  /**
   * Tests the correctness of the answers a search request returns.
   */
  public void testSearchCorrectness() {

    // not yet implemented
    // done by hand
  }

} // end FunctionTest
