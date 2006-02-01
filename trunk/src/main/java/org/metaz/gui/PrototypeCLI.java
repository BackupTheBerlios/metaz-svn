package org.metaz.gui;

import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.metaz.domain.MetaData;
import org.metaz.repository.Facade;
import org.metaz.util.MetaZ;


public class PrototypeCLI {

    private static String syntax =
        "PrototypeCLI -option [goal] [[-option goal][..]]";

    public PrototypeCLI() {
    }

    public static void main(String[] args) {
        //PrototypeCLI prototypeCLI = new PrototypeCLI();

        // Definition of the possible CLI options
        Options options = defineOptions();

        BasicParser parser = new BasicParser();
        CommandLine cmd = null;

        // Reading of the input
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter f = new HelpFormatter();
                System.out.println();
                f.printHelp(syntax, options);
                java.lang.System.exit(0);
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("Command line option parsing failed");
            System.out.println("Option error: " + e.toString());
            System.out.println();
            HelpFormatter f = new HelpFormatter();
            f.printHelp(syntax, options);
            java.lang.System.exit(0);
        }

        // The searchString should have the correct syntax.
        String searchString = printClauses(cmd, options);

        // If all is right, send the searchString to the searchService.
        if (checkQuery(searchString))
            performSearch(searchString);
        else
            System.out.println("fout in checkQuery");
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
            List list = myFacade.doSearch(searchString);
            System.out.println(list.toString());
        } catch (Exception e) {
            // TODO
            System.out.println("niet gelukt");
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
        hOpt.setArgs(5);
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
    private static String printClauses(CommandLine cl, Options options) {
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
            for (int n = 0; n < keywords.length; n++)
                clausule += keywords[n] + " ";
            j = 1;
        }

        // metadata valuepairs
        for (int i = j; ((shortOpts[i] != null) && (i < shortOpts.length));
             i++) {
            o = options.getOption(shortOpts[i]);
            String[] valueStr = cl.getOptionValues(shortOpts[i]);
            String value = valueStr[0];
            if (valueStr.length > 1) {
                for (int n = 1; n < valueStr.length; n++)
                    value += " " + valueStr[n];
                value = "\"" + value + "\"";
            }
            clausule += o.getLongOpt() + ":" + value + " ";
        }
        System.out.println();
        System.out.println("ZOEKSTRING: " + clausule);
        return clausule;
    }


}
