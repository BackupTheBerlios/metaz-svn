package org.metaz.repository.alt;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.metaz.repository.DataService;
import org.metaz.repository.Facade;
import org.metaz.repository.FacadeFactory;
import org.metaz.repository.SearchService;
import org.metaz.util.MetaZ;

/**
 * This factory class provides a static method to create an instance of 
 * the Facade. 
 * 
 * @author Sammy Dalewyn
 */
public class FacadeFactoryAlt {
    private static final String SEARCH_PROP = "org.metaz.repository.search.service.impl";

    private static final String SEARCH_DEF_PROP = "org.metaz.repository.alt.SearchServiceImplAlt";

    private static final String FACADE_PROP = "org.metaz.repository.facade.impl";

    private static final String FACADE_DEF_PROP = "org.metaz.repository.alt.FacadeImplAlt";

    private static Logger logger = MetaZ.getLogger(FacadeFactory.class);

    public static Facade createFacade(){

            Facade facade = null;
            SearchServiceAlt search = null;
            DataService data = null;

            Properties props = MetaZ.getInstance().getProperties();

            try {
                    //try instantiate the classes that are defined by the properties
                    String cls = props.getProperty(FACADE_PROP, FACADE_DEF_PROP);
                    logger.debug(FACADE_PROP + "=" + cls);
                    facade = (Facade) (Class.forName(cls).newInstance());
                    cls = props.getProperty(SEARCH_PROP, SEARCH_DEF_PROP);
                    logger.debug(SEARCH_PROP + " " + cls);
                    search = (SearchServiceAlt) (Class.forName(cls).newInstance());

                    //inject the appropriate services
                    facade.setSearchService(search);
                    
                    logger.debug("Facade created");

            } catch (Exception e) {
                    //log and re-throw exception
                    logger.log(Priority.ERROR,
                                    "Error instantiating a new Facade instance", e);
            }

            return facade;
    }
}
