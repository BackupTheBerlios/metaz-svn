package org.metaz.util;

import java.util.Vector;

/**
 * Helper/utility class to parse the value of the toString() method
 * from HierarchicalStructuredTextMetaData. 
 *
 * @author Erik-Jan Spaans  
 */
public class HierarchicalStructuredMetaDataValueParser {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Gets the value of the lowest hierarchical level of the supplied hierarchy. This means the value after the
   * last '/'.
   *
   * @param fullHierarchy
   *
   * @return
   */
  public static String getLowestHierarchicalLevelValue(String fullHierarchy) {

    String lowestLevelValue = null;

    if (fullHierarchy != null) {

      int lowestLevelSeparator = fullHierarchy.lastIndexOf("/");

      // add one to lowestLevelSeparator to exclude the '/'
      lowestLevelValue = fullHierarchy.substring(lowestLevelSeparator + 1);

    }

    return lowestLevelValue;

  }

  /**
   * Splits a full hierarchical path into all its hierarchical components.<p><i>E.g. the string
   * &#47;a&#47;b&#47;c&#47;d is splitted into  &#47;a&#47;b&#47;c&#47;d, &#47;a&#47;b&#47;c, &#47;a&#47;b and
   * &#47;a.</i></p>
   *
   * @param fullHierarchy the pathlike string representation of the hierarchical value
   *
   * @return all subpaths
   */
  public static String[] getAllHierarchicalPaths(String fullHierarchy) {

    String[] components = null;

    if (fullHierarchy != null) {

      Vector paths = new Vector();

      paths.add(fullHierarchy);

      while (fullHierarchy.lastIndexOf("/") != 0) {

        fullHierarchy = fullHierarchy.substring(0, fullHierarchy.lastIndexOf("/"));
        paths.add(fullHierarchy);

      }

      if (paths.size() != 0) {

        components = new String[paths.size()];

        for (int i = 0; i < paths.size(); i++) {

          components[i] = (String) paths.elementAt(i);

        }

      }

    }

    return components;

  }

}
