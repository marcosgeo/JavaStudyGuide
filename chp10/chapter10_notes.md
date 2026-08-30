# Chapter 10 - Streams

This chapter add over Lambdas, Functional Interfaces, Collection, and Generics the 
concept of _functional programming_, focusing on the Streams API. Note that this is 
not a stream in the sense of input/output of the `java.io` package. This is another 
type of stream.

In this chapter the `Optional` interface is introduced. Then the concept of _stream_
_pipeline_ and how to tie all together. Functional programming tends to have a steep 
learning  curve but can be very exciting once we get the hang of it.


## Returning an _Optional_

Suppose that we are taking an introductory Java class and receive scores of 90 and 100 
on the first two exams. Now, we want the average. We could easily create a function that 
return the average of two number, but if we have only one, how to handle this? In this 
situation we could use the `Optional` type to return a value or do other thing when not 
all condition as met.

An `Optional` is created using a factory. We can either request an empty `Optional` or 
pass a value for the `Optional` to wrap. An `Optional` could be seen as a kind of a box 
that have something in it or might instead be empty.

**Figure 10.1 - Optional**

![optional box figure](optional.png)


### Creating an _Optional_

Here's how to code our average method
```
10: public static Optional<Double> average(int... scores) {
11:   if (scores.length == 0) return Optional.empty();
12:   int sum = 0;
13:   for (int score : scores) sum += score;
14:   return Optional.of( (double) sum / scores.length );
15: }
```

Line 11 returns an empty `Optional` when we can't calculate an average. Lines 12 and 
13 add up the scores. There is a functional programming way to doing this math, and 
that will be get later in the chapter. In fact, the entire method could be written in 
one line, but that wouldn't shows us how `Optional` works. Line 14 creates an `Optional` 
to wrap the average.

Calling the method shows what is inside out two boxes:
```
System.out.println( average(90, 100) );     // Optional[95.0]
System.out.println( average() );            // Optional.empty
```

WE can see that on `Optional` contains a value and the other is empty. Normally, we 
want to check whether a value is there and/or get it out of the box. Here's on way:
```
Optional<Double> opt = average(90, 100);
if (opt.isPresent())
  System.out.println( opt.get() );     // 95.0
```

First we check wheter the `Optional` contains a value. Then we print it out. What if 
we didn't do the check, and the `Optional` was empty?
```
Optional<Double> opt = average();
System.out.println( opt.get() );     // NoSuchElementException
```

We'd get an exception since there is no value inside the `Optional`.
```
java.util.NoSuchElementException: No value present
```

When creating an `Optional`, it is common to want to use `empty()` when the value is 
`null`. We can do this with an if statement or ternary operator:
```
Optional o = (value == null) ? Optional.empty() : Optional.of(value);
```

If `value` is `null`, `o` is assigned to the empty `Optional`. Otherwise, we wrap the 
`value`. Since this is such a common pattern, Java provides a factory method to do the 
same thing:
```
Optional o = Optional.ofNullable(value);
```

This cover the static methods that we need to know about `Optional`. There area a few 
others that involve chaining, that will be cover later in this chapter. Next we will 
look to the instance methods

**Table 10.1 - Common Optional instance methods**

![optional instance methods](optional_intance_methods.png)

We already seen `get()` and `isPresent()`. The other methods allows us to write code 
that uses an `Optional` in one line without having to use the ternary operator. This 
makes the code easier to read. Instead of using an if statement, which we used when 
checking the average earlier, we can specify a `Consumer` to be run when there is a 
value inside the `Optional`. When there isn't, the method simply skips running the 
`Consumer`.
```
Optional<Double> opt = average(90, 100);
opt.ifPresent(System.out::println);     // 95
```

Using `ifPresent()` better express out intent. We want something done if a value is 
present. We cant think of it as an if statement with no else.


### Dealing with an Empty _Optional_

The remaining methods allows us to specify what to do if a value isn't present. There 
are a few choices. The first wo allows us to specify a return value either directly or 
using a `Supplier`.
```
30: Optional<Double> opt = average();
31: System.out.println( opt.orElse(Double.NaN) );
32: System.out.println( opt.ElseGet( () -> Math.random() ) );
```

This prints something like the following:
```
NaN
0.4977778435122
```

