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

public class HttpException extends IOException implements HttpProcessor {
  protected int code;

  public HttpException (int code, String detail) {
    super (detail);
    this.code = code;
  }

  public void processRequest (HttpOutputStream out) throws IOException {
    out.setCode (code);
    out.setHeader ("Content-Type", "text/html");
    //System.out.println("bla bla");
    if (out.sendHeaders ()) {
      String msg = HTTP.getCodeMessage (code);
      out.write ("<HTML><HEAD><TITLE>" + code + " " +
                 msg + "</TITLE></HEAD>\n" + "<BODY><H1>" + msg + "</H1>\n" +
                 getMessage () + "<P>\n</BODY></HTML>\n");
    }
  }
}
