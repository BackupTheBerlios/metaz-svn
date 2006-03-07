package org.metaz.harvester;

import junit.framework.TestCase;

import org.metaz.util.MetaZ;

import java.io.*;

/**
 * Tests Harvester class, requires test_koppleing.xml in xml folder set up in properties file
 *
 * @author Lars Oosterloo
 * @version 1.0
 */
public class HarvesterTest extends TestCase {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * 
   *
   * @throws Exception DOCUMENT ME!
   */
  protected void setUp()
                throws Exception
  {

    super.setUp();

  }

  /*
   * Test method for 'org.metaz.harvester.Harvester.setXMLFile(String)'
   */
  /**
   * DOCUMENT ME!
   *
   * @throws FileNotFoundException DOCUMENT ME!
   * @throws IOException DOCUMENT ME!
   */
  public void testSetXMLFile()
                      throws FileNotFoundException,
                             IOException
  {

    String filename = "test_koppeling.xml";

    //copy testfile to /xml/transfer
    MetaZ        app = MetaZ.getInstance();
    InputStream  in = new FileInputStream(app.getRelativeFileSpec("xml/" + filename));
    OutputStream out = new FileOutputStream(app.getRelativeFileSpec("xml/transfer/" + filename));

    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    int    len;

    while ((len = in.read(buf)) > 0) {

      out.write(buf, 0, len);

    }

    in.close();
    out.close();

    //initiate the Harvester class
    Harvester harvester = new Harvester();

    harvester.setXMLFile(filename);

  }

}
