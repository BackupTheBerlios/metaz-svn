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

public class Echo implements HttpClassProcessor {
  protected String message;
  
  public void initRequest (HttpInputStream in) throws IOException {
    if (in.getMethod () != HTTP.METHOD_GET)
      throw new HttpException
        (HTTP.STATUS_NOT_ALLOWED,
         "Request method <TT>" + in.getMethod () + "</TT> not allowed.");
    message = HTTP.decodeString (in.getQueryString ());
  }

  public void processRequest (HttpOutputStream out) throws IOException {
    out.setHeader ("Content-type", "text/plain");
    if (out.sendHeaders ())
      out.write (message + "\r\n");
  }
}
