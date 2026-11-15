package chp14;

import java.io.Serializable;

public class Gorilla implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private int age;
  private Boolean friendly;
  private transient String favoriteFood;

  public Gorilla(String name, int age, Boolean friendly) {
    this.name = name;
    this.age = age;
    this.friendly = friendly;
  }

  public String toString() {
    return "name="+name+", age="+age+", friendly="+friendly;
  }
}
