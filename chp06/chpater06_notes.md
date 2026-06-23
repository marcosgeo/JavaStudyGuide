# Chapter 6 - Class Design

Objectives:
 - utilizing Java Object-Oriented Approach
  - create classes and records, and define instance and static fields and methods, constructors, and instance and static initializers.
  - understand variable scopes, use local variable type inference, apply encapsulation, and make objects immutable.
  - implement polymorphism and differentiate object type versus reference. Perform type casting, identify object types using `instanceof` operator and pattern matching.
  
This chapter is the culmination of some of the most important topics in Java including 
inheritance, class design, constructors, order of initialization, overriding methods, 
abstract classes, and immutable objects.

## Understanding Inheritance

### Declaring a Subclass

```
---
// BigCat.java
package family;
public class BigCat {
  protected double size;
}
---
// Jaguar.java
package cats;
import family.BigCat;
public class Jaguar extends BigCat {
  public Jaguar() {
    size = 10.2;
  }
  public void printDetails() {
    System.out.print(size);
  }
}
---
// Spider.java
package spiders;
public class Spider {
  public void printDetails() {
    System.out.println(size);    // does not compile
  }
}
```
`Jaguar` is a *subclass* or *child* of `BigCat`, making `BigCat` a *superclass* or 
*parent* of `Jaguar`. The only condition to be a *superclass* it not to be declared 
*final*. In the `Jaguar` class, `size`is accessible because it is marked `protected` on 
the *superclass*. Via inheritance, the `Jaguar` subclass can read and write `size` as 
if it were its own member. Contrast this with the `Spider` class, which has no access 
to `size` since it is not inherited.

### Class Modifiers

A class declaration can have various modifiers:

    +--------------+-----------------------------------+---------------+
    |  Modifier    |  Description                      |  Covered in   |
    +--------------+-----------------------------------+---------------+
    |  final       |  The class may not be extended.   |  chapter 6    |
    +--------------+-----------------------------------+---------------+
    |  abstract    |  Requires a concrete subclass;    |  chapter 6    |
    |              |  may contain abstract methods.    |               |
    +--------------+-----------------------------------+---------------+
    |  sealed      |  May only be extend by a          |  chapter 7    |
    |              |  specific list of classes.        |               |
    +--------------+-----------------------------------+---------------+
    |  non-sealed  |  A subclass of a sealed class     |  chapter 7    |
    |              |  that permits unnamed subclasses. |               |
    +--------------+-----------------------------------+---------------+
    |  static      |  Used for static nested classes   |  chapter 7    |
    |              |  defined within a class.          |               |
    +--------------+-----------------------------------+---------------+
    

The `final` modifier prevents a class from being extend any further.
```
public final class Rhinoceros extends Mammal {}
---
public class Clara extends Rhinoceros {}    // does not compile
```

### Single vs Multiple Inheritance

Java supports *single inheritance*, by which a class may inherit from **only one 
direct parent class**. Java also supports multiple levels of inheritance, by which one 
class may extend another class, which in turn extends another class.

Part of what makes multiple inheritance complicated is determining which parent to 
inherit values from in case of a conflict. For example, if we have an object or method 
defined in all of the parents, which one does the child inherit?

### Inheriting Object

In Java, all classes inherit from a single class: `java.lang.Object`, or `Object` for 
short. Furthermore, `Object` is the only class that doesn't have a parent class. When 
compiling, the compiler automatically insert code into any class we write that doesn't 
extend a specific class. For example, the following two are equivalent:
```
public class Zoo {}
---
public class Zoo extends java.lang.Object {}
```
The result is that every class gains access to any accessible methods in the `Object` 
class. For example, the `toString()` and `equals()` methods are available in `Object`; 
therefore, they are accessible in all classes. Without being overridden in a subclass, 
though, they may not bee particularly useful.
```
java.lang.Object  <-- ...  <-- Mammal <-- Cat <-- Oxx  
```
When we define a class that extends an existing class, java *does not* automatically 
extend `Object` to it. Since all class inherit from `Object`, extending an existing 
class means the child already inherits from `Object`, like `Oxx` above.

Primitive types such as `int` and `boolean` do not inherit from `Object`, since the 
are not classes. Through autoboxing they can be assigned or passed as an instance of 
an associated wrapper class, which does inherit `Object`.


## Creating Classes

Now that we've established how inheritance works in Java, we can use it to define and 
create complex class relationships.

### Extending a Class

