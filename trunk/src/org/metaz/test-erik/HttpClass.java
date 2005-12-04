//package webserver;
/*
 * Java Network Programming, Second Edition
 * Merlin Hughes, Michael Shoffner, Derek Hamner
 * Manning Publications Company; ISBN 188477749X
 *
 * http://nitric.com/jnp/
 *
 * Copyright (c) 1997-1999 Merlin Hughes, Michael Shoffner, Derek Hamner;
 * all rights reserved; see license.txt for details.
 */

import java.io.*;

public class HttpClass implements HttpProcessor {
  protected HttpClassProcessor processor;

  public HttpClass (HttpInputStream in) throws IOException,
    IllegalAccessException, InstantiationException {
    String classPath = in.getPath ().substring (HTTP.CLASS_BIN.length ());
    //System.out.println("in.getPath = "+ in.getPath ());
    //System.out.println("classPath = "+ in.getPath ());
    int idx = classPath.indexOf ('/');
    String className = (idx < 0) ? classPath : classPath.substring (0, idx);
    //System.out.println("className = "+className);
    try {
      Class theClass = Class.forName (className);
      processor = (HttpClassProcessor) theClass.newInstance ();

    } catch (ClassNotFoundException ex) {
      throw new HttpException (HTTP.STATUS_NOT_FOUND,
                               "Class <TT>" + className + "</TT> not found.");
    }
    processor.initRequest (in);
  }

  public void processRequest (HttpOutputStream out) throws IOException {
    processor.processRequest (out);
  }
}
