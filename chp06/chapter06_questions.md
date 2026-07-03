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
C.  Option A is correct, as `this(3)` calls the constructor declared on line 5, while 
`this("")` calls the constructor declared on line 10. Option B does not compile, as 
inserting `this()` at line 3 results in a compiler error, since there is no matching 
constructor. Option C is correct, as `short` can be implicitly cast to `int`, resulting 
in `this((short)1)` calling the constructor declared on line 5. In addition, 
`this(null)` calls the `String` constructor declared on line 10. Option D does not 
compile because inserting `super()` on line 14 results in an invalid constructor call. 
The `Howler` class does not contain a no-argument constructor. Option E is also 
incorrect. Inserting `this(2L)` at line 3 results in a recursive constructor definition. 
The compiler detects this and reports an error. Option F is incorrect, as using 
`super(null)` on line 14 does not match any parent constructors. If an explicit cast 
was used, such as `super((Integer)null)`, then the code would have compiled but would 
throw an exception at runtime during unboxing. Finally, option G is incorrect because 
the superclass `Howler` does not contain a no-argument constructor. Therefore, the 
constructor declared on line 13 will not compile without an explicit call to an 
overloaded or parent constructor.

11) 
C
The code compiles and runs without issue making, options F and G incorrect. Line 16 
initializes a `PolarBear` and assigns it to the `bear` reference instance. The 
variable declaration and instance initializers are run first, setting value to `tac`. 
The constructor declared on line 5 is called, resulting in value being set to `tacb`. 
Remember, a `static main()` method can access private constructors declared in the same 
class. Line 17 creates another `PolarBear` instance, replacing the `bear` reference 
declared on line 16. First, `value` is initialized to `tac` as before. Line 17 calls 
the constructor declared on line 8, since `String` is the narrowest match of a `String` 
literal. This constructor then calls the overloaded constructor declared on line 5, 
resulting in `value` being updated to `tacb`. Control returns to the previous 
constructor, with line 10 updating `value` to `tacbf`, and making option C the correct 
answer. Note that if the constructor declared on line 8 did not exist, then the 
constructor on line 12 would match. Finally, the `bear` reference is properly cast to 
`PolarBear` on line 18, making the `value` parameter accessible.



12) b,
C
The code doesn't compile, so option A is incorrect. The first compilation error is on 
line 8. Since `Rodent` declares at least one constructor and it is not a no-argument 
constructor, `Beaver` must declare a constructor with an explicit call to a `super()` 
constructor. Line 9 contains two compilation errors. First, the return types are not 
covariant since `Number` is a supertype, not a subtype, of `Integer`. Second, the 
inherited method is static, but the overridden method is not, making this an invalid 
override. The code contains three compilation errors, although they are limited to two 
lines, making option C the correct answer.


13) a, 
A, G
The compiler will insert a `default` no-argument constructor if the class compiles and 
does not define any constructors. Options A and G fulfill this requirement, making them 
the correct answers. The `bird()` declaration in option G is a method declaration, not 
a constructor. Options B and C do not compile. Since the constructor name does not 
match the class name, the compiler treats these as methods with missing return types. 
Options D, E, and F all compile, but since they declare at least one constructor, the 
compiler does not supply one.

14) b, e, 
B, E, F 
A class can only directly extend a single class, making option A incorrect. A class can 
implement any number of interfaces, though, making option B correct. Option C is 
incorrect because primitive variables types do not inherit java.lang.Object. If a class 
extends another class, then it is a subclass, not a superclass, making option D 
incorrect. A class that implements an interface is a subtype of that interface, making 
option E correct. Finally, option F is correct as it is an accurate description of 
multiple inheritance, which is not permitted in Java.

15) b,
C
The code does not compile because the `isBlind()` method in `Nocturnal` is not marked 
`abstract` and does not contain a method body. The rest of the lines compile without 
issue, making option C the only correct answer. If the abstract modifier was added to 
line 2, then the code would compile and print `false` at runtime, making option B the 
correct answer.

