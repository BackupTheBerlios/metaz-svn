package org.metaz.test.toxgene;

import toxgene.ToXgene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import toxgene.interfaces.ToXgeneCdataGenerator;
import java.util.Random;

import org.metaz.util.MetaZ;


public class Tekst implements ToXgeneCdataGenerator {

    public final static int len=45669;
    public static String[] data;
    public static boolean loaded = false;

    private static Random pick;
    private static String result;
    
    private static MetaZ app;
    private static String wordlist = "test/toxgene/input/voc.txt";
    
    /**
     * Constructor. There are too many words to be loaded here, so
     * we cannot declare them explicitly as data = {...}, so we load the
     * words stored in a text file.
     */
    public Tekst(){
            init();
    }
    
    /**
     * Specifies a seed for the random generator so that repeated
     * executions always produce the same content, if the same seed is
     * provided.
     */
    public void setRandomSeed(int seed){
            pick = new Random(seed);
    }
    
    /**
     * Generates random text that is at most @param length characters
     * long if @param lenght is different than -1.
     */
    public String getCdata(int wordCount){
            //length == -1 means that the string should not be trimmed
            if (wordCount == -1){
                wordCount = (int) (1024.0 * Math.random());
            }
            if (wordCount == 0){
                wordCount = 1;
            }
            result = new String();
            
            for(int i=0; i<wordCount; i++){
                result += Tekst.data[(int)pick.nextInt(len)] + " "; 
            }
            return result;

            
    }

    /**
     * This method does all the work of loading the words into
     * memory.
     */
    public static void init(){
            if (loaded)
                    return;

            data = new String[len];

            try{
                    app = MetaZ.getInstance();
                    File f = app.getRelativeFile(wordlist);
                    FileInputStream fs = new FileInputStream(f);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fs));

                    String line = null;
                    int count;
                    count = 0;
                    do{
                            line = reader.readLine();
                            if (line == null)
                                    break;
                            data[count++] = line;
                    }while (true);
            }
            catch (Exception e){
                    ToXgene.error("could not initialize text generator (tekst.txt)");
            }

            loaded = true;
    }

}
