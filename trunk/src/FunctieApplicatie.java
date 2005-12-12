import java.util.*;
import javax.swing.*;

/**
 * <p>Title: Functie Applicatie</p>
 * <p>Description: Start de FunctieApplicatie applicatie en geeft de mogelijkheid
 * aan de hand van een tweetal parameters en een drietal functies de betreffende
 * functie op een Paneel te tekenen</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: OU</p>
 * @author Lars Oosterloo
 * @version 1.0
 */
public class FunctieApplicatie extends Observable{
  /**
   * constante om aan te geven dat er alleen maar initialisatie behoeft te gebeuren
   */
  public static final int INIT_FUNCTION=-1;
  /**
   * Constante om aan te geven dat het om de lijn functie gaat
   */
  public static final int LIJN_FUNCTION=0;
  /**
   * Constante om aan te geven dat het om de parabool functie gaat
   */
  public static final int PARABOOL_FUNCTION=1;
  /**
   * Constante om aan te geven dat het om de sinus functie gaat
   */
  public static final int SINUS_FUNCTION=2;

  private double a;
  private double b;

  private Functie fn;

  /**
   * Constructor, initialiseert het functie teken paneel
   */
  public FunctieApplicatie(){
    setFunctionType(INIT_FUNCTION,0,0);
  }

  /**
   * Creeert een instantie van het applicatie frame en laat het zien
   * @param args extra argumenten bij de aanroep
   */
  public static void main(String[] args) {
    FunctieApplicatieFrame functieclientFrame = new FunctieApplicatieFrame();
    //maak window aan
    functieclientFrame.show();
  }

  /**
   * Instantieert een functie van het aangegeven type met de aangegeven
   * parameters en notificeerd de Observers van de wijziging
   *
   * @param functietype type functie dat gebruikt moet worden
   * @param a parameter die moet worden doorgegeven aan de functie
   * @param b parameter dat moet worden doorgegeven aan de functie
   * @throws IllegalArgumentException indien een ongeldige type functie wordt
   * doorgegeven
   */
  public void setFunctionType(int functietype, double a, double b) throws IllegalArgumentException{
    //initialiseer
    if (functietype > 2 | functietype<-1){
      throw new IllegalArgumentException("Functietype moet liggen tussen -1 en 2!");
    }
    else {
       this.a = a;
       this.b = b;
       switch (functietype){
         case INIT_FUNCTION: {
           fn = (Functie) new LijnFunctie(0,0);
           break;
         }
         case LIJN_FUNCTION: {
           fn = (Functie) new LijnFunctie(a,b);
           break;
         }
         case PARABOOL_FUNCTION: {
           fn = (Functie) new ParaboolFunctie(a,b);
           break;
         }
         case SINUS_FUNCTION: {
           fn = (Functie) new SinusFunctie(a,b);
         }
       }
       setChanged();
       notifyObservers();
    }
  }

  /**
   * Geeft de geinitialiseerde functie terug
   * @return geinitialiseerde functie
   */
  public Functie getFunction(){
    return fn;
  }
}

