# Chapter 15: JDBC

## Objectives

### Accessing Databases using JDBC

* Create connections, create and execute basic and prepared queries 
 - callable statements
 - process queries results
 - control transaction using JDBC API


JDBC stands for Java Database Connectivity. This chapter introduces to the basics 
of accessing databases from Java. We cover the key interfaces for how to connect, perform 
queries, process the results, and work with transactions.

[Introducing Relational Database and SQL](#introducing-relational-databases-and-sql)

[Connecting to a Database](#connecting-to-a-database)



## Introducing Relational Databases and SQL

A _relational database_ is a database tha is organized into _tables_, which consist 
of rows and columns of data. We can think of a tables as a spreadsheet. There are two 
main ways to access a relational database from Java:
* _Java Database Connectivity (JDBC)_: Access data as rows and columns. JDBC is the API 
    covered in this chapter
* _Java Persistence API (JPA)_: Access data through Java objects using a concept called 
  _object-relational mapping (ORM)_. The idea is that we don't have to write as much code, 
  and we get our data in  Java  objects.  JPA  is  not on the exam, and therefore is not 
  covered in this chapter.

A relational database is accessed through _Structured Query Language (SQL)_. SQL is a 
programming language used to interact with database records. JDBC works by sending a SQL 
command to the database and then processing the response.

In addition to relational databases, there is another type of database called a _NoSQL_ 
_database_. These databases store their data in a format other than tables, such as key/
value, document stores, and graph-based databases. NoSQL is out of scope for the exam also.

In the following sections we introduce a small relational database that we will be 
using for the examples in this chapter and present the SQL to access it. We also cover 
some vocabulary that are necessary for working with databases. The instructions to 
download and set up the database fot the example in this chapter are here: 
[selikoff.net/ocp17](https://www.selikoff.net/2022/01/22/ocp-17-book-bonus-creating-a-hsql-database-in-java-17/)


### Identifying the Structure of a Relational Database

Our sample database hat two table. One has a row for each species that is in our 
zoo. The other has a row for each animal. These two relate to each other because an 
animal belongs to a species. These relationships are wy this type of database is called 
relational database. Figure 15.1 shows the structure of out database.

![table in relational database](img/tables_relational_database.png)

**Figure 15.1: Tables in relational database**

As we can see, we have two tables. One is name `exhibits` and the other is named 
`names`. Each table has a _primary key_, which gives us a unique way to reference each 
row. After all, two animal might have the same name, but they can't have the same `ID`. 
In out example, it so happens that the primary key is only one column. In some cases, 
it is a combination of columns, called _compound key_.


## Writing Basic SQL Statements

The most basic SQL commands are resumed to four types of statements for working with 
data in tables. They are referred to as CRUD (create, read, update, delete). The SQL 
keyword don't match the acronym, so we need to pay attention to the SQL keyword for 
each in Table 15.1.

**Table 15.1: The CRUD operation**

![the crud operations](img/the_crud_operations.png)


Unlike Java, SQL keywords are case insensitive. This means `select`, `SELECT`, and 
`Select` are all equivalent. Like Java primitive types, SQL has a number of data types. 
Most are self-explanatory, like `INTEGER`. There's also `DECIMAL`, which functions a lot 
like a `double` in Java. The one that needs to note is `VARCHAR`, standing for "variable 
character", which is like a `String` in Java. The _variable_ part means that the database 
should use only as much space as it needs to store the value.


## Introducing the Interfaces of JDBC

There are five key interfaces of JDBC that we need to know. They are declared in the 
JDK. The are just like all of the other interfaces and classes that we've seen in this 
book. For example, in Chapter 9, "Collection and Generics", we worked with the interface 
`List` and the concrete class `ArrayList`.

With JDBC, the concrete classes come from the JDBC driver. Each database has a different 
JAR file with theses classes. For example, PostgreSQL's JAR is called sometimes like 
`postgresql-9.4-1201.jdbc4.jar`. MySQL's JAR is called sometimes like `mysql-connector-java-5.1.36.jar`. 
The exact name depends on the vendor and version of the driver JAR.

This driver JAR contains an implementation of these key interfaces along with a number 
of other interfaces. The key is that the provided implementations know how to communicate 
with a database. There are also different types of drivers; luckily, we don't need to use 
all of them in the same project.

Figure 15.2 shows the five key interfaces that we need to know the most. It also shows 
that the implementation is provided by an imaginary `Foo` driver JAR. They cleverly stick 
the name `Foo` for all classes.

![jdbc key interfaces](img/jdbc_key_interfaces.png)

**Figure 15.2: Key JDBC interfaces**

Notice that we didn't mention what the implementing classes are called in any real 
database. The main point is that we shouldn't know. With JDBC, we use only the interfaces 
in our code and never the implementation classes directly. In fact, they might no even 
be public classes.

What do these five interfaces do? On a very high level, we have the following:
- Driver: Establishes a connection to the database
- Connection: Sends commands to a database
- PreparedStatement: Executes SQL query
- CallableStatement: Executes commands stored in the database
- ResultSet: Reads the results of a query

All database interfaces are in the package `java.sql` so, sometimes the imports 
are omitted.

In this next example, we show what JDBC code looks like, end to end. If you are 
new to JDBC, just notice that three of the five interfaces are in the code. If 
you are experienced, notice that here we uses the `DriverManager` class instead 
of the `DataSource` interface.

```
import java.sql.*;

public class MyFirstDatabaseConnection{
  public static void main(String[] args) throws SQLException {
    String url = "jdbc:hsqldb:file:zoo";
    try (
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement ps = conn.prepareStatement("SELECT name FROM exhibits");
      ResultSet rs = ps.executeQuery()
    ) {
      while (rs.next())
        System.out.println(rs.getString(1));
    }
  }
}
```

If the URL were using out imaginary Foo driver, `DriverManager` would return an 
instance of FooConnection. Calling `preparedStatement()` would then return an instance 
of FooPreparedStatement, and  calling  `executeQuery()`  would  return  an instance of 
FooResultSet. Since the URL uses `hsqldb` instead, it returns the implementation that 
HyperSQL has provided for these interfaces. We don't need to know their names. In the 
rest of the chapter, we explain how to use all five of the interfaces and go into more 
detail about what they do. By the end of the chapter, writing code like this will be 
easy.

---
**Compiling with Modules**

Almost all packages on the exam are in the `java.base` module. As we saw in Chapter 
12, Module, this module is included automatically when we run our application as a module.

In contrast, the JDBC classes are all in the module `java.sql`. They are also in the 
package `java.sql`. The names are the same, so they should be easy to remember. When 
working with SQL, we need the `java.sql` module and `import java.sql.*`.

Is recommended separating the studies for JDBC and modules. We can use the `classpath` 
when working with JDBC and reserve our practice with module path for when we are studying 
modules.

That said, to use JDBC code with modules, remember to update the `module-info` file to 
include the following:
```
    requires java.sql;
```

---

[back to top](#chapter-15-jdbc)


## Connecting to a Database

The first step in doing anything with a database is connecting to it. First we 
will see how to build the JDBC URL. Then we will use it to get a `Connection` to 
the database.


### Building a JDBC URL

To access a website we need to know it URL. To access our e-mail, we need to know 
our username and password. JDBC is no different. To access a database, we need to know 
this information about it.

Unlike web URLs, JDBC URLs have a variety of formats. the have three parts in common, 
as shown in Figure 15.3. The first piece is always the name. It is the protocol `jdbc`. 
The second part is the _subprotocol_, which is the name of the database or, been more 
precise, the _database vendor name_, such as `hsqldb`, `mysql`, or `postgres`. The third 
part is the _subname_, which  is  a  database-specific  format. Colons (:) separate the 
three parts.

![jdbc url parts](img/jdbc_url_parts.png)

**Figure 15.3: The JDBC URL format**


The subname typically contains information about the database such as its location 
and/or name. The syntax varies. We need to know about the three main parts. We don't 
need to memorize the subname formats. We've already seen one such URL: 
```
jdbc:hsqldb:file:zoo
```

Notice the three parts. It starts with `jdbc`, and then comes the subprotocol `hsqldb`. 
It ends with the subname, which tells us we are using the `file` system. And finally, 
the location is then the database name: `zoo`. Other examples of sub-names are shown here:
```
jdbc:postgresql://localhost/zoo
jdbc:oracle:thin:@123.123.123.123:1521:zoo
jdbc:mysql://localhost:3306
jdbc:mysql://localhost:3306/zoo?profileSQL=true
```

We can see that each of these JDBC URLs begins with `jdbc`, followed by a colon, 
followed by the vendor/product name. After that, the URLs vary. Notice how all of 
them include the location of the database: `localhost`, `123.123.123.123:1521`, and 
`localhost:3306`. Also notice that the `port` is optional when using the default 
location.


### Getting a Database _Connection_
