package org.metaz.util;

import org.apache.log4j.*; // log4j stuff
import java.util.Arrays;
 
// @author Falco Paul, Open University Netherlands, OTO Meta/Z project

public class Example extends Object { 

  private static Logger logger = MetaZ.getLogger(Example.class);
  
  // some fake properties for usage in equals() and hashCode() demonstrations
  
  public int property_1;
  public String property_2;
  public Integer[] property_3;
  
  // example of equals() implementation using Equals class
  
  public boolean equals(Object anotherObject) {
  
     if (! Equals.simpleTest(this, anotherObject))
       return false;
  
     // save to typecast now...
  
     Example that = (Example) anotherObject;
     
     // Now perform any advanced testing...
     
     return Equals.test(this.property_1, that.property_1) &&
            Equals.test(this.property_2, that.property_2) &&
          Arrays.equals(this.property_3, this.property_3);
  
  }
  
  // example of hashCode() implementation using HashCode class

  public int hashCode() {

    int result = HashCode.SEED;
    
    result = HashCode.hash(result, property_1);
    result = HashCode.hash(result, property_2);
    return   HashCode.hash(result, property_3);    
    
  }

  public static void main(String []args) {
  
    MetaZ app = MetaZ.getInstance();
  
    logger.info("Hello, logging world");

    System.out.println("Root dir = " + app.getPath(app.getRoot()));
    System.out.println("Config file dir = " + app.getPath(app.getConfigFilePath()));
    System.out.println("ClassPath = " + app.getClassPathString(app.getClassPath()));
    
    // Make sure we actually see DEBUG output
    
    Logger rootLogger = Logger.getRootLogger();
    rootLogger.setLevel((Level) Level.DEBUG);
    
    Debug.printException(new Exception("Test 1"));
    Debug.printExceptionAndTrace(new Exception("Test 2"));
    Debug.printObject(MetaZ.getInstance());
    Debug.printTrace("Test 3");
    
  }

}