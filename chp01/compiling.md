## compiling in the same directory

``` 
javac packagea/ClassA.java packageb/ClassB.java
tree
---
.
├── NumberPicker.java
├── packagea
│   ├── ClassA.class
│   └── ClassA.java
├── packageb
│   ├── ClassB.class
│   └── ClassB.java
├── Zoo.class
└── Zoo.java
```
---
this is also valid:
javac packagea/*.java packageb/*.java

---
but this not:
```
javac *.java

---
```

in the end, this command should execute:
```
java packageb.ClassB
---
Got it!

```

## compiling to a target directory

```
rm packagea/ClassA.class 
rm packageb/ClassB.class 
javac -d classes packagea/ClassA.java packageb/ClassB.java 
tree
---
.
├── classes
│   ├── packagea
│   │   └── ClassA.class
│   └── packageb
│       └── ClassB.class
├── NumberPicker.java
├── packagea
│   └── ClassA.java
├── packageb
│   └── ClassB.java
├── Zoo.class
└── Zoo.javal

6 directories, 7 files
---
```

Note that the target directory is created by the compile command

now, to run the program we specify the classpath:

`java -cp classes packageb/ClassB`

or

`java --classpath classes packageb/ClassB`


--
## Compiling with JAR files

Just like the classes directory in the previous example, you can also specify the location of the other files explicitly using a classpath. 
This technique is useful when the class files are located elsewhere or in special JAR files. 
A Java archive (JAR) file is like a ZIP file of mainly Java class files.


`java -cp ".:/temp/someOtherLocation:/temp/myJar.jar" myPackage.MyClass`
`java -cp ".:/temp/directoryWithJARS/*" myPackage.MyClass`


The period (.) indicates that you want to include the current directory in the classpath. The rest of the command says to look for loose class files (or packages) in someOtherLocation and within myJar.jar. 
Windows uses semicolons (;) to separate parts of the classpath; other operating systems use colons.

## createing JAR file

The simplest commands to crate a jar containning the files in the current directory is this:

```
jar -cvf myNewFile.jar .

or

jar --create --verbose --file myNewFile.jar .
```

or, specify a directory

`jar -cvf myNewFile.jar -C somePath`


