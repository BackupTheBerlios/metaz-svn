package org.metaz.repository;

import junit.framework.TestCase;

/**
 * Class for testing the FacadeFactory
 *
 * @author J. Goelen
 */
public class RepositoryFactoryTest extends TestCase {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Tests the method createFacade()
   */
  public void testCreateFacade() {

    //test the default properties
    Facade facade = FacadeFactory.createFacade();

    assertNotNull(facade);

  }

}
