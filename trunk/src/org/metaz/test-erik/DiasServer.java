import java.io.*;
import java.net.*;

/**
 * <p>Title: DiasServer.</p>
 * <p>Description: Server-applicatie voor het tonen van plaatjes.</p>
 * @author Ed de Geest & Erik Schouten
 * @version 1.0
 */

public class DiasServer extends Thread
{
  // attributen
  private PlaatjesDirectory plaatjesDirectory;
  protected Socket socket;

  DiasServer (Socket socket)
  {
    this.socket = socket;
  }

  public void run ()
  {
    try
    {
      // Input
      BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
      // Output
      OutputStream out = socket.getOutputStream();
      BufferedOutputStream bout = new BufferedOutputStream(out);
      // PrintWriter met autoflush
      PrintWriter pout = new PrintWriter(out, true);
      // Lees het commando
      String commando = in.readLine();
      if (commando == null)
        commando = "";
      if (commando.equals(Dias.COMMANDO_LIJST))
      {
        // Commando: lijst
        System.out.println("Commando: " + Dias.COMMANDO_LIJST);
        plaatjesDirectory = new PlaatjesDirectory(".");
        String[] directorynamen = plaatjesDirectory.getDirectorynames();
        for (int i=0; i<directorynamen.length; i++)
        {
          pout.println(directorynamen[i]);
          System.out.println("Directorynaam: " + directorynamen[i]);
        }
      }
      else
        if (commando.equals(Dias.COMMANDO_DIR))
        {
          // Commando: dir
          // Selecteren subdirectory's
          System.out.println("Commando: " + Dias.COMMANDO_DIR);
          String directory = in.readLine();
          System.out.println("Directorynaam: " + directory);
          // Selecteren bestanden
          plaatjesDirectory = new PlaatjesDirectory(directory);
          String[] bestandsnamen = plaatjesDirectory.getFilenames();
          // Verzenden lengte gevolgd door het bestand
          for (int i=0; i<bestandsnamen.length; i++)
          {
            int grootteBestand = plaatjesDirectory.getFileLength(bestandsnamen[i]);
            if (grootteBestand > 0)
            {
              // Verzenden lengte
              bout.write((byte)(0xff & (grootteBestand >> 24)));
              bout.write((byte)(0xff & (grootteBestand >> 16)));
              bout.write((byte)(0xff & (grootteBestand >> 8)));
              bout.write((byte)(0xff & grootteBestand));
              System.out.println(bestandsnamen[i] + ": " + grootteBestand + " bytes");
              // Ophalen inhoud bestand
              byte[] buffer = new byte[grootteBestand];
              buffer = plaatjesDirectory.getFile(bestandsnamen[i]);
              // Verzenden inhoud bestand
              ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(buffer);
              byte[] writeBuffer = new byte[Dias.DEFAULT_BUFFERGROOTTE];
              int aantalBytesGeschreven = 0;
              int numberRead = 0;
              while (aantalBytesGeschreven < grootteBestand &&
                     (numberRead = byteArrayIn.read(writeBuffer)) >= 0)
              {
                bout.write(writeBuffer, 0, numberRead);
                aantalBytesGeschreven += numberRead;
              }
              bout.flush();
            } // eind "if (grootteBestand > 0)"
          } // eind "for (int i=0; i<bestandsnamen.length; i++)"
          //out.flush();
        } //eind "(commando.equals(Dias.COMMANDO_DIR))"
      out.close();
      bout.close();
      pout.close();
      System.out.println("");
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
    finally
    {
      try
      {
        socket.close ();
      }
      catch (IOException ignored) {}
    }
  }

  public static void main(String[] args) throws IOException
  {
    if (args.length != 1)
      throw new IllegalArgumentException ("Syntax: DiasServer <port>");
    ServerSocket server = new ServerSocket (Integer.parseInt (args[0]));
    System.out.println("Gestart op poort " + args[0]);
    System.out.println("");
    while (true)
    {
      Socket client = server.accept ();
      System.out.println("Client " + client.getInetAddress() + " verbonden");
      DiasServer diasServer = new DiasServer (client);
      diasServer.start ();
    }
  }
}
