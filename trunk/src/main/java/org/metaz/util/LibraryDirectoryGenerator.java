package org.metaz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Simple utility class that generates a "library" direcotry from a (Maven) JAR tree
 *
 * @author Falco Paul
 */
public final class LibraryDirectoryGenerator {

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Private constructor, prevents instantiation
   */
  private LibraryDirectoryGenerator() {

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Get a list of available JAR's
   *
   * @param dir directory to scan
   *
   * @return ArrayList filled with JAR founds (File objects)
   *
   * @throws IOException Thrown on some I/O error
   */
  private static ArrayList getJarListing(File dir)
                                  throws IOException
  {

    ArrayList result = new ArrayList();

    if (! dir.isDirectory())

      return result;

    File[]         filesAndDirs = dir.listFiles();
    java.util.List filesDirs = Arrays.asList(filesAndDirs);

    Iterator filesIter = filesDirs.iterator();
    File     file = null;

    while (filesIter.hasNext()) {

      file = (File) filesIter.next();

      if (file.isFile())

        if (file.canRead())

          if (file.getName().endsWith(".jar"))
            result.add(file); //Always add, even if it's a directory.

      if (! file.isFile()) { //Recursive call if the current file is a directory.

        ArrayList deeperList = getJarListing(file);

        result.addAll(deeperList);

      }

    }

    Collections.sort(result);

    return result;

  }

  /**
   * Copy available jars to the target library
   *
   * @param jarFiles ArrayList filled with JAR files (File objects)
   * @param libDir Target library directory
   *
   * @throws IOException IOException Thrown on some I/O error
   */
  private static void populateLibraryDirectory(ArrayList jarFiles, File libDir)
                                        throws IOException
  {

    Iterator filesIter = jarFiles.iterator();

    File     source = null;
    File     dest = null;

    while (filesIter.hasNext()) {

      source = (File) filesIter.next();
      dest = new File(libDir + "/" + source.getName());

      System.out.println("Copy " + source.getCanonicalPath() + " to " + dest.getCanonicalPath());

      InputStream  in = new FileInputStream(source);
      OutputStream out = new FileOutputStream(dest);

      // Transfer bytes from in to out
      byte[] buf = new byte[1024];
      int    len;

      while ((len = in.read(buf)) > 0) {

        out.write(buf, 0, len);

      }

      in.close();
      out.close();

    }

  }

  /**
   * Core logic starts here
   *
   * @param sourceDirSpec input source directory
   * @param libDirSpec target library directory
   */
  private static void createLibrary(String sourceDirSpec, String libDirSpec) {

    try {

      File libDir = new File(libDirSpec);

      if (! libDir.isDirectory()) {

        System.out.println("Not a directory: " + libDirSpec);

        return;

      }

      ArrayList jarFiles = getJarListing(new File(sourceDirSpec));

      populateLibraryDirectory(jarFiles, libDir);

    } catch (Exception e) {

      e.printStackTrace();

    }

  }

  /**
   * Main method
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {

    System.out.println("Library directory generator by Falco Paul");

    if (args.length < 2) {

      System.out.println("Usage: LibraryDirectoryGenerator MavenRootDir LibraryTargetDir");

    }

    String source = args[0];
    String libDir = args[1];

    LibraryDirectoryGenerator.createLibrary(source, libDir);

    System.out.println("Finished");

  }

}