Let's create two classes in the same package.
```
// Animal.java
package animal;
public class Animal {
  private int age;
  protected String name;
  public int getAge() {
    return age;
  }
  public void setAge(int newAge) {
    age = newAge;
  }
}
---
// Lion.java
package animal;
public class Lion extends Animal {
  protected void setProperties(int age, String n) {
    setAge(age);
    name = n;
  }
  public void roar() {
    System.out.print(name + ", age " + getAge() + ", says: Roar!");
  }
  public static void main(String[] args) {
    var lion = new Lion();
    lion.setProperties(3, "kion");
    lion.roar();
  }
}
```
The `age` variable exists in the parent `Animal` class and is not directly accessible 
in the `Lion` child class. It is indirectly accessible via the `getAge()` and 
`setAge()` methods. The `name` variable is `protected`, so it is inherited in the 
`Lion` class and directly accessible.

In the `Lion` class, the instance variable `age` is marked `private` and is not 
directly accessible from the subclass `Lion`. Therefore, the following would not 
compile:
```
public class Lion extends Animal {
  public void roar() {
    System.out.print("Lions age: " + age);    // does not compile
  }
}
```
Since we are in a subclass, private member on the parent class are never inherited, 
and package members are only inherited if the two classes are in the same package.

### Applying Class Access Modifiers

A *top-level* class is one not defined inside of another class (the most common), is
the one that gives the file name, and *is not* declared as `protected` or `private`. 
It should be `public` or with no access modifier declared (package access).

### Accessing the *this* Reference

Inside a class method, when we have a local variable with the same name as an instance 
member, we use the `this` keyword in order to access the class member and avoid 
conflict. The `this` reference refers to the current instance of the class and can be 
used to access any member of the class, including inherited members. It can be used in 
any instance method, constructor, or instance initializer block. It cannot be used 
when there is no implicit instance of the class, such as in a static method or static 
initializer block.

### Calling the *super* Reference

In Java, a variable or method cab be defined in both a parent class and a child class. 
This means the object instance actually holds two copies of the same variable with the 
same underlying name. When this happens, we could use the `super` reference or 
keyword. With the `super` reference, we access member defined in the superclass, the 
first that is found.
```
// Reptile.java
public class Reptile {
  protected int speed = 10;
}
---
// Crocodile.java
public class Crocodile extends Reptile {
  protected int speed = 20;
  public int getSpeed() {
    return speed;
  }
  public static void main(String[] args) {
    var croc = new Crocodile();
    System.out.print(croc.getSpeed());    // 20
    System.out.print(super.getSpeed());   // 10
  }
}
```
When the method `getSpeed()` is called, Java first checks if there is any *local* 
variable called `speed`, since there isn't, then it uses `this.speed`. If the 
`Crocodile` class doesn't has a variable called `speed`, Java will use `super.speed` 
ant then returning the value of `speed` in the `Reptile` class.


## Declaring Constructors

A *constructor* is a method that has no return type and the name matches the name of 
the class. There are a lot of rules about constructors, we start with how to create a 
constructor. Then, we look at default constructors, overloading constructors, calling 
parent constructors, final fields, and the order of initialization.

### Creating a Constructor
This is a valid constructor:
```
public class Bunny {
  public Bunny() {
    System.out.print("hop");
  }
}
```
The name of the constructor, `Bunny`, matches the name of the class, `Bunny`, and 
there is no return type.

Like method parameters, constructor parameters can be any valid class, array, or 
primitive type, including generics, but may not include `var`. The following does not 
compile:
```
public class Bonobo {
  public Bonobo(var food) {    // does not compile
     ...
  }
}
```
A class can have multiple constructors, as long as each constructor has a unique 
constructor signature. Like methods with the same name but different signatures, this 
is called *constructor overriding*.

Constructors are used when creating a new object. This process is called 
*instantiation* because it creates a new instance of the class. Example:
```
new Turtle(15);
```
When Java sees the `new` keyword, it allocates memory form the new object. It then 
looks for a constructor with a matching signature and calls it.

### The Default Constructor

Every class in Java has a constructor, whether we code one or not. **If we don't** 
**include any constructors in the class, Java will create one**, without any 
parameters, for us. This constructor is called *default constructor*.
```
public class Rabbit1 {}
---
public class Rabbit2 {
  public Rabbit2() {}
}
---
public class Rabbit3 {
  public Rabbit3(boolean b) {}
}
---
public class Rabbit4 {
  private Rabbit4() {}
}
```
Calling this constructors
```
public class RabiitsMultiply {
  public static void main(String[] args) {
    var r1 = new Rabbit1();
    var r2 = new Rabbit2();
    var r3 = new Rabbit3(true);
    var r4 = new Rabbit4();    // does not compile
  }
}
```
Since `Rabbit1` nod declares a constructor, a default is created by the compiler and 
allows that the object `r1` is created. The object `r2` and `r3` are created using 
public constructors existing in the classes. Because `Rabbit4` has a `private` 
constructor, it not allow other classes to call it.

