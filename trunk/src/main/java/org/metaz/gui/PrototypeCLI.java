package org.metaz.gui;

import org.apache.commons.cli.*;

import testcli.Testapp;

public class PrototypeCLI {
    public PrototypeCLI() {
    }

    public static void main(String[] args) {
        PrototypeCLI prototypeCLI = new PrototypeCLI();

        Options options = new Options();

        Option idOpt =
            new Option("id", "identity", false, "Identity(Docent|Begeleider|Manager)");
        idOpt.setArgs(1);
        idOpt.setType(new String());
        idOpt.setRequired(false);
        options.addOption(idOpt);

        Option schoolOpt =
            new Option("sc", "school", false, "School Type(Pre-primair onderwijs|Primair onderwijs|Voortgezet onderwijs)");
        schoolOpt.setArgs(1);
        schoolOpt.setType(new String());
        schoolOpt.setRequired(false);
        options.addOption(schoolOpt);

        Option searchOpt = new Option("s", "search", false, "Search string");
        searchOpt.setArgs(1);
        searchOpt.setType(new String());
        searchOpt.setRequired(false);
        options.addOption(searchOpt);

        Option helpOpt =
            new Option("h", "help", false, "Shows the help message");
        helpOpt.setArgs(0);
        helpOpt.setType(new String());
        helpOpt.setRequired(false);
        options.addOption(helpOpt);

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

        String optIdentity = cmd.getOptionValue(idOpt.getOpt());
        String optSchool = cmd.getOptionValue(schoolOpt.getOpt());
        String optSearch = cmd.getOptionValue(searchOpt.getOpt());

        if (optIdentity != null && optSchool != null && optSearch != null)
            performSearch(optIdentity, optSchool, optSearch);
        else {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("ApplicatieZ", options);
        }
    }

    public static void performSearch(String Identity, String schoolType,
                                     String searchString) {

        System.out.println("Here are the results of the Meta/Z jury:");
        System.out.println();
        System.out.println("...we don't know yet...");
    }
}
