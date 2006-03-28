package org.metaz.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This class implements the FilenameFilter interface. The accept method only returns true for .xml files.
 *
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 */
public class XMLFilter
  implements FilenameFilter
{

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method represents a file filter for .xml files.
   *
   * @param dir the directory containing the files to filter 
   * @param s the filename to filter
   *
   * @return true if the file is accepted by the filter, else false
   */
  public boolean accept(File dir, String s) {

    if (s.endsWith(".xml"))

      return true;

    return false;

  }

}
