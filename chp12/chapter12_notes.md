# Chapter 12: Modules

**Objectives:**
- Packaging and deploying Java code and use the Java Platform Module System
- Define modules and their dependencies, exposes module content including for 
    reflection. Define services, producers, and consumers
- Compile Java code, produce modular and non-modular jars, runtime images, and 
    implement migration using unnamed and automatic modules

Packages can be grouped int modules. In this chapter, the purpose of modules and 
how to build our own are explained. We also will see how to run them and how to 
discover existing modules. Next, we will cover strategies for migrating an application 
to use modules, running a partially modularized application, and dealing with 
dependencies. Whe then move on to discuss services and services locators. Finally, 
we show how to create a runtime image.

## Introducing Modules

When writing code in real life, programs tends to become bigger. A real project 
will consist of hundreds or thousands of classes grouped int packages. These packages 
are grouped into Java archive (JAR) files. A JAR is a ZIP file with some extra 
information, and the extension is .jar.

The Java Platform Module System includes the following:
- a format for module JAR files
- Partitioning of the JDK into modules
- Additional command-line options for Java tools


### Exploring a Module

In Chapter 1 we had a small Zoo application. It had only on class and just printed 
out on thing. Now imagine that we had a whole staff of programmers and were automating 
the operation of the zoo. Many things need to be coded, including the interaction with 
the animals, visitors, the public website, and outreach.

A _module_ is a group of on or more packages plus a special file called `module-info.java`. 
The contents of this file are the _module declaration_. Figure 12.1 list just a few of the 
modules a zoo might need. We decided to focus on the animal interactions in our example. 
The full zoo could easily have a dozen modules.

![design modular system](design_modular_system.png)

**Figure 12.1: Design of a modular system**

Now let's drill down into on of these modules. Figure 12.2 shows wht is inside the 
zoo.animal.talks module. There are three packages with two classes each. (It's a small 
zoo). There is also a strange file called module-info.java. This file is required to 
be inside all modules. More detail will be explained later in this chapter.

![looking inside module](looking_inside_module.png)

**Figure 12.2: Looking inside a module**

### Benefits of Modules

Modules look like another layer of things we need to know in order to program. While 
using modules is optional, it is important to understand the problems they are designed 
to solve:

- **Better access control**: in additional to the levels of control covered in chapter 5,
    Methods, we can have packages that are only accessible to other packages in the module.
- **Clearer dependency management**: since modules specify what the rely on, Java 
    can complain about a missing JAR when starting up the program rather than when it 
    is first accessed at runtime.
- **Custom Java builds**: we can create a Java runtime that has only the parts of the 
    JDK that our program needs rather than the full on at over 150 MB.
- **Improved security**: since we can omit parts of the JDK from our custom build, we 
    don't have to worry about vulnerabilities discovered in a part we don't use.
- **Improved performance**: another benefit of a smaller Java package is improved 
    startup time and a lower memory requirement.
- **Unique package enforcement**: since modules exposed packages, Java can ensure 
    that each package comes from only one module and avoid confusion about what is 
    being run.

[back to top](#chapter-12-modules)


## Creating and Running a Modular Program
