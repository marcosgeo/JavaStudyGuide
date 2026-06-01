1) a, b, c, d, e, f,
A, B, C, E, F, G

2) a
B: 
The code compiles and runs without issue; Even though two consecutive else statements 
on lines 7 and 8 look a little odd, they are associated with separate if statements 
on lines 5 and 6, respectively.

humidity = 9
too low

3) a, d, f
A, D, F, H
A `for-each` loop also supports classes that implement java.lang.Iterable. 
Although this includes many of the Collection Framework classes, not all of them
implements java.lang.Iterable.


4) c,
F,
The code does not compile because the switch expression requires all possible case values to be handled making option F correct. I a valid default statement was added, 
them the code would compile and print Turtle at runtime. Unlike traditional switch 
statements, switch expressions execute exactly one branch and do not use break 
statements between case statements.

type = turtle

5) e
E,
The second for-each loop contains a continue followed by a print() statement. Because 
the continue is not conditional and always included as part of the body of the 
for-each loop, the print() statement is not reachable. For this reason, the print() 
statement does not compile. As this is the only compilation error, option E is
correct. The others lines of code compile without issue.

List<Integer> myFavoriteNumbers = new ArrayList<>();
myFavoriteNumbers.add(10);
myFavoriteNumbers.add(14);
for (var a : myFavoriteNumbers) {
  System.out.println(a + ", ");
  break;
}

for (int b : myFavoriteNumbers) {
  continue;
  System.out.print(b + ", ");
}

for (Object c : myFavoriteNumbers) {  // not compile
  System.out.println(c + ", ");

6) c, d, e,
C, D, E,
A fore-each loop can be executed on any Collections objec that implements 
java.lang.Iterable, such as List or Set, but not all Collections classes, such as 
Map, so option A is incorrect. The body of a do/while loops is executed one or 
more times, while the body of a while loop is executed zero or more times, making 
option E correct and option B incorrect. The conditional expression of for loops is 
evaluated at the start of the loop execution, meaning the for loop may execute zero
or more times, making option C correct. A switch expression that takes a String 
requires a default branch if the result is assigned to a variable, making option D 
correct. Finally, each if statement hat at most one matching else statement, making 
F incorrect.

7) b, d, e, f(check)
B, D,
Option E does not compile. We can declare multiple elements in a for loop, 
but the data type must be listed only once, such as in (int i=0, j=3;). Finally, 
option F is incorrect because the first element of the array is skipped. 
Since the conditional expression is checked before the loop is executed the first 
time, the first value of i used inside the body of the loop will be 1.

8) g,
G
The first two pattern matching statements compile without issue. The variable bat is 
allowed to be used again, provided it is no longer in scope. Line 36 does not 
compile, though. Dues to flow scoping, if s is not a Long, then bat is not in scope 
in the expression bat <= 20. Line 38 also does not compile as default cannot be used 
as part of an if/else statement. For these two reasons, option G is correct.


9) a,
B, C, E,
The code contains a nested loop and a conditional expression that is 
executed if the sum of col + row is an even number; otherwise, count is incremented.
Note that options E and F are equivalent to options B and D, since unlabeled 
statements apply to the most inner loop. Option A is incorrect because this causes
the loop to exit immediately with count only being set to 1. Options B, C, and E
follow the same pathway. Fist, count is incremented to 1 on the first inner loop,
and then the inner loop is exited. On the next iteration of the outer loop, row
is 2 and col is 0, so execution exits the inner loop immediately. On the third 
iteration of the outer loop, row is 3 and col is 0, so count is incremented to 2. 
In the next iteration of the inner loop, the sum is even, so we exit, and our
program is complete, making options B, C, E each correct. 

1, 0, count++
1, 1, break bunny
2, 0, break bunny
2, 1, count++
2, 2, break bunny
3, 1, break bunny

10) b
E, 
This code contains numerous compilation errors, making options A and H incorrect.
Line 15 does not compile, as continue cannot be used inside a switch statement
like this. Line 16 is not a compile-time constant since any int value cab be passed 
as a parameter. Marking if final does not change this, so it does not compile. Line 18
does not compile because Sunday is not marked as final. Being effectively final is
insufficient. Finally, line 19, does not compile because DayOfWeek.MONDAY is not 
an int value. While switch statements do support enum values, each statement must 
have the same data type as the switch variable otherDay, which is int. The others
lines compile. Since exactly four lines do not compile, option # is the correct
answer

