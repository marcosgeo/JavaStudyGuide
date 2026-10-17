# Chapter 13: Concurrency

* Managing concurrent code execution
  - Create worker threads using `Runnable` and `Callable`, manage the thread lifecycle, 
      including automations provided by different `Executor` services and concurrent API
  - Develop thread-safe code, using different locking mechanisms and concurrent API
  - Process Java collection concurrently including the use of parallel stream.
* Working with Stream and Lambda expressions
  - Perform decomposition, concatenation, and reduction an grouping and partitioning 
      on sequential and parallel streams.

As we will see in Chapter 14, I/O, and Chapter 15, JDBC, computers are capable of 
reading and writing data to external resources. Unfortunately, as compared to CPU 
operations, these disk/network operation tend to be extremely slow. In fact, it is 
so slow that, if our computer's operating system were to stop and wait for every 
disk or network operation to finish, our computer would appear freeze constantly.

Luckily, all operation systems support what is known as _multithreaded processing_. 
The idea behind multithreaded processing is to allow an application or group of 
applications to execute multiple tasks at the same time. This allows tasks waiting 
for other resources to give way to other processing requests.

In this chapter, we will see the concept of threads and numerous way to manage 
threads using the Concurrency API. Threads and concurrency are challenging topics 
for many programmers to grasp, as problems with threads can be frustrating even 
for veteran developers. In practice, concurrency issues are among the most 
difficult problems to diagnose and resolve.


