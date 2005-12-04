import java.io.*;

/**
 * <p>Title: PlaatjesDirecory.</p>
 * <p>Description: Representeert een bestaande directory.</p>
 * @author Ed de Geest & Erik Schouten
 * @version 2.0
 */

public class PlaatjesDirectory
{
  /**
   * De naam van de directory.
   */
  protected File filename;
  /**
   * De toegestane extensions.
   */
  protected static final String[] EXTENSIONS = {".gif", ".GIF", ".jpg", ".JPG"};

  /**
   * De constructor verwacht een parameter van type String, de naam van
   * de directory en gooit een instantie van IOException op als de
   * parameter geen geldige directory-naam is.
   * @param name de naam van de directory.
   * @throws IOException als de parameter geen geldige directory-naam is.
   */
  public PlaatjesDirectory(String name) throws IOException
  {
    filename = new File(name);
    if (filename.isDirectory())
      this.filename = filename;
    else
      throw new IOException("Incorrecte directorynaam");
  }

  /**
   * Levert de bestandsnamen uit de opgegeven directory.
   * @return de bestandsnamen.
   */
  public String[] getFilenames()
  {
    // Filter de bestandsnamen op de EXTENSIONS
    FilenameFilter only = new NaamFilter();
    String[] filenames = new String[]{};
    filenames = filename.list(only);
    return filenames;
  }

  /**
   * Levert de directorynamen uit de opgegeven directory.
   * @return de directorynamen.
   */
  public String[] getDirectorynames()
  {
    // Filter de bestandsnamen op directory's
    FilenameFilter only = new DirectoryFilter();
    String[] directorynames = new String[]{};
    directorynames = filename.list(only);
    return directorynames;
  }

  /**
   * Levert de lengte op van de bestandsnaam uit de opgegeven directory.
   * @param name de naam van het bestand.
   * @return de lengte van het bestand
   *            of -1 als het bestand niet bestaat.
   */
  public int getFileLength(String name)
  {
    File file = new File(filename, name);
    if (file.exists())
      return (int)file.length();
    else
      return -1;
  }

  /**
   * Levert de inhoud van het bestand uit de opgegeven directory.
   * @param name de naam van het bestand.
   * @return een array van bytes met de inhoud van het bestand of
   *         een arryay van 0 bytes als het bestand niet bestaat.
   */
  public byte[] getFile(String name)
  {
    byte[] returnBuffer = new byte[0];
    File file = new File(filename, name);
    if (file.exists())
    {
      try
      {
        FileInputStream fin = new FileInputStream(file);
        byte[] buffer = new byte[ (int) file.length()];
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int numberRead;
        while ( (numberRead = fin.read(buffer)) >= 0)
          byteArrayOut.write(buffer, 0, numberRead);
        byteArrayOut.flush();
        returnBuffer = byteArrayOut.toByteArray();
        fin.close();
      }
      catch (IOException ex) {}
    }
    return returnBuffer;
  }


  /**
   * Binnenklasse van PlaatjesDirectory waarin de in de directory
   * voorkomende namen worden gefilterd op EXTENSIONS.
   */
  public class NaamFilter implements FilenameFilter
  {
    public boolean accept(File dir, String name)
    {
      boolean ok = false;
      for (int i = 0; i < EXTENSIONS.length; i++)
        ok = (ok || name.endsWith(EXTENSIONS[i]));
      return ok;
    }
  }

  /**
   * Binnenklasse van PlaatjesDirectory waarin de in de directory
   * voorkomende namen worden gefilterd op directory's.
   */
  public class DirectoryFilter implements FilenameFilter
  {
    public boolean accept(File dir, String name)
    {
      boolean ok = false;
      File file = new File(name);
      ok = file.isDirectory();
      return ok;

    }
  }

}


