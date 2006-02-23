package org.metaz.test.toxgene;

import org.metaz.util.MetaZ;

import toxgene.core.Engine;
import toxgene.core.ToXgeneErrorException;

import toxgene.interfaces.ToXgeneCdataDescriptor;
import toxgene.interfaces.ToXgeneDocumentCollection;
import toxgene.interfaces.ToXgeneSession;

import toxgene.util.ToXgeneReporterImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.text.DecimalFormat;

import java.util.Vector;


/**
 * Generates a test xml file.
 * The formatting is done in the template file.
 *
 * @author Sammy Dalewyn    
 * @version 1.0
  */
public class GenerateTestXml
{
    private static Engine tgEngine;
    private static ToXgeneReporterImpl tgReporter;
    private static String template;
    private static MetaZ app;
    private static Vector descriptors;

    /**
     * Creates a new GenerateTestXml object.
     */
    public GenerateTestXml()
    {
    } // end GenerateTestXml()

    /**
     * The main function
     *
     * @param argv none
     */
    public static void main(String[] argv)
    {
        if (argv.length == 0) {
            System.out.println("Usage: GenerateTestXml <template file>\n");
            System.exit(0);
        }
        try
        {
            //template = "test/toxgene/records.tsl";
            template = argv[0];
            app = MetaZ.getInstance();

            ToXgeneCdataDescriptor dutchDescriptor = new DutchWordsCdataDescriptor();
            descriptors = new Vector();
            descriptors.add(dutchDescriptor);

            ToXgeneCdataDescriptor gibberishDescr = new ToXgeneCdataDescriptor();
            gibberishDescr.cdataClass = "toxgene.core.genes.literals.ToxString";
            gibberishDescr.cdataName = "gibberish";
            gibberishDescr.minLength = 0;
            gibberishDescr.maxLength = 200;
            descriptors.add(gibberishDescr);

            tgEngine = new Engine();

            boolean verbose = true; /* useful for debugging
              * templates */

            boolean showWarnings = true;
            tgReporter = new ToXgeneReporterImpl(verbose, showWarnings);

            /*
             *
             * if (System.getProperty("TOXGENE_HOME") == null)
             * {
             *   System.out.println("\n***** WARNING: " +
             *       "TOXGENE_HOME property is not set. " +
             *       "ToXgene will attempt to load\n" +
             *       "toxgene.jar/config/cdata.xml assuming" +
             *       "toxgene.jar is in the current " +
             *       "directory.\n\nUse java " + "-DToXgene_home=<path>... " +
             *       "to override this.");
             *} // end if
             */

            /*
             * The ToXgeneSession specifies all parameters the
             * engine needs for generating the documents.
             */
            ToXgeneSession session = new ToXgeneSession();
            session.reporter = tgReporter;
            session.initialSeed = 123456;
            session.addNewLines = true;
            session.inputPath = "./";
            session.usePOM = false;
            session.pomBufferPath = ".";
            session.pomMemFracBuffer = (float) 0.5;
            session.pomBufferSize = 8 * 1024;
            session.cdataDescriptors = descriptors;

            /* Initialize the engine */
            tgEngine.startSession(session);

            /*
             * The progress() method sends a progress report
             * message to the message handler.
             */
            tgReporter.progress("Parsing template: ");

            File f = app.getRelativeFile(template);
            tgEngine.parseTemplate(new FileInputStream(f));
            tgReporter.progress("Done !\n");

            /*
             * The generateLists() method tells the engine to
             * generate all temporary data declared in tox-list
             * elements in the template. Calling this method is
             * optional, ToXgene will materialize all temporary
             * data if needed even if generateLists is not
             * invoked.
             */
            tgEngine.generateLists();
            generateCollections();
        } // end try
        catch (ToXgeneErrorException e1)
        {
            error(e1.getMessage());
        } // end catch
        catch (FileNotFoundException e)
        {
            tgEngine.endSession();
            error("cannot open template file \"" + template + "\"");
        } // end catch
        catch (Exception e)
        {
            e.printStackTrace();
        } // end catch

        tgEngine.endSession();

        int nWarnings = tgReporter.warnings();

        if (nWarnings > 0)
        {
            System.out.println("There were " + nWarnings + " warning(s).");
            tgReporter.printAllWarnings();
        } // end if

        System.exit(0);
    } // end main()

    /**
     * Scans the collections declared in the template and generates the
     * XML documents they specify on files.
     *
     * @throws ToXgeneErrorException when the xml document can not be generated
     */
    private static void generateCollections()
    {
        Vector collections = tgEngine.getToXgeneDocumentCollections();
        String outputPath = "./"; /* Path where to put the
         * documents */

        if (collections.size() == 0)
        {
            tgReporter.warning("no document genes found");

            return;
        } // end if

        /* Iterate over all collections in the template */
        for (int i = 0; i < collections.size(); i++)
        {
            ToXgeneDocumentCollection tgColl = (ToXgeneDocumentCollection) collections.get(i);

            /*
             * Test whether this collection has more than one
             * document
             */
            if (tgColl.getSize() > 1)
            {
                int start = tgColl.getStartingNumber();
                int documents = tgColl.getSize();
                DecimalFormat nf = new DecimalFormat("0;0");

                tgReporter.progress("Generating collection: " +
                    tgColl.getName());

                String current;
                int count = 0;
                int sum = 0;

                for (int j = start; j < (start + documents); j++)
                {
                    current = outputPath + tgColl.getName() + nf.format(j) +
                        ".xml";

                    try
                    {
                        /*
                         * Create a file for storing
                         * this document; note that
                         * any PrintStrem object
                         * would work here as far as
                         * ToXgene is concerned.
                         */
                        PrintStream outStream = new PrintStream(new FileOutputStream(
                                    current));
                        /*
                         * The materialize() method
                         * "prints" the document into
                         * the given PrintStream
                         * object.
                         */
                        tgEngine.materialize(tgColl, outStream);
                    } // end try
                    catch (Exception e)
                    {
                        /*
                         * The endSession() method
                         * tells ToXgene's engine to
                         * clean up, e.g., temporary
                         * files it may have created.
                         */
                        tgEngine.endSession();
                        error("Couldn't create " + current);
                    } // end catch
                } // end for

                tgReporter.progress(" ...Done!\n");
            } // end if
            else
            {
                /*
                 * In this case, the collection has a single
                 * document
                 */
                tgReporter.progress("Generating document \"" +
                    tgColl.getName() + ".xml\"");

                String file = outputPath + tgColl.getName() + ".xml";

                try
                {
                    //PrintStream outStream = new PrintStream(new FileOutputStream(file));
                    PrintStream outStream = new PrintStream(new FileOutputStream(file),true,"US-ASCII");
                    tgEngine.materialize(tgColl, outStream);
                } // end try
                catch (Exception e)
                {
                    tgEngine.endSession();
                    throw new ToXgeneErrorException("Couldn't create " + file);
                } // end catch

                tgReporter.progress(" ...Done!\n");
            } // end else
        } // end for
    } // end generateCollections()

    /**
     * Prints an error message and aborts.
     *
     * @param msg the error message
     */
    public static void error(String msg)
    {
        tgReporter.printAllWarnings();
        System.out.println("\n***** ERROR: " + msg);
        System.exit(1);
    } // end error()
} // end GenerateTestXml