### Calling Overloaded Constructors with *this()*

Since a class can contain multiple overloaded constructors, this constructors can call 
one another. 
```
public class Hamster {
  private String color;
  private int weight;
  public Hamster(int weight, String color) {    // first constructor
    this.weight = weight;
    this.color = color;
  }
  public Hamster(int weight) {    // second constructor
    this.weight = weight;
    color = "brown";
  }
}   
```
This class compile, although there is a bit of duplication, as `this.weight` is 
assigned the same way in both constructors. Note that calling 
`Hamster(weight, "brown")` in the second constructor will not work since we need to 
use the keyword `new` to a constructor be called. Using `new Hamster(weight, "brown")` 
creates a second object.
When `this()` is used in this way, with parenthesis, Java calls another 
constructor in the same instance of the class, so the second constructor should be.
```
public class Hamster {
  ...
  public Hamster(int weight) {
    this(weight, "brown");
  }
}
```
Now Java calls the constructors with two parameters. Calling `this()` has one special 
rule: **it need to be called in the first statement in the constructor**. Is not 
permitted calling anything before the `this()`, even a print statement.

### Calling Parent Constructors with *super*

In order to initialize instance members of the *parent class* we need a way to call 
a constructor in that class. We do this using `super()`.

```
public class Animal {
  private int age;
  public Animal(int age) {
    super();    // refers to a contstructor in java.lang.Object (Java do this auto//)
    this.age = age;
  }
}
---
public class Zebra extends Animal {
  public Zebra(int age) {
    super(age);    // refers to a constructor in Animal
  }
  public Zebra() {
    this(5);    // refers to a constructor in Zebra with an int argument
  }
}
```
The first statement of **every constructor** is a call to a parent constructor using 
`super()` or another constructor in the class using `this()`. When we not call the 
constructor of the parent class, Java will do a call to `super()`, the constructor of 
the *superclass* or the constructor of `Object` class (when the class is nothing 
inheriting any class). 
Like calling `this()`, calling `super()` should be the first statement of the 
constructor.
The three classes declaration below, result the same one, and Java will make the job 
of transform the first two in the last one.
```
public class Donkey {}
---
public class Donkey{
  public Donkey(){}
}
---
public class Donkey {
  public Donkey() {
    super();
  }
}
```

### Default Constructor Tips and Tricks

When a class defines a constructor, the compiler does not insert a no-argument 
constructor.
```
public class Mammal {
  public Mammal(int age) {}
}
---
public class Seal extends Mammal {}    // does not compile
---
public class Elephant extends Mammal {
  public Elephant() {}    // does not compile
}
```
Since `Mammal` defines a constructor, the compiler does not insert a no-argument 
constructor. The compiler will insert a default no-argument constructor into `Seal`, 
but it will be a simple implementation that just calls a nonexistent parent 
constructor, like this.
``` 
public class Seal extends Mammal {
  public Seal() {
    super();    // does not compile
  }
}
```
`Elephant` will not compile for similar reasons. The compiler doesn't see a call to 
`super()` or `this()` and it inserts a call to a nonexistent no-argument `super()` 
automatically. 

In this cases, the compiler will not help, and we need to create at least on 
constructor in our child classes that explicitly calls a parent constructor.

To remember:
 - the first line of every constructor is a call to a parent constructor using `super()` or an overloaded constructor using `this()`
 - if the constructor does not contain a `this()` or `super()` reference, the compiler will insert `super()` with no arguments as the first line of the constructor
 - if a constructor calls `super()`, then it must be the first line of the constructor


## Initializing Objects

This sections discuss in detail how the *order of initialization* works, it refers to 
how member of a class are assigned values.

### Initializing classes

Before initializing an object, first all static members of a class are initialized. 
**Classes are loaded when they are needed**, that is, when an object of the class is 
instantiated or a static member is used. This is done in an hierarchical order, with 
the most base classes starting first. The order is this:

 - 1. if there is a superclass Y of X, then initialize Y first.
 - 2. process all static variable declarations in the order in which they appear in the class
 - 3. process all static initializers in the order in which they appear in the class

Taking a look at an example, what does the following program print?
```
public class Animal {
  static {System.out.print("A);}
}
---
public class Hippo extends Animal {
  String color = "brown";
  public static void main(String[] args) {
    System.out.print("C");
    new Hippo();
    new Hippo();
    new Hippo();
  }
  static {System.out.print("B");}
}
```
It prints "ABC" exactly once. Since the `main()` method is inside the `Hippo` class, 
the class will be initialized first, starting with the superclass and going to the 
*static initializer*, printing "AB". Afterward, the `main()` method is executed, 
printing "C". Even though the `main()` method created three instances, the class is 
loaded only once.

