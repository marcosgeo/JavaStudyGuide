# Chapter 9: Collection and Generics

In this chapter, we will learn _Java Collections Framework_ classes and interfaces we 
need to know. The thread-safe collection types are discussed in Chapter 13, "Concurrency".
Lambdas, method reference and built-in functional interfaces are very used here, so, 
we need to know them very well and we cover them in Chapter 8.

We will discuss the details about Comparator and Comparable. Finally, we will cover 
how to create our own classes and methods that use generics so that the same class 
can be used with many types.


## Using Common Collection APIs

A _collection_ is a group of objects contained in a single object. The _Java Collections_ 
_Framework_ is a set of classes in `java.util` for storing collections. There are four 
main interfaces in the Java Collection Framework.
- `List`: is an ordered collection of elements that allows duplicate entries and 
    are accessed by an index.
- `Set`: is a collection of objects that does not allows duplicate entries.
- `Queue`: is a collection that orders its elements in a specific order for processing.
    A `Deque` is a subinterface of `Queue` that allows access at both ends.
- `Map`: is a collection that maps key to values, with not duplicate keys allowed. The
    elements in a map are key/value pairs, also known as **dictionary**.

The Collection interface, it subinterfaces, and some classes (rounded rect) that 
implement the interfaces (rectangles) are shown in the figure below: 

Note that `Map` doesn't implement `Collection` interface. It is considered part of 
Java Collection Framework even though it isn't technically a `Collection`. But _it_ 
_is a collection, since it contains a group of objects_. The reason they are treated 
differently is that they need different methods due to being key/value pairs.

![Collections Interface](collections.png)

### Using the Diamond Operator

When construction a Java Collection Framework, we need to specify the type that will go 
inside. We could write like this:
```
List<Integer> list = new ArrayList<Integer>();
```

And we might have generics that contain other generics, such this:
```
Map<Long, List<Integer>> mapOfLists = new HashMap<Long, List<Integer>>();
```

Thats a long and duplicated code. We could use the _diamond operator_ (<>) as shorthand 
notation that allows us to omit the generic type from the right side of a statement 
when the type can be inferred. So the two previous sentences can be write like this:
```
List<Integer> list = new ArrayList<>();

Map<Long, List<Integer>> mapOfLists = new HashMap<>();
```

To the compiler, both these declarations and the previous ones are equivalent, but 
the latter, beyond shorter, is easier to read.

The diamond operator cannot be used as tye type in a variable declaration. It can be 
used only on the right side of the assignment operation. This code does not compile:
```
List<> list = new ArrayList<Integer>();    // does not compile

class InvalidUse {
  void use(List<> data) {}    // does not compile
}
```

### Adding Data

The `add()` method inserts a new element int the `Collection` and returns whether it 
was successful. The method signature is this:
```
public boolean add(E element)
```

Here `E` represents the generic type that was used to create the collection. For some 
`Collection` types, `add()` always return `true`. For other types, there is logic as to 
whether the `add()` call was successful.
```
3: Collection<String> list = new ArrayList<>();
4: System.out.println(list.add("Sparrow"));    // true
5: System.out.println(list.add("Sparrow"));    // true
6:
7: Collection<String> set = new HashSet<>();
8: System.out.println(set.add("Sparrow"));    // true
9: System.out.println(set.add("Sparrow"));    // false
```

A `List` allows duplicates, making the return value `true` each time. A `Set` does not 
allow duplicates, so on line 9 Java returns `false` from the `add()` method call.

### Removing Data

The `remove()` method removes a single matching value in the `Collection` and returns 
whether it was successful. The method signature is as follows:
```
public boolean remove(Object object)
```

This time, the `boolean` return value tells us whether a match was removed. Examples:
```
3: Collection<String> birds = new ArrayList<>();
4: birds.add("hawk");     // [hawk]
5: birds.add("hawk");     // [hawk,hawk]
6: System.out.println(birds.remove("cardinal"));    // false
7: System.out.println(birds.remove("hawk"));     // true
8: System.out.println(birds);     // [hawk]
```

Line 6 tries to remove an element that is not in birds. It returns `false` because 
no such element is found. Line 7 tries to remove an element that is in birds, so 
it return `true`. Notice that it removes only one match.

## Counting Elements






