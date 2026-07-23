## Chapter 7 responses

1) d, e
B, D.  Iguana does not compile, as it declares a static field with the same name as an 
instance field. Records are implicitly final and cannot be marked abstract, which is why 
`Gecko` compiles and `Chameleon` does not, making option B correct. Notice in `Gecko` 
that records are not required to declare any fields. `BeardedDragon` also compiles, as 
records may override any accessor methods, making option D correct. `Newt` does not 
compile because records are immutable, so any mutator methods that modify fields are not 
permitted. Overriding the `equals()` method is allowed, though.

2) a, d
A, B, D, E.  The code compiles without issue, so option G is incorrect. The blank can be 
filled with any class or interface that is a supertype of `TurtleFrog`. Option A is the 
direct superclass of `TurtleFrog`, and option B is the same class, so both are correct. 
`BrazilianHornedFrog` is not a superclass of `TurtleFrog`, so option C is incorrect. 
`TurtleFrog` inherits the `CanHop` interface, so option D is correct. Option E is also 
correct, as var is permitted when the type is known. Finally, `Long` is an unrelated 
class that is not a superclass of `TurtleFrog` and is therefore incorrect.

3) c
C.  When an enum contains only a list of values, the semicolon (;) after the list is 
optional. When an enum contains any other members, such as a constructor or variable, the 
semicolon is required. Since the enum list does not end with a semicolon, the code does 
not compile, making option C the correct answer. If the missing semicolon were added, the 
program would print 0 1 2 at runtime.

4) b,
C.  A class extending a sealed class must be marked final, sealed, or non-sealed. Since 
`Armadillo` is missing a modifier, the code does not compile, and option C is correct.

5) f,
```
01: interface HasExoskeleton {
02:   double size = 2.0f;
03:   abstract int getNumberOfSections();
04: }
---
05: abstract class Insect implements HasExoskeleton {
06:   abstract int getNumberOfLegs();
07: }
---
08: public class Beetle extends Insect {
09:   int getNumberOfLegs() { return 6; }
10:   int getNumberOfSections(int count) { return 1; }
11: } 
```
E.  First, the declarations of `HasExoskeleton` and `Insect` are correct and do not 
contain any errors, making options C and D incorrect. The concrete class `Beetle` extends 
`Insect` and inherits two abstract methods, `getNumberOfSections()` and `getNumberOfLegs()`. 
The `Beetle` class includes an overloaded version of `getNumberOfSections()` that takes 
an `int` value. The method declaration is valid, making option F incorrect, although it 
does not satisfy the abstract method requirement inherited from `HasExoskeleton`. For 
this reason, only one of the two abstract methods is properly overridden. The `Beetle` 
class therefore does not compile, and option E is correct.


6) e
```
01: public abstract interface Herbivore {
02:   int amount = 10; 
03:   public void eatGrass();
04:   public abstract int chew() { return 13; }
05: }
---
07: abstract class IsAPlant extends Herbivore {
08:   Object eatGras(int season) { return null; }
09: }
```
D, E.  Line 4 does not compile, since an abstract method cannot include a body. Line 7 
also does not compile because the wrong keyword is used. A class implements an interface; 
it does not extend it. For these reasons, options D and E are correct.

7) b
E.  The inherited interface method `getNumOfGills(int)` is implicitly public; therefore, 
it must be declared public in any concrete class that implements the interface. Since the 
method uses the package (default) modifier in the `ClownFish` class, line 6 does not 
compile, making option E the correct answer. If the method declaration were corrected to 
include public on line 6, then the program would compile and print 15 at runtime, and 
option B would be the correct answer.

8) 1
A, B, C.  Instance variables must include the private access modifier, making option D 
incorrect. While it is common for methods to be public, this is not required. Options A, 
B, and C fulfill this requirement.

9) g
A, E, F.  The `setSnake()` method requires an instance of `Snake`. `Cobra` is a direct 
subclass, while `GardenSnake` is an indirect subclass. For these reasons, options A and 
E are correct. Option B is incorrect because `Snake` is abstract and requires a concrete 
subclass for instantiation. Option C is incorrect because `Object` is a supertype of 
`Snake`, not a subtype. Option D is incorrect as String is an unrelated class and does 
not inherit Snake. Finally, a null value can always be passed as an object value, 
regardless of type, so option F is also correct.

10) b,  c, d, e
A, B, C, E.  `Walk` declares a private method that is not inherited in any of its 
subtypes. For this reason, any valid class is supported on line X, making options A, B, 
and C correct. Line Z is more restrictive, with only `ArrayList` or subtypes of 
`ArrayList` supported, making option E correct.

