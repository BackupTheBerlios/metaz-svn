package org.metaz.gui;

import org.apache.commons.cli.*;

import org.metaz.domain.MetaData;
import org.metaz.repository.*;

//import java.util.List;


public class PrototypeCLI {
    public PrototypeCLI() {
    }

    public static void main(String[] args) {
        //PrototypeCLI prototypeCLI = new PrototypeCLI();

        Options options = new Options();

        Option tOpt = new Option("t", "trefwoorden", false, "Trefwoorden");
        tOpt.setArgs(1);
        tOpt.setType(new String());
        tOpt.setRequired(false);
        options.addOption(tOpt);

        Option gOpt =
            new Option("g", MetaData.TARGETENDUSER, false, "Docent, Begeleider, Manager");
        gOpt.setArgs(1);
        gOpt.setType(new String());
        gOpt.setRequired(false);
        options.addOption(gOpt);

        Option sOpt =
            new Option("s", MetaData.SCHOOLTYPE, false, "Pre-primair onderwijs, Primair onderwijs, Voortgezet onderwijs");
        sOpt.setArgs(1);
        sOpt.setType(new String());
        sOpt.setRequired(false);
        options.addOption(sOpt);

        Option vOpt =
            new Option("v", MetaData.SCHOOLDISCIPLINE, false, "Toepasselijke vakkenlijst");
        vOpt.setArgs(1);
        vOpt.setType(new String());
        vOpt.setRequired(false);
        options.addOption(vOpt);

        Option dOpt =
            new Option("d", MetaData.DIDACTICFUNCTION, false, "Oefening, Simulatie, enz");
        dOpt.setArgs(1);
        dOpt.setType(new String());
        dOpt.setRequired(false);
        options.addOption(dOpt);

        Option pOpt =
            new Option("p", MetaData.PRODUCTTYPE, false, "Document, Afbeelding, Video, enz");
        pOpt.setArgs(1);
        pOpt.setType(new String());
        pOpt.setRequired(false);
        options.addOption(pOpt);

        Option bOpt =
            new Option("b", MetaData.PROFESSIONALSITUATION, false, "...een zeer lange lijst...");
        bOpt.setArgs(1);
        bOpt.setType(new String());
        bOpt.setRequired(false);
        options.addOption(bOpt);

        Option cOpt =
            new Option("c", MetaData.COMPETENCE, false, "Interpersoonlijk, Pedagogisch, enz..");
        cOpt.setArgs(1);
        cOpt.setType(new String());
        cOpt.setRequired(false);
        options.addOption(cOpt);

        Option hOpt = new Option("h", "help", false, "Laat de helptekst zien");
        hOpt.setArgs(0);
        hOpt.setType(new String());
        hOpt.setRequired(false);
        options.addOption(hOpt);

        BasicParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp("ApplicatieZ", options);
                java.lang.System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Command line option parsing failed");
            System.out.println("Option error: " + e.toString());
            HelpFormatter f = new HelpFormatter();
            f.printHelp("ApplicatieZ", options);
            java.lang.System.exit(0);
        }


        String searchStr = "";
        // Nog doen: koppeling met MetaData klasse!
        if (cmd.hasOption("t")) {
            String[]trefwrds = cmd.getOptionValues(tOpt.getOpt());
            String optTrefwoorden = "";
            for (int i=0;i<trefwrds.length;i++) 
              optTrefwoorden+= trefwrds[i]+" ";
            searchStr += optTrefwoorden;
        }
        if (cmd.hasOption("g")) {
            String optGebruiker =
                MetaData.TARGETENDUSER + ":" + cmd.getOptionValue(gOpt.getOpt()) +
                " ";
            searchStr += optGebruiker;
        }
        if (cmd.hasOption("s")) {
            String optSchool =
                MetaData.SCHOOLTYPE + ":" + cmd.getOptionValue(sOpt.getOpt()) +
                " ";
            searchStr += optSchool;
        }
        if (cmd.hasOption("v")) {
            String optVakleergebied =
                MetaData.SCHOOLDISCIPLINE + ":" + cmd.getOptionValue(vOpt
                                                                                           .getOpt()) +
                " ";
            searchStr += optVakleergebied;
        }
        if (cmd.hasOption("d")) {
            String optDidactischeFunctie =
                MetaData.DIDACTICFUNCTION + ":" + cmd
                .getOptionValue(dOpt.getOpt()) + " ";
            searchStr += optDidactischeFunctie;
        }
        if (cmd.hasOption("p")) {
            String optProducttype =
                MetaData.PRODUCTTYPE + ":" + cmd.getOptionValue(pOpt
                                                                                    .getOpt()) +
                " ";
            searchStr += optProducttype;
        }
        if (cmd.hasOption("b")) {
            String optBeroepsituatie =
                MetaData.PROFESSIONALSITUATION + ":" + cmd
                .getOptionValue(bOpt.getOpt()) + " ";
            searchStr += optBeroepsituatie;
        }
        if (cmd.hasOption("c")) {
            String optCompetentie =
                MetaData.COMPETENCE + ":" + cmd.getOptionValue(cOpt.getOpt()) +
                " ";
            searchStr += optCompetentie;
        }
        if (checkQuery(searchStr))
            performSearch(searchStr);
    }

    public static void performSearch(String searchString) {
        System.out.println();
        System.out.println(searchString);
        System.out.println();
        Facade myFacade = FacadeFactory.createFacade();
        try {
            myFacade.doSearch(searchString);
        } catch (Exception e) {
            // TODO
        }
    }

    /**
     * <p>Checks whether the commandline input is a valid searchString.<br>
     * <code>Query ::= (FullTextSearchString) | (Clause)*<br>
     * Clause ::= &lt;FIELDNAME&gt;:&lt;VALUE&gt;<br></code>
     * @param searchString
     * @return boolean
     */
    public static boolean checkQuery(String searchString) {
        if (searchString == "")
            return false;
        return true;
    }


}
