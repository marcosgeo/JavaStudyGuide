## Chapter 15: JDBC


1) 

B, F

The `Driver` and `PreparedStatement` interfaces are part of the JDK, making option 
A and E incorrect. Option C is incorrect because we made it up. The concrete `DriverManager` 
class is also part of the JDK, making option D incorrect. Options B and F are correct 
since the implementation of these interfaces is part of the database-specific driver 
JAR file.


2) 

A

A JDBC URL has three main parts separated by single colons, making options B, C, E and 
F incorrect. The first part is always `jdbc`, making option D incorrect. Therefore, the 
correct answer is option A. Notice that we can get this right even if we're never heard 
of the Sybase database before.

3) 

B, D

When setting parameters on a `PreparedStatement`, there are only option that take an 
index, making options C and F incorrect. The indexing starts with 1, making option A 
incorrect. This query hs only one parameter, so option E is also incorrect. Option B 
is correct because it simply sets the parameter. Option D is also correct because it 
sets the parameter and then immediately overwrites it with the same value.