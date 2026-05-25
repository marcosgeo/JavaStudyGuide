## Control Flow

### switch statement
**executes a block of code**
Be aware with switch statements. The switch below compiles, but is give strange results:

```
public void printSeason(int month) {
     switch(month) {
         case 1, 2, 3:
           System.out.println("Summer at suthern hemisphere");
           System.out.println("Winter at northern hemisphere");
         case 4, 5, 6:
           System.out.println("Spring in north, Fall in south");
         default: System.out.println("Since no 'break' was included, this will show");
         case 7, 8, 9: System.out.println("Winter in south, Summer in north");
         case 10, 11, 12:
           System.out.println("Spring in south, Fall in north");
     }
 }
```
Calling `printSeason(3)` will print all output statements.

It is common, although certainly not required, to use a break statement after every case statement.


### switch expression
**executes a block of code or must return (with yeld) a value**
We can rewrite the previous method in a much more concise manner using case expressions:

```
public void printSeason(int month) {
    switch(month) {
        case 1, 2, 3 -> System.out.print("Winter");
        case 4, 5, 6 -> System.out.print("Spring");
        case 7, 8, 9 -> System.out.print("Summer");
        case 10, 11, 12 -> System.out.print("Fall")
    }
}
```

Calling `printSeason(3)` prints the single value "Winter". This time we don't have to
worry about break statements, sinc only on branch is executed.

## while loops


### *break* statement and *label*

A label is a reference that we put on a code block (bad) or, commom, in a loop or switch
```
optionalLabel: while (boolean expression) {
  // body
  
  // somewhere in the loop
  break optionalLabel;
}
```

this is ideal to break out of a higher-level outer loop whithout the need to use a control variable and a if statement.

```
jshell> public class FindInMatrix {
  public static void exec() {
    int[][] list = {{1, 23}, {5, 2}, {8, 7}};
    int searchValue = 2;
    int positionX = -1;
    int positionY = -1;
     
    PARENT_LOOP: for (int i = 0; i < list.length; i++) {
      for (int j = 0; j < list[i].length; j++) {
        if (list[i][j] == searchValue) {
          positionX = i;
          positionY = j;
          break PARENT_LOOP;
        }
      }
    }
    if (positionX == -1 || positionY == -1) {
      System.out.println("Value " + searchValue + " not found");
    } else {
      System.out.println("Value " + searchValue + " found at " +
          "(" + positionX + ", " + positionY +")");
    }
  }
}
|  created class FindInMatrix

jshell> FindInMatrix.exec();
Value 2 found at (1, 1)
```

Label applyed to a continue statement:
```
jshell> public class CleaningSchedule {
  public static void exec() {
    CLEANING: for (char stables = 'a'; stables <= 'd'; stables++) {
      for (int leopard = 1; leopard < 4; leopard++) {
        if (stables == 'b' || leopard == 2) {
          continue CLEANING;
        }
        System.out.println("Cleaning: " + stables + ", " + leopard);
      }
    }
  }
}
|  created class CleaningSchedule

jshell> CleaningSchedule.exec();
Cleaning: a, 1
Cleaning: c, 1
Cleaning: d, 1
```




