# Chapter 8 - Lambdas and Functional Interfaces 

This chapter starts with [*introduction lambdas*](#writing-simple-lambdas) which is a new 
piece of syntax. Lambadas allow us to specify code that will be run later in the program.

Then the concept of [*functional interfaces*](#coding-functional-interfaces) are explained 
showing how to write our own and identify whether an interface is a functional interface.

After that, another piece of syntax is showed: [*method references*](#using-method-references). 
These are like a shorter form os lambdas.

[*Built-in functional interfaces*](#working-with-built-in-functional-interfaces) are 
covered.

[*Working with variables in lambdas*](#working-with-variables-in-lambdas)


## Writing Simple Lambdas

*Functional programming* is a way of writing code more declaratively. We specify what 
we want to do rather than dealing with the state of the objects. We focus more on 
expressions than loops.

Functional programming uses lambda expression to write ode. A *lambda expression* is 
a block of code that gest passed around. We can think of a lambda expression as an 
unnamed method existing inside an anonymous class. It has parameters an a body just 
like full-fledged methods do, but it doesn't have a name like a real method. Lambda 
expression are often referred to as *lambdas* for short and they are known as *closures* 
in other programming languages.

### Looking at a Lambda Example

Our goal is to print out all the animals in a list according to some criteria. We will 
do this without a lambda to show how lambdas are useful.
```
public record Animal (String species, boolean canHop, boolean canSwim) {}
---
public interface CheckTrait {
  boolean test(Animal a);
}
```
The `Animal` record has three fields and we have a list of animal to process the data 
based on a particular attribute, for example, `canHop`. So we define an interface to 
generalize this concept ans support a large variety of checks.

Since we want to check whether the `Animal` can hop, we provide a class that implements 
our interface:
```
public class CheckIfHopper implements CheckTrait {
  public boolean test(Animal a) {
    return a.canHop();
  }
}
```
Now that we have all we need, we wrote the code that find the animal the can hop:
```
01: import java.util.*;
02: public class TraditionalSearch {
03:   public static void main(String[] args) {
04:     
05:     // list of animals
06:     var animals = new ArrayList<Animal>();
07:     animals.add(new Animal("fish", false, true));
08:     animals.add(new Animal("kangaroo", true, false));
09:     animals.add(new Animal("rabbit", true, false));
10:     animals.add(new Animal("turtle", false, true));
11: 
12:     // pass class that does check
13:     print(animals, new CheckIfHopper());
14:   }
15:   private static void print(List<Animal> animals, CheckTrait checker) {
16:     for (Animal animal : animals) {
17: 
18:       // general check
19:       if (checker.test(animal))
20:         System.out.print(animal + " ");
21:     }
22:     System.out.println();
23:   }
24: }
```
Line 6 shows configuring an `ArrayList` with a specific type of `Animal`. The `print()` 
method on line 15 is very general: it can check for any trait. Note that this is good 
design. What happens if we want to print the `Animals` that swim? Yes, we need to write 
another class, `CheckIfSwims` ... only a few lines, but another file ... Then we new 
to add a new line under 13 that instantiates that new class. Two things just to do 
another check.

We can specify *the logic that do just what we need* right on line 13. This is what 
lambda expressions permits:

```
13: print(animals, a -> a.canHop());
```

Here we are telling Java that we only care if an `Animal` can hop. And if we care is if 
an `Animal` can swim, we to this:

```
13: print(animals, a -> a.canSwim());
```

And if the condition is the opposite:
```
13: print(animals, a -> !a.canSwim());
```

The point is that it is really easy to write code that uses lambdas oce we get the 
basics in place. This code uses a concept called *deferred execution*, that means that
the code is specified now but will run later. In this case, "later" is inside the 
`print()` method body, as opposed to when it is passed to the method.


### Lambda Syntax

Lambdas work with interfaces that have exactly on abstract method. In this case, Java 
looks at the `CheckTrait` interface, which has one method: `boolean test(Animal a)`.
The lambda in this example suggest that Java should call a method with an `Animal` para-
meter that returns a b`boolean` value that's the result of `a.canHop()`.

We know that because we write the code, but Java relies on *context* when figuring out 
what lambda expressions mean. Context refers to where and how the lambda is interpreted.

In the earlier example, the lambda is passed as the second parameter of the `print()`:
<pre><code>print( animals, <b>a -> a.canHop()</b> );</code></pre>

The `pritn()` method expects a `CheckTrait` as the second parameter:
<pre><code>
private static void print(List<Animal> animals, <b>CheckTrait checker</b>) {...}
</code></pre>

Since we are passing a lambda instead, Java tries to map our lambda to the abstract 
method declaration in the `CheckTrait` interface:
<pre><code><b>boolean test(Animal a);</b></code></pre>

Since that interface's method takes an `Animal`, the lambda parameters hat to be an 
`Animal`. And since that interface's method return a `boolean`, we known the lambda 
returns a boolean.

The syntax of lambdas is tricky because many parts are optional. These two lines do exact 
the same thing:
```
a -> a.canHop()

(Animals a) -> {  return a.canHop(); }
```

Here is what's going on:
- a single parameters specified with the name `a`
- the *arrow operator* `->` to separate the parameter and body
- a body that class a single method and return the result of that method

The second example shows the most verbose form of a lambda that returns a boolean:
- a single parameter specified with the name `a` and stating that the type is `Animal`
- the arrow operator `->` to separate the parameter and body
- a body that has one or more lines of code, including a semicolon and a `return` statement

The parentheses around the lambda parameters can be omitted only if there is a single 
parameter and its type is not explicitly stated (first line example). The same for the 
braces: if we have only a single statement, the braces can be omitted.

The syntax can also be mixed, this are valid lambda expressions:
<pre><code>a -> <b> { return a.canHop(); }</b>

<b>(Animal a)</b> -> a.canHop()
</code></pre>


**Valid lambdas that that return as boolean**
```
() -> true

x -> x.startsWith("test")

(String x) -> x.startsWith("test")

(x, y) -> { return x.startsWith("test"); }

(String x, String y) -> x.startsWith("test")
```

The first row takes zero parameters and always return the boolean value `true`. The 
second row takes one parameter and calls a method on it, returning the result. The 
third row does the same, except that it explicitly defines the type of the variable. 
The final two rows take two parameters and ignore one of them: there isn't a rule that 
says we must use all defined parameters.


### Assigning Lambdas to *var*

Lambdas cannot be assigned to `var` statements because the isn't enough context to 
determine which type is resulted.
```
var invalid = (Animal a) -> a.canHop();    // does not compile
```
Here, neither the lambda nor var have enough information to determine what type of 
functional interface should be used.

[back to top](#chapter-8---lambdas-and-functional-interfaces)

## Coding Functional Interfaces

A *functional interface* is an interface that contains a single abstract method and is 
officially known as a *single abstract method - (SAM)*  rule. The `CheckTrait` interface 
is a functional interface.

### Defining a Functional Interface

An example of functional interface and a class that implements it:
```
@FunctionalInterface
public interface Sprint {
  public void sprint(int speed);
}
---
public class Tiger implements Sprint {
  public void sprint(int speed) {
    System.out.println("Animal is sprinting fast!" + speed);
  }
}
```
In this example, the `Sprint` interface is a functional interface because it contains 
exactly one abstract method, and the `Tiger` class is a valid class that implements it.

**The _@FunctionalInterface_ Annotation**

The `@FunctionalInterface` annotation tells the compiler that our intend for the code 
to be a functional interface. If the interface does now follow the rules for a functional 
interface, the compiler will give an error.

This annotation means that the author of the interface promise it will be safe to use 
in a lambda in the future. However, just because we don't see the annotation doesn't mean 
it's not a functional interface Having exactly on abstract method is what makes it a 
functional interface, not the annotation.

Consider the following four interfaces, which of them are functional interfaces?
```
public interface Dash extends Sprint {}
---
public interface Skip extends Spring {
  void skip();
}
---
public interface Sleep {
  private void snore() {}
  default int getZzz() { return 1; }
}
---
public interface Climb {
  void reach();
  default void fall() {}
  static int getBackUp() { return 100; }
  private static boolean checkHeight() { return true; }
}
```
All four of these are valid interfaces, but not all of them are functional interfaces. 
The `Dash` interface is a *functional interface* because it extends the `Sprint` 
interface and inherits the single abstract method `sprint()`. The `Skip` interface is 
not a valid functional interface because it has two abstract methods: the inherited 
`sprint()` method and the declared `skip()` method.

The `Sleep` interface is not a valid functional interface. neither `snore()` nor 
`getZzz()` meets the criteria of a single abstract method. Even though *default methods* 
function like abstract methods, in that the can be overridden in a class implementing 
the interface, the are insufficient for satisfying the single abstract method requirement.
The `Climb` interface is a functional interface. Despite defining a slew of methods, it 
contains only on abstract methods: `reach()`.

### Adding Object Methods

All class inherit certain methods from `Object` and these are they signatures:
- `public String toString()`
- `public boolean equals(Object)`
- `public int hashCode()`

If a functional interface includes an abstract method with the same signature as a public 
method found in `Object`, *these methods do no count toward the single abstract method* 
test. The motivation behind this rule is that any class that implements the interface 
will inherit form `Object`, as all classes do, and therefore always implement these 
methods.

Examples:
```
public interface Soar {
  abstract String toString();
}
---
public interface Dive {
  String toString();
  public boolean equals(Object o);
  public abstract in hashCode();
  public void dive();
}
```
The `Soar` interface is not a functional interface. Since `toString()` is a public 
method implemented in `Object`, is does not count toward the single abstract method test.

The `dive()` method is the single abstract method, while the other are not counted since 
they are public methods defined in the `Object` class.

[back to top](#chapter-8---lambdas-and-functional-interfaces)


## Using Method References

*Method references* are another way to make the code easier to read, such as simply 
mentioning the name of the method. Like lambdas, it takes time to get used to the 
new syntax.

Suppose we are coding a duckling that is trying to learn how to quack. Fist we have a 
functional interface:
```
public interface LearnToSpeak  {
  void speak(String sound);
}
```

And suppose that exists a helper class that we can use.
```
public class DuckHelper {
  public static void teacher(String name, LearnToSpeak trainer) {
    // exercise patience(omitted)
    trainer.speak(name);
  }
}
```

Finally, we can put all together and meet our little `Duckling`. This code implements 
the functional interface using a lambda:
<pre><code>
public class Duckling {
  public static void makeSound(String sound) {
    <b>LearnToSpeak learner = s -> System.out.println(s);</b>
    DuckHelper.teacher(sound, learner);
  }
}
</code></pre>

This is good, but there is a redundancy: the lambda declares on parameter named `s`.
However, it does nothing other than pass that parameter to another method. A method 
reference lets us remove that redundancy writing this:
```
LearnToSpeak learner = System.out::println;
```

The `::` operator tell Java to call the `println()` method later. Once we get used to 
write code with this syntax, our code will be shorter and less distracting without 
writing as many lambdas.

A method reference and a lambda behave the same way at runtime. We can pretend the 
compiler turns our method reference into lambdas for us. There are four formats for 
method references:
- static methods
- instance methods on a particular object
- instance methods on a parameter to tbe determined at runtime
- constructors

We will take a look at each of those, in each example, we show the method reference 
and its lambda equivalent. For now, we create a separate functional interface for each 
example.

### Calling _static_ Methods

In the first example, we use a functional interface that converts a `double` to a `long`:
```
interface Converter {
  long round(double num);
}
```

We can implement this interface with the `round()` method in `Math`. Here we assign a 
method reference and a lambda to this functional interface:
<pre><code>
...
14: Converter methodRef = <b>Math::round;</b>
15: Converter lambda = x -> Math.round(x);
16:
17: System.out.println(methodRef.round(100.1));  // 100

</code></pre>
On line 14, we reference a method with one parameter, and Java knows that it's like a 
lambda with one parameter. Additionally, Java knows to pass that parameter to the method.

Since `round()` is overloaded and can take a `double`or a `float`, how does Java knows 
that we want to call the version with a `double`? In this case, we said that we were 
declaring a `Converter`, which has a method taking a `double` as parameter. Java looks 
for a method that matches that description. I tit cant't find it or finds multiple 
matches, the the compiler will report an error.

### Calling Instance Methods on a Particular Object

For this example, the functional interface checks if a `String` starts with a specific 
value:
```
interface StringStart {
  boolean beginningCheck(String prefix);
}
```

Since the `String` class has a `startsWith()` method that takes one parameter and return 
a boolean. Let's look at how to use method references with this code:
```
...
18: var str = "Zoo";
19: StringStart methodRef = str::startsWith;
20: StringStart lambda = s -> str.startsWith(s);
21:
22: System.out.println(methodRef.beginningCheck("A"));    // false
```

Line 19 shows that we want to call `str.startsWith()` and pass a single parameter to be 
supplied at runtime. This would be a nice way of filtering the data in a list.

A method reference doesn't have to take any parameters. In this new example we create a 
functional interface with a method that doesn't take any parameters, but returns a value:
```
interface StringChecker {
  boolean check();
}
```
And in the class that implement it:
```
...
18: var str = "";
19: StringChecker methodRef = str::isEmpty;
20: StringChecker lambda = () -> str.isEmpty();
21:
22: System.out.println(methodRef.check());    // true
```

Since the method on `String` is an *instance method*, we call the method reference on an 
instance of the String class.

While all method references can be turned into lambdas, the opposite is not always true. 
Consider this code:
```
var str = "";
StringChecker lambda = () -> str.startsWith("Zoo");
```

How might we writer this as a method reference? We might try this:
```
StringChecker methodReference = str::startsWith;    // does not compile

StringChecker methodReference = str:startsWith("Zoo");    // does not compile
```

Neither of these works. While we can pass the `str` as prt of the method reference, 
there's no way to pass the "Zoo" parameter with it. Therefore, it is not possible 
to write this lambda as a method reference.


### Calling Instance Methods on a Parameter

Now an example where we  call an instance method that doesn't take any parameters. More 
than that, this is done without knowing the knowing the instance in advance:
```
interface StringParameterChecker {
  boolean check(String text);
}
---
...
23: StringParameterChecker methodRef = String::isEmpty;
24: StringParameterChecker lambda = s -> s.isEmpty();
25:
26: System.out.println(methodRef.check("Zoo"));   // false
```

Line 23 say the method that we want to call is declare in `String`. It looks like a 
static method, but it isn't. Instead, Java knows that `isEmpty()` is an instance method 
that does not take any parameter. Java uses the parameter supplied at runtime as the 
instance on which the method is called.

Comparing lines 23 and 24 with the lines 19 and 20 of the instance example, we see that 
they look similar, although on references a local variable named `str`, while the other 
only references the functional interface parameters.

We can even combine the two types of instance method references. Again, we need a 
functional interface that takes two parameters:
<pre><code>
interface StringTwoParameterChecker {
  boolean <b>check</b>(String text, String prefix);
}
---
26: StringTwoParameterChecker methodRef = <b>String::startsWith;</b>
27: StringTwoParameterChecker lambda = (s, p) -> s.startsWith(p);
28:
29: System.out.println(methodRef.check("Zoo", "A"));    // false
30: System.out.println(lambda.check("Zoo", "B"));    // false
</code></pre>

Since the functional interface takes two parameters, Java has to figure out what they 
represent. The first one will always be the instance of the object for instance methods. 
Any other are to be method parameters.

Line 27 shows some of the power of a method reference: we were able to replace two 
lambda parameters. Again: line 26 may look like a static method, but is really a method 
reference declaring that the instance of the object will be specified later, at runtime.


### Calling Constructors

A *constructor reference* is a special type of method reference that uses `new` instead 
of a method and instantiates an object. In this example, our functional interface will 
not take any parameters but will return a `String`:
<pre><code>
interface EmptyStringCreator {
  String <b>create()</b>;
}
---
...
30: EmptyStringCreator methodRef = <b>String::new;</b>
31: EmptyStringCreator lambda = () -> new String();
32:
33: var myString = methodRef.create();
34: System.out.println(myString.equals("Snake"));    // false
</code></pre>

Here, we use `new` as if it were a method name. It expands like method references we 
have seen so far.

If the method of the interface have parameters, than method reference become tricky:
<pre><code>
interface StringCopier {
  String <b>copy</b>(String value);
}
---
...
32: StringCopier methodRef = <b>String::new;</b>
33: StringCopier lambda = x -> new String(x);
34:
35: var myString = methodRef.copy("Zebra");
36: System.out.println(myString.equals("Zebra"));    // true
</code></pre>

This means we can't always determine which method can be called by looking at the 
method reference. Instead, we have to look at the context to see what parameters are 
used and if there is a return type. In this example, Java sees that we are passing a 
`String` parameter and calls the constructor of `String` that takes such parameter.


### Reviewing Method References

    +------------------+-------------------+---------------+-----------------+
    |  Type            |  Before colon     |  After colon  |  Example        |
    +------------------+-------------------+---------------+-----------------+
    | static methods   | Class name        | Method name   | Math::random    |
    +------------------+-------------------+---------------+-----------------+
    | Instance methods | Instance variable | Method name   | str::startsWith |
    | on a particular  | name              |               |                 |
    | object           |                   |               |                 |
    +------------------+-------------------+---------------+-----------------+
    | Instance methods | Class name        | Method name   | String::isEmpty |
    | on a parameter   |                   |               |                 |
    +------------------+-------------------+---------------+-----------------+
    | Constructor      | Class name        | new           | String::new     |
    +------------------+-------------------+---------------+-----------------+
  
[back to top](#chapter-8---lambdas-and-functional-interfaces)  


## Working with Built-in Functional Interfaces

Java provides to us a large number of general-purpose functional interfaces, so we do 
not need write our own every time we want to write a lambda.

The core functional interfaces in Table 8.4 are provided in the `java.util.function` 
package. Since the table use _generics_ (next chapter), a bit of explanation:

- `<T>` allows the interface to take an object of a specified type T
- `<U>` if a second type is necessary, U represents it
- `<R>` represents a return type when the returned object is different than T or U



**Table 8.4: common functional interfaces**

    +----------------------+-------------+---------------+-----------------+
    | Functional interface | Return type | Method name   | # of parameters |
    +----------------------+-------------+---------------+-----------------+
    |  Supplier<T>         |  T          |  get()        |  0              |
    +----------------------+-------------+---------------+-----------------+
    |  Consumer<T>         |  void       |  accept(T)    |  1 (T)          |
    +----------------------+-------------+---------------+-----------------+
    |  BiConsumer<T, U>    |  void       |  accept(T, U) |  2 (T, U)       |
    +----------------------+-------------+---------------+-----------------+
    |  Predicate<T>        |  boolean    |  test(T)      |  1 (T)          |
    +----------------------+-------------+---------------+-----------------+
    |  BiPredicate<T, U>   |  boolean    |  test(T, U )  |  2 (T, U)       |
    +----------------------+-------------+---------------+-----------------+
    |  Function<T, R>      |  R          |  apply(T)     |  1 (T)          |
    +----------------------+-------------+---------------+-----------------+
    |  BiFunction<T, U, R> |  R          |  apply(T, U)  |  2 (T, U)       |
    +----------------------+-------------+---------------+-----------------+
    |  UnaryOperator<T>    |  R          |  apply(T, U)  |  2 (T, U)       |
    +----------------------+-------------+---------------+-----------------+
    |  BinaryOperator<T>   |  T          |  apply(T, T)  |  2 (T, T)       |
    +----------------------+-------------+---------------+-----------------+


Most of the time we don't assign the implementation of the interface to a variable. 
The interface name is implied, and it is passed directly to the method that needs it.
The name are been introduced her so that we can better understand and remember what 
is going on.

Now we will see how implement these interfaces using both: lambdas and method reference.
Some convenience methods provided in these interfaces are also shown.

### Implementing _Supplier_

A `Supplier` is used whe we want to generate or supply values without taking any input. 
The `Supplier` interface is defined as follows:
```
@FunctionalInterface
public interface Supplier<T> {
  T get();
}
```

We can create a `LocalDate` object using the factory method `now()`. This example shows 
how to use a `Supplier` to call this factory:
```
Supplier<LocalDate> s1 = LocalDate::now;
Supplier<LocalDate> s2 = () -> LocalDate.now();

LocalDate d1 = s1.get();
LocalDate d2 = s2.get();

System.out.println(d1);    // 2026-07-01
System.out.println(d2);    // 2026-07-01
```

This example prints a date twice. The `LocalDate::now` method method reference is used 
to create a `Supplier` to assign to an intermediate variable `s1`. A `Supplier` is often 
used when constructing new object. For example, we can print two empty `StringBuilder` 
objects:
```
Supplier<StringBuilder> s1 = StringBuilder::new;
Supplier<StringBuilder> s2 = () -> new StringBuilder();

System.out.println(s1.get());    // empty string
System.out.println(s2.get());    // empty string
```

This time, we used a *constructor reference* to create the object. We've been using 
generics to declare what type of `Supplier` we are using. This can be a little long 
to read.
```
Supplier<ArrayList<String>> s3 = ArrayList::new;
ArrayList<String> a1 = s3.get();
System.out.println(a1);    // []
```

We have a `Supplier` of a certain type, `ArrayList<String>`. The calling `get()` creates 
a new instance of `ArrayList<String>`, which is the generic type of the `Supplier`. That 
is: `s3` is a generic that contains another generic.

To get the value of the interface we new to call `get()`. If we do not call, the code 
will print something like this:
```
System.out.println(s3);

functionalinterface.BuiltIns$$Lambda$1/0x00000000000000x85959@58534d1a
```

This is the result of calling `toString()` on a lambda. We can see the name of the test 
class, `BuiltIns`, and it is in a package name `functionalinterface`. The `$$` means that 
the class doesn't exist in a class file on file system. I exists only in memory. After 
that is a memory address.


### Implementing _Consumer_ and _BiConsumer_

We use a `Consumer` when we want to do something with a parameter but not return 
anything. `BiConsumer` dos the same thing, except that it takes two parameters. This 
are the interfaces.
```
@FunctionalInterface
public interface Consumer<T> {
  void accept(T t);
  // omitted default method
}

@FunctionalInterface
public interface BiConsumer<T, U> {
  void accept(T t, U u);
  // omitted default method
}
```

Printing is a common use of the `Consumer` interface:
```
Consumer<String> c1 = System.out::println;
Consumer<String> c2 = x -> System.out.println(x);

c1.accept("Annie");    // Annie
c2.accept("Annie");    // Annie
```

`BiConsumer` is called with two parameters. They don't have to be the same type. 
For example, we can put a key and value in a map using this interface:
```
var map = new HashMap<String, Integer>();
BiConsumer<String, Integer> b1 = map::put;
BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);

b1.accept("chicken", 7);
b2.accept("chick", 1);

System.out.println(map);    // {chicken=7,chick=1}
```

The output is `{chicken=7,chick=1}`, which shows that both `BiConsumer` implementations 
were called. When declaring `b1`, we used an instance method reference on an object 
since we want to call a method on the local variable `map`. The code to instantiate `b1` 
is a good bit shorter than the code for `b2`, showing the power of method references.

In this other example we use the same type for both generic parameters:
```
var map = new HashMap<String, String>();
BiConsumer<String, String> b1 = map::put;
BiConsumer<String, String> b2 = (k, v) -> map.put(k, v);

b1.accept("chicken", "Cluck");
b2.accept("chick", "Tweep");

System.out.println(map);    // {chicken=Cluck,chick=Tweep}
```

This shows that a `BiConsumer` can use the same type for both the `T` and `U` generic 
parameters.


### Implementing _Predicate_ and _BiPredicate_

`Predicate` is often used when filtering or matching. Bot are common operation. A 
`BiPredicate` is jus like a `Predicate`, except that it takes two parameters instead 
of one. These are the interfaces:
```
@FunctionalInterface
public interface Predicate<T> {
  boolean test(T t);
  // omitted default and static methods
}

@FunctionalInterface
public interface BiPredicate<T, U> {
  boolean test(T t, U u);
  // omitted default methods
}
```

We can use a `Predicate` to test a condition.
```
Predicate<String> p1 = String::isEmpty;
Predicate<String> p2 = x -> x.isEmpty();

System.out.println(p1.test(""));    // true
System.out.println(p2.test(""));    // true
```

More interesting is a `BiPredicate`. This example also prints true twice:
```
BiPredicate<String, String> b1 = String::startsWith;
BiPredicate<String, String> b2 = (string prefix) -> string.startsWith(prefix);

System.out.println(b1.test("chicken", "chick"));    // true
System.out.println(b2.test("chicken", "chick"));    // true
```

The method reference includes both the instance variable and parameter for `startsWith()`. 
This is a good example of hwo method references save quite a lot o typing. The downside 
is that they are less explicit, and we really have to understand what is going on!


### Implementing _Function_ and _BiFunction_

A `Function` is responsible for turning on parameter into a value of a potentially 
different type and return it. Similarly, a `BiFunction` is responsible for turning 
two parameters into a value and return it. These are the interfaces:
```
@FunctionalInterface
public interface Function<T, R> {
  R apply(T t);
  // omitted default and static methods
}

@FunctionalInterface
public interface BiFunction<T, U, R> {
  R apply(T t, U u);
}
```

In this example, we use a function to covert a `String` to the length ot the `String`:
```
Function<String, Integer> f1 = String::length;
Function<String, Integer> f2 = x -> x.length();

System.out.println(f1.apply("cluck"));    // 5
System.out.println(f2.apply("cluck"));    // 5
```

This function turns a `String` into an `Integer`, more precisely, a `String` into an 
`int`, which is autoboxed into an `Integer`. The type don't have to be different, the
next example combines two `String` objects and produces another `String`:
```
BiFunction<String, String, String> b1 = String::concat;
BiFunction<String, String, String> b2 = (string, another) -> string.concat(another);

System.out.println(b1.apply("baby ", "chick"));    // baby chick
System.out.println(b2.apply("baby ", "chick"));    // baby chick
```

The first two types in the `BiFunction` are the input types. The third is the result 
type. For the method reference, the first parameter is the instance that `concat()` 
is called on, and the second is passed to `concat()`.


### Implementing _UnaryOperator_ and _BinaryOperator_

`UnaryOperator` and `BinaryOperator` are special case of a `Function`. They require 
all type parameters to be the same type. A `UnaryOperator` transforms its value into 
one of the same type. For example, incrementing by one is a unary operation. In fact, 
`UnaryOperator` extends `Function`. A `BinaryOperator` merges two values into one of 
the same type. Adding two number is a binary operation. Similarly, `BinaryOperator`
extends `BiFunction`. The interfaces are these:
```
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
  // omitted static method
}

@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T, T, T> {
  // omitted static methods
}
```

This means the method signatures look like this:
```
T apply(T t);    // UnaryOperator

T apply(T t1, T t2);    // BinaryOperator
```

The generic declaration on the subclass are what force the type to be the same. For 
the unary example, note how the return type is the same type as the parameter.
```
UnaryOperator<String> u1 = String::toUpperCase;
UnaryOperator<String> u2 = x -> x.toUpperCase();

System.out.println(u1.apply("chirp"));    // CHIRP
System.out.println(u2.apply("chirp"));    // CHIRP
```

This prints CHIRP twice. We don't need to specify the return type in the generics because 
`UnaryOperator` requires it to be the same as the parameter. Now the binary example:
```
BinaryOperator<String> b1 = String::concat;
BinaryOperator<String> b2 = (string, another) -> string.concat(another);

System.out.println(b1.apply("baby ", "chick"));    // baby chick
System.out.println(b2.apply("baby ", "chick"));    // baby chick
```
Notice that this code does the same thing as the `BiFunction` example. The code is more 
succinct, which shows the importance of using the best functional interface. It's nice 
to have one generic type specified instead of three.


### Checking Functional Interfaces

It's really important to know the number of parameters, types return value and method 
name for each of the functional interfaces, so memorize the Table 8.4 is a must:
[Table 8.4](#working-with-built-in-functional-interfaces).

What functional interface we should use in these three situation?
- Returns a `String` without taking any parameter
- Returns a `Boolean` and takes a `String`
- Returns an `Integer` and takes two `Integers`

The first one is a `Supplier<String>` because it generates an objet and takes zero 
parameters.
The second one is a `Function<String, Boolean>` because it takes one parameter and 
returns another type. Note that `Predicate<String>` returns a `boolean` primitive.
The third is either a `BinaryOperator<Integer>` or a `BiFunction<Integer,Integer,Integer>`. 
Since `BinaryOperator`is a special case of a `BiFunction`, either is a correct answer. 
`BinaryOperator<Integer>` is the better answer of the two since it is more specific.

```
6: Predicate<List> ex1 = x -> "".equals(x.get(0));
7: ________<Long> ex2 = (Long 1) -> System.out.println(1);
8: Predicate<String, String> ex3 = (s1, s2) -> false;
```


### Using Convenience Methods on Functional Interfaces

By definition, all function interfaces have a single abstract method. This doesn't 
mean the can have only one method, though. Several of the common functional interfaces 
provide a number of helpful default interface methods.

The methods on table below are good to know since all of them facilitate modifying 
or combining functional interfaces of the same type. Here are listed only the main 
interfaces. The `BiConsumer`, `BiFunction`, and `BiPredicate` interfaces have similar 
methods available.

**Table 8.5: convenience methods**

    +---------------------+----------------+---------------+--------------------+
    | Interface instance  | Method return  |  Method name  | Method parameters  |
    |                     | type           |               |                    |
    +---------------------+----------------+---------------+--------------------+
    |  Consumer           |  Consumer      |  addThen()    |  Consumer          |
    +---------------------+----------------+---------------+--------------------+
    |  Function           |  Function      |  addThen()    |  Function          |
    +---------------------+----------------+---------------+--------------------+
    |  Function           |  Function      |  compose()    |  Function          |
    +---------------------+----------------+---------------+--------------------+
    |  Predicate          |  Predicate     |  and()        |  Predicate         |
    +---------------------+----------------+---------------+--------------------+
    |  Predicate          |  Predicate     |  negate()     |  ---               |
    +---------------------+----------------+---------------+--------------------+
    |  Predicate          |  Predicate     |  or()         |  Predicate         |
    +---------------------+----------------+---------------+--------------------+


Let's start with these two `Predicate` variables:
```
Predicate<String> egg = s -> s.contains("egg");
Predicate<String> brown = s -> s.contains("brown");
```

Now we want a `Predicate` for brown eggs ant another for all other colors of eggs.
We could write this by hand, as shown here:
```
Predicate<String> brownEggs = s -> s.contains("egg") && s.contains("brown");
Predicate<String> otherEggs =  s -> s.contains("egg") && !s.contains("brown");
```

This works, but is not great. It's a bit long to read, and it contains duplication. 
What if we decide the letter _e_ should be capitalized in _egg_ ? We'd have to change 
it in three variables: `egg`, `brownEgg`, and `otherEggs`. A better way to deal with 
this situation is to use two of the default methods on `Predicate`.
```
Predicate<String> brownEggs = egg.and(brown);
Predicate<String> otherEggs = egg.and(brown.negate());
```

Now we are reusing the login in the original `Predicate` variables to build two new 
ones. It's' shorter and clearer what the relationship is between variables. We can 
also change the spelling of _egg_ in one place, and the other two objects will have 
new logic because they reference it.


Moving on to `Consumer`, let's take a look at the `andThen()` method, which runs two 
functional interfaces in sequence:
```
Consumer<String> c1 = x -> System.out.print("1: " + x);
Consumer<String> c2 = x -> System.out.print(", 2: " + x);

Consumer<String> combined = c1.andThen(c2);
combined.accept("Annie");    // 1: Annie, 2: Annie

```

The same parameter is passed to both `c1` and `c2`. This shows that the `Consumer` 
instances are run in sequence and are independent of each other.

By contrast, the `compose()` method on `Function` chains functional interfaces. 
However, it passes along the output of one to the input of another.
```
Function<Integer, Integer> before = x -> x + 1;
Function<Integer, Integer> after = x -> x * 2;

Function<Integer, Integer> combined = after.compose(before);
System.out.println(combined.apply(3));    // 8
```

This time, the `before` runs first, turning the 3 into 4. The the `after` runs, 
doubling the 4 to 8. All of the methods in this section are helpful for simplifying 
our code as we work with functional interfaces.


### Learning the Functional Interfaces for Primitives

There are a large number of special functional interfaces for primitives, these sum 
to those on [table 8.4](#working-with-built-in-functional-interfaces), so is good 
to memorize this table. Most of them are for `double`, `int`, and `long` types, with 
one exception which is `BooleanSupplier`.

#### Functional Interfaces for _boolean_

`BooleanSupplier` is a separate type. It has one method to implement:
```
@FunctionalInterface
public interface BooleanSupplier {
  boolean getAsBoolean();
}
---
...
12: BooleanSupplier b1 = () -> true;
13: BooleanSupplier b2 = () -> Math.random() > .5;
14: System.out.println(b1.getAsBoolean());    // true
15: System.out.println(b2.getAsBoolean());    // false
```

Lines 12 and 13 each create a `BooleanSupplier`, which is the only functional interface 
for boolean. Line 14 prints 'true', since it is the result ob `b1`. Line 15 prints 
'true' or 'false', depending on the random value generated.


#### Functional Interfaces for _double_, _int_, and _long_

Most of the functional interfaces for `double`, `int`, and `long` are the equivalent 
of these primitives to those present in Table 8.4.

**Table 8.6 - common functional interfaces for primitives**

    +------------------------+-------------+------------------+----------------------+
    | Functional interface   | Return type | Single abstract  | # of parameters      |
    |                        |             | method           |                      |
    +------------------------+-------------+------------------+----------------------+
    |  DoubleSupplier        |  double     |  getAsDouble     |  0                   |
    |  IntSupplier           |  int        |  getAsInt        |                      |
    |  LongSupplier          |  long       |  getAsLong       |                      |
    +------------------------+----------------+---------------+----------------------+
    |  DoubleConsumer        |  void       |  accept          |  1 (double)          |
    |  IntConsumer           |             |                  |  1 (int)             |
    |  LongConsumer          |             |                  |  1 (long)            |
    +------------------------+----------------+---------------+----------------------+
    |  DoublePredicate       |  boolean    |  test            |  1 (double)          |
    |  IntPredicate          |             |                  |  1 (int)             |
    |  LongPredicate         |             |                  |  1 (long)            |
    +------------------------+----------------+---------------+----------------------+
    |  DoubleFunction<R>     |  R          |  apply           |  1 (double)          |
    |  IntFunction<R>        |             |                  |  1 (int)             |
    |  LongFunction<R>       |             |                  |  1 (long)            |
    +------------------------+----------------+---------------+----------------------+
    |  DoubleUnaryOperator   |  double     |  applyAsDouble   |  1 (double)          |
    |  IntUnaryOperator      |  int        |  applyAsInt      |  1 (int)             |
    |  LongUnaryOperator     |  long       |  applyAsLong     |  1 (long)            |
    +------------------------+-------------+------------------+----------------------+
    |  DoubleBinaryOperator  |  double     |  applyAsDouble   |  2 (double, double)  |
    |  IntBinaryOperator     |  int        |  applyAsInt      |  2 (int, int)        |
    |  LongBinaryOperator    |  long       |  applyAsLong     |  2 (long, long)      |
    +------------------------+----------------+---------------+----------------------+

The differences to Table 8.4 are these:
- Generics are gone from some interfaces, and instead the type name tells us what 
primitive is involved.
- The single abstract method is often renamed when a primitive type is returned.

With this table, we should be able to answer questions like this: which functional 
interface would be used to fill the blanks to make the code compile?
```
var d = 1.0;

________________ f1 = x -> 1;

f1.applyAsInt(d);
```
Looking for the clues we can see that the functional interface takes a single `double` 
parameter and returns an `int`. We can also see that it has a single abstract method 
named `applyAsInt`. The `DoubleToIntFunction` and `ToIntFunction` functional interfaces 
meet all three of those criteria.

[back to top](#chapter-8---lambdas-and-functional-interfaces)

## Working with Variables in Lambdas



