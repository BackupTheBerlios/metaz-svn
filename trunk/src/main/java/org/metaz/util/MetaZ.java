package org.metaz.util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import org.metaz.harvester.MetazScheduler;

import org.metaz.repository.Facade;
import org.metaz.repository.FacadeFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Properties;

/**
 * This is the Meta/Z application instance (implemented as a singleton object). This class offers "global" services
 * to application modules. The singleton pattern assures that only one instance will exist in the VM. Please note that
 * there are some special cases where this may not be true. Fortunally, these cases are very rare and it's not likely
 * we'll have to deal with this. See http://www.javaworld.com/javaworld/jw-01-2001/jw-0112-singleton.html for a
 * discussion.
 *
 * @author Falco Paul, Open University Netherlands, OTO Meta/Z project
 * @version $Revision$
 */
public final class MetaZ
  implements java.io.Serializable
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  public static final String    METAZ_PROPERTIES_FILE = "metaz.props"; // name of the metaz properties file
  public static final String    METAZ_CONFIG_FILE_PATH = "config/metaz"; // metaz configuration directory (relative)
  public static final String    PROP_LOG4J_CONFIG_FILE = "log4j.config.file"; // default log4j config file propety
  public static final String    PROP_ROOT_DIR = "root.dir"; // root directory property key
  private static MetaZ          instance; // Global Meta/Z application instance (singleton)
  private static Logger         logger = Logger.getLogger(MetaZ.class); // logger
  private static Facade         facadeInst; // instance for this class
  private static SessionFactory hibernateSessionFactory; // Facade object of the Repository
  private static MetazScheduler scheduler; // the Meta/Z scheduler

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Properties properties = null; // application properties loaded from the above stream
  private String     log4JConfigFile = null; // log4J configuration file
  private boolean    verboseOutput = false; // set true if logging does not work

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * private constructor
   */
  private MetaZ() {

    // set up a default logging style, to be used as a fallback style in
    // case of property file loading errors
    PatternLayout layout = new PatternLayout();

    layout.setConversionPattern("%d{dd/mm/yy HH:mm:ss} %-5p[%t]: %m%n");

    ConsoleAppender appender = new ConsoleAppender(layout);

    Logger          rootLogger = Logger.getRootLogger();

    rootLogger.removeAllAppenders();
    rootLogger.addAppender(appender);
    rootLogger.setLevel((Level) Level.INFO);

    // attempt to load the log4j properties file
    properties = readProperties();
    log4JConfigFile = getRelativeFileSpec(getProperties().getProperty(PROP_LOG4J_CONFIG_FILE));

    if (log4JConfigFile == null) {

      logger.warn("Could not seed log4J with a configuration file, using default output settings");

    } else {

      PropertyConfigurator.configure(log4JConfigFile);
      logger.info("Seeded log4J with configuration file <" + log4JConfigFile + ">");

    }

    logger.info("Constructed Meta-Z application singleton instance <" + format(this) + ">");

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Retrieves the Meta/Z application instance (allows for lazy initialization)
   *
   * @return MetaZ the Meta/Z application instance
   */
  public static synchronized MetaZ getInstance() {

    if (instance == null) {

      instance = new MetaZ();

    }

    return instance;

  }

  /**
   * Returns the Meta/Z Facade instance
   *
   * @return Facade instance
   */
  public static synchronized Facade getRepositoryFacade() {

    if (facadeInst == null) {

      facadeInst = FacadeFactory.createFacade();

      logger.info("Created Meta-Z Facade singleton instance: <" + format(facadeInst) + ">");

    }

    return facadeInst;

  }

  /**
   * Returns the Meta/Z Hibernate Session Factory instance
   *
   * @return Hibernate Session Factory instance
   */
  public static synchronized SessionFactory getHibernateSessionFactory() {

    if (hibernateSessionFactory == null) {

      Configuration cfg = new Configuration().configure();

      hibernateSessionFactory = cfg.buildSessionFactory();

      logger.info("Created Meta-Z SessionFactory singleton instance: <" + format(hibernateSessionFactory) + ">");

    }

    return hibernateSessionFactory;

  }

  /**
   * Returns the Meta/Z Scheduler instance
   *
   * @return MetazScheduler instance
   */
  public static synchronized MetazScheduler getScheduler() {

    if (scheduler == null) {

      scheduler = new MetazScheduler();
      logger.info("Created Meta-Z Scheduler singleton instance: <" + format(scheduler) + ">");

    }

    return scheduler;

  }

  /**
   * Gets a logger instance and initializes the logger "look and feel"
   *
   * @param clazz classname you would like a logger for
   *
   * @return Logger a fully configured logger object
   */
  public static Logger getLogger(final Class clazz) {

    // this next line actually forces initialization of the MetaZ singleton
    // object, which in turns sets up the logger properties
    getInstance();

    Logger newLogger = Logger.getLogger(clazz);

    logger.info("Created Logger object <" + format(newLogger) + ">");

    return newLogger;

  }

  /**
   * Returns a nice string describing an object, to be used in logging strings Works better than calling
   * toString() as that will print actual content in a lot of cases...
   *
   * @param object the object to be stringified
   *
   * @return String stringified object
   */
  public static String format(final Object object) {

    return object.getClass().getName() + '@' + Integer.toHexString(object.hashCode());

  }

  /**
   * Returns the current Meta/Z application properties object
   *
   * @return Properties the Meta/Z application properties object
   */
  public Properties getProperties() {

    return properties;

  }

  /**
   * Returns A path string (in location file system notation) for the given file object
   *
   * @param fileObject file instance for which to generate a canonical path
   *
   * @return A canonical path string (in location file system notation) representing the given file object, or null if
   *         the file object is not representable in the file system
   */
  public String getPath(final File fileObject) {

    if (fileObject == null) {

      return null;

    }

    try {

      return fileObject.getCanonicalPath();

    } catch (Exception e) {

      logger.warn("Could not construct a canonical path for this file object <" + fileObject.toString() +
                  "> because of an error: " + Debug.prettyException(e));

      return null;

    }

  }

  /**
   * Returns the Meta/Z root directory as definied by the user, or "/" if not set
   *
   * @return The root directory specification This is _NOT_ a verified path
   */
  public String getRootSpec() {

    return properties.getProperty(PROP_ROOT_DIR, "/");

  }

  /**
   * Returns The Meta/Z root directory, as a File Object
   *
   * @return A file object representing the Meta/Z root directory Returns null if the MetaZ root directory canonical
   *         path could not be constructed
   */
  public File getRoot() {

    File   fileObject = new File(getRootSpec());
    String root = null;

    try {

      if (fileObject == null) {

        throw new Exception("Could not construct a file object");

      }

      root = fileObject.getCanonicalPath();

      if (root == null) {

        throw new Exception("Could not retrieve the canonical path");

      }

      return fileObject;

    } catch (Exception e) {

      logger.warn("Could not construct a canonical path for the MetaZ root directory <" + getRootSpec() +
                  "> because of an error: " + Debug.prettyException(e));

      return null;

    }

  }

  /**
   * Returns A String representing the complete specification for the given relative file name
   *
   * @param relativeFileName DOCUMENT ME!
   *
   * @return A String representing the complete path for the relative file specification This is _NOT_ a verified path
   */
  public String getRelativeFileSpec(final String relativeFileName) {

    if (relativeFileName == null) {

      return null;

    }

    return getPath(getRoot()) + File.separator + relativeFileName;

  }

  /**
   * Returns A File object representing the given file name (file name is relative to the root)
   *
   * @param relativeFileName DOCUMENT ME!
   *
   * @return A File object representing the given file name (file name is relative to the root) Returns null if the
   *         file canonical path could not be constructed
   */
  public File getRelativeFile(final String relativeFileName) {

    if (relativeFileName == null) {

      return null;

    }

    File   fileObject = new File(getRelativeFileSpec(relativeFileName));
    String file = null;

    try {

      if (fileObject == null) {

        throw new Exception("Could not construct a file object");

      }

      file = fileObject.getCanonicalPath();

      if (file == null) {

        throw new Exception("Could not retrieve the canonical path");

      }

      return fileObject;

    } catch (Exception e) {

      logger.warn("Could not construct a canonical path for the relative file <" +
                  getRelativeFileSpec(relativeFileName) + "> because of an error: " + Debug.prettyException(e));

      return null;

    }

  }

  /**
   * Returns A File object representing the MetaZ configuration file directory
   *
   * @return A File object representing the MetaZ configuration file directory Returns null if the MetaZ configuration
   *         file directory path could not be constructed
   */
  public File getConfigFilePath() {

    return getRelativeFile(METAZ_CONFIG_FILE_PATH);

  }

  /**
   * Seeds the Meta/Z application properties object
   *
   * @return Properties the seeded Meta/Z application properties object
   */
  private Properties readProperties() {

    properties = new Properties(System.getProperties());

    String fileName = System.getProperty("user.home") + File.separator + METAZ_PROPERTIES_FILE;

    InputStream propStream = null;

    // try to load from the user home
    try {

      propStream = new FileInputStream(fileName);

    } catch (Exception e) {

      if (verboseOutput) {

        logger.info("Could not open properties resource file from the user home <" + fileName + ">");

      }

    }

    if (propStream == null) {

      // try to load from the current directory
      fileName = System.getProperty("user.dir") + File.separator + METAZ_PROPERTIES_FILE;

      try {

        propStream = new FileInputStream(fileName);

      } catch (Exception e) {

        if (verboseOutput) {

          logger.info("Could not open properties resource file from the current directory <" + fileName + ">");

        }

      }

    }

    if (propStream == null) {

      // try to load from somewhere in the classpath
      propStream = getResourceStream(METAZ_PROPERTIES_FILE);

      if (propStream == null) {

        logger.warn("Could not open properties resource file from anywhere in the classpath location <" +
                    getClassPathString(getClassPath()) + ">");

      }

    }

    // attempt to load, if we obtained a valid stream
    if (propStream != null) {

      try {

        properties.load(propStream);

        if (verboseOutput) {

          StringWriter sw = new StringWriter();
          PrintWriter  pw = new PrintWriter(sw);

          properties.list(pw);
          pw.close();
          logger.info("About to list Meta/Z properties");
          logger.info(sw.toString());
          sw.close();

        }

      } catch (Exception e) {

        logger.warn("Exception <" + e.toString() + "> occurred while opening properties from <" +
                    propStream.toString() + ">");

      }

    }

    return properties;

  }

  /**
   * Looks up a resource named 'name' in the classpath, and return a stream pointing to that resource. The name
   * is assumed to be absolute and MUST use "/" to separate package segments (with an optional leading "/"). The
   * following names thus refer to the same resource:  org/metaz/resources/Resource.properties
   * /org/metaz/resources/Resource.properties  This method WILL find resources located within JAR files
   *
   * @param resourceName a resource name somewhere available in the classpath [may not be null]
   * @param loader classloader through which to load the resource [null is equivalent to the application loader]
   *
   * @return InputStream the located resoruce
   *
   * @throws IllegalArgumentException Thrown if the supplied resource name is null
   */
  public static InputStream getResourceStream(final String resourceName, final ClassLoader loader) {

    if (resourceName == null) {

      throw new IllegalArgumentException("Null input for name argument");

    }

    StringBuffer name = new StringBuffer(resourceName);

    if (name.charAt(0) == '/') {

      name.deleteCharAt(0);

    }

    // Returns null on lookup failures:
    InputStream in = null;

    ClassLoader classLoader = loader;

    if (classLoader == null) {

      classLoader = ClassLoader.getSystemClassLoader();

    }

    in = classLoader.getResourceAsStream(name.toString());

    if (in == null) {

      logger.info("Cannot open stream for resource <" + name.toString() + ">");

    }

    return in;

  }

  /**
   * A convenience overload of getProperties(String, ClassLoader) that uses the current thread's context
   * classloader.
   *
   * @param resourceName The property file name
   *
   * @return A Properties object seeded with the properties from the supplied file name
   */
  public static InputStream getResourceStream(final String resourceName) {

    return getResourceStream(resourceName, Thread.currentThread().getContextClassLoader());

  }

  /**
   * Returns the classpath as an array of URL's
   *
   * @param loader classloader for which to get the classpath
   *
   * @return the array of URL's representing the classpath
   */
  public static URL[] getClassPath(final ClassLoader loader) {

    ClassLoader classLoader = loader;

    if (classLoader == null) {

      classLoader = ClassLoader.getSystemClassLoader();

    }

    return ((URLClassLoader) classLoader).getURLs();

  }

  /**
   * A convenience overload of getClassPath(ClassLoader) that uses the current thread's context classloader
   *
   * @return Array of URL's representing the classpath
   */
  public static URL[] getClassPath() {

    return getClassPath(Thread.currentThread().getContextClassLoader());

  }

  /**
   * Returns the classpath as a platform specific string Simply call getClassPathString(getClassPath()) to get
   * the current (stringified ) classpath
   *
   * @param classPath an array of URL's representing a class path
   *
   * @return Stringified classpath
   */
  public static String getClassPathString(final URL[] classPath) {

    StringBuffer path = new StringBuffer();

    for (int i = 0; i < classPath.length; i++) {

      if (path.length() > 0) {

        path.append(System.getProperty("path.separator"));

      }

      path.append(classPath[i].toString());

    }

    return path.toString();

  }

}
