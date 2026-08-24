
1) a, e
A, E,
For the first scenario, the answer need to implement `List` because the scenario allows 
duplicates, narrowing ti down to options A dn D. Option A is a better answer than option 
D becaus `LinkedList` is both a `List` and a `Queue`, and we just need a regular list.

For the second scenario, the answer needs to implement `Map` because we are dealing with 
key/value pairs per the unique id field. This narrows id down to options B and E. Since 
the question talks about ordering, we need the `TreeMap`. Therefore, the answer is E.


2) d
C, G,
Line 12 creates a `List<?>`, which means it is treated as if all the elements are of 
type `Object` rather than `String`. Lines 15 and 16 do not compile since they call the 
`String` methods `isEmpty()` and `length()`, which are not defined on `Object`. Line 
13 creates a `List<String>` because `var` uses the type that it deduces from the context. 
Lines 17 and 18 do compile. However, `List.of()` creates an immutable list, so both of 
those lines would throw an `UnsupportedOperationException` if run. Therefore, options C 
and G are correct.


3) b
B
This is a double-ended queue. On lines 4 and 5, we add to the back, giving us [hello, hi]. 
On line 6, we add to the front and have [ola, hello, hi]. On line 7, we remove the first 
element, which is "ola". On line 8, we look at the new first element ("hello") but don't 
remove it. On lines 9 and 10, we remove each element in turn until no elements are left, 
printing "hello" and "hi" together which makes option B the answer.

4) a, b, c, d
B, F,
Option A does not compile because the generic types are not compatible. We could say 
`HashSet<? extends Number> hs2 = new HashSet<Integer>()`; Option B uses a lower bound, 
so it allows superclass generic types. Option C does not compile because the diamond 
operator is allowed only on the right side. Option D does not compile because a `Set` 
is not a `List`. OOption E does not Compile because upper bound are not allowed when 
instantiating the type. Finally, option F does compile because the upper bound is on 
the correct side of the `=`.

5) c,
```
01: public record Hello<T>(T t) {
02:   public Hello(T t) { this.t = t; }
03:   private <T> void println(T message) {
04:     System.out.print(t + "-" + message);
05:   }
06:   public static void main(String[] args) {
07:     new Hello<String>("Hi").print(1);
08:     new Hello("hola").println(true);
09:   }
10: }
```
B,
The record compiles and runs without issue. Line 8 gives a compiler warning for not 
using generics but not a compiler error. Line 7 creates the `Hello` class with the 
generic type `String`. It also passes an `int` to the `println()` method, which gets 
autoboxed into an `Integer`. While the `println()` method takes a generic parameter 
of tye `T`, it is not the same `<T>` defined for the class on line 1. Instead, it is 
a different `T` defined as part of the method declaration on line 3. Therefore, the 
`String` argument on line 7 applies only to the class. The method can take any object 
as a parameter, including autoboxed primitives. Line 8 creates the `Hello`class with 
the generic type `Object`, since no type is specified for that instance. It passes a 
`boolean` to `println()`, which gets autoboxed into a `Boolean`. The result is that 
"hi-1 hola-true" is printed, making option B correct.


6) b, d, e, f 
```
08: public record Platypus(String name, int beakLength) {
09:   @Override public String toString() { return "" + beakLength; }
10: 
11:   public static void main(String[] args) {
12:     Platypus p1 = new Platypus("Paula", 3);
13:     Platypus p2 = new Platypus("Peter", 5);
14:     Platypus p3 = new Platypus("Peter", 7);
15: 
16:     List<Platypus> list = Arrays.asList(p1, p2, p3);
17: 
18:     Collections.sort(list, Comparator.comparing_____);
19: 
20:     System.out.println(list);
21:   }
22: }
```
B, F
We're looking for a `Comparator` definition that sorts in descending order by `beakLength`. 
Option A is incorrect because it sorts in ascending order by `beakLength`. Similarly, option 
C is incorrect because it sorts by `beakLength` in ascending order within those matches 
that have the same name. Option E is incorrect because there is no `thenComparingNumber()` 
method. Option B is a correct answer, as it sorts by `beakLength` ind descending order. 
Options D and F are tricker. First, notice that we can call either `thenComparing()` or 
`thenComparingInt()` because the former will simply autobox the `int` into an `Integer`. 
Then observe what `reversed()` applies to. Option D is incorrect because it sorts by `name` 
in ascending order and only reverses the beak length of those with the same name. Option F 
creates a comparator that sorts by `name` in ascending order and then by beak size in 
ascending order. Finally, it reverses the result. This is just what we want, so option F 
is correct.


7) a, b, c, f,
B, F,
A valid override of a method with generic arguments must have a _return type_ that is 
covariant, with matching generic type parameters. Options D and E are incorrect because 
the _return type_ is too broad. Additionally, the generic arguments must have the same 
signature with the same generic types. This eliminates options A and C. The remaining 
options are correct, making the answer option B and F.

8) c
```
03: public class MyComparator implements Comparator<String> {
04:   public int compare(String a String b) {
05:     return b.toLowerCase().compareTo(a.toLowerCase());
06:   }
07:   public static void main(String[] args) {
08:     String[] values = ["123", "Abb", "aab"];
09:     Arrays.sort(values, new MyComparator());
10:     for (var s : values)
11:       System.out.print(s + " ");
12:   }
13: }
```
what is the result of this program?
A
The array is sorted using `MyComparator`, which sorts the elements in reverse alphabetical 
order in a case-insensitive fashion. Normally, numbers sort before letters. This code 
reverses that by calling the `compareTo()` on `b` instead of `a`. Therefore, option A is 
correct.