Line 31 shows that we can return a specify values or variable. In our case, we print 
"not a number" value. Line 32 shows using a `Supplier` to generate a value at runtime 
to return instead.

Alternatively, we can have the code throw an exception if the `Optional` is empty:
```
30: Optional<Double> opt = average();
31: System.out.println( opt.orElseThrow() );
```

This prints something like this:
```
Exception in thread "main" java.util.NoSuchElementException:
  No value present
  at java.base/java.util.Optional.orElseThrow(Optional.java:382)
```

Without specifying a `Supplier` for the exception, Java will throw a `NoSuchElementException`. 
Alternatively, we can have the code throw a custom exception if the `Optional`is empty. 
Remembering that the stack trace looks weird because the lambdas are generated rather than 
named classes.
```
30: Optional<Double> opt = average();
31: System.out.println( opt.orElseThrow( 
32:      () -> new IllegalStateException() ) );
```

This prints:
```
Exception in thread "main" java.lang.IllegalStateException
  at optionals.Methods.lambda$orElse$1(Methods.java:31)
  at java.base/java.util.Optional.orElseThrow(Optional.java:408)
```

Line 32 shows using a `Supplier` to create an exception that should be thrown. Notice 
that we do not write `throw new IllegalStateException()`. The `orElseThrow()` method 
takes care of actually throwing the exception when we run it.

The two methods that take a `Supplier` have different names. Why this code does not compile?
```
System.out.println(opt.orElseGet(
    () -> new IllegalStateException() ) );     // does not compile
```

The `opt` variable is an `Optional<Double>`. This means the `Supplier` must return a 
`Double`. Since this `Supplier` return an exception, tye type does not match.

The last example with `Optional` is easy. What this code does?
```
Optional<Double> opt = average(90, 100);
System.out.println( opt.orElse(Double.NaN) );
System.out.println( opt.orElseGet( () -> Math.random() ) );
System.out.println( opt.orElseThrow() );
```

It prints 95.0 three times. Since the value does exist, there is no nee to use the 
"or else" logic.

---
**Is _Optional_ the same as _null_?**

An alternative to `Optional`is to return `null`. There area a few shortcomings with 
this approach. One is that there isn't a clear way to express that `null` might be a 
special value. By contrast, returning an `Optional` is a clear statement in the API 
that there might not be a value.

Another advantage of `Optional` is that we can use a functional programming style 
with `ifPresent()` and the other methods rather than needing an if statement. We 
will see at the end of the chapter how to chain `Optional` calls.
---

## Using Streams

A _stream_ in Java is a sequence of data. A _stream pipeline_ consists of the operations 
that run on a stream to produce a result. First, we will look at the flow of pipelines 
conceptually, than we get into the code.

### Understanding the Pipeline Flow

Think of a stream pipeline as an assembly line in a factory. suppose that we are running 
an assembly line to make signs for the animal exhibits at the zoo. We have a number of 
jobs. It is one person's job to take the signs out of a box. It is a second person's job 
to paint the sign. Its is a third person's job to stencil the name ot the animal on the 
sign. It's the last person's job to put the completed sign in a box to be carried to the 
proper exhibit.

Notice that the second person can't do anything until one sign has been taken out of 
the box by the first person. Similarly, the third person can't do anything until one 
sign has been painted, and the last person cant'd do anything until it is stenciled.

The assembly line for making sign is finite. Once we process the contents of tout box of 
signs, we are finished. _ Finite_ streams have a limit. Other assembly lines essentially 
run forever, like one for food production. Of course, the do stop at some point when the 
factory closes down, but for an inordinately large period of time, they don't. 

Another important feature of an assembly line is that each person touches each element 
to do their operation, and then that piece of data is gone. It doesn't come back. The 
next person deals with it at that point. This is different tha the lists and queues, 
that we can access any element at any time. With streams, the data isn't generated up 
front, it is created when needed. This is an example of _lazy evaluation_, which delays 
execution until necessary.

Many things can happen in the assembly line stations along the way. In functional 
programming, these are called _stream operations_. Just like with the assembly line, 
operations occur in a pipeline. Someone has to start and end the work, and there can 
be any number of stations in between. After all, a job with one person isn't an 
assembly line!. There are three parts to a stream pipeline, as shown in Figure 10.2.
 - **Source**: where the stream comes from
 - **Intermediate operations**: transforms the stream into another one. There can be
    as few or as many intermediate operation as we would like. Since streams use lazy 
    evaluation, the intermediate operations do not run until the terminal operation runs.
 - **Terminal operation**: produces a result. Since streams can be used only once, the 
    stream is no longer valid after a terminal operation completes.

