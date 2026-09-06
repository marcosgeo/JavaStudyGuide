## Chapter 10: Streams - Responses

1) D
No terminal operation is called, so the stream never executes. The first line create 
an infinite stream reference. If the stream were executed on the second line, it would 
get the first two elements from that infinite stream, "" and "1", and add an extra 
character, resulting in "2" and "12", respectively. Since the stream is not executed, 
the reference is printed instead, giving us option D.



2) F
Both streams created in this code snippet are infinite streams. The variable `b1` is 
set to true since `anyMatch()` terminates. Even though the stream is infinite, Java 
finds a match on the first element and stops looking. However, when `allMatch()` runs, 
it needs to keep going until the end of the stream since ti keeps finding matches. 
Since all elements continue to match, the program hangs making options F the answer.



3) E
An infinite stream is generated where each element is twice as long as the previous one. 
While this code uses the three-parameter `iterate()` method, the condition is never false. 
The variable `b1` is set to false because Java finds an element that matches when it gets 
to the element of length 4. However, the next line tries to operate on the same stream. 
Since streams can be used only once, this throws an exception that the "stream has 
already been operated upon or closed" and making option E the answer. If two different 
streams were used, the result would be option B.



4) A, B
Terminal operations are the final step in a stream pipeline. Exactly one is required, 
because it triggers the execution of the entire stream pipeline. Therefore, options A and 
B are correct. Option C is true of intermediate operations rather than terminal operations. 
Option D is incorrect because `peek()` is an intermediate operation. Finally, option E is 
incorrect because once a stream pipeline is run, the `Stream` is marked invalid.



5) C, F
This question is a lot of reading. Remember to look for the differences between options 
rater than studying each line. The options all have much in common. All of them start out 
with a `LongStream` and attempt to convert it to an `IntStream`. However, options B and E 
are incorrect because they do not cast the `long` to an `int`, resulting in a compiler 
error on the `mapToInt()` calls. 

Next, we hit the second difference. Options A and D are incorrect because they 
are missing `boxed()` before the `collect()` call. Since `groupingBy()` is creating a 
`Collection`, we need a non-primitive `Stream`. The final difference is that option F 
specifies the type of `Collection`. This is allowed, though, meaning both options C 
and F are correct.



6) A
Options C and D do not compile because these methods do not take a Predicate parameter 
and do not return a boolean. When working with streams, it is important to remember the 
behavior of the underlying functional interfaces. Options B and E are incorrect. While 
the code compiles, ti runs infinitely. The stream has no way to know that a a match won't 
show up later. Option A is correct because it is safe to return false as soon as one 
element passes through the stream that doesn't match.



7) F
There is no `Stream<T>` method called `compare()` or `compareTo()`, so options A 
through D can be eliminated. The `sorted()` methods is correct to use in a stream 
pipeline to return a sorted `Stream`. The `collect()` method can be used to turn the 
stream into a `List`. The `collect()` method requires a collector be selected, 
making option E incorrect and option F correct.



8) D, E
The `average()` method returns an `OptionalDouble` since averages of any type can 
result in a fraction. Therefore, options A and B are both incorrect. The `findAny()` 
method return an `OptionalInt` because there might no be any elements to find. Therefore, 
option D is correct. The `sum()` method returns an int rather than an `OptionalInt` 
because the sum of an empty list is zero. Therefore, option E is correct.



9) B, D
Lines 4--6 compile and run without issue, making option F incorrect. Line 4 creates 
a stream of elements [1, 2, 3]. Line 5 maps the stream to a new stream with values 
[10, 20, 30]. Line 6 filters out all items not less than 5, which in this case result 
in an empty stream. For this reason, `findFirst()` return an empty `Optional`. Option 
A does not compile. It would work for a `Stream<T>` object, but we have a `LongStream` 
and therefore need to call `getAsLong()`. Option C also does not compile, as it is 
missing the `::` that would make it a method reference. Options B and D both compile 
and run without error, although neither produces any output at runtime since the 
stream is empty.



10) F
Only one of the method calls, `forEach()`, is a terminal operation, so any answer in 
which M is not the last line will not execute the pipeline. This eliminates all but 
options C, E, and F. Option C is incorrect because `filter()` is called before `limit()`. 
Since none of the elements of the stream meets the requirement for the `Predicate<String>`, 
the `filter()` operation will run infinitely, never passing any elements to `limit()`. 
Option E is incorrect because there is no `limit()` operation , which means that the code 
would run infinitely. Only option F is correct. It first limits the infinite stream to 
a finite stream of ten elements and then prints the result.



