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
constructor of the parent class, Java will do a call `super()` to the constructor of the *superclass* or to the constructor of `Object` class, when the class is nothing 
inheriting any class. 
Like calling `this()`, calling `super()` should be the first statement of the 
constructor.

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

