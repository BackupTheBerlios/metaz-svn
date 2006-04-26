package org.metaz.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of hierarchical structured text metadata
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class HierarchicalStructuredTextMetaDataSet extends MetaData {

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Set value;

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Constructor
   */
  public HierarchicalStructuredTextMetaDataSet() {

    value = new HashSet();

  } // end HierarchicalStructuredTextMetaDataSet()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the set value
   *
   * @return the set value
   */
  @Override
  public Object getValue()
  {

    return this.value;

  } // end getValue()

  /**
   * Sets the set value
   *
   * @param value the set value
   */
  @Override
  public void setValue(Object value)
  {

    this.value = (Set) value;

  } // end setValue()

  /**
   * Adds a hierarchical metadata structure to the set.
   *
   * @param hierarchy the hierarchical metadata
   */
  public void addHierarchy(HierarchicalStructuredTextMetaData hierarchy) {

    value.add(hierarchy);

  } // end addHierarchy()

  /**
   * Returns a representation of this MetaData instance.
   *
   * @return string representation.
   */
  public String toString() {

    StringBuffer tstring = new StringBuffer();

    for (Object val : value) {

      tstring.append(val.toString());

    } //end for

    return tstring.toString();

  } // end toString()

} // end HierarchicalStructuredTextMetaDataSet
