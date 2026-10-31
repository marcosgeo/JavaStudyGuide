# Chapter 14: Input/Output

**Objective in this chapter:**
- Using Java I/O API
  - Read and write console and file data using I/O Stream
  - Serialize and de-serialize Java objects
  - Create, traverse, read, and write `Path` objects and their properties using 
    `java.nio.file` API


What can Java application do outside the scope of managing objects and attributes 
in memory? How can they save data so that information is not lost every time the program 
is terminated? They use files, of course! We can design code that writes the current state 
of an application to a file every time the application is closed and then reloads the data 
when the application is executed the next time. In this manner, information is preserved 
between program executions.

This chapter focuses on using I/O (input/output) and NIO.2 (non-blocking I/O) APIs to 
interact with files and I/O streams. The preferred approach for working with files and 
directories with newer software applications is  to uses  NIO.2  rather than  I/O  where 
possible. However, we'll see that the two relate, and both are in wide use.

We start by describe how files and directories are organized within a file system and 
show how to access them with the `File` class and `Path` interface. Then we show how to 
work with files and directories. Ws conclude this chapter with advanced topics like 
serializing data, discussing ways of reading user input at runtime using the `Console` 
class, and interacting with attributes.

[Referencing Files and Directories](#referencing-files-and-directories)

[Introducing I/O Streams](#introducing-io-streams)



## Referencing Files and Directories

We begin this chapter by reviewing what files and directories are within a file system. 
We also present the `File` class and `Path`interfaces along with how to create them.


### Conceptualizing the File System

Data is stored on persistent storage devices, such as hard disk drives and memory cards. 
A _file_ within the storage device holds data. Files are organized into hierarchies using 
directories. A _directory_ is a location that can contain files and other directories. 
When working with directories in Java, we often treat them like files. In fact, we use 
many of the same classes and interfaces to operate on files and directories. For example, 
a file and directory both can be renamed with the same Java method. We have to note that 
we often say _file_ to mean _file_ or _directory_ along this chapter.

To interact with files, we need to connect to fhe file system, The _file system_ is 
in charge of reading and writing data within a computer. Different operating systems 
use different file systems to manage their data. For example, Windows-based system use 
a different file system than Unix-based ones. We ust need to know how to issue command 
using the Java APIs. The JVM will automatically connect to the local file system, allowing 
us to perform the same operations across multiple platforms.

Next, the _root directory_ is the topmost directory in the file system, from which 
all files and directories inherit. In Windows, it is denoted with a drive letter such 
as C:\, while on linux, it is denoted with a single forward slash, /.

A _path_ is a representation of a file or directory within a file system. Each file 
system defines its own path separator character that is used between directory entries. 
The value to the left of a separator is the parent of the value to the right of the 
separator. For example, the path value `/user/home/zoo.txt` means that the file `zoo.txt` 
is inside the `home` directory, with the `home` directory inside the `user` directory.

In the Figure 14.1, a directory and file is is organized in a hierarchical manner.

![directory file hierarchy](directory_file_hierarchy.png)

**Figure 14.1: Directory and file hierarchy**

This diagram shows the root directory, `c:`, containing two directories, `app` and `zoo`, 
along with the file `info.txt`. Within the `app` directory, there are two more folders, 
`animals` and `employees`, along with the file `java.exe`. Finally, the `animals` 
directory contains two files, `Bear.java` and `Bear.class`.

We use both absolute and relative paths to fhe file or directory within the file system. 
The _absolute path_ of a file or directory is the full path from the root directory to 
the file or directory, including all subdirectories that contain the file or directory. 
Alternatively, the _relative path_ of a file or directory is th path from the current 
working directory to the file or directory. For example, the following is in absolute 
path to the `Bear.java` file:
```
C:\app\animals\Bear.java
```

The following is a relative path to the same file, assuming the user's current directory 
is set to `c:\app`:
```
animals\Bear.java
```

Determining whether a path is relative o absolute is file-system dependent. To match the 
exam, we adopt the following conventions:
- If a path starts with a forward slash (/), it is absolute, with / as the root directory, 
    such as _/bird/parrot.png_.
- If a path stars with a driver letter (c:), it is absolute, with the drive letter as the 
    root directory, such as c:/bird/info.
- Otherwise, tis is a relative path, such as _bird/parrot.png_.


Absolute and relative path can contain path symbols. A _path symbol_ is one of a reserved 
series of character with special meaning in some file systems. The two most common path 
symbols are shown in Table 14.1.

**Table 14.1: File-system symbols**

![file system symbols](file_system_symbols.png)

Looking at Figure 14.2, suppose the current directory is _/fish/shark/hammerhead_. In 
this case, _.../swim.txt_ is a valid relative path equivalent to _/fish/shark/swim.txt._ 
Likewise, _./play.png_ refers to _play.png_ in the current directory. The symbols can 
also be combined for greater effect. For example, _../../clownfish_ is a relative path 
equivalent to _/fish/clownfish_ within the file system.

![relative paths using symbols](relative_paths_using_symbols.png)

**Figure 14.2: Relative paths using path symbols**


Sometimes, we'll see path symbols that are redundant or unnecessary. For example, 
the absolute path _/fish/clownfish/../shark/./swim.txt_ can be simplified to 
_/fish/shark/swim.txt_. We see how to handle these redundancies later in the chapter 
when we cover `normalize()`.

A _symbolic link_ is a special file within a file system that server as a 
reference or pointer to another file or directory. Suppose we have a symbolic link 
from /zoo/user/favorite to /fish/shark. The `shark` folder and its elements can be 
accessed directly or via the symbolic link. For example, the following paths reference 
the same file:
```
/fish/shark/swim.txt
/zoo/user/favorite/swim.txt
```

In general, symbolic links are transparent to the user, as the operating system takes 
care of resolving the reference to the actual file. While the I/O APIs do not support 
symbolic links, NIO.2 includes full support for creating, detecting, and navigating 
symbolic links within the file system.


### Creating a _File_ or _Path_

In order to do anything useful, we first need an object that represents the path 
to a particular file or directory on the file system. Using legacy I/O, this is the 
`java.io.File` class, whereas with NIO.2, it is the `java.nio.file.Path` interface. 
The `File` class and `Path` interface cannot read or write data directly within a file, 
although they are passed as a reference to other classes, they are just references.


#### Creating a _File_

The `File` class is created by calling its constructor. This code shows three 
different constructors:
```
File zooFile1 = new File("/home/tiger/data/stripes.txt");
File zooFile2 = new File("/home/tiger", "data/stripes");

File parent = new File("/home/tiger");
File zooFile3 = new File(parent, "/data/stripes.txt");

System.out.println(zooFile.exists());
```

All three create a `File` object that points to the same location on disk. If we 
passed `null` as the parent to the final constructor, it would be ignored, and the 
method would behave the same way as the single `String` constructor. For fun, we 
also show how to tell if the file exists on the file system.


#### Creating a _Path_

Since `Path` is an interface, we can't create an instance directly. Java provides 
a number of class and methods that we can use to obtain `Path` objects. The simplest 
and most straightforward way to obtain a `Path` object is to use a static factory 
method defined on `Path` or `Paths`. All four of these examples point to the same 
reference on disk:
```
Path zooPath1 = Path.of("/home/tiger/data/stripes.txt");
Path zooPath2 = Path.of("/home", "tiger", "data", "stripes.txt");

Path zooPath3 = Paths.get("/home/tiger/data/stripes.txt");
path zooPath3 = Paths.get("/home", "tiger", "data", "stripes.txt");

System.out.println(Files.exists(zooPath1));
```

Both methods allow passing a varargs parameter to pass additional path elements. The 
values are combined and automatically separated by the operating system-dependent file 
separator. We also show the `Files` helper class, which can check if the file exists 
on the file system.

As we can see, there are two way of doing the same thing here. The `Path.of()` method 
was introduced in Java 11 as a static method on the interface. The `Paths` factory class 
als provides a `get()` methods to do the same thing. Both `Path.of()` and `Paths.get()` 
are used interchangeably in this chapter.

One thing to note is that both, the I/O and NIO.2 classes, can interact with a URI. 
A uniform resource identifier (URI) is a string of characters that identifies a resource. 
It begins with a schema that indicates the resource type, followed by a path value such 
as `file://` for local file systems and `http://`, `https://`, and `ftp://` for 
remote file system.

#### Switching between _File_ and _Path_

Since `File` and `Path` both reference locations on disk, it is helpful to be able to 
convert between them. Luckily, Java makes this easy by providing methods to do just that:
```
File file = new File("rabbit");
Path nowPath = file.toPath();
File backToFile = nowPath.toFile();
```

Many older libraries use `File`, making it convenient to be able to get a `File` from 
a `Path` and vice versa. When working with newer applications, we should rely on NIO.2's 
`Path` interface, as it contains more features. For example, only NIO.2 provides 
`FileSystem` support, as we are about to discuss.


#### Obtaining a _Path_ from the _FileSystems_ Class

NIO.2 makes extensive use of creating objects with factory classes. The `FileSystems` 
class create instances of the abstract `FileSystem` class. The later includes methods 
for working with the file system directly. Both `Paths.get()` and `Path.of()` are 
shortcuts for this `FileSystem` method. Let's rewrite our earlier examples one more 
time to see how to obtain a `Path` instance the long way:

```
Path zooPath = FileSystems.getDefault().getPath("/home/tiger/data/stripes.txt");
Path zooPath2 = FileSystems.getDefault().getPath("/home", "tiger", "data", "stripes.txt");
```

### Reviewing I/O and NIO.2 Relationship

The model for I/O is smaller, and we only need to understand the `File` class. In 
contrast, NIO.2 has more features and makes extensive use of the factory pattern. We 
need to become comfortable with this approach. Many of our interaction with NIO.2 will 
require two types: an abstract class or interface and a factory or helper class. Figure 
14.3 shows the relationships among the classes and interfaces we have used in this 
chapter so far

![I/O and NIO.2 class and interfaces relationship](io_nio_relationships.png)

**Figure 14.3: I/O and NIO.2 class and interface relationship**

Keep an ey on whether the class name is singular or plural. Classes with plural names 
include methods to create o operate on class/interface instance with singular names.
We need to remember that, as a convenience (and sometime confusion), a `Path` can also 
be created from the the `Path` interface using the static factory `of()` method.

The `java.io.File` is the I/O class, while `Files` is an NIO.2 helper class. `Files` 
operates on `Path` instances, not `java.io.File` instances. This is confusing, but 
the are from completely different APIs.

Table 14.2 reviews the APIs we have covered for creating object of types `java.io.File` 
or `java.nio.file.Path`. When reading the table, is good to remember that static methods 
operate on the class/interface, while instance methods require an instance of an object.

**Table 14.2: Options for creating File and Path**

[options creating file path](file_and_path_creating_options.png)


Just a memory refresh.
```
File zooFile1 = new File("/home/tiger/data/stripes.txt");
File zooFile2 = new File("/home/tiger", "data/stripes");

Path zooPath1 = Path.of("/home/tiger/data/stripes.txt");
Path zooPath2 = Path.of("/home", "tiger", "data", "stripes.txt");

Path zooPath3 = Paths.get("/home/tiger/data/stripes.txt");
path zooPath3 = Paths.get("/home", "tiger", "data", "stripes.txt");
```


## Operating on File and Path

Now that we know how to create `File` and `Path` objects, we can start using them 
to do useful things. In this section, we explore the functionality available to us 
that involves directories.


### Using Shared Functionality

Many operation can be done using both the I/O and NIO.2 libraries. We present 
many common APIs in table 14.3 and Table 14.4. Although these tables may seem like 
a lot of methods to learn, many of them are self-explanatory. We can ignore the 
varargs parameters for now. Those will be explained later in the chapter.

**Table 14.3: Common File and Path operations**

![file and path operations](file_and_path_common_operations.png)


**Table 14.4: Common File and Path operations**

![file and path operation](file_and_path_operations.png)


Now let's try to use some ot these APIs. The following is a sample program using only 
legacy I/O APIs. Given a file path, it outputs information about the file or directory, 
such as whether it exists, what files are contained within it, and so forth:
```
10: public static void io() {
11:   var file = new File("/home/users/user/data/zoo.txt");
12:   if (file.exists()) {
13:     System.out.println("Absolute path: " + file.getAbsolutePath());
14:     System.out.println("Is Directory: " + file.isDirectory());
15:     System.out.println("Parent Path: " + file.getParent());
16:     if (file.isFile()) {
17:       System.out.println("Size: " + file.length());
18:       System.out.println("Last Modified": file.lastModified());
19:     } else {
20:       for (File subfile : file.listFiles()) {
21:         System.out.println(" " + subfile.getName());
22:       }
23:     }
24:   }
15: }
```

If the path provided points to a valid file, the program outputs something similar 
to the following due to the `if` statement on line 16:
```
Absolute Path: /home/users/user/data/zoo.txt
Is Directory: false
Parent Path: /home/users/user/data
Size: 1238
Last Modified: 16506100000000
```

Finally, if the path is provided to a valid directory, such as `/home/users/user/data`, 
the program outputs something similar to the following, the `else` block:
```
Absolute Path: /home/users/user/data
Is Directory: true
Parent Path: /home/user/user
  employees.txt
  zoo.txt
  zoo-backup.txt
```

In these examples, we see that the output of an I/O-based program is completely 
dependent on the directories and files available at runtime in the underlying file 
system.

Sometimes we might see paths that look like files but are directories or vice versa. 
For example _/home/users/user/data/zoo.txt_ could be a file or a directory, even though 
it has a file extension. Don't assume it is either unless the clearly explicitly said.

Now, let's write that same program using only NIO.2 and see how it differs:
```
10: public static void nio() throws IOException {
11:   var path = Path.of("/home/users/user/data/zoo.txt");
12:   if (Files.exists(path)) {
13:     System.out.println("Absolute Path: " + path.toAbsolutePath());
14:     System.out.println("Is Directory: " + Files.isDirectory(path));
15:     System.out.println("Parent Path: " + path.getParent());
16:     if (Files.isRegularFile(path)) {
17:       System.out.println("Size: " + Files.size(path));
18:       System.out.println("Last Modified: "
19:           + Files.getLastModifiedTime(path));
20:     } else {
21:       try (Stream<Path> stream = Files.list(path)) {
22:         stream.forEach(
23:           p -> System.out.println(" " + p.getName())
24:         );
25:       }
26:     }
27:   }
28: }
```

Most of this example is equivalent and replaces the I/O method call in the previous 
tables with tbe NIO.2 versions. However, there are key differences. First, line 10 
declares a checked exception. More APIs in NIO.2 throw IOException than the I/O APIs 
did. In this case, `Files.size()`, `Files.getLastModifiedTime()`, and `Files.list()` 
thrown an `IOException`.

Second, lines 21 to 25 use a `Stream` and a lambda instead of a loop. Since streams 
use lazy evaluation, this means the method will load each path element as needed, rather 
than the entire directory at once.

---
**Closing the Stream**

Did you notice that in the last code sample, we put our `Stream object inside a 
_try-with-resources_? The NIO.2 stream-based methods open a connection to the file 
system **that must be properly closed**; otherwise, a resource leak could ensue. A 
resource leak within the file system means the path may be locked from modification 
long after the process that used it is completed.

If we assumed that a stream's terminated operation would automatically close the 
underlying file resources, we would be wrong. There was a lot of debate about this 
behavior when it was first presented; in short, requiring developers to close the 
stream won out.

On the plus side, not all streams need to be closed: only those that open resources, 
like the ones found in NIO.2. For instance, we didn't need to close any of the streams 
we worked with in Chapter 10, "Streams".

Finally, the exam doesn't always properly close NIO.2 resources. To match the exam, 
this book sometimes skip close NIO.2 resources in review and practice questions. In 
a real world, we need always use _try-with-resources_ statements with these NIO.2 
methods in our own code.

---

For the remainder of this section, we only discuss the NIO.2 methods, because they 
are more important. There is also more to known about them, and the are more likely 
to come up on the exam.


### Handling Methods that Declare _IOException_

Many of the methods presented in this chapter declare `IOException`. Common causes 
of a method throwing this exception include the following:
- Loss of communication to the underlying file system.
- File or directory exists but cannot be accessed or modified.
- File exists but cannot be overwritten.
- File or directory is required but does not exist

Methods that access or changes files and directories, such as those in the `Files` 
class, often declare `IOException`. There are exceptions to this rule, as we will see. 
For example, the method `Files.exists()` does not declare `IOException`.  If it did 
throw an exception whe the file did not exist, it would never be able to return `false`. 
As a rule of thumb, if a NIO.2 method declares an IOException, it _usually_ requires 
the paths it operates on to exist.


### Providing NIO.2 Optional Parameters

Many of the NIO.2 methods in this chapter include a varargs that takes an optional 
list of values. Table 14.5 presents the arguments we should be familiar.

**Table 14.5: Common NIO.2 method arguments**

![common nio methods arguments](nio_common_methods_arguments.png)

With the exceptions of `Files.copy()` and `Files.move()`, we won't discuss these 
varargs parameters each time we present a method. Their behavior should be straight-
forward, though. For example, can you figure out the following call to `File.exists()` 
with the `LinkOption` does in the following code snippet?
```
Path path = Paths.get("schedule.xml");
boolean exists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
```

This `Files.exists()`  simply checks whether a file exists. But if the parameter 
is a symbolic link, the methods checks whether the target of the symbolic link exists, 
instead. Providing `LinkOption.NOFOLLOW_LINKS` means the default behavior will be over-
ridden, adn the method will check whether the symbolic link itself exists.

Note that some of the enums in table 14.5 inherit an interface. That means some 
methods accept a variety of enum types. For example, the `Files.move()` method takes 
a `CopyOption` vararg so it can take enums of different types, and more options can 
be added over time.
```
void copy(Path source, Path target) throws IOException {
  Files.move(
    source,
    target,
    LinkOption.NOFOLLOW_LINKS,
    StandardCopyOption.ATOMIC_MOVE
  );
}
```

### Interacting with NIO.2 Paths

Just like `String` values, `Path` instances are immutable. In the following example, 
the `Path` operation on the second file is lost since `p`  is immutable.
```
Path p = Path.of("whale");
p.resolve("krill");
System.out.println(p);    // whale
```

Many of the methods available in the `Path` interface transform the path value 
in some way and return a new `Path` object, allowing the methods to be chained. We 
demonstrate chaining in the following example, the details of which we discuss in 
this section of the chapter:
```
Path.of("/zoo/../home").getParent().normalize().toAbsolutePath();
```

#### Viewing the Path

The `Path` interface contains three methods to retrieve basic information about 
the path representation. The `toString()` method returns a `String` representation 
of the entire path. In fact, it is the only method in the `Path` interface to return 
a `String`. Many of the other methods in the `Path` interface return `Path` interfaces.

The `getNameCount()` and `getName()` methods are often used together to retrieve the 
number of elements in the path and a reference to each element, respectively. These 
two methods do not include the root directory as part of the path.
```
Path path = Paths.get("/land/hippo/harry.happy");
System.out.println("The Path Name is: " + path);
for (int i = 0; i < path.getNameCount(); i++) {
  System.out.println("  Element " + i + " is: " + path.getName(i));
}
```

The code prints the following:
```
The Path name is: /land/hippo/harry.happy
  Element 0 is: land
  Element 1 is: hippo
  Element 2 is: harry.happy
```

Even though this is an absolute path, the root element is not included in the 
list of names. As we said, these methods do not consider the root part of the path.
```
var p = Path.of("/");
System.out.println(p.getNameCount());    // 0
System.out.println(p.getName(0));        // IllegalArgumentException
```

Notice that if we try to call `getName()` with an invalid index, it will thrown 
an exception at runtime.


#### Creating Part of the Path

The `Path` interface includes the `subpath()` method to select portions of a path. 
It takes two parameters: an inclusive `beginIndex` and an exclusive `endIndex`. This 
should sound familiar as it is how String's `substring()` method works, as we saw 
in Chapter $, "Core APIs".

The following code snippet shows how `subpath()` works. We also print the elements 
of the `Path` using `getName()` so that we can see how the indices are used.
```
var p = Paths.get("/mammal/omnivore/raccoon.image");
System.out.println("Path is: " + p);
for (int i = 0; i < p.getNameCount(); i++) {
  System.out.println("  Element " + i + " is: " + p.getName(i));
}
System.out.println()
System.out.println("subpath(0, 3): " + p.subpath(0, 3));
System.out.println("subpath(1, 2): " + p.subpath(1, 2));
System.out.println("subpath(1, 3): " + p.subpath(1, 3));
```

The output of this code snippet is the following:
```
Path is: /mammal/omnivore/raccoon.image
  Element 0 is: mammal
  Element 1 is: omnivore
  Element 2 is: raccoon.image

subpath(0, 3): mammal/omnivore/raccoon.image
subpath(1, 2): omnivore
subpath(1, 3): omnivore/raccoon.image
```

Like `getNameCount()` and `getName()`, `subpath()` is zero-based index and does 
not include the root. Als like `getName()`, `subpath()` throws an exception if 
invalid indices are provided.
```
var q = p.subpath(0, 4);    // IllegalArgumentException
var x = p.subpath(1, 1);    // IllegalArgumentException
```

The first example throws an exception at runtime, since the maximum index value 
allowed is 3. The second example throws an exception since the `start` and the `end` 
indexes are the same, leading to an empty path value.


#### Accessing Path Elements

The `Path` interface contains numerous methods for retrieving particular elements 
of a `Path`, returned as `Path` objects themselves. the `getFileName()` method returns 
the `Path` element of the current file or directory, while `getParent()` returns  the 
full path of the containing directory. The `getParent()` method returns `null` if 
operated on the root path or at the top of a relative path. The `getRoot()` method 
returns the root element of the file within the file system, or null if the path is 
a relative path.

Consider the following method, which prints various `Path` elements:
```
public void printPathInformation(Path path) {
  System.out.println("Filename is: " + path.getFileName());
  System.out.println("  Root is: " + path.getRoot());
  Path currentParent = path;
  while ( (currentParent == currentParent.getParent()) != null )
    System.out.println("  Current parent is: " + currentParent);
  System.out.println()
}
```

The while loop in the `printPathInformation()` method continues until `getParent()` 
returns `null`. We apply this method to the following three paths:
```
printPathInformation(Path.of("zoo"));
printPathInformation(Path.of("/zoo/armadillo/shells.txt"))
printPathInformation(Pat.of("./armadillo/../shells.txt"));
```

This sample application produces the following output:
```
Filename is: zoo
  Root is: null

Filename is: shells.txt
  Root is: /
  Current parent is: /zoo/armadillo
  Current parent is: /zoo
  Current parent is: /

Filename is: shells.txt
  Root is: null
  Current parent is: ./armadillo/..
  Current parent is: ./armadillo
  Current parent is: .
```

Reviewing the sample output, we can see the difference in the behavior of `getRoo()` 
on absolute and relative paths. As we can see in the first and last examples, the 
`getParent()` method does not traverse relative paths outside the current working 
directory.

We also see that these methods do no resolve the path symbols and treat them as 
a distinct part of the path. While most of the methods in this part of the chapter 
treat path symbols as part of the path, we present on shortly that cleans up path 
symbols.


#### Resolving Paths (concatenating paths)

Suppose we want to concatenate paths in a manner similar to how we concatenate 
strings. The `resolve()` method provides overloaded version that let us pass either 
a `Path` or `String` parameter. The object on which the `resolve()` method is invoked 
becomes the basis of the new `Path` object, with the input argument being appended 
onto the `Path`. Let's see what happens if we apply `resolve()` to an absolute 
path and a relative path.
```
Path path1 = Path.of("/cats/../panther");
Path path2 = Path.of("food");
System.out.println(path1.resolve(path2));
```

The code snippet generate the following output:
```
/cat/../panther/food
```

Like the other methods we've seen, `resolve()` does not clean up path symbols. 
In this example, the input argument to the `resolve()` method was a relative path, 
but what if it had been an absolute path?
```
Path path3 = Path.of("/turkey/food");
System.out.println(path.resolve("/tiger/cage"));
```

Since the input parameter is an absolute path, the output would be the following: 
```
/tiger/cage
```

For the exam, we should be cognizant of mixing absolute and relative paths with 
the `resolve()` method. If an absolute path is provided a input to the method, that 
is the value returned. Simply put, we cannot combine two absolute paths using 
`resolve()`.


#### Relativizing a Path

The `Path` interface includes a `relativize()` method for constructing the relative 
path from on `Path` to another, often using path symbols. What do you thing the following 
examples will print?
```
var path1 = Path.of("fish.txt");
var path2 = Path.of("friendly/birds.txt")
System.out.println(path1.relativize(path2));
System.out.println(path2.relativize(path1));
```

The examples print the following:
```
../friendly/birds.txt
../../fish.txt
```

The idea is this: if we are pointed at a path in the file system, what steps would 
be necessary to take to reach the other path? For example, to get to `fish.txt` from 
`friendly/birds.txt`, we need to go up two levels (the file itself counts as one level) 
and ten select `fish.txt`.

If both path values are relative, the `relativize()` method computes the paths as 
if they are in the same current working directory. Alternatively, if both path values 
are absolute, the method computes the relative path from one absolute location to 
another, regardless of the current working directory. The following example demonstrates 
this property when run on a Windows computer:
```
Path path3 = Paths.get("E:\\habitat");
Path oath4 = Paths.get("E:\\sanctuary\\raven\\poe.txt");
System.out.println(path3.relativize(path4));
System.out.println(path4.relativize(path3));
```

This code snippet produces the following output:
```
..\sanctuary\raven\poe.txt
..\..\..\habitat
```

The `relativize()` method requires both paths to be absolute or relative and 
throws an exception if the types are mixed.
```
Path path5 = Paths.get("/primate/chimpanzee");
Path path6 = Paths.get("bananas.txt");
path5.relativize(path6);                // IllegalArgumentException
```

On Windows-based systems, it also requires that if absolute path are used, both 
paths must have the same root directory or drive letter.


#### Normalizing a Path

So far, we've presented a number of examples that included path symbols that were 
unnecessary. Luckily, Java provides the `normalize()` method to eliminate unnecessary 
redundancies in a path.

Remembering that the path symbol `..` refers to the parent directory, while the path 
symbol `.` refers to the current directory, We can apply `normalize()` to some of our 
previous paths.
```
var p1 = Path.of("./armadillo/../shells.txt");
System.out.println(p1.normalize());             // shells.txt

var p2 = Path.of("/cats/../panther/food");
System.out.println(p2.normalize());             // /panther/food

var p3 = Path.of("../../fish.txt");
System.out.println(p3.normalize());             // ../../fish.txt
```

The first two examples apply the path symbols to remove the redundancies, but what 
about the last one? That is as simplified as it can be. The `normalize()` method does 
not remove all of the path symbols, only the ones that can be reduced.

The `normalize()` method also allows us to compare equivalent paths. Consider the 
following example:
```
var p1 = Paths.get("/pony/../weather.txt");
var p2 = Paths.get("/weather.txt");
System.out.println(p1.equals(p2))                            // false
System.out.println(p1.normalize().equals(p2.normalize()));   // true
```

The `equals()` method returns `true` if two paths represent the same value. In the 
first comparison, the path values are different. In the second comparison, the path 
values have both reduced to the same normalized value, `/weather.txt`. This is the 
primary function of the `normalize()` method: to allow us to better compare different 
paths.


#### Retrieving the Real File System Path

While working with theoretical path is useful, sometimes we want to verify that 
the path exists within the file system using `toRealPath()`. This method is similar 
to `normalize()` in that it eliminates any redundant path symbols. It also similar 
to `toAbsolutePath()`, in that it will join the path with the current working 
directory if the path is relative.

Unlike those two methods, though, `toRealPath()` will throw an exception if the 
path does not exist. In addition, it will follow symbolic links, with an optional 
`LinkOption` varargs parameter to ignore them.

Let's say that we have a file system in which we have a symbolic link from `/zebra` 
to `/horse`. What do you think the following will print, given a current working 
directory of `/horse/scheduled`?
```
System.out.println(Paths.get("/zebra/food.txt").toRealPath());
System.out.println(Paths.get(".././food.txt"));
```

The output of both lines is the following:
```
/horse/food.txt
```

In this example, the absolute and relative paths both resolve to the same absolute 
file, as the symbolic kink points to a real file within the file system. We can also 
use the `toRealPath()` method to gain access tot the current working directory as 
a `Path` object.
```
System.out.println(Paths.get(".").toRealPath());
```


### Reviewing NIO.2 Path APIs

Table 14.6 list the methods covered in this section.

**Table 14.6: Path APIs**

![NIO.2 path apis](nio_path_apis.png)


### Creating, Moving, and Deleting Files and Directories

Since creating, moving, and deleting have some nuances, we flesh them out in this 
section.


#### Making Directories

To create a directory, we use `Files` methods:
```
public static Path createDirectory(Path dir, 
  FileAttribute<?>... attrs) throws IOException

public static Path createDirectories(Path dir,
  FileAttribute<?>... attrs) throws IOException
```

The `createDirectory()` methods will create a directory and throw an exception 
if it already exists or fi the paths leading up to the directory do not exist. The 
`createDirectories()` method creates the target directory along with any nonexistent 
parent directories leading up to the path. If all of the directories already exist, 
`createDirectories()` will simply complete without doing anything. This is useful in 
situations where we want to unsure a directory exists and create if if it does not.

Both of these methods also accept an optional list of `FileAttribute<?>` values 
to apply to the newly create directory or directories. We discuss file attributes 
toward the end of the chapter.

The following shows how to create directories:
```
Files.createDirectory(Path.of("/bison/field"));
Files.createDirectories(Path.of("/bison/filed/pasture/green"));
```

The first example create a new directory, `field`, in the directory `/bison`, 
assuming `/bison` exists; otherwise, an exception is thrown. Contrast this, the 
second example, which creates the directory `green` along with any of the following 
parent directories fi the do not already exist, including `bison`, `field`, and 
`pasture`.


#### Copying Files

The `Files` class provides a method for copying files and directories within the 
file system.
```
public static Path copy(Path source, Path target,
  CopyOption... options) throws IOException
```

The method copies a file or directory from on location to another using `Path` 
objects. The following shows an example of copying a file and a directory:
```
Files.copy(Paths.get("/panda/bamboo.txt"), 
  Paths.get("/panda-save/bamboo.txt"));

Files.copy(Paths.get("/turtle"), Paths.get("/turtleCopy"));
```

When directories are copied, the copy is shallow. A _shallow copy_ means that 
the files and subdirectories within the directory are not copied. A _deep copy_ means 
that the entire tree is copied, including all of its content and subdirectories. A 
deep copy typically requires _recursion_, where a method calls itself.
```
public void copyPath(Path source, Path target) {
  try {
    Files.copy(source, target)
    if (Files.isDirectory(source)) {
      try (Stream<Path> s = Files.list(source)) {
        s.forEach( p -> copyPath(p, target.resolve(p.getFileName() )) )
      }
    }
  } catch (IOException) {
    // handle exception
  }
}
```

The method first copies the path, whether a file or a directory. If it is a directory, 
only a shallow copy ins performed. Next, it check whether the path is a directory and, 
if it is, performs a recursive copy of each of its elements. What if the method comes 
across a symbolic link? Don't worry: the JVM will not follow symbolic links when 
using the `list()` method.


#### Copying and Replacing Files

By default, if the target already exists, the `copy()` method will thrown an exception. 
We can change this behavior bby providing `StandardCopyOption` enum value REPLACE_
EXISTING t the method. The following method call will overwrite the `movie.txt` file 
if it already exists.
```
Files.copy(Paths.get("book.txt"), Paths.get("movie.txt"), 
  StandardCopyOption.REPLACE_EXISTING);
```

Without the REPLACE_EXISTING option, this method will throw an exception if the 
file already exist.


#### Copying Files with I/O Streams

The `Files` class includes two `copy()` methods that operate with I/O streams.
```
public static long copy(InputStream in, Path target, 
  CopyOption... options) throws IOException

public static long copy(Path source, OutputStream out)
  throws IOException
```

This first method reads the contents of an I/O stream and writes the output to 
a file. The second method reads the contents of a file and writes the output to 
an I/O stream. These methods are quite convenient if we need to quickly rear/write 
data from/to disk.

The following are examples of each `copy()` method:
```
try (var is = new FileInputStream("source-data.txt")) {
  // write I/O stream data to a file
  Files.copy(is, Paths.get("/mammals/wolf.txt"));
}

Files.copy(Paths.get("/fish/clown.xsl"), System.out);
```

While we used `FileInputStream` in the first example, the I/O stream could have 
been any valid I/O stream including website connections, in-memory stream resources, 
and so forth. The second example prints the contends of a file directory to the 
`System.out` stream.


#### Copy Files into a Directory

It is important to understand how the `copy()` method operates on both files and 
directories. For example, let's say we have a file, `food.txt`, and a directory, 
`/enclosure`. Both the file and directory exist. What do you think is the result 
of executing the following process?
```
var file = Paths.get("food.txt");
var directory = Paths.get("/enclosure");
Files.copy(file, directory);
```

If you said it would create a new file at `/enclosure/food.txt`, you're way off. 
It throws an exception. The command tries to create a new file named `/enclosure`. 
Since the path `/enclosure` already exists, an exception is thrown at runtime.

On the other hand, if the directory did not exist, the process would create a new 
file with the contents of `food.txt`, but the file would be called `/enclosure`. 
To remember, we said files may no need to have extensions, and in this example, it 
matters.

This behavior applies to both the `copy()` and `move()` methods, the latter of 
which we cover next. The correct way to copy the file into the directory is to 
do the following:
```
var file = Paths.get("food.txt");
var directory = Paths.get("/enclosure/food.txt");
Files.copy(file, directory);
```


#### Moving or Renaming Paths with _move()_

The `Files` class provides a useful method for moving or renaming files and 
directories.
```
public static Path move(Path source, Path target, 
  CopyOption... options) throws IOException
```

The following sample code uses the `move()` method:
```
Files.move(Path.of("C:\\zoo"), Path.of("C:\\zoo-new"));

Files.move(path.of("C:\\user\\addresses.txt"), Path.of("C:\\zoo-new\addresses2.txt"));
```

The first example renames the `zoo` directory to a `zoo-new` directory, keeping 
all of the original contents from the source directory. The second example moves the 
`addresses.txt` file from the directory `user` to the directory `zoo-new` and renames 
it `addresses2.txt`.


#### Similarities between _move()_ and _copy()_

Like `copy()`, `move()` requires REPLACE_EXISTING to overwrite the target if it exists; 
otherwise, it will thrown an exception. Also lik `copy()`, `move()` will not put a file 
in a directory if the source is a file and the target is a directory. Instead, it will 
create a new file with the name of the directory.


**Performing an Atomic Move**

Another enum value that we need to know when working with the `move()` method 
is the `StandardCopyOption` value ATOMIC_MOVE.
```
Files.move(Path.of("mouse.txt"), Path.of("gerbil.txt"), 
  StandardCopyOption.ATOMIC_MOVE);
```

The principle of an atomic move is similar to the one that we saw in Chapter 13, 
"Concurrency".  An atomic move is on in which a file is moved within the file system 
as a single indivisible operation. Put another way, any process monitoring the file 
system never sees an incomplete or particularly written file. If the file system 
does not support this feature, an `AtomicMoveNotSupportedException` will be thrown.

Note that while ATOMIC_MOVE is available as a member of the `StandardCopyOption` 
type, it will likely throw an exception if passed to a `copy()` method.


#### Deleting a File with _delete()_ and _deleteIfExists()_

The `Files` class includes two methods that delete a file or empty directory 
within the file system.
```
public static void delete(Path path) throws IOException

public static boolean deleteIfExists(Path path) throws IOException
```

To delete a directory, is must be empty. Both of these methods throw an exception 
if operated on a nonempty directory. In addition, if the path is a symbolic link, 
the symbolic link will be deleted, not the path that the symbolic link points to.

The methods differ on how they handle a path that does not exist. The `delete()` 
method throws an exception if the path does not exist, while the `deleteIfExists()` 
method return `true` if the delete was successful or `false` otherwise. Similar 
to `createDirectories()`, `deleteIfExists()` is useful in situations where we 
want to ensure that a path does not exists and delete it if it does.

Here we provide sample code that performs `delete()` operations:
```
Files.delete(Paths.get(/vulture/feathers.txt));
Files.deleteIfExists(Paths.get("/pigeon"));
```

The first example deletes the `feather.txt` file in the `vulture` directory, and 
it throws a `NoSuchFileException` if the file or directory does not exist. The second 
example deletes the `pigeon` directory, assuming it is empty. If the `pigeon` directory 
does not exist, the second line will not thrown an exception.


### Comparing Files with _isSameFile()_ and _mismatch()_

Since a path may include path symbols and symbolic links within a file system, 
the `equals()` method can't be relied on to know if two `Path` instances refer to 
the same file. Luckily, there is the `isSameFile()` method. This method takes two 
`Path` objects as input, resolves all path symbols, and follows symbolic links. 
Despite the name, the method can also be used to continue whether two `Path` objects 
refer to the same directory.

While most uses of `isSameFile()` will trigger an exception if the paths do not 
exist, there is a special case in which it does not. If the two path objects are 
equal in terms of `equals()`, the method will just return true without checking 
whether the file exists.

Assume that the file system exists, as shown in Figure 14.4, with a symbolic link 
from `/animals/snake` to `/animals/cobra`.

![comparing file uniqueness](file_comparing_uniqueness.png)

**Figure 14: Comparing file uniqueness**


Given the structure defined in Figure 14.4, what does the following output?
```
System.out.println(Files.isSameFile(
  Path.of("/animals/cobra"),
  Path.of("/animals/snake")
));

System.out.println(Files.isSameFile(
  Path.of("/animals/monkey/ears.png"),
  Path.of("/animals/wolf/ears.png")
));
```

Since `snake` is a symbolic link to `cobra`, the first example outputs `true`. In 
the second example, the paths refer to different files, so `false` is printed.

Sometimes we want to compare the contents of the file rather than whether it is 
physically the same file. For example,  we could have two files with the same text 
"hello world". The `mismatch()` method was introduced in Java 12 to help us out here. 
It takes two `Path` objects as input. The method return -1 if the files are same; 
otherwise, it return the index of the first position in the file that differs.
```
System.out.println(Files.mismatch(
  Path.of("/animals/monkey.txt"),
  Path.of("/animals/wolf.txt")
));
```

Suppose `monkey.txt` contains the name "Harold" and `wolf.txt` contains the name 
"Howler". The previous code print 1 in that case because the second position is 
different, and we use zero-based indexing in Java. Given those values, what do you 
thin this code prints?
```
System.out.println(Files.mismatch(
  Path.of("/animals/wolf.txt"),
  Path.of("/animals/monkey.txt")
));
```

The answer is the same as the previous example. The code prints 1 again. The 
`mismatch()` method is symmetric and return the same result regardless of the 
order of the parameters.

[back to top](#chapter-14-inputoutput)


## Introducing I/O Streams