## Chapter 4 -  Core APIs
  - handling date, time, text numeric and boolean values
  - working with Arrays and Collections

## Strings

### StringBuilder methods

*StringBuilder.append* has a lot of signatures

`public StringBuilder append(String str)`  // this is one of those

```
var sb = new StringBuilder().append(1).append('c');
sb.append("-").append(true);
System.out.println(sb);  // 1c-true
```
By having all these method signatures, we can just call append() without having to 
convert our parameter to a String.

*StringBuilder.insert* add characters to the StringBuilder at the requested index and 
returns a reference to the current StringBuilder.

```
3:var sb = new StringBuilder("animals");
4:sb.insert(7, "-");        // sb = animals-
5:sb.insert(0, "-");        // sb = -animals-
6:sb.insert(5, "-");        // sb = -ani-mals-
7:System.out.println(sb);
```

### Deleting contents

```
public StringBuilder delete(int startIndex, int endIndex)
public StringBuilder deleteCharAt(int index)

var sb = new StringBuilder("abcdef");
sb.detete(1,3);         // sb = adef
sb.deleteCharAt(5)      // exception
```

### Replacing portions

The replace method works differently of StringBuilder than it did for String.

```
public StringBuilder replace(int startIndex, int endIndex, String newString)

var builder = new StringBuilder("pigeon dirty");
builder.replace(3, 6, "sty");
System.out.println(builder);    // pigsty dirty

builder.replace(3, 5, "star");
System.out.println(builder);    // pigstary dirty
```
### Formatting Values
There are methods to format String values using formatting flags. Two of the methods 
take the format string as a parameter, and the other uses an instance for that value.

The method parameters are used to construct a formatted String in a single method 
call, rather than via a lost of format and concatenation operations.
```
public static String format(String format, Object args...)
public static String format(Locale loc, String format, Object args...)
public String formatted(Objects args...)
```
Usages:
```
int orderId = 100;
String name = "Joao";
System.out.println(String.format("Hello %s, your order %d is ready", name, orderId));
System.out.println("Hello %s, your order %d is ready".formatted(name, orderId));
```

Common formatting symbols
- %s applies to any type, commonly String values
- %d applies to integer values like int and long
- %f applies to floating-point values like float and double (also %.Nf, N decimals)
- %n inserts a line break using the system-dependent line separator


## Arrays

When creating arrays, we can put the `[]` before or after the name and adding spaces 
is optional. So, all five these statements are valid.

```
int[] numAnimals;
int [] numAnimals2;
int []numAnimals3;
int numAnimals4[];
int numAnimals5 [];
```
The most common is the first one, but w can find the others one in the exam.

### Important Notation
```
int[] ids, types;       // two variables, both array of ints
int ids[], types;       // two variables, the first an array of int, the other an int
```

Consider this:
```
String[] bugs = {"cricket", "beetle", "ladybug"};
```

Arrays does not allocate space for the Strings objects, it allocates space for a 
reference to where the objects are really stored.


### Sorting and Searching
Use the java.utils.Arrays to sort and search in an array. The array need to be sorted 
to be searched, if the array is not sorted, the results are unpredictable.
```
int[] numbers = new int[] {3, 6, 1};
Arrays.sort(numbers);
System.out.println(Arrays.search(numbers, 1));  // 0

int[] numbers2 = new int[] {3, 6, 1};
System.out.println(Arrays.search(numbers, 1)); // unpredictable result;
```
Although we said that the result is unpredictable, in fact *is possible* predict, but 
this is not cover in the exam and is only a logic exercise.

### Comparing

The `compare()` method returns one of the three possible results:
 - a *negative* number means the first array *is smaller* than the second.
 - a *zero* means the arrays are equal.
 - a *positive* number means the first array *is larger* than the second.

```
System.out.println(Arrays.compare(new int[] {1}, new int[] {2}));  // -1
```

When the arrays have different values and/or lengths, this rules are applied:
 - if both arrays are the same length and have the same values in each spot in the same order, return zero.
 - if all the elements are the same but *the second array* has extra elements at the end, return a negative number
 - if all the elements are the same, but *the first array* has extra elements at the end, return a positive number
 - if the first element that differs is smaller in the first array, return a negative number
 - if the first element that differs is larger in the first array, return a positive number

Finally, here are some rules of what **smaller** means:
 - null is smaller than any other value.
 - for numbers, normal numeric order applies.
 - for strings, one is smaller if it is a prefix of another.
 - for strings/characters, number are smaller than letters.
 - for strings/characters, uppercase is smaller than lowercase.


Beyond the rules, the arrays *must be the same type* to be comparable.
```
System.out.println(Arrays.compare(
  new int[] {1}, new char[] {'a'}));  // does not compile
```

The `mismatch()` method returns the first index where two arrays differs. If they are 
equal it returns -1.
```
System.out.println(Arrays.mismatch(
  new int[] {1}, new int[] {1}));  // returns -1

System.out.println(Arrays.mismatch(
  new String[] {"a"}, new String[] {"A"}));  // returns 0
  
System.out.println(Arrays.mismatch(
  new int[] {1, 2}, new int[] {1}));  // returns 1
```

