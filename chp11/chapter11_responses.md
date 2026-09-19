## Chapter 11: Exceptions and Localization


1) 
A, C, D, E
A method that declares an exception isn't required to throw one, making option A 
correct. Unchecked exceptions can be thrown in any method, making options C and E 
correct. Option D matches the exception type declared, so it's also correct. 
Options B is incorrect because a broader exceptions is not allowed.



2) 
F, 
The code does not compile because the throw and throws keywords are incorrectly 
used on lines 6, 7, and 9. If the keywords were fixed, the rest of the code would 
compile and print a stack trace with YesProblem at runtime. For this reason, 
option F is correct.


3) 
A, D, E,
Localization refers to user-facing elements. Dates, currency, and numbers are 
commonly used in different formats for different countries, making options A, D, 
and E correct. Class and variable names, along with lambda expression, are internal 
to the application, so there is no need to translate them for users.


4) 
E, 
The order of catch blocks is important because the're checked in the order 
they appear after the try block. Because ArithmeticException is a child class of 
RuntimeException, the catch block on line 7 iis unreachable. Line 7 generates a 
compiler error because it is unreachable code, making option E correct.


5) 
C, F, 
The code compiles and runs without issue. When a CompactNumberFormat instance 
is requested without a style, it uses the SHORT style by default. This results in both 
of the first two statements printing 100K, making option C correct. If the LONG style 
were used, then 100 thousand would be printed. Option F is also correct, as the full 
value is printed with a currency formatter.


6) 
E,
A LocalDate does not hav a time element. Therefore, a data/time formatter is not 
appropriate. The coded compiles but throws an exception at runtime, making option E 
correct. I ISO_LOCAL_DATE were used, the code wound print 2022 APRIL 30.


7) 
E, 
The first compiler error is on line 12 because each resource in a try-with-statement 
must have its own data type and be separated by a semicolon (;). Line 15 does not compile 
because the variable `s` is already declared in the method. Line 17 also does not compile. 
The FileNotFoundException, which inherits from IOException and Exception, is a checked 
exception, so it must be handled in a try/catch block or declared by the method. Because 
the three lines of code do not compile, option E is the correct answer.


8) 
C, 
Java will first look for the most specific matches it can find, starting with 
Dolphins_en_US.properties. Since that is not an answer choice, it drops the country 
and looks for Dolphins_en.properties, making option C correct. Option B is incorrect 
because a country without a language is not a valid locale.


9) 
D, 
When working with a custom number formatter, the 0 symbol displays the digit as 0, 
even if it's not present, while the # symbol omits the digit fromm start or end of the 
String if it is not present. Based on the requested output, a String that displays at 
least three digits before the decimal (including a comma) and ate least on after the 
decimal is required. It should display a second digit after the decimal if on is 
available. For this reason, option D is the correct answer.


10) 
B, 
An IllegalArgumentException is used when an unexpected parameter is passed into a 
method, making option B correct. Option A is incorrect because return null or -1 is a 
common return value for searching for data. Option D is incorrect because a for loop is 
typically used for this scenario. Option E is incorrect because we should find out how 
to code the method and not leave it for the unsuspecting programmer who calls our 
method. Option C is incorrect because we should run!


11) 
B, E, F,
An exception that must be handled or declared is a checked exception. A checked 
exception inherits Exception bu not RuntimeException. The entire hierarchy counts, so 
options B and E are both correct. Option F is also correct, as a class that inherits 
Throwable but not RuntimeException or Error is also checked.


12) 
B, C,
The code does not compile as is because the exception declared by the close() method 
must be handled or declared. Option A is incorrect because removing the exception from 
the declaration causes a compilation error on line 4, as FileNotFoundException is a 
checked exception that must be handled or declared. Option B is correct because the 
unhandled exception within the main() method becomes declared. Option C is also correct 
because the exception becomes handled. Option D is incorrect because the exception 
remains unhandled.


13) 
A, B,
A try-with-resources statement does not require a catch or finally block. A traditional 
try statement requires at least one of the two. Neither statement can be written without 
a body encased in braces, {}. For these reason, options A and B are correct.


14) 
C,
Starting with Java 15, NullPointerException stack traces include the name of the 
variable that is null by default, making option A incorrect. The first NullPointerException 
encountered at runtime is when dewey.intValue() is called, making option C correct. Options 
E and F are incorrect as only one NullPointerException exception can be thrown at a time.


15) 
C, D, 
The code compiles with the appropriate input, so option G is incorrect. A locale 
consists of a required lowercase language code and optional uppercase country code. 
In the Locale() constructor, the language code is provided first. For these reasons, 
options C and D are correct. Option E is incorrect because a Locale is created using 
a constructor or Locale.Builder class. Option F is really close but is missing 
build() at the end. Without that, option F does not compile.


