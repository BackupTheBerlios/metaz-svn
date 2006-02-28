package org.metaz.util;

/**
 * Nice helper class for equality testing, assists with implementing equals() functions Null values are handled
 * correctly The sister class of Equals is HashCode See javadoc for java.lang.Object for more information on both
 * methods  Falco Paul, 6 january 2006 Example usage: public boolean equals(Object anotherObject) { if (!
 * Equals.simpleTest(this, anotherObject)) return false; // save to typecast now... MyClass that = (MyClass)
 * anotherObject;  // Now perform any advanced testing... return Equals.test(this.property_1, that.property_1) &&
 * Equals.test(this.property_2, that.property_2) && .. Equals.test(this.property_x, that.property_x) &&
 * Equals.test(this.property_y, that.property_y) && Arrays.equals(this.property_z, that.property_z); } Note: use
 * Array.equals() to test for array field equality
 */
public final class Equals {

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method performs some basic equality checks on two instances (of any class) It does NOT perform a real
   * equality test... just some starter tests to rule out the obvious If this test passes, you can be sure that both
   * objects are NOT null, and are of the same class
   *
   * @param a The first object
   * @param b The second object
   *
   * @return boolean indicating wether these basic checks have passed succesfully
   */
  static public boolean simpleTest(Object a, Object b) {

    // real equality?
    if (a == b)

      return true;

    // if b == null and (a != b) then cannot be same
    if (b == null)

      return false;

    // we now both a and b are not null...
    if (a.getClass() != b.getClass())

      return false;

    // the test passes, we conclude a and b are both not null, and of the same class
    return true;

  }

  /**
   * Performs an equality test for two booleans
   *
   * @param a The first boolean
   * @param b The second boolean
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(boolean a, boolean b) {

    return a == b;

  }

  /**
   * Performs an equality test for two chars
   *
   * @param a The first char
   * @param b The second char
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(char a, char b) {

    return a == b;

  }

  /**
   * Performs an equality test for two longs (also works for bytes, shorts and ints)
   *
   * @param a The first long
   * @param b The second long
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(long a, long b) {

    return a == b;

  }

  /**
   * Performs an equality test for two floats
   *
   * @param a The first float
   * @param b The second float
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(float a, float b) {

    return Float.floatToIntBits(a) == Float.floatToIntBits(b);

  }

  /**
   * Performs an equality test for two doubles
   *
   * @param a The first double
   * @param b The second double
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(double a, double b) {

    return Double.doubleToLongBits(a) == Double.doubleToLongBits(b);

  }

  /**
   * Performs an equality test for two Objects
   *
   * @param a The first Object
   * @param b The second Object
   *
   * @return boolean indicating wether the objects are identicial
   */
  static public boolean test(Object a, Object b) {

    return (a == null) ? (b == null) : a.equals(b);

  }

}