11) f
```
01: public class Movie {
02:   private int butter = 5;
03:   private Movie() {}
04:   protected class Popcorn() {
05:     private Popcorn() {}
06:     public static int butter = 10;
07:     public void startMovie() {
08:       System.out.println(butter);
09:     }
10:   }
11:   public static void main(String[] args) {
12:     var movie = new Movie();
13:     Movie.Popcorn in = new Movie().new PopCorn();
14:     in.startMovie();
15:   }
16: }
```
B.  Starting with Java 16, inner classes can contain static variables, so the code 
compiles. Remember that private constructors can be used by any methods within the outer 
class. The `butter` reference on line 8 refers to the inner class variable defined on 
line 6, with the output being `10` at runtime, and making option B correct.

12) a, e
A, B, E.  Encapsulation allows using methods to get and set instance variables so other 
classes are not directly using them, making options A and B correct. Instance variables 
must be private for this to work, making option E correct and option D incorrect. While 
there are common naming conventions, they are not required, making option C incorrect.

13) d
F.  When using an `enum` in a `switch` expression, the `case` statement must be made up 
of the enum values only. If the enum name is used in the case statement value, then the 
code does not compile. In this question, `SPRING` is acceptable, but `Seasons.SPRING` is 
not. For this reason, the three case statements do not compile, making option F the 
correct answer. If these three lines were corrected, then the code would compile and 
produce a `NullPointerException` at runtime.

14) a, c, d, e, 
A, C, E.  A sealed interface restricts which interfaces may extend it, or which classes 
may implement it, making options A and E correct. Option B is incorrect. For example, a 
`non-sealed` subclass allows classes not listed in the permits clause to indirectly 
extend the sealed class. Option C is correct. While a sealed class is commonly extended 
by a subclass marked final, it can also be extended by a `sealed` or `non-sealed` 
subclass marked abstract. Option D is incorrect, as the modifier is `non-sealed`, not 
`nonsealed`. Finally, option F is incorrect, as sealed classes can contain nested subclasses.

15) d, f
G.  Trick question—the code does not compile! For this reason, option G is correct. The 
`Spirit` class is marked `final`, so it cannot be extended. The `main()` method uses an 
anonymous class that inherits from `Spirit`, which is not allowed. If `Spirit` were not 
marked final, then options C and F would be correct. Option A would print "Booo!!!"", 
while options B, D, and E would not compile for various reasons.

16) c,
```
01: public class Ostrich {
02:   private int count;
03:   static class OstrichWrangler {
04:     public int stampede() {
05:       return count;
06:     }
07:   }
08: }
```
E.  The `OstrichWrangler` class is a static nested class; therefore, it cannot access the 
instance member count. For this reason, line 5 does not compile, and option E is correct.  

17) d, e, f, g, h
```
01: public interface Omnivore {
02:   int amount = 10;
03:   static boolean gather = true;
04:   static void eatGrass() {}
05:   int findMode() { return 2; }
06:   default float rest() { return 1; }
07:   protected in chew() { return 13; }
08:   private static void eadLeaver() {}
09: }
```
E, G.  Lines 2 and 3 compile with interface variables implicitly public, static, and 
final. Line 4 also compiles, as static methods are implicitly public. Line 5 does not 
compile, making option E correct. Non-static interface methods with a body must be 
explicitly marked private or default. Line 6 compiles, with the public modifier being 
added by the compiler. Line 7 does not compile, as interfaces do not have protected 
members, making option G correct. Finally, line 8 compiles without issue.

18) b
```
public class Deer {
  enum Food {APPLES, BERRIES, GRASS}
  protected class Diet {
    private Food getFavorite() {
      return Food.BERRIES;
    }
  }
  public static void main(String[] seasons) {
    System.out.print(switch(new Diet().getFavorite()){
      case APPLES -. "a";
      case BERRIES -> "b";
      default -> "c";
    });
  }
}
```
E.  `Diet` is an inner class, which requires an instance of `Deer` to instantiate. Since 
the `main()` method is static, there is no such instance. Therefore, the `main()` method 
does not compile, and option E is correct. If a reference to `Deer` were used, such as 
calling `new Deer().new Diet()`, then the code would compile and print b at runtime.


19) g
G.  The `isHealthy()` method is marked abstract in the enum; therefore, it must be 
implemented in each enum value declaration. Since only `INSECTS` implements it, the 
code does not compile, making option G correct.

20) a, b, e, f
A, D, F.  Polymorphism is the property of an object to take on many forms. Part of 
polymorphism is that methods are replaced through overriding wherever they are called, 
regardless of whether they're in a parent or child class. For this reason, option A is 
correct, and option E is incorrect. With hidden static methods, Java relies on the 
location and reference type to determine which method is called, making option B 
incorrect and option F correct. Finally, making a method final, not static, prevents 
it from being overridden, making option D correct and option C incorrect.

21) a, b, c
```
public record RabbitFood(int size, String brand, LocalDate expires) {
  public static int MAX_STORAGE = 100;
  public RabbitFood() {
    ____________________________;
  }
}
```
F.  The `record` defines an overloaded constructor using parentheses, not a compact one. 
For this reason, the first line must be a call to another constructor, such as 
`this(500, "Acme", LocalDate.now())`. For this reason, the code does not compile and 
option F is correct. If the parentheses were removed from the constructor to declare a 
compact constructor, then options A, C, and E would be correct.