16) 
uq uq crm  uqcrm
D
The code compiles, so option G is incorrect. Based on order of initialization, the 
`static` components are initialized first, starting with the `Arachnid` class, since 
it is the parent of the `Scorpion` class, which initializes the `StringBuilder` to `u`. 
The static initializer in `Scorpion` then updates `sb` to contain `uq`, which is printed 
twice by lines 13 and 14 along with spaces separating the values. Next, an instance of 
`Arachnid` is initialized on line 15. There are two instance initializers in `Arachnid`, 
and they run in order, appending `cr` to the `StringBuilder`, resulting in a value of 
`uqcr`. An instance of `Scorpion` is then initialized on line 16. The instance 
initializers in the superclass `Arachnid` run first, appending `cr` again and updating 
the value of `sb` to `uqcrcr`. Finally, the instance initializer in `Scorpion` runs and 
appends `m`. The program completes with the final value printed being `uq uq uqcrcrm`, 
making option D the correct answer.


17) c, 
C, F
Calling an overloaded constructor with `this()` may be used only as the first line of a 
constructor, making options A and B incorrect. Accessing `this.variableName` can be 
performed from any instance method, constructor, or instance initializer, but not from 
a *static method* or *static initializer*. For this reason, option C is correct, and 
option D is incorrect. Option E is tricky. The default constructor is written by the 
compiler only if no user-defined constructors were provided. And `this()` can only be 
called from a constructor in the same class. Since there can be no user-defined 
constructors in the class if a *default constructor* was created, it is impossible for 
option E to be true. Since the `main()` method is in the same class, it can call 
private methods in the class, making option F correct.


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
D, F
The `eat()` method is private in the `Mammal` class. Since it is not inherited in the 
`Primate` class, it is neither overridden nor overloaded, making options A and B 
incorrect. The `drink()` method in `Mammal` is correctly hidden in the `Monkey` class, 
as the signature is the same and both are static, making option D correct and option C 
incorrect. The version in the `Monkey` class throws a new exception, but it is 
unchecked; therefore, it is allowed. The `dance()` method in `Mammal` is correctly 
overloaded in the `Monkey` class because the signatures are not the same, making option 
E incorrect and option F correct. For methods to be overridden, the signatures must 
match exactly. Finally, line 12 is an invalid override and does not compile, as `int` 
is not covariant with `void`, making options G and H both incorrect.

19) b,
F.  
The `Reptile` class defines a constructor, but it is not a no-argument constructor. 
Therefore, the `Lizard` constructor must explicitly call `super()`, passing in an `int` 
value. For this reason, line 9 does not compile, and option F is the correct answer. If 
the `Lizard` class were corrected to call the appropriate `super()` constructor, then 
the program would print `BALizard` at runtime, with the *static initializer* running 
first, followed by the *instance initializer*, and finally the method call using the 
overridden method.

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
E.
The program compiles and runs without issue, making options A through D incorrect. The 
`fly()` method is correctly overridden in each subclass since the signature is the same, 
the access modifier is less restrictive, and the return types are covariant. For 
covariance, `Macaw` is a subtype of `Parrot`, which is a subtype of `Bird`, so 
overridden return types are valid. Likewise, the constructors are all implemented 
properly, with explicit calls to the parent constructors as needed. Line 19 calls the 
overridden version of `fly()` defined in the `Macaw` class, as overriding replaces the 
method regardless of the reference type. This results in `feathers` being assigned a 
value of 3. The `Macaw` object is then cast to `Parrot`, which is allowed because 
`Macaw` inherits `Parrot`. The `feathers` variable is visible since it is defined in 
the `Bird` class, and line 19 prints `3`, making option E the correct answer.

