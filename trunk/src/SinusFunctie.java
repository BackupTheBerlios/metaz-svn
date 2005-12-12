
/**
 * Representeert een wiskundige Sinusoide functie met twee parameters
 */
public class SinusFunctie extends Functie
{
  /**
   * In deze constructor krijgen de twee parameters
   * een waarde.
   * @param a een parameter
   * @param b een parameter
   * @throws IllegalArgumentException wordt opgegooid als b=0
   */
  public SinusFunctie(double a, double b) throws IllegalArgumentException{
    super(a, b);
    if (b==0){
      //delen door 0 mag niet
      throw new IllegalArgumentException("b mag voor de Sinus functie niet de waarde 0 hebben!");
    }
  }


  /**
   * De functiewaarde wordt berekend voor een gegeven
   * x-waarde.
   * @param x de x-waarde
   * @return de functiewaarde (of y-waarde)
   */
  public double berekenY(double x)
  {
    return 10*a*Math.sin(x/b) ;
  }

  /**
   * Wordt gebruikt voor unit testing.
   * Niet relevant voor de werking van de klasse zelf.
   *
   * @param args eventuele argumenten bij de aanroep
   */
  public static void main(String[] args) {
    //a=0 en b=1
    SinusFunctie fn = new SinusFunctie(0,1);
    if (fn.berekenY(1000.67)!=1){
      System.out.println("Fout a=0 b=1 x=1000.67 Verwachte uitkomst 1, werkelijke uitkomst =" + fn.berekenY(1000.67));
    }
    else{
      System.out.println("Test succesvol!");
    }
  }

}