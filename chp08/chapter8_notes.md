# Chapter 8 - Lambdas and Functional Interfaces 

This chapter starts with [*introduction lambdas*](#writing-simple-lambdas) which is a new 
piece of sintax. Lambadas allow us to specify code that will be run later in the program.

Then the concept of [*functional interfaces*](#coding-fucntional-interfaces) are explained showing how to write our
own and identify wheter an interface is a functional interface.

After that, another piece of syntax is showed: (*method references*)[]. These are like 
a shorter form os lambdas.

## Writing Simple Lambdas

*Functional programming* is a way of wrting code more declaratively. We specify what 
we want to do rather than dealing with the state of the objects. We focus more on 
expressions than loops.

Functional programming uses lmabda expression to wrte ode. A *lambda expression* is 
a block of code that gest passed around. We can think of a lambda expression as an 
unnameed method existing inside an anonymmous class. It has parameters an a body just 
like full-fledged methods do, but it doesn't have a name like a real method. Lambda 
expression are often referred to as *lambdas* for short and they are known as *closures* 
in other programing languages.

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
based on a particular attribute, for example, `canHop`. So we define an itnerface to 
generalize this concept ans support a large variety of checks.

Since we want to check whether the `Animal` can hop, we provide a class that implements 
our interfface:
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
another class, `CheckIfSwims` ... only a fews lines, but anohter file ... Then we new 
to add a new line under 13 that instantiates that new class. Two things just to do 
another check.

We can specify *the logic that do just what we need* right on line 13. This is what 
lambda empressions permits:

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

The point is that it is really esay to write code that uses lambdas oce we get the 
basics in place. This code uses a concept called *deferred execution*, that means that
the code is specified now but will run later. In this case, "later" is inside the 
`print()` method body, as opposed to when it is passed to the method.


### Lambda Syntax

Lambdas work with interfaces that have exatly on abstract method. In this case, Java 
looks at the `CheckTrait` interface, which has one method: `boolean test(Animal a)`.
The lambda in this example suggest that Java shoul call a method with an `Animal` para-
meter that returns a b`boolean` value that's the result of `a.canHop()`.

We know that because we write the code, but Java relies on *context* when figuring out 
what lambda expressions mean. Context refers to where and how the lambda is interpreted.

In the erlier example, the lambda is passed as the second parameter of the `print()`:
<pre><code>print( animals, <b>a -> a.canHop()</b> );</code></pre>

The `pritn()` method expects a `CheckTrait` as the second parameter:
<pre><code>
private static void print(List<Animal> animals, <b>CheckTrait checker</b>) {...}
</code></pre>

Since we are passing a lambda instead, Java tries to map our lambda to the abstract 
method declaration in the `CheckTrait` interface:
<pre><code><b>boolean test(Animal a);</b></code></pre>

Since that interface's method takes an `Animal`, the lambda parameters hat to be an 
`Animal`. And since that interface's method return a `boolean`, we knwow the lambda 
returns a boolean.

The syntax of lambdas is tricky because many parts are optional. Thes two lines do exact 
the same thing:
```
a -> a.canHop()

(Animals a) -> {  return a.canHop(); }
```

Here is what's going on:
- a single parameters specified with the name `a`
- the *arrow operator* `->` to separate the parameter and body
- a body that calss a single method and return the result of that method

The second exaple shows the most verbose form of a lambda that returns a boolean:
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
second reow takes one parameter and calls a method on it, returning the result. The 
third reow does the same, except that it explicitly defines the type of the variable. 
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

## Coding Fucntional Interfaces