22) a, c, 
```
public class Lion {
  class Cub {}
  static class Den {}
  static void rest() {
    _______________________;
  }
}
```
C, D, G.  Option C correctly creates an instance of an inner class `Cub` using an 
instance of the outer class `Lion`. Options A, B, E, and H use incorrect syntax for 
creating an instance of the `Cub` class. Options D and G correctly create an instance 
of the static nested `Den` class, which does not require an instance of `Lion`, while 
option F uses invalid syntax.

23) b
D.  First, if a class or interface inherits two interfaces containing default methods 
with the same signature, it must override the method with its own implementation. The 
`Penguin` class does this correctly, so option E is incorrect. The way to access an 
inherited default method is by using the syntax `Swim.super.perform()`, making option 
D correct. We agree that the syntax is bizarre, but you need to learn it. Options A, 
B, and C are incorrect and result in compiler errors.

24) b, c, d, e, f
B, E.  Line 3 does not compile because the static method `hunt()` cannot access an 
abstract instance method `getName()`, making option B correct. Line 6 does not compile 
because the private static method `sneak()` cannot access the private instance method 
`roar()`, making option E correct. The rest of the lines compile without issue.

25) e,
B.  `Zebra.this.x` is the correct way to refer to `x` in the `Zebra` class. Line 5 
defines an abstract local class within a method, while line 11 defines a concrete 
anonymous class that extends the `Stripes` class. The code compiles without issue and 
prints x is 24 at runtime, making option B the correct answer.

26) b
C, F.  Enums are required to have a semicolon `(;)` after the list of values if there 
is anything else in the enum. Don't worry; you won't be expected to track down missing 
semicolons on the whole exam—only on enum questions. For this reason, line 5 should 
have a semicolon after it since it is the end of the list of enums, making option F 
correct. Enum constructors are implicitly private, making option C correct as well. 
The rest of the enum compiles without issue.

27) c, d, g,
B, C, D, G.  
The compiler inserts an accessor for each field, a constructor containing all of 
the fields in the order they are declared, and useful implementations of `equals()`, 
`hashCode()`, and `toString()`, making options B, C, D, and G correct. Option A is 
incorrect, as the compiler would only insert a no-argument constructor if the record 
had no fields. Option E is incorrect, as records are immutable. Option F is also 
incorrect and not a property of records.

28) e 
```
public abstract class Camel { void travel(); }
---
public interface EatsGrass { private abstract int chew(); }
---
public abstract class Elephant {
  abstract private class SleepsAlot {
    abstract int sleep();
  }
}
---
public class Eagle { abstract soar(); }
---
public interface Spider { default void crawl() {} }
```
A, B, D.  `Camel` does not compile because the `travel()` method does not declare a body, 
nor is it marked abstract, making option A correct. `EatsGrass` also does not compile 
because an interface method cannot be marked both `private` and `abstract`, making option 
B correct. Finally, `Eagle` does not compile because it declares an abstract method 
`soar()` in a concrete class, making option D correct. The other classes compile without 
issue.


29) f,
```
01: class Primate {
02:   protected int age = 2;
03:   { age = 1; }
04:   public Primate {
05:     this().age = 3;
06:   }
07: }
---
08: this class Orangutan {
09:   protected int age = 4;
10:   { age = 5; }
11:   public Orangutan() {
12:     this().age = 6;
13:   }
14:   public static void main(String[] args) {
15:     final Primate x = (Primate)new Orangutan();
16:     System.out.println(x.age);
17:   }
18: }
```
F.  The code does not compile, so options A through C are incorrect. Both lines 5 and 12 
do not compile, as `this()` is used instead of `this`. Remember, `this()` refers to 
calling a constructor, whereas this is a reference to the current instance. Next, the 
compiler does not allow casting to an unrelated class type. Since `Orangutan` is not a 
subclass of `Primate`, the cast on line 15 is invalid, and the code does not compile. 
Due to these three lines containing compilation errors, option F is the correct answer.

30) a, b, c, d, e,  
```
sealed class Bird {
  public final class Flamingo extends Bird {}
}
---
sealed class Monkey {}
---
class EmperorTamarin extends Monkey {}
---
non-sealed class Mandrill extends Monkey {}
---
sealed class Friendly extends Mandril permits Silly {}
---
final class Silly {}
```
C, E.  `Bird` and its nested `Flamingo` subclass compile without issue. The permits 
clause is optional if the subclass is nested or declared in the same file. For this 
reason, `Monkey` and its subclass `Mandrill` also compile without issue. `EmperorTamarin` 
does not compile, as it is missing a `non-sealed`, `sealed`, or `final` modifier, making 
option C correct. `Friendly` also does not compile, since it lists a subclass `Silly` 
that does not extend it, making option E correct. While the `permits` clause is optional, 
the `extends` clause is not. `Silly` compiles just fine. Even though it does not extend 
`Friendly`, the compiler error is in the sealed class.

