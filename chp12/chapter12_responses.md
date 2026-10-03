## Chapter 12: Modules

1) 
E, 
Modules are required to have a `module-info.java` file at the root folder of the 
module. Option E matches this requirement.

2)
B,
Options A, C, and E are incorrect because they refer to directives that don't
exist. The `exports` directive is used when allowing a package to be called by code 
outside of the module, making options B the correct answer. Notice that options 
D and F are incorrect because of `requires`.


3)
G,
The `m` or `module` options is used to specify the module and classes name. The 
`-p` or `--module-path` options is used to specify the location of the modules. Option 
D would be correct if the rest of the command were correct. However, running a program 
requires specifying the package name with periods(.) instead of slashes. Since the 
command is incorrect, option G is correct.

4)
D,
A service consists of the service provider interface and logic to look up implementations 
using a service locator. This makes option D correct. We have to make sure we know that 
the service provider itself is the implementation, which is not considered part of the 
service.

5)
E, F,
Automatic modules are on the module path but do not have a module-info.java file. Named 
modules are on the module path and do have a module-info. Unnamed modules are on the 
classpath. Therefore, options E and F are correct.

6)
A, F,
Options C and D are incorrect because there is no 'use' directive. Options A and F are 
correct because 'opens' is for reflection and 'uses' declares that an API consumes a 
service.

7)
A, B, E,
Any version information at the end of the JAR filename is removed, making options A and 
B correct. Underscores (_) are turned into dots (.), making options C and D incorrect. 
Other special characters like a dollar sign ($) are also turned into dots. However, 
adjacent dots are merged, and leading/trailing dots are remove. Therefore, option E is 
correct.

8)
A, D,
A cyclic dependency is when a module graph forms a circle. Option A is correct because 
the Java Platform Module System. does not allow cyclic dependencies between modules. No 
such restriction exists for packages, making option B incorrect. A cyclic dependency can 
involve two or more modules that require each other, making option D correct, while 
option C is incorrect. Finally, option E is incorrect because unnamed modules cannot 
be referenced from an automatic module.

9)
F,
The 'provides' directive takes the interface name first and the implementing class 
name second and also 'uses with'. Only option F meets these two criteria, making it 
the correct answer.

10)
B, C,
Packages inside a module are not exported by default, making option B correct and 
option A incorrect. Exporting is necessary for other code to use the packages; it 
is not necessary to call the `main()` method at the command line, making option C 
correct and option D incorrect. The module-info.java file has the correct name and 
compiles, making options E and F incorrect.

11)
D, G, H,
Options A, B, E, and F are incorrect because they refer to directives that don't 
exist. The `requires transitive` directive is used when specifying a module to be used 
by the requesting module. Therefore, `dog` needs to specify the transitive relationship, 
and option G is correct. The module `puppy` just needs `requires dog`, and it gets the 
transitive dependencies, making option D correct. However, `requires transitive` does 
everything `requires` does and more, which makes option H the final answer. 

12)
A, B, C, F,
Option D is incorrect because it is a package name rather than a module name. 
Option E is incorrect because `java.base` is the module name, not 'jdk.base'. Option 
G is wrong because we made iu up. Options A, B, C and F are correct.

13)
D,
There is no `getStream()` method on a `ServiceLoader`, making options A and C 
incorrect. Option B does not compile because the `stream()` method returns a list 
of `Provider` interfaces and need to be converted to the `Unicorn` interface we are 
interested in. Therefore, option D is correct.

14)
C
The `-p` option is a shorter form of `--module-path`. Since the same option cannot be 
specified twice, options B, D, and F are incorrect. The `--module-path` options is an 
alternate form of `-p`. The module name and class name are separated with a slash, making 
option C the answer. Note that x-x is legal because the module path is a folder name, 
so dashes are allowed.

15)
B,
A top-down migration strategy first places all JARS on the module path. Then, it 
migrates the top-level module to tbe a named module, leaving the other modules as 
automatic modules. Option B is correct as it matches both of those characteristics.

16)
A,
Since this is a new module, we need to compile it. However, none of the existing modules 
needs to be recompiled, making option A correct. The `service locator` will see the new 
`service provider` simply by having that new `service provider` on the module path.

17)
E,
This is a trick question. An unnamed module doesn't use a `module-info.java` file. 
Therefore, option E is correct. An unnamed module can access an automatic module as 
a regular JAR without involving the `module.info` file.

18)
D,
The `jlink` command created a directory with a smaller Java runtime containing 
just what is needed to run a program. The JMOD format is for native code. Therefore, 
option D is correct.

19)
E,
There is a trick here. A module definition uses the keyword `module` rather than 
`class`. Since the code does not compile, option E is correct. If the code did compile, 
options A and D would be correct.

20)
A,
When running java with the `-d` option, all the required modules are listed. 
Additionally, the `java.base` module is listed since it is include automatically. 
The line ends with `mandated`, making option A correct. The `java.lang` is a trick 
since it is a package that is imported by default in a class rather than a module.

21)
H, 
Another tricky question. The service locator must have a `uses` directive, but 
that is on the service provider interface. No modules need to specify `requires` 
on the service provider since that is the implementation. Since none are correct, 
option H is the answer.

22)
A, F,
An automatic module export all packages, making option A correct. An unnamed module 
is not available to any modules on the `module path`. Therefore, it doesn't export 
any packages, and option F is correct.

23)
E,
The module name is valid, as are the `exports` statement. Lines 4 and 5 are tricky 
because each is valid independently. However, the same module name is not allowed 
to be used in two `requires` statement. The second one fails to compile on line 5, 
making option E the answer.

24)
A,
Since the JAR is on the `classpath`, it is treated as a regular unnamed module even 
though it has a `module-info.java` file inside. Remember from learning about top-down 
migration that modules on the `module path` are not allowed to refer to the classpath, 
making options B and D incorrect. The `classpath` does not have a facility to restrict 
packages, making option A correct and options C and E incorrect.

25)
A, C, D
Options A and C are correct because both the `consumer` and `service locator` depend 
on the `service provider` interface. Additionally, option D is correct because the 
`service locator` must specify that it uses the service provider interface to look it up.
