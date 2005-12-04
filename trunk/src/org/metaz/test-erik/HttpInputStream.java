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
import java.net.*;
import java.util.*;

public class HttpInputStream extends BufferedInputStream {
  public HttpInputStream (InputStream in) {
    super (in);
  }

  protected String method, path, queryString;
  protected float version;
  protected Hashtable headers = new Hashtable ();

  public void readRequest () throws IOException {
    String request = readLine ();
    if (request == null)
      throw new HttpException (HTTP.STATUS_BAD_REQUEST, "Null query");
    StringTokenizer parts = new StringTokenizer (request);
    try {
      parseMethod (parts.nextToken ());
      parseRequest (parts.nextToken ());
    } catch (NoSuchElementException ex) {
      throw new HttpException (HTTP.STATUS_BAD_REQUEST, request);
    }
    if (parts.hasMoreTokens ())
      parseVersion (parts.nextToken ());
    else
      version = 0.9f;
    if ((version < 1.0f) && (method == HTTP.METHOD_HEAD))
      throw new HttpException (HTTP.STATUS_NOT_ALLOWED, method);
    if (version >= 1.0f)
      readHeaders ();
  }

  protected void parseMethod (String method) throws HttpException {
    if (method.equals (HTTP.METHOD_GET))
      this.method = HTTP.METHOD_GET;
    else if (method.equals (HTTP.METHOD_POST))
      this.method = HTTP.METHOD_POST;
    else if (method.equals (HTTP.METHOD_HEAD))
      this.method = HTTP.METHOD_HEAD;
    else
      throw new HttpException (HTTP.STATUS_NOT_IMPLEMENTED, method);
  }

  protected void parseRequest (String request) throws HttpException {
    if (!request.startsWith ("/"))
      throw new HttpException (HTTP.STATUS_BAD_REQUEST, request);
    int queryIdx = request.indexOf ('?');
    if (queryIdx == -1) {
      path = HTTP.canonicalizePath (request);
      queryString = "";
    } else {
      path = HTTP.canonicalizePath (request.substring (0, queryIdx));
      queryString = request.substring (queryIdx + 1);
    }
  }

  protected void parseVersion (String verStr) throws HttpException {
    if (!verStr.startsWith ("HTTP/"))
      throw new HttpException (HTTP.STATUS_BAD_REQUEST, verStr);
    try {
      version = Float.valueOf (verStr.substring (5)).floatValue ();
    } catch (NumberFormatException ex) {
      throw new HttpException (HTTP.STATUS_BAD_REQUEST, verStr);
    }
  }

  protected void readHeaders () throws IOException {
    String header;
    while (((header = readLine ()) != null) && !header.equals ("")) {
      int colonIdx = header.indexOf (':');
      if (colonIdx != -1) {
        String name = header.substring (0, colonIdx);
        String value = header.substring (colonIdx + 1);
        headers.put (name.toLowerCase (), value.trim ());
      }
    }
  }

  public String readLine () throws IOException {
    StringBuffer line = new StringBuffer ();
    int c;
    while (((c = read ()) != -1) && (c != '\n') && (c != '\r'))
      line.append ((char) c);
    if ((c == '\r') && ((c = read ()) != '\n') && (c != -1))
      -- pos;
    return ((c == -1) && (line.length () == 0)) ? null : line.toString ();
  }

  public String getMethod () {
    return method;
  }

  public String getPath () {
    return path;
  }

  public String getQueryString () {
    return queryString;
  }

  public float getVersion () {
    return version;
  }

  public String getHeader (String name) {
    return (String) headers.get (name.toLowerCase ());
  }

  public Enumeration getHeaderNames () {
    return headers.keys ();
  }
}