In the previous example, note that all the class members, are initialized before the 
`main()` method. If we have other class that instantiate `Hippo`, we have:
```
public class HippoFriend {
  public static void main(String[] args) {
    System.out.print("C");
    new Hippo();
  }
}
```
Assuming the `Hippo` class isn't reference anywhere else, this program will *likely* 
print "CAB", with the `Hippo` class **not being loaded until it is needed** inside the 
`main()` method. It is likely because is the JVM, at runtime, that controls everything.

### Initializing *final* Fields

Fields marked `final` must be assigned values when they are declared or in an instance 
initializer.
```
public class MouseHouse {
  private final int volume;
  private final String name = "The Mouse House";    // declaration assignment
  {
    volume = 10;    // instance initializer assignment
  }
}
```

Unlike static class members, final instance fields can also be set in the constructor, 
since the constructor is part of the initialization process. So, this is also valid:
```
public class MouseHouse {
  private final int volume;
  private final String name;
  public MouseHouse() {
    this.name = "Empty House";    // constructor assignment
  }
  {
    volume = 10;    // instance initializer assignment
  }
}
```

Instance variables marked as final **must be assigned a value** during the object 
initialization, by:
 - assigning a value when they are declared
 - assigning a value in an instance initializer, or
 - assigning a values in the constructor
 
 If not assigned, they will cause a compiler error.


### Initializing Instances
 
The initialization starts with the lowest-level constructor where the `new` keyword is 
used. Remembering that **the first line of every constructor is a call to `this()` or `super()`** 
and if omitted, the compiler will automatically insert a call to the parent no
-argument constructor `super()`. Then, the initialization progress upward following 
the order of the constructors. Finally, starting with the superclass, each class is 
initialized, processing each instance initializer and constructor in the reverse order 
in which it was called.
 
This is the summary of the order of initialization for an instance of X class:
 1. initialize class X if it has not been previously initialized.
 2. if there is a superclass Y of X, then initialize the instance of Y first.
 3. process all instance variables declarations in the order in which they appear
 4. process all instance initializers in the order in which they appear
 5. initialize the constructor, including any overloaded constructors referenced with `this()`

An example with no inheritance. What is the output?
```
01: public class ZooTickets {
02:   private String name = "BestZoo";
03:   { System.out.print(name + "-"); }
04:   private static int COUNT = 0;
05:   static { System.out.print(COUNT + "-"); }
06:   static { COUNT += 10; System.out.print(COUNT + "-"); }
07:
08:   public ZooTickets() {
09:     System.out.print("z-");
10:   }
11:
12:   public static void main(String... patrons) {
13:     new ZooTickets();
14:   }
15: }
```
The output is this: `0-10-BestZoo-z-`

Fist, we hat to initialize the class. Since there is no superclass declared, `Object` 
becomes the superclass, and we start with the static components of `ZooTickets`. In 
this case, lines 4, 5, and 6 are executed, printing '0-' and '10-'. Next, we 
initialize the instance created on line 13, again, since no superclass is declared, we 
start with the instance components. Then, lines 2 and 3 are executed, which prints 
'BestZoo-'. Finally, we run the constructor, on lines 8-10, which outputs 'z-'.

Another example, now with inheritance.
```
class Primate {
  public Primate() {
    System.out.print("Primate-");
  }
}
---
class Ape extends Primate {
  public Ape(int fur) {
    System.out.print("Ape1-");
  }
  public Ape() {
    System.out.print("Ape2-");
  }
}
---
public class Chimpanzee extends Ape {
  public Chimpanzee() {
    super(2);
    System.out.print("Chimpanzee-");
  }
  public static void main(String[] args) {
    new Chimpanzee();
  }
}
```
The compiler inserts the `super()` command as the first statement of both `Primate` 
and `Ape` constructors. The code will execute with the parent constructors called 
first and yield the following output:

Primate-Ape1-Chimpanzee-

Notice that only one of the two `Ape` constructors is called. We start with the call 
to `new Chimpanzee()` to determine which constructors will be executed. Constructors 
are executed from the bottom up, but since the first line of every constructor is a 
call tho another constructor, the flows ends up with the parent constructor executed 
before the child constructor.

