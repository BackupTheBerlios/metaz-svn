package org.metaz.gui;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.log4j.Logger;

import org.metaz.domain.MetaData;
import org.metaz.domain.Record;

import org.metaz.repository.Facade;
import org.metaz.repository.Result;

import org.metaz.util.MetaZ;

import java.util.List;

/**
 * <p>This class provide a command line interface to Application Z, an application  that provides a search facility
 * within multiple CMS's of the Ruud de Moor Centrum.</p>
 */
public class PrototypeCLI {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  public static final int MAX_OPTIONS = 10;
  private static String   syntax = "PrototypeCLI -option [goal] [[-option goal][..]]";
  private static Logger   logger = MetaZ.getLogger(PrototypeCLI.class);

  //~ Constructors -----------------------------------------------------------------------------------------------------

  private PrototypeCLI() {

    //empty
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The main method can be run from the command line. It reads the specified options, reformats it, and sends it
   * to the searchservice.
   *
   * @param args The options specified in the command line.
   */
  public static void main(final String[] args) {

    String searchString = "";

    // Definition of the possible CLI options
    Options options = defineOptions();

    BasicParser parser = new BasicParser();
    CommandLine cmd = null;

    // Reading of the input
    try {

      cmd = parser.parse(options, args);

      if (cmd.hasOption("h")) {

        printHelp(options);

      }

    } catch (Exception e) {

      System.out.println();
      System.out.println("Command line option parsing failed");
      System.out.println("Option error: " + e.toString());
      logger.error("Option error: " + e.getMessage());
      printHelp(options);

    }

    // The searchString should have the correct syntax.
    try {

      searchString = printClauses(cmd, options);

    } catch (Exception e) {

      System.out.println(e.getMessage());
      logger.error(e.getMessage());
      printHelp(options);

    }

    // If all is right, send the searchString to the searchService.
    if (checkQuery(searchString)) {

      System.out.println();
      System.out.println("ZOEKSTRING: " + searchString);
      logger.info("ZOEKSTRING: " + searchString);
      performSearch(searchString);

    } else {

      System.out.println();
      System.out.println("Please use the correct syntax.");
      logger.error("Please use the correct syntax.");
      printHelp(options);

    }

  }

  /**
   * <p>Sends the searchstring to the searchservice through the MetaZ  singleton instance method
   * getRepositoryFacade().</p>
   *
   * @param searchString The string which is sent to Lucene
   */
  public static void performSearch(String searchString) {

    System.out.println();

    Facade myFacade = MetaZ.getInstance().getRepositoryFacade();

    try {

      List<Result<Record>> resultList = myFacade.doSearch(searchString);

      // Printing of the record list is not yet tested!!!
      System.out.println("Here are the results of your query:");

      if (resultList.size() == 0) {

        System.out.println("--> No records were found.");

      }

      for (int i = 0; i < resultList.size(); i++) {

        Record record = resultList.get(i).getObject();

        System.out.println(i + ". " + record.getTitle().getValue() + " " + record.getURI().getValue());

      }

    } catch (Exception e) {

      System.out.println("Error in result presentation: " + e.toString());
      logger.error("Error in result presentation: " + e.getMessage());

    }

  }

  /**
   * <p>Checks whether the commandline input is a valid searchString.<br>
   * <code>Query ::= (Clause)<sup>+</sup><br>
   * *  Clause ::= (FullTextSearchString) | &lt;FIELDNAME&gt;:&lt;VALUE&gt;<br>
   * * </code></p>
   *
   * @param searchStr The string which is sent to Lucene
   *
   * @return boolean
   */
  public static boolean checkQuery(String searchStr) {

    // FIXME (no checks done, except for empty String)
    if (searchStr == "") {

      return false;

    }

    return true;

  }

  /**
   * <p>Prints the helpmessage.<br></p>
   *
   * @param opts The available options and syntax for PrototypeCLI
   */
  public static void printHelp(Options opts) {

    System.out.println();

    HelpFormatter f = new HelpFormatter();

    f.printHelp(syntax, opts);
    java.lang.System.exit(0);

  }

  /**
   * <p>The method defines the CLI options that are allowed as arguments.</p>
   *
   * @return Options The options of PrototypeCLI
   */
  private static Options defineOptions() {

    // Definition of the CLI options
    Options options = new Options();

    Option  tOpt = new Option("t", "trefwoorden", false, "Trefwoorden");

    tOpt.setArgs(MAX_OPTIONS);
    tOpt.setType(new String());
    tOpt.setRequired(false);
    options.addOption(tOpt);

    Option gOpt = new Option("g", MetaData.TARGETENDUSER, false, "Docent, Begeleider, Manager");

    gOpt.setArgs(MAX_OPTIONS);
    gOpt.setType(new String());
    gOpt.setRequired(false);
    options.addOption(gOpt);

    Option sOpt = new Option("s", MetaData.SCHOOLTYPE, false,
                             "Pre-primair onderwijs, Primair onderwijs, Voortgezet onderwijs");

    sOpt.setArgs(MAX_OPTIONS);
    sOpt.setType(new String());
    sOpt.setRequired(false);
    options.addOption(sOpt);

    Option vOpt = new Option("v", MetaData.SCHOOLDISCIPLINE, false, "Toepasselijke vakkenlijst");

    vOpt.setArgs(MAX_OPTIONS);
    vOpt.setType(new String());
    vOpt.setRequired(false);
    options.addOption(vOpt);

    Option dOpt = new Option("d", MetaData.DIDACTICFUNCTION, false, "Oefening, Simulatie, enz");

    dOpt.setArgs(MAX_OPTIONS);
    dOpt.setType(new String());
    dOpt.setRequired(false);
    options.addOption(dOpt);

    Option pOpt = new Option("p", MetaData.PRODUCTTYPE, false, "Document, Afbeelding, Video, enz");

    pOpt.setArgs(MAX_OPTIONS);
    pOpt.setType(new String());
    pOpt.setRequired(false);
    options.addOption(pOpt);

    Option bOpt = new Option("b", MetaData.PROFESSIONALSITUATION, false, "...een zeer lange lijst...");

    bOpt.setArgs(MAX_OPTIONS);
    bOpt.setType(new String());
    bOpt.setRequired(false);
    options.addOption(bOpt);

    Option cOpt = new Option("c", MetaData.COMPETENCE, false, "Interpersoonlijk, Pedagogisch, enz..");

    cOpt.setArgs(MAX_OPTIONS);
    cOpt.setType(new String());
    cOpt.setRequired(false);
    options.addOption(cOpt);

    Option hOpt = new Option("h", "help", false, "Laat deze helptekst zien");

    hOpt.setArgs(0);
    hOpt.setType(new String());
    hOpt.setRequired(false);
    options.addOption(hOpt);

    return options;

  }

  /**
   * <p>Prints a string containing all the clauses.  This is necessary for Lucene.</p>
   *
   * @param cl The commandline object
   * @param options The specified options
   *
   * @return String
   *
   * @throws ParseException Parsing error
   */
  private static String printClauses(CommandLine cl, Options options)
                              throws ParseException
  {

    String clausule = "";
    Option o;
    int    j = 0;

    // Ordering of the CLI options
    String[] shortOpts = new String[options.getOptions().size()];
    int      k = 0;

    if (cl.hasOption("t")) {

      shortOpts[k] = "t";
      k++;

    }

    if (cl.hasOption("g")) {

      shortOpts[k] = "g";
      k++;

    }

    if (cl.hasOption("s")) {

      shortOpts[k] = "s";
      k++;

    }

    if (cl.hasOption("v")) {

      shortOpts[k] = "v";
      k++;

    }

    if (cl.hasOption("d")) {

      shortOpts[k] = "d";
      k++;

    }

    if (cl.hasOption("p")) {

      shortOpts[k] = "p";
      k++;

    }

    if (cl.hasOption("b")) {

      shortOpts[k] = "b";
      k++;

    }

    if (cl.hasOption("c")) {

      shortOpts[k] = "c";

    }

    // the freetext searchstring
    if (shortOpts[j] == "t") {

      o = options.getOption(shortOpts[0]);

      String[] keywords = cl.getOptionValues(shortOpts[j]);

      for (int n = 0; n < keywords.length; n++) {

        if (keywords[n].startsWith("-")) {

          throw new ParseException("Unrecognised option \"" + keywords[n] + "\"");

        }

        clausule += (keywords[n] + " ");

      }

      j = 1;

    }

    // metadata valuepairs
    for (int i = j; ((shortOpts[i] != null) && (i < shortOpts.length)); i++) {

      o = options.getOption(shortOpts[i]);

      String[] valueStr = cl.getOptionValues(shortOpts[i]);
      String   value = valueStr[0];

      if (valueStr.length > 1) {

        for (int n = 1; n < valueStr.length; n++) {

          value += (" " + valueStr[n]);

          if (valueStr[n].startsWith("-")) {

            throw new ParseException("Unrecognised option \"" + valueStr[n] + "\"");

          }

        }

        value = "\"" + value + "\"";

      }

      clausule += (o.getLongOpt() + ":" + value + " ");

    }

    return clausule;

  }

}