[Introducing Thread](#introducing-threads)

[Creating Threads with Concurrency API](#creating-threads-with-the-concurrency-api)

[Writing Thread-Safe Code](#writing-thread-safe-code)

[Using Concurrent Collections](#using-concurrent-collections)


## Introducing Threads

We begin this chapter by reviewing common terminology associated with threads. A 
_thread_ is the smallest unit of execution that can be scheduled by the operation 
system. A _process__ is a group of associated threads that execute in the same 
shared environment. It follows, then, that a _single-threaded process_ is one that 
contains exactly on thread, whereas a _multithreaded process_ supports more than 
one thread.

By _shared environment_, we mean that the threads in the same process share the 
same memory space and can communicate directly with one another. Refer to Figure 13.1 
for an overview of thread and their shared environment within a process.

This figure shows a single process with three threads. It also shows how they are 
mapped to an arbitrary number of _n CPUs_ available within the system.

![process model](process_model.png)

**Figure 13.1: Process model**

By _shared memory_ in Figure 13.1, we are generally referring to static variables 
as well as instance and local variables passed to a thread. Yes, we will finally see 
how static variables can be useful for performing complex, multithreaded tasks!. From 
Chapter %, Methods, we see that static methods and variables are defined on a single 
class object that all instances share. For example, if on thread updates the value of 
a static object, this information is immediately available for other threads within 
the process to read.


### Understanding Thread Concurrency

The property of executing multiple threads and process at the same time is referred 
to as _concurrency_. How does the system decide what to execute when there are more 
threads available than CPUs? Operating systems use a _thread schedule_ to determine 
which threads should be currently executing, as shown in Figure 13.1. For example, a 
thread schedules may employ a _round-robin scheduler_ in which each available thread 
receives an equal number of CPU cycles with which to execute, with threads visited 
in a circular order.

When a thread's alloted time is complete but the thread has not finished processing, 
a context switch occurs. A _context switch_ is the process of storing a thread's current 
state and later restoring the state of the thread to continue execution. Be aware that a 
cost is often associated with a context switch due to lost time and having to reload a 
thread's state. Intelligent thread schedulers do their best to minimize the number of 
context switches while keeping an application running smoothly.

Finally, a thread can interrupt or supersede another thread if it has a higher thread 
priority than the other thread. A _thread priority_ is a numeric value associated with 
a thread that is taken into consideration by the thread scheduler when determining 
which threads should currently be executing. In java, thread priorities are specified 
as integer values.


### Creating a Thread

One of the most common ways to define a task for a thread is by using the `Runnable` 
instance. `Runnable` is a functional interface that takes no arguments and returns no data.
```
@FunctionalInterface
public interface Runnable {
  void run();
}
```

With this, it's easy to create and start a thread. In fact, we can do so in one line of
code using the `Thread` class:
```
new Thread( () -> System.out.print("Hello") ).start();
System.out.println("Word");
```

The first line create a new `Thread` object and then starts it with the `start()` 
method. Does this code print "HelloWord" or "WordHello"? The answer is that we don't 
know. Depending on the thread priority/scheduler, either is possible. Remember that 
order of thread execution is no often guaranteed.

let's take a look at a more complex example:
```
Runnable printInventory = () -> System.out.println("Printing zoo inventory");
Runnable printRecords = () -> {
  for (int i = 0; i < 3; i++)
    System.out.println("Printing record: " + i)
};
```

Given these instances, what is the output of this block of code? 
```
1: main(String... args){
2:   System.out.println("begin");
3:   new Thread(printInventory).start();
4:   new Thread(printRecord).start();
5:   new Thread(printInventory).start();
6:   System.out.println("end")
7: }
```

The answer is that it is unknown until runtime. The following is just one possible output:
```
begin
Print record: 0
Printing zoo inventory 
end
Printing record: 1
Printing zoo inventory
Printing record: 2
```

This sample uses a total of four thread: the `main()` user thread and three addition 
thread created on lines 3, 4 and 5. Each thread created on these lines is executed as 
an asynchronous task. By _asynchronous_, we men that the thread executing the `main()` 
method does not wait for the results of each newly created thread before continuing. For 
example, lines 4 and 5 may be executed before the thread created on line 3 finishes. The 
opposite of this behavior is a _synchronous_ task in which the program waits (or blocks) 
on line 3 for the thread to finish executing before moving on the next line. The vast
majority of method call used in this book have been synchronous up until this chapter.

While the order of thread execution is indeterminate once the threads have been started, 
the order within a single thread is still linear. In particular, the `for() loop` is 
ordered. Also, "begin" always appears before "end" on the program above.

More generally, we can create a `Tread`and its associated task one of two ways in Java:
- Provide a `Runnable` object or lambda expression to the `Tread` constructor
- Create a class that extends `Thread` and overrides the `run()` method

Throughout this book, we prefer creating tasks with lambda expression. After all, 
it's a lot easier, especially when we get to the Concurrency API. Creating a class that 
extends `Tread` is relatively uncommon and should only be done under certain circumstances, 
such as if we need to overwrite other threads methods.

---
**Calling _run()_ Instead of _start()_**

Sometime we could see code that attempts to start a thread by calling `run()` instead 
of `start()`. Calling `run()` on a `Thread` or a `Runnable` _does not start a new thread_. 
While the following code snippets will compile, none will execute a task on a separate 
thread.
```
{
  System.out.println("begin");
  new Thread(printInventory).run();
  new Thread(printRecords).run();
  new Thread(printInventory).run();
  System.out.println("end");
}
```

Unlike the previous example, each line ot this code will wait until the `run()` method 
is complete before moving on the next line. Also unlike the previous program, the output 
for this code sample wil be the same every time it is executed.

---


### Distinguishing Thread Types

It might be a surprise that all Java applications, including all of the ones that we 
have presented in this book, are multithreaded because the include system threads. A 
_system thread_ is created by the JVM and runs in the background of the application. 
For example, garbage collection is managed by a system thread created by the JVM.

Alternatively, a _user defined thread_ is on created by the application developer to 
accomplish a specific task. The majority of the programs we've presented so far have 
contained only on user-defined thread, which calls the `main()` method. For simplicity, 
we commonly refer to programs that contain only a single user-defined thread as 
_single-threaded applications_.

System and user-defined threads can both be created as daemon threads. A _daemon thread_ 
is on that will no prevent the JVM from exiting when the program finishes. A Java application 
terminates when the only threads that are running are daemon threads. For example, if 
garbage collection is the only thread left running, the JVM will automatically shut down.

Let's take a look at an example. What this program outputs?
```
01: public class Zoo {
02:   public static void pause() {     // defines a thread task
03:     try {
04:       Thread.sleep(10_000);       // wait for 10 seconds
05:     } catch (InterruptedException e) {}
06:     System.out.println("Thread finished");
07:   }
08:   
09:   public static void main(String[] args) {
10:     var job = new Thread( () -> pause() );     // create tread
11:   
12:     job.start();      // start thread
13:     System.out.println("Main method finished");
14:   }
15: }
```

The program will output two statements roughly 10 seconds apart:
```
Main method finished!
--- 10 second wait ---
Thread finished!
```

That's right. Even though the `main()` method is done, the JVM will wait for the 
user thread to be done before ending the program. What if we change `job` to be a 
daemon thread by adding this to line 11?
```
11:     job.setDaemon(true);
```

The program will print the first statement and terminate without ever printing the 
second line.
```
Main method finished
```

We just need to remember that by default, user-defined threads are not daemons, and 
the program will wait for them to finish.


### Managing a Thread's Life Cycle

After a thread has been created, it is in one of six states, shown in Figure 13.2. 
We can query a thread's state by calling `getState()` on the thread object.

![thread states](thread_states.png)

**Figure 13.2: Thread states**

Every thread is initialized with a `NEW` state. As soon as `start()` is called, 
the thread is moved to a `RUNNABLE` state. Does that mean it is actually running? Not 
exactly: it may be running, or it may no be. The `RUNNABLE` state just means the thread 
is able to be run. Once the work for the thread is completed or an uncaught exception 
is thrown, the thread state becomes `TERMINATED`, and no more work is performed.

While in a `RUNNABLE` state, the thread may transition to one of three states where it 
pauses its work: `BLOCKED`, `WAITING`, or `TIMED_WAITING`. This figure includes common 
transition between thread states, but there are other possibilities. For example, a 
thread that is interrupted by another thread will exit `TIMED_WAITING` and go straight 
back into `RUNNABLE`.

We cover some (but not all) of these transition in this chapter, Some thread-related 
methods -- such as `wait()`, `notify()`, and `join()` -- are beyond the scope of this 
book and are difficult to use. We should avoid them and use the Concurrency API as much 
as possible. It takes a large amount of skill (and some luck!) to use these methods.


### Polling with Sleep

Even though multithreaded programming allows us to execute multiple tasks at the 
same time, oe thread often needs to wait for the result of another thread to proceed. One 
solution is to use polling. _Polling_ is the the process of intermittently checking data 
at some fixed interval.

Let's say we have a thread that modifies a shared static counter value, and our `main()` 
thread is waiting for the thread to reach 1 million:
```
public class CheckResults {
  private static int counter = 0;
  public static void main(String[] args) {
    new Thread( () -> {
      for (int i = 0; i < 1_000_000; I++) counter++;
    }).start();
    while (counter < 1_000_000) {
      System.out.println("Not reached yet");
    }
    System.out.println("Reached: " + counter);
  }
}
```

How many times does this program print "Not reached yet"? The answer is, we don't 
know! It could output 0, 10, or a million times. Using a `while()` loop to check for 
data without some kind of delay is considered a bad coding practice as it ties up CPU 
resources for no reason.

We can improve this result by using the `Thread.sleep()` method to implement polling 
and sleep for 1000 milliseconds, aks 1 second:
```
public class CheckResultsWithSleep{
  private static int counter = 0;
  public static void main(String[] args) {
    new Thread( () -> {
      for (int i = 0; i < 1_000_000; i++) counter++;
    }).start();
    while (counter < 1_000_000) {
      System.out.println("Not reached yet");
      try {
        Thread.sleep(1_000);     // 1 second
      } catch (InterruptedExecution e) {
        System.out.println("Interrupted");
      }
    }
    System.out.println("Reached: " + counter);
  }
}
```

While one second may seem like a small amount, we have now freed the CPU to do other 
work instead of checking the counter variable infinitely within a loop. Notice that the 
`main()` tread alternated between `TIMED_WAITING` and `RUNNABLE` when `sleep()` is 
entered and exited, respectively.

How many times does the `while()` loop execute in this revised class? Still unknown! 
While polling does prevent the CPU from being overwhelmed with a potentially infinite 
loop, id does not guarantee when the loop will terminate. For example, the separate thread 
could be losing CPU time to a higher-priority process, result in multiple executions 
of the `while()` loop before it finishes.

Another issue to be concerned about is the shared `counter` variable. What if one thread 
is reading the `counter` variable while another thread is writing it? The thread reading 
the shared variable may end up with an invalid or unexpected value. We discuss these 
issues in detail in the upcoming section on writing thread-safe code.


### Interrupting a Thread

While our previous solution prevented the CPU from waiting endlessly on a `while()` loop, 
it did come at the cost of inserting one-second delays into our program. If the task takes 
2.1 seconds to run, the program will use full 3 seconds, wasting 0.9 seconds. 

One way to improve this program is to allow the thread to interrupt the `main()` thread 
when it's done:
```
public class CheckResultsWithSleepAndInterrupt {
  private static int counter = 0;
  public static void main(String[] args) {
    final var mainThread = Thread.currentTread();
    new Thread( () -> {
      for (int i = 0; i < 1_000_000; i++) counter++;
      mainThread.interrupt();     // new
    }).start();
    while (counter < 1_000_000) {
      System.out.println("Not reached yet");
      try {
        Thread.sleep(1_000);     // 1 second
      } catch (InterruptedExecution e) {
        System.out.println("Interrupted");
      }
    }
    System.out.println("Reached: " + counter);
  }
}
```

This improved version includes both `sleep()`, to avoid tying up the CPU, and 
`interrupt()`, so the thread's work ends without delaying the program. As before, 
our `main()` thread's state alternates between `TIMED_WAITING` and `RUNNABLE`. Calling 
`interrupt()`on a thread in the `TIMED_WAITING` or `WAITING` state causes the `main()` 
thread to become `RUNNABLE` again, triggering an `InterruptedException`. The thread may 
also move to a `BLOCKED` state if it needs to reacquire resources when it wakes up.

Calling `interrupt()` on a thread already in a `RUNNABLE` state doesn't change the 
state. In fact, it only changes the behavior if the thread is periodically checking 
the `Thread.isInterrupted()` value state.

[back to top](#chapter-13-concurrency)


## Creating Threads with the Concurrency API

Java includes the `java.util.concurrent` package, which we refer to as the 
Concurrency API, to handle the complicated work of managing threads for us. The 
Concurrency API includes the `ExecutionService` interface, which defines that create 
and manage threads.

We first obtain an instance of an `ExecutionService` interface, and then we send the 
service tasks to be processed. The framework includes numerous useful features, such 
as thread pooling and scheduling. It is recommended that we use this framework any 
time when we need to create and execute a separate task, even if we need only a 
single thread.

When writing multithreaded programs, it is often better to use the Concurrency API 
(or some other multithreaded SDK) rather than work with `Thread` objects directly. The 
libraries are much more robust, and it is easier to handle complex interactions.


### Introducing the Single-Thread Executor

Since `ExecutorService` is an interface, how do we can obtain an instance of it? 
The Concurrency API includes the `Executors` factory class that can be used to create 
instances of the `ExecutionService` object. Let's rewrite out earlier example with the 
two `Runnable` instances to using an `ExecutorService`.
```
{
  ExecutorService service = Executors.newSingleThreadExecutor();
  try {
    System.out.println("begin");
    service.execute(printInventory);
    service.execute(printRecords);
    service.execute(printInventory);
    System.out.println("end");
  } finally {
    service.shutdown();    // extremely necessary
  }
}
```

In this example, we use the `newSingleThreadExecutor()` method to create the service. 
Unlike our earlier example, in which we had four threads (one `main()` and three new 
thread), we have only two threads (one `main()` and one new thread). This means that 
the output, while still unpredictable, will have less variation than before. For example, 
the following is one possible output:
```
begin
Printing zoo inventory
Printing record: 0
Printing record: 1
end
Printing record: 2
Printing inventory
```

Noting that the `printRecords` loop is no longer interrupted by other `Runnable` tasks 
sent to the thread executor. With a single-thread executor, tasks are guaranteed to be 
executed sequentially. Notice that the `end` text is output while our thread executor 
tasks are still running. This is because the `main()` method is still an independent 
thread from the `ExecutorService`. 


### Shutting Down a Thread Executor

Once we have finished using a thread executor, it is important that we call the 
`shutdown()` method. A thread executor creates a `non-daemon` thread on the first task 
that is executed, so failing to call `shutdown()` will result in our application 
_never terminating_.

The shutdown process for a thread executor involves first rejecting any new tasks 
submitted to the thread executor while continuing to execute any previously submitted 
tasks. During this time, calling `isShutdown()` will return `true`, while `isTerminated()` 
will return `false`. If a new task is submitted to the thread executor while it is 
shutting down, a `RejectedExecutionException` will be thrown. Once all active task have 
been completed, `isShutdown()` and `isTerminated()` will both return `true`. Figure 13.3 
shows the life cycle of an `ExecutorService` object.

![ExecutorService life cycle](executor_life_cycle.png)

**Figure 13.3: ExecutorService life cycle**

We should be aware that `shutdown()` does not stop any tasks that have already 
been submitted to the thread executor. If we want to cancel all running and upcoming 
tasks, we have to call `shutdownNow()`, which _attempts to stop_ all running tasks and 
discards any that have not been started yet. It is not guaranteed to succeed because 
it is possible to create a thread that will never terminate, so any attempt to 
interrupt it may be ignored.


### Submitting Tasks

We can submit tasks to an `ExecutorService` instance multiple ways. The first method 
we were presented, `execute()`, is inherited from the `Executor` interface, which the 
`ExecutorService` interface extends. The `execute()` method takes a `Runnable` instance 
and completes the task asynchronously. Because the return type of the method is void, 
it does not tell us anything about the result of the task. It is considered a "fire and 
forget" method, as once it is submitted, the result are not directly available to the 
calling thread.

Fortunately, the writers of Java added `submit()` methods to the `ExecuteService` 
interface, which like `execute()`, can be used to complete tasks asynchronously. Unlike 
`execute()`, though, `submit()` return a `Future` instance that can be used to determine 
whether the task is complete. It can also be used to return a generic result object 
after the task has been completed.

Table 13.1 show the five methods including `execute()` and two `submit()` methods, 
that we need to known most. We will discuss `Future` and `Callable` in the next section.

![executor service methods](executor_methods.png)

**Table 13.1: ExecutorService methods**

In practice, using the `submit()` method is quite similar to using the `execute()`, 
except that the `submit()` return a `Future` instance that can be used to determine 
whether the task has completed execution.

---
**Submitting Tasks: _execute()_ vs _submit()_**

It might be clear that `execute()` and `submit()` methods are nearly identical when 
applied to `Runnable` expressions. The `submit()` method has the obvious advantage of 
doing the same thing `execute()` does, but with a return object that can be used to 
track the result. Because of this advantage and the fact that `execute()` does not 
support `Callable` expressions, we tend to prefer `submit()` over `execute()`, even 
if we don't store the `Future` reference.

Besides this, we need to be familiar with both `execute()` and `submit()`, since it 
may appear in many situations.

---


### Waiting for Results

How do we know when a task submitted to an `ExecutorService` is complete? As mentioned 
in the previous sections, the `submit()` method returns a `Future<V>` instance that can 
be used to determine this result.
```
Future<?> future = service.submit( () -> { System.out.println("Hello") } );
```

The `Future` type is actually an interface. For the exam, we don't need to know any 
of the classes that implement `Future`, just that a `Future` instance is returned by 
various API methods. Table 13.2 includes useful methods for determining the state 
of a task.

**Table 13.2: Future methods**

![future method](future_methods.png)

The following is an updated version of out earlier polling example `CheckResults` class, 
which uses a `Future` instance to wait for the results.
```
import java.util.concurrent.*;

public class CheckResults {
  private static int counter = 0;
  public static void main(String[] args) throws Exception {
    ExecutorService service = Executors.newSingleThreadExecutor();
    try {
      Future<?> result = service.submit( () -> {
        for (int i =0; i < 1_000_000; i++) counter++;
      });
      result.get(10, TimeUnit.SECONDS);     // returns null for Runnable
      System.out.println("Reached!");
    } catch (TimeoutException e) {
      System.out.println("Not reached in time");
    } finally {
      service.shutdown();
    }
  }
}
```

This example is similar to ut earlier polling implementation, but is does not use 
the `Tread` class directly. In part, this is the essence of the Concurrency API: to 
do complex things with threads without having to manage threads directly. It also 
waits at mos 10 seconds, throwing a `TimeoutException` on the call to `result.get()` 
if the task is not done.

What is the return value of this task? As `Future<V>` is a generic interface, the type 
`V` is determined by the return type of the `Runnable` method. Since the return type 
of `Runnable.run()` is void, the `get()` method always return null when working with 
`Runnable` expressions.

The `Future.get()` method can take an options value and enum type `TimeUnit` imported 
from the package `java.util.concurrent`. Table 13.3 presents the full list of `TimeUnit` 
values since numerous methods in the Concurrency API use this enum.

**Table 13.3: TimeUnit values**

![time unit values](timeunit_values.png)


### Introducing Callable

The `java.util.concurrent.Callable` functional interface is similar to `Runnable` 
except that its `call()` method return a value and can throw a checked exception. 
The following is the definition of the `Callable` interface:
```
@FunctionalInterface
public interface Callable<V> {
  V call() throws Exception;
}
```

The `Callable` interface is often preferable over `Runnable`, since it allows more 
details to be retrieved easily from the task after it is completed. That said, we 
use both interfaces throughout this chapter, as they are interchangeable in situations 
where the lambda does not throw an exception, and there is no return type. Luckily, 
the `ExecutorService` includes an overloaded version of the `submit()` method that 
takes a `Callable` object and return a generic `Future<T>` instance.

Unlike `Runnable`, in which the `get()` methods always return null, the `get()` 
methods on a `Future` instance return the matching generic type (which could also 
be a `null` value).

Let's take a look at an example using `Callable`:
```
var service = Executor.newSingleThreadExecutor();
try {
  Future<Integer> result = service.submit( () -> 30 + 11 );
  System.out.println(result.get());    // 41
} finally {
  service.shutdown();
}

```

We could rewrite this example using `Runnable`, some shared object, and an `interrupt()` 
or timed wait, but this implementation is a lot easier to code and understand. In essence, 
that's the spirit of the Concurrency API, give us the tools to write multithreaded code 
that is thread-safe, performant, and easy to follow.


### Waiting for All Tasks to Finish

After submitting a set of tasks to a thread executor, it is common to wait for the 
results. As we saw in the previous sections, one solution is to call `get()` on each 
`Future` object returned by the `submit()` methods. If we don't need the results of 
the tasks and are finished using our thread executor, there is a simpler approach.

First, we shut down the thread executor using the `shutdown()` method. Next, we use 
the `awaitTermination()` method available for all thread executors. The method waits 
the specified time to complete all tasks, returning sooner if all tasks finish or an 
`InterruptedExecution` is detected. We can see an example of this in the following 
code snippet:
```
ExecutionService service = Executors.newSingleThreadExecutor();
try {
  // add tasks to the thread executor
} finally {
  service.shutdown();
}
service.awaitTermination(1, TimeUnit.MINUTES);

// check whether all tasks are finished
if (service.isTerminated()) System.out.println("All finished!");
else System.out.println("At least one task is still running");
```

In this example, we submit a number of tasks to the thread executor and then shut 
down the thread executor and wait up to one minute for the results. Notice that we 
can call `isTerminated()` after the `awaitTermination()` method finishes to confirm 
that all tasks are finished.


### Scheduling Tasks

Often in Java, we need to schedule a task to happen at some future time. We might 
even need to schedule the task to happen repeatedly, at some set interval. For example, 
imagine that we want to check the supply of food for zoo animal once an hour and fill 
it as needed. `ScheduledExecutorService`, which is a sub-interface of `ExecutorService`, 
can be used for just such as task.

Like `ExecutorService`, we obtain an instance of `ScheduledExecutorService` using a 
factory method in the `Executors` class, as shown in the following snippet:
```
ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
```

We could store an instance of `ScheduledExecutorService` in an `ExecutorService` variable, 
although doing so would mean we'd have to cast the object to call any scheduling methods.

Refer to Table 13.4 for our summary of `ScheduledExecutorService` methods. Each of these 
methods return a `ScheduledFuture` object.

**Table 13.4: ScheduledExecutorService methods**

![scheduled executor service methods](scheduled_executor_service_methods.png)


In practice, these methods are among the most convenient in the Concurrency API, 
as they perform relatively complex tasks with a single line of code. The `delay` and 
`period` parameters rely on the `TimeUnit` argument to determine the format of the 
value, such as seconds or milliseconds.

The first two `schedule()` methods in Table 13.4 take a `Callable` or `Runnable`, 
respectively to perform a task after some delay and return a `ScheduleFuture` instance. 
The `ScheduledFuture` instance is identical to the `Future` interface, except that it 
includes a `getDelay()` method that returns the remaining delay. The following uses the 
`schedule()` methods with `Callable` and `Runnable` tasks:
```
ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

Runnable task1 = () -> System.out.println("Hello zoo");
Callable<String> task2 = () -> "Monkey";

ScheduledFuture<?> r1 = service.schedule(task1, 10, TimeUnit.SECONDS);
ScheduledFuture<?> r2 = service.schedule(task2, 8, TimeUnit.MINUTES);
```

The first task is schedule 10 seconds in the future, whereas the second task is 
scheduled 8 minutes in the future. While these tasks are scheduled in the future, 
the actual execution may be delayed. For example, there may be no threads available 
to perform the tasks, at which point they will just wait in the queue. Also, if the 
`ScheduledExecutorService` is shut down by the time the scheduled task execution 
time is reached, then these tasks will be discarded.

Each of the `ScheduledExecutorService` methods is important and has real-world 
applications. For example, we can use the `schedule()` command to check on the state 
of cleaning a lion's cage. It can then send out notifications if it is not finished 
or even call `schedule()` to check again later.

The last two methods in Table 13.4 might be a little confusing, conceptually, they 
area similar as they both perform the same task repeatedly after an initial delay. The 
difference is related to the timing of the process and when the next task starts.

The `scheduleAtFixedRate()` method created a new task an submits it to the executor 
every period, regardless of whether the previous task finished. The following example 
executes a `Runnable` task every minute, following an initial five-minute delay:
```
service.scheduleAtFixedRate(command, 5, 1, TimeUnit.MINUTES);
```

the `scheduleAtFixedRate()` method is useful for tasks that need to be run at specific 
intervals, such as checking the health of the animals once a day. Even if it takes two 
hours to examine an animal on Monday, this doesn't mean that Tuesday's exam should 
start any later in the day.

Bad things can happen with `scheduleAtFixedRate()` if each task consistently takes 
longer to run than the execution interval. Imagine if our boss came by our desk every 
minute and dropped of a piece of paper. Now imagine that it took us five minutes to read 
each piece of paper. Before long, we would be drowning in piles of paper. This is how 
an executor feels. Given enough time, the program would submit more tasks to the 
executor service than could fit in memory, causing the program to crash.

On the other hand, the `scheduleWithFixedDelay()` method creates a new task only after 
the previous task has finished. For example, if a task runs as 12:00 and takes five 
minutes to finish, with a period between executions of two minutes, the nest task will 
start as 12:07.
```
service.scheduleWithFixedDelay(task1, 0, 2, TimeUnit.MINUTES);
```

The `scheduleWithFixedDelay()` method is useful for processes that we want to happen 
repeatedly but whose specific time in unimportant. For example, imagine that we have 
a soo cafeteria worker who periodically restocks the salad bar through the day. The 
process can take 20 minutes or more, since i requires the worker to haul a large number 
of items from the back room. Once the worker has filled the salad bar with fresh food, 
they don't need to check at some specific time, just after enough time has passed for 
it to become low on stock again.


### Increasing Concurrency with Pods

All of our examples up until now have been with a single-thread executor, which, 
while interesting, weren't particularly useful. We now present three additional factory 
methods in the `Executors` class that act on a pool of threads rather than on a single 
thread. A _thread pool_ is a group of pre-instantiated reusable threads that are 
available to perform a set of arbitrary tasks. Table 13.5 show them.

**Table 13.5: Executors factory methods**

![executors factory methods](executors_factory_methods.png)

As shown in Table 13.5, these methods return the same instance types, `ExecutorService` 
and `ScheduledExecutorService`, that we used earlier in this chapter. In other words, all 
of our previous examples are compatible with these new pooled-thread executors.

The difference between a single-treaded and a pooled-threaded executor is what happens 
when a task is already running. While a single-thread executor will wait for the thread 
to become available before running the next task, a pooled-thread executor can execute 
the next task concurrently. If the pol runs out of available threads, the task will be 
queued by the thread executor and wait to be completed.

[back to top](#chapter-13-concurrency)


## Writing Thread-Safe Code

_Thread-safety_ is the property of an object that guarantees safe execution by multiple 
threads at the same time. Since threads run in a shared environment and memory space, 
how do we prevent two threads from interfering with each other? We must organize access 
to data so that we don't end up with invalid or unexpected results.

In this part of the chapter, we will see how to use a variety of techniques to protect 
data, including atomic classes, synchronized blocks, the Lock Framework, and cyclic barriers.


### Understanding Thread-Safety

Imagine that our zoo has a program to count sheep, preferable on that won't pub the 
zoo worker to sleep!. Each zoo worker urns out to a field, adds a new sheep to the flock, 
count the total number of sheep, and runs back to us to report the results. We present 
the following code to represent this conceptually, choosing a thread pool size so that 
all tasks can be run concurrently:
```
01: import java.util.concurrent.*;
02: 
03: public class SheepManager {
04:   private int sheepCount = 0;
05:   private void incrementAndReport() {
06:     System.out.print( (++sheepCount) + " ");
07:   }
08:   public static void main(String[] args) {
09:     ExecutorService service = Executors.newFixedThreadPool(20);
10:     try {
11:       SheepManager manager = new SheepManager();
12:       for (int i = 0; i < 10; i++)
13:         service.submit( () -> manager.incrementAndReport() );
14:     } finally {
15:       service.shutdown();
16:     }
17:   }  // end main
18: }  // end class
```

What does this program output? We might thing it will output numbers from 1 to 10, 
in order, bu that is far from guaranteed. It may output in a different order. Worse 
yet, it may print some number twice and no print some number all all!. The following 
are possible output of this program:
```
12345678910
1987366245
1873265429
```

So, what went wrong? In this example, we use the pre-increment (++) operator to 
update the `sheepCount` variable. A problem occurs when two threads both execute 
the right side of the expression, reading the "old" value before either thread 
writes the "new" value of the variable. The two assignments become redundant, they 
both assign the same new value, with one thread overwriting the results of the 
other. Figure 13.4 demonstrates this problem with two threads, assuming that 
`sheepCount` has a starting value of 1.

![lack thread synchronization](lack_thread_synchronization.png)

**Figure 13.4: Lack of thread synchronization**

We can see in Figure 13.4 that both threads read and write the same values, causing 
one of the two `++sheepCount` operations to be lost. Therefore, the increment operator 
`++` is not thread-safe. As we will see later in this chapter, the unexpected result of 
two tasks executing an the same time is referred to as a _race condition_.

Conceptually, the idea here is that some zoo worker may run faster on their way to 
the field but more slowly on their way back and report late. Other worker may get to 
the field last but somehow be the first ones back to report the results.


### Accessing Data with _volatile_

The `volatile` keyword is used to guarantee that access to data within memory is 
consistent. For example, it is possible (albeit unlikely) that our `SheepManager` 
example using `++sheepCount` return an unexpected value due to invalid memory access 
while the code is executing a critical section. Conceptually, this corresponds to 
one of our zoo employees tripping on the way back from the field and someone asking 
them the current number of sheep while they are still trying to get up!

The `volatile` attribute ensures that only on thread is modifying a variable at 
one time and that data read among multiple threads is consistent. In this manner, we 
don't interrupt one of our zoo workers in the middle of running. So, does `volatile` 
provide thread-safety? Not exactly. Consider this replacement to our previous 
application:
```
04: private volatile int sheepCount = 0;
05: private void incrementAndReport() {
06:   System.out.print( (++sheepCount) + " "); 
07: }
```

Unfortunately, this code is not thread-safe and could still result in numbers 
been missed:
```
2617532948
```

The reason this code is not thread-safe is that `++sheepCount` is still two distinct 
operations. Put another way, if the increment operator represents the expression 
`sheepCount = sheepCount + 1`, then each read and write operation is thread-safe, but 
the combined operation is not. Referring back to out sheep example, we don't interrupt 
the employee while running, but we could still have multiple in the field at the same 
time. Because of this, **in practice, `volatile` is rarely used**.


### Protecting Data with Atomic Classes

In our previous `SheepManager` applications, the same values were printed twice, 
with the highest counter being 9 instead of 10. As we saw, the increment operator 
`++` is not thread-safe, even when `volatile' is used. It is not thread-safe because 
the operation is not atomic, carrying out two tasks, read and write, that can be 
interrupted by other threads.

_Atomic_ is the property of an operation to be carried out as a single unit of 
execution without any interference from another thread. A thread-safe atomic version 
of the increment operator would perform the read and write of the variable as a single 
operation, not allowing any other threads to access the variable during the operation. 
Figure 13.5 shows the result of making the `sheepCount` variable atomic.

![thread sync using atomic](thread_synchronization_atomic_operations.png)

**Figure 13.5: Thread synchronization using atomic operations**

In this case, any thread trying to access the `sheepCount` variable while an atomic 
operation is in process will have to wait until the atomic operation on the variable 
is complete. Conceptually, this is like setting a rule four our zoo worker that there 
can be only one employee in the field at a time. Although they may not each report 
their result in order.

Since accessing primitives and references is common in Java, the Concurrency API 
includes numerous useful classes in the `java.util.concurrent.atomic` package. Table 
13.6 list the most common class that we need to be familiar. As with many of the 
classes in the Concurrency API< these classes exist to make our life easier.

**Table 13.6: Atomic classes**

![atomic classes](atomic_classes.png)

How dow we use atomic classes? Each class includes methods that are equivalent 
to many of the primitive built-in operations that we use on primitives, such as the 
assignment operator (=) and the increment operators (++). In Table 13.7 the most 
common methods from atomic classes are show. The `type` is determined by the class.

In the following example, an updated version of out `SheepManager` class with an 
`AtomicInteger` 
```
import java.util.concurrent.atomic.*;

private AtomicInteger sheepCount = new AtomicInteger(0);
private void incrementAndReport() {
  System.out.println(sheepCount.incrementAndGet() + " ");
}
```

**Table 13.7: Common atomic methods**

![common atomic methods](atomic_methods.png)

How does this implementation differ from out previous examples? When we runt this 
modification, we get varying output, such as the following:
```
2 3 1 4 5 6 7 8 9 10
1 4 3 2 5 6 7 8 9 10
1 4 3 5 6 2 7 8 10 9
```

Unlike out previous sample output, the number 1 through 10 will alway be printed, 
although the order is still not guaranteed (this will be address shortly). The key 
in this section is that using the atomic classes ensures that the data is consistent 
between workers and that no values are lost due to concurrent modifications.


### Improving Access with _synchronized_ Blocks

While atomic classes are great at protecting a single variable, they aren't particularly 
useful if we need to execute a series of commands or call a method. For example, we can't 
use them to update two atomic variables at the same time. How do we improve the results 
so that each worker is able to increment and report the results in order?

The mos common technique is to use a monitor to synchronize access. A _monitor_, also 
called a _lock_, is a structure that support _mutual exclusion_, which is the property 
that a most on thread is executing a particular segment of code at a given time.

In Java, any `Object` can be used as a monitor, along with the `synchronized` keyword, 
as shown in the following example:
```
var manager = new SheepManager();
synchronized(manager) {
  // work to be completed by on thread at a time
}
```

this example is referred to as a _synchronized block_. Each thread that arrives will 
first check if any threads are already running the block. If the lock is not available, 
the thread will transition to a `BLOCKED` state until it can "acquire the lock". If the 
lock is available (or the thread already holds the lock), the single thread will enter 
the block, preventing all other threads from entering. Once the thread finishes executing 
th block, it will release the lock, allowing on of the waiting thread to proceed.

---
To synchronize access across multiple threads, each thread must have access to the _same_ 
object. If each thread synchronizes on different objects, the code is not thread-safe.
---

Let's revisit our `SheepManager` example that used `++sheepCount` and see whether we can 
improve the results so that each worker increments and outputs the counter in order. Let's 
say that we replaced our `for()` loop with the following implementation:
```
12:   for (int i = 0; i < 10; i++) {
13:     synchronized(manager) {
14:       service.submit( () -> manager.incrementAndReport() );  
15:     }
16:   }
```

Dos this solution fix the problem? No, it does not! The problem here is that we've 
synchronized the _creation_ of the threads but not the _execution_ of the threads. In 
this example, the threads would be created one at a time, but the might all still execute 
and perform their work simultaneously, result in the same type of output that we saw 
earlier. We did say diagnosing and resolving thread problems is difficult in practice!

Now a corrected version of the `SheepManager` class that orders the workers.
```
01: import java.util.concurrent.*;
02: 
03: public class SheepManager {
04:   private int sheepCount = 0;
05:   private void incrementAndReport() {
06:     synchronized(this) {
07:       System.out.print( (++sheepCount) + " ");
08:     }
09:   }
10:   public static void main(String[] args) {
11:     ExecutorService service = Executors.newFixedThreadPool(20);
12:     try {
13:       var manager = new SheepManager();
14:       for (int i = 0; i < 10; i++)
15:         service.submit( () -> manager.incrementAndReport() );
16:     } finally {
17:       service.shutdown();
18:     }
19:   }
20: }
```

When this code executes, it will consistently output the following:
```
1 2 3 4 5 6 7 8 9 10
```

Although all threads are still created and executed at the same time, they each 
wait at the synchronized block for the worker to increment and report the result before 
entering. In this manner, each zoo worker waits for the previous zoo worker to come 
back before running out on the field. While it's random which zoo worker will run out 
next, it is guaranteed that there will be at most one on the field and that the result 
will be reported in order.

We could have synchronized on any object, as long as it was the same object. For 
example, the following code snipped would also work:
```
05:  private final Object herd = new Object();
06:  private void incrementAndReport() {
07:    synchronized(herd) {
08:      System.out.print( (++sheepCount) + "");
09:    }
10:  }
```

Although we didn't need to make the `heard` variable `final`, doing so ensures 
that it is not reassigned after threads start using it.


### Synchronizing on Methods

In the previous example, we established out monitor using `synchronized(this)` around 
the body of the methods. Java provided a more convenient compiler enhancement for doing 
so. We can add the `synchronized` modifier to any instance method to synchronize 
automatically on the object itself. For example, the following two method definitions 
are equivalent:
```
void sing(){
  synchronized(this) {
    System.out.print("La la la...");
  }
}

synchronized void sing() {
  System.out.print("La la la...")
}
```

The first uses a synchronized block, whereas the second uses the synchronized method 
modifier. Which to use is completely up to us. We can also apply the `synchronized` 
modifier to static methods. What object is used as the monitor when we synchronize on 
a static method? The class object, of course. For example, the following two methods 
are equivalent for static synchronization inside out `SheepManager` class:
```
static void dance() {
  synchronized(SheepManager.class) {
    System.out.print("Let's go dance")
  }
}

static synchronized void dance() {
  System.out.print("Let's go dance")
}
```

As before, the first uses a synchronized block, with the second example using the 
synchronized modifier. We can use static synchronization if we need to order thread 
access across all instances rather than a single instance.


### Understanding the _Lock Framework_

A synchronized block supports only a limited set of functionality. For example, 
what if we want to check whether a lock is available and, if it is not, perform some 
other task? Furthermore, if the lock is never available and we synchronize on it, we 
might wait forever.

The Concurrency API includes the `Lock` interface, which is conceptually similar to 
using the `synchronized` keyword but with a lot more bells and whistles. Instead of 
synchronizing on any `Object`, though, we can "lock" only on an object that implements
the `Lock` interface.

#### Applying a _ReentrantLock_

The `Lock` interface is pretty easy to use. When we need to protect a piece of code 
from multithreaded processing, create an instance of `Lock` that all threads have 
access to. Each thread then calls `lock()` before it enters the protected code and 
calls `unlock()` before it exits the protected code.

For contrast, the following shows two implementations, on with a synchronized block 
and one with a `Lock` instance. While longer, the `Lock` solution has a number of 
features not available to the synchronized block.
```
// implementation #1 with a synchronized block
Object object = new Object();
synchronized(object) {
  // protected code
}

// implementation #2 with a Lock
Lock lock = new ReentrantLock();
try {
  lock.lock();
  // protected code
} finally {
  lock.unlock();
}
```

these two implementations are conceptually equivalent. The `ReentrantLock` class is 
a simple monitor that implements the `Lock` interface and supports mutual exclusion. 
In other words, at most on thread is allowed to hold a lock at any given time.

---
While certainly not required, it is a good practice to use a `try/finally` block 
with `Lock` instances. Doing so ensures that any acquired lock is properly released.
---

The `ReentrantLock` class ensures that once a thread has called `lock()` and obtained 
the lock, all other threads that call `lock()` will wait until the first thread calls 
`unlock()`. Which thread gets the lock next depends on the parameters used to create 
the `Lock` object.

The `ReentrantLock` class includes a constructor that takes a single boolean and sets 
a "fairness" parameter. If the parameter is set to `true`, the lock will usually be 
granted to each thread in the order in which it was request. It is `false` by default 
when using the no-argument constructor. In practice, we should enable fairness only 
when ordering is absolutely required, as it could lead to a significant slowdown.

Besides always making sure to release a lock, we also need to be sure that we only 
release a lock that we have. If we attempt to release a lock that we don not have, 
we will get an exception at runtime.
```
Lock lock = new ReentrantLock();
lock.unlock();                    // IllegalMonitorStateException
```

The `Lock` interface includes four methods we should know for every day life, as 
listed in Table 13.8

**Table 13.8: Lock methods**

![lock methods](lock_methods.png)


#### Attempting to Acquire a Lock

While the `ReentrantLock` class allows us to wait for a lock, it so far suffers 
from the same problem as a synchronized block. A thread could end up waiting forever 
to obtain a lock. Luckily, Table 13.8 includes two additional methods that made the 
`Lock` interface a lot safe to use than a synchronized block.

For convenience, we will use the following `printHello()` method for the code in this 
section:
```
public static void printHello(Lock lock) {
  try {
    lock.lock();
    System.out.println("Hello");
  } finally {
    lock.unlock();
  }
}
```

**tryLock()**

The `tryLock()` method will attempt to acquire a lock and immediately return a 
boolean result indicating whether the lock was obtained. Unlike the `lock()` method, 
it does not wait if another thread already holds the lock. It return immediately, 
regardless of whether a lock is available.

The following is a sample implementation using the `tryLock()` method:
```
Lock lock = new ReentrantLock();
new Thread( () -> printHello(lock) ).start();
if (lock.tryLock()) {
  try {
    System.out.println("Lock obtained, entering protected code");
  } finally {
    lock.unlock();
  }
} else {
  System.out.println("Unable to acquire lock, doing something else");
}
```

When we run this code, it could produce either the `if` or the `else` message, 
depending on the order of execution. It will always print "Hello", though, as the 
cal to `lock()` in `printHello()` will wait indefinitely for the lock to become 
available. A fun exercise is to insert some `Thread.sleep()` delays int this
snippet to encourage a particular message to be displayed.

Like `lock()`, the `tryLock()` method should be used with a `try/finally` block. 
Fortunately, we nee to release the lock only if it was successfully acquired. For 
this reason, it is common to use the output of `tryLock()` in an `if` statement, 
so that `unlock()` is called only when the lock is obtained.


**tryLock(long, TimeUnit)**

The `Lock` interface includes an overloaded version of `tryLock(long, TimeUnit)` 
that acts like a hybrid of `lock()` and `tryLock()`. Like the other two methods, if 
a lock is available, it will immediately return with it. If a lock is unavailable, 
though, it will wait up to the specified time limit for the lock.

The following code snippet uses the overloaded version of `tryLock(long, TimeUnit)`:
```
Lock lock = new ReentrantLock();
new Thread( () -> printHello(lock) ).start();
if (lock.tryLock(10, TimeUnit.SECONDS)) {
  try {
    System.out.println("Lock obtained, entering protected code");
  } finally {
    lock.unlock()
  }
} else {
  System.out.println("Unable to acquire lock, doing something else");
}
```

The code is the same as before, except this time, one of the threads waits up to 
10 seconds to acquire the lock.


#### Acquiring the Same Lock Twice

The `ReentrantLock` class maintains a counter of the number of times a lock has 
been successfully granted to a thread. To release the lock for other threads to use, 
`unlock()` must called the same number of times the lock was granted. The following 
code snippet contains an error:
```
Lock lock = new ReentrantLock();
if (lock.tryLock()) {
  try {
    lock.lock();
    System.out.println("Lock obtained, entering protected code");
  } finally {
    lock.unlock();
  }
}
```

The thread obtains the lock twice but releases it only once. We can verify this 
by spawning a new thread after this code runs that attempts to obtain a lock. The 
following prints false:
```
new Thread( () -> System.out.print(lock.tryLock()) ).start();    // false 
```

---
It is critical that we release a lock the same number of times it is acquired. For 
call with `tryLock()`, we need to call `unlock()` only if the method returned `true`.
---


#### Reviewing the _Lock_ Framework

To review, the `ReentrantLock` class supports the same features as a synchronized 
block while adding a number of improvements:
- Ability to request a lock without blocking.
- Ability to request a lock while blocking for a specific amount of time.
- A lock can be created with a fairness property, in which the lock is granted 
    to threads in the order in which it as requested.

---

Theres is also a more specialized `Lock` class, `ReentrantReadWriteLock` is a 
useful class. It includes separate locks for reading and writing data and is useful 
on data structures where reads are far more common that writes. For example, if we 
have a thousand threads reading data but only one thread writing data, this class 
can help us to maximize concurrent access.

---


### Orchestrating Tasks with a _CyclicBarrier_

We started the thread-safety topic by discussing protecting individual variables 
and then moved on to blocks of code and locks. We complete our discussion of thread-
safety by showing how to orchestrate complex tasks with many steps.

Our zoo workers are back, and this time they are cleaning pens. Imagine a lion pen 
that needs to be emptied, cleaned, and then refilled with the lions. To complete the 
task, we have assigned four zoo workers. Obviously, we don't want to start cleaning 
the cage while a lion is roaming in it, or we lose a zoo worker! Furthermore, we don't 
want to let the lions back into the pen while it still being cleaned.

We could have all of the work completed by a single worker, but this would be slow and 
ignore the fact that we have three zoo worker standing by to help. A better solution 
would be to have all four zoo employees work concurrently, pausing between the end of 
one set of tasks and the start of the next.

To coordinate these tasks, we can use the `CyclicBarrier` class:
```
import java.util.concurrent.*;

public class LionPenManager {
  private void removeLions() {
    System.out.println("Removing lions");
  }
  private void cleanPen() {
    System.out.println("Cleaning the pen");
  }
  private void addLions() {
    System.out.println("Adding lions");
  }
  public void performTask() {
    removeLions();
    cleanPen();
    addLions();
  }
  public static void main(String[] args) {
    var service = Executors.newFixedThreadPool(4);
    try {
      var manager = new LionPenManager();
      for (int i = 0; i < 4; i++)
        service.submit( () -> manager.performTask() );
    } finally {
      service.shutdown();
    }
  }
}
```

The following is sample output base on this implementation;
```
Removing lions
Removing lions
Cleaning the pen
Adding lions
Removing lions
Cleaning the pen
Adding lions
Removing lions
Cleaning the pen
Adding lions
Cleaning the pen
Adding lions
```

Although the results are ordered within a single thread, the output is entirely random 
among multiple workers. We see that some lions are still being removed while the cage 
is being cleaned, and other lions are added before the cleaning process is finished.

We can improve these results by using the `CyclicBarrier` class. The `CyclicBarrier` 
takes in its constructors a `limit` value, indicating the number of threads to wait 
for. As each thread finishes, it call the `await()` method on the cyclic barrier. Once 
the specified number of thread have each called `await()`, the barrier is released, 
and all threads can continue.

```
import java.util.concurrent.*;

public class LionPenManager {
  private void removeLions() {
    System.out.println("Removing lions");
  }
  private void cleanPen() {
    System.out.println("Cleaning the pen");
  }
  private void addLions() {
    System.out.println("Adding lions");
  }
  public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
    try {
      removeLions();
      c1.await();
      cleanPen();
      c2.await();
      addLions();
    } catch (InterruptedException | BrokenBarrierException e) {
      // handle checked exceptions here
    }
  }
  public static void main(String[] args) {
    var service = Executors.newFixedThreadPool(4);
    try {
      var manager = new LionPenManager();
      var c1 = new CyclicBarrier(4);
      var c2 = new CyclicBarrier(4, () -> System.out.println("*** Pen Cleaned!"));
      for (int i = 0; i < 4; i++) {
        service.submit( () -> manager.performTask(c1, c2) );
      }
    } finally {
      service.shutdown();
    }
  }
}
```

The following is sample output based on this revised implementation of our 
`LionPenManager` class:
```
Removing lions
Removing lions
Removing lions
Removing lions
Cleaning the pen
Cleaning the pen
Cleaning the pen
Cleaning the pen
*** Pen Cleaned!
Adding lions
Adding lions
Adding lions
Adding lions
```

As we can see, all of the results are now organized. Removing the lions happens 
in one step, as does cleaning the pen and adding the lions back in. In this example, 
we used two different constructors for out `CyclicBarrier` objects, the latter of 
which executes a `Runnable` instance upon completion.

The `CyclicBarrier` class allows us to perform complex, multithread tasks while 
all threads stop and wait at logical barriers. This solution is superior to a single-
threaded solution, as the individual tasks, such as removing the lions, can be 
completed in parallel by all four zoo workers.

[back to top](#chapter-13-concurrency)


## Using Concurrent Collections