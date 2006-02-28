package org.metaz.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Simple helper methods for debugging and stack traces, etc Will grow as needed...
 */
public class Debug extends Object {

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  // static inner helper class
  static public class OrchestratedException extends Exception {

    // Does nothing particular usefull, just used for pretty output
  }

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static Logger       logger = MetaZ.getLogger(Debug.class);
  private static final String STACK_DUMP_TEXT = "Orchestrated stack dump follows:";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Pretty prints an exception with DEBUG level to the logger
   *
   * @param e The exception to print nicely
   */
  public static void printException(Exception e) {

    logger.debug("Exception: " + e.getClass().getName() + ": " + e.getMessage());

  }

  /**
   * Pretty prints an exception and barfs a strack trace Prints everything with DEBUG level to the logger
   *
   * @param e The exception to print nicely
   */
  public static void printExceptionAndTrace(Exception e) {

    // Don't call printStack() from here... it will add another stack entry
    printException(e);

    Throwable throwable = new OrchestratedException();

    logger.debug(STACK_DUMP_TEXT, throwable);

  }

  /**
   * Barfs a strack trace Prints everything with DEBUG level to the logger
   *
   * @param message A message to print before the trace
   */
  public static void printTrace(String message) {

    logger.debug(message);

    Throwable throwable = new OrchestratedException();

    logger.debug(STACK_DUMP_TEXT, throwable);

  }

  /**
   * Prints all properties of an object to the logger. To print the properties, it invokes those getter methods
   * on objects that do not have parameters. Prints everything with DEBUG level to the logger
   *
   * @param obj The Object whose properties need to be printed.
   */
  public static void printObject(Object obj) {

    logger.debug("Printing Information on: " + obj);

    Method[] ms = obj.getClass().getMethods();

    logger.debug(" Class: " + obj.getClass().getName());

    for (int i = 0; i < ms.length; i++) {

      if (ms[i].getName().startsWith("get")) {

        Class[] cls = ms[i].getParameterTypes();

        if (cls.length == 0) {

          Object value;

          try {

            value = ms[i].invoke(obj, null);

          } catch (Exception e) {

            value = e.toString() + ":" + e.getMessage();

          }

          logger.debug("  " + ms[i].getName() + ": " + value);

        }

      }

    }

  }

}
