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

public class RedirectException extends HttpException {
  protected String location;
  
  public RedirectException (int code, String location) {
    super (code, "The document has moved <A HREF=\"" +
           location + "\">here</A>.");
    this.location = location;
  }

  public void processRequest (HttpOutputStream out) throws IOException {
    out.setHeader ("Location", location);
    super.processRequest (out);
  }
}
