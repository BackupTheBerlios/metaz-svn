package org.metaz.util;

import java.io.*;

/**
 * This class implements the FilenameFilter interface The accept method only returns true for .xml files.
 *
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 */
public class XMLFilter
  implements FilenameFilter
{

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param dir DOCUMENT ME!
   * @param s DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean accept(File dir, String s) {

    if (s.endsWith(".xml"))

      return true;

    return false;

  }

}
