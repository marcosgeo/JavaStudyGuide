# Chapter 7: Beyond Classes

This chapter introduces other top-level types available in java, beyond classes, 
including [*interfaces*](#implementing-interfaces), [*enums*](#working-with-enums), 
[*sealed classes*](#sealing-classes), [*nested classes*](#creating-nested-classes), 
and [*records*](#encapsulating-data-with-records). 

Many of the basic rules applied to methods, applies hero to, but with additional rule 
for each type. Encapsulation and [polymorphism](#understanding-polymorphism) are 
discussed hero too.

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
interfaces. 

The method signature of `getSpeed()` matches exactly, and the return type 
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
[top](#top)

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

The last two type of concrete methods that can be added to interfaces are *private* and 
*private static* interface methods. Because both types of methods are private, the can 
only be used in the interface declaration in which the are declared. For this reason, 
**they were added primarily to reduce code duplication**. Consider this example:
```
public interface Schedule {
  default void wakeUp() { checkTime(7); }
  private void haveBreakfast() { checkTime(9); }
  static void workOut() { checkTime(18); }
  private static void chackTime(int hour) {
    int currentTime = 17;   // just for clarity
    if (hour > currentTime) {
      System.out.println("You're late!");
    } else {
      System.out.println("You're late" + (currentTime - hour) + " hours left "
          + "to make the appointment");
    }
  }
}
```
We could write this interface without using a private method by copying the contents of 
`checkTime()` method into the places it is used. It's a lot shorter and easier to read 
if we copy.

We could have also declared `checkTime()` as public, but this would expose the method 
to use outside the interface. One important principle of encapsulation is to not expose 
the internal workings of a class or interface when not is required.

The difference between a *non-static private method* and a *static private method* is 
analogous to the difference between an instance and static method declared within a 
class. In particular, it's all about what methods each can be called from.

**Private Interface Method Definition Rules**
1. a private interface method must be marked with the `private` modifier and include a body
2. a private static interface method may be called by any method within the interface definition
3. a private interface method may only be called by default and other private non-static methods 
   within the interface definition

Another way to think of it is that a *private interface method* is only accessible to 
no-static methods defined within the interface. A *private static interface method*, on 
the other hand, can be accessed by any method in the interface. For both types of 
private methods, **a class inheriting the interface cannot directly invoke them**.

## Calling Abstract Methods

The primary reason that *default* and *private non-static*, declared in the interface, 
are associated with an instance membership, is that this methods can be accessed by 
*abstract methods* declared in the interface. When the are invoke, there is an instance 
of the interface.
```
public interface ZooRenovation {
  public String projectName();
  abstrac String status();
  default void printStatus() {
    System.out.print("The" + projectName() + " project " + status());
  }
}
```
In this example, both `projectName()` and `status()`have the same modifiers (*abstract* 
and *public* are implicit) and can be called by the *default* method `printStatus()`.

### Reviewing Interface Members

**Table 7.2 Interface member access rules**

    +-------------------------------------+-----------------+--------------------+--------------------+
    |                   | Accessible from | Accessible from | Accessible from    | Accessible without |
    |                   | default and     | static methods  | methods in classes | an instance of     |
    |                   | private methods | within the      | inheriting the     | the interface?     |
    |                   | within the      | interface?      | interface?         |                    |
    |                   | interface?      |                 |                    |                    |
    --------------------+-----------------+-----------------+--------------------+--------------------+
    | Constant variable |  Yes            |  Yes            |  Yes               |      Yes           |
    +-------------------+-----------------+-----------------+--------------------+--------------------+
    | abstract method   |  Yes            |  No             |  Yes               |  No                |
    +-------------------+-----------------+-----------------+--------------------+--------------------+
    | default method    |  Yes            |  No             |  Yes               |  No                |
    +-------------------+-----------------+-----------------+--------------------+--------------------+
    | static method     |  Yes            |  Yes            |  Yes (i.Face.req)  |  Yes (i.Face.req)  |
    +-------------------+-----------------+-----------------+--------------------+--------------------+
    | private method    |  Yes            |  No             |  No                |  No                |
    +-------------------+-----------------+-----------------+--------------------+--------------------+
    | private static    |  Yes            |  Yes            |  No                |  No                |
    | method            |                 |                 |                    |                    |
    +-------------------+-----------------+-----------------+--------------------+--------------------+

This rules can results in this:
- *abstract*, *default*, and *non-static private* methods belongs to an instance of the interface.
- *static methods* and *static variables* belongs to the interface class object.
- all private interface method types are only accessible within the interface declaration.

Using these rules, which methods below do not compile?
```
public interface ZooTrainTour {
  abstract int getTrainName();
  private static void ride() {}
  default void playHorn() { getTrainName(); ride(); }
  public static void slowDown() { playHorn(); }
  static void speedUp() { ride(); }
}
```
The `ride()` method is private and static, so it can be accessed by any default or 
static method within the interface declaration. The `getTrainName()` is abstract, so it 
can be accessed by a default method associated with the instance. The `slowDown()` 
method is static, though, and cannot call a default or private method, such as 
`playHorn()`, without an explicit reference object. Therefore, the `slowDown()` method 
does not compile.


## Working with Enums
[back-to-top](#chapter-7-beyond-classes)

It is common to have a type that can only have a finite set of values, such as days of 
week, season of the year, primary colors, and so on. An *enumeration*, or *enum*, is 
like a fixed set of constants.

### Creating Simple Enums

To create an enum, we declare a type with the `enum` keyword, a name and a list of 
values, like shown below:
```
public enum Season {
  WINTER, SPRING, SUMMER, FALL;
}
```
We refer to an enum that only contains a list of values as a *simple enum*. When 
working with simple enums, the semicolon at the end of the list is optional. Enum 
values are considered constants and are commonly written using snake case, e.g.: 
ROCK_ROAD, MINT_CHOCOLATE_CHIP. The list of values is the first statement of an enum.
```
var s Season.SUMMER;
System.out.println(Season.SUMMER);    // SUMMER
System.out.println(s == Season.SUMMER);    // true
```
As we can see, enum print the name of the enum when `toString()` is called. They can 
be compared using `==` because the are like static final constants, so we cane use 
`equals()` or `==` to compare enums, since each enum value is initialized only once in 
the JVM.


**Calling the _values()_, _name()_, and _ordinal()_ Methods**

An enum provides a `values()` method to get an array of all of the values. We can use 
this like any normal array, including in a for-each loop:
```
for (var season : Season.values()) {
  System.out.println(season.name() + " " + season.ordinal());
}
---
WINTER 0
SPRING 1
SUMMER 2
FALL 3
```
As we can see, each enum value has a corresponding `int` value and the values are listed 
in the order in which they are declared.

**Calling the _valueOf()_ Method**

The `valueOf()` retrieves an enum value from a `String`.
```
Season s = Season.valueOf("SUMMER");    // SUMMER
Seaton t = Season.valueOf("summer");    // IllegalArgumentException
```
The first statement works and assigns the proper enum value to `s`. Note that this line 
is not creating an enum value. Each enum value is created once when the enum is first 
loaded. Once the enum has been loaded, it retrieves the single enum value with the 
matching name.

Since there is no enum value with the lowercase name "summer", Java throws up its hands 
in defeat an throws an `IllegalArgumentException`.

### Using Enums in _switch_ Statements

Enums can be used in `switch` statements and expressions:
```
Season summer = Season.SUMMER;
switch(summer) {
  case WINTER:
    System.out.print("Get out the sled!");
    break;
  case SUMMER:
    System.out.print("Time for the pool");
    break;
  default:
    System.out.print("It is summer yet?");
}
```
The code prints "Time for the pool!" since it matches `SUMMER`. In each case statement, 
we just typed the **value** of the enum rather than writing `Season.WINTER`. Since the 
compiler already knows that the only possible matches cab be enum values, Java treats 
the enum type as implicit. If we type `Season.WINTER`, the code will not compile. Also, 
the code will not compile if we try use the ordinal, the `int` value, for checking the 
case statement.

### Adding Constructors, Fields, and Methods

While simple enum is composed of just a list of values, we can define a *complex* enum 
with additional elements. Let's say our zoo wants to keep track of traffic patterns to 
determine which seasons get the most visitors.
```
01: public enum Season {
02:   WINTER("Low"), SPRING("Medium"), SUMMER("High"), FALL("Medium");
03:   private final String expectedVisitors;
04:   private Season(String expectedVisitors) {
05:     this.expectedVisitors = expectedVisitors;
06:   }
07:   public void printExpectedVisitors() {
08:     system.out.println(expectedVisitors);
09:   }
10: }
```
Things to notice.

On line 2, the list of enum values ends with a semicolon (;). While this is optional 
when our enum is composed solely of a list of values, it is required if there is 
anything in the enum besides the values.

Lines 3-10 are regular Java code. We have an instance variable, a constructor, and a 
method. We mark the instance variable private and final on line 3 so that our enum 
properties cannot be modified.

All enum constructors are implicitly private, with the modifier being optional and an 
enum constructor will not compile if it contains a public or protected modifier.

The parentheses on line 2 are constructor calls, but without the `new` keyword, 
normally used for objects. The first time we ask for any of the enum values, Java 
constructs all of the enum values. After that, Java just returns the already constructed 
enum values. Given that, the constructor below will be called only once:
```
public enum OnlyOne {
  ONCE(true);
  private OnlyOnee(boolean b) {
    System.out.print("constructing, ");
  }
}
---
public class PrintTheOne {
  public static void main(String[] args) {
    System.out.print("begin, ");
    OnlyOne firstCall = OnlyOne.ONCE;    // prints "constructing, "
    OnlyOne secondCall = OnlyOne.ONCE;    // doesn't print anything
    System.out.print("end");
  }
}
```
This class prints this: `begin, constructing, end`. If the `OnlyOne` enum was used 
earlier in the program, and therefore initialized sooner, then the line that declares 
the `firstCall` variable would not print anything.

To call an enum method we just use the enum value followed by the method call:
```
Season.SUMMER.printExpectedVisitors();    // High
```

We can define different methods for each enum value. For example, our *zoo* has different 
seasonal hours. We can keep track of the hours through instance variables, or we can let 
each enum value manage hours itself.
```
public enum Season {
  WINTER {
    public String getHours() { return "10am-3pm"; }
  },
  SPRING {
    public String getHours() { retun "9am-5pm"; }
  },
  SUMMER {
    public String getHours() { return "9am-7pm"; }
  },
  FALL {
    public String getHours() { return "9am-5pm"; }
  };
  public abstract String getHours();
}
```
Note that the values are comma separated with a semi-colon at the end, so the values 
are a single and fist statement of this enum.

In this case the enum has an *abstract method*, this means that each and every enum 
value is required to implement this method. If we forget to implement the method for 
one of the values, we'll get a compiler error. 

An enum can even implement an interface, as this just requires overriding the abstract 
methods:
```
public interface Weather { int getAverageTemparature(); }
---
public enum Season implements Weather {
  WINTER, SPRING, SUMMER, FALL;
  public int getAverageTemperature() { return 30; }
}
```
This king of thing should be used with caution, just because an enum can have lots of 
methods doesn't mean that it should. Is a good practice the keep the things as simple 
as possible. When  enum get too long or too complex, they are hard to read.

## Sealing Classes
[top](#chapter-7-beyond-classes)

A *sealed class* is a class that restricts which other classes may directly extend id. 
As an enum with many constructors, fields, and methods may start to resemble a full-
featured class with a limit of direct subclasses, *sealed class* is exactly that. 

### Declaring a Sealed Class

A sealed class declares a list of classes that can extend it, while the subclasses 
declare that they extend the sealed class.
```
public sealed class Bear permits Kodiak, Panda {}
---
public final class Kodiak extends Bear {}
---
public non-sealed class Panda extends Bear {}
```

**Sealed Class Keywords**
  - **sealed**: indicates that a class or interface may on be extended/implemented by 
 named classes or interfaces.
  - **permits**: used with the sealed keyword to list the classes and interfaces allowed
  - **non-sealed**: applied to a class or interface that extends a sealed class, 
 indicating that it can be extend by unspecified classes
 
Some examples that does not compile:
``` 
public class sealed Frog permits GlassFrog {}    // does not compile
public final class GlassFrog extends Frog {}
---
public abstract sealed class Wolf permits Timber {}
public final class Timber extends Wolf {}
public final class MyWolf exteds Wolf {}    // does not compile
```
The first example does not compile because the `class` and `sealed` modifiers are in 
the wrong order. The modifier hat to be before the class type. The second example does 
not compile because `MyWolf` ins't listed in the declaration of `Wolf`.

### Compiling Sealed Classes

Sealed classes needs to be declared (an compiled) in the same package as its direct 
subclasses and the subclasses need to extends the sealed.
```
//Penguin.java
package zoo;
public sealed class Penguin permits Emperor {}    // does not compile
---
Emperor.java
package zoo;
public final class Emperor {}
```
Even though the `Emperor` class is declared, it does not extend the `Penguin` class.
In a more advanced topic, *modules*, we will see *named modules*, which allow sealed 
classes an their direct subclasses in a different package, provided that they are in 
the same named module.

### Specifying the Subclass Modifier

While some types, like interfaces, have a certain number of implicit modifiers, sealed 
classes do not. *Every class that directly extends a sealed class must specify exactly* 
*one of the following three modifiers*: `final`, `sealed`, or `non-sealed`.

**A _final_ subclass**

```
public sealed class Antelope permits Gazelle {}
---
public final class Gazelle extends Antelope {}
--
public class George extends Gazelle {}    // does not compile
```
Just as with a regular class, the `final` modifier prevents the subclass `Gazelle` from 
being extended further.

**A _sealed_ subclass**

```
public sealed class Mammal pertmits Equine {}
---
public sealed class Equine extends Mammal pertmits Zebra {}
---
public final class Zebra extends Equine {} 
```
Despite allowing indirect subclasses not named in `Mammal`, the list of classes that 
can inherit `Mammal`is still fixed. If we have a reference to a `Mammal` object, it must 
be a `Mammal`, `Equine`, or `Zebra`.

**A _non-sealed_ subclass**

The `non-sealed` modifier is used to open a `sealed` parent class to potentially unknown 
subclasses. Let's modify a previous example:
```
public sealed class Wolf permits Timber {}
---
public non-sealed class Timber extends Wolf {}
---
public class MyWolf extends Timber {}
```
In this example we are able to create an indirect subclass of `Wolf`, called `MyWolf`, 
not named in the declaration of `Wolf`. Also notice that `MyWolf` is not final, so it 
my be extended by any subclass, such as `MyFurryWolf`.

**Omitting the _permits_ clause**

If we have a single file with two top-level classes defined inside of it, the `permits` 
clause could be omitted. The two declaration bellow are equivalent
```
// Snake.java
public sealed class Snake permits Cobra {}
final class Cobra extends Snake {}
---
// Snake.java
public sealed class Snake {}
public class Cobra extends Snake {}
```
It these class were in separate files, this code would not compile.
In nested subclasses, is the same rule:
```
// Snake.java
public sealed class Snake {
  final class Cobra extends Snake {}
}
```
While is allowed to omit the `permits` clause in certain cases, like the above, it is 
not a good practice. In nested classes the syntax is this:
```
public sealed class Snake permits Snake.Cobra {
  final class Cobra extends Snake {}
}
```
If all subclasses are nested, is recommended to omit the the `pertmits` clause.


### Sealing Interfaces

Interfaces can also be sealed in the same way to classes, and many of the same rules 
apply. For example, the *sealed interface* must appear in the same package of named 
module as the classes or interfaces that directly extend or implement it.

One distinct feature of a sealed interface is that the `permits` list can apply to a 
class that implements the interface or an interface that extends the interface.
```
// sealed interface
public sealed interface Swims permits Duck, Swan, Floats {}
---
// classes permitted to implement sealed interface
public final class Duck implements Swims {}
---
public final class Swan implements Swims {}
---
// interface permitted to extend sealed interface
public non-sealed interface Floats extends Swims {}
```

### Reviewing Sealed Class Rules

**Sealed Class Rules**
- sealed classes are declare with the `sealed` and `permits` modifiers.
- sealed classes must be declared in the same package or named module as their direct 
  subclasses.
- direct subclasses of sealed classes must be marked `final`, `sealed`, or `non-sealed`.
- the `permits` clause is optional if the sealed class and its direct subclasses are 
  declared in the same file or the subclasses are nested the sealed class.
- interfaces can be sealed to limit the classes that implements them or the interfaces 
  that extend them.
[back to start of section](#sealing-classes)

## Encapsulating Data with Records
[back to top](#chapter-7-beyond-classes)

The best new Java type was saved for last! *Records* are exciting because they remove a 
ton of boilerplate code.

### Understanding Encapsulation

A simple *POJO* to hold data:
```
public class Crane {
  int numberEggs;
  String name;
  public Crane(int numberEggs, String name) {
    this.numberEggs = numberEggs;
    this.name = name;
  }
}
```
Since the fields have *package access*, some code outside of the class could change the 
data, causing inconsistencies. Obvious this is not good, so we need a more robust 
implementation of `Crane`. *Encapsulation* is a way to protect class members by 
restricting access to them. In Java this is done declaring all instance variables 
private and creating access methods to them.

An encapsulated POJO class:
```
public final class Crane {
  private final int numberEggs;
  private final String name;
  public Crane(int numberEggs, String name) {
    if (numberEggs >= 0) this.numberEggs = numberEggs;    // guard condition
    else throw new IllegalArgumentException();
    this.name = name;
  }
  public int getNumberEggs() {
    return numberEggs;
  }
  public String getName() {
    return name;
  }
}
```
Since the attributes are private and final, and there are no setter, this class is 
creates immutables objects (additional rules on chapter 6).

### Applying Records

The `Crane` class is 15 lines long and could be written much more succinctly as shown 
below: 
```
public record Crane(int numberEggs, String name) {}
```
A *record* is a special type of data-oriented class in which the compiler inserts 
boilerplate code for us. In fact, the compiler insert much more than the 14 lines 
written earlier. As bonus, the compiler inserts these useful methods: `equals()`, 
`hashCode()`, and `toString()`.

Creating an instance of a `Crane` *record* and printing some fields is simple:
```
var mom = new Crane(4, "Cammy");
System.out.println(mom.numberEggs());    // 4
System.out.println(mon.name());    // Cammy
```
Behind the scenes, the compiler creates a *constructor* for us with the parameters in 
the same order in which they appear in the `record` declaration. Omitting or changing 
the type order will lead to a compiler error:
```
var mom1 = new Crane("Cammy", 4);    // does not compile
var mom2 = new Crane("Cammy");    // does not compile
```
For each field, the compiler also creates an *accessor method* using the *field name* 
and a set of parentheses.

**Member Automatically Added to Records**
- **constructor**: a constructor with the parameters in the same order as the record declaration
- **accessor method**: one accessor for each field
- **equals()**: a method to compare two elements that returns true if each field is equal in terms of `equals()`
- **hashCode()**: a consistent `hashCode()` method using all fields
- **toString()**: a `toString()` implementation that prints each field of the record in a easy-to-read format

Examples of use of a *record*.
```
var father = new Crane(0, "Craig");
System.out.println(father);    // Crane[numberEggs=0, name=Craig]

var copy = new Crane(0, "Craig");
System.out.println(copy);    // Crane[numberEggs=0, name=Craig]
System.out.println(father.equals(copy));    // true
System.out.println(father.hashCode() + ", " + copy.hashCode());    // 1007, 1007
```
[back to start of section](#encapsulating-data-with-records)

### Understanding Record Immutability

Since records don't have setters and every field is inherently final and cannot be 
modified after it has been written in the constructor. In order to "modify" a record, 
we have to make a new object and copy all the data that we want to preserve.
```
var cousin = new Crane(3, "Jenny");
var friend = new Crane(cousin.numberEggs(), "Janeice");
```

Just as interfaces are implicitly `abstract`, records are also implicitly `final`. The 
`final` modifier is optional but assumed.
```
public final record Crane(int numberEggs, String name) {}
```
Like enums, this means that we can't extend or inherit a record. We could, if we want, 
implement an interface:
```
public interface Bird {}
public record Crane(int numberEggs, String name) implements Bird{}
```

In this case, of course, we will have to implement all methods required by the interface.

### Declaring Constructors

We can add constructor to *records*, both, the **long** and **compact** constructors.

**The Long Constructor**

We can just declare the constructor the compiler normally inserts automatically, also 
known as *long constructor*:
```
public record Crane(int numberEggs, String name) {
  public Crane(int numberEggs, String name) {
    if (numberEggs < 0 || name.length() < 2) throw new IllegalArgumentException();
    this.numberEggs = numberEggs;
    this.name = name;
  }
}
```
The compiler will not insert a constructor if we define on with the same list of 
parameters in the same order. Since each field is final, the constructor must set every 
field.

While being able to declare a constructor is a nice feature of *records*, it's also 
problematic. If we have 20 fields, we'll need to declare assignments for every one, 
introducing the boilerplate we sought to remove.

**Compact Constructors**

A *compact constructor* is a special type of constructor used for records to process 
validation and transformations succinctly. It takes no parameters and implicitly sets 
all fields.
```
public record Crane(int numberEggs, String name) {
  public Crane {  // compact constructor (no parameters)
    // all fields are available here
    if (numberEggs < 0 || name.length() < 2) throw new IllegalArgumentException();
    name = name.toUpperCase();    // change the input parameter
  }
  // long constructor implicitly called at the end of compact constructor
}
```
Great! Now we can check the values we want, and we don't have to list all the 
constructor parameters and trivial assignments. Java will execute the full constructor 
after the compact constructor.

While compact constructors can modify constructor parameters, they cannot modify the 
fields of the record. This will not compile:
```
public record Crane(int numberEggs, String name) {
  public Crane {
    this.numberEggs = 10;    // does not compile
  }
}
```
At this point, the instance is not create yet and, beyond this, it is immutable. Removing 
the `this` reference allows the code to compile, as the constructor parameters is the one 
been modified instead.

**Is highly recommended that we stick with the compact form of the constructor.**


### Overloaded Constructors

We can also create overloaded constructor that use a completely different list of 
parameters. They are more closely related to the long constructor.
```
public record Crane(int numberEggs, String name) {
  public Crane(String firstName, String lastName) {
    this(0, firstName + " " + lastName);
  }
}
```
*The first line of an overloaded constructor in a record must be an explicit call to* 
*another constructor via `this()`*. If there are no other constructor, the long constructor 
must be called. In contrast of the constructor in a class, where calling `super()` or 
`this()` was often optional in constructor declarations.

Note also that all the transformation must occur in the first line. After the first line, 
all of the fields will already be assigned, and the object is immutable.
```
public record Crane(int numberEggs, String name) {
  public Crane(int numberEggs, String firstName, String lastName) {
    this(numberEggs + 1, firstName + " " + lastName);
    numberEggs = 10;    // no effect, since this is modifying the parameter
    this.numberEggs = 20;    // does not compile
  }
}
```

### Customizing Records

Until now we're focused on features that are more commonly used. Records also support 
many of the same features as a class, here are the summary of records features:
- overloaded and compact constructors
- instance methods including overriding any provided methods (accessors, equals(), 
  hashCode(), toString())
- nested classes, interfaces, annotation, enum, and records

Here an example of overriding methods:
```
public record Crane(int numberEggs, String name) {
  @Override public int numberEggs() { return 10; }
  @Override public String toString() { return name; }
}
```

While we can add methods, static fields, and other data types, we cannot add instance 
fields out the record declaration, even if they are private. Doing so defeats the purpose 
of using a record and could break immutability.
```
public record Crane(int numberEggs, String name) {
  private static int type = 10;
  public int size;    // does not compile
  private boolean friendly;    // does not compile
}
```

While it's as useful feature that records support many of the same members as a class, we 
need to try to keep them simple, since the more complicated they get, the less usable 
they become.
[back to start of section](#encapsulating-data-with-records)

## Creating Nested Classes

A *nested class*  is a class that is defined within another class. A nested class can 
come in one of four flavors:
- **inner class**: a non-static type defined at the member level of a class.
- **static nested class**: a static type defined at the member level of a class.
- **local class**: a class defined within a method body.
- **anonymous class**: a special case of a local class that does not have a name.

There area many benefits of using nested classes. They can define helper classes and 
restrict them to the containing class, thereby improving encapsulation. They can make it 
easy to create a class that will be used in only one place. They can even make code 
cleaner and easier to read.

When used improperly, though, nested classes can sometime make the code harder to read. 
They also tend to tightly couple the enclosing and inner class, but there may be cases 
where we want to use the inner class by itself. In this case, we should move the inner 
class out into a separate top-level class.


### Declaring an Inner Class

An *inner class*, also called a *member inner class*, is a non-static type defined at the 
member level of a class (the same level as the methods, variables, constructor). Because 
they are not top-level type, they can use any of the four access levels, not just public 
and package access.

Inner classes have the following properties:
- can be declared public, protected, package, or private
- can extend a class and implement interfaces
- can be marked abstract or final
- can access members of the outer class, including private members

The last property means that the inner class can access variables in the outer class 
without doing anything special. Here an illustrative example with a complicated way to 
print "Hi" three times.
```
01: public class Home {
02:   private String greeting = "Hi";    // outer class instance variable
03:
04:   protected class Room {    // inner class declaration
05:     public int repeat = 3;
06:     public void enter() {
07:       for (int i = 0; i < repeat; i++) greet(greeting);
08:     }
09:     private static void greet(String message) {
10:       System.out.println(message); 
11:     }
12:   }  // end of Room class
13:
14:   public void enterRoom() {    // instance method in outer class
15:     var room = new Room();    // inner class instance
16:     room.enter();
17:   }
18:   public static void main(String[] args) {
19:     var home = new Home();    // create the outer class instance
20:     home.enterRoom();
21:   }
22: }
```

An inner class declaration looks just like a stand-alone class declaration except that it 
happens to be located inside another class. Line 7 shows that the inner class just refers 
to `greeting` as if it were defined in the `Room` class. But in fact, it is *available*, 
since it is a member of the outer class like `Room` is also a member of the outer class.

Since an inner class is not static, it hat to be called using an instance of the outer 
class. That means we have to create two objects. Line 19 create the outer `Home` object, 
while line 15 creates the inner `Room` object. It's important to notice that line 15 
doesn't require an explicit instance of `Home` because it is an instance method within 
`Home`. This works because `enterRoom()` is an instance method within the `Home` class. 
Both `Room` and `enterRoom()` are members of `Home`.

In line 9  was declared an static method in the inner class, this is a new feature 
include in Java since version 16. All four type of nested classes can now define static 
variables and methods.

**Instantiating an Instance of an Inner Class**

There is another way to instantiate `Room` that looks odd at first. This syntax isn't 
used often enough to get used to it:
```
20:   public static void main(String[] args) {
21:     var home = new Home();
22:     Room room = home.new Room();    // create the inner class instance
23:     room.enter();
24:   }
```
At lines 21 and 22, we need an instance of `Home` to create a `Room`. We can't just call 
`new Room()` inside the static `main()` method, because Java won't know which instance of 
`Home` it is associated with. Java solves this by calling `new` as if it were a method 
on the `room` variable. So, we can shorten lines 21-23 to a single line:
```
new Home().new Room().enter();     // yes, is ugly, but is possible
```

When compiled, the Home.java file, two class files will be created: one for `Home` and 
other for `Room`, named `Home.class` and `Home$Room.class`, respectively.

### Referencing Members of an Inner Class

Inner class can have the same variable names as outer classes, making scope a little 
tricky. There is a special way of calling `this` to say which variable we want to access.
Although the code below is common in a Java certification exam, we do not have to do this 
in real production code.
```
01: public class A {
02:   private int x 10;
03:   class B {
04:     private int x = 20;
05:     class C {
06:       private in x = 30;
07:       public void allTheX() {
08:         System.out.println(x);    // 30
09:         System.out.println(this.x);    // 30
10:         System.out.println(B.this.x);    // 20
11:         System.out.println(A.this.x);    // 10
11:       }
12:     }
13:   }
14:   public static void main(String[] args) {
15:     A a = new A();
16:     A.B v = a.new B();
17:     A.B.C c = b.new C();
18:     c.allTheX();
19:   }
20: }
```
This kind of code make anyone cringe. It has two nested classes. Line 15 instantiate the 
outermost one. Line 16 uses the awkward syntax to instantiate a `B`. Note that the type 
is `A.B`. We could have written `B` as the type because that is available at the member 
level of `A`. Java knows where to look for it. On line 17, we instantiate a `C`. This 
time, the `A.B.C` type is necessary to specify. `C` is too deep for Java know where to 
look for. Then line 18 call a method on the instance variable `c`.

Lines 8 and 9 are the type of code that we are used to seeing. They refer to the instance 
variable on the current class, the one declared on line 6. Line 10 uses `this` in a 
special way, we still want an instance variable. But this time, we want the one on the 
`B` class, which is the variable on line 4. Line 11 does the same thing for class `A`, 
getting the variable from line 2;


### Creating a _static_ Nested Class

A *static nested class* is a static type defined at the member level. Unlike an inner 
class, a static nested class can be instantiated without an instance of the enclosing 
class. The trade-off, though, is that it can't access instance variables or methods 
declared in the outer class. It is like a top-level class except for the following:

- the nesting creates a namespace because the enclosing class name must be used to refer to it.
- it can additionally be marked private or protected.
- the enclosing class can refer to the fields and methods of the static nested class

``` 
public class Park {
  static class Ride {
    private int price = 6;
  }
  public static void main(String[] args) {
    var ride = new Ride();
    System.out.println(ride.price);
  }
}
```
The nested class is initialized inside the main without the need of an instance of `Park` 
and we are allowed to access private instance variables of `Ride`.

### Writing a Local Class

A *local class* is a nested class defined within a method. Like local variables, a local 
class declaration does not exist until the method is invoked, and it goes out of scope 
when the method returns. The instances can be created only inside the method and can be 
returned from the method. Local classes have the following properties:

- the do not have an access modifier
- the can be declared final or abstract
- the have access to all fields and methods of the enclosing class
- they can access final and effectively final local variables

An example with a complicated way to multiply two numbers:
```
01: public class PrintNumbers {
02:   private int length = 5;
03:   public void calculate() {
04:     final int width = 20;
05:     class Calculator {
06:       public void multiply() {
07:         System.out.print(length * width);
08:       }
09:     }
10:     var calculator = new Calculator();
11:     calculator.multiply();
12:   }  // end of method
13:   public static void main(String[] args) {
14:     var printer = new PrintNumbers();
15:     print.calculate();    // 100
16:   }
17: }
```

Lines 5 - 9 are the local class. That class's scope ends on line 12, where the method 
ends. Line 7 refers to an instance variable and a final local variable, so both variable
references are allowed from within the local class.

Local classes can only access *final*  or *effectively final* variables. If the value of
one of the variable that a local class access could change, than the code will not 
compile. This occurs because, when compiled, there will be two files, and Java will not 
have a way to know the value of the variable. If the variable is *final* or *effectively* 
*final*, the compiler will pass a reference for the local class and thus, it can access 
the value of the variable.

### Defining an Anonymous Class

An *anonymous class* is a specialized form of a local class that does ot have a name. It 
is declared and instantiated all in one statement using the `new` keyword, a type name 
with parentheses, and a set of braces `{}`. Anonymous classes must extend an existing 
class or implement an existing interface. They are useful when we have a short 
implementation that will not be used anywhere else.

```
01: public class ZooGiftShop {
02:   abstract class SaleTodayOnly {
03:    abstract int dollarsOff();
04:   }
05:   public int admission(int basePrice) {
06:     SaleTodayOnly sale = new SaleTodayOnly() {
07:       int dollarsOff() { return 3; }
08:     };
09:     return basePrice - sale.dollarsOff();
10:   }
11: }
```
Lines 2 - 4 define an abstract class. Lines 6 - 8 define the anonymous class. Note that 
`SaleTodayOnly` is abstract and we create a object that type, this is possible because we 
provide the class body right there -- anonymously. Since we are creating a new variable, 
`sale`, we terminate on line 8 with a semicolon.

We can define anonymous classes outside a method body. The following code may look like 
we are instantiating an interface as an object, but the `{}` after the interface name 
indicates that this is an anonymous class implementing the interface:
```
public class Gorilla {
  interface Climb {}
  Climb climbing = new Climb() {};
}
```

#### Anonymous Classes and Lambda Expressions

Prior Java 8, anonymous classes were frequently used for asynchronous tasks and event 
handlers. The following shows an anonymous class used as an event handler in a JavaFX 
application:
```
Button redButton = new Button();
redButton.setOnAction( new EventHandler<ActionEvent>() {
  public void handle(ActionEvent e){
    System.out.println("Red button pressed");
  }
})
```

Since the introduction of lambda expressions, anonymous classes are now often replaced 
with much shorter implementations:
```
Button redButton = new button();
redButton.setOnAction(e -> System.out.println("Red button pressed!"));
```
Lambda expressions will be cover in detail in the next chapter.

### Reviewing Nested Classes

**Modifiers in nested classes**

    +---------------------+-------------+---------------+-------------+-----------------+
    | Permitted modifiers | Inner class | static nested | Local class | Anonymous class |
    |                     |             | class         |             |                 |
    +---------------------+-------------+---------------+-------------+-----------------+
    | access modifiers    |  All        |  All          |  None       |  None           |
    +---------------------+-------------+---------------+-------------+-----------------+
    | abstract            |  Yes        |  Yes          |  Yes        |  No             |
    +---------------------+-------------+---------------+-------------+-----------------+
    | final               |  yes        |  Yes          |  Yes        |  No             |
    +---------------------+-------------+---------------+-------------+-----------------+

[back to to](#chapter-7-beyond-classes)


## Understanding Polymorphism

Polymorphism is the property of an object to take on many different forms, that is, a 
Java object may be accessed using:
- a reference with the same type as the object
- a reference that is a superclass of the object
- a reference that defines an interface the object implements or inherits

Furthermore, a cast is not required if the object is being reassigned to a supertype or 
interface of the object.

```
public class Primate {
  public boolean hasHair() {
    return true;
  }
}
---
public interface HasTail {
  public abstract boolean isTailStriped();
}
---
public class Lemur extends Primate implements HasTail {
  public boolean isTailStriped() {
    return false;
  }
  public int age = 10;
  public static void main(String[] args) {
    Lemur lemur = new Lemur();
    System.out.println(lemur.age);

    HasTail hasTail = lemur;
    System.out.println(hasTail.isTailStriped());
    
    Primate primate = lemur;
    System.out.println(primate.hasHair()); 
  }
}
```
The most important thing to note about this examples is that only one object, `Lemur`, is 
created. Polymorphism enables an instance of `Lemur` to be reassigned or passed to a 
method using of its super-types, such as `Primate` or `HasTail`.

Once the object has been assigned to a new reference type, only the methods and variables 
available to that reference type are callable on the object without an explicit cast.

### Object vs. Reference

In Java, all objects are accessed by reference and we never have direct access to the 
object itself. Conceptually the object exist in the memory and regardless ot the type of 
the reference we have for the object in memory, the object itself does't change.
```
Lemur lemur = new Lemur();
Object lemurAsObject = lemur;
```
Even though the `Lemur` object has been assigned to a reference with a different type, 
the object itself has not changed and still exists as a `Lemur` object in memory. What 
has changed is our ability to access methods within the `Lemur` class with `lemurAsObject` 
reference.

This could be summarized in this two principles:
- the type of the object determines which properties exist within the object in memory.
- the type of the reference to the object determines which methods and variables are available.

### Casting Objects

Once we changed the reference type we lost access to more specific members defined in the 
subclass that still exist within the object. We can reclaim those references by casting 
the object back to the specific subclass it came from:
```
Lemur lemur = new Lemur();

Primate primate = lemur;    // implicit cast to supertype

Lemur lemur2 = (Lemur)primate;    // explicit cast to subtype

Lemur lemur3 = primate;   // does not compile (missing cast)
``` 
Since `Lemur` is a subtype of `Primate`, we can cast the object to the supertype implicit, 
that is, without the cast operator. When we cast it back to a `Lemur` object using an 
explicit cast, gaining access to all of the methods and field in the `Lemur` class. The 
last line does not compile because an explicit cast is required. 

We could cast the `Primate` object to other subtype than `Lemur` provided that the other 
subtype is compatible with `Lemur`.

### Disallowed Casts

Consider this example:
```
public class Bird {}
---
public class Fish {
  public static void main(String[] args) {
    Fish fish = new Fish();
    Bird bird = (Bird)fish;    // does not compile
  }
}
``` 
In this example, the classes `Fish` and `Bird` are not relate through any class hierarchy 
that the compiler is aware of; therefore, the code will not compile. While the both 
extend `Object` implicitly, they are considered unrelated types, since one cannot be a 
subtype of the other.

### Casting Interfaces

While the compiler can enforce rules about casting to unrelated types for classes, it 
cannot always do the same for interfaces. Instances support multiple inheritance, which 
limits what the compiler can reason about them. When holding a reference toa particular 
class, the compiler doesn't know which specific subtype it is holding.

The compiler does not allow a cast from an interface reference to an object reference if 
the object type cannot possibly implement the interface, such as if the class is marked 
final.

### The _instanceof_ Operator

The *instanceof* operator can be used to check whether an object belongs to a particular 
class or interface and to prevent a ClassCastException at runtime. Consider this:
```
class Rodent {}

public class Capybara extends Rodent {
  public static void main(String[] args) {
    Rodent rodent = new Rodent();
    var capybara = (Capybara)rodent;    // ClassCastException
  }
}
```
To prevent the exception we need to do this:
```
if (rodent instanceof Capybara c) {
  // so something with c
}
```

Note that the compiler does not allow `instanceof` operator be used with unrelated types.
```
public class Bird {}

public class Fish {
  public static void main(String[] args) {
    Fish fish = new Fish();
    if (fish instanceof Bird b) {    // does not compile
      // do something with b
    }
  }
}
```

### Polymorphism and Method Overriding

In Java, polymorphism states that when we override a method, we replace all calls to it, 
even those defined in the parent class. Example:
```
class Penguin {
  public int getHeight() { return 3; }
  public voi printInfo() {
    System.out.print(this.getHeight());
  }
}
---
public class EmperorPenguin extends Penguin {
  public int getHeight() { return 8; }
  public static void main(String[] args){
    new EmperorPenguin().printInfo();
  }
}
```
The `getHeight()` is overridden in the subclass, meaning all call to it are replaced at 
runtime. Despite `printInfo()` being defined in the `Penguin` class, calling `getHeight()` 
on the object *calls the method associated with the precise object in memory*, not the 
current reference type where it is called.

Polymorphism allows us to create complex inheritance models with subclasses that have 
their own custom implementation of overridden methods. It also means the parent class 
does not need to be updated to use the custom or overridden method. If the method is 
properly overridden, then the overridden version will be used in all places that it is 
called.

### Overriding vs. Hiding Members

Hiding members is not a form of polymorphism since the methods and variables maintain 
their individual properties. Unlike method overriding, hiding members is very sensitive 
to the reference type and location where the member is being used.
```
class Penguin {
  public static int getHeight() { return 3; }
  public void printInfo() {
    System.out.println(this.getHeight());
  }
}
---
public class CrestedPenguin extends Penguin {
  public static int getHeight() { return 8; }
  public static void main(String[] args) {
    new CreatedPenguin().printInfo();
  }
}
```
The `CrestedPenguin` example is nearly identical to the previous `EmperorPenguin` example, 
whit the difference that it prints 3 instead of 8. The `getHeight()` method is `static` 
and is therefore *hidden*, not *overridden*. The result is that calling `getHeight() in 
`CrestedPenguin` returns a different value than calling it in `Penguin`, even if the 
underlying object it the same.

About the fact that we used `this` to access a `static` method in `this.getHeight()`, 
while permitted to use an instance reference to access a static variable or method, doing 
so is often discouraged. The compiler will warn us when we access static members in a 
non-static way. In this case,, the `this` reference had no impact on the program output.

Besides the location, the reference type can also determine the value we get when we are 
working with hidden members. Let's look to a more complex example.
```
class Marsupial {
  protected int age = 2;
  public static boolean isBiped() {
    return false;
  }
}
---
public class Kangaroo extends Marsupial {
  protected int age = 6;
  public static boolean isBiped() {
    return true;
  }
  public static void main(String[] args) {
    Kangaroo joey = new Kangaroo();
    Marsupial moey = joey;
    System.out.println(joey.isBiped());
    System.out.println(moey.isBiped());
    System.out.println(joey.age);
    System.out.println(moey.age);
  }
}
```
In this example, *only one object (of type Kangaroo)* is created and stored in memory. 
Since static methods can only be hidden, nor overridden, Java uses the reference type to 
determine which version of `isBiped()` should be called, result in `joey.isBiped()` 
printing `true` and `moey.isBiped()` printing `false`. The total output is this:
```
true
false
6
2
```
Likewise, the `age` variable is hidden, not overridden, so the reference type is used to 
determine which value to output. This result in `joey.age` returning 6 and `moey.age` 
returning 2.


## Hide Members: a bad practice

Although Java allows us to hide variables and static methods, it is considered an 
extremely poor coding practice. As we saw in the previous example, the value of the 
variable or method can change depending on what reference is used, making the code 
very confusing, difficult to follow, and challenging for others to maintain.

When we are defining a new variable or static method in a child class, it is considered 
good coding practice to select a name that is not already used by an inherited member. 
Redeclaring private methods and variables is considered less problematic, though, because 
the ching class does not have access to the variable in the parent class to begin with.
[back to top](#chapter-7-beyond-classes)