21) b, g
B, G.
Immutable objects do not include setter methods, making option A incorrect. An immutable 
class must be marked `final` or *contain only private constructors*, so no subclass can 
extend it and make it mutable, making option B correct. Options C and E are incorrect, 
as immutable classes can contain both instance and static variables. Option D is 
incorrect, as marking a class `static` is not a property of immutable objects. Option F 
is incorrect. While an immutable class may contain only private constructors, this is 
not a requirement. Finally, option G is correct. It is allowed for the caller to access 
data in mutable elements of an immutable object, provided they have no ability to modify 
these elements.

22) e,
D.
The code compiles and runs without issue, making option E incorrect. The Child class 
overrides the `setName()` method and hides the static `name` variable defined in the 
inherited `Person` class. Since variables are only hidden, not overridden, there are two 
distinct `name` variables accessible, depending on the location and reference type. Line 
8 creates a `Child` instance, which is implicitly cast to a `Person` reference type on 
line 9. Line 10 uses the `Child` reference type, updating `Child.name` to `Elysia`. Line 
11 uses the `Person` reference type, updating `Person.name` to `Sophia`. Lines 12 and 13 
both call the overridden `setName()` instance method declared on line 6. This sets 
`Child.name` to `Webby` on line 12 and then to `Olivia` on line 13. The final values of 
`Child.name` and `Person.name` are `Olivia` and `Sophia`, respectively, making option D 
the correct answer.

23) f,
B.
The program compiles, making option F incorrect. The constructors are called from the 
child class upward, but since each line of a constructor is a call to another 
constructor, via `this()` or `super()`, they are ultimately executed in a top-down 
manner. On line 29, the `main()` method calls the `Fennec()` constructor declared on 
line 19. Remember, integer literals in Java are considered int by default. This 
constructor calls the `Fox()` constructor defined on line 12, which in turn calls the 
overloaded `Fox()` constructor declared on line 11. Since the constructor on line 11 
does not explicitly call a parent constructor, the compiler inserts a call to the 
no-argument `super()` constructor, which exists on line 3 of the `Canine` class. Line 3 
is then executed, adding `q` to the output, and the compiler chain is unwound. Line 11 
then executes, adding `p`, followed by line 14, adding `z`. Finally, line 21 is 
executed, and `j` is added, resulting in a final value for logger of `qpzj`, and making 
option B correct. For the exam, remember to follow constructors from the lowest level 
upward to determine the correct pathway, but then execute them from the top down using 
the established order.

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
17:   { System.out.print("9"); }
18: }
```
C.
The code compiles and runs without issue, making options E and F incorrect. First, 
the class is initialized, starting with the superclass `Antelope` and then the 
subclass `Gazelle`. This involves invoking the *static variable* declarations and 
*static initializers*. The program first prints `1`, followed by `8`. Then we follow 
the constructor pathway from the object created on line 14 upward, initializing each 
class instance using a top-down approach. Within each class, the instance initializers 
are run, followed by the referenced constructors. The `Antelope` instance is initialized, 
printing `24`, followed by the `Gazelle` instance, printing `93`. The final output is 
`182493`, making option C the correct answer.

25) b, c, e
B, C.
Concrete classes are, by definition, not abstract, so option A is incorrect. A concrete 
class must implement all inherited abstract methods, so option B is correct. Concrete 
classes can be optionally marked `final`, so option C is correct. Option D is incorrect; 
concrete classes need not be immutable. A concrete subclass only needs to override the 
inherited abstract method, not match the declaration exactly. For example, a covariant 
return type can be used. For this reason, option E is incorrect.

26) d, e,
D.
The classes are structured correctly, but the body of the `main()` method contains a 
compiler error. The `Orca` object is implicitly cast to a `Whale` reference on line 7. 
This is permitted because `Orca` is a subclass of `Whale`. By performing the cast, the 
`whale` reference on line 8 does not have access to the `dive(int… depth)` method. For 
this reason, line 8 does not compile, making option D correct.

