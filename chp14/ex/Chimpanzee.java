package chp14;

import java.io.Serializable;

public class Chimpanzee implements Serializable {
  public static final long serialVersionUID = 2L;
  private transient String name;
  private transient int age = 10;
  private static char type = 'C';
  
  { this.age = 14; }
  
  public Chimpanzee() {
    this.name = "Unknown";
    this.age = 12;
    this.type = 'Q';
  }
  
  public Chimpanzee(String name, int age, char type) {
    this.name = name;
    this.age = age;
    this.type = type;
  }
  
  public String toString() {
    return "[name="+name + ", age="+age + ", type="+type;
  }
}
