package org.metaz.gui;

import org.apache.commons.cli.*;

import org.metaz.repository.SearchServiceImpl;

import java.util.List;


public class PrototypeCLI {
    public PrototypeCLI() {
    }

    public static void main(String[] args) {
        //PrototypeCLI prototypeCLI = new PrototypeCLI();

        Options options = new Options();

        Option gOpt =
            new Option("g", "gebruiker", false, "Gebruiker(Docent|Begeleider|Manager)");
        gOpt.setArgs(1);
        gOpt.setType(new String());
        gOpt.setRequired(false);
        options.addOption(gOpt);

        Option sOpt =
            new Option("s", "schooltype", false, "Schooltype(Pre-primair onderwijs|Primair onderwijs|Voortgezet onderwijs)");
        sOpt.setArgs(1);
        sOpt.setType(new String());
        sOpt.setRequired(false);
        options.addOption(sOpt);

        Option vOpt =
            new Option("v", "vakleergebied", false, "Vakleergebied(Toepasselijke vakkenlijst)");
        vOpt.setArgs(1);
        vOpt.setType(new String());
        vOpt.setRequired(false);
        options.addOption(vOpt);

        Option dOpt =
            new Option("d", "didactischeFunctie", false, "Didactische Functie(Oefening, Simulatie, enz)");
        dOpt.setArgs(1);
        dOpt.setType(new String());
        dOpt.setRequired(false);
        options.addOption(dOpt);

        Option pOpt =
            new Option("p", "producttype", false, "Producttype(Document, Afbeelding, Video, enz)");
        pOpt.setArgs(1);
        pOpt.setType(new String());
        pOpt.setRequired(false);
        options.addOption(pOpt);

        Option bOpt =
            new Option("b", "beroepssituatie", false, "Beroepssituatie(...een zeer lange lijst...)");
        bOpt.setArgs(1);
        bOpt.setType(new String());
        bOpt.setRequired(false);
        options.addOption(bOpt);

        Option cOpt =
            new Option("c", "competentie", false, "Competentie(Interpersoonlijk, Pedagogisch, enz...)");
        cOpt.setArgs(1);
        cOpt.setType(new String());
        cOpt.setRequired(false);
        options.addOption(cOpt);

        Option tOpt = new Option("t", "trefwoorden", false, "Trefwoorden");
        tOpt.setArgs(0);
        tOpt.setType(new String());
        tOpt.setRequired(false);
        options.addOption(tOpt);

        Option hOpt = new Option("h", "help", false, "Laat de helptekst zien");
        hOpt.setArgs(0);
        hOpt.setType(new String());
        hOpt.setRequired(false);
        options.addOption(hOpt);

        BasicParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (Exception e) {
            System.out.println("Command line option parsing failed");
            System.out.println("Option error: " + e.toString());
            HelpFormatter f = new HelpFormatter();
            f.printHelp("ApplicatieZ", options);
            java.lang.System.exit(0);
        }

        // Nog doen: koppeling met MetaData klasse?
        String optGebruiker =
            gOpt.getOpt() + ":" + cmd.getOptionValue(gOpt.getValue());
        String optSchool =
            sOpt.getOpt() + ":" + cmd.getOptionValue(sOpt.getValue());
        String optVakleergebied =
            vOpt.getOpt() + ":" + cmd.getOptionValue(vOpt.getValue());
        String optDidactischeFunctie =
            dOpt.getOpt() + ":" + cmd.getOptionValue(dOpt.getValue());
        String optProducttype =
            pOpt.getOpt() + ":" + cmd.getOptionValue(pOpt.getValue());
        String optBeroepsituatie =
            bOpt.getOpt() + ":" + cmd.getOptionValue(bOpt.getValue());
        String optCompetentie =
            cOpt.getOpt() + ":" + cmd.getOptionValue(cOpt.getValue());
        String optTrefwoorden = cmd.getOptionValue(tOpt.getValue());

        String searchString =
            optGebruiker + optSchool + optVakleergebied + optVakleergebied +
            optDidactischeFunctie + optProducttype + optBeroepsituatie +
            optCompetentie;

        if (checkQuery(searchString))
            performSearch(searchString);
        //   else {
        //       HelpFormatter f = new HelpFormatter();
        //       f.printHelp("ApplicatieZ", options);
    }

    public static void performSearch(String searchString) {
        System.out.println("Here are the results of the Meta/Z jury:");
        System.out.println();
        SearchServiceImpl searchServiceImpl = new SearchServiceImpl();
        if (checkQuery(searchString)) {
            List list = searchServiceImpl.doSearch("een test");
            if (list != null)
                // link naar repository werkt nog niet...
                for (int i = 0; i < list.size(); i++)
                    System.out.println(list.toString());

        }
    }

    /**
     * Checks whether the commandline input is a valid searchString.
     * Query ::= (FullTextSearchString) | (Clause)*
     * Clause ::= <FIELDNAME>:<VALUE>
     * @param searchString
     * @return boolean
     */
    public static boolean checkQuery(String searchString) {

        return true;
    }


}
