# Chapter 13: Concurrency

1) 
D, F
There is no such class within the Java API called ParallelStream, so options A 
and E are incorrect. The method in the `Stream` class to create a parallel stream 
from an existing stream is `parallel()`; therefore, option F is correct, and option 
C is incorrect. The method defined in the `Collection` class to create a parallel 
stream from a collection is `parallelStream(); therefore, option D is correct and 
option B is incorrect.


2) 
A, D
The `tryLock()` method return immediately with a value of `false` if the lock 
cannot be acquired. Unlike `lock()`, id does not wait for a lock to become available. 
This code fails to check the return value on line 8, resulting in the protected code 
being entered regardless of whether the lock is obtained. In some executions (when 
`tryLock()` return `false` at least once), the `unlock()` method on line 10 will throw 
an `IllegalMonitorStateException` at runtime, making option D correct. Option B would 
be possible if line 10 did no throw an exception.


3) 
B, C, F
`Runnable` return `void` and `Callable` return a generic type, making options A 
and D incorrect and option F correct. All methods are capable of throwing unchecked 
exceptions, so option E is incorrect. Both `Runnable` and `Callable` are functional 
interfaces that can be implemented with a lambda expression, so option C is also correct.

4) 
B, C
The code does not compile, so options A and F are incorrect. The first problem 
is that although a `ScheduleExecutorService` is created, is is assigned to an 
`ExecutorService`. The type of the variable on line `w1` would have to be updated 
to `ScheduledExecutorService` for the code to compile, making option B correct. 
The second problem is that `scheduledWithFixedDelay()` supports only `Runnable`, 
not `Callable`, and any attempt to return a value is invalid in a `Runnable` 
lambda expression; therefore line `w2` will also not compile, and option C is correct. 
The rest of the lines compile without issue, so options D and E are incorrect.


5) 
C
The code compiles and run without throwing an exception or entering an infinite loop, 
so options D, E, and F are incorrect. The key here is that the increment operator `++` 
is not atomic. While the first part of the output will always be 100, the second part 
is non-deterministic. It may output any value from 10 to 100, because the threads can 
overwrite each other's work. Therefore, option C is the correct answer, and options A
and B are incorrect.

6) 
C, E
The code compiles, so option G is incorrect. The `peek()` method on a parallel stream 
will process the elements concurrently, so the order cannot be determined ahead of time, 
and option C is correct. The `forEachOrdered()` method will process the elements in the 
order in which they are stored in the stream, making option E correct. None of the 
methods sort the elements so options A and D are incorrect.

7) 
D
Livelock occurs when two or more threads are conceptually blocked forever, although 
they are each still active and trying to complete their task. A race condition is an 
undesirable result that occurs when two tasks that should have been completed 
sequentially are completed at the same time. For these reasons, option D is correct.

8) 
B
We have to be wary with `run()` vs `start()`. The method looks like it executes a 
task concurrently, but it runs synchronously. In each iteration of the `forEach()` 
loop, the process waits for the `run()` method to complete before moving on. For 
this reason, the code is thread-safe. Since the program consistently prints 500 at 
runtime, option B is correct. Note that if `start()` had been used instead of `run()`, 
or the stream was parallel, the output would be indeterminate, and option C would 
have been correct.

9) 
C
If a task is submitted to a thread executor, and the thread executor does not have 
any available threads, the call to the task will return immediately with the task 
being queued internally by the thread executor. For this reason, option C is the 
correct answer.

10) 
A
The code compiles without issue, so option D is incorrect. The `CopyOnWriteArrayList` 
class is designed to preserve the original list on iteration, so the first loop will be 
executed  exactly three times and, in the precess, will increase the size of `tigers` to 
six elements. The `ConcurrentSkipListSet` class allows modification, and since it 
enforces the uniqueness of its elements, the value "5" is added only once, leading 
to a total of four elements in `bears`. Finally, despite using the elements of `lions` 
to populate the collection, `tigers` and `bears` are not backed by the original list, 
sot the size of `lions` is 3 throughout this program. For these reasons, the program 
prints 3 6 4, and option A is correct.

11) 
F
The code compiles and runs without issue, so options C, D, E and G are incorrect. 
There are two important things to notice. First, synchronizing on the first variable 
doesn't impact the results of the code. Second, sorting on a parallel stream does not 
mean that `findAny()` will return the first record. The `findAny()` method will return 
the value from the first thread that retrieves a record. Therefore, the output is not 
guaranteed, and option F is correct. Option A looks correct, bu even on serial streams, 
`findAny()` is free to select any element.

12) 
B
The code snippet submits three task to an `ExecutorService`, shuts it down, and then 
waits for the results. The `awaitTermination()` method waits a specified amount of time 
for all tasks to complete and the service to finish shutting down. Since each five-second 
task is still executing, the `awaitTermination()` method will return with a value of 
`false` after two seconds but no throw an exception. For these reasons, option B is 
correct.


13) 
C
The code does not compile, so options A and E are incorrect. The problem here is that 
`c1` is a `String`, so the code fails to combine on line `q2`, since calling `length()` 
on an `Integer`is not allowed, and option C is correct. The rest of the lines compile 
without issue. Note that calling `parallel()`on an already parallel stream is allowed, 
and it may return the same object.


14) 
C, E
The code compiles without issue, so option D is incorrect. Since both tasks are 
submitted to the same thread executor pool, the order cannot be determined, so options 
A and B are incorrect, and option C is correct. The key here is that the order in which 
the resources `o1` and `o2` are synchronized could result in a deadlock. For example, 
if the first thread gets a lock on `o1` and the second thread gets a lock on `o2` before 
either thread can get their second lock, the code will hang at runtime, making option E 
correct. The code cannot produce a livelock, since both threads are waiting, so option 
F is incorrect. Finally, if a deadlock does occur, an exception will bot be thrown, 
so option G is incorrect.


15) 
A
The code compiles and runs without issue, so options C, D, E, and F are incorrect. 
The `collect()` operation groups the animals int those that do and do not start with 
the letter "p". Note that there are four animals that do not start with the letter "p" 
and three animals that do. The logical complement operator (!) before the `startsWith()` 
method means that results are reversed, so the output is "3 4", and option A is correct, 
making option B incorrect.


16) 
A, B
The code compiles just fine. If the call to `fuel++` are ordered sequentially, then 
the program will print "100" at runtime, making option B correct. On the other hand, 
the call may overwrite each other. The `volatile` attribute only guarantees memory 
consistency, not thread-safety, making option A correct and option C incorrect. Option 
E is also incorrect, as no `InterruptedException` is thrown by this code. To remember, 
`interrupt()` only impacts a thread that is in a `WAITING` or `TIMED_WAITING` state. 
Calling `interrupt()` on a thread in a `NEW` or `RUNNABLE` state has no impact unless 
the code is running and explicitly checking the `isInterrupted()` method.

17) 
F
The `lock()` method will wait indefinitely for a lock, so option A is incorrect. 
Options B and C are also incorrect, as the correct method name to attempt to acquire 
a lock is `tryLock()`. Option D is incorrect, as `fairness` is set to `false` by default 
and must be enabled by using an overloaded constructor. Finally, option E is incorrect 
because a thread that holds the lock may have called `lock()`  or  `tryLock()` multiple 
times. A thread need to call `unlock()` once for each call to `lock()` and successful 
`tryLock()`. Option F is the correct answer since none of the other options are valid 
statements.


18) 
C, E, G
A `Callable` lambda expression takes no values and return a generic type; therefore, 
option C, E, and G are correct. Options A and F are incorrect because they both take an 
input parameter. Option B is incorrect because it does not return a value. Option D is 
not a valid lambda expression, because it is missing a semicolon at the end of the return 
statement, which is required when inside braces, {}.


19) 
E, G
The application compiles and does not thrown an exception. Even though the stream 
is processed in sequential order, the tasks are submitted to a thread executor, which 
may complete the tasks in any order. Therefore, the output cannot be determined ahead 
of time, and option E is correct. Finally, the thread executor is never shut down; 
therefore, the code will run but never terminate, making option G also correct.

20) 
F
The key to solving this question is to remember that the `execute()` method returns 
`void`, ot a `Future` object. Therefore, line `n1` dos not compile, and option F is 
the correct answer. It the `submit()` method had been used instead of `execute()`, 
option C would have been the correct answer, as the output of the `submit(Runnable)` 
task is a `Future<?>` object that can only return `null` on its `get()` method.


21) 
A, D
The `findFirst()` method guarantees the first element in the stream will be returned, 
whether it is serial or parallel, making options A and D correct. While option B may 
consistently print "1" at runtime, the behavior of `findAny()` on a serial stream is 
not guaranteed, so option B is incorrect. Option C is likewise incorrect, with the 
output being random at runtime.


22) 
B
The code compiles and runs without issue. The key aspect to notice in the code is that 
a single-thread executor is used, meaning that no task will be executed concurrently. 
Therefore, the results are valid and predictable, with "100 100" being the output, and 
option B is correct answer. If a thread executor with more threads was used, then the 
`s2++`operation could overwrite each other, making the second value indeterminate at the 
end of the program. In this case, option C would be the correct answer.

23) 
F
The code compiles without issue, so options B, C, and D are incorrect. The `limit` on 
the cyclic barrier is 10, but the stream can generate only up to 9 threads that reach 
the barrier; therefore, the `limit` can never be reached, and option F is the correct 
answer, making options A and E incorrect. Even if the `limit(9)` statement was changed 
to `limit(10)`, the program could still hang since the JVM might not allocate 10 
threads to the parallel stream.

24) 
A, F
The class compiles without issue, so option A is correct. Since `getInstance()` 
is a static method and `sellTickets()` is an instance method, like `k1` and `k4` 
synchronize on different objects, making option D incorrect. The class is not thread-
safe because the `addTickets()` method is not synchronized, and option E is incorrect. 
One thread could call `sellTickets()` while another thread call `addTickets()`, possibly 
result in bad data. Finally, option F is correct because the `getInstance()` method is 
synchronized. Since the constructor is private, this method is the only way to create 
an instance of `TicketManager` outside the class. The first thread to enter the method 
will set the instance variable, and all other threads will use the existing value. 
This a a singleton pattern.


25) 
C, D
The code compiles and runs without issue, so options F and G are incorrect. The return 
type of `performCount()` is `void`, so `submit()` is interrupted as being applied to a 
`Runnable`expression. While `submit(Runnable)` does return a `Future<?>`, calling `get()` 
on it always returns `null`. For this reason, option A and B are incorrect, and option C 
is correct. The `performCount()` method can also throw a runtime exception, which will 
then be thrown by the `get()` call as an `ExecutionException`; therefore, option D is 
also a correct answer. Finally, it is also possible for our `performCount()` to hang 
indefinitely, such as with a deadlock or infinite loop. Luckily, the call to `get()` 
includes a timeout value. While each call fo `Future.get()` can wait up to a day for 
a result, it will eventually finish, so option E is incorrect.