**Figure 10.2 - Stream pipeline**

![stream pipeline](stream_pipeline.png)

Notice that the operations are unknown to us. When viewing the assembly line from the 
outside, we care only about what comes in and goes out. What happens in between is an 
implementation detail.


### Creating Stream Sources

In Java, the streams we have been talking about are represented by the `Stream<T>` 
interface, defined int the `java.util.stream` package.

#### Creating Finite Streams

For simplicity, we start with finite streams. There are a few way to create them.
```
11: Stream<String> empty = Stream.empty();               // count = 0
12: Stream<Integer> singleElement = Stream.of(1);        // count = 1
13: Stream<Integer> fromArray = Stream.of(1, 2, 3);      // count = 3
```

Line 11 shows how to create an empty stream. Line 12 shows how to create a stream 
with a single element. Line 13 shows how to create a stream from a varargs. 

Java also provides a convenient way of converting a Collection to a stream.
```
14: var list = List.of("a", "b", "c", "d");
15: Stream<String> fromList = list.stream();
```

Line 15 shows that it is a simple method call to create a stream from a list. This is 
helpful since such conversions are common.


#### Creating Infinite Streams

What we could do with list is good, but not impressive. We can't create an infinite 
list, though, which makes streams more powerful.
```
17: Stream<Double> randoms = Stream.generate(Math::random);
18: Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2 );
```

Line 17 generates a stream of random numbers. How many random number? How many we need. 
It we call `randoms.forEach(System.out::println)`, the program will print random numbers 
until we kill it. Later in this chapter we will learn about operation like `limit()` to 
turn the infinite stream into a finite stream.

Line 18 gives us more control. The `iterate()` methods takes a seed o starting value as 
first parameter. This is the first element that will be part of the stream. The other 
parameter is a lambda expression that is passed the previous value and generate the next 
value. AS with the random number examples, it will keep on producing odd numbers as long 
as we need them.

What if we want just odd number less than 100? There's an overloaded version of 
`iterate()` that helps:
```
19: Stream<Integer> oddNumbersUnder100 = Stream.iterate(
20:   1,              // seed
21:   n -> n < 100,   // Predicate to specify when done
22:   n -> n + 2      // UnaryOperator to get next value  
23);
```
This method takes three parameters. Notice how they are separated by commas (,) just 
like in all other methods. When in a single line, don't confuse with a for loop and 
not use semicolons.


#### Reviewing Stream Creation Methods

These are the way of creating a source for streams, give a `Collection` instance `coll`.

**Table 10.3 - Creating source**

![creating streams source](stream_creating_source.png)


### Using Common Terminal Operations

We can perform a terminal operation without any intermediate operations but no the 
other way around. This is wny we talk about terminal operations first. _Reductions_ 
area a special type of terminal operation where all of the contents ot the stream are 
combined into a single primitive or `Object`. For example, we might have an `int` or 
a `Collection`.

Table 10.4 summarizes this section, they will be explained from simplest to most complex.

**Table 10.4 - Terminal Stream Operations**

![terminal stream operations](stream_terminal_operations.png)


#### Counting

The `count()` method determines the number of elements in a finite stream. For an 
infinite stream, it never terminates. The `count()` method is a reduction because 
it looks at each element in the stream and returns a single value. The method 
signature is as follows: 
```
public long count()
```

This example shows calling `count()` on a finite stream:
```
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
System.out.println(s.count());     // 3
```

#### Finding the Minimum and Maximum

The `min()` and `max()` methods allow us to pass a custom comparator and find the 
smallest or largest value in a finite stream according to that sort order. Like the 
`count()` method, `min()` and `max()` hang on an infinite stream because the cannot 
be sure that a smaller or larger value isn't coming later in the stream. Both methods 
are reductions because the return a single value after looking at the entire stream. 
The method signatures re as follows:
```
public Optional<T> min(Comparator<? super T> comparator)
public Optional<T> max(Comparator<? super T> comparator)
```

