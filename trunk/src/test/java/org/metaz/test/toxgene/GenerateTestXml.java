package org.metaz.test.toxgene;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.metaz.domain.MetaData;

import org.metaz.util.MetaZ;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.List;

/**
 * Class for generating test xml data. This class merges the results of the provided
 * template file with the schooltype/schooldiscipline-dependency template file.
 *
 * @author Sammy Dalewyn
 * @version 1.0
  */
public final class GenerateTestXml {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  static final String DEPEND_TEMPLATE = "test/toxgene/depend.tsl";
  static final String DEPEND_XML = "test/toxgene/output/depend.xml";
  static final String TEMP_XML = "test/toxgene/output/temp.xml";
  static final String FINAL_XML = "test/toxgene/output/koppeling.xml";
  private static MetaZ        app;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Constructor
   */
  private GenerateTestXml() {

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Merges the results of the provided
   * xml file with the schooltype/schooldiscipline-dependency xml file.
   *
   * @param records the input xml file
   * @param schoolDependencies the dependency xml file
   * @param output the output xml file
   */
  public static void mergeDocuments(File records, File schoolDependencies, File output) {

    try {

      SAXReader reader = new SAXReader();
      Document  doc1 = reader.read(records);
      Element   root1 = doc1.getRootElement();
      Iterator  elements1 = root1.elementIterator();

      SAXReader reader2 = new SAXReader();
      Document  doc2 = reader2.read(schoolDependencies);
      Element   root2 = doc2.getRootElement();
      List      elements2 = root2.elements();

      System.out.println("Merging documents");

      int i = 0;

      while (elements1.hasNext()) {

        Element record1 = (Element) elements1.next();
        Element record2 = (Element) elements2.get(i % elements2.size());
        Element schoolDiscipline1 = record1.element(MetaData.SCHOOLDISCIPLINE);
        Element schoolType1 = record1.element(MetaData.SCHOOLTYPE);

        if (schoolDiscipline1 != null) {

          record1.remove(schoolDiscipline1);

          Element schoolDiscipline2 = record2.element(MetaData.SCHOOLDISCIPLINE);
          Element schoolDiscipline = schoolDiscipline2.createCopy();

          record1.add(schoolDiscipline);

        }

        if (schoolType1 != null) {

          record1.remove(schoolType1);

          Element schoolType2 = record2.element(MetaData.SCHOOLTYPE);
          Element schoolType = schoolType2.createCopy();

          record1.add(schoolType);

        }

        i++;

      }

      OutputStream out = new FileOutputStream(output);
      XMLWriter    writer = new XMLWriter(out);

      writer.write(doc1);
      System.out.println("Merged document written to disk");

    } catch (Exception e) {

      System.out.println(e.getMessage());

    }

  }

  /**
   * The main method
   *
   * @param argv the ToXgene template file to use
   */
  public static void main(String[] argv) {

    if (argv.length == 0) {

      System.out.println("Usage: GenerateTestXml <template file>\n");
      System.exit(0);

    }

    try {

      Generator generator = new Generator();

      generator.generateXml(DEPEND_TEMPLATE);
      generator.generateXml(argv[0]);
      app = MetaZ.getInstance();

      File f1 = app.getRelativeFile(DEPEND_XML);
      File f2 = app.getRelativeFile(TEMP_XML);
      File output = app.getRelativeFile(FINAL_XML);

      mergeDocuments(f2, f1, output);
      f1.delete();
      f2.delete();
      System.out.println("Temporary files removed");
      System.exit(0);

    } catch (Exception e) {

      System.out.println(e.getMessage());

    }

  }

}
