package packageb;

import packagea.ClassA;

public class ClassB {
  public static void main(String[] args) {
    ClassA a;
    System.out.println("Got it!");
  }
}

/*
## compiling in the same directory

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

---
this is also valid:
javac packagea/*.java packageb/*.java

---
but this not:
javac *.java

---
in the end, this command should execute:
java packageb.ClassB
---
Got it!

---


*/

