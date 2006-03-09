package org.metaz.harvester;

import com.sun.msv.verifier.jarv.TheFactoryImpl;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
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

//import com.sun.msv.grammar.relaxng.datatype.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import java.io.*;

import java.text.*;

import java.util.*;

/**
 * The class Harvester initiates a check of an offered xml file to check on xml schema conformity, extract all
 * Learnobjects, transform them to records of metadata and pass them to the repository facade.
 *
 * @author Lars Oosterloo
 * @version 0.1 The Harvester class is responsible for parsing xml data files into a collection of LearningObjects and passing this collection to the repository interface
 */
public class Harvester {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger       logger = MetaZ.getLogger(Harvester.class);
  private final static String APPLICATIONZ_SCHEMA = "xml/schema/metaz.xsd";
  private final static String APPLICATIONZ_TRANSFER_PATH = "xml/transfer";
  private final static String APPLICATIONZ_PROCESSED_PATH = "xml/log/processed";
  private final static String APPLICATIONZ_REJECTED_PATH = "xml/log/error";
  private final static String APPLICATIONZ_TRANSFERSTAGING_PATH = "xml/transferstaging";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  protected File xmlfile;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Constructor
   */
  public Harvester() {

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
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

      xmlfile = app.getRelativeFile(APPLICATIONZ_TRANSFER_PATH + "/" + f);

      logger.info(xmlfile.getAbsoluteFile().toString());

      if (! xmlfile.exists()) {

        logger.info("File does not exist!");

        return;

      } else if (xmlfile.canWrite()) {

        xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_TRANSFERSTAGING_PATH + "/" + f));
        logger.info("after rename" + xmlfile.getAbsolutePath());
        xmlfile = app.getRelativeFile(APPLICATIONZ_TRANSFERSTAGING_PATH + "/" + f);

        long timestamp = System.currentTimeMillis();

