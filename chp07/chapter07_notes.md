# Chapter 7 - Beyond Classes

This chapter introduces other top-level types available in java, beyond classes, 
including *interfaces*, *enums*, *sealed classes*, and *records*. Many of the basic 
rules applied to methods, applies hero to, but with additional rule for each type.
Encapsulation and polymorphism are discussed hero too.

## Implementing Interfaces

An *interface* is an abstract data type that declares a list of abstract methods that 
any class implementing the interface must provide. A class can implement any number of 
interfaces, thus, super-passing the limitation of extending only one class.

### Declaring and Using an Interface

One aspect of an interface declaration that differs from an abstract class is that it 
contains implicit modifiers. An *implicit modifier* is a modifier that the compiler 
automatically inserts into the code. For example, an interface is always considered 
to be abstract, even if it is not marked so.
```
public interface WalksOnTwoLegs {}    // this interface definition
---
public abstract interface WalksOnTwoLegs {}    // is the same of this
---
public final interface WalksOnEightLegs {}    // does not compile
```
Note that interfaces are not required to define any method, so the code above compile.
The `abstract` is optional for interfaces, sine the compiler inserts it if it is not 
provided. An interface cannot be barked as `final` since this implies no class could 
ever implement it.

The methods of an interfaces are also *implicit public abstract*, meaning, without any 
modifier provided, the compiler will insert `public abstract` for each method of an 
interface. The variables are all `public static final` and must receive a value when 
declared.
```
// This interface
public interface CanBurrow {
  Float getSpeed(int age);
  int MINIMUN_DEPTH = 2;
}
---
// is compiled to this
public abstract interface CanBurrow {
  public abstract Float getSpeed(int age);
  public static final int MINIMUN_DEPTH = 2;
}
```

To use an interface we use the keyword `implements` on the class declaration signaling 
that the class uses the interface.
```
public interface Climb {
  Number getSpeed(int age);
}
--
public class FieldMouse implements Climb, CanBurrow {
  public Float getSpeed(int age) {
    return 11f;
  }
}
```
The `FieldMouse` class declares that it implements the `Climb` and `CanBurrow` 
interfaces and includes an overridden version of `getSpeed()` inherited from both 
interfaces. The method signature of `getSpeed()` matches exactly, and the return type 
is covariant, since a `Float` can be implicitly cast to a `Number`. The access modifier 
of the interface method is implicitly public in `Climb`, but the concrete class 
`FieldMouse` must explicitly declare it.
If any of the interfaces defines abstract methods, the the concrete class is required 
to override them. In this case, `FieldMouse` implements two interfaces at the same time 
since the `getSpeed()` overrides two abstract methods with one single implementation.

### Extending an Interface

Like a class, an interface can extend another interface using the `extends` keyword.
```
public interface Nocturnal {}
---
public interface CanFly {
  public void flap();
}
---
public interface HasBigEyes extends Nocturnal, CanFly {}
---
public class Owl implements HasBigEyes {
  public int hunt() { return 5; }
  public void flap() { System.out.println("Flaps!"); }
}
```
In this example, the `Owl` class implements the `HasBigEyes` interface and must 
implement the `hunt()` and `flap()` methods. Extending two interface is permitted 
because interfaces are not initialized as part of a class hierarchy. Unlike abstract 
classes, they do not contain constructors and are not part of instance initialization. 
Interfaces simply define a set of rules and methods that a class implementing them 
must follow.

### Inheriting an Interface

Like an abstract class, when a concrete class inherits an interface, all of the 
inherited abstract methods must be implemented. How many abstract methods the concrete 
`Swan` class inherit?
```
interface Fly {
  void fly();
}
---
abstrac class Animal {
  abstract int getType();
}
---
abstract class Bird implements Fly {
  abstract boolean canSwoop();
}
---
interface Swim {
  void swim();
}
---
class Swan extends Bird implements Swim {
  ???
}
```
The concrete `Swan` class inherits four abstract methods that it must implement: 
`getType()`, `catSwoop()`, `fly()`, and `swim()`.