16) 
F, 
The code compiles, but the first line produces a runtime exception regardless of 
what is inserted into the black, making option F correct. When creating a custom 
formatter, any non-symbol code must be properly escaped using paris of single quotes ('). 
In this case, it fails because `o` is not a symbol. Even if we didn't know `o` wasn't 
a symbol, the code contains an unmatched single quote. If the properly escaped value 
of "hh' o''clock'" were used, then the correct answers would be ZonedDateTime. 
Option B would bot be correct because LocaleDate values do not have an hour part.

17) 
D, F, 
Option A is incorrect because Java will look at parent bundles if a key is not 
found in a specified resource bundle. Option B is incorrect because resource bundles 
are loaded from static factory methods. Option C is incorrect, as resource bundle values 
are read from the `ResourceBundle` object directly. Option D is correct because the 
locale is changed only in memory. Option E is incorrect, as the resource bundle for 
the specified locale (or its locale without a country code). Finally, option F is 
correct. The JVM will set a default locale automatically.


18) 
C,
After bot resources are declared and created in the try-with-resources statement, 
T is printed as part of the body. Then the try-with-resources completes and closes the 
resources in the reverse of the order in which they wre declared. After W is printed, 
and exception is thrown. However, the remaining resource still needs to be closed, so 
D is printed. Once all the resources are closed, the exception is thrown and swallowed 
in the catch block, causing E to be printed. Last, the finally block is run, printing 
F. Therefore, the answer is TWDEF and option C is correct.

19) 
D,
Java will use Dolphins_fr.properties as the matching resource bundle on line 7 because 
it is an exact match on the language of the requested locale. Line 8 finds a matching key 
in this file. Line 9 does not find a match in that file; therefore, it has to look higher 
up in the hierarchy. Once a bundle is chosen, only resources in that hierarchy. Once a 
bundle is choses, only resources in that hierarchy are allowed. It cannot use the default 
locale anymore, but it can use the default resource bundle specified by 
Dolphins.properties. For these reasons, option D is correct.


20) 
G, 
The `main()` method invokes `go()` and A is printed on line 3. The `stop()` method
is invoked, and E is printed on line 14. Line 16 throws an NullPointerException, so 
`stop()` immediately ends, and line 17 doesn't execute. The exception isn't caught 
in `go()` method as well but not before its finally block executes and C is printed 
on line 9. Because `main()` doesn't catch the exception, the stack trace displays, 
and no further output occurs. For these reasons, AEC is printed followed by stack 
trace for a NullPointerException, making option G correct.

21) 
C,
The code does not compile because the multi-catch block on line 7 cannot catch 
both a superclass and a related subclass. Option A and B do no address this problem, 
so they are incorrect. Since the try body throws SneezeException, it can be caught 
in a catch block, making option C correct. Option D allows the catch block to compile 
but causes a compiler error on line 6. Both of the custom exceptions are checked and 
must be handled or declared in the `main()` method. A SneezeException is not a 
SniffleException, so the exception is not handled. Likewise, option E leads to 
an unhandled exception compiler error on line 6.


22) 
B,
For this question, the date used is April 5, 2022 at 12:30:20pm. The code 
compiles, and either form of the formatter is correct: `dateTimeFormat(formatter)` or 
`formatter.format(dateTime)`. The custom format `m` returns the minute, so 30 is output 
first. The next line throws an exception as `z` relates to time zone, and date/time not 
have a zone component. This exception is then swallowed by the try/catch block. Since 
this is the only value printed, option B is correct. If the code had not thrown an 
exception, the last line would have printed 2022.

23) 
A, E, 
Resources mut inherit AutoCloseable to be used in a try-with-resources block. Since 
Closeable, which is used fo I/O classes, extends AutoCloseable, bot may be used, making 
options A and E correct.


24) 
G,
The code does not compile because the resource `walk1` is not final or effectively 
final and cannot be used in the declaration of a try-with-resources statement. For this 
reason, option G is correct. It the line that set `walk1` to null were removed, then the 
code would compile and print `blizzard 2` at runtime, with the exception inside the try 
block being the primary exception since it is thrown first. Then two suppressed 
exceptions would be added to it when trying to close the AutoCloseable resources.

25) 
A, 
The code compiles and prints the value for Germany, 2,40 €, making option A the 
correct answer. Note that the default locale category is ignored since an explicit 
currency locale is selected.


26) 
B, F, 
The try block is not capable of throwing an IOException, making the catch block 
unreachable code and option A incorrect. Options B and F are correct, as both are 
unchecked exceptions that do not extend or inherit from IllegalArgumentException. We 
must remember that is ot a good idea to catch Error in practice, although because it 
is possible, it may come up on the exam. Option C is incorrect because the variable 
`c` is declared already in the method declaration. Option D is incorrect because the 
IllegalArgumentException inherits from RuntimeException, making the first declaration 
unnecessary. Similarly, option E is incorrect because NumberFormatException inherits 
from IllegalARgumentException, making the second declaration unnecessary. Since 
options B and F are correct, option G is incorrect.
