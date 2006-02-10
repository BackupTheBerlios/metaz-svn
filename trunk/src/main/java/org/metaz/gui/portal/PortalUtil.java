// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

public class PortalUtil {

  // entities for HTML escaping
  // see http://hotwired.lycos.com/webmonkey/reference/special_characters/
  
  static Object[][] entities = {
  
      // {"#39", new Integer(39)},       // ' - apostrophe
      
      {
      "quot", new Integer(34)}, { // " - double-quote
      "amp", new Integer(38)}, { // & - ampersand
      "lt", new Integer(60)}, { // < - less-than
      "gt", new Integer(62)}, { // > - greater-than
      "nbsp", new Integer(160)}, { // non-breaking space
      "copy", new Integer(169)}, { // © - copyright
      "reg", new Integer(174)}, { // ® - registered trademark
      "Agrave", new Integer(192)}, { // À - uppercase A, grave accent
      "Aacute", new Integer(193)}, { // Á - uppercase A, acute accent
      "Acirc", new Integer(194)}, { // Â - uppercase A, circumflex accent
      "Atilde", new Integer(195)}, { // Ã - uppercase A, tilde
      "Auml", new Integer(196)}, { // Ä - uppercase A, umlaut
      "Aring", new Integer(197)}, { // Å - uppercase A, ring
      "AElig", new Integer(198)}, { // Æ - uppercase AE
      "Ccedil", new Integer(199)}, { // Ç - uppercase C, cedilla
      "Egrave", new Integer(200)}, { // È - uppercase E, grave accent
      "Eacute", new Integer(201)}, { // É - uppercase E, acute accent
      "Ecirc", new Integer(202)}, { // Ê - uppercase E, circumflex accent
      "Euml", new Integer(203)}, { // Ë - uppercase E, umlaut
      "Igrave", new Integer(204)}, { // Ì - uppercase I, grave accent
      "Iacute", new Integer(205)}, { // Í - uppercase I, acute accent
      "Icirc", new Integer(206)}, { // Î - uppercase I, circumflex accent
      "Iuml", new Integer(207)}, { // Ï - uppercase I, umlaut
      "ETH", new Integer(208)}, { // Ð - uppercase Eth, Icelandic
      "Ntilde", new Integer(209)}, { // Ñ - uppercase N, tilde
      "Ograve", new Integer(210)}, { // Ò - uppercase O, grave accent
      "Oacute", new Integer(211)}, { // Ó - uppercase O, acute accent
      "Ocirc", new Integer(212)}, { // Ô - uppercase O, circumflex accent
      "Otilde", new Integer(213)}, { // Õ - uppercase O, tilde
      "Ouml", new Integer(214)}, { // Ö - uppercase O, umlaut
      "Oslash", new Integer(216)}, { // Ø - uppercase O, slash
      "Ugrave", new Integer(217)}, { // Ù - uppercase U, grave accent
      "Uacute", new Integer(218)}, { // Ú - uppercase U, acute accent
      "Ucirc", new Integer(219)}, { // Û - uppercase U, circumflex accent
      "Uuml", new Integer(220)}, { // Ü - uppercase U, umlaut
      "Yacute", new Integer(221)}, { // Ý - uppercase Y, acute accent
      "THORN", new Integer(222)}, { // Þ - uppercase THORN, Icelandic
      "szlig", new Integer(223)}, { // ß - lowercase sharps, German
      "agrave", new Integer(224)}, { // à - lowercase a, grave accent
      "aacute", new Integer(225)}, { // á - lowercase a, acute accent
      "acirc", new Integer(226)}, { // â - lowercase a, circumflex accent
      "atilde", new Integer(227)}, { // ã - lowercase a, tilde
      "auml", new Integer(228)}, { // ä - lowercase a, umlaut
      "aring", new Integer(229)}, { // å - lowercase a, ring
      "aelig", new Integer(230)}, { // æ - lowercase ae
      "ccedil", new Integer(231)}, { // ç - lowercase c, cedilla
      "egrave", new Integer(232)}, { // è - lowercase e, grave accent
      "eacute", new Integer(233)}, { // é - lowercase e, acute accent
      "ecirc", new Integer(234)}, { // ê - lowercase e, circumflex accent
      "euml", new Integer(235)}, { // ë - lowercase e, umlaut
      "igrave", new Integer(236)}, { // ì - lowercase i, grave accent
      "iacute", new Integer(237)}, { // í - lowercase i, acute accent
      "icirc", new Integer(238)}, { // î - lowercase i, circumflex accent
      "iuml", new Integer(239)}, { // ï - lowercase i, umlaut
      "igrave", new Integer(236)}, { // ì - lowercase i, grave accent
      "iacute", new Integer(237)}, { // í - lowercase i, acute accent
      "icirc", new Integer(238)}, { // î - lowercase i, circumflex accent
      "iuml", new Integer(239)}, { // ï - lowercase i, umlaut
      "eth", new Integer(240)}, { // ð - lowercase eth, Icelandic
      "ntilde", new Integer(241)}, { // ñ - lowercase n, tilde
      "ograve", new Integer(242)}, { // ò - lowercase o, grave accent
      "oacute", new Integer(243)}, { // ó - lowercase o, acute accent
      "ocirc", new Integer(244)}, { // ô - lowercase o, circumflex accent
      "otilde", new Integer(245)}, { // õ - lowercase o, tilde
      "ouml", new Integer(246)}, { // ö - lowercase o, umlaut
      "oslash", new Integer(248)}, { // ø - lowercase o, slash
      "ugrave", new Integer(249)}, { // ù - lowercase u, grave accent
      "uacute", new Integer(250)}, { // ú - lowercase u, acute accent
      "ucirc", new Integer(251)}, { // û - lowercase u, circumflex accent
      "uuml", new Integer(252)}, { // ü - lowercase u, umlaut
      "yacute", new Integer(253)}, { // ý - lowercase y, acute accent
      "thorn", new Integer(254)}, { // þ - lowercase thorn, Icelandic
      "yuml", new Integer(255)}, { // ÿ - lowercase y, umlaut
      "euro", new Integer(8364)}, // Euro symbol
  };
  