9) 
```
02: public class Helper {
03;   public static <U extends Exception> 
04:     void printException(U u) {
05: 
06:     System.out.println(u.getMessage());
07:   }
08:   public static void main(String[] args) {
09:     Helper.___________________________;
10:   }
11: }
```
a. printException(new FileNotFoundException(""))
b. printException(new Exception("B"))
c. <Throwable>printException(New Exception("C"))
d. <NullPointerException>printException(new NullPointerException("D"))
e. printException(new Throwable("E"))
A, B, D,
The generic type must be `Exception` or a subclass of `Exception` since this is an upper 
bound, making options A and B correct. Options C and E are wrong because  `Throwable` is 
a superclass of `Exception`. Additionally, option D is correct despite the odd syntax by 
explicitly listing the type. We should still be able to recognize ti as acceptable.

10) a, b, c, e, f

A, B, E, F,
The `forEach()` method works with a `List` or a `Set`. Therefore, options A and B are 
correct. Additionally, options E and F return a `Set` and cab be used as well. Options 
D and G refer to methods that do not exist. Option C is tricky because a `Map`does 
have a `forEach()` method. However, it uses two lambda parameters rather than one. 
Since there is no matching `System.out.print` method id does not compile.


11) 
B, E,
The `showSize()` method can take any type of `List` since it uses an unbounded wildcard. 
Option A is incorrect because it is a `Set` and not a `List`. Option C is incorrect 
because the wildcard is not allowed to be on the right side of an assignment. Option 
D is incorrect because the generic types are not compatible. Option B is correct because 
a lower-bounded wildcard allows that same type to be the generic. Option E is correct 
because `Integer` is  a subclass of `Number`.

12) 
C,
This question is difficult because it defines both `Comparable` and `Comparator` on the 
same object. The `t1` object doesn't specify a a`Comparator`, so it uses the `Comparable` 
object's `compareTo` method. This sorts by the text instance variable. The `t2` object 
does specify a `Comparator` when calling the constructor, so it uses the `compare()` 
method, which sorts by the `int`. This gives us option C as the answer.


13) 
A
When using `binarySearch()`, the `List` must be sorted in the same order that the 
`Comparator` uses. Since the `binarySearch()` method does not specify a `Comparator` 
explicitly, the default sort order is used. Only `c2` uses that sort order and correctly 
identifies that the value 2 is at index 0. Therefore, option A is correct. The other two 
comparators sort in descending order. Therefore, the precondition for `binarySearch()` 
is not met, and the result is undefined for those two. The two call to `reverse()` are 
just there to distract, since they cancel each other out.

14) 
A, B,
`Y` is both a class and a type parameter. This means that within the class `Z`, when we 
refer to `Y`, it uses the type parameter. All of the choices that mention class `Y` are 
incorrect because it no longer means the `class `Y`. Only options A and B are correct.


15) 
A, C,
A `LinkedList` implements both `List` and `Queue`. The `List` interface has a method 
to remove by index. Since this method exists, Java does not autobox to call the other 
method, making the output [10] and option A correct. Similarly, option C is correct 
because the method to remove an element by index is available on a `LinkedList<Object>` 
(which is what `var` represents here). By contrast, `Queue` has only the remove by 
object method, so Java does autobox there. Since the number 1 is not in the list, Java 
does not remove anything for the `Queue`, and the output is [10, 12].

16) 
E
This question looks like it is about generics, but it's not. It is trying to see whether 
we noticed that `Map`does not have a `contains()` method. It has `containsKey()` and 
`containsValue()` instead, making option E the answer. If `containsKey()` were called, 
the answer would be false because 123 is an `Integer` key in the `Map`, rather than a 
`String`.


17) 
A, E,
The key to this question is keeping track of the types. Line 48 is a `Map<Integer, Integer>`. 
Line 49 builds a `List` out of a `Set` of `Entry` objects. giving us `List<Entry<Integer, Integer>>`. 
This causes a compiler error on line 56 since we can't multiply an `Entry` object by two.

Lines 51 to 54 are all of type `List<Integer>`. The first three are immutable, and the 
one on line 54 is mutable. This means line 57 throws an `UnsupportedOperationException` 
since we attempt to modify the list. Line 58 would work if we could get to it. Since 
there is one compiler error and one runtime error, options A and E are correct.


18) 
B,
When using generic types in a method, the generic specifications goes before the return 
type and option B is correct.

19) 
F
The first call to `merge()` calss the mapping function and adds the number to get 13. 
It then updates the map. The second call to `merge()` sees that the map currently has 
a `null` value for that key. It does not call the mapping function but instead replaces 
it with the new value of 3. Therefore, option F is correct.

20) 
B, D, F,
The `java.lang.Comparable` interface is implemented on the object to compare. It specifies 
the `compareTo()` method, which takes one parameter. The `java.util.Comparator` interface 
specifies the `compare()` method, which takes two parameter. This gives us options B, D, 
and F as the answers.