Although a **class can implement an interface**, a *class cannot extend an interface*. 
Likewise, while an **interface can extend another interface**, an *interface cannot* 
*implement another interface*.

### Inheriting Duplicate Abstract Methods

Java supports inheriting two abstract methods that have compatible method declarations.
```
public interface Herbivore { public void eatPlants(); }
---
public interface Omnivore { public void eatPlants(); }
---
public class Bear implements Herbivore, Omnivore {
  public void eatPlants() {
    System.out.println("Eating plants");
  }
}
```
By *compatibles*, we understand a method can be written that properly overrides both 
inherited methods, sometime using covariant return types. The following is an example 
of an incompatible declaration:
```
public interface Herbivore { public void eatPlants(); }
---
public interface Omnivore { public int eatPlants(); }
---
public class Tiger implements Herbivore, Omnivore {
  ...
}    // does not compile
```
It's ins impossible to write a version of `Tiger` that satisfies both inherited abstract 
methods. The code does not compile, regardless of what is declared inside the `Tiger` 
class.

### Inserting Implicit Modifiers

As mentioned earlier, an implicit modifier is on that the compiler will automatically 
inserts. It's reminiscent of the compiler inserting a default no-argument constructor 
if we do not define a constructor. We can choose insert theses implicit modifiers by 
ourselves or let the compiler do the job. This is the list of implicit modifiers:
- interfaces are implicitly abstract.
- interface variables area implicitly public, static and final.
- interface methods without a body are implicitly abstract.
- interface methods without the private modifier are implicit public

The following two interface definitions are equivalent, as the compiler will convert 
them both to the second declaration:
```
public interface Soar {
  int MAX_HEIGHT = 10;
  final static boolean UNDERWATER = true;
  void fly(int speed);
  abstract void takeoff();
  public abstract double dive();
}
---
public **abstract** interface Soar {
  **public static final** int MAX_HEIGHT = 10;
  **public** final static boolean UNDERWATER = true;
  **public abstract** void fly(int speed);
  **public** abstract void takoff();
  public abstract double dive();
}
```
The bold words are the implicit modifiers that the compiler *"automagically"* inserts.

### Conflicting Modifiers

What happens if we marks a method or variable with a modifier that conflicts with an 
implicit modifier? Simply: it no compiles.
```
public interface Dance {
  private int count = 4;    // does not compile
  protected void step();    // does not compile
}
```
Neither of these interface declaration compiles, as the compiler will apply the `public` 
modifier to both, resulting in a conflict.

### Differences between Interfaces and Abstract Classes

Even though abstract classes and interfaces are both considered abstract types, only 
interfaces make use of implicit modifiers. Observe the behavior of `play()`:
```
abstract class Husky {    // abstract is required
  abstract void play();    // abstract is required
}
---
interface Poodle {    // abstract is optional
  void play();    // abstract is optiona
}
---
public class Webby extends Husky {
  void play() {}    // ok, play() is declared with package access in Husky
}
---
public class Geogette implements Poodle {
  void play() {}    // does not compile, play() is public in Poodle
}
```
Even though the two method implementations are identical, the method in the `Geogette` 
class reduces the access modifier on the method from *public* to *package access*.

## Declaring Concrete Interface Methods

The six interfaces member types that we need to know. Until now, we cover the first two.

    +-------------------------------------+-----------------+-----------------+----------------+
    |                   | **Membership**  | **Required**    | **Implicit**    | **Has body**   |
    |                   | **type**        | **modifiers**   | **modifiers**   | **or value**   |
    --------------------+-----------------+-----------------+-----------------+----------------+
    | Constant variable |  Class          |     ---         |  public         |  Yes           |
    |                   |                 |                 |  static         |                |
    |                   |                 |                 |  final          |                |
    +-------------------+-----------------+-----------------+-----------------+----------------+
    | abstract method   |  Instance       |     ---         |  public         |  No            |
    |                   |                 |                 |  abstract       |                |
    +-------------------+-----------------+-----------------+-----------------+----------------+
    | default method    |  Instance       |     default     |  public         |  Yes           |
    +-------------------+-----------------+-----------------+-----------------+----------------+
    | static method     |  Class          |     static      |  public         |  Yes           |
    +-------------------+-----------------+-----------------+-----------------+----------------+
    | private method    |  Instance       |     private     |  ---            |  Yes           |
    +-------------------+-----------------+-----------------+-----------------+----------------+
    | private static    |  Class          |     private     |  ---            |  Yes           |
    | method            |                 |     static      |                 |                |
    +-------------------+-----------------+-----------------+-----------------+----------------+

