// @author: Falco Paul

package org.metaz.gui.portal;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

public class PortalUtil {

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
 
}
