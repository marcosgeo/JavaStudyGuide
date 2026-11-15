# Chapter 5 - Methods

### OCP exam objectives covered in this chapter
 - Utilizing Java Object-Oriented Approach
 - create classes and records, and define and use instance and static fields and methods, constructors, and instance and static initializers
 - implement overloading, including var-arg methods

## Designing Methods

```
public   final   void   nap (int  minutes)   throws   InterruptedException  {

  // take a nap

}
"""
public: access modifier (optional)
final: optional specifier (optional)
void: return type (mandatory)
nap: method name (mandatory)
int minutes: paramters list (mandatory)
nap(int minutes): method signature (mandatory, can be empty)
throws: exception (optional)
"""
```

### Access modifiers

An access modifier determines what classes a method can be accessed from.
 - **private** determines that the method cab be called on from within the same class.
 - *package access*, **no specific keyword**, the method can be called only from a class int the same package.
 - **protected** indicated that the method can be called only from a class in the same package or subclass.
 - **public** indicates that the method can be called from anywhere.

### Optional specifiers
 
There are a number of optional specifiers for methods. Unlike with access modifiers, 
we can have multiple specifiers in the same method (although not all combinations are 
valid) and we can specify them in any order.

 - `static`       indicates the method is a member of the shared class
 - `abstract`     used in an abstract class or interface when method body is excluded
 - `final`        specifies that the method may not be overridden in a subclass
 - `default`      used in interface to provide a default implementation of the method
 - `synchronized` use with multi-threaded code
 - `native`       used when interacting with code written in another language
 - `strictfp`     used for making floating-point calculation portable


Rule: **access modifiers** and **optional specifiers** can be listed in any order, but 
once the return type is specified, the rest of the parts of the method are written in a 
specific order: name, parameters list, exception list, body.

```
public class Exercise {
  public void bike1() {}
  public final void bike2() {}
  public static final void bike3() {}
  public final static void bike4() {}
  public void final bike5() {}  // does not compile
  final public void bike6() {}
}
```

### Parameters List & Method Signature

The names of the parameters in the method signature are not used as part of a method 
signature. The parameters list is about the **types** of the parameters and their 
**order**. 
```
public class Trip {
  public void visitZoo(String name, int waitTime){}
  public void visitZoo(String attraction, int rainFall) {}   // does not compile
}

public class Trip {
  public void visitZoo(String name, int waitTime){}
  public void visitZoo(int rainFall, String attraction) {}  // ok
}
```

### Exception List

While the list of exceptions is optional, it may be required by the compiler, depending 
on what appears inside the method body. Details in Chapter 11.

### Method Body

A method body is simply a code block, it has braces that contains zero or more java 
statements.


## Declaring Local and Instance Variables

Local variables are those defined with a method or block, while instance variables are 
those that are defined as a member of a class.

```
public class Lion {
  int hunger = 4;  // instance variable
  
  public int feedZooAnimals(){
    int snack = 10;  // local variable
    if (snack > 4) {
      long dinnerTime = snack++;
      hunger--;
    }
    return snack;
  }
}
```

In the `Lion` class, `snack` and `dinnerTime` are local variables only accessible 
within their respective code blocks, while `hunger` is an instance variable and 
created in every object of the `Lion` class.

The object or value returned by a method may be available outside the method, but the 
variable reference `snack` is gone. *All local variable references are destroyed after 
the block is executed, but the objects they point to may still be accessible*.

### Local Variable Modifiers

There's only on modifiers that can be applied to a local variable: `final`. When 
writing methods, we may want to set a variable that does not change during the course 
of the method. We do this putting the modifier `final` before the type of the variable.

