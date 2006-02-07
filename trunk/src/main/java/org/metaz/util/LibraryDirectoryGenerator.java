package org.metaz.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.log4j.Logger;

import org.metaz.domain.MetaData;
import org.metaz.gui.PrototypeCLI;

/**
 * @author Falco Paul
 */
public class LibraryDirectoryGenerator {

  private static Logger logger = MetaZ.getLogger(LibraryDirectoryGenerator.class);
  
  private static ArrayList getJarListing(File dir) 
                 throws IOException {
  
    ArrayList result = new ArrayList();
    
    if (! dir.isDirectory())
      return result;
    
    File[] filesAndDirs = dir.listFiles();
    java.util.List filesDirs = Arrays.asList(filesAndDirs);
    
    Iterator filesIter = filesDirs.iterator();
    File file = null;
    
    while (filesIter.hasNext()) {
    
      file = (File) filesIter.next();
      
      if (file.isFile())
        if (file.canRead())
          if (file.getName().endsWith(".jar"))
            result.add(file); //Always add, even if it's a directory.
      
      if (!file.isFile()) { //Recursive call if the current file is a directory.
        ArrayList deeperList = getJarListing(file);
        result.addAll(deeperList);
      }
        
    }
    
    Collections.sort(result);
  
    return result;
    
  }

  private static void populateLibraryDirectory(ArrayList jarFiles, File libDir) 
                 throws IOException {
  
    Iterator filesIter = jarFiles.iterator();
    
    File source = null;
    File dest   = null;
    
    while (filesIter.hasNext()) {
    
      source = (File) filesIter.next();
      dest   = new File(libDir + "/" + source.getName());
      
      System.out.println("Copy " + source.getCanonicalPath() + " to " + 
                                   dest.getCanonicalPath());
      
      InputStream in = new FileInputStream(source);
      OutputStream out = new FileOutputStream(dest);
  
      // Transfer bytes from in to out
      
      byte[] buf = new byte[1024];
      int len;
      
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      
      in.close();
      out.close();
      
        
    }
    
  }

	private static void createLibrary(String sourceDirSpec, String libDirSpec) {
  
		try {

      File libDir = new File(libDirSpec);
      
      if (! libDir.isDirectory()) {
        logger.error("Not a directory: " + libDirSpec);
        return;
      }
    
      ArrayList jarFiles = getJarListing(new File(sourceDirSpec));
      populateLibraryDirectory(jarFiles, libDir);
    
		} catch (Exception e) {
    
			e.printStackTrace();
      
		}
    
	}

	/**
	 * @param args
	 */
   
	public static void main(String[] args) {

	  System.out.println("Library directory generator by Falco Paul");

	  Options options = new Options();
    BasicParser parser = new BasicParser();
    CommandLine cmd = null;

	  Option sourceOpt =
	      new Option("r", "repo", false, "Maven repository root dir, containing JAR files");
	  sourceOpt.setArgs(1);
	  sourceOpt.setType(new String());
	  sourceOpt.setRequired(true);
	  options.addOption(sourceOpt);

	  Option libDirOpt =
	      new Option("l", "libdir", false, "Library directory to populate");
	  libDirOpt.setArgs(1);
	  libDirOpt.setType(new String());
	  libDirOpt.setRequired(true);
	  options.addOption(libDirOpt);

	  // Reading of the input

	  try {

     cmd = parser.parse(options, args);

	  } catch (Exception e) {

      System.out.println("Command line option parsing failed");
      System.out.println("Option error: " + e.toString());

      HelpFormatter f = new HelpFormatter();
      f.printHelp("Option format:", options);
      logger.error("Option error: " + e.getMessage());
	    java.lang.System.exit(0);

	  }
    
    String source = cmd.getOptionValue(sourceOpt.getOpt());
	  String libDir = cmd.getOptionValue(libDirOpt.getOpt());
    
		LibraryDirectoryGenerator.createLibrary(source, libDir);
    
		System.out.println("Finished");
    
	}

}
