## Review Questions

1) e, f
E
Options A and B will not compile because constructors cannot be called without `new`.
Options C and D will compile but will create a new object rather than setting the fields 
in this one. The result is the program will print 0, not 2, at runtime. 
Calling an overloaded constructor, using `this()`, or a parent constructor, using 
`super()` is only allowed on the first line of the constructor, making option E correct 
and option F incorrect.
Option G is incorrect because the program prints 0 without any changes, not 2.


2) a, b, c, d, f
A, B, F
the `final` modifier can be used with `private` and `static`, making options A and F 
correct. Marking a private method `final` is redundant but allowed. 
A private method may also be marked `static`, making option B correct.
Options C, D and E are incorrect because methods marked `static`, `private`, or `final` 
cannot be overridden; therefore, they cannot be marked `abtract`.


3) b, d, 
B, C
Overloaded methods have the same method name but a different signature, making option A 
incorrect.
Overridden instance methods and hidden static methods must have the same signature, 
making options B and C correct.
Overloaded methods can have different return types, while overridden and hidden methods 
can have covariant return types. None of these methods are required to use the same 
return type, making options D, E, and F incorrect.


4) d (e)
F
The code will not compile as is, because the parent class `Mammal` does not define a 
no-argument constructor. For this reason, the first line of a `Platypus` constructor 
should be an explicit call to `super(int)`, making option F the correct answer.
Option E is incorrect, as line 7 compiles without issues. The `sneeze()` method in the 
`Mammal` class is marked private, meaning it is not inherited and therefore is not 
overridden in the `Platypus` class. For this reason, the `sneeze()` method in the 
`Platypus` class  is free to define the same methods with any return type.

5) c
E
The code compiles, making option F incorrect. An instance variable with the same name as 
an inherited variable is hidden, not overridden. This means that both variables exist, 
and the one that is used depends on the location and reference type.
Because the `main()` method uses a reference type of `Speedster` to access the `numSpots` 
variable, the variable in the `Speedster` class, not the `Cheetah` class, must be set to 
50.
Option A is incorrect, as it reassigns the method parameter to itself. Option B is 
incorrect, as it assigns the method parameter the value of the instance variable in 
`Cheetah`, which is 0.
Option C is incorrect, as it assigns the value to the instance variable int `Cheetah`, not 
`Speedster`.
Option D is incorrect, as it assigns the method parameter the value of the instance variable 
in `Speedster`, which is 0.
Options A, B, C and D all print "0" at runtime. Option E is the only correct answer, as it 
assigns the instance variable `numSpots` in the `Speedster` class a value of 50. The 
`numSpots` variable in the `Speedster` class is then correctly referenced in the `main()` 
method, printing 50 at runtime.

6) e, d
D, E
The `Moose` class doesn't compile, as the `final` variable `antlers` is not initialized 
when it is declared, in an instance initializer, or in a constructor. `Caribou` and 
`Reindeer` are not immutable because they are not marked `final`, which means a subclass 
could extend them and add mutable fields. `Elk`and `Deer` are both immutable classes 
since they are marked `final` and only include private final members, making options D 
and E correct. As shown with `Elk`, a class doesn't need to declare any fields to be 
considered immutable.

7) e,
A
The code compiles and runs without issues, so options E and F area incorrect. The 
`Arthropedo`class defines two overloaded versions of the `printName()` method. The
`printName()` method that takes an `int` value on line 5 is correctly overridden 
in the `Spider` class on line 9. To remember, an overridden method can have a broader 
access modifier, and *protected access is broader than package access*. Because of 
polymorphism, the overridden method replaces the method on all call, even if an 
`Arthropod` reference variable is used, as is done int the `main()` method. For these 
reasons, the overridden method is called on lines 15 and 15, printing "Spider" twice.
Note that the `short` value is automatically cast to the larger type of `int`, which 
then uses the overridden method. Line 16 calls the overloaded method in the 
`Arthropod` class, as the `long` value 5L does not match the overridden method, 
resulting in `Arthropod` being printed. Therefore, option A is the correct answer.

8) e
D
The code compiles without issue. The question is making sure we known that superclass 
constructors area called in the same manner int abstract classes as they are in 
non-abstract classes. Line 9 calls the constructor on line 6. the compiler auto// 
inserts `super()` as the first line of the construct defined on line 6. The program 
then calls the constructor on line 3 and prints "Wow-". Control then returns to line 6, 
and "Oh-" is printed. Finally, the method call on line 10 uses the version of `fly()` 
in the `Pelican` class, since it it marked private and the reference type of `var` is 
resolved as `Pelican`. The final output is "Wow-Oh-Pelican", making option D the correct 
answer. Remembering that private methods cannot be overridden. If the reference type of 
`chirp` was `Bird`, than the code would not compile as it would not be accessible 
outside the class.

9) a, d, e
B, E
The signature must match exactly, making option A incorrect. There is no such thing as 
a covariant signature. An overridden method must not declare any new checked exceptions 
or a checked exception that is broader than the inherited method. For this reason, 
option B is correct, and option D is incorrect. Option C is incorrect because an 
overridden method may have the same access modifier as the version in the parent class. 
Finally, overridden methods must have covariant return types, and only `void` is 
covariant with `void`, making option E correct.

10) a
A, C
Option A is correct, as `this(3)` calls the constructor declared on line 5, while 
`this("")` calls the constructor declared on line 10. Option B does not compile, as 
inserting `this()` at line 3 results in a compiles error, since there is no matching 
constructor. Option C is correct, as `short` can be implicitly cast to `int`, resulting 
in `this((short)1)` calling the constructor declared on line 5. In addition, 
`this(null)` calls the `String` constructor declared on line 10. Option D does not 
compile because inserting `super()` on line 14 result in an invalid constructor call.


11) 



12) b,



13) a, 


14) b, e, 



15) b,



16) 
uq uq crm  uqcrm



17) c, 



18) b, d, e, 

```
public class Mammal {
  private void eat() {}
  protected static void drink() {}
  public Integert dance(String p) {return null;}
}
---
class Primate extends Mammal {
  public void eat(String p) {}
}
---
class Monkey extends Primate {
  public static void drink() throws RuntimeException {}
  public Number dance(CharSequence p) { return null; }
  public int eat(String p) {}
}
```


19) b,

20) c

```
class Bird {
  int feathers = 0;
  Bird(int x) { this.feathers = x; }
  Bird fly() {
    return new Bird(1);  // err
  }
}
---
class Parrot extends Bird {
  protected Parrot(int y) { super(y); }
  protected Parrot fly() {
    return new Parrot(2);  // err
  }
}
---
public class Macaw extends Parrot {
  public Macaw(int z) { super(z); }
  public Macaw fly() {
    return new Macaw(3);  // err
  }
  public static void main(String... sing) {
    Bird p = new Macaw(4);
    System.out.print(((Parrot)p.fly()).feathers);
  }
}
```

21) b, g


22) e,

23) f,


24) b
182943
```
01: class Antelope {
02:   public Antelope(int p) {
03:     System.out.println("4");
04:   }
05:   { System.out.print("2"); }
06:   static { System.out.print("1"); }
07: }
---
08: public class Gazelle extends Antelope {
09:   public Gazelle(int p) {
10:     super(6);
11:     System.out.print("3");
12:   }
13:   public static void main(String hopping[]) {
14:     new Gazelle(0);
15:   }
16:   static { System.out.print("8"); }
17:   { Sytem.out.print("9"); }
18: }
```

25) b, c, e


26) d, e,


