package org.metaz.harvester;

import com.sun.msv.verifier.jarv.TheFactoryImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.dom4j.io.XMLWriter;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.VerifierHandler;

import org.metaz.domain.BooleanMetaData;
import org.metaz.domain.DateMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaData;
import org.metaz.domain.HierarchicalStructuredTextMetaDataSet;
import org.metaz.domain.HtmlTextMetaData;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.MetaData;
import org.metaz.domain.NumericMetaData;
import org.metaz.domain.Record;
import org.metaz.domain.RecordAttributeSetter;
import org.metaz.domain.TextMetaData;
import org.metaz.util.MetaZ;


//import org.dom4j.io.OutputFormat;
//import com.sun.msv.grammar.relaxng.datatype.*;
//import org.xml.sax.ErrorHandler;
//import org.xml.sax.SAXParseException;
//import java.io.*;
//import java.util.*;

/**
 * The  Harvester class initiates a check of an offered xml file to check on xml schema conformity, extract all
 * Learnobjects, transform them to records of metadata and pass them to the repository facade. The Harvester class is
 * responsible for parsing xml data files into a  collection of LearningObjects  and passing this collection to the
 * repository interface
 *
 * @author Lars Oosterloo
 * @version 0.1
 */
public class Harvester {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger logger = MetaZ.getLogger(Harvester.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // default file and directory settings
  // private final static String APPLICATIONZ_SCHEMA = "xml/schema/metaz.xsd";
  // private final static String APPLICATIONZ_TRANSFER_PATH = "xml/transfer";
  // private final static String APPLICATIONZ_PROCESSED_PATH = "xml/log/processed";
  // private final static String APPLICATIONZ_REJECTED_PATH = "xml/log/error";
  // private final static String APPLICATIONZ_TRANSFERSTAGING_PATH = "xml/transferstaging";
  
  private final String APPLICATIONZ_SCHEMA = "xml/schema/metaz.xsd";
  private final String APPLICATIONZ_TRANSFER_PATH = "xml/transfer";

  // NOTE: if the default transfer path is changed, it has to be changed in the MetazScheduler as well!
  
  private final String APPLICATIONZ_PROCESSED_PATH = "xml/log/processed";
  private final String APPLICATIONZ_REJECTED_PATH = "xml/log/error";
  private final String APPLICATIONZ_TRANSFERSTAGING_PATH = "xml/transferstaging";
  private File         xmlfile;

  // file and directory settings to be read from runtime properties file (metaz.props)
  
  private String applicationz_schema_prop;
  private String applicationz_transfer_path_prop;
  private String applicationz_processed_path_prop;
  private String applicationz_rejected_path_prop;
  private String applicationz_transferstaging_path_prop;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Constructor
   * Attempts to read file and directory settings from runtime properties file (metaz.props)
   * and to set the corresponding attributes. If this is not successful, the default
   * values are set.
   */
  public Harvester() {

    MetaZ app = MetaZ.getInstance();

    applicationz_schema_prop = app.getProperties().getProperty("applicationz.schema");

    if (applicationz_schema_prop == null)
      applicationz_schema_prop = APPLICATIONZ_SCHEMA;

    applicationz_transfer_path_prop = app.getProperties().getProperty("applicationz.transfer.path");

    if (applicationz_transfer_path_prop == null)
      applicationz_transfer_path_prop = APPLICATIONZ_TRANSFER_PATH;

    applicationz_processed_path_prop = app.getProperties().getProperty("applicationz.processed.path");

    if (applicationz_processed_path_prop == null)
      applicationz_processed_path_prop = APPLICATIONZ_PROCESSED_PATH;

    applicationz_rejected_path_prop = app.getProperties().getProperty("applicationz.rejected.path");

    if (applicationz_rejected_path_prop == null)
      applicationz_rejected_path_prop = APPLICATIONZ_REJECTED_PATH;

    applicationz_transferstaging_path_prop = app.getProperties().getProperty("applicationz.transferstaging.path");

    if (applicationz_transferstaging_path_prop == null)
      applicationz_transferstaging_path_prop = APPLICATIONZ_TRANSFERSTAGING_PATH;

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  // ~ Methods
  // ----------------------------------------------------------------------------------------------------------
  /**
   * Only used as a temporary start-up
   *
   * @param args arguments, first one should contain xml file to parse
   */
  public static void main(String[] args) {

    String filename = "koppeling.xml";

    if (args.length == 1) {

      //parse filename from args
      filename = args[0].toString();

    }

    //initiate the Harvester class
    Harvester harvester = new Harvester();

    harvester.setXMLFile(filename);

  }

  /**
   * sets the name of the file to parse and initiates parsing of the file
   *
   * @param f filename to parse
   */
  protected void setXMLFile(String f) {

    //copy file to gain exclusive rights
    MetaZ app = MetaZ.getInstance();

    try {

      xmlfile = app.getRelativeFile(applicationz_transfer_path_prop + "/" + f);

      logger.info(xmlfile.getAbsoluteFile().toString());

      if (! xmlfile.exists()) {

        logger.info("File does not exist!");

        return;

      } else if (xmlfile.canWrite()) {

        xmlfile.renameTo(app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f));
        logger.info("after rename" + xmlfile.getAbsolutePath());
        xmlfile = app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f);

        long timestamp = System.currentTimeMillis();

        if (parseFile(xmlfile)) {

          //if parsing was successful the file is already saved to the success
          //folder, so we need to remove the file from the staging folder
          xmlfile.delete();

          //xmlfile.renameTo(app.getRelativeFile(applicationz_processed_path_prop + "/" + Long.toString(timestamp) + f));
        } else {

          xmlfile.renameTo(app.getRelativeFile(applicationz_rejected_path_prop + "/" + Long.toString(timestamp) +
                                               "_complete_" + f));

        }

        logger.info("after parsing" + xmlfile.getAbsolutePath());

      } else {

        logger.fatal("The file " + xmlfile.getName() + " was locked or not available!");

      }

    } catch (Exception ex) {

      logger.error(ex.toString());

      try {

        xmlfile = app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f);

        long timestamp = System.currentTimeMillis();

        xmlfile.renameTo(app.getRelativeFile(applicationz_rejected_path_prop + "/" + Long.toString(timestamp) +
                                             "_complete_" + f));

      } catch (Exception ignore) {

        logger.fatal("Unable to move the file: " + ignore.getMessage());

      }

    }

  }

  /**
   * Processes the file to parse and initiates parsing of the file
   *
   * @param f the file to parse
   */
  protected void processXMLFile(File f) {

    xmlfile = f;

    //copy file to gain exclusive rights
    MetaZ app = MetaZ.getInstance();

    try {

      logger.info(xmlfile.getAbsoluteFile().toString());

      if (! xmlfile.exists()) {

        logger.info("File does not exist!");

        return;

      } else if (xmlfile.canWrite()) {

        xmlfile.renameTo(app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f));
        logger.info("after rename" + xmlfile.getAbsolutePath());
        xmlfile = app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f);

        long timestamp = System.currentTimeMillis();

        if (parseFile(xmlfile)) {

          //if parsing was successful the file is already saved to the success
          //folder, so we need to remove the file from the staging folder
          xmlfile.delete();

          //xmlfile.renameTo(app.getRelativeFile(applicationz_processed_path_prop + "/" + Long.toString(timestamp) + f));
        } else {

          xmlfile.renameTo(app.getRelativeFile(applicationz_rejected_path_prop + "/" + Long.toString(timestamp) +
                                               "_complete_" + f));

        }

        logger.info("after parsing" + xmlfile.getAbsolutePath());

      } else {

        logger.fatal("The file " + xmlfile.getName() + " was locked or not available!");

      }

    } catch (Exception ex) {

      logger.error(ex.toString());

      try {

        xmlfile = app.getRelativeFile(applicationz_transferstaging_path_prop + "/" + f);

        long timestamp = System.currentTimeMillis();

        xmlfile.renameTo(app.getRelativeFile(applicationz_rejected_path_prop + "/" + Long.toString(timestamp) +
                                             "_complete_" + f));

      } catch (Exception ignore) {

        logger.fatal("Unable to move the file: " + ignore.getMessage());

      }

    }

  }

  /**
   * Is responsible for parsing file f and transforming  the contents to a collection of records
   *
   * @param f File to parse and transform to a record
   *
   * @return True if the file is successfully parsed else False
   */
  private boolean parseFile(File f) {

    Boolean validated = false;

    try {

      logger.info("start parsing");

      //repeat for each learnObject in the xml file, create a new document
      //for each learnObject and parse each learnobject xml part
      Document doc = getDom4jDocument(f);

      logger.info("doc created");

      //convert to collection of LearningObjects
      //get and check root element
      Element root = doc.getRootElement();

      if (root.getName() == "leerobjecten") {

        logger.info("before iterating");

        List<Record> leerobjecten = new Vector<Record>();

        for (Iterator i = root.elementIterator(); i.hasNext();) {

          //get first/next leerobject
          Element  element = (Element) i.next();
          Document ldoc = createDocument(element);

          try {

            processValidation(ldoc);
            validated = true;

          } catch (Exception ex) {

            validated = false;

            //save ldoc to errordir
            try {

              logger.fatal(element.element("titel").getText() + " is an invalid node./n" + ex.getMessage());
              writeDocument2File(ldoc, ex.getMessage(), f.getName());

            } catch (Exception ignore) {

              logger.warn("Cannot write node to file!");

            }

          }

          if (validated) {

            Record                rec = createNewRecord(element);
            RecordAttributeSetter recset = new RecordAttributeSetter(rec);

            try {

              for (Iterator k = recset.iterator(); k.hasNext();) {

                MetaData metadata = (MetaData) k.next();

                logger.info("metadata :" + metadata.getMetaDataType() + " - " + metadata.getXMLTagName());

                if (! metadata.isMandatory()) {

                  logger.info("not mandatory");

                  if (metadata.getMetaDataType().equals("TextMetaData")) {

                    parseTextMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("BooleanMetaData")) {

                    parseBoolMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("HtmlTextMetaData")) {

                    parseHtmlTextMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("HierarchicalStructuredTextMetaData")) {

                    parseHierarchicalStructuredTextMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("HierarchicalStructuredTextMetaDataSet")) {

                    parseHierarchicalStructuredTextMetaDataSet(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("HyperlinkMetaData")) {

                    parseHyperlinkMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("NumericMetaData")) {

                    parseNumericMetaData(element, metadata, recset);

                  }

                  if (metadata.getMetaDataType().equals("DateMetaData")) {

                    parseDateMetaData(element, metadata, recset);

                  }

                }

              }

            } catch (Exception exc) {

              logger.fatal(exc.toString());

            }

            leerobjecten.add(rec);

          }

        }

        writeDocument2File(doc, "", f.getName());
        MetaZ.getRepositoryFacade().doUpdate(leerobjecten);

      }

      return true;

    } catch (Exception ex) {

      logger.error(ex.toString());

    }

    return false;

  }

  /**
   * Converts a file in the filesystem into a DOM4J document
   *
   * @param f filename of the file to convert to a DOM4J document
   *
   * @return the DOM4J document to return, null if an error occurs
   *
   * @throws IOException any input output exception whilst creating a Dom4J Document
   */
  private Document getDom4jDocument(File f)
                             throws IOException
  {

    Document document = null;

    try {

      SAXReader reader = new SAXReader();

      document = reader.read(f);
      logger.info("doc read");
      //validation will not trigger move of complete file to error
      //directory. This will be done later on a leerObject level
      //remarks will be logged
      processValidation(document);
      logger.info("document created and validated");

    } catch (DocumentException ex) {

      logger.error(ex.getMessage());

    } catch (Exception exc) {

      logger.error(exc.getMessage());

    }

    return document;

  }

  /**
   * Creates a new document from a node, adding the required parent nodes
   *
   * @param node that will be cloned and added to the new document
   *
   * @return Document containing the passed node with a leerobjecten parent
   */
  protected Document createDocument(Element node) {

    Document document = DocumentHelper.createDocument();
    Element  root = document.addElement("leerobjecten");

    root.add(node.createCopy());

    return document;

  }

  /**
   * Writes the content of a DOM4J xml document to a file
   *
   * @param document to write the contents to a file
   * @param error string which hold the error why validation failed
   * @param file name of the file to write the content of document to
   *
   * @throws IOException can be thrown for the file argument
   */
  public void writeDocument2File(Document document, String error, String file)
                          throws IOException
  {

    Element root = document.getRootElement();
    long    timestamp = System.currentTimeMillis();
    String  path = applicationz_processed_path_prop + "/" + Long.toString(timestamp) + file;

    if (! error.equals("")) {

      Element errEl = root.addElement("Error");

      path = applicationz_rejected_path_prop + "/" + Long.toString(timestamp) + file;
      errEl.setText(error);
      logger.info("Error element created");

    }

    // lets write to a file
    XMLWriter writer = new XMLWriter(new FileWriter(MetaZ.getInstance().getRelativeFile(path)));

    writer.write(document);
    writer.close();

    // Pretty print the document to System.out
    //OutputFormat format = OutputFormat.createPrettyPrint();

    //writer = new XMLWriter(System.out, format);
    //writer.write(document);

    // Compact format to System.out
    //format = OutputFormat.createCompactFormat();
    //writer = new XMLWriter(System.out, format);
    //writer.write(document);
  }

  /**
   * Validate document using MSV
   *
   * @param document xml document to validate
   *
   * @throws Exception progresses any exception
   */
  protected void processValidation(Document document)
                            throws Exception
  {

    MetaZ app = MetaZ.getInstance();

    logger.info("Loaded schema document: " + applicationz_schema_prop);

    //write validator to xml
    Element root = document.getRootElement();

    //add comment with validator
    root.addComment("Validated by ApplicationZ harvester, developed by Meta/Z projectteam (OTO)");

    // use autodetection of schemas
    VerifierFactory factory = new TheFactoryImpl();

    logger.info("after validation factory");

    Schema schema = factory.compileSchema(app.getRelativeFile(applicationz_schema_prop));

    logger.info("schema loaded");

    Verifier verifier = schema.newVerifier();

    logger.info("verifier created");

    //Do not catch validation exceptions, simply propagate the error
/**
     *
     verifier.setErrorHandler(

    new ErrorHandler() {

        public void error(SAXParseException e) {

          logger.error(e.getMessage());

        }

        public void fatalError(SAXParseException e) {

          logger.fatal(e.getMessage());

        }

        public void warning(SAXParseException e) {

          logger.warn(e.getMessage());

        }

      });
*/
    logger.info("Validating XML document");

    VerifierHandler handler = verifier.getVerifierHandler();
    SAXWriter       writer = new SAXWriter(handler);

    writer.write(document);

    if (handler.isValid()) {

      logger.info("VALID");

    } else {

      logger.info("NOT VALID");
      throw new Exception("invalid document");

    }

    logger.info("Wrote verifier to xml");

  }

  /**
   * Recursive function to get paths of hierarchical data
   *
   * @param e current element in xml document
   * @param root root element of the hierarchical structure
   * @param hSet Hierarchical set of MetaData
   */
  private void addNodeRecursive(Element e, Element root, HierarchicalStructuredTextMetaDataSet hSet) {

    for (Iterator i = e.elementIterator(); i.hasNext();) {

      //get first/next leerobject
      Element element = (Element) i.next();

      //isleaf then add branch to hSet
      if ((element.elements().size() == 0) && ! (element.getParent().elements().size() > 1)) {

        List<TextMetaData> branch = new Vector<TextMetaData>();
        Element            etemp = element;

        while (! etemp.equals(root) && ! etemp.getParent().equals(root)) {

          if (! etemp.getName().equals("waarde")) {

            etemp = etemp.getParent().element("waarde");

          }

          //get TextMetaData
          TextMetaData tmtdt = new TextMetaData();

          tmtdt.setValue(etemp.getText());
          logger.info(etemp.getParent().getName() + ": " + etemp.getText());
          //add to list
          branch.add(tmtdt);
          //get parents parent en set in etemp
          etemp = etemp.getParent();

        }

        //reverselist
        HierarchicalStructuredTextMetaData hMetaData = new HierarchicalStructuredTextMetaData();

        for (int j = branch.size(); j > 0; j--) {

          //add listelements to HierarchicalMetaDataText
          hMetaData.addChild(branch.get(j - 1));
          logger.info(j + "-branch: " + branch.get(j - 1).getValue());

        }

        //add HierarchicalMetaDataText to hSet
        hSet.addHierarchy(hMetaData);

        //next branch
      } else {

        //if not leaf make recursive call
        addNodeRecursive(element, root, hSet);

      }

    }

    return;

  }

  /**
   * Recursive function to get paths of hierarchical data
   *
   * @param e current element in xml document
   * @param root element root of the hierarchical structure
   * @param hMetaData Hierarchical MetaData sequence
   */
  private void addNodeRecursive(Element e, Element root, HierarchicalStructuredTextMetaData hMetaData) {

    for (Iterator i = e.elementIterator(); i.hasNext();) {

      //get first/next leerobject
      logger.info("next element");

      Element element = (Element) i.next();

      //isleaf then get branch
      logger.info("isleaf? " + element.getName());

      if ((element.elements().size() == 0) && ! (element.getParent().elements().size() > 1)) {

        List<TextMetaData> branch = new Vector<TextMetaData>();
        Element            etemp = element;

        logger.info("temp element");

        while (! etemp.equals(root) && ! etemp.getParent().equals(root)) {

          if (! etemp.getName().equals("waarde") && ! etemp.getName().equals("eindgebruikerwaarde")) {

            if (! etemp.getParent().getParent().getParent().getName().equals("beoogdeEindgebruiker")) {

              etemp = etemp.getParent().element("waarde");

            } else
              etemp = etemp.getParent().element("eindgebruikerwaarde");

          }

          //get TextMetaData
          logger.info(etemp.getName());

          TextMetaData tmtdt = new TextMetaData();

          tmtdt.setValue(etemp.getText());
          logger.info(etemp.getParent().getName() + ": " + etemp.getText());
          //add to list
          branch.add(tmtdt);
          //get parents parent en set in etemp
          etemp = etemp.getParent();

        }

        //reverselist
        for (int j = branch.size(); j > 0; j--) {

          //add listelements to HierarchicalMetaDataText
          hMetaData.addChild(branch.get(j - 1));
          logger.info(j + "-branch: " + branch.get(j - 1).getValue());

        }

      } else {

        //if not leaf make recursive call
        logger.info("new call");
        addNodeRecursive(element, root, hMetaData);

      }

    }

    return;

  }

  /**
   * Parses a TxtMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseTextMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("textmetadata");

      TextMetaData tmdt = new TextMetaData();

      //sleutelwoorden
      if (metadata.getName().equals("keywords")) {

        String keywords = "";

        for (Iterator j = e.element("sleutelwoorden").elementIterator(); j.hasNext();) {

          Element keyword = (Element) j.next();

          keywords = keywords + keyword.getText() + ";";
          //.element("sleutelwoord")
          logger.info(keywords);

        }

        tmdt.setValue(keywords);

      } else if (metadata.getName().equals("roleName")) {

        //rolEnNaam
        String rolenames = "";

        for (Iterator m = e.element("rolEnNaam").elementIterator(); m.hasNext();) {

          Element rolename = (Element) m.next();

          rolenames = "Rol: " + rolename.element("rol").getText() + "\n Naam: " + rolename.element("naam").getText() +
                      "\n";
          logger.info(rolenames);

        }

        tmdt.setValue(rolenames);

      } else {

        tmdt.setValue(e.element(metadata.getXMLTagName()).getText());

      }

      recset.setValue(metadata.getXMLTagName(), tmdt);
      logger.info(metadata.getXMLTagName() + " : " + tmdt.getValue().toString());

    } catch (Exception ignore) {

      logger.error("here 1:" + ignore.toString());

    }

  }

  /**
   * Parses a BoolMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseBoolMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("boolmetadata");

      BooleanMetaData bmdt = new BooleanMetaData();

      bmdt.setValue(e.element(metadata.getXMLTagName()).getText());
      recset.setValue(metadata.getXMLTagName(), bmdt);
      logger.info(metadata.getXMLTagName());

    } catch (Exception ignore) {

      logger.error("here 2:" + ignore.toString());

    }

  }

  /**
   * Parses a HtmlTextMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseHtmlTextMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("htmlmetadata");

      HtmlTextMetaData hmdt = new HtmlTextMetaData();

      hmdt.setValue(e.element(metadata.getXMLTagName()).getText());
      recset.setValue(metadata.getXMLTagName(), hmdt);
      logger.info(metadata.getXMLTagName() + " : " + hmdt.getValue().toString());

    } catch (Exception ignore) {

      logger.error("here 3:" + ignore.toString());

    }

  }

  /**
   * Parses a HierarchicalStructuredTextMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseHierarchicalStructuredTextMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("hstmetadata");

      HierarchicalStructuredTextMetaData hMetaData = new HierarchicalStructuredTextMetaData();

      addNodeRecursive(e.element(metadata.getXMLTagName()).element("hoofdwaarden"),
                       e.element(metadata.getXMLTagName()).element("hoofdwaarden"), hMetaData);
      recset.setValue(metadata.getXMLTagName(), hMetaData);
      logger.info(metadata.getXMLTagName());

    } catch (Exception ignore) {

      logger.error("here 4:" + ignore.toString());

    }

  }

  /**
   * Parses a HierarchicalStructuredTextMetaDataSet node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseHierarchicalStructuredTextMetaDataSet(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("hstmetadataSet");

      HierarchicalStructuredTextMetaDataSet hSet = new HierarchicalStructuredTextMetaDataSet();

      addNodeRecursive(e.element("schooltype").element("hoofdwaarden"),
                       e.element("schooltype").element("hoofdwaarden"), hSet);
      recset.setValue(metadata.getXMLTagName(), hSet);
      logger.info(metadata.getXMLTagName());

    } catch (Exception ignore) {

      logger.error("here 5:" + ignore.toString());

    }

  }

  /**
   * Parses a HyperlinkMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseHyperlinkMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("hyperlinkmetadata");

      HyperlinkMetaData hlmdt = new HyperlinkMetaData();

      hlmdt.setValue(e.element(metadata.getXMLTagName()).getText());
      recset.setValue(metadata.getXMLTagName(), hlmdt);
      logger.info(metadata.getXMLTagName());

    } catch (Exception ignore) {

      logger.error("here 6:" + ignore.toString());

    }

  }

  /**
   * Parses a NumericMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseNumericMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    try {

      logger.info("nummetadata");

      NumericMetaData nmdt = new NumericMetaData();

      nmdt.setValue(Long.parseLong(e.element(metadata.getXMLTagName()).getText()));
      recset.setValue(metadata.getXMLTagName(), nmdt);
      logger.info(metadata.getXMLTagName() + ": " + nmdt.getValue());

    } catch (Exception ignore) {

      logger.error("here 7:" + ignore.toString() + e.element(metadata.getXMLTagName()).getText());

    }

  }

  /**
   * Parses a DateMetaData node in an xml Document
   *
   * @param e current element in the xml document
   * @param metadata current metatdata type
   * @param recset Record Attribute setter to identify the attributes of the current metadata
   */
  private void parseDateMetaData(Element e, MetaData metadata, RecordAttributeSetter recset) {

    logger.info("datemetadata");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DateMetaData     dmdt = new DateMetaData();

    try {

      Date d = sdf.parse(e.element(metadata.getXMLTagName()).getText());

      dmdt.setValue(d);
      recset.setValue(metadata.getXMLTagName(), dmdt);

    } catch (Exception ignore) {

      logger.error("here 8:" + ignore.toString());

    }

  }

  /**
   * Creates a new record from anDom4J Document node
   *
   * @param e cutten node in the Dom4J Document
   *
   * @return newly created record
   */
  private Record createNewRecord(Element e) {

    //create new record from mandatory elements
    TextMetaData tmdt1 = new TextMetaData();

    tmdt1.setValue(e.element("titel").getText());

    TextMetaData tmdt2 = new TextMetaData();

    tmdt2.setValue(e.element("bestandsformaat").getText());

    TextMetaData tmdt3 = new TextMetaData();

    tmdt3.setValue(e.element("didactischeFunctie").getText());

    TextMetaData tmdt4 = new TextMetaData();

    tmdt4.setValue(e.element("producttype").getText());

    HyperlinkMetaData hper = new HyperlinkMetaData();

    hper.setValue(e.attributeValue("URI"));

    BooleanMetaData bln = new BooleanMetaData();

    bln.setValue(Boolean.valueOf(e.element("beveiligd").getText()));

    Record rec = new org.metaz.domain.Record(tmdt1, bln, tmdt2, tmdt3, tmdt4, hper);

    logger.info("record created" + e.attributeValue("URI"));

    return rec;

  }

}
