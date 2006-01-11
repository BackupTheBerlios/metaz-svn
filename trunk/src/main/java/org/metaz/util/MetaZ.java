package org.metaz.util;

import java.io.File;
import org.apache.log4j.*; // log4j stuff
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Falco Paul, Open University Netherlands, OTO Meta/Z project
 * Meta/Z application instance (implemented as a singleton object)
 * This class offers "global" services to application modules
 * The singleton pattern assures that only one instance will exist in the VM
 * And yes, I know there are some exceptions... 
 * See http://www.javaworld.com/javaworld/jw-01-2001/jw-0112-singleton.html for a discussion
 */
public class MetaZ implements java.io.Serializable { 

  public static final String METAZ_PROPERTIES_FILE = "metaz.props";
  public static final String METAZ_CONFIG_FILE_PATH = "config/metaz";
  
  public static final String PROP_LOG4J_CONFIG_FILE = "log4j.config.file";
  public static final String PROP_ROOT_DIR = "root.dir";

  /**
   * Global Meta/Z application instance, private and static to force singleton pattern
   */
  private static MetaZ instance; 
 
  private static Logger logger = Logger.getLogger(MetaZ.class); // logger instance for this class
  
  private InputStream propertyStream = null; // a stream pointing to the actual property file we use
  private Properties properties = null;      // application properties loaded from the above stream
  private String log4JConfigFile = null;     // log4J configuration file

  // private constructor

  private MetaZ() {
  
    // set up a default logging style, to be used as a fallback style in case of property
    // file loading errors

    PatternLayout layout = new PatternLayout(); 
    layout.setConversionPattern("%d{dd/mm/yy HH:mm:ss} %-5p[%t]: %m%n");

    ConsoleAppender appender = new ConsoleAppender(layout);
                                
    Logger rootLogger = Logger.getRootLogger();
    rootLogger.removeAllAppenders();
    rootLogger.addAppender(appender);
    rootLogger.setLevel((Level) Level.INFO);

    // attempt to load the log4j properties file

    properties = readProperties();
    log4JConfigFile = getRelativeFileSpec(getProperties().getProperty(PROP_LOG4J_CONFIG_FILE));
      
    if (log4JConfigFile == null)
      logger.warn("Could not seed log4J with a configuration file, using default output settings");
    else
    {
      PropertyConfigurator.configure(log4JConfigFile);
      logger.info("Seeded log4J with configuration file <" + log4JConfigFile + ">");
    }
    
    logger.info("Constructed Meta-Z application singleton instance <" + format(this) + ">");

  } 
  
  /**
   * Retrieves the Meta/Z application instance (allows for lazy initialization)
   * @return MetaZ the Meta/Z application instance
   */
  public static synchronized MetaZ getInstance() { 

  if (instance ==null) { 
    instance = new MetaZ(); 
  } 

  logger.info("Returned Meta-Z application singleton instance: <" + format(instance) + ">");

  return instance; 

 }

  /**
   * Gets a logger instance and initializes the logger look and feel
   * @param clazz classname where you would like a logger for
   * @return Logger a fully configured logger object
   */
  public static Logger getLogger(Class clazz)
  {
  
    // this next line actually forces initialization of the MetaZ singleton object
    // which in turns sets up the logger properties
    
    MetaZ metaz = getInstance();
    
    Logger logger = Logger.getLogger(clazz);
    logger.info("Created Logger object <" + format(logger) + ">");
    
    return logger;
    
  }
  
  /**
   * Returns a nice string describing an object, to be used in logging strings
   * Works better than calling toString() as that will print actual content in a lot of cases...
   * @param object  the object to be stringified
   * @return String stringified object
   */
  public static String format(Object object)
  {
    return object.getClass().getName() + '@' + Integer.toHexString(object.hashCode());
  }
  
  /**
   * Returns the current Meta/Z application properties object
   * @return Properties the Meta/Z application properties object
   */
  public Properties getProperties() 
  {
    return properties;
  }

  /**
   * Returns A path string (in location file system notation) for the given file object
   * @return A canonical path string (in location file system notation) representing the 
   *         given file object, or null if the file object is not representable in the file system
   */
  public String getPath(File fileObject)
  {
  
    if (fileObject == null)
      return null;
    
    try {
    
      return fileObject.getCanonicalPath();
      
    } catch (Exception e)
    {
      return null;
    }
    
  }


  /**
   * Returns the Meta/Z root directory as definied by the user, or "/" if not set
   * @return The root directory specification
   *         This is _NOT_ a verified path
   */
  public String getRootSpec()
  {
    return properties.getProperty(PROP_ROOT_DIR, "/");
  }

  /**
   * Returns The Meta/Z root directory, as a File Object
   * @return A file object representing the Meta/Z root directory
   *         Returns null if the MetaZ root directory canonical path could not be constructed
   */
  public File getRoot()
  {

    File fileObject  = new File(getRootSpec());
    String root = null;
    
    try {
    
      root = fileObject.getCanonicalPath();
      return fileObject;
      
    } catch (Exception e)
    {
      logger.warn("Could not construct a canonical path for the MetaZ root directory <" + getRootSpec() + ">");
      return null;
    }

  }

