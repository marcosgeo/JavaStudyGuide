## JShell - useful commands

`jshell`  -- starts the shell

`/exit`  -- for exit
`/clear`  -- clear the console screen
`/list`  -- list all snippets entered
`/save filename.jsh`  -- save current session into a file
`/open filename.jsh`  -- load an executes commans from a file
`/var`  -- list all declared vars
`/methods`  -- list all methods defined in the session


### Testing streams
```
List<Integer> num = List.of(1, 2, 3, 4);

nums.stream()
  .filter(n -> n % 2 == 0)
  .map(n -> n * 10)
  .toList();
```
- no need to create a class or main()
- great for learning new Java features (Streams, Optional, etc.)


### Data Transformation
Use case: a quick one-off script (like parsing logs or JSON)

```
Files.lines(Path.of("file.log"))
  .filter(line -> line.contains("ERROR"))
  .limit(5)
  .forEach(System.out::println);
```

### Testing Database
Use case: validate DB conectivity or queries quickly
```
import java.sql.*;
Connection conn = DriverManager.getConnection(
  "jdbc://user:pass@postgres:port/db", "name", "pw");

conn.createStatement()
  .execute("CREATE TABLE test(id INT)");

con.createStatement()
  .execute("INSERT INTO test VALUES (1)");

var rs = conn.createStatement().executeQuery("select * from test");
```
a jshell session
```
jshell> /list

   1 : String pyramid = """
         *
        *
       ***
       """;
   2 : System.out.println(pyramid);
   3 : String spaces = """
         two
           four
             six
       """;
   4 : spaces
   5 : System.out.println(spaces);
   6 : String __ = "none";
   7 : __
   8 : System.out.println(__);
   9 : boolean b1, b2;
  10 : boolean b1, b2;
  11 : String s1 = "2", s2;
  12 : String s1 = "2", s2;
  13 : int i1;
  14 :  int i2;
  15 : int i3;
  16 : final int yy = 10;
  17 : int xx = 20;
  18 :  yy = xx + 10;
  19 : yy
  20 : public int valid(){
           int y = 10;
           int x;
           x = 3;
           int z;
           int reply = x + y;
           return reply;
       }
  21 : valid()
  22 : char c;
  23 : System.out.println(c);
  24 : byte b;
  25 : b = -1
  26 : b
  27 : b = -127
  28 : b = -128
  29 : b
  30 : b
  31 : b = -1
  32 : b
  33 : b = 127
  34 : b
  35 : yy
  36 : class User {
           String name;
           public User(){
               name="fulano";
           }
       }
  37 : User user = new User();
  38 : user
  40 : class Person {
           public static int quant = 0;
           private String name;
           public Person(String nm){
               name = nm;
               quant += 1;
           }
       }
  41 : Person p1 = new Person("Joao");
  42 : Person p2 = new Person("Joao");
  43 : Person p3 = new Person("Jose");
  44 : Person.quant
  45 : var p4 = new Person("Luis");
  46 : p4
  47 : String var = "Carlos";
  48 : var
  49 : public void eatIfHungry(boolean hungry){
           if (hungry){
               int bitesOfCheese = 1;
               {
                   var teenyBit = true;
                   System.out.println(bitesOfCheese);
               }
           }
           System.out.println(teenyBit);
       }
  50 : class people {  // yes, this is a valid class
           String name;
           public people(String n){
               name=n;
           }
       }  // although not following the conventions, this code compiles
  51 : people p = new people("povo");
  52 : p

```


## garbage collection

the names to the left are variables and the |o| are objects. each line
show when a variable is pointed to an object. When an object has no
variable referencing him or when all references the point to it are out
of scope, it is elegible for garbage collection (gc). 
Bellow is a schema to tracing eligibility for gc.

```
one, two
"one" xx |a|
"two" -> |b|
"one" -> |b|
"three" -> |b|
"one" xx |b|
```

