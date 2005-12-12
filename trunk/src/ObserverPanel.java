import java.util.*;
import java.awt.*;
import javax.swing.*;


/**
 * <p>Title: ObserverPanel</p>
 * <p>Description: Voegt Observer toe aan JPanel en tekent een functie</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: OU</p>
 * @author Lars Oosterloo
 * @version 1.0
 */
public class ObserverPanel extends JPanel implements Observer{

  /**
   * Verplichte implementatie van de Observer interface.
   * Initieert het tekenen van de actuele functie in de instantie van
   * FunctieApplicatie.
   *
   * @param obs het Observable object in de vorm van een FunctieApplicatie object
   * @param arg extra object doorgegeven door notifyObservers
   */
  public void update(Observable obs, Object arg){
    FunctieApplicatie fapp=(FunctieApplicatie) obs;
    DrawFunction(fapp.getFunction());
  }

  /**
   * Tekent de meegegeven functie op het ObserverPanel
   *
   * @param fn de functie die getekend moet worden
   */
  private void DrawFunction(Functie fn){
    Graphics g = this.getGraphics();
    g.clearRect(0, 0, 400, 400);
    //kleur de achtergrond wit
    g.setColor(Color.WHITE);
    g.fillRect(0,0,400,400);
    //kleur het assenstelsel zwart
    g.setColor(Color.BLACK);
    g.drawLine(200,0,200,400);
    g.drawLine(0,200,400,200);
    //draw function in blue
    g.setColor(Color.BLUE);
    for(int x=-199;x<201;x++){
      //het nulpunt is bij x=200, dus als we starten bij 0 is dit voor de waarde x=0-200
      //om een lijn te kunnen tekenen hebben we 2 punten nodig, dus we beginnen bij x=1
      //zodat we ook voor x=0 een punt kunnen berekenen
      //omdat we de grootste waarden van y bovenaan willen hebben en de coordinaat van y juist
      //groter wordt naar beneden moeten we de berekende waarde van y van de hoogte van het
      //paneel aftrekken, zodat we de inverse krijgen en de y waarde op de juiste hoogte komt.
      int y1 = (int)fn.berekenY(x-1);
      int y2 = (int)fn.berekenY(x);
      g.drawLine(x+199,200-y1,x+200,200-y2);
    }
  }
}