        if (parseFile(xmlfile)) {

          xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_PROCESSED_PATH + "/" + Long.toString(timestamp) + f));

        } else {

          xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_REJECTED_PATH + "/" + Long.toString(timestamp) + f));

        }

        logger.info("after parsing" + xmlfile.getAbsolutePath());

      } else {

        logger.fatal("The file " + xmlfile.getName() + " was locked or not available!");

      }

    } catch (Exception ex) {

      logger.error(ex.toString());

      try {

        xmlfile = app.getRelativeFile(APPLICATIONZ_TRANSFERSTAGING_PATH + "/" + f);

        long timestamp = System.currentTimeMillis();

        xmlfile.renameTo(app.getRelativeFile(APPLICATIONZ_REJECTED_PATH + "/" + Long.toString(timestamp) + f));

      } catch (Exception ignore) {

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

      //validate and create document
      logger.info("start parsing");

      //repeat for each learnObject in the xml file
      //create a ne document for each learnObject
      //parse each learnobject xml part
      Document doc = getDom4jDocument(f);

      //if (doc == null) {

      //return false;

      //}
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

              logger.fatal(element.element("titel").getText() + " is an invalid node.");
              logger.info(ex.getMessage());
              writeDocument2File(ldoc, ex.getMessage(), f.getName());

            } catch (Exception ignore) {

              logger.warn("Cannot write node to file!");

            }

          }

          if (validated) {

            //create new record from mandatory elements
            TextMetaData tmdt1 = new TextMetaData();

            tmdt1.setValue(element.element("titel").getText());

            TextMetaData tmdt2 = new TextMetaData();

            tmdt2.setValue(element.element("bestandsformaat").getText());

            TextMetaData tmdt3 = new TextMetaData();

            tmdt3.setValue(element.element("didactischeFunctie").getText());

            TextMetaData tmdt4 = new TextMetaData();

            tmdt4.setValue(element.element("producttype").getText());

            HyperlinkMetaData hper = new HyperlinkMetaData();

            hper.setValue(element.attributeValue("URI"));

            BooleanMetaData bln = new BooleanMetaData();

            bln.setValue(Boolean.valueOf(element.element("beveiligd").getText()));

            Record rec = new org.metaz.domain.Record(tmdt1, bln, tmdt2, tmdt3, tmdt4, hper);

            logger.info("record created" + element.attributeValue("URI"));

            RecordAttributeSetter recset = new RecordAttributeSetter(rec);

            try {

              for (Iterator k = recset.iterator(); k.hasNext();) {

                logger.info("in iterator");

                MetaData metadata = (MetaData) k.next();

                logger.info("metadata :" + metadata.getMetaDataType() + " - " + metadata.getXMLTagName());
                logger.info("mandatory :" + metadata.isMandatory());

                if (! metadata.isMandatory()) {

                  logger.info("not mandatory");

                  if (metadata.getMetaDataType().equals("TextMetaData")) {

                    try {

                      logger.info("textmetadata");

                      TextMetaData tmdt = new TextMetaData();

                      //sleutelwoorden
                      if (metadata.getName().equals("keywords")) {

                        String keywords = "";

                        for (Iterator j = element.element("sleutelwoorden").elementIterator(); j.hasNext();) {

                          Element keyword = (Element) j.next();

                          keywords = keyword.getText() + ";";
                          //.element("sleutelwoord")
                          logger.info(keywords);

                        }

                        tmdt.setValue(keywords);

                      } else if (metadata.getName().equals("roleName")) {

                        //rolEnNaam
                        String rolenames = "";

                        for (Iterator m = element.element("rolEnNaam").elementIterator(); k.hasNext();) {

                          Element rolename = (Element) m.next();

                          rolenames = "Rol: " + rolename.element("rol").getText() + "\n Naam: " +
                                      rolename.element("naam").getText() + "\n";
                          logger.info(rolenames);

                        }

                        tmdt.setValue(rolenames);

                      } else {

                        tmdt.setValue(element.element(metadata.getXMLTagName()).getText());

                      }

                      recset.setValue(metadata.getXMLTagName(), tmdt);
                      logger.info(metadata.getXMLTagName() + " : " + tmdt.getValue().toString());

                    } catch (Exception ignore) {

                      logger.error("here 1:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("BooleanMetaData")) {

                    try {

                      logger.info("boolmetadata");

                      BooleanMetaData bmdt = new BooleanMetaData();

                      bmdt.setValue(element.element(metadata.getXMLTagName()).getText());
                      recset.setValue(metadata.getXMLTagName(), bmdt);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 2:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("HtmlTextMetaData")) {

                    try {

                      logger.info("htmlmetadata");

                      HtmlTextMetaData hmdt = new HtmlTextMetaData();

                      hmdt.setValue(element.element(metadata.getXMLTagName()).getText());
                      recset.setValue(metadata.getXMLTagName(), hmdt);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 3:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("HierarchicalStructuredTextMetaData")) {

                    try {

                      logger.info("hstmetadata");

                      HierarchicalStructuredTextMetaData hMetaData = new HierarchicalStructuredTextMetaData();

                      addNodeRecursive(element.element(metadata.getXMLTagName()).element("hoofdwaarden"),
                                       element.element(metadata.getXMLTagName()).element("hoofdwaarden"), hMetaData);
                      recset.setValue(metadata.getXMLTagName(), hMetaData);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 4:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("HierarchicalStructuredTextMetaDataSet")) {

                    try {

                      logger.info("hstmetadataSet");

                      HierarchicalStructuredTextMetaDataSet hSet = new HierarchicalStructuredTextMetaDataSet();

                      addNodeRecursive(element.element("schooltype").element("hoofdwaarden"),
                                       element.element("schooltype").element("hoofdwaarden"), hSet);
                      recset.setValue(metadata.getXMLTagName(), hSet);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 5:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("HyperlinkMetaData")) {

                    try {

                      logger.info("hyperlinkmetadata");

                      HyperlinkMetaData hlmdt = new HyperlinkMetaData();

                      hlmdt.setValue(element.element(metadata.getXMLTagName()).getText());
                      recset.setValue(metadata.getXMLTagName(), hlmdt);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 6:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("NumericMetaData")) {

                    try {

                      logger.info("nummetadata");

                      NumericMetaData nmdt = new NumericMetaData();

                      nmdt.setValue(Long.getLong(element.element(metadata.getXMLTagName()).getText()));
                      recset.setValue(metadata.getXMLTagName(), nmdt);
                      logger.info(metadata.getXMLTagName());

                    } catch (Exception ignore) {

                      logger.error("here 7:" + ignore.toString());

                    }

                  }

                  if (metadata.getMetaDataType().equals("DateMetaData")) {

                    logger.info("datemetadata");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    DateMetaData     dmdt = new DateMetaData();

                    try {

                      Date d = sdf.parse(element.element(metadata.getXMLTagName()).getText());

                      dmdt.setValue(d);
                      recset.setValue(metadata.getXMLTagName(), dmdt);

                    } catch (Exception ignore) {

                      logger.error("here 8:" + ignore.toString());

                    }

                  }

                }

              }

            } catch (Exception exc) {

              logger.fatal(exc.toString());

            }

            leerobjecten.add(rec);

          }

        }

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
   * @throws Exception any exception whilst creating a Dom4J Document
   */
  private Document getDom4jDocument(File f)
                             throws Exception
  {

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
   * @throws IOException
   */
  public void writeDocument2File(Document document, String error, String file)
                          throws IOException
  {

    Element root = document.getRootElement();
    Element errEl = root.addElement("Error");

    errEl.setText(error);
    logger.info("Error element created");

    long timestamp = System.currentTimeMillis();

    // lets write to a file
    XMLWriter writer = new XMLWriter(new FileWriter(MetaZ.getInstance()
                                                    .getRelativeFile(APPLICATIONZ_REJECTED_PATH + "/" +
                                                                     Long.toString(timestamp) + file)));

    writer.write(document);
    writer.close();

    // Pretty print the document to System.out
    OutputFormat format = OutputFormat.createPrettyPrint();

    writer = new XMLWriter(System.out, format);
    writer.write(document);

    // Compact format to System.out
    format = OutputFormat.createCompactFormat();
    writer = new XMLWriter(System.out, format);
    writer.write(document);

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

    logger.info("Loaded schema document: " + APPLICATIONZ_SCHEMA);

    // use autodetection of schemas
    VerifierFactory factory = new TheFactoryImpl();

    logger.info("after validation factory");

    Schema schema = factory.compileSchema(app.getRelativeFile(APPLICATIONZ_SCHEMA));

    logger.info("schema loaded");

    Verifier verifier = schema.newVerifier();

    logger.info("verifier created");
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
   * @param root DOCUMENT ME!
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
   * @param root DOCUMENT ME!
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

}
