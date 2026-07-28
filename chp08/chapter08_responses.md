## Review Questions

1) a
```
01: import java.util.function.*;
02:
03: public class Panda {
04:   int age;
05:   public static void main(String[] args) {
06:     Panda p1 = new Panda();
07:     p1.age = 1;
08:     check(p1, p -> p.age < 5);
09:   }
10
11:   private static check(Panda panda, Predicate<Panda> pred) {
12:     String result =
13:       pred.test(panda) ? "match" : "not match";
14:     System.out.print(result)
15:   }
16: }
```


2) c
```
01: interface Climb {
02:   boolean isTooHigh(int height, int limit);
03: }
04:
05: public class Climber {
06:   public static void main(String[] args) {
07:     check((h, m) -> h.append(m).isEmpty(), 5);
08:   }
09:   private static void check(Climb climb, int height) {
10:     if (climb.isTooHigh(heigh, 10))
11:       System.out.println("too high");
12:     else
13;       System.out.println("ok");
14:   }
15: }
```


3) a, c, 



4) a



5) a, b, c, d, e


6) a, c, e, 


7) a


8) d


9) a, c, 


10) c, 


11) b


12) b


13) e


14) b, d, e, f,


15) a, 


16) c, 


17) a


18) a, b, c, d


19) b, c, f