A much harder:
```
01: public class Cuttlefish {
02:   private String name = "swimmy";
03:   { System.out.println(name); }
04:   private static int COUNT = 0;
05:   static { System.out.println(COUNT); }
06:   { COUNT++; System.out.println(COUNT); }
07:
08:   public Cuttlefish() {
09:     System.out.println("Constructor");
10:   }
11:
12:   public static void main(String[] args) {
13:     System.out.println("Ready");
14:     new Cuttlefish();
15:   }
16: }
```
The output is this:
```
0
Ready
swimmy
1
Constructor
```
No superclass is declared, so we can skip any steps that relate to inheritance. We 
first process the static variables and static initializers -- lines 4 and 5, with line 
5 printing '0'. Now that the static initializers are out of the way, the `main()` 
method can run, which prints 'Ready'. Next we create an instance declared on line 14. 
Lines 2, 3, and 6 are processed, with line 3 printing 'swimmy' and line 6 printing 
'1'. Finally, the constructor is run on lines 8 - 10, which prints 'Constructor'.

A more difficult one. What does the following output?
```
01: class GiraffeFamily {
02:   static { System.out.print("A"); }
03:   { System.out.print("B"); }
04: 
05:   public GiraffeFamily(String name) {
06:     this(1);
07:     System.out.print("C");
08:   }
09:
10:   public GiraffeFamily() {
11:     System.out.print("D");
12:   }
13:
14:   public GiraffeFamily(int stripes) {
15:     System.out.print("E");
16:   }
17: }
18: public class Okapi extends GiraffeFamily {
19:   static { System.out.print("F"); }
20:
21:   public Okapi(int stripes) {
22:     super("sugar");
23:     System.out.print("G");
24:   }
25:   { System.out.print("H"); }
26:
27:   public static void main(String[] grass) {
28:     System.out.println("J");
29:     new Okapi(1);
30:     System.out.println();
31:     new Okapi(2);
32:  }
33: }
```
This are the rules of initialization:
- class initialization: static initializers, first super-classes than classes
- after classes initialization, the `main()` is executed
- super-class instance initialization: first variables, than blocks, finally constructors
- class instance initialization: blocks, than constructors

The program prints the following:
AFJBECHG
BECHG

## Inheriting Members

Inheriting a class not only grants access to inherited methods in the parent class but 
also sets the stage for collisions between methods defined in both, the parent class 
and the subclass.

### Overriding a Method

In Java, *overriding* a method occurs when a subclass declares a new implementation 
for a inherited method with the same signature and compatible return type. The parent 
version of the overrode method still accessible via `super` keyword or, if is a 
`static` method, via class name. We do this when we want to define a new version of a 
method and have it behave differently for the subclass.

To override a method we need follow this rules:
1. the method in the child class must have the same signature
2. the method in the child class must be at least as accessible at the method in the parent class
3. the method in the child class may not declare a checked exception that is new or broader than the class of any exception declared in the parent class method
4. if the method returns a value, it must be the same or a sub-type of the method of 
the parent class, known as *covariant return types*.

**Since we want to take the benefits of overriding, we must follows this rules**, or 
the code will not compile.

### Making Methods with the @Override Annotation

An annotation is a metadata tag that provides additional information about our code. 
We can user the *@Override* annotation to tell the compiler that we are attempting to 
override a method.
This annotation can prevent us from making a mistake, because it prevents the code to 
compile when they could, but is not our intention.
```
public class Fish {
  public void swim() {};
}
---
public class Shark extends Fish {
  @Override
  public void swim(int speed) {};    // does not compile
}
```
Without the annotation, we will create an overloaded method instead of an overrode, 
which is not our intention.

### Re-declaring *private* Methods

Java permits us to re-declare a new method in the child class with the same name or 
modified signature as the private method in the parent class. The method in the child 
class is a separate and independent method, unrelated to the parent version's method, 
so none of the rules for overriding methods is invoked.
```
public class Beetle {
  private String getSize() {
    return "Undefined";
  }
}
---
public class RhinocerosBeetle extends Beetle {
  private int getSize() {
    return 5;
  }
}
```
In this example, the method `getSize()` in the parent class is re-declared, so the 
method in the child class is a new method and not an override of the method in the 
parent class. If the method in the `Beetle` class was declared `public`, the rules of 
overriding will be applied and the code will not compile.

### Hiding Static Methods

A *hidden method* occurs when a child class defines a static methods with the same 
name and signature as an inherited static methods defined in a parent class. Method 
hiding is similar to but not exactly the same as method overriding. When this occur, a 
new *fifth* rule is added for hiding a method:

- The method defined in the child class must be marked as `static` if it is marked as `static` in a parent class.

So, if the two methods are marked `static`is method hiding and is overriding if they 
have other **equal access modifier**. Otherwise, the code will no compile.

