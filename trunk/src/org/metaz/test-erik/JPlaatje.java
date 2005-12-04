import java.io.*;
import java.awt.*;
import javax.swing.*;

public class JPlaatje extends JPanel
{
  protected ImageIcon plaatje;

  public JPlaatje(File bestand) throws IOException
  {
    if (!bestand.isFile())
      throw new IOException("Incorrecte bestandsnaam");
    try
    {
      String bestandsnaam = bestand.getCanonicalPath();
      plaatje = new ImageIcon(bestandsnaam);
    }
    catch (Exception ex)
    {
      throw new IOException("IOException: " + ex);
    }
  }

  public JPlaatje(byte[] buffer)
  {
    plaatje = new ImageIcon(buffer);
  }

  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(plaatje.getImage(), 0, 0, this);
  }
}