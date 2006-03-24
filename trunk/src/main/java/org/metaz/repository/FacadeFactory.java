package org.metaz.repository;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.metaz.util.MetaZ;

/**
 * This factory class provides a static method to create an instance of 
 * the Facade. 
 * <br/><br/>
 * The concrete implementations to be used  for the Facade interface, and 
 * the different services it encapsulates, can be configured by modifying the 
 * following default properties:
 * <li>
 * org.metaz.repository.facade.impl = org.metaz.repository.FacadeImpl
 * </li>
 * <li>
 * org.metaz.repository.search.service.impl = org.metaz.repository.SearchServiceImpl
 * </li>    
 * <li>
 * org.metaz.repository.data.service.impl = org.metaz.repository.DataServiceImpl
 * </li>
 * 
 * @author J. Goelen
 */
public class FacadeFactory {

	private static final String SEARCH_PROP = "org.metaz.repository.search.service.impl";

	private static final String SEARCH_DEF_PROP = "org.metaz.repository.SearchServiceImpl";

	private static final String DATA_PROP = "org.metaz.repository.data.service.impl";

	private static final String DATA_DEF_PROP = "org.metaz.repository.DataServiceImpl";

	private static final String FACADE_PROP = "org.metaz.repository.facade.impl";

	private static final String FACADE_DEF_PROP = "org.metaz.repository.FacadeImpl";

	private static Logger logger = MetaZ.getLogger(FacadeFactory.class);

	public static Facade createFacade(){

		Facade facade = null;
		RepositoryService search = null;
		RepositoryService data = null;

		Properties props = MetaZ.getInstance().getProperties();

		try {
			//try instantiate the classes that are defined by the properties
			String cls = props.getProperty(FACADE_PROP, FACADE_DEF_PROP);
			logger.debug(FACADE_PROP + "=" + cls);
			facade = (Facade) (Class.forName(cls).newInstance());
			cls = props.getProperty(SEARCH_PROP, SEARCH_DEF_PROP);
			logger.debug(SEARCH_PROP + " " + cls);
			search = (RepositoryService) (Class.forName(cls).newInstance());
			cls = props.getProperty(DATA_PROP, DATA_DEF_PROP);
			logger.debug(DATA_PROP + " " + cls);
			data = (RepositoryService) (Class.forName(cls).newInstance());

			//inject the appropriate services
			facade.setSearchService(search);
			facade.setDataService(data);
			
			logger.debug("Facade created");

		} catch (Exception e) {
			//log and re-throw exception
			logger.log(Priority.ERROR,
					"Error instantiating a new Facade instance", e);
		}

		return facade;
	}

}
