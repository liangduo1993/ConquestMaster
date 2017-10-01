package MapEditor.Domain;


public class Continent {
  String name;
  int bonus;
  
  public Continent(String name, int bonus)
  {
    this.name = name;
    this.bonus = bonus;
  }
  
  public int getBonus()
  {
    return this.bonus;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setBonus(int bonus)
  {
    this.bonus = bonus;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String toString()
  {
    return this.name;
  }
}