  static Map e2i = new HashMap();
  static Map i2e = new HashMap();
  
  // static initializer
  
  static {
  
    for (int i = 0; i < entities.length; ++i) {
    
      e2i.put(entities[i][0], entities[i][1]);
      i2e.put(entities[i][1], entities[i][0]);
      
    }
    
  }

  private static Logger logger = Logger.getLogger(PortalUtil.class); // logger instance for this class

  public final static String REQUEST_URL_KEY = "Request.Url";

  public static String linkHtml(String text, String tooltip, String url, String styleClass, String target) {
  
    StringBuffer html = new StringBuffer();
    
    html.append("<a ");
    
    if (styleClass != null)
      html.append("class=\"" + styleClass + "\" ");

    html.append("href=\"" + url + "\" ");

    if (tooltip != null)
      html.append("title=\"" + tooltip + "\" ");
      
    if (target != null)
      html.append("target=\"" + target + "\"");
    
    html.append(">" + text + "</a>");
    
    return html.toString();

  }

  public static String getPortalHostName(HttpServletRequest request) 
  {
  
    return request.getServerName();
    
  }
  
  // warning: this function uses the localhost function
  //          it's saver to use the HttpServletRequest object version!

  public static String getPortalHostName() 
  {

    String hostName;
    
    try 
    {
    
      hostName = InetAddress.getLocalHost().getHostName();
      
    } catch (Exception e)
    {
    
      hostName = "localhost";      
      
    }
   
    return hostName;
    
  }

  public static String getPortalBaseUrl(HttpServletRequest request)
  {
  
    String protocol;
    int    index = request.getProtocol().indexOf("/");
    
    if (index == -1)
    {
    
      if (request.isSecure())
        protocol = "https";
      else
        protocol = "http";
        
    }
    else
      
      protocol = request.getProtocol().substring(0, index).toLowerCase();
      
    String portString = "";
      
    // check for default port numbers
    
    if (protocol.equals("http"))
      if (request.getServerPort() != 80)
        portString = new String(":" + request.getServerPort());
 
    if (protocol.equals("https"))
      if (request.getServerPort() != 443)
        portString = new String(":" + request.getServerPort());
    
    return protocol + "://" + request.getServerName() + portString + request.getContextPath();
    
  }

