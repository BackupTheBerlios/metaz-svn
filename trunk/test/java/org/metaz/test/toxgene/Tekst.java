package org.metaz.test.toxgene;

import org.metaz.util.MetaZ;

import toxgene.ToXgene;

import toxgene.interfaces.ToXgeneCdataGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.Random;

/**
 * This class implements a simple cdata generator that produces random dutch text.
 *
 * @author Sammy Dalewyn
 * @version 1.0
 */
public class Tekst
  implements ToXgeneCdataGenerator
{

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private final static int len = 45669;
  private static String[]  data;
  private static boolean   loaded = false;
  private static Random    pick;
  private static String    result;
  private static MetaZ     app;
  private static String    wordlist = "test/toxgene/input/voc.txt";

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
     * Constructor. There are too many words to be loaded here, so
     * we cannot declare them explicitly as data = {...}, so we load the
     * words stored in an external text file.
     */
  public Tekst() {

    init();

  } // end Tekst()

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Specifies a seed for the random generator so that repeated executions always produce the same content, if
   * the same seed is provided.
   *
   * @param seed random seed to initialize the random generator
   */
  public void setRandomSeed(int seed) {

    pick = new Random(seed);

  } // end setRandomSeed()

  /**
   * Generates random dutch text that consists of at most wordCount words.
   *
   * @param wordCount the number of words
   *
   * @return a dutch words text string
   */
  public String getCdata(int wordCount) {

    //length == -1 means that the string should not be trimmed
    if (wordCount == -1) {

      wordCount = (int) (1024.0 * Math.random());

    } // end if

    if (wordCount == 0) {

      wordCount = 1;

    } // end if

    result = new String();

    for (int i = 0; i < wordCount; i++) {

      result += (Tekst.data[(int) pick.nextInt(len)] + " ");

    } // end for

    return result;

  } // end getCdata()

  /**
   * This method does all the work of loading the words into memory.
   */
  public static void init() {

    if (loaded) {

      return;

    }

    data = new String[len];

    try {

      app = MetaZ.getInstance();

      File            f = app.getRelativeFile(wordlist);
      FileInputStream fs = new FileInputStream(f);
      BufferedReader  reader = new BufferedReader(new InputStreamReader(fs));

      String line = null;
      int    count;

      count = 0;

      do {

        line = reader.readLine();

        if (line == null) {

          break;

        }

        data[count++] = line;

      } // end do
       while (true);

    } // end try
    catch (Exception e) {

      ToXgene.error("could not initialize text generator (tekst.txt)");

    } // end catch

    loaded = true;

  } // end init()

} // end Tekst