This example finds the animal with the fewest letters in its name:
```
Stream<String> s = Stream.of("monkey", "ape", "bonobo");
Optional<String> min = s.min( (s1, s2) -> s1.length() - s2.length() );
min.ifPresent(System.out::println);     // ape
```

Notice that the code returns an `Optional` rater than the value. This allows the method 
to specify that no minimum or maximum was found. We use the `Optional` method `ifPresent()` 
and a method reference to print out the minimum only if on is found. As an example of 
where there isn't a minimum, let's look at an empty stream:
```
Optional<?> minEmpty = Stream.empty().min( (s1, s2) -> 0 );
System.out.println(minEmpty.isPresent());     // false
```

Since the stream is empty, the comparator is never called, and no value is present 
in the `Optional`.

#### Finding a Value

The `findAny()` and `findFirst()` methods return an element of the stream unless the 
stream is empty. If the stream is empty, they return an empty `Optional`. This is the 
first method we've seen that can terminate with an infinite stream. Since Java generates 
only the amount of stream we need, the infinite stream need to generate only one element.

As its name implies, the `findAny()` method can return any element of the stream. When 
called on the streams we've seen up until now, it commonly returns the first element, 
although this  behavior is not guaranteed.

These methods are terminal operation but no reductions. The reason is that they sometimes 
return without processing all of the elements. This means that they return a value based 
on the stream but do not reduce the entire stream in one value.

These is the method signatures:
```
public Optional<T> findAny()
public Optional<T> findFirst()
```

This examples find an animal:
```
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
Stream<String> infinite = Stream.generate( () -> "chimp" );


s.findAny().ifPresent(System.out::println);           // monkey (usually)
infinite.findAny().ifPresent(System.out::println);    // chimp 
```

Finding any one match is more useful that it sounds. Sometime we just want to sample 
the results and get a representative element, but we don't need to waste the processing 
generating then all.


#### Matching

The `allMatch()`, `anyMatch()`, and `noneMatch()` methods search a stream and return 
information about how the stream pertains to the predicate. These may or may not 
terminate for infinite streams. It depends on the data. Like the find methods, they 
are not reductions because they do not necessarily look at all of the elements.

The methods signatures are as follows:
```
public boolean anyMatch(Predicate<? super T> predicate)
public boolean allMatch(Predicate<? super T> predicate)
public boolean noneMatch(Predicate<? super T> predicate)
```

This examples checks whether animal names begin with letters
```
var list = List.of("monkey", "2", "chimp");
Stream<String> infinite = Stream.generate( () -> "chimp" );
Predicate<String> pred = x -> Character.isLetter( x.charAt(0) );

System.out.println( list.stream().anyMatch(pred) );     // true
System.out.println( list.stream().allMatch(pred) );     // false
System.out.println( list.stream().noneMatch(pred) );    // false
System.out.println( infinite.anyMatch(pred) );          // true
```

This shows that we can reuse the same predicate, but we need a different stream each 
time. The `anyMatch()` method returns `true` because two of the three elements match. 
The `allMatch()` method returns `false` because on doesn't match. The `noneMatch()` 
method also returns `false` because at least one matches. On the infinite stream, one 
match is found, so the call terminates. If `allMatch()` was called, it would run until 
we killed the program.


#### Iterating

As in the Java Collections Framework, it is common to iterate over the elements of 
a stream. As expected, calling `forEach()` on an infinite stream does not terminate. 
Since there is no return value, it is not a reduction. Before use it, we have to 
consider if another approach would be better.

The method signature is as follows:
```
public void forEach(Consumer<? super T> action)
```

Notice that this is the only terminal operation with a return type of void. If we 
want something to happen, we have to make it happen in the `Consumer`. Here's one 
way to print the elements in the stream (other will be shown):
```
Stream<String> s = Stream.of("Monkey", "Gorilla", "Bonobo");
s.forEach(System.out::println);     // MonkeyGorillaBonobo
```

We cant use a traditional for on a stream:
```
Stream<Integer> s = Stream.of(1);
for (Integer i : s) { }     // does not compile
```

So, while in a `Collection` wen can call `for` or `forEach()`, in a `Stream` just 
`forEach()`, be aware.

While `forEach()` sounds like a loop, it is really a terminal operator for streams. 
Streams cannot be used as the source in a for-each loop because the don't implement 
the `Iterable` interface.


#### Reducing

