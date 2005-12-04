import java.util.*;
import java.io.*;

/**
 * <p>Title: ZipLezer.</p>
 * <p>Description: Applicatie voor het downloaden van zip bestanden. De user-
 * interactie vindt plaats via een dropdown menu.</p>
 * @author Ed de Geest & Erik Schouten
 * @version 1.0
 */

public class ZipLezer implements HttpClassProcessor
{
  protected File file;
  protected static final String[] EXTENSIONS = {".zip"};

  /**
    * Deze methode controleert de http request die aan ZipLezer gesteld.
    * @param in een HttpInputStream
    * @throws HttpException
    */
  public void initRequest(HttpInputStream in) throws HttpException {

    // controle op gebruik GET methode
    if (in.getMethod() != HTTP.METHOD_GET)
      throw new HttpException
          (HTTP.STATUS_NOT_ALLOWED,
           "Request method <TT>" + in.getMethod() + "</TT> not allowed.");

    // inlezen bestandsnaam
    String regel = HTTP.decodeString(in.getQueryString());
    StringTokenizer st = new StringTokenizer(regel, "=");
    st.nextToken();
    String zipNaam = st.nextToken();
    file = new File("zipfiles/" + zipNaam);

    // controle op aanwezigheid en leesbaarheid bestand
    if (!file.exists())
      throw new HttpException(HTTP.STATUS_NOT_FOUND,
                              "File <TT>" + zipNaam + "</TT> was not found.");
    if (file.isDirectory())
      throw new HttpException(HTTP.STATUS_NOT_FOUND,
                    "<TT>" + zipNaam + "</TT> is not a file, but a directory.");
    if (!file.isFile() || !file.canRead())
      throw new HttpException(HTTP.STATUS_FORBIDDEN, "File <TT>" + zipNaam
                              + "</TT> is not a file or cannot be read");
    boolean ok = false;
    for (int i = 0; i < EXTENSIONS.length; i++)
      ok = (ok || zipNaam.toLowerCase().endsWith(EXTENSIONS[i]));
    if (!ok)
      throw new HttpException(HTTP.STATUS_NOT_IMPLEMENTED, "File <TT>" +
                              zipNaam + "</TT> is not a zip-file.");
  }

  /**
    * Deze methode geeft de http response van de request aan ZipLezer.
    * @throws HttpException
    */
  public void processRequest(HttpOutputStream out) throws IOException {
    out.setHeader("Content-type", "application/zip");
    out.setHeader ("Content-length", String.valueOf (file.length ()));
    if (out.sendHeaders ())
    {
      FileInputStream in = new FileInputStream(file);
      out.write(in);
      in.close();
    }
  }
}
