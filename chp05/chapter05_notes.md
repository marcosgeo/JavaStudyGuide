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
 - *package access* **no specific keyword**, the method can be called only from a class int the same package.
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