  /**
   * Returns A String representing the complete specification for the given relative file name
   * @return A String representing the complete path for the relative file specification
   *         This is _NOT_ a verified path
   */
  public String getRelativeFileSpec(String relativeFileName) 
  {
  
    if (relativeFileName == null)
      return null;
      
    return getPath(getRoot()) + File.separator + relativeFileName;
    
  }

  /**
   * Returns A File object representing the given file name (file name is relative to the root)
   * @return A File object representing the given file name (file name is relative to the root)
   *         Returns null if the file canonical path could not be constructed
   */
  public File getRelativeFile(String relativeFileName) 
  {

    if (relativeFileName == null)
      return null;

    File fileObject  = new File(getRelativeFileSpec(relativeFileName));
    String file = null;
    
    try {
    
      file = fileObject.getCanonicalPath();
      return fileObject;
      
    } catch (Exception e)
    {
      logger.warn("Could not construct a canonical path for the relative file <" + getRelativeFileSpec(relativeFileName) + ">");
      return null;
    }

  }

  /**
   * Returns A File object representing the MetaZ configuration file directory
   * @return A File object representing the MetaZ configuration file directory
   *         Returns null if the MetaZ configuration file directory path could not be constructed
   */
  public File getConfigFilePath() 
  {
    return getRelativeFile(METAZ_CONFIG_FILE_PATH);
  }

  /**
   * Seeds the Meta/Z application properties object
   * @return Properties the seeded Meta/Z application properties object
   */
  private Properties readProperties() 
  {
  
    Properties properties = new Properties(System.getProperties());
    
    String defaultFileName = System.getProperty("user.home") + 
                             File.separator + 
                             METAZ_PROPERTIES_FILE;
                             
    InputStream propStream = null;
    
    // try to load from the default location
    
    try {    

      propStream = new FileInputStream(defaultFileName);

    } catch (Exception e)
    {
      logger.info("Could not open properties resource file from default location <" + defaultFileName + ">");
    }

    // try to load from somewhere in the classpath

    if (propStream == null)
    {
    
      propStream = getResourceStream(METAZ_PROPERTIES_FILE);
      
      if (propStream == null)
        logger.warn("Could not open properties resource file from anywhere in the classpath location <" + getClassPathString(getClassPath())+ ">");
        
    }
    
    // attempt to load, if we obtained a valid stream
     
    if (propStream != null)
    {
    
      try 
      {
        
        properties.load(propStream);
        propertyStream = propStream;

      } catch (Exception e)
      {
      
        logger.warn("Exception <" + e.toString() + "> occurred while opening properties from <" + propStream.toString() + ">");
       
      }
      
    }
  
    return properties;
    
  }

  /**
   * Looks up a resource named 'name' in the classpath, and return a stream pointing
   * to that resource. The name is assumed to be absolute and MUST use "/" to separate
   * package segments (with an optional leading "/"). 
   * The following names thus refer to the same resource:
   *
   * org/metaz/resources/Resource.properties
   * /org/metaz/resources/Resource.properties
   * 
   * This method WILL find resources located within JAR files
   * 
   * @param resourceName a resource name somewhere available in the classpath [may not be null]
   * @param loader classloader through which to load the resource [null
   * is equivalent to the application loader]
   * @return InputStream the located resoruce
   */
  public static InputStream getResourceStream (String resourceName, ClassLoader loader)
  {

    if (resourceName == null)
      throw new IllegalArgumentException ("Null input for name argument");

    StringBuffer name = new StringBuffer(resourceName);
    
    if (name.charAt(0) == '/')
      name.deleteCharAt(0);

    // Returns null on lookup failures:

    InputStream in = null;
  
    if (loader == null) 
      loader = ClassLoader.getSystemClassLoader ();
    
    in = loader.getResourceAsStream(name.toString());
    
    if (in == null)
      logger.info("Cannot open stream for resource <" + name.toString() + ">");
    
    return in;
    
  }
  
  /**
   * A convenience overload of getProperties(String, ClassLoader)
   * that uses the current thread's context classloader.
   * @param   resourceName The property file name
   * @return  A Properties object seeded with the properties from the supplied file name
   */
   
  public static InputStream getResourceStream(String resourceName)
  {
    return getResourceStream(resourceName, Thread.currentThread ().getContextClassLoader ());
  }

  /**
   * Returns the classpath as an array of URL's
   * @param loader classloader for which to get the classpath
   * @return the   array of URL's representing the classpath
   */
  public static URL[] getClassPath (ClassLoader loader)
  {

    if (loader == null) loader = ClassLoader.getSystemClassLoader ();

    return ( (URLClassLoader) loader).getURLs();

  }

  /**
   * A convenience overload of getClassPath(ClassLoader) that uses the current thread's context classloader
   * @return Array of URL's representing the classpath
   */
  public static URL[] getClassPath ()
  {
    return getClassPath (Thread.currentThread ().getContextClassLoader ());
  }

  /**
   * Returns the classpath as a platform specific string
   * Simply call getClassPathString(getClassPath()) to get the current (stringified ) classpath
   * @param classPath an array of URL's representing a class path 
   * @return Stringified classpath
   */
  public static String getClassPathString (URL[] classPath)
  {
  
    StringBuffer path = new StringBuffer();
  
    for (int i = 0; i < classPath.length; i++)
    {
      
      if (path.length() > 0)
        path.append(System.getProperty("path.separator"));
        
      path.append(classPath[i].toString());
      
    }
      
    return path.toString();

  }

}