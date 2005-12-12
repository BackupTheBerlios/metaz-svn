/**
 * Representeert een wiskundige Lijn functie met twee parameters
 */
public class LijnFunctie extends Functie
{
  /**
   * In deze constructor krijgen de twee parameters
   * een waarde.
   * @param a een parameter
   * @param b een parameter
   */
  public LijnFunctie(double a, double b){
    super(a,b);
  }


  /**
   * De functiewaarde wordt berekend voor een gegeven
   * x-waarde.
   * @param x de x-waarde
   * @return de functiewaarde (of y-waarde)
   */
  public double berekenY(double x)
  {
    //System.out.println("Waarde a=" + a + " Waarde b=" + b + " Waarde x=" + x + " Warde y=" + ((a*x) + b));
    return (a*x + b);

  }

  /**
   * Wordt gebruikt voor unit testing.
   * Niet relevant voor de werking van de klasse zelf.
   *
   * @param args eventuele argumenten bij de aanroep
   */
  public static void main(String[] args) {
    //a=0 en b=1
    LijnFunctie fn = new LijnFunctie(0,1);
    if (fn.berekenY(1000.67)!=1){
      System.out.println("Fout a=0 b=1 x=1000.67 Verwachte uitkomst 1, werkelijke uitkomst =" + fn.berekenY(1000.67));
    }
    else{
      System.out.println("Test succesvol!");
    }
  }

}