# Deferred

--------
Deferred is a fork of Ray Tsang's [JDeferred](https://github.com/jdeferred/jdeferred) library inspired in [JQuery](https://github.com/jquery/jquery)

The purpose of the library is to provide an easy-to-use implementation of promised for Android applications with an architecture based on pools. In Tuenti we have an architecture where the developer is the responsible of known in which thread pool should his code be executed, in a similar way to RxJava, we distinguish between **network**, **disk**, **computation** or **UI** work, and we wanted a Promise implementation that let us work in that way easily.

--------
## How to use

### Features
* Deferred object and Promise
* Promise callbacks
  * ```.then(…)```
  * ```.done(…)```
  * ```.fail(…)```
  * ```.progress(…)```
  * ```.always(…)```
* Multiple promises
  * ```.when(p1, p2, p3, …).then(…)```
* Callable and Runnable wrappers
  * ```.when(new Runnable() {…})```
* Uses Executor Service
* Java Generics support
  * ```Deferred<Integer, Exception, Double> deferred;```
  * ```deferred.resolve(10);```
  * ```deferred.reject(new Exception());```
  * ```deferred.notify(0.80);```


#### Promises and Deferred objects

##### How to create them
```java
Deferred<Integer, Exception, Void> deferred = deferredFactory.build();

deferred.resolve(1);

Promise<Integer, Exception, Void> promise = deferred.promise();

promise.done((Done.Immediately<Integer>) i -> System.out.println(i));
```  
As you can see, `Done.Immediately<Integer>` is the type of Done callback that we want to use, for the callbacks, you can use:

* **Immediately:** To be run in the same thread where the promise has been resolved.
* **UI:** To run it on UI thread.
* **Computation:** To run it on the computation pool.
* **Disk:** To run it on disk pool.
* **Network:** To run it on network pool.

##### They are asynchronous

The promises are asynchronous, the callbacks can be set before/after the promise is finished

```java
Deferred<String, Exception, Void> deferred = deferredFactory.build();

deferred.done((Done.UI<String>) result -> System.out.println(result));

deferred.resolve("Finish!");
```

##### Filter

```java
Deferred<Integer, Void, Void> deferred = deferredFactory.build();

deferred.resolve(1);

deferred.then((Done.Filter.Computation<Integer, Integer>) i -> i * 2)
    .done((Done.UI<Integer>) i -> System.out.println(i));
```

##### Pipe
```java
Deferred<Integer, Void, Void> deferred = deferredFactory.build();

deferred.resolve(1);

deferred.then((Computation<Integer, Integer, Exception, Void>) i ->
    deferredFactory.<Integer, Exception, Void>build().reject(new Exception()))
    .done((UI<Integer>) i -> System.out.println(i))
    .fail((Fail.UI<Exception>) e -> System.out.println("Uh oh!"));
```
##### All this can be combined together
```java
Deferred<Integer, Void, Void> deferred = deferredFactory.build();

deferred.resolve(1);

deferred.then((Done.Pipe.Computation<Integer, Integer, Exception, Void>) i -> {
  Deferred<Integer, Exception, Void> positiveDeferred = deferredFactory.build();
  positiveDeferred.resolveOrReject(i > 0, i, new Exception("Noooo"));
  return positiveDeferred;
})
    .then((Done.Filter.Computation<Integer, String>) i -> "Result is " + String.valueOf(i))
    .done((Done.UI<String>) i -> System.out.println(i))
    .fail((Fail.UI<Exception>) e -> System.out.println(e.getMessage()));
}
```
##### DeferredManager:
You can use a `DeferredManager` implementation, `DefaultDeferredManager` or your custom one to combine the execution of several promises

for example:
```java
DeferredManager dm = new DefaultDeferredManager();
Promise p1, p2, p3;
// initialize p1, p2, p3
dm.when(p1, p2, p3)
  .done(…)
  .fail(…)
```

Or use `sequentiallyRunUntilFirstDone` to execute all promises until one is resolved:

```java
deferredManager.sequentiallyRunUntilFirstDone(this::getDataFromMemory,
				this::getDataFromDisk,
				this::getDataFromAPI)
```

Also, you can use `lazyAnd` or `lazyOr` to execute promises sequentially until the boolean condition is achieved.
--------

## How to add to your project

Add `compile TODO` to your _build.gradle_ file.

The promises are created via a `DeferredFactory`, to create an instance of this class you should provide an implementation of `ExecutorProvider`, this class will is where you will tell Deferred in which pool you want to execute your code, for example:

```java

import java.util.concurrent.Executor;
import com.tuenti.deferred.ExecutorProvider;

public class MyExecutorProvider implements ExecutorProvider {

	private final Executor myExecutor = new Executor() {
		@Override
		public void execute(Runnable command) {
			command.run();
		}
	};

	@Override
	public Executor getUiExecutor() {
		return myExecutor;
	}

	@Override
	public Executor getComputationExecutor() {
		return myExecutor;
	}

	@Override
	public Executor getDiskExecutor() {
		return myExecutor;
	}

	@Override
	public Executor getNetworkExecutor() {
		return myExecutor;
	}
}


```
