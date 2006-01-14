package org.metaz.harvester;

import org.apache.log4j.Logger;
import org.metaz.util.MetaZ;

import java.io.*;
//import java.util.*;

import org.dom4j.Document;
//import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
//import org.dom4j.io.DOMReader;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.VerifierHandler;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import com.sun.msv.verifier.jarv.TheFactoryImpl;



/**
 * @author Lars Oosterloo
 * @version 0.1
 * The Harvester class is responsible for
 * parsing xml data files into a collection
 * of LearningObjects and passing this collection
 * to the repository interface
 *
 */
public class Harvester {
	
	private static Logger logger = MetaZ.getLogger(Harvester.class);
	private final static String APPLICATIONZ_SCHEMA="metaz.xsd";
	protected String xmlfile;
	
	/**
	 * @param args arguments, first one should contain xml file to parse
	 */
	public static void main(String[] args) {

		if (args.length==1){
			//initiate the Harvester class
			Harvester harvester = new Harvester();
			//parse filename from args
			String filename=args[0].toString();
			harvester.setXMLFile(filename);
		}
	}
	
	/**
	 * Constructor
	 */
	private Harvester(){
		

	}
	
	/**
	 * 
	 * @param f
	 */
	protected void setXMLFile(String f){
		xmlfile = f;
		parseFile(xmlfile);
	}
	
	/**
	 * 
	 * @param f
	 */
	private void parseFile(String f){
		try{
		//validate and create document
		//Document doc = 
			getDom4jDocument(f);
		//convert to collection of LearningObjects
		//iterate throught the xml
		//add every learnobject to the collection
		//release file
		//call repository interface with collection
		}
		catch (Exception ex){
			logger.error(ex.getMessage());
		}
	}
	
/**
 * Converts a file in the filesystem into a DOM4J document
 * ToDo: add validation against a schema
 * @param f filename of the file to convert to a DOM4J document
 * @return the DOM4J document to return, null if an error occurs
 */
	private Document getDom4jDocument(String f) throws Exception{
		{
			Document document=null;
			try{
				SAXReader reader = new SAXReader();
				document = reader.read(new File(f));
				processValidation(document);			
			}
			catch (DocumentException ex){
				logger.error(ex.getMessage());
				return null;
			}
			catch (Exception exc){
				logger.error(exc.getMessage());
				return null;
			}
			return document;
		}

	}

/**
 * Validate document using MSV
 * 
 * @param document
 * @param schemaURI
 * @throws Exception
 */	
    protected void processValidation(Document document) throws Exception {

        logger.info( "Loaded schema document: " + APPLICATIONZ_SCHEMA );
        
        // use autodetection of schemas
        VerifierFactory factory = new TheFactoryImpl();
        Schema schema = factory.compileSchema( APPLICATIONZ_SCHEMA );
                  
        Verifier verifier = schema.newVerifier();
        verifier.setErrorHandler(
            new ErrorHandler() {
                public void error(SAXParseException e) {
                    logger.error( e.getMessage() );
                }
                
                public void fatalError(SAXParseException e) {
                    logger.fatal( e.getMessage() );
                }
                
                public void warning(SAXParseException e) {
                    logger.warn(e.getMessage() );
                }
            }
        );
        
        logger.info( "Validating XML document" );
        
        VerifierHandler handler = verifier.getVerifierHandler();
        SAXWriter writer = new SAXWriter( handler );
        writer.write( document );
        logger.info("Wrote verifier to xml");
    }

	
}
