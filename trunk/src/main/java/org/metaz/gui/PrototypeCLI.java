package org.metaz.gui;

import java.net.URI;

import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.metaz.domain.MetaData;
import org.metaz.repository.Facade;
import org.metaz.repository.Result;
import org.metaz.util.MetaZ;

import org.apache.log4j.Logger;

import java.util.List;

import java.net.URI;

import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.Record;

/**
 *  This class provide a command line interface to Application Z, an application 
 *  that provides a search facility within multiple CMS's of the Ruud de Moor 
 *  Centrum.
 */
public class PrototypeCLI {

    private static String syntax =
        "PrototypeCLI -option [goal] [[-option goal][..]]";

    private static Logger logger = MetaZ.getLogger(PrototypeCLI.class);


    public PrototypeCLI() {
    }

    public static void main(String[] args) {

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
     * <p> Sends the searchstring to the searchservice through the MetaZ 
     * singleton instance method getRepositoryFacade().
     * @param searchString
     */
    public static void performSearch(String searchString) {
        System.out.println();

        Facade myFacade = MetaZ.getInstance().getRepositoryFacade();
        try {
            List<Result<Record>> resultList = myFacade.doSearch(searchString);
            // Printing of the record list is not yet tested!!!
            for (int i = 0; i < resultList.size(); i++) {
                resultList.remove(i).getObject().getURI().getValue()
                .toString();
            }
        } catch (Exception e) {
            System.out
            .println("Error in result presentation: " + e.toString());
            logger.error("Error in result presentation: " + e.getMessage());
        }
    }

    /**
     * <p>Checks whether the commandline input is a valid searchString.<br>
     * <code>Query ::= (Clause)<sup>+</sup><br>
     * Clause ::= (FullTextSearchString) | &lt;FIELDNAME&gt;:&lt;VALUE&gt;<br></code>
     * @param searchString
     * @return boolean
     */
    public static boolean checkQuery(String searchString) {
        if (searchString == "")
            return false;
        return true;
    }

    /**
     * p>Prints the helpmessage.<br>
     * @param options
     */
    public static void printHelp(Options options) {
        System.out.println();
        HelpFormatter f = new HelpFormatter();
        f.printHelp(syntax, options);
        java.lang.System.exit(0);
    }


    /**
     * Defines the possible CLI options
     * @return Options
     */
    private static Options defineOptions() {
        // Definition of the CLI options
        Options options = new Options();

        Option tOpt = new Option("t", "trefwoorden", false, "Trefwoorden");
        tOpt.setArgs(10);
        tOpt.setType(new String());
        tOpt.setRequired(false);
        options.addOption(tOpt);

        Option gOpt =
            new Option("g", MetaData.TARGETENDUSER, false, "Docent, Begeleider, Manager");
        gOpt.setArgs(5);
        gOpt.setType(new String());
        gOpt.setRequired(false);
        options.addOption(gOpt);

        Option sOpt =
            new Option("s", MetaData.SCHOOLTYPE, false, "Pre-primair onderwijs, Primair onderwijs, Voortgezet onderwijs");
        sOpt.setArgs(5);
        sOpt.setType(new String());
        sOpt.setRequired(false);
        options.addOption(sOpt);

        Option vOpt =
            new Option("v", MetaData.SCHOOLDISCIPLINE, false, "Toepasselijke vakkenlijst");
        vOpt.setArgs(5);
        vOpt.setType(new String());
        vOpt.setRequired(false);
        options.addOption(vOpt);

        Option dOpt =
            new Option("d", MetaData.DIDACTICFUNCTION, false, "Oefening, Simulatie, enz");
        dOpt.setArgs(5);
        dOpt.setType(new String());
        dOpt.setRequired(false);
        options.addOption(dOpt);

        Option pOpt =
            new Option("p", MetaData.PRODUCTTYPE, false, "Document, Afbeelding, Video, enz");
        pOpt.setArgs(5);
        pOpt.setType(new String());
        pOpt.setRequired(false);
        options.addOption(pOpt);

        Option bOpt =
            new Option("b", MetaData.PROFESSIONALSITUATION, false, "...een zeer lange lijst...");
        bOpt.setArgs(5);
        bOpt.setType(new String());
        bOpt.setRequired(false);
        options.addOption(bOpt);

        Option cOpt =
            new Option("c", MetaData.COMPETENCE, false, "Interpersoonlijk, Pedagogisch, enz..");
        cOpt.setArgs(5);
        cOpt.setType(new String());
        cOpt.setRequired(false);
        options.addOption(cOpt);

        Option hOpt =
            new Option("h", "help", false, "Laat deze helptekst zien");
        hOpt.setArgs(0);
        hOpt.setType(new String());
        hOpt.setRequired(false);
        options.addOption(hOpt);

        return options;
    }

    /**
     * <p> Prints a string containing all the clauses. 
     * This is necessary for Lucene.
     * @param cl
     * @param options
     * @return String
     */
    private static String printClauses(CommandLine cl,
                                       Options options) throws ParseException {
        String clausule = "";
        Option o;
        int j = 0;

        // Ordering of the CLI options
        String[] shortOpts = new String[options.getOptions().size()];
        // Onderstaande kan waarschijnlijk efficienter...
        int k = 0;
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
                if (keywords[n].startsWith("-"))
                    throw new ParseException("Unrecognised option \"" +
                                             keywords[n] + "\"");
                clausule += keywords[n] + " ";
            }
            j = 1;
        }

        // metadata valuepairs
        for (int i = j; ((shortOpts[i] != null) && (i < shortOpts.length));
             i++) {
            o = options.getOption(shortOpts[i]);
            String[] valueStr = cl.getOptionValues(shortOpts[i]);
            String value = valueStr[0];
            if (valueStr.length > 1) {
                for (int n = 1; n < valueStr.length; n++) {
                    value += " " + valueStr[n];
                    if (valueStr[n].startsWith("-"))
                        throw new ParseException("Unrecognised option \"" +
                                                 valueStr[n] + "\"");
                }
                value = "\"" + value + "\"";
            }
            clausule += o.getLongOpt() + ":" + value + " ";
        }
        return clausule;
    }


}