The `reduce()` method combines a stream into a single object. It is a reduction, which 
means it processes all elements. The three method signatures are these:
```
public T reduce(T identity, BinaryOperator<T> accumulator)

public Optional<T> reduce(BinaryOperator<T> accumulator)

public <U> U reduce(
  U identity,
  BiFunction<U,? super T,U> accumulator,
  BinaryOperator<U> combiner
)
```

Whe will taken then one at a time. The most common way of doing a reduction is to start 
with an initial value and keep merging it with the next value. Thing about how we would 
concatenate an array of `String` object into a single `String` without functional 
programming. It might look something like this:
```
var array = new String[] { "w", "o", "l", "f"};
var result = "";
for (var s : array) { result = result + s; }
System.out.println(result);                            // wolf
```

The _identity_ is the initial value of the reduction, in this case an empty `String`. 
The _accumulator_ combines the current result with the current value in the stream. 
With lambdas, we can do the same thing with a stream and reduction:
```
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", (s, c) -> s + c );
System.out.println(word);                              // wolf
```

Notice how we still have the empty `String` as the identity. We also still concatenate 
the `String` object to get the next value. We can even rewrite this with a method 
reference:
```
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", String::concat);
System.out.println(word);                              // wolf
```

Lest's try another one. How we can write a reduction to multiply all of the `Integer` 
object in a stream? One solution:
```
Stream<Integer> stream = Stream.of(3, 5, 6);
System.out.println(stream.reduce(1, (a, b) -> a*b) );   // 90
```

We set the identity to 1 and the accumulator to multiplication. In many cases, the 
identity isn't really necessary, so Java lets us omit it. When we don't specify an 
identity, an `Optional` is returned because there might not be any data. There are 
three choices for what is in the `Optional`:
- If the stream is empty, an empty `Optional` is returned
- if the stream has one element, it is returned
- if the stream has multiple elements, the accumulator is applied to combine them

The following illustrates each of these scenarios:
```
BinaryOperator<Integer> op = (a, b) -> a * b;
Stream<Integer> empty = Stream.empty();
Stream<Integer> oneElement = Stream.of(3);
Stream<Integer> threeElements = Stream.of(3, 5, 6);

empty.reduce(op).ifPresent(System.out::println);          // no output
oneElement.reduce(op).ifPresent(System.out.println);      // 3
threeElements.reduce(op).ifPresent(System.out::println);  // 90
```

Why are there two similar methods? Why not just always require the identity? Java 
could have done that. However, sometimes it is nice to differentiate the case where 
the stream is empty rather than the case where there is a value that happens to match 
the identity being returned from the calculation. The signature returning an `Optional` 
lets us differentiate these cases. For example, we might return `Optional.empty()` 
when the stream is empty and `Optional.of(3)` when there is a value.

The third method signature is used when we are dealing with different types. It allows 
Java to create intermediate reductions and then combine them at the end. Let's take 
a look an an example that counts the number of characters in each `String`.
```
Stream<String> stream = Stream.of("w", "o", "l", "f!");
int length = stream.reduce(0, (i, s) -> i + s.length(), (a, b) -> a + b );
System.out.println(length);        // 5
```

The first parameter, `0`, is the value for the _initializer_. If we had an empty 
stream, this would be the answer. The second parameter is the _accumulator_. Unlike 
the accumulators we previously saw, this one handles mixed data types. In this example, 
the first argument, `i`, is an `Integer`, while the second argument, `s`, is a `String`. 
It adds the length of the current `String`to our running total. The third parameter is 
called the _combiner_, which combines any intermediate totals. In this case, `a` and 
`b` are both `Integer` values. 

The three-argument `reduce()` operation is useful when working with parallel streams 
because it allows the stream to be decomposed and reassembled by separate threads. 
For example, if we needed to count the length of four 100-character strings, the first 
two values and the last two values could b computed independently. The intermediate 
result, 200 + 200, would then be combined into the final value.


#### Collecting

The `collect()` is a special type of reduction called a _mutable reduction_. It is 
more efficient than a regular reduction because we use the same mutable object while 
accumulating. Common mutable object include `StringBuilder` and `ArrayList`. This is 
a really useful method, because it lets us get data out of streams and into another 
form. The method signatures are as follows:
```
public <T> R collect(
  Supplier<T> supplier, 
  BiConsumer<T, ? super T> accumulator,
  BiConsumer<R, R> combiner
)

public <R, A> R collect(Collector<? super T, A, R> collector)
```

