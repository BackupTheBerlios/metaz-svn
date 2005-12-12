
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Title: FunctieApplicatieFrame</p>
 * <p>Description: Laat in functie van de parameters a en b een lijn,
 * een parabool of een sinus zien</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: OU</p>
 * @author Lars Oosterloo
 * @version 1.0
 */
public class FunctieApplicatieFrame extends JFrame {
  JLabel lbl_B = new JLabel();
  JButton btn_Lijn = new JButton();
  JButton btn_Parabool = new JButton();
  JButton btn_Sinus = new JButton();
  JTextField txt_A = new JTextField();
  JTextField txt_B = new JTextField();
  JLabel lbl_A = new JLabel();

  //initialiseer een Observer instantie van JPanel
  private ObserverPanel pnl_Functie = new ObserverPanel();
  //voeg een referentie toe naar de Observable instantie
  private FunctieApplicatie functieapplicatie=new FunctieApplicatie();

  /**
   *Initialiseert het Applicatie frame
   */
  public FunctieApplicatieFrame() {
    try {
      jbInit();
      //extra niet JBuilder initialisatie
      mijnInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * wordt gegenereerd door JBuilder en initialiseert alle componenten etc.
   *
   * @throws Exception als er een uitzondering optreed wordt een Exception
   * opgegooid
   */
  private void jbInit() throws Exception {
    this.setBackground(SystemColor.menu);
    this.setSize(new Dimension(600, 431));
    //this.setIgnoreRepaint(false);
    this.setLayout(null);
    lbl_A.setFont(new java.awt.Font("Dialog", 1, 12));
    lbl_A.setOpaque(false);
    lbl_A.setPreferredSize(new Dimension(17, 16));
    lbl_A.setRequestFocusEnabled(true);
    lbl_A.setHorizontalTextPosition(SwingConstants.TRAILING);
    lbl_A.setText("a =");
    lbl_A.setBounds(new Rectangle(473, 176, 35, 24));
    lbl_B.setOpaque(false);
    lbl_B.setFont(new java.awt.Font("Dialog", 1, 12));
    lbl_B.setHorizontalTextPosition(SwingConstants.TRAILING);
    lbl_B.setText("b =");
    lbl_B.setBounds(new Rectangle(473, 207, 32, 23));
    btn_Lijn.setBounds(new Rectangle(473, 51, 101, 34));
    btn_Lijn.setText("Lijn");
    btn_Lijn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn_Lijn_actionPerformed(e);
      }
    });
    btn_Parabool.setBounds(new Rectangle(473, 93, 101, 34));
    btn_Parabool.setText("Parabool");
    btn_Parabool.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn_Parabool_actionPerformed(e);
      }
    });
    btn_Sinus.setBounds(new Rectangle(473, 134, 101, 34));
    btn_Sinus.setText("Sinus");
    btn_Sinus.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn_Sinus_actionPerformed(e);
      }
    });
    txt_A.setSelectionStart(2);
    txt_A.setText("10");
    txt_A.setBounds(new Rectangle(508, 176, 66, 24));
    txt_B.setSelectionStart(2);
    txt_B.setText("10");
    txt_B.setBounds(new Rectangle(508, 207, 66, 22));
    this.add(btn_Lijn, null);
    this.add(btn_Parabool, null);
    this.add(btn_Sinus, null);
    this.add(txt_A, null);
    this.add(txt_B, null);
    this.add(lbl_A, null);
    this.add(lbl_B, null);
    this.addWindowListener(new java.awt.event.WindowAdapter()
    {

     public void windowClosing(WindowEvent e)
     {
       this_windowClosing(e);
     }
   });
 }

 /**
  * Sluit de applicatie af
  *
  * @param e Venster gebeurtenis
  */
  void this_windowClosing(WindowEvent e)
  {
    System.exit(0);
  }

  /**
   *Observer gerelateerde toevoegingen betreffende ObserverPanel, waar de
   *functies op worden getoond.
   */
  private void mijnInit(){
    pnl_Functie.setBounds(new Rectangle(0, 0, 400, 400));
    pnl_Functie.setBackground(Color.WHITE);
    this.getContentPane().add(pnl_Functie,null);
    functieapplicatie.addObserver(pnl_Functie);
  }

  /**
   *Geeft de gewijzigde parameters en de gevraagde functie door aan de instantie
   * van FunctieApplicatie
   *
   * @param ftype Type van de functie die gevraagd wordt
   */
  private void wijzigFunctie(int ftype){
    try{
      double a = Double.parseDouble(this.txt_A.getText());
      double b = Double.parseDouble(this.txt_B.getText());
      functieapplicatie.setFunctionType(ftype, a, b);
    }
    catch (Exception ex){
      JOptionPane.showMessageDialog(null, "De functie kan niet worden getekend! \n" + "De volgende fout is geconstateerd:\n" + ex.getMessage(), "Fout", JOptionPane.ERROR_MESSAGE);
    }

  }

  /**
   * Roept wijzigFunctie aan als op de knop btn_Lijn wordt geklikt
   *
   * @param e parameter die de klik op de knop representeerd
   */
  void btn_Lijn_actionPerformed(ActionEvent e) {
    wijzigFunctie(FunctieApplicatie.LIJN_FUNCTION);
  }

  /**
   * Roept wijzigFunctie aan als op de knop btn_Parabool wordt geklikt
   *
   * @param e parameter die de klik op de knop representeerd
   */
  void btn_Parabool_actionPerformed(ActionEvent e) {
    wijzigFunctie(FunctieApplicatie.PARABOOL_FUNCTION);
  }

  /**
   * Roept wijzigFunctie aan als op de knop btn_Sinus wordt geklikt
   *
   * @param e parameter die de klik op de knop representeerd
   */

  void btn_Sinus_actionPerformed(ActionEvent e) {
    wijzigFunctie(FunctieApplicatie.SINUS_FUNCTION);
  }

}

