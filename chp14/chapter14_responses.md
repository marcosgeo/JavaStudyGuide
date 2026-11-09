
1, a
2, a, f
3, a, 

# Chapter 14: I/O

1) C,
Since the question asks about putting data into a structured object, the best class 
would be one that deserializes the data. Therefore, `ObjectInputStream` is the best 
choice, which is option C. `ObjectWriter`, `BufferedStream`, and `ObjectReader` are not 
I/O stream classes. `ObjectOutputStream` is an I/O class but is used to serialize data, 
not deserialize it. `FileReader` can be used to read text file data an construct an 
object, but the question asks what would be the best class to use for binary data.


2) A, F,
Paths that begin with the root directory are absolute paths, so option A is correct, 
and option C is incorrect. Option B is incorrect because the path could be a file or 
directory within the file system. There is no rule that files have to end with a file 
extension. Option D is incorrect, as it is possible to create a `File` reference to 
files and directories that do not exist. Option E is also incorrect. The `delete()` 
method return `false` if the file or directory cannot be deleted. Character stream 
classes often include built-in convenience methods for working with `String` data, so 
option F is correct. There is no such optimization for multi-threading, making option 
G incorrect.


3) 
```
public static void main(String[] args) throws IOException {
  String line;
  var c = System.console();
  Writer w = c.writer();
  try (w) {
    if ( (line = c.readLine("Enter your name":)) != null )
      w.append(line);
    w.flush();
  }
}
```
B, D, 
If the console is unavailable, `System.console()` will return `null`, making option 
D correct and options E and F incorrect. The writer methods throw a checked `IOException`, 
making option C incorrect. The code works, prompting for input and printing it. So,
option A is incorrect and option B is correct.


4) F,
The code does not compile, as `Files.deleteIfExists()` declares the checked `IOException` 
that must be handled or declared. We must remember that most `Files` methods declares 
`IOException`, especially the ones that modify a file directory. For this reason, option 
F is correct. If the method were corrected to declare the appropriate exceptions, option 
C would be correct. Option B would also be correct if the method were provided a symbolic 
link that pointed to an empty directory. Options A and E would not print anything, as 
`Files.isDirectory()` returns `false` for both. Finally, option D would throw a 
`DirectoryNotEmptyException` at runtime.


5) C, 
The `filter()` operation applied to a `Stream<Path>` takes only one argument, not two, 
so the code does not compile, and option C is correct. If the code were rewritten to use 
the `Files.find()` method with the `BiPredicate` as input (along with a `maxDepth` value), 
the output would be option B, Has Sub, since the directory is given to be empty. For fun, 
we reversed the expected output of the ternary operation.


6) C, 
The code compiles and runs without issue, so option F and G are incorrect. The key 
here is that while `Eagle` is serializable, its parent class, `Bird`, is not. Therefore, 
none of the member of `Bird` will be serialized. Even if we didn't know that, we should 
know what happens on deserialization. During deserialization, Java calls the constructor 
of the first non-serializable parent. In this case, the `Bird` constructor is called, 
with `name` being set to "Matt", making option C correct. Note that none of the 
constructors or instance initializers in `Eagle` are executed as part of deserialization.


7) B, C,
The code snippet will attempt to crate a directory if the target of the symbolic 
link exists and is directory. If the directory already exists, though, it will throw an 
exception. For this reason, option A is incorrect, and option B is correct. It will be 
created in `/mammal/kangaroo/joey` and also reachable at `/kang/joey` because of the 
symbolic link, making option C correct.


8) B,
The `readAllLines()` method returns a `List`, not a `Stream`. Therefore, the call 
to `flatMap()` is invalid, and option B is correct. If the `Files.lines()` method were 
used instead, it would print the contents of the file capitalized, one word at a time 
with the commas removed.


9) C, E, G,
Fist, the method does compile, so options A and B are incorrect. Methods to read/write 
`byte[]` values exist in the abstract parent of all I/O stream classes. This implementation 
is not correct, though, as the return value of `read(buffer)` is not used properly. It 
will only correctly copy files whose character count is a multiple of 10, making option  
C correct and option D incorrect. Option E is also correct as the data may not have made 
it to disk yet. Option F would be correct if the `flush()` method were called after 
every write. Finally, option G is correct as the `reader` stream is never closed.