  public static String getDefaultSecurePortalBaseUrl(HttpServletRequest request)
  {
  
    return "https://" + request.getServerName() + ":8443" + request.getContextPath();
    
  }
 
  // returns a string with just the URL path?query#ref part
  // To be exact: the protocol, host, port and context path are stripped off

  public static String relativeUrl(HttpServletRequest request, String url) {
  
    if (url == null)
      return null;

    try 
    {

      URL urlObject = new URL(url);
      StringBuffer pathQueryRef = new StringBuffer(urlObject.getPath());
      
      if (request.getContextPath() != null)
      {
      
        String context = request.getContextPath() + "/";

        if (urlObject.getPath().startsWith(context))
          pathQueryRef.delete(0, context.length());
          
      }
      
      if (urlObject.getQuery() != null)
        pathQueryRef.append("?").append(urlObject.getQuery());

      if (urlObject.getRef() != null)
        pathQueryRef.append("#").append(urlObject.getRef());
        
      return pathQueryRef.toString();

    } catch (Exception e)
    {
    
      logger.error(e);

      return null;

    }

  }

  public static String relativeUrl(HttpServletRequest request, URL url) {
  
    if (url == null)
      return null;
  
    return relativeUrl(request, url.toExternalForm());
  
  }

  public static void setRequestObject(ServletRequest request, String key, Object value) {

   request.setAttribute(key, value);

  }

  public static Object getRequestObject(ServletRequest request, String key, Object defaultValue) {
  
    Object content = request.getAttribute(key);
    
    if (content == null)
      return defaultValue;
     
    return content;

  }

  public static void removeRequestObject(ServletRequest request, String key) {
  
    request.removeAttribute(key);

  }

  public static void setSessionObject(HttpSession session, String key, Object value) {

   session.setAttribute(key, value);

  }

  public static Object getSessionObject(HttpSession session, String key, Object defaultValue) {
  
    Object content = session.getAttribute(key);
    
    if (content == null)
      return defaultValue;
     
    return content;

  }

  public static void removeSessionObject(HttpSession session, String key) {
  
    session.removeAttribute(key);

  }

  public static String getRequestUrl(HttpServletRequest request) {
  
    return (String) getRequestObject(request, REQUEST_URL_KEY, request.getRequestURL().toString());

  }

  public static void setRequestUrl(HttpServletRequest request) {

    setRequestObject(request, PortalUtil.REQUEST_URL_KEY, request.getRequestURL().toString());

  }
  
  /**
   * Turns funky characters into HTML entity equivalents<p>
   * e.g. <tt>"bread" & "butter"</tt> => <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
   * Update: supports nearly all HTML entities, including funky accents. See the source code for more detail.
   * @see #htmlunescape(String)
   **/
   
  public static String htmlEscape(String s1) {
  
    StringBuffer buf = new StringBuffer();
    
    int i;
    
    for (i = 0; i < s1.length(); ++i) {
    
      char ch = s1.charAt(i);
      
      String entity = (String) i2e.get(new Integer( (int) ch));
      
      if (entity == null) {
      
        if ( ( (int) ch) > 128) {
          buf.append("&#" + ( (int) ch) + ";");
        }
        else {
          buf.append(ch);
        }
        
      }
      else {
      
        buf.append("&" + entity + ";");
        
      }
      
    }
    
    return buf.toString();
    
  }
  
  /**
   * Given a string containing entity escapes, returns a string
   * containing the actual Unicode characters corresponding to the
   * escapes.
   *
   * @see #htmlEscape(String)
   **/

  public static String htmlUnescape(String s1) {

    StringBuffer buf = new StringBuffer();

    int i;

    for (i = 0; i < s1.length(); ++i) {

      char ch = s1.charAt(i);

      if (ch == '&') {
        int semi = s1.indexOf(';', i + 1);
        if (semi == -1) {
          buf.append(ch);
          continue;
        }

        String entity = s1.substring(i + 1, semi);
        Integer iso;

        if (entity.charAt(0) == '#') {
          iso = new Integer(entity.substring(1));
        }
        else {
          iso = (Integer) e2i.get(entity);
        }

        if (iso == null) {
          buf.append("&" + entity + ";");
        }
        else {
          buf.append( (char) (iso.intValue()));
        }

        i = semi;

      }
      else {

        buf.append(ch);

      }

    }

    return buf.toString();

  }
  
