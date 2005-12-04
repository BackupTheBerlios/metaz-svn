import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: DiaServer.</p>
 * <p>Description: Client-applicatie voor het tonen van plaatjes.</p>
 * @author Ed de Geest & Erik Schouten
 * @version 1.0
 */

public class DiasClient extends JFrame
{
  // attributen
  protected Socket socket;
  protected String host, protocol;
  protected int poort;
  protected String selectie;
  /**
   * Timer voor het periodiek tonen van een (volgend) plaatje.
   */
  protected Timer timer;
  /**
   * Het ingetoetste tempo in seconden.
   */
  protected int tempo;
  /**
   * De vector waarin de plaatjes worden opgeslagen.
   */
  protected Vector vector = new Vector();

  JLabel serverLabel = new JLabel();
  JTextField serverVeld = new JTextField();
  JButton verbindingKnop = new JButton();
  JLabel serverMeldingVeld = new JLabel();
  JPanel plaatjePanel = new JPanel();
  JLabel tempoLabel = new JLabel();
  JTextField tempoVeld = new JTextField();
  JLabel tempoMeldingVeld = new JLabel();
  JList klikLijst = new JList(new DefaultListModel());
  DefaultListModel model = (DefaultListModel)klikLijst.getModel();

  public DiasClient()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    DiasClient diasClient = new DiasClient();
    diasClient.show();
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(729, 572));
    this.setTitle("DiasClient");
    this.addWindowListener(new DiasClient_this_windowAdapter(this));

    serverLabel.setText("Server (server:poort):");
    serverLabel.setBounds(new Rectangle(33, 35, 125, 20));

    serverVeld.setText("localhost:8080");
    serverVeld.setBounds(new Rectangle(33, 69, 225, 20));

    verbindingKnop.setText("Maakverbinding");
    verbindingKnop.setBounds(new Rectangle(33, 109, 225, 20));
    verbindingKnop.addActionListener(new DiasClient_verbindingKnop_actionAdapter(this));
    verbindingKnop.setFocusPainted(false);

    serverMeldingVeld.setText("");
    serverMeldingVeld.setBounds(new Rectangle(164, 35, 469, 20));

    plaatjePanel.setBounds(new Rectangle(287, 109, 400, 400));
    plaatjePanel.setLayout(null);

    tempoLabel.setText("Tempo in seconden:");
    tempoLabel.setBounds(new Rectangle(33, 415, 130, 20));
    tempoVeld.setText("");
    tempoVeld.setBounds(new Rectangle(33, 445, 67, 20));
    tempoMeldingVeld.setText("");
    tempoMeldingVeld.setBounds(new Rectangle(33, 482, 180, 20));
    klikLijst.setModel(model);
    klikLijst.setBounds(new Rectangle(34, 140, 222, 271));
    klikLijst.addListSelectionListener(new DiasClient_klikLijst_listSelectionAdapter(this));
    this.getContentPane().add(serverLabel, null);
    this.getContentPane().add(serverVeld, null);
    this.getContentPane().add(verbindingKnop, null);
    this.getContentPane().add(serverMeldingVeld, null);
    this.getContentPane().add(tempoLabel, null);
    this.getContentPane().add(tempoVeld, null);
    this.getContentPane().add(tempoMeldingVeld, null);
    this.getContentPane().add(plaatjePanel, null);
    this.getContentPane().add(klikLijst, null);
  }

  /**
   * Wordt geactiveerd bij het klikken op de verbindingKnop.
   */
  void verbindingKnop_actionPerformed(ActionEvent e)
  {
    serverMeldingVeld.setText("");
    String adres = serverVeld.getText();
    boolean adresOk = dissect(adres);
    if (adresOk)
    {
      activeerVerbinding();
    }
    else
    {
      serverMeldingVeld.setText("Onjuiste server");
    }
  }

  /**
   * Ontleden van het ingegeven adres van de server.
   * @param adres de naam en het poortnummer van de server.
   * @return true als adres ontleedt kan worden in een host
   *         en een poortnummer en anders false
   */
  public boolean dissect(String adres)
  {
    boolean returnOk = false;
    StringTokenizer st = new StringTokenizer(adres, ":");
    int aantalTokens = st.countTokens();
    if (aantalTokens == 2)
    {
      host = st.nextToken();
      String poortString = st.nextToken();
      try
      {
        poort = Integer.parseInt(poortString);
        returnOk = true;
      }
      catch (NumberFormatException ex)
      {}
    }
    return returnOk;
  }

  /**
   * Activeren van de verbinding.
   */
  public void activeerVerbinding()
  {
    try
    {
      socket = new Socket(host, poort);
      serverMeldingVeld.setText("Verbonden ...");
      // Input
      InputStream in = socket.getInputStream();
      BufferedReader bin = new BufferedReader(new InputStreamReader(in));
      BufferedInputStream bisin = new BufferedInputStream(in);
      // Output
      // PrintWriter met autoflush
      PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()), true);
      // Bepalen commando
      if (selectie == null || selectie.equals("") || klikLijst.getSelectedIndex()==-1)
      {
        // Commando: lijst
        // Verwerken van de aanvraag van directorynamen.
        verwerkenAanvraagDirectorynamen(bin, out);
      }
      else
      {
        // Commando: lijst
        // Controleren tempo
        tempoMeldingVeld.setText("");
        boolean okTempo = controleerTempo();
        if (okTempo)
        {
          // Verwerken van de aanvraag van plaatjes.
          verwerkenAanvraagPlaatjes(bisin, selectie, out);
          klikLijst.removeSelectionInterval(0,model.size()-1);
        }
        else
          serverMeldingVeld.setText("");
      } // einde "else"
      bin.close();
      bisin.close();
      out.close();
    } // einde try
    catch (IOException ex)
    {
      serverMeldingVeld.setText("Verbindingsfout");
    }
    catch (IllegalArgumentException ex) // fout in
    {
      serverMeldingVeld.setText("Verbindingsfout");
    }
    finally
    {
      try
      {
        socket.close ();
      }
      catch (NullPointerException ignored) {}
      catch (IOException ignored) {}
    }
  }

  /**
   * Verwerken van de aanvraag van directorynamen.
   * @param bin het bestand waarvan de directorynamen worden gelezen.
   * @param out het bestand waarnaar het commando wordt weggeschreven.
   */
  public void verwerkenAanvraagDirectorynamen(BufferedReader bin, PrintWriter out)
  {
    try
    {
      // Versturen van het commando naar de server.
      out.println(Dias.COMMANDO_LIJST);
      // Wissen van de lijst op het scherm.
      if (model.getSize() > 0)
        model.removeAllElements();
      // Inlezen van de directorynamen.
      String regel = bin.readLine();
      while (regel != null)
      {
        model.addElement(regel);
        regel = bin.readLine();
      }
      // Het lezen ging goed.
      serverMeldingVeld.setText("");
    }
    catch (IOException ex)
    {
      serverMeldingVeld.setText("Verbindingsfout");
    }
  }

  /**
   * Controleren van het tempo.
   * @return true als het tempo correct is en anders false.
   */
  public boolean controleerTempo()
  {
    boolean ok = false;
    try
    {
      tempo = Integer.parseInt(tempoVeld.getText());
      if (tempo < 0)
        tempoMeldingVeld.setText("Negatief tempo");
      else
        if (tempo > Dias.MAXTEMPO)
          tempoMeldingVeld.setText("Maximum tempo is " + Dias.MAXTEMPO);
        else
          ok = true;
     }
     catch (NumberFormatException ex)
     {
       tempoMeldingVeld.setText("Incorrect tempo");
     }
    return ok;
  }

  /**
   * Verwerken van de aanvraag van plaatjes.
   * @param bisin het bestand waarvan de plaatjes worden gelezen.
   * @param din het bestand waarvan de grootte van het plaatje wordt gelezen.
   * @param selectie het commando dat wordt verzonden naar de server.
   * @param out het bestand waarnaar het commando wordt weggeschreven.
   */
  public void verwerkenAanvraagPlaatjes(BufferedInputStream bisin,
                                        String selectie, PrintWriter out)
  {

    // Versturen van een tekstregel met het commando.
    out.println(Dias.COMMANDO_DIR);
    // Versturen van een tekstregel met de gewenste directory.
    out.println(selectie);
    // Lezen van de grootte van een plaatje.
    int groottePlaatje = leesGroottePlaatje(bisin);
    while (groottePlaatje > 0)
    {
      System.out.println("Grootte van het plaatje = " + groottePlaatje);
      // Lezen van het plaatje zelf.
      byte[] buffer = new byte[groottePlaatje];
      buffer = lezenPlaatje(bisin, groottePlaatje);
      // Plaatje plaatsen in vector.
      vector.add(buffer);
      // Lezen van de grootte van een volgend plaatje.
      groottePlaatje = leesGroottePlaatje(bisin);
    }
    if (groottePlaatje == -1)
    {
      // Het verwerken ging goed
      serverMeldingVeld.setText("");
      // Starten van de timer
      if (timer != null)
        timer.stop();
      if (!vector.isEmpty())
      {
        timer = new Timer(0, new TimerLuisteraar(vector));
        timer.start();
        vector.removeAllElements();
      }
    }
    else
    {
      // Het verwerken ging niet goed
      serverMeldingVeld.setText("Verbindingsfout");
    }
    // Laatste plaatje verwerkt; afdrukken lege regel
    System.out.println("");
  }

  /**
   * Lezen van de grootte van een plaatje.
   * @param bisin het bestand waarvan de lengte wordt gelezen.
   * @return de grootte van een plaatje,
   *         -1 bij eof of -99 bij een storing in de verbinding.
   */
  public int leesGroottePlaatje(BufferedInputStream bisin)
  {
    int groottePlaatje = -1;
    try
    {
      int a = bisin.read();
      int b = bisin.read();
      int c = bisin.read();
      int d = bisin.read();
      groottePlaatje = ( ( (a & 0xff) << 24) | ( (b & 0xff) << 16) |
                         ( (c & 0xff) << 8)  | (d & 0xff));
    }
    catch (EOFException ex)
    {
      groottePlaatje = -1;
    }
    catch (IOException ex)
    {
      // Problemen met de verbinding
      groottePlaatje = -99;
    }
    return groottePlaatje;
  }

  /**
   * Lezen van het plaatje zelf.
   * @param bisin het bestand waarvan het plaatje wordt gelezen.
   * @param groottePlaatje de grootte van het plaatje.
   * @return een byte-array met het plaatje.
   */
  public byte[] lezenPlaatje(BufferedInputStream bisin, int groottePlaatje)
  {
    byte[] returnBuffer = new byte[groottePlaatje];
    byte[] leesBuffer = new byte[groottePlaatje];
    int aantalBytesGelezen = 0;
    int leesLengte = 0;
    int numberRead = 0;
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
    try
    {
      while (aantalBytesGelezen < groottePlaatje)
      {
        // Bepalen aantal te lezen (resterende) bytes.
        leesLengte = groottePlaatje - aantalBytesGelezen;
        if (leesLengte > Dias.DEFAULT_BUFFERGROOTTE)
          leesLengte = Dias.DEFAULT_BUFFERGROOTTE;
        // Daadwerkelijk lezen van 'leesLengte' bytes
        numberRead = bisin.read(leesBuffer, 0, leesLengte);
        byteArrayOut.write(leesBuffer, 0, numberRead);
        aantalBytesGelezen += numberRead;
      }
      byteArrayOut.flush();
      returnBuffer = byteArrayOut.toByteArray();
    }
    catch (IOException ex)
    {
      serverMeldingVeld.setText("Verbindingsfout");
    }
    return returnBuffer;
  }

  /**
   * Het sluiten van het frame.
   */
  void this_windowClosing(WindowEvent e)
  {
    System.exit(0);
  }

  /**
   * Binnenklasse van DiasClient welke middels een timer periodiek
   * geactiveerd wordt en zorg draagt voor het tonen van een (volgend) plaatje.
   */
  public class TimerLuisteraar implements ActionListener
  {
    private int plaatjesnummer;
    private Vector timerVector = new Vector();

    public TimerLuisteraar(Vector vector)
    {
      plaatjesnummer = 0;
      timerVector = (Vector)vector.clone();
    }

    public void actionPerformed(ActionEvent e)
    {
      if (plaatjesnummer == 0)
        timer.setDelay(tempo * 1000);
      byte[] plaatjesBuffer = (byte[])timerVector.elementAt(plaatjesnummer);
      JPlaatje jplaatje = new JPlaatje(plaatjesBuffer);
      // Vaststellen van de breedte voor een gelijke verdeling
      ImageIcon pict = new ImageIcon(plaatjesBuffer);
      int breedte = pict.getIconWidth();
      if (breedte > Dias.MAXWIDTH)
        jplaatje.setLocation(0, 0);
      else
        jplaatje.setLocation((Dias.MAXWIDTH-breedte)/2, 0);
      jplaatje.setSize(new Dimension(Dias.MAXWIDTH, Dias.MAXHEIGHT));
      if (plaatjePanel != null)
        plaatjePanel.removeAll();
      plaatjePanel.add(jplaatje);
      repaint();
      plaatjesnummer ++;
      if (plaatjesnummer >= timerVector.size())
        timer.stop();
    }
  }

  void klikLijst_valueChanged(ListSelectionEvent e)
  {
    int i = klikLijst.getSelectedIndex();
    if (i>-1)
      selectie = (String) model.get(i);
  }
}

class DiasClient_this_windowAdapter extends WindowAdapter
{
  DiasClient adaptee;

  DiasClient_this_windowAdapter(DiasClient adaptee)
  {
    this.adaptee = adaptee;
  }
  public void windowClosing(WindowEvent e)
  {
    adaptee.this_windowClosing(e);
  }
}

class DiasClient_verbindingKnop_actionAdapter implements ActionListener
{
  DiasClient adaptee;

  DiasClient_verbindingKnop_actionAdapter(DiasClient adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.verbindingKnop_actionPerformed(e);
  }
}

class DiasClient_klikLijst_listSelectionAdapter implements ListSelectionListener
{
  DiasClient adaptee;

  DiasClient_klikLijst_listSelectionAdapter(DiasClient adaptee)
  {
    this.adaptee = adaptee;
  }
  public void valueChanged(ListSelectionEvent e)
  {
    adaptee.klikLijst_valueChanged(e);
  }
}

