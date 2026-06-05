1) f, 
F
Line 5 does not compile. This quest is checking to see whether we are paying attention 
to the types.

2) b, e, 
C, E, F,
Option B declares a legal 3D array. Option D declares a legal 2D array. 
Option C uses the variable name as if it were a type, which is clearly illegal. 
Options E and F don't specify any size. Although it is legal to leave out the size 
for later dimensions of a multidimensional array, the first one is required. 
Option A declares a legal 2D array. 
Remember that it is normal to see classes on the exam we might not have learned and we 
are not expected to know anything about them. 

3) a, c, d,
A, C, D
Option B throws an exception because there is no March 40. Option E also throws an 
exception because 2023 is not a leap year and therefore has no February 29. Option F 
does not compile because the enum should be named just Month. Option D is correct 
because it is just a regular date and has nothing to do with daylight saving time. 
Options A and C are correct because Java is smart enough to adjust for daylight saving 
time.

4) a
A, C, D,
The code compiles fine. Line 3 points to the String in the string pool. Line 4 calls 
the String constructor explicitly and is therefore a different object than `s`. Line 5 
checks for object equality, which is true, and so it prints `one`. Line 6 uses object 
reference equality, which is not true since we have different objects. Line 7 call 
`intern()`, which return the value from the string pool and is therefore the same 
reference as `s`. Line 8 also compares references but is true since both references 
point to the object from the *string pool*. Finally, line 9 is a trick. The string 
Hello is already in the string pool, so calling `intern()` does not change anything. 
The reference `t` is a different object, so the result is still false.

5) b
B
This examples uses method chaining. After the call to append(), `sb` contains "aaa". 
That result is passed to the first insert() call, which insert at index 1. At this 
point, `sb` contains "abbaa". That result is passed to to the final insert(), which 
insert at index 4, result in "abbaccca".


6) c
C


7) a, e,
A, E

8) a, b, f
A, B, F

9) a, c, e,
A, C, F
Array are zero-indexed, making option A correct and option B incorrect. They are not 
able to change size, which is option C. The values can be changed, making option D 
incorrect. An array does not override equals(), so it uses object equality. Since two 
different objects are not equal, option F is correct, and options E and G are not 
correct.

10) c, 
A
All of these line compile. The min() and floor() methods return the same type passed 
in: int and double, respectively. The round() method returns a long when called with a 
double. Option A is correct since the code compiles.

11) e,
E

12) a, d
A, D, E
First, notice that the indent() call adds a black space to the beginning of `numbers`, 
and `stripLeading()` immediately removes it. Therefore, these methods cancel each 
other out and have no effect. 
The `substring()` method has two forms. The first takes the 
index to start with and goes to the end of the string. Remember that indexes are 
zero-based. The first call starts at index 1 and ends with index 2 since it needs to 
stop before index 3. This gives us option A. 
The second call starts at index 7 and ends in the same place, resulting in an empty 
`String` which is option E. This prints out a blank line. The final call stars at 
index 7 and goes to the end of the `String` finishing up with option D.

13) d,
B
A String is immutable. Calling concat() returns a new String but does not change the 
original. A StringBuilder is mutable. Calling append() adds characters to the existing 
character sequence along with returning a reference to the same object. Therefore, 
option B is correct.

14) a, f
A, F,

15) c, e
[123, PIG, pig], -3
C, E,

16) a, c, b
A, B, G
```
jshell> {
   ...>   var base = "ewe\nsheep\\t";
   ...>   int length = base.length();
   ...>   int indent = base.indent(2).length();
   ...>   int translate = base.translateEscapes().length();
   ...>   
   ...>   var formatted = "%s %s %s".formatted(length, indent, translate);
   ...>   System.out.format(formatted);
   ...> }
```
There are 11 characters in base because there are two escape characters. The `\n` 
counts as one character representing a `new line`, and `\\` counts as one character 
representing a `backslash`. This makes option B one of the answers. The indent() 
method adds two characters to the beginning of each of the two lines of base. This 
gives us four additional characters. However, the method also normalizes by adding a 
new line to the end if it is missing. The extra character means we add five characters 
to the existing 11, which is option G. Finally, the `translateEscapes()` method turns 
any text escape characters into actual escape characters, making `\\t`into `\t`. This 
gets rid of one character, leaving us with 10 characters matching option A.

17) a, g, 
A, G
letters "abcdefg"


18) f, c
C, F
s1 """
  purr"""
s2 ""
s1 PURR
s1 PURRtwo
s2 2
s2 2c
s2 2cfalse
This question is tricky because it has several parts. First, we have to know that the 
text block on lines 13 and 14 is equivalent to a regular String. Since there is no 
line break at the end, this is four character. 

19) b, c, d, f
A, B, D
The `compare()` method returns a positive integer when the arrays are different and 
the first is larger. This the case for option A since the element at index 1 comes 
first alphabetically. It is not the case for option C because the `s4` is longer or 
for option E because the arrays are the same.
The `mismatch()` method return a positive integer when the arrays are different in a 
position index 1 or greater. This is the case for options B and D since the difference 
is at index 1. It is not the case for option F because there is no difference.

20) a, d, 
A, D
diff 2
hour 3
offset false


21) a,
A, C
The `reverse()` method is the easiest way of reversing the characters in a 
`StringBuilder`; therefore, option A is correct. In option B, `substring()` returns a 
`String`, which is not stored anywhere. Option C uses method chaining. First, it 
creates the values "JavavaJ%". Then, it removes the first three characters, resulting 
in "avaJ$". Finally, it removes the last character, resulting in "avaJ". Option D 
throws an exception because we cannot delete the character after the last index.

22) e
A
The date starts out as April 30, 2022. Since date are immutable and the plus methods' 
return values are ignored, the result is unchanged. Therefore, option A is correct.