11) a
A


12) c
C
sing 8, squawk 2, notes 0
sing 7, squawk 4, notes 11
sing 6, squawk 6, notes 23

13) d
G, 
This example may look complicated, but the code does not compile. Line 8 is 
missing the required parentheses around the boolean conditional expression. Since
the code does not compile and it is not because of line 6, option G is the 
correct answer. If line 8 was corrected with parentheses, then the loop would be
executed twice, and the output would be 11.

keepGoing true, result 15, meters 10
meters 9, kg true, result 13
meters 8, keepGoing false, 11

14) a, b, d, f,
B, D, F,
The code does compile, making option G incorrect. In the first for-each loop, the 
right side of the for-each loop has a type of int[], sot each element penguin has 
type of int, making option B correct. In the second for-each loop, ostrich has a type 
of Character[], so emu has a data type of Character, making option D correct. In the 
last for-each loop, parrots has a data type of List<Integer>. Since the type of 
integer is used in the List, macaw will have a data type of Integer, making option F 
correct.


15) f
F,
The code does not compile, although not for the reason specified on option E. The
second case statement contains invalid syntax. Each case statement must have the 
keyword `case` -- in other words, we cannot chain them with a colon (:). For this 
reason, option F is the correct answer. This line could have been fixed to say case 
'B', 'C' or by adding the case keyword before 'C'; the the rest of the code would 
have compiled and printed `great good` at runtime.


16) a, b,
A, B, D,
To print items in the wolf array in reverse order, the code needs to start with
`wolf[wolf.length-1]` and end with wolf[0]. Option A accomplishes this and is the 
first correct answer. Option B is also correct and is on of the most common ways 
a reverse loop is written. The termination condition is often m >= 0 or m > -1, 
and both are correct. Options C and F each cause an `ArrayIndexOutOfBoundsException`
at runtime. Option D is also correct, as the j is extraneous and can be ignored
in this example. Option E produces an infinite loop.

17) e,
B, E,
The code compiles without issue and prints two distinct numbers at runtime, so 
options G and H area incorrect. The first loop executes a total of five times,
with the loop ending when participants has a value of 10. For this reason, option
 E is correct. In the second loop, animals stars out not less than or equal to 1, 
but since it is a do/while loop, it executes at least once. In this manner, 
animals takes on a value of 3 and the loop terminates, making option B correct.
Finally, the last loop executes a total of two times, with performers starting
with -1, going to 1 at the end of the first loop, and then ending with a value 
of 3 after the second loop, which breaks the loop. This makes option B a correct
answer twice over.

participants 4, animal 2, performers -1
participants = 5
participants = 10
animals 3
performers 3

18) c, e
C, E,
Pattern matching with an if statement is implemented using the `instanceof` operator, 
making option C correct and options A and B incorrect. Option D is incorrect as it 
is possible to access a pattern variable outside the if statement in which it is 
defined. Option E is a correct statement about flow scoping. Option F is incorrect. 
Pattern matching does not support declaring variables in else statements as else 
statements do no have a boolean expression.


19) f
E,
The variable snake is declared within the body of the do/while statement, so it 
is out of scope on line 7. For this reason, option E is the correct answer. If snake were declared before line 3 with a value of 1, then the output would have been 
1 2 3 4 5 - 5.0, and option G would have been the correct answer.

20)
A, E,
The most important thing to notice when reading this code is that the innermost 
loop is an infinite loop. Therefore, you are looking for solutions that skip the 
innermost loop entirely or that exit that loop. Option A is correct, as break L2 
on line 8 causes the second inner loop to exit every time it is entered, skipping 
the innermost loop entirely. For option B, the first continue on line 8 causes 
execution to skip the innermost loop on the first iteration of the second loop 
but not the second iteration of the second loop. The innermost loop is executed 
and with continue on line 12, it produces an infinite loop at runtime, making 
option B incorrect. Option C is incorrect because it contains a compiler error.
the label L3 is not visible outside its loop. Option D is incorrect, as it is 
equivalent to option B since the unlabeled break and continue apply to the nearest 
loop and therefore produce an infinite loop at runtime. Like option A, the continue 
on line 8 allows the innermost loop to be executed the second time the second 
loop is called. The continue L2 on line 12 exits the infinite loop, though, causing control to return to the second loop. Since the first and second loops terminate, 
the code terminates and option E is a correct answer.