  /**
   * Prepares a string for output inside a JavaScript string,
   * e.g. for use inside a document.write("") command.
   *
   * Example:
   * <pre>
   * input string: He didn't say, "Stop!"
   * output string: He didn\'t say, \"Stop!\"
   * </pre>
   *
   * Deals with quotes and control-chars (tab, backslash, cr, ff, etc.)
   * Bug: does not yet properly escape Unicode / high-bit characters.
   *
   * @see #jsEscape(String, Writer)
   **/
   
  public static String jsEscape(String source) {
  
    try {
    
      StringWriter sw = new StringWriter();
      jsEscape(source, sw);
      sw.flush();
      
      return sw.toString();
      
    }
    catch (IOException ioe) {
    
      // should never happen writing to a StringWriter
      ioe.printStackTrace();
      
      return null;
      
    }
  }
  
  /**
   * @see #javaEscape(String, Writer)
   **/
   
  public static String javaEscape(String source) {
  
    try {
    
      StringWriter sw = new StringWriter();
      javaEscape(source, sw);
      sw.flush();
      
      return sw.toString();
      
    }
    catch (IOException ioe) {
    
      // should never happen writing to a StringWriter
      ioe.printStackTrace();
      
      return null;
      
    }
    
  }

  // Little helper function
  
  private static void zeroPad(Writer out, String s, int width) throws IOException {
  
    while (width > s.length()) {
    
      out.write('0');
      width--;
      
    }
    
    out.write(s);
    
  }
  
  // helper function for escaping strings
  
  private static void stringEscape(String source, Writer out,
                                   boolean escapeSingleQuote) throws IOException {
                                   
    char[] chars = source.toCharArray();
    
    for (int i = 0; i < chars.length; ++i) {
    
      char ch = chars[i];
      
      switch (ch) {
      
        case '\b': // backspace (ASCII 8)

          out.write("\\b");
          break;
          
        case '\t': // horizontal tab (ASCII 9)

          out.write("\\t");
          break;
          
        case '\n': // newline (ASCII 10)

          out.write("\\n");
          break;
          
        case 11: // vertical tab (ASCII 11)

          out.write("\\v");
          break;
          
        case '\f': // form feed (ASCII 12)

          out.write("\\f");
          break;
          
        case '\r': // carriage return (ASCII 13)

          out.write("\\r");
          break;
          
        case '"': // double-quote (ASCII 34)

          out.write("\\\"");
          break;
          
        case '\'': // single-quote (ASCII 39)

          if (escapeSingleQuote) {
            out.write("\\'");
          }
          else {
            out.write("'");
          }
          break;
          
        case '\\': // literal backslash (ASCII 92)

          out.write("\\\\");
          break;
          
        default:
        
          if ( (int) ch < 32 || (int) ch > 127) {
          
            out.write("\\u");
            zeroPad(out, Integer.toHexString(ch).toUpperCase(), 4);
            
          }
          
          else {
          
            out.write(ch);
            
          }
          break;
          
      }
      
    }
    
  }
 
  /**
   * Prepares a string for output inside a JavaScript string,
   * e.g. for use inside a document.write("") command.
   *
   * Example:
   * <pre>
   * input string: He didn't say, "stop!"
   * output string: He didn\'t say, \"stop!\"
   * </pre>
   *
   * Deals with quotes and control-chars (tab, backslash, cr, ff, etc.)
   *
   * @see #jsEscape(String)
   **/
   
  public static void jsEscape(String source, Writer out) throws IOException {
  
    stringEscape(source, out, true);
    
  }
  
  /**
   * Prepares a string for output inside a Java string,
   *
   * Example:
   * <pre>
   * input string: He didn't say, "stop!"
   * output string: He didn't say, \"stop!\"
   * </pre>
   *
   * Deals with quotes and control-chars (tab, backslash, cr, ff, etc.)
   *
   * @see #jsEscape(String,Writer)
   **/

  public static void javaEscape(String source, Writer out) throws IOException {

    stringEscape(source, out, false);

  }

}
