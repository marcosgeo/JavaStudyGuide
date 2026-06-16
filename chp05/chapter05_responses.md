1) a,
A, E
Instance and static variables cab be marked final, making option A correct. Effectively 
final means a local variable is not marked final but whose value does not change after 
it is set, making option B incorrect. Option C is incorrect, as final refers only to 
the reference to an object, not its content. Option D is incorrect, as var and final 
can be used together. Finally, option E is correct: once a primitive is marked final, 
it cannot be modified.

2) c, 
B, C,
The keyword void is a return type. Only the access modifier or optional specifiers are 
allowed before the return type. Option C is correct, creating a method with private 
access. Option B is also correct, creating a method with package access and the 
optional specifier final. Since package access does not use a modifier, we get to jump 
right to final.

3) d,
A, D
Options A and D are correct because the optional specifiers are allowed in any order. 
Options B and C are incorrect because they each have two return types. Options E and F 
are incorrect because the return type is before the optional specifier and access 
modifier, respectively.

4) a, b, c, d, e,
A, B, C, E
The value 6 can be implicitly promoted to any of the primitive types, making options 
A, C, and E correct. It can also be autoboxed to Integer, making option B correct. It 
cannot be both promoted and autoboxed, making options D and F incorrect.

5) a, c, d,
A, C, D
Options A and C are correct because a void method is optionally allowed to have a 
return statement as long as it doesn't try to return a value. Option D is correct 
because it returns an int value.

6) a, b, e, f
A, B, F,
Options A and B are correct because the single varargs parameter is the last parameter 
declared. Option F is correct because it doesn't use any varargs parameters.
Option E is incorrect because the `...` for a varargs must be after the type, not 
before it.

7) d, e, f
D, F,
Option D passes the initial parameters plus two more to turn into a varargs array of 
size 2. Option F passes the initial parameter plus an array of size 2. 
Option E does not compile because it does not declare an array properly. It should be 
`new boolean[] {true, true}`


8) d,
D,
Option D is correct. A common practice is to set all fields to be private and all 
methods to be public.

9) d, f
B, C, D, F,
The two classes are in different packages, which means private access and package 
access will not compile. this causes compiler errors on lines 5, 6, and 7, making 
options B, C, and D correct answers. Additionally, protected access will not compile 
since `School` dos not inherit from `Classroom`. This causes the compiler error on 
line 9, making option F a correct answer as well.


10) c,
B
`Rope` runs line 3, setting `LENGTH` to 5, and then immediately after that runs the 
static initializer, which sets it to 10. Line 5 in the `Chimp` class calls the static 
method normally and prints swing and a space. Line 6 also calls the static method. 
Java allows calling a static method through an instance variable, although it is not 
recommended. Line 7 uses the static import on line 2 to reference `LENGTH`. For these 
reasons, option B is correct.

11) c, d, 
B, E
Line 10 does not compile because static methods are not allowed to call instance 
methods. Even though we are calling `play()` as if it were an instance method and an 
instance exists, Java knows `play()` is really a static method and treats it as such. 
Since this is the only line that does not compile, option B is correct. If line 10 is 
removed, the code prints "swing-swing", making option E correct. It does not throw a 
`NullPointerException` on line 17 because `play()` is a static method. Java looks at 
the type of the reference for `rope2` and translates the call to `Rope.play()`.


12) a
B
The test for effectively final is if the final modifier can be added to the local 
variable and the code still compiles. The `monkey` variable declared on line 11 is not 
effectively final because it is modified on line 13. The `giraffe` and `name` 
variables declared on line 13 and 14, are effectively final and not modified after 
they are set. The `name` variable declared on line 17 is not effectively final since 
it is modified on line 22. Finally, the `food` variable on line 18 is not effectively 
final since it is modified on line 20. Since there are two effectively final 
variables, option B is correct.


13) a,
D
There are two details to notice in this code. First, not that `RopeSwing` has an 
instance initializer and not a static initializer. Since `RopeSwing` is never 
constructed, the instance initializer does not run. The other detail is that length is 
static. Changes from any object update this common static variable. The code prints 8, 
making option D correct.

14) c,
E,
If a variable is static final, it must be set exactly once, and it must be in the 
declaration line or in a static initialization block. Line 4 doesn't compile because 
`bench` is not set in either of theses locations. Line 15 doesn't compile because 
final variables are not allowed to be set after that point. Line 11 doesn't compile 
because `name` is set twice: once in the declaration and again in the static block. 
Line 12 doesn't compile because `rightRope` is set twice as well. Both are in static 
initialization blocks. Since four lines do not compile, option E is correct.

15) c,
B,
The two valid ways to do this are `import static java.utils.Collections.*;` and 
`import static java.utils.Collection.sort;`. Option A is incorrect because we can do a 
static import only on static members. Classes such as `Collections` require a regular 
import. Option C is nonsense as method parameters have no business in an import. 
Option D, E and F try to trick us into reversing the syntax. 

16) c,
E
The argument on line 17 is a `short`. It can be promoted to an `int`, so `print()` on 
line 5 is invoked. The argument on line 18 is a `boolean`. It can be autoboxed to a 
`Boolean`. so `print()` on line 11 is invoked. The argument on line 19 is a `double`. 
It can be autoboxed to a `Double`, so `print()` on line 11 is invoked. Therefore, the 
output is "int-Object-Object-", and the correct answer is option E.

17) b,
B
Since Java is pass-by-value and the variable on line 8 never gets reassigned, it 
stays as 9. In the method `square`, `x` starts as 9. The `y` value becomes 81, and 
then `x` gets set to -1. Line 9 does set `result` to 81. However, we are printing 
out `value`, ant that is still 9, making option B correct.

18) b, d, e,
B, D, E
Since Java is a pass-by-value, assigning a new object to `a` does not change the 
caller. Calling `append()` does not affect the caller because both the method 
parameter and the caller have a reference to the same object. Finally, returning a 
value does pass the reference to the caller for assignment to `s3`. For these reasons, 
options B, D, and E are correct.

19) b, c, e, f
B, C, E
The variable `value1` is a final instance variable. It can be set only once: in the 
variable declaration, an instance initializer, or a constructor.
Options D and F do not compile because a static initializer does not have access to 
instance variables.

20) a,
A, E
The `100` parameter is an `int` and so calls the matching `int` method, making 
option A correct. When this method is removed, Java looks for the next most specific 
constructor. Java prefers autoboxing to varargs, so it chooses the `Integer` 
constructor. The `100L` parameter is a `long`. Since it can't be converted into a 
smaller type, it is autoboxed into a `Long`, and then the method for `Object` is 
called, making option E correct.

21) b, d, 
B, D
Options B and D are valid methods overloads because the types of parameters in the 
list change. When overloading methods, the return type and access modifiers do not 
need to be the same.