Let's start with the first signature, which is used when we want to code specifically how 
collection should work. Our wold example from reduce can be converted to use `collect()`:
```
Stream<String> stream = Stream.of("w", "o", "l", "f");

StringBuilder word = stream.collect(
  StringBuilder::new,
  StringBuilder::append,
  StringBuilder::append
)

System.out.println(word);     // wolf
```

The first parameter is the _supplier_, which creates the object that will store the 
results as we collect. Remembering that a `Supplier` doesn't take any parameters and 
return a value. In this case, it constructs a new `StringBuilder`.

The second parameter is the _accumulator_, which is a `BiConsumer` that takes two 
parameters and doesn't return anything. It is responsible for adding one more element to 
the data collection. In this example, it appends the next `String` to the `StringBuilder`.

The final parameter is the _combiner_, which is another `BiConsumer`. It is responsible 
for taking two data collections and merging them. This is useful when we are processing 
in parallel. Two smaller collections are formed and then merged into one. This would 
work with `StringBuilder` only if we didn't care about the order of the letters. In this 
case, the accumulator and combiner have similar logic.

Now let's look at an example where the logic is different in the accumulator and combiner: 
```
Stream<String> = Stream.of("w", "o", "l", "f");

TreeSet<String> set = stream.collect(
  TreeSet::new,
  TreeSet::add,
  TreeSet::addAll
)
```

The collector has three parts as before. The _supplier_ creates an empty `TreeSet`. The 
_accumulator_ adds a single `String` from the `STream` to the `TreeSet`. The _combiner_ 
adds all of the elements of one `TreeSet` to another in case the operation were done in 
parallel and need to be merged.

We started with the long signature because that's how we implement our own collector. 
It is important to know how to do this to understand how collectors work. In practice, 
many common collectors como up over and over so, Java, rather than making developer 
keep reimplementing the same ones, provides a class with common collectors, named 
`Collectors`, of course. This approach also makes the code easier to read because it 
is more expressive. For example, we could rewrite the previous example as follows:
```
Stream<String> stream = Stream.of("w", "o", "l", "f");
TreeSet<String> set =
  stream.collect(Collectors.toCollect(TreeSet::new));
System.out.println(set);     // [f, l, o, w]
```

if we didn't nee the set to be sorted, we could make the code even shorter:
```
Stream<String> stream = Stream.of("w", "o", "l", "f");
Set<String> set = stream.collect(Collectors.toSet());
System.out.println(set);     // [f, w, l, o]
```

We might get a different output for this last one since `toSet()` makes no guarantees 
as to which implementation of `Set` we'll get. It is likely to be a `HashSet`, but 
we shouldn't expect or rely on that.


### Using Common Intermediate Operations

Unlike a terminal operation, an intermediate operation produces a stream as its result. 
An intermediate operation can also deal with an infinite stream simply by returning 
another infinite stream. Since elements are produced only as needed, this works fine. 
The assembly line worker doesn't need to worry about how many more elements are coming 
through and instead can focus only on the current element.

#### Filtering

The `filter()` method returns a `Stream` with elements that match a given expression. 
Here is the method signature:
```
public Stream<T> filter<Predicate<? super T> predicate
```

This operation is easy to remember and powerful because we can pass any `Predicate` to 
it. For example, this retains all elements that begin with the letter "m":
```
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.filter( x -> x.startsWith("m") )
  .forEach(System.out::print);      // monkey
```

#### Removing Duplicates

The `distinct()` method returns a stream with duplicate values removed. The duplicates 
do not nee to be adjacent to be removed. Java call `equals()` to determine whether the 
objects are equivalent. The method signature is as follows:
```
public Stream<T> distinct()
```

Here an example:
```
Stream<String> s = Stream.of("duck", "duck", "duck", "goose");
s.distinct()
  .forEach(System.out::print);     // duckgoose
```

#### Restricting by Position

The `limit()` and `skip()` methods can make a `Stream` smaller, or `limit()` could 
make a finite stream out of an infinite stream. The methods signatures are these:
```
public Stream<T> limit(long maxSize)
public Stream<T> skip(long n)
```

