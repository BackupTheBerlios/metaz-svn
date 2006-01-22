package org.metaz.repository;

import junit.framework.TestCase;

/**
 * @author J. Goelen
 */
public class RepositoryFactoryTest extends TestCase {
		
	/*
	 * Test method for 'org.metaz.repository.RepositoryFactory.createFacade()'
	 */
	public void testCreateFacade() {				
		//test the default properties
		Facade facade = FacadeFactory.createFacade();
		assertNotNull(facade);		
	}

}