10) B, D, G,
Options A and E are incorrect because `Path` and `FileSystem`, respectively, are 
abstract types that should be instantiated using a factory method. Option C is incorrect 
because the static method in the `Path` interface is `of()`, not `get()`. Option F is 
incorrect because the static method in the `Paths()` class is `get()`, not _getPath()_. 
Options B and D are correct ways to obtain a `Path` instance. Option G is also correct, 
as there is an overloaded static method in `Path` that takes a URI instead of a `String`.


11) A, E,
The code will compile if the correct classes are used, so option G is incorrect. We 
need to remember that, a try-with-resources statement can use resources declared before 
the start of the statement. The reference type of `wrapper` is `InputStream`. We can 
eliminate `BufferedWriter`, `ObjectOutputStream`, and `BufferedReader` since their names 
do not end in `InputStream`. Next, we see the class must take another stream as input, so 
we need to choose the remaining streams that are high-level streams. `BufferedInputStream` 
is a high-level stream, so option A is correct. Even though the instance is already a 
`BufferedInputStream`, there's no rule that it can't be wrapped multiple times by a high-
level stream. Option D is incorrect, as `FileInputStream` operates on a file, not another 
stream. Finally, option E is correct, an `ObjectInputStream` is a high-level stream that 
operates on other streams.


12) C, E,
The method to create a directory in the `Files` class is `createDirectory()`, not 
_mkdir()_. For this reason, line 6 does not compile, and option C is correct. In 
addition, the `setTimes()` method is available only on `BasicFileAttributeView`, not 
the read-only `BasicFileAttributes`, so line 8 also not compile, making option E 
correct.


13) A, G,
For a class to be serialized, it must implement the `Serializable` interface and 
contain instance members that are serializable or marked `transient`. For these reasons, 
options A and G are correct and option F is incorrect. Option B is incorrect because 
even records are required to implement `Serializable` to be serialized. Option C is 
incorrect because it describes deserialization. The `Serializable` interface is a marker 
interface that does not contain any abstract methods, making option D incorrect. While 
it is a good practice for a serializable class to include a `static serialVersionUID` 
variable, it is not required. Therefore, option E is incorrect as well.


14) B, D, E,
`Path` is immutable, so line 23 is ignored. It it were assigned to `p1`, option A 
would be correct. Since it is not assigned, the original value is still present, which 
is option B. Moving on to the second section, the `subpath()` method on line 27 is 
applied to the absolute path, which returns the relative path `animals/bear`. Next, 
the `getName()`method is applied to the relative path, and since this is indexed from 
0, it return the relative path `bear`. Therefore, option D is correct. Finally, we 
need to remember that calling `resolve()` with an absolute path as a parameter returns 
the absolute path, so option E is correct.


15) B, E, F,
Option A does not compile, as there is no `File` constructor that takes three 
parameters. Option B is correct and is the proper way to create a `File` instance 
with a single `String` parameter. Option C is incorrect, as there is no constructor 
that takes a `String` followed by a `File`. There is a constructor that takes a `File` 
followed by a `String`, making option E correct. Option D is incorrect because the first 
parameter is missing as slash (/) to indicate it is an absolute path. Since it's relative, 
it is correct only when the user's current directory is the root directory. Finally, option 
F is correct as it creates a `File` from a `Path`.


16) A, D,
The method compiles, so option E is incorrect. The method creates a `new-zoo.txt` file 
and copies the first line from `zoo-data.txt` into it, making option A correct. The try-
with-resources statement closes all of the declared resources, including the `FileWriter` 
`o`. For this reason, the `Writer` is closed when the last `o.write()` is called, resulting 
in an `IOException`  at  runtime  and  making  option D  correct.  Option F is incorrect 
because this  implementation uses the  characters stream classes,  which inherit from 
`Reader` or `Writer`.


17) B, C, E,
Options B and C are properties of NIO.2 and are good reason to use it over the 
`java.io.File` class. Option A is incorrect as both APIs can delete only empty directories, 
not a directory tree. Using a view to read multiple attributes leads to fewer round trips 
between the process and the file system and better performance, making option E correct. 
Views can be used to access file system-specify attributes that are no available in `Files` 
methods; therefore, option D is correct. `Files` is part of NIO.2, whereas `File` is part 
of `java.io`, which means option F is incorrect.