11) B, C, E
As written, the code doesn't compile because the `Collectors.joining()` expects to 
get a `Stream<String>`. Option B fixes this, at which point noting is output because 
the collector creates a `String`without outputting the result. Option E fixes this and 
causes the output to be 11111. Since the post-increment operator is used, the stream 
contains an infinite number of the character 1. Option C fixes this and causes the 
stream to contain increasing numbers.



12) F
The code does not compile because `Stream.concat()` takes two parameters, not the 
three provided. This makes the answer option F.



13) F
If the `map()` and `flatMap()` calls were reversed, option B would be correct. In 
this case, the `Stream` created from the source is of type `Stream<List>`. Trying to 
use the addition operator, `+`, on a `List`is not supported in Java. Therefore, the 
code does not compile, and option F is correct.



14) B, D
Line 4 creates a Stream and use auto-boxing to put the Integer wrapper of 1 inside. 
Line 5 does not compile because `boxed()`is available only on primitive streams like 
`IntStream`, not `Stream<Integer>`. This makes option B on answer. Line 6 converts to 
a double primitive, which works since `Integer` can be un-boxed to a value that can be 
implicitly cast to a double. Line 7 does not compile for two reasons, making option D the 
second answer. First, converting from a double to an int would require an explicitly cast. 
Also, `matToInt()` returns an `IntStream`, so the data type of `s2` is incorrect. The 
rest of the lines compile without issue.



15) B, D
Options A and C do not compile because they are invalid generic declarations. Primitives 
are not allowed as generics, and `Map` must have two generic types parameters. Option E 
is incorrect because partitioning only gives a `Boolean` key. Options B and D are correct 
because they return a `Map` with a `Boolean` key and a value type that can be customized 
to any `Collection`.



16) B, C
First, this mess of code does compile. While it starts with an infinite stream on 
line 23, it becomes finite on line 24 thanks to `limit()`, making option F incorrect. 
The pipeline preserves only non-empty elements on line 25. Since there aren't any of 
those, the pipeline is empty. Line 26 converts this to an empty map. 

Lines 27 and 28 create a `Set` with no elements and then another empty stream. Lines 
29 and 30 convert the generic type of the `Stream` to `List<String>` and then `String`. 
Finally, line 31 gives us another `Map<Boolean, List<String>>`.

The `partitioningBy()` operation always returns a map with two `Boolean` keys, even 
if there are no corresponding values. Therefore, options B is correct if the code is 
kept as is. By contrast, `groupingBy()` return only keys that are actually needed, 
making option C correct if the code is modified on line 31.



17) D
The terminal operation is `count()`. Since there is a terminal operation, the 
intermediate operations run. The `peek()` operation comes before the `filter)`, 
so both numbers are printed, making option D the answer. After the `filter()`, the 
`count()` happens to be 1 since on of the numbers is filtered out. However, the 
result of the stream pipeline isn't stored in a variable or printed, and it is ignored. 



18) D
This compiles, ruling out options E, F, and G. Since line 29 filters by names starting 
with E, that rules out options A and B. Finally, line 31 counts the entire list, which 
is of size 2, giving us option D as the answer.



19) B
Both lists and streams have `forEach()` methods. There is no reason to collect into 
a list just to loop through it. Option A is incorrect because it does not contain a 
terminal operation or print anything. Options B an C both work. However, the question 
asks about the simplest way, which is option B.



20) C, E, F
Options A and B compile and return an empty string without throwing an exception, using 
a `STring`and `Supplier` parameter, respectively. Option G does not compile as the `get()` 
method does not take a parameter. Options C and F throw a `NoSuchElementException`. Option 
E throws a `RuntimeException`. Option D looks correct but will compile on if the throw is 
removed. Remember, the `orElseThrow()` should get a lambda expression or method reference 
that _returns_ an exception, not on that _throws_ an exception.



21) B
we start with an infinite stream where each element is x. The `spliterator()` 
method is a terminal operation since it return a `Spliterator` rater than a `Stream`. 
The `tryToAdvance()` method gets the first element and prints a single x. The `trySplit()` 
method takes a large number os elements from the stream.  Since this is an infinite stream, 
it doesn't attempt to take half. Then `tryAdvance()` is called on the new split variable, 
and another x is printed. Since there are two values printed, option B is correct. 

