package org.metaz.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import java.util.Arrays;

// log4j stuff
/**
 * Simple example class to demonstrate some org.metaz.util package features
 *
 * @author author Falco Paul, Open University Netherlands, OTO Meta/Z project
 * @version $Revision$
 */
public class Example extends Object {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger logger = MetaZ.getLogger(Example.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // some fake properties for usage in equals() and hashCode() demonstrations
  private int       property1;
  private String    property2;
  private Integer[] property3;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Equals usage example
   *
   * @param anotherObject object to test for equality
   *
   * @return true if equal, false if unequal
   */
  private boolean testEquals(Object anotherObject) {

    if (! Equals.simpleTest(this, anotherObject))

      return false;

    // save to typecast now...
    Example that = (Example) anotherObject;

    // Now perform any advanced testing...
    return Equals.test(this.property1, that.property1) && Equals.test(this.property2, that.property2) &&
           Arrays.equals(this.property3, that.property3);

  }

  // example of hashCode() implementation using HashCode class
  /**
   * Hashcode usage example
   *
   * @return hashcode
   */
  private int testHashCode() {

    int result = HashCode.SEED;

    result = HashCode.hash(result, property1);
    result = HashCode.hash(result, property2);

    return HashCode.hash(result, property3);

  }

  /**
   * Main method
   *
   * @param args run time arguments
   */
  public static void main(String[] args) {

    MetaZ app = MetaZ.getInstance();

    logger.info("Hello, logging world");

    System.out.println("Java home dir = " + System.getProperty("user.home"));
    System.out.println("Root = " + app.getRoot());
    System.out.println("Root dir path = " + app.getPath(app.getRoot()));
    System.out.println("Config file dir = " + app.getPath(app.getConfigFilePath()));
    System.out.println("ClassPath = " + app.getClassPathString(app.getClassPath()));

    File testFile = app.getRelativeFile("data/testfile.dat");

    try {

      System.out.println("My file = " + testFile.getCanonicalPath());

    } catch (Exception e) {

      logger.error(testFile, e);

    }

    // Make sure we actually see DEBUG output
    Logger rootLogger = Logger.getRootLogger();

    rootLogger.setLevel((Level) Level.DEBUG);

    Debug.printException(new Exception("Test 1"));
    Debug.printExceptionAndTrace(new Exception("Test 2"));
    Debug.printObject(MetaZ.getInstance());
    Debug.printTrace("Test 3");

  }

}
