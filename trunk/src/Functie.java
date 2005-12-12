/**
 * Representeert een wiskundige functie met twee parameters
 */
public abstract class Functie
{
  protected double a, b; //de parameters

  /**
   * In deze constructor krijgen de twee parameters
   * een waarde.
   * @param a een parameter
   * @param b een parameter
   */
  public Functie(double a, double b)
  {
    this.a=a;
    this.b=b;
  }

  /**
   * De functiewaarde wordt berekend voor een gegeven
   * x-waarde.
   * @param x de x-waarde
   * @return de functiewaarde (of y-waarde)
   */
  public abstract double berekenY(double x);
}