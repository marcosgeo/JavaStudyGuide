package chp14.ex;


import java.util.ArrayList;
import java.util.List;


public class Question22 {

  public static void main(String[] args){
    var zebra = new Zebra();

    zebra = Zebra.writeAndRead(zebra);
  }


  public class Zebra implements Serializable {
    private transient String name = "George";
    private static String birthPlace = "Africa";
    private transient int age;
    List<Zebra> friends = new ArrayList<>();
    private Object stripes = new Object();
    {age = 10;}

    public Zebra() {
      this.name = "Sophia";
    }

    static Zebra writeAndRead(Zebra z) {
      // omitted
      return z;
    }
  }
}
