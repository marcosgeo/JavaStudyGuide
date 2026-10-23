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