choose statements to compile and not produce a infinite loop
4: int height = 1;
5: L1: while (height++ < 10) {
6:   long humidity = 12;
7:   L2: do {
8:     if (humidity-- % 12 == 0) ______;
9:     int temperature = 30;
10:    L3: for (;;) {
11:      temperature++;
12:      if (temperature > 50) ________;
13:    }
14:  } while (humidity > 4);
15:}

21) b
E,
Line 22 does not compile because Long is not a compatible type for a switch 
statement or expression. Line 23 does not compile because it is missing a 
semicolon after "Jane" and a yield statement. Line 24 does not compile because 
it contains an extra semicolon at the end. Finally, lines 25 and 26 do not compile 
because they use the same case value. At least one of them would need to be changed 
for the code to compile. Since four lines need to be corrected, option E is correct.


22) g
E,
The code compile without issue, making options F and G incorrect. Remember, var 
is supported in both switch and while loops, provided the compiler determines that 
the type is compatible with these statements. In addition, the variable one is 
allowed in a case statement because it is a final local variable, making it a 
compile-time constant. The value of tailFeathers is 3, which matches the second 
case statement, making 5 the first output. The while loop is executed twice, with 
the pre-increment operator (--) modifying the value of `tailFeathers` from 3 to 2 
and then to 1 on the second loop. For this reason, the final output is 5 2 1, 
making option E the correct answer.

23) e
F,
Line 19 starts with an else statement, but there is no preceding if statement that 
it matches. For this reason, line 19 does not compile, making option F the correct 
answer. If the else keyword was removed form line 19, then the code snippet would 
print Success.

24) a, d, e
G,
The statement is not a valid for-each loop (or a traditional for loop) since it 
uses a nonexistent in keyword. For this reason, the code does not compile, and 
option G is correct. If the in was changed to a colon (:), then Set, Int[], and 
Collection would be correct.

25) b 
D, 
The code compiles without issue, so option F is incorrect. The viola variable 
created in line 8 is never used and can be ignored. If it had been  used as the 
case value on line 15, it would have caused a compilation error since it is not 
marked final. Since "violin" and "VIOLIN" are not an exact match, the default 
branch of the switch statement is execute at runtime. This execution path 
increments p a total of three times, bringing the final value of p to 2 and 
making option D the correct answer. 

instrument violin;
CELLO cello;
viola viola;
p -1
p 0

26) 
F,
The code snipped does not contain any compilation errors, so option D and E are 
incorrect. There is a problem with this code snippet, though. While it may seem complicated, the key is to notice that the variable r is updated outside of the 
do/while loop. This is allowed from a compilation standpoint, since it is defined 
before the loop, but it means the innermost loop never breaks the termination 
condition r <= 1. AT runtime, this will produce an infinite loop the first time 
the innermost loop is entered, making option F the correct answer.

w 0, r 1
name ""
name "A"
name "AB"
name "ABC"
name "ABCA"

27) a
F,
Line 27 does not compile because the case block does not yield a value if name is 
not equal to Frog. For this reason, option F is correct. Every path within a case 
block must yield a value if the switch expression is expected to return a value.

28) b
F,
based on flow scoping, guppy is the scope after lines 41 - 42 if the type is not 
a String. In this case, line 43 declares a variable guppy that is a duplicate of 
the previously defined local variable defined on line 41. For this reason, the 
code does not compile, and option F is correct. If a different variable name was 
used on line 43, then the code would compile and print Swinm! at runtime with 
the specified input.


29) d
C,
Since the pre-increment operator was used, the first value that will be displayed 
is -1, so option A and B are incorrect. On the second-to-last iteration of the 
loop, y will be incremented to 5, and the loop will output 5. The loop will continue 
since 5 <= 5 is true, and on the last iteration, 6, will be output. At the end of 
this last iteration, the boolean expression 6 <= 5 will evaluate to false, and the 
loop will terminate. Since 6 was the lat value output by the loop, the answer is 
option C.