The **membership type** determines how it is able to be accessed. A method with a 
membership type of *class* is shared among all instances of the interface, whereas a 
method with a membership type of *instance* is associated with a particular instance 
of the interface.

### Writing a *default* Interface Method

A *default method* is a method defined in an interface with the `deffault` keyword and 
includes a method body. It may be optionally overridden by a class implementing the 
interface.
```
public interface IsColdBlooded {
  boolean hasScales();
  default double getTemperature() {
    return 10.0;
  }
}
---
public class Snake implements IsColdBlooded {
  public boolean hasScales() {    // required override
    return True;
  }
  public double getTemperature() {    // optional override
    return 12;
  }
}
```
One use of default methods is for backward compatibility. We can add a new default 
method to an interface without the need to modify all of the existing classes that 
implement the interface. The older classes will just use the `default` implementation.

**Default Interface Method Definition Rules**

1. a default method may be declared only within an interface.
2. a default method must be marked with the `default` keyword and include a body
3. a default method is implicitly public
4. a default method cannot be marked abstract, final, or static.
5. a default method may be overridden by a class the implements the interface
6. if a class inherits two or more default methods with the same method signature, 
then the class must override the method.

### Inheriting Duplicate *default* Methods

What value would the following code output?
```
public interface Walk {
  public default int getSpeed() { return 5; }
}
---
public interface Run {
  public default getSpeed() { return 10; }
}
---
public class Cat implements Walk, Run {}    // does not compile
```
In this example, `Cat` inherits the two default methods for `getSpeed()`, so which does 
it use? By overriding the conflicting method, the ambiguity about which version of the 
method to call has been removed.
```
public class Cat implements Walk, Run {
  public int getSpeed() { return 1; }
}
```

### Calling a Hidden *default* Method

What if the `Cat` class wanted to access the version of `getSpeed()` in `Walk` or `Run`?
```
public class Cat implements Walk, Run {
  public int getSpeed() {
    return 1;
  }
  public int getWalkSpeed() {
    return Walk.super.getSpeed();
  }
}
```
`Walk` and `Run` versions of `getSpeed()` still accessible via `ClassName.super`, a kind 
of static an instance method, strange, but is Java.

### Declaring *static* and Interface Methods

Interface area also declared with static methods. These methods are defined explicitly 
with the `static`keyword and, the the most part, behave just like static methods 
defined in classes.

**Static Interface Method Definition Rules**
1. a static method must be marked with the `static` keyword and include a method body.
2. a static method without an access modifier is implicitly public.
3. a static method cannot be marked `abstract` or `final`.
4. a static method is not inherited an cannot be accessed in a class implementing the 
interface without a reference to the interface name.

Let's take a look at a static interface method:
```
public interface Hop {
  static int getJumpHeight() {
    return 8;
  }
}
---
public class Skip {
  public int skip() {
    return Hop.getJumpHeight();
  }
}
```
Since in the interface the method is defined without an access modifier, the compiler 
will automatically insert the `public` access modifier. The method `getJumpHeight()` 
works just like a static method as defined in a class.


The last rule, about inheritance might be a little confusing:
```
public class Bunny implements Hop {
  public void printDetails() {
    System.out.println(getJumpHeight());    // does not compile
  }
}
```
Without an explicit reference to to the name of the interface, the code will not compile 
even though `Bunny` implements `Hop`. This could be fixed that way:
```
public class Bunny implements Hop {
  public void printDetails() {
    System.out.println(Hop.getJumpHeight());
  }
}
```


### Reusing Code with *private* Interface Methods