### Using Methods with Varargs
We can call a method that receives an array as arguments using a special notation 
called *varargs* (variable arguments).
```
public static void main(String[] args)
public static void main(String args[])
public static void main(String... args)  // varargs
```

### Multidimensional Arrays
All forms used below compile and will be used in the exam, in real programs we have to  use only with the brackets after the type: `String[]` or `int[][][]`.

```
int[] vars1;        // one dimensional
int[][] vars2;      // two dimensional
int vars3 [][];     // two dimensional
int[] vars4 [];     // two dimensional
int[] vars5[];      // two dimensional
int[] vars6[], space[][];   // two and three dimensional
```

The most common operation on a multidimensional array is to loop through it. This 
example prints a 2D array:
```
var twoD = new int[3][2];
for (int i = 0; i < twoD.length; i++){
    for (int j = 0; j < twoD[i].length; j++)
        System.out.print(twoD[i][j] + ", ");  // print elements
    System.out.println();  // new row
}
```

With an enhanced for loop, is much more easy:
``` 
for (int[] inner : twoD) {
    for (int num : inner)
        System.out.print(num + ", ");
    System.out.println();
}
```
Not fewer lines, but less complex and more readable.


## Math APIs

```
Math.max(3, 7);         // 7
Math.min(3.7, 2.1);     // 2.1
Math.round(4.500);      // 5
Math.round(4.499);      // 4
Math.ceil(3.14);        // 4
Math.floor(3.94);       // 3
Math.pow(5, 2);         // 25.0  note: this method cast the entries do double
```

### Generating Random Numbers
```
double num = Math.random();     // generates a number between 0 and 1

int between0and100inclusive = (int)(Math.random() * 100) + 1;
```

## Working with Dates and Times

Java provides a number of APIs for working with dates and times. There's also an old 
java.util.Date class, but is is not on the exam.

To work with the modern date and time classes, we need to import it.
```
import java.time.*;
```

In American English, the word *data* is used to represent two different concepts. 
Sometimes, it is the month/day/year combination when something happened, such as 
January 1, 2000. Sometimes, is is the day of the month, such as "Today's date is the 
6th.".

That's, the words *day* and *date* are often used as synonyms. Be alert on the exam.

### Creating Dates and Times

When working with dates and times, the first thing to do is to decide how much 
information we need. Java gives us four choices:

- **LocalDate** contains just a date -- no time and time zone
- **LocalTime** contains just a time -- no date and time zone
- **LocalDateTime** contains both date and time but no time zone
- **ZonedDateTime** contains a date, time and time zone.

We obtain data and time instances using static methods:
```
import java.time.*;
System.out.println(LocalDate.now());        // 2026-03-31
System.out.println(LocalTime.now());        // 17:18:23.745
System.out.println(LocalDateTime.now());    // 2026-03-31T17:18:23756
System.out.println(ZonedDateTime.now());    // 2026-03-31T17:18:235-03:00[America/Sao_Paulo]
```

Creating from values
```
var date1 = LocalDate.of(2026, 12, 31);   // 2026-12-31
var date2 = LocalDate.of(2026, Month.JUNE, 30);   // 2026-06-30

var time1 = LocalTime.of(6, 15); // 05:15:00
var time2 = LocalTime.of(6, 15, 30, 200);   //06:15:30.000000200

var localDateTime1 = LocalDateTime.of(2026, 1, 30, 15, 17, 45); // 2026-01-30T15:17:45
var localDateTime2 = LocalDateTime.of(date1, time1); // using previous created objects

```

In order to create a ZonedDateTime, we first need to get the desired time zone:
```
var zone = ZoneId.of("America/Sao_Paulo");
var timeZened = ZonedDateTime.of(2026, 06, 01, 17, 12, 18, 0, zone);
var timeZoned2 = ZonedDateTime.of(date1, time1, zone);
```

Although there are other ways of creating a ZonedDateTime, we only to need to know 
this three signatures for the exam:
```
public static ZonedDateTime of (
  int year, int month, int day, int hour,int min, int sec, int nanos, ZonedId zone)

public static ZonedDateTime of (LocalDate date, LocalTime time, ZoneId zone)

public static SonedDateTime of (LocalDateTime dateTime, ZoneId zone)

```

Notice that there isn't an option to pass in the Month enum. Also, we did not use a 
constructor in any of the examples. The date and time classes have private 
constructors along with static methods that return instances. This is known as the 
*factory pattern*. The exam creator may throw something like this:
```
var d = new LocalDate();
```
This does not compile, is just a trap. Java does not allow to construct a date or 
time object directly from those classes.


### Manipulating Dates and Times

Adding dates is easy. The date and time classes are immutable, so we need to assign 
the results to a reference variable.
```
var date = LocalDate.of(2026, 1, 28);
System.out.println(date);   // 2026-01-28
date = date.plusDays(2);
System.out.println(date);   // 2026-01-30
date = date.plusWeeks(1);
System.out.println(date);    // 2026-02-06
...

date = date.minusMonths(10);  // 2025-04-06
date = date.minusYears(20);  // 2005-04-06
```