The following code create an infinite stream of number counting from 1. The `skip()` 
operation return an infinite stream starting with the numbers counting from 6, since 
it skips the first five elements. The `limit()` call takes the first two of those. Now 
we have a finite stream with two elements, which we can then print with the `forEach()` 
method:
```
Stream<Integer> s = Stream.iterate(1, n -> n + 1);
s.skip(5)
  .limit(2)
  .forEach(System.out::print);    // 6 7
```

#### Mapping

The `map()` method creates a one-to-one mapping from the elements in the stream to 
the elements of the next step in the stream. The method signature is as follows:
```
public <R> Stream<R> map(Function<? super T, ? extends R> mapper)
```

This one looks more complicated than the others. It uses the lambda expression to 
figure out the type passed to that function and the on returned. The return type is 
the stream that is returned.

---
The `map()` method on streams is for transforming data. Don't confuse it with the 
`Map` interface, which maps keys to values.
---

As an example, this code converts a list of `String` objects to a list of `Integer` 
objects representing their lengths:
```
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.map(String::length)
  .forEach(System.out::print);     // 6 7 6
```

Remembering that `String::length` is shorthand for the lambda `x -> x.length()`, which 
clearly shows it is a function that turns a `String` into an `Integer`.


#### Using _flatMap_

The `flatMap()` method takes each element in the stream and makes any elements it 
contains top-level elements in a single stream. This is helpful when we want to 
remove empty elements from a stream or combine a stream os lists. The method signature 
will be shown here only for consistency with other methods, but we don't need to read 
this nor will be asked to explain it:
```
public <R> Stream<R> flatMap(
  Function<? super T, ? extends Stream<? extends R>> mapper)
```

This gibberish basically says that it returns a `Stream` of the type that fhe function 
contains at a lower level.

What we should understand is the example. this gets all of the animals int the same 
level and removes the empty list.
```
List<String> zero = List.of();
var one = List.of("Bonobo");
var two = List.of("Mama Gorilla", "Baby Gorilla");
Stream<List<String>> animals = Stream.of(zero, one, two);

animals.flatMap( m -> m.stream())
  .forEach(System.out::println);
```
The output:
```
Bonobo
Mamma Gorilla
Baby Gorilla
```

As we can see, it removed the empty list completely and changed all elements of each 
list to be at the top level of the stream.

---
**Concatenating Streams

While `flatMap()` is good for the general case, there is a more convenient way to 
concatenate two streams:
```
var one = Stream.of("Bonobo");
var two = Stream.of("Mamma Gorilla", "Baby Gorilla");

Stream.concat(one, two)
  .forEach(System.out::println);
```

This produces the same three lines as the previous example. The two streams are 
concatenated, and the terminal operation, `forEach()`, is called.
---

#### Sorting

The `sorted()` method returns a stream with the elements sorted. Just like sorting 
arrays, Java uses natural ordering unless we specify a comparator. The method signatures 
are these:
```
public Stream<T> sorted()
public Stream<T> sorted(Comparator<? super T> comparator)
```

Calling the first signature uses the default sort order.
```
Stream<String> s = Stream.of("brown-", "bear-");
s.sorted()
  .forEach(System.out::print);    // bear-brown
```

We can optionally use a `Comparator` implementation via a method or a lambda. In this 
example, we are using a method:
```
Stream<String> s = Stream.of("brown bear", "grizzly-");
s.sorted(Comparator.reverseOrder())
  .forEach(System.out::print);     // grizzly-brown bear-
```

Here we pass a `Comparator` to specify that we want to sort in the reverse of natural 
sort order. Now a tricky one. Why this doesn't compile?
```
Stream<String> = s Stream.of("brown bear-", "grizzly-");
s.sorted(Comparator::reverseOrder);     // does not compile
```

Let's take a look at the second `sorted()` method signature again. It takes a `Comparator`, 
which is a functional interface that takes two parameter and returns an `int`. However, 
`Comparator::reverseOder` doesn't do that. Because `reverseOrder()` takes no arguments and 
returns a value, the method reference is equivalent to `() -> Comparator.reverseOrder()`, 
which is really a `Supplier<Comparator>`. This is not compatible with `sorted()`. This 
shows us that is really important to know method reference well.


#### Taking a Peek



## Working with Primitive Streams


## Working with Advanced Stream Pipelines Concepts


## Summary