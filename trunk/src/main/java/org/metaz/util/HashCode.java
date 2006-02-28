package org.metaz.util;

import java.lang.reflect.Array;

/**
 * Nice helper class to assist with hashCode() function implementations This class makes sure that you generate a
 * proper hashcode The sister class of HashCode is Equals See javadoc for java.lang.Object for more information on
 * both methods Falco Paul, 6 january 2006 Usage: int result = HashCode.SEED; result = HashCode.hash(result,
 * property_1); result = HashCode.hash(result, property_2); ... return   HashCode.hash(result, property_x);
 */
public final class HashCode {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final int ODD_PRIME_NUMBER = 37;

  /**
   * An initial value for a hashCode, to which is added contributions from fields. Using a non-zero value
   * decreases collisons of hashCode values.
   */
  public static final int SEED = 23;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  private static int firstTerm(int seed) {

    return ODD_PRIME_NUMBER * seed;

  }

  private static boolean isArray(Object object) {

    return object.getClass().isArray();

  }

  /**
   * Returns a hash code for a boolean parameter, given a seed
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, boolean value) {

    return firstTerm(seed) + (value ? 1 : 0);

  }

  /**
   * Returns a hash code for a int parameter, given a seed
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, char value) {

    return firstTerm(seed) + (int) value;

  }

  /**
   * Returns a hash code for a byte/short/int parameter, given a seed Note that bytes and shorts are also handled
   * by this method (through implicit conversion)
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, int value) {

    return firstTerm(seed) + value;

  }

  /**
   * Returns a hash code for a long parameter, given a seed
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, long value) {

    return firstTerm(seed) + (int) (value ^ (value >>> 32));

  }

  /**
   * Returns a hash code for a float parameter, given a seed
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, float value) {

    return hash(seed, Float.floatToIntBits(value));

  }

  /**
   * Returns a hash code for a double parameter, given a seed
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, double value) {

    return hash(seed, Double.doubleToLongBits(value));

  }

  /**
   * Returns a hash code for a Object parameter, given a seed Object may be null. Array objects are allowed If
   * Object is an array, then each element may be a primitive or a (possibly null) object
   *
   * @param seed The seed
   * @param value The value
   *
   * @return int     A hash code based on the given seed and value
   */
  public static int hash(int seed, Object value) {

    int result = seed;

    if (value == null) {

      result = hash(result, 0);

    } else if (! isArray(value)) {

      result = hash(result, value.hashCode());

    } else {

      int length = Array.getLength(value);

      for (int idx = 0; idx < length; ++idx) {

        Object item = Array.get(value, idx);

        //recursive call!
        result = hash(result, item);

      }

    }

    return result;

  }

}
