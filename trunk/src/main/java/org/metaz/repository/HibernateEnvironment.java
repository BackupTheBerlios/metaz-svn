/**
 *
 * The environment class that controls the Hibernate configuration.
 * <p>
 * The configuration parameters are loaded from a property file:
 * <code>"hibernate.properties"</code>, which must be placed in the
 * classpath.  
 * <p> 
 * This is a singleton implementation. The whole
 * application uses one HibernateEnvironment instance, and one Session
 * Factory.
 * </p>
 * <p>
 * Example:
 *   <code>
 *    SessionFactory factory = HibernateEnvironment.getInstance().getSessionFactory();
 *   </code>
 * </p>
 * @author Falco Paul
 */

package org.metaz.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;

public class HibernateEnvironment {

  /**
   * All the persistent classes of the application.
   * It is enough to register here the top classes, all subclasses will be included
   */
  private static Class[] applicationClasses = null;

  /** The one and only instance. */
  private static HibernateEnvironment theInstance;

  /** The hibernate property file name. */
  private static String HiberatePropertiesFileName = "hibernate.properties";

  /** The logger of this class. */
  private static final Logger logger = Logger.getLogger(HibernateEnvironment.class.getName());

  /** The Hibernate configuration. */
  private Configuration configuration;

  /** The Hibernate session factory. */
  private SessionFactory sessionFactory;
  
  /** Creates a new instance of HibernateEnvironment */
  
  private HibernateEnvironment() {

    if (applicationClasses == null) {
    
        try {
        
          applicationClasses = new Class[] {

            Class.forName("org.metaz.repository.Record"),
         // Class.forName("org.metaz.repository.NogEenKlasse"),
         // Class.forName("org.metaz.repository.EnNogEenKlasse"),
          
          };
          
        } catch (java.lang.ClassNotFoundException cnfe) {
          java.lang.System.out.println(cnfe.getMessage());
          cnfe.printStackTrace();
        }
        
    }
    
  }

  /**
   * Get the only instance of the HibernateEnvironment.
   */
   
  public static HibernateEnvironment getInstance() {
  
    if (theInstance == null) {
      theInstance = new HibernateEnvironment();
      theInstance.initialize();
    }

    return theInstance;
    
  }
  
  /**
   * Get the Hibernate configuration.
   * @return the Hibernate configuration
   */
  public Configuration getConfiguration() {
    return configuration;
  }

  /**
   * Get the Hibernate session factory.
   * @return the session factory
   */
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * Initialize the Hibernate environment.
   */
  private void initialize() {
  
    Properties  properties = getHibernateProperties();
    configuration = new Configuration();
    configuration = configuration.setProperties(properties);
  
    // add all persistent classes to the configuration
    try {
    
      for(int i = 0; i < applicationClasses.length; i++) {

        logger.info("Configuring: " + applicationClasses[i].toString());
        configuration = configuration.addClass(applicationClasses[i]);

      }
      
    } catch (MappingException me) {
        throw new RuntimeException("Configuration of Hibernate failed.", me);
    }
  
    // create a session factory

    try {

      sessionFactory = configuration.buildSessionFactory();

    } catch (HibernateException he) {

      System.err.println(he.getMessage());
      he.printStackTrace();
      throw new RuntimeException("Session factory could not be created.", he);

    }

  }

  /**
   * Set all relevant Hibernate configuration properties 
   * and switch on platform or dbms engine.
   * @return The hibernate properties.
   */
  private Properties getHibernateProperties() {

    Properties properties = new Properties();
    
    // dialect dependent settings...
    
    // DB2
    // properties.put("hibernate.dialect","net.sf.hibernate.dialect.DB2Dialect");
    // properties.put("hibernate.connection.driver_class","COM.ibm.db2.jdbc.app.DB2Driver");
    // properties.put("hibernate.connection.url","jdbc:db2:crd_test");
    // properties.put("hibernate.connection.username","db2admin");
    // properties.put("hibernate.connection.password","Metadata01");
    // properties.put("hibernate.default_schema","crd");

    // MYSQL
    // properties.put("hibernate.dialect","net.sf.hibernate.dialect.MySQLDialect");
    // properties.put("hibernate.connection.driver_class","org.gjt.mm.mysql.Driver");
    // properties.put("hibernate.connection.url","jdbc:mysql:///CRD_TEST?user=arjanbos");
    // properties.put("hibernate.connection.username","");
    // properties.put("hibernate.connection.password","");

    // POSTGRES
    // properties.put("hibernate.dialect","net.sf.hibernate.dialect.PostgreSQLDialect");
    // properties.put("hibernate.connection.driver_class","org.postgresql.Driver");
    // properties.put("hibernate.connection.url","jdbc:postgresql:crd_test");
    // properties.put("hibernate.connection.username","rvjansen");
    // properties.put("hibernate.default_schema","crd");
    // properties.put("hibernate.connection.password"," ");
    
    properties.put("hibernate.query.substitutions", "true 1, false 0, yes 'Y', no 'N'");
    properties.put("hibernate.connection.pool_size","10");
    properties.put("hibernate.proxool.pool_alias","pool1");
    properties.put("hibernate.show_sql","false");
    properties.put("hibernate.jdbc.batch_size","0");
    properties.put("hibernate.jdbc.batch_versioned_data","true");
    properties.put("hibernate.jdbc.use_streams_for_binary","true");
    properties.put("hibernate.max_fetch_depth","1");
    properties.put("hibernate.cache.region_prefix","hibernate.test");
    properties.put("hibernate.cache.use_query_cache","true");
    properties.put("hibernate.cache.provider_class","net.sf.hibernate.cache.HashtableCacheProvider");
    
    // keep this or DB2's JDBC driver will fail when using jumbo sized joins

    properties.put("hibernate.use_outer_join","false"); 
  
    return properties;
    
  }

}