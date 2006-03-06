package org.metaz.test.toxgene;

import toxgene.interfaces.ToXgeneCdataDescriptor;

/**
 * Data structure that describes the dutch words CDATA generator
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class DutchWordsCdataDescriptor extends ToXgeneCdataDescriptor {

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Creates a new DutchWordsCdataDescriptor object.
     */
  public DutchWordsCdataDescriptor() {
    super();
    this.cdataClass = "org.metaz.test.toxgene.Tekst";
    this.cdataName = "tekst";
    this.minLength = 1;
    this.maxLength = 200;

  } // end DutchWordsCdataDescriptor()

} // end DutchWordsCdataDescriptor