```
public void sooAnimalCheckup(boolean isWeekend) {
  final int rest;
  if (isWeekend) rest = 5; else rest = 20;
  system.out.print(rest);
  
  final var giraffe = new Animal();
  final int[] friends = new int[5];
  
  rest = 10;    // does not compile
  giraffe = new Animal();    // does not compile
  friends = null;
}
```
We do not need to assign a value when a final variable is declared, this can be done 
later. The rule is on that if must be assigned a value before is can be used. Note 
that using the final modifier does not mean that we can't modify the data, **the final 
modifier only refers to the variable reference, the contents can be freely 
modified** (assuming the object isn't immutable). 

```
public void zooAnimalCheckup() {
  final int rest = 5;
  final Animal firaffe = new Animal();
  final int[] friends = new int[5];
  
  firaffe.setname("Geoger");
  friends[2] = 2;
}
```

The `rest` variable **is a primitive**, so it's just a value that cant't be modified. 
On the other hand, the contents of `giraffe` and `friends` variables can be freely 
modified, provided the variables are not reassigned.


### Instance Variables Modifiers

Instance variables can use access modifiers such as: `private`, `package`, 
`protected`, and `public`. Remember that package access is indicated by the lack of 
any modifier. Instance variables can also use optional specifiers:
 - **final**    specifies that the instance variable must be initialized with each instance of the class exactly once.
 - **volatile**    instructs the JVM that the value in this variable may be modified by others threads (chp 13).
 - **transient**    used to indicate that an instance variable should not be serialized with the class (chp 14).
 
If an instance variable is marked final, then it must be assigned a value when it is 
declared or when the object is instantiated and it cannot be assigned a value more 
than once.

```
public class PolarBear {
  final int age = 10;
  final int fishEaten;
  final String name;
  
  {fishEaten = 10;}
  
  public PolarBear() {
    name = "Robert";
  }
}
```

The `age` variable is given a value when it is declared, while the `fishEaten` 
variable is assigned a value in an instance initializer. The `name` variable is given 
a value in the no-argument constructor.

**The compiler does not apply a default value to final variables. A final instance or 
final static variable must receive a value when it is declared or as part of 
initialization**.


## Working with Varargs

A method may use a `varargs` parameter as if it is an array. Creating a method with a 
`vargargs` parameter is a bit more complicated. In fact, calling such a method may not 
use an array at all.

### Creating methods with varargs

1. a method can have at most one varargs
2. if a method contains a varargs paramter, it must be the last parameter in the list

```
public class VasitAttractions {
  public void walk1(int... steps){}
  public void walk2(int start, int... steps){}
  public void walk3(int... steps, int start){}      // does not compile
  public void walk4(int... start, int... steps){}   // does not compile
}
```

### Calling methods with vargars

When calling a method with `varargs` parameter, we have a choice: pass in an `array` 
or can list the elements of the array and let Java create it for us.

```
// pass an array
int[] data = new int[] {1, 2, 3};
walk1(data);

// pass a list of values
walk(1, 2, 3);
```
Regardless of which one we use to call the method, the method will receive an array 
containing the elements. The example below reinforce this idea:

```
public void wailk1(int... steps) {
  int[] steps2 = steps;     // unnecessary, but shows that steps is of type int[]
  System.out.println(steps2.length);
}
```

If we call `walk1()` with no parameters, Java will create the variable `steps` inside 
the method with length 0.


### Using varargs with other method parameters

```
1. jshell> public class DogWalker {
2.    ...>   public static void walkDog(int start, int... steps) {
3.    ...>     System.out.println(steps.length);
4.    ...>   }
5.    ...>   public static void main(String[] args) {
6.    ...>     walkDog(1);             // 0
7.    ...>     walkDog(1, 2);          // 1
8.    ...>     walkDog(1, 2, 3);       // 2
9.    ...>     walkDog(1, new int[]{4, 5});  // 2
10.   ...>   }
11.   ...> }
|  created class DogWalker
```
Line 6 passes 1 as start but nothing else. This means Java creates an array of length 
0 for steps. Line 7 passes 1 as start and one more value. Java converts this one 
value to an array of length 1. Line 8 passes 1 as start and tow more values. Java 
converts these two values to an array of length 2. Line 9 passes 1 as start and an 
array of length 2 directly as steps.


## Applying Access Modifiers

There are four access modifiers in Java: private, package, protected, and public. We 
are going to discuss them in order from most restrictive to least restrictive:
 - **private**: only accessible within the same class.
 - *Package access*: private plus other members of the same package. Sometime referred to as package-private or default access.
 - **protected**: package access plus access within sub-classes.
 - **public**: protected plus classes in the other packages.
 
### Private Access
 
Consider the class diagram below to explore private and package access. The external 
boxes are packages and the inside boxes are classes.

    +----------------------------------------------------+
    | pond.duck                                          |
    |  +----------------+          +-----------------+   |
    |  |                |          |                 |   |
    |  |   FatherDuck   |          |   MotherDuck    |   |
    |  |                |          |                 |   |
    |  +----------------+          +-----------------+   |
    |                                                    |
    |  +----------------+          +-----------------+   |
    |  |                |          |                 |   |
    |  |  BadDuckling   |          |  GoodDuckling   |   |
    |  |                |          |                 |   |
    |  +----------------+          +-----------------+   |
    +----------------------------------------------------+

    +-------------------------+
    | pond.swan               |
    |  +---------------+      |
    |  |               |      |
    |  |   BadCygnet   |      |
    |  |               |      |
    |  +---------------+      |
    |                         |
    +-------------------------+

Based on this diagram this is a perfectly legal code because everything is one class:
```
package pond.duck;
public class FatherDuck {
  private String noise = "quack";
  private void quack() {
    System.out.print(noise);    // private access is ok
  }
}
```
Adding another class:
```
package pond.duck;
public class BadDuckling {
  public void makeNoise() {
    var duck = new FatherDuck();
    duck.quack();    // does not compile
    System.out.print(duck.noise);    // does not compile
  }
}
```
`BadDuckling` is trying to access an instance variable and a method it has no business 
touching. On `duck.quack()` tries to access a private method in another class. 
On `duck.noise` it tries to access a private instance variable in another class.

In this example, `FatherDuck` and `BadDuckling` are in separate files, but what if the 
were declared in the same file? Even then, the code will not compile as Java prevents 
access outside the class.


### Package Access

`MotherDuck` is more accommodating about what her ducklings can do. She allows class 
in the same package to access her members. When there is no access modifier, Java 
assumes package access.
```
package pond.duck;
public class MotherDuck {
  String noise = "quack";
  void quack() {
    System.out.print(noise);    // package access is ok
  }
}
```
`MotherDuck` can refer to `noise` and call `quack()`. After all, members in the same 
class are certainly in the same package. The big difference is that `MotherDuck` lets 
other classes in the same package access members, whereas `FatherDuck` does not (due 
to being private). `GoodDuckling` has a much better experience than `BadDuckling`:
```
package pond.duck;
public class GoodDucling {
  public void makeNoise() {
    var duck = new MotherDuck();
    duck.quack();     // package access is ok
    System.out.print(duck.noise);    // package access is ok
  }
}
```
`GoodDuckling` succeeds in learning to `quack()` and make noise by copying its mother. 
Notice that all the classes covered so far are in the same package, `pond.duck`. This 
allows packages to work.

In the same pond, there is a baby swan, *a cygnet*, that tries to learn to quack from 
`MotherDuck` as well:
```
package pond.swan;
import pond.duck.MotherDuck;    // import from another package
public class BadCygnet {
  public void makeNoise() {
    var duck = new MotherDuck();
    duck.quack();    // does not compile
    System.out.print(duck.noise);    // does not compile
  }
}
```
`MotherDuck` only allows lessons to other ducks by restricting access to the 
`pond.duck` package, since that, when there is no access modifier on a member, only 
classes in the same package can access the member.

### Protected Access

Protected access allows everything that package access does, and more. The `protected` 
access modifier adds the ability to access member of a parent class. In the following 
example, the "child" `ClownFish` class is a subclass of the "parent" `Fish` class, 
using the `extends` keyword to connect them:
```
public class Fish {}

public class ClownFish extends Fish {}
```
By extending a class, the subclass gains access to all protected and public members of 
the parent class, as if they (the member) were declared in the subclass. If the two 
classes are in the same package, the the subclass also gains access to all package 
members.


For the next classes, visit this figure along.

Figure 5.3: classes to show protected access

    +------------------------+          +---------------------------+
    | pond.shore             |          | pond.goose                |
    |  +------------------+  |          |   +-------------------+   |
    |  |                  |  |          |   |                   |   |
    |  |     `Bird`       |  |          |   |     `Gosling`     |   |
    |  |                  |  |          |   |   (extend Bird)   |   |
    |  +------------------+  |          |   +-------------------+   |
    |                        |          |                           |
    |  +------------------+  |          |   +-------------------+   |
    |  |                  |  |          |   |                   |   |
    |  |  `BirdWatcher`   |  |          |   |      `Goose`      |   |
    |  |                  |  |          |   |   (extend Bird)   |   |
    |  +------------------+  |          |   +-------------------+   |
    +------------------------+          +---------------------------+

    +-----------------------------+        +-------------------------+
    | pond.inland                 |        | pond.swan               |
    |  +-----------------------+  |        |   +-----------------+   |
    |  |                       |  |        |   |                 |   |
    |  | `BirdWatcherFromAfar` |  |        |   |     `Swan`      |   |
    |  |                       |  |        |   |  (extend Bird)  |   |
    |  +-----------------------+  |        |   +-----------------+   |
    +-----------------------------+        +-------------------------+

    +-------------------------+
    | pond.duck               |
    |  +-----------------+    |
    |  |                 |    |
    |  | `GooseWatcher`  |    |
    |  |                 |    |
    |  +-----------------+    |
    |                         |
    +-------------------------+


```
package pond.shore;
public class Bird {
  protected String text = "floating";
  protected void floatInWater() {
    System.out.print(text);    // protected access is ok
  }
}
```
A subclass
```
package pond.goose;    // different package than bird
import pond.shore.Bird;
public class Gosling extends Bird {    // Gosling is a subclass of Bird
  public void swin() {
    floatInWater();    // protected access is ok
    System.out.print(text);    // protected access is ok
  }
  public static void main(String[] args) {
    new Gosling().swin();
  }
}
```
This is a simple subclass. It *extends* the `Bird` class. Extending means creating a 
subclass that has access to any protected or public members of the parent class. 
Running this program print "floating" twice: once from calling `flotInWater()`, and 
once from the print statement `swin()`. Since `Gosling` is a subclass of `Bird`, it 
can access these members even though it is in a different package.

Protected also gives us access to everything that package access does, this means that 
a class in the same package as `Bird` can access its protected members.
```
package pond.shore;    // same package as Bird
public class BirdWatcher {
  public void watchBird() {
    Bird bird = new Bird();
    bird.floatInWater();    // protected access is ok
    System.out.print(bird.text);    // protected access is ok
  }
}
```
The definition of protected allows access to sub-classes and classes in the same 
package. This example uses the same package part of that definition.

Now something different.
```
package pond.inland;     // diferent package than bird
import pond.shore.Bird;
public class BirdWatcherFromAfar {    // not a subclass of Bird
  public void watchBird() {
    Bird bird = new Bird();
    bird.floatInWater();    // does not compile
    System.out.print(bird.text);    // does not compile
  }
}
```
`BirdWatcherFromAfar` is not in the same package as `Bird`, and it does not inherit 
from `Bird`. This means it is not allowed to access protected members of `Bird`. 
*Sub-classes and classes in the sames package are the only ones allowed to access 
protected members*.

There is one gotcha fore protected access. Consider this:
```
1:  packge pond.swan;    // different package than Bird
2:  import pond.shore.Bird;
3:  public class Swan extends Bird {    // Swan is a subclass of Bird
4:    public void swim() {
5:      floatInWater();    // protected access is ok
6:      System.out.print(text);    // protected acces is ok
7:    }
8:    public void helpOtherSwanSwin() {
9:      Swan other = new Swan();
10:     other.floatInWater();    // subclass access to superclass
11:     System.out.print(other.text);    // subclass acces to superclass
12:   }
13:   public void helpOtherBirdSwim() {
14:     Bird other = new Bird();
15:     other.floatInWater();    // does not compile
16:     Sytem.out.print(other.text);    // does not compile
17:   }
18: }
```
Lines 5 and 6 refers to protected members via inheritance.

Lines 10 and 11 because they refer to a `Swan` object that **inherits** from `Bird`. 
It is sort of a two-phase check.

Lines 15 and 16 do *not* compile. But the are almost exactly the same as lines 10 
and 11! But **they are an object from other class**, `Bird`, that resides **in 
another package**. These two lines of code are simply in the same file as the lines 10 
and 11, but they not *inherit* from `Bird`. The `extends` on line 3 applies to `Swan` 
objects. So, since the object `other` on line 14 creates an object `Bird` outside the 
package it is not allowed to him access protected member.

Another examples.
```
package pond.goose;
import pond.shore.Bird;
public class Goose extends Bird {
  public void helpGooseSwin() {
    Goose ohter = new Goose();
    other.floatInWater();
    System.out.print(ohter.text);
  }
  public void helpOtherGooseSwim() {
    Bird other = new Goose();
    other.floatInWater();    // does not compile
    System.out.print(other.text);    // does not compile
  }
}
```
The problem with the second method is that, although the object happens to be a 
`Goose`, it is stored in a `Bird` reference. We are not allowed to refer to members of 
the `Bird` class since we are no in the same package and the reference type is not a 
subclass of `Goose`. In essence, is a `Bird` object outside the package, so, **only 
the public methods are available here**.

What about this?
```
package pond.duck;
import pond.goose.Goose;
public class GooseWatcher {
  public void watch() {
    Goose goose = new Goose();
    goose.floatInWater();    // does not compile
  }
}
```
This code does not compile because we are not in the same package as `Goose` class. 
The `floatInWater()` method is declared in in `Bird`. `GooseWater` is not in the same 
package, and it does not extend `Bird`.


### Public Access

The last type of access modifier is easy: public means anyone can access the member 
from anywhere. Java will define "anywhere" in a way that it restrict "anywhere" 
outside of a "module" (chapter 12).

A class that public members:
```
package pond.duck;
public class DuckTeacher {
  public String name = "helpful";
  public void swinm() {
    System.out.print(name);    // public access is ok
  }
}
```

`DuckTeacher` allows access to any class that wants it. Now we can try it:
```
package pond.goose;
import pond.duck.DuckTeacher;
public class LostDuckling {
  public void swim() {
    var teacher new DuckTeacher();
    teacher swim();    // allowed
    System.out.print("Thanks " + teacher.name);    // allowed
  }
}
```

`LostDuckling` is able to refer to `swim()` and `name` on `DuckTeacher` because they 
are public. 


### Reviewing Access Modifiers

Table 5.4: A method in _______________ can access a ________________ member.

    +-------------------------------------------------------------------------------+
    |                                      | private | package | protected | public |
    +--------------------------------------+---------+---------+-----------+--------+
    | the same class                       |   Yes   |   Yes   |   Yes     |  Yes   |
    +--------------------------------------+---------+---------+-----------+--------+
    | another class in the same package    |   No    |   Yes   |   Yes     |  Yes   |
    +--------------------------------------+---------+---------+-----------+--------+
    | a subclass in a different package    |   No    |   No    |    Yes    |  Yes   |
    +--------------------------------------+---------+---------+-----------+--------+
    | an unrelated class in a diff package |   No    |   No    |    No     |  Yes   |
    +--------------------------------------+---------+---------+-----------+--------+


## Accessing *static* Data

When the `static` keyword is applied to a variable, method, or class, it belongs to 
the class rather than a specific instance. This keyword can also be applied to 
import statements.

### Designing *static* Methods and Variables

Methods and variables declared `static` don't require an instance of the class. They 
are shared among all users of the class.
```
public class Penguin {
  String name;
  static String nameOfTallestPenguin;
}
```
In this class, every `Penguin` instance has its own name, but only on `Penguin` among 
all the instances is the tallest. We access static members using the class name: 
`Penguin.nameOfTallestPenguin`.

**The main**
```
public class Koala {
  public static int count = 0;    // static variable
  public static void main(String[] args) {    // static method
    System.out.print(count);
  }
}
```
Since this class has a *main* method, JVM calls `Koala.main()` to start the program. 
We also can call this method.
```
public class KoalaTester {
  public static void main(String[] args) {
    Koala.main(new String[0]);    // call static method
  }
}
```
Here `KoalaTester` has the only purpose of start the `Koala` program and this shows us 
that the `main()` can be called like any other static methods.

In addition to `main()` methods, static members have two main purposes:
 - for *utility* or *helper* methods that don't require any object state.
 - for *state* that is shared by all instances of a class, like a counter. 

**A trick**
```
public class Snake {
  public static long hiss = 2;
}
System.out.println(Snake.hiss);  // 2

var s = new Snake();
System.out.print(s.hiss); // 2
s = null;
System.out.print(s.hiss);  // 2
```
This happens because when we call a static method from an object, the compiler checks 
for the object type of the reference and uses that instead of the object.


### Class vs. Instance Membership

A static member cannot call an instance member without referencing an instance of the 
class.
```
public class MantaRay {
  private String name = "Sammy";
  public static void first() {}
  public static void second() {}
  public void third() { System.out.print(name);}
  public static void main(String[] args) {
    first();
    second();
    third();    // does not compile
  }
}
```
The compiler will give us an error about making a static reference to an instance 
method. To fix the problem we have to do this:
```
public class MantaRay {
  private String name = "Sammy";
  ...
  public static void main(String[] args {
    ...
    var ray = new MantaRay();
    ray.third();
  }
}
```

### *static* Variable Modifiers

Some static variables are meant to never change. This type of static variable is 
called *constant*. It uses the `final` modifier to ensure the variable never changes.
They also uses a different naming convention than other variables, they use all 
uppercase letters with underscores between "words".
```
public class ZooPen {
  private static final int NUM_BUCKETS = 45;
  public static void main(String[] args) {
    NUM_BUCKETS = 5;   // does not compile
  }
}
```
The compiler will make sure that we to not accidentally try to update a final 
variable. Note that the *value is a primitive*, if the *value were a reference value* 
the things are a little different.
```
import java.ulti.*;
public class ZooInventoryManager {
  private static final String[] treats = new String[10];
  public static void main(String[] args) {
    treats[0] = "popcorn";
  }
}
```
We are allowed to modify the reference object or array's contents. What the compiler 
prevents us to do is *reassign* the variable, in this case `treats`, to point to a 
different object.


### *static* Initializers

The rules for static final variables are similar to instance final variables, except 
they to not use static constructors and use **static initializers** instead of 
instance initializers.
```
public class Panda {
  final static String name = "Ronda";
  static final int bamboo;
  static final double height;    // does not compile
  static { bamboo = 5; }
}
```
The `name` variable is assigned a value when it is declared, while the `bamboo` 
variable is assigned a value in a **static initializer**. The `height` variable is 
not assigned a value anywhere in the class definition, so that line does not compile.


In chapter 1, **instance initializers** were covered, they looked like unnamed 
methods - is just code inside braces. **static initializer** look similar, we add 
the `static` keyword to specify that they should be run when the class is first 
loaded. An example:
```
private static final int NUM_SECONDS_PER_MINUTE;
private static final int NUM_MINUTES_PER_HOUR;
private static final int NUM_SECONDS_PER_HOUR;
static {
  NUM_SECONDS_PER_MINUTE = 60;
  NUM_MINUTES_PER_HOUR = 60;
}
static {
  NUM_SECONDS_PER_HOUR = NUM_SECONDS_PER_MINUTE * NUM_MINUTES_PER_HOUR;
}
```
All static initializers run when the class is first used, in the order they are 
defined. The statements in them run and assign any static variables as needed. 

Note that although *final variables can't be reassigned*, the point here is that the 
**static initializer is the first assignment**. And since it occurs up front, it is 
okay.


### *static* Imports

Imports are convenient because we do not to specify where each class comes from each 
time we need use it. There is another type of import called *static import* that we 
use to **import static member of a class**. 
The syntax is `import static package.Class.member`:
```
import java.utils.List;
import static java.util.Arrays.asList;    // static import
public class ZooParking {
  public static void main(String[] args) {
    List<String> list = asList("one", "two");    // no Arrays.prefix
  }
}
```
If we not use `import static` the code should be this:
```
import java.utils.List;
import java.utils.Arrays;
public class ZooParking {
  public static void main(String[] args) {
    List<String> list = Arrays.asList("one", "two");    // using the class prefix
  }
}
```
Clearly, is a good convenience use `import static`. Is we write a method called 
`asList` in our class `ZooParking`, Java will use our method instead of the imported. 
Importing two classes or two static methods with the same name will cause a compiler 
error. 


## Passing Data among Methods

Java is a "pass-by-value" language. This means that a copy of the variable is made and 
the method receives that copy. Assignments made in the method do not affect the 
caller.
```
public class Dog {
  public static void main(String[] args) {
    String name = "Webby";
    speak(name);
    System.out.print(name);    // Webby
  }
  public static void spean(String name) {
    name = "Georgette";
  }
}
```
The `name` variable on `speak()` method could be anything, but in the exam they will 
use this strategy to try to confuse us.

**Calling methods on a reference to an object can affect the caller**. This is 
what occurs here:
```
public class Dog {
  public static void main(String[] args) {
    var name = new StringBuilder("Webby");
    speak(name);
    System.out.print(name);    // WebbyGeorgette
  }
  public static void speak(StringBuilder sb) {
    sb.append("Georgette");
  }
}
```
In this case, `speak()` call a method on the parameter. I doesn't reassigns to a 
different object.

## Returning Objects

Getting data back from a method is simple: a copy is made of the primitive or 
reference and returned from the method. This value can be used (normally) or ignored 
(sometimes). In the exam, is common to ignore value as strategy to deceive us.🙃


## Autoboxing and Unboxing Variables

Java supports some helpful features around passing primitives and wrapper data types, 
such as `int` and `Integer`. Although we can explicitly convert between *primitives* 
and *wrapper* classes, it is verbose:
```
int quack = 5;
Integer quackquack = Integer.valueOf(quack);    // convert int to Integer
int quackquackquack = quackquack.intValue();    // convert Integer to int
```

Luckily, Java has handlers built into the language that automatically convert between 
primitives and wrapper classes and vice-versa. *Autoboxing* is the process of 
converting a primitive into its equivalent wrapper class, while *unboxing* is the 
process of converting a wrapper class int its equivalent primitive.
```
int quack = 5;
Integer quackquack = quack;    // autoboxing
int quackquackquack = quackquck;    // unboxing
```
The new code is equivalent to the previous code, as the compiler is "doing the work" 
of converting the types automatically for us.

### Limits of Autoboxing and Numeric Promotion

Java will implicitly cast a smaller primitive to a larger type, as well as 
*autoboxing*, it will not not do both ate the same time.
```
Long badGorilla = 8;    // does not ompilet
```
Here, first have to occur a *cast* from the primitive `int` to other primitive `long` 
and than an *autoboxing* from the primitive `long` to the *wrapper* `Long`. This is 
what have to done:
```
long gorila1 = 8;    // cast from int to long
Long gorila2 = gorila1;    // autoboxing long to Long
```

Where autoboxing and unboxing really shine is when we apply them to method calls.
```
public class Chimpanzee {
  public void climb(long t) {}
  public void swing(Integer u) {}
  public void jump(int v) {}
  public static void main(String[] args) {
    var c = new Chimpanzee();
    c.climb(123);
    c.swing(123);
    c.jump(123L);    // does not compile
  }
}  
```
In this example, the call to `climb()` compiles because the `int` value cab be 
implicitly cast to a `long`. The call to `swing()` also is permitted, because the 
`int` value is *autoboxed* to an `Integer`. On the other hand, the call to `jump()` 
results in a compiler error because a long must be explicitly cast to an int.

## Overloading Methods

*Method overloading* occurs when methods in the same class have the same name but 
**different signatures**, which means they use different parameter lists.
```
public class Falcon {
  public void fly(int numMiles) {}
  public void fly(short numFeet) {}
  public boolean fly() {return faslse;}
  void fly(int numMiles, short numFeet) {}
  public void fly(short numFeet, int numMiles) throws Exception {}
}
```
As we can see, we can overload by changing anything in the parameter list. We can have 
a different type, more types, or the same types in a different order. Also notice that 
the return type, access modifier, and exception list are irrelevant to overloading. 
**Only the method name and parameter list matter**.

Now, let's look at some more complex scenarios that we may encounter.

### Reference Types

Given the rule about Java picking the mos specific version of a method that it can, 
what is the output of this code?
```
public class Pelican {
  public void fly(String s) {
    System.out.print("string");
  }
  public void fly(Object o) {
    System.out.print("object");
  }
  public static void main(String[] args) {
    var p = new pelican();
    p.fly("test);
    System.out.print("---");
    p.fly(56);
  }
}
```
The answer is "string---object". The first call passe a `String` and finds a direct 
match. There's no reason to use the `Object` version when there is a nice `String` 
parameter list just waiting to be called. The second call looks for an int parameter 
list. When if doesn't find one, it *autoboxes* to `Integer`. Since it still doesn't 
find a match, it goes to the `Object` one.

Another, what does this code print?
```
import java.time.*;
import java.util.*;
public class Parrot {
  public static void print(List<Integer> i) {
    System.out.print("I");
  }
  public static void print(CharSequence c) {
    System.out.print("C");
  }
  public static void print(Object o) {
    Sytem.out.print("O");
  }
  
  public sttic void main(String[] args) {
    print("abc");
    print(Arrays.asList(3));
    print(LocalDate.of(2026, 6, 13));
  }
}
```
The answer is "CIO". The first call to `print()` passes a `String`. As we know, 
`String` and `StringBuilder` implement the `CharSequence` interface. We also know that 
`Arrays.asList()` can be used to create a `List<Integer>` object, which explains the 
second output. The final call to `print()` passes a `LocalDate`, which clearly isn't a 
sequence of characters or a list. That means the `Object` method signature is used.


### Primitives

Primitives work in a way that's similar to reference variables. Java tries to find 
the most specific matching overloaded method. What happens here?
```
public class Ostrich {
  public void fly(int i) {
    System.out.print("int");
  }
  public void fly(long i) {
    System.out.print("long");
  }
  public static void main(String[] args) {
    var p = new Ostrich();
    p.fly(123);
    System.out.print("---");
    p.fly(123L);
  }
}
```
The answer is "int---long". The first call passes an `int` and sees an exact match. 
The second call passes a `long` and also sees an exact match. If we comment out the 
overloaded method with the `int` parameter lit, the output becomes "long---long". 
Java has no problem calling a large primitive, however, it will not do so unless a 
better match is not found.


### Autoboxing

As we saw earlier, autoboxing applies to method calls, but what happens if we have 
both a primitive, `int`, and a reference, `Integer`, version?
```
public class Kiwi {
  public void fly(int numMiles) {}
  public void fly(Integer numMiles) {}
}
```
These method overloads are valid. *Java tries to use the most specific parameter list 
it can find*. This is true for autoboxing as well as other matching types we see in 
this section.
This means calling `fly(3)` will call the first method. When the primitive `int` 
version isn't present, Java will autobox. However, when the primitive `int` is 
provided, there is no reason for Java to do the extra work of autoboxing.


### Arrays

Unlike the previous example, this code does not autobox:
```
public static void walk(int[] ints) {}
public static void walk(Integer[] integers) {}
```
Arrays have been around since the beginning of Java, they specify their actual types. 
When dealing with generics, things like `List<Integer>` will be covered.


### Varargs

Which method will be called if we pass as `int[]`?
```
public class Toucan {
  public void fly(int[] lengths) {}
  public void fly(int... lengths) {}    // does not compile
}
```
Since Java treats varargs as if the were an array, the method signatures are the same 
for both methods, and the code will not compile.

Now that we've see that the two methods are similar, it time to see how they are 
different. It is not a surprise that we can call both method passing an array:
```
fly(new int[] {1, 2, 3});    // allow to call either fly() method
```
However, we can only call the varargs version with stand-alone parameters:
```
fly(1, 2, 3);    // allowed only for varargs methods
```
This means they don't compile *exactly* the same. The parameters list is the same, 
though, and that is what we need to know.


### Putting All Together

All the rules for when a overloaded method is called should be logical: Java call the 
most specific method it can. When some type interact, the Java rules focus on 
backward compatibility. Since a long time ago, autoboxing and varargs didn't exist, 
both come last when Java looks at overloaded methods.

Table 5.6: The order that Java use to choose the right overloaded method

    +-------------------------+--------------------------------------------------+
    |          Rule           |  Example of what will be chosen for glide(1, 2)  |
    +-------------------------+--------------------------------------------------+
    |  Exact match by type    |  String glide(int i, int j)                      |
    +-------------------------+--------------------------------------------------+
    |  Larger primitive type  |  String glide(long i, long j)                    |
    +-------------------------+--------------------------------------------------+
    |  Autoboxed type         |  String glide(Integer i, Integer j)              |
    +-------------------------+--------------------------------------------------+
    |  Varargs                |  String glide(int... nums)                       |
    +-------------------------+--------------------------------------------------+

<br>

A small practice using the rules. What this code outputs?
```
public class Glider {
  public static String glide(String s) {
    return "1";
  }
  public static String glide(String... s) {
    return "2";
  }
  public static String glide(Object o) {
    return "3";
  }
  public static String glide(String s, String t) {
    return "4";
  }
  public static void main(String[] args) {
    System.out.print(glide("a"));
    System.out.print(glide("a", "b"));
    System.out.print(glide("a", "b", "c"))
  }
}
```
<br>
It print out "142". The first call matches the signature taking a single `String` 
because that is the most specific match. The second call matches the signature taking 
two `String` parameters since that is an exact match. I isn't until the third call 
that the varargs version is used since there are no better matches.