18) C,
Since a `Reader` may or may not support `mark()`, we can rule out options E, F, G, 
and H. Assuming `mark()` is supported, "P" is added to the `StringBuilder` first. Next, 
the position in the stream is marked before "E". The "E" is added to the `StringBuilder`, 
with "AC" being skipped, and then the "O" is added to the `StringBuilder`, with "CK" being 
skipped. The stream is then `reset()` to the position before the "E". The call to `skip(0)` 
doesn't do anything  since  there are  no  characters  to  skip, so "E" is added onto the 
`StringBuilder` in the next `read()` call. The value "PEOE" is printed, and option C 
is correct.


19) C,
The code compiles and runs without issues, so option G is incorrect. If we simplify 
the redundant path symbols, `p1` and `p2` represent the same path `/lizard/walking.txt`. 
Therefore, `isSameFile()` returns `true`. The second output is `false`, because `equals()` 
checks only if the path values are the same, without reducing the path symbols. Finally, 
`mismatch()` sees that the contents are the same and returns -1. For these reasons, 
option C is correct.


20) D,
The target path of  the  file  after  the  `move()`  operation  is  `/animals`, not 
`/animal/monkey.txt`, so options A and B are both incorrect. Bot will throw an exception 
at runtime since `/animal` already exists and is a directory. Next, the `NOFOLLOW_LINKS` 
option means that if the source is a symbolic kink, the link itself and not the target 
will be copied at runtime, so option C is also incorrect. The option `ATOMIC_MOVE` means 
that any process monitoring the file system will not see an incomplete file during the 
move, so option D is correct.


21) C,
The code compiles and runs without issue, so option D, E, and F are incorrect. The most 
important thing to notice is that the `depth` parameter specified as the second argument 
to `find()` is 0, meaning the only record that will be searched is the top-level directory. 
Since we know that the top directory is directory and not a symbolic link, no other paths 
will be visited, and nothing will be printed. For these reasons, option C is the correct. 


22) G
The code compiles, so option F is incorrect. To be serializable, a class must implement 
the `Serializable` interface, which `Zebra` does. It must also contain instance members 
that either are marked `transient` or are serializable. The instance member `stripes` is 
of the type `Object`, which is not serializable. If `Object` implemented `Serializable`, 
all object would  be serializable  by default,  defeating the the  purpose of having the 
`Serializable` interface. Therefore, the `Zebra'  class is  not serializable, with  the 
program throwing an exception at runtime if serialized and making option G correct. If 
`stripes` were removed from the class, options A and D would be the correct answers, as 
`name` and `age` are both marked `transient`.


23) A, D
The code compiles without issue, so option E and F are incorrect. The `toRealPath()` 
method will simplify the path to `/animals` and throw an exception if it does not exist, 
making option D correct. If the path does exist, calling `getParent()` on it returns the 
root directory. Walking the root directory with the filter expression will print all 
`.java` files in the root directory (along with all `.java`files in the directory tree), 
making option A correct. Option B is incorrect because it will skip files and directories 
that do not end in the `.java` extension. Option C is also incorrect as `Files.walk()` 
does not follow symbolic links by default. Only if the `FOLLOW_LINKS` option is provided 
and a cycle is encountered will the exception be thrown.


24) B
The methods compiles without issue, so option E is incorrect. Option F is also incorrect. 
Even though `/flip` exists, `createDirectories()` does not throw an exception if the path 
already exists. If `createDirectory()` were used instead, option F would be correct. Next, 
the `copy()` command takes a target that is  the  path to  the new file location, not the 
directory to be copied into. Therefore, the target path should be `/flip/sounds.txt`, not 
`/flip`. For this reason, option A and C are incorrect. Since the question says the file 
already exists, the `REPLACE_EXISTING` option must be specified or an exception will be 
thrown at runtime, making option B the correct answer.


25) B, D
Since we need to read characters, the Reader classes are appropriate. Therefore, 
we can eliminate options A, C and F. Additionally, options E and G are incorrect, as they 
reference classes that do not exist. Options B and D are correct since they read from a 
file and buffer for performance.

