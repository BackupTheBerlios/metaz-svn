package org.metaz.harvester;

import org.apache.log4j.Logger;
import org.metaz.util.MetaZ;

import java.io.*;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.dom4j.io.DOMReader;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.VerifierHandler;
import com.sun.msv.grammar.relaxng.datatype.*;



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
	private final static String APPLICATIONZ_SCHEMA="xml/schema/metaz.xsd";
	private final static String APPLICATIONZ_TRANSFER_PATH="xml/transfer";
	private final static String APPLICATIONZ_PROCESSED_PATH="xml/log/processed";
	private final static String APPLICATIONZ_REJECTED_PATH="xml/log/error";
	private final static String APPLICATIONZ_TRANSFERSTAGING_PATH="xml/transferstaging";
	protected File xmlfile;
	
	/**
	 * @param args arguments, first one should contain xml file to parse
	 */
	public static void main(String[] args) {
		String filename="koppeling.xml";
		if (args.length==1){
			
			//parse filename from args
			filename=args[0].toString();
		}
		//initiate the Harvester class
		Harvester harvester = new Harvester();
		harvester.setXMLFile(filename);
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
		//copy file to gain exclsive rights
		MetaZ app = MetaZ.getInstance();
		try{
			File xmlfile = app.getRelativeFile(APPLICATIONZ_TRANSFER_PATH+ "/" + f);
			logger.info(xmlfile.getAbsoluteFile().toString());
			if (xmlfile.canWrite()){
				xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_TRANSFERSTAGING_PATH+ "/" + f));
				logger.info("after rename" + xmlfile.getAbsolutePath());
				xmlfile = app.getRelativeFile(APPLICATIONZ_TRANSFERSTAGING_PATH + "/" + f);
				parseFile(xmlfile);
				logger.info("after parsing" + xmlfile.getAbsolutePath());
				long timestamp = System.currentTimeMillis();
				xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_PROCESSED_PATH+ "/" + Long.toString(timestamp) + f));
			}
			else {
				logger.fatal("The file " + xmlfile.getName() + " was locked!");
			}
		}
		catch (Exception ex){
			logger.error(ex.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @param f
	 */
	private boolean parseFile(File f){
		try{
		//validate and create document
			logger.info("start parsing");
		Document doc = getDom4jDocument(f);
		logger.info("doc created");
		//convert to collection of LearningObjects
		//test
		Element root = doc.getRootElement();
		// iterate through child elements of root
		for ( Iterator i = root.elementIterator(); i.hasNext(); )
		{
			Element element = (Element) i.next();
			// do something
			System.out.println("Element Name:"+element.getQualifiedName() );
			System.out.println("Element Value:"+element.getText());
		}
		//iterate throught the xml
		//add every learnobject to the collection
		//release file
		
		//call repository interface with collection
			return true;
		}
		catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return false;
	}
	
/**
 * Converts a file in the filesystem into a DOM4J document
 * 
 * @param f filename of the file to convert to a DOM4J document
 * @return the DOM4J document to return, null if an error occurs
 */
	private Document getDom4jDocument(File f) throws Exception{
		{
			Document document=null;
			try{
				SAXReader reader = new SAXReader();
				document = reader.read(f);
				logger.info("doc read");
				processValidation(document);
				logger.info("document created and validated");
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
    	MetaZ app = MetaZ.getInstance();
        logger.info( "Loaded schema document: " + APPLICATIONZ_SCHEMA ); 
        // use autodetection of schemas
        VerifierFactory factory = new TheFactoryImpl();
        logger.info("after validation factory");
        Schema schema = factory.compileSchema( app.getRelativeFile(APPLICATIONZ_SCHEMA));
        logger.info("schema loaded");          
        Verifier verifier = schema.newVerifier();
        logger.info("verifier created");
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
