/*
 * Copyright 2013 Ray Tsang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuenti.deferred;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


/**
 * {@link DeferredManager} is especially useful when dealing with asynchronous
 * tasks, either {@link Runnable} or {@link Callable} objects.
 * <p>
 * It's also very useful when you need to get callbacks from multiple
 * {@link Promise} objects.
 * <p>
 * <pre>
 * <code>
 * {@link DeferredManager} dm = new {@link DefaultDeferredManager}();
 *
 * {@link Promise} p1, p2, p3;
 * // p1 = ...; p2 = ...; p3 = ...;
 * dm.when(p1, p2, p3)
 *   .done(new DoneCallback() { ... })
 *   .fail(new FailCallback() { ... })
 *   .progress(new ProgressCallback() { ... })
 * </code>
 * </pre>
 * <p>
 * When dealing with async threads:
 * <p>
 * <pre>
 * <code>
 * dm.when(new Callable() { ... }, new Callable() { ... })
 *   .done(new DoneCallback() { ... })
 *   .fail(new FailCallback() { ... })
 * </code>
 * </pre>
 *
 * @author Ray Tsang
 * @see DefaultDeferredManager
 * @see MasterDeferredObject
 */
@SuppressWarnings({"rawtypes"})
public interface DeferredManager {
	enum StartPolicy {
		/**
		 * Let Deferred Manager to determine whether to start the task at its own
		 * discretion.
		 */
		DEFAULT,

		/**
		 * Tells Deferred Manager to automatically start the task
		 */
		AUTO,

		/**
		 * Tells Deferred Manager that this task will be manually started
		 */
		MANUAL
	}

	/**
	 * Simply returns the promise.
	 *
	 * @param promise
	 * @return promise
	 */
	<D, F, P> Promise<D, F, P> when(Promise<D, F, P> promise);

	/**
	 * Wraps {@link Runnable} with {@link DeferredFutureTask}.
	 *
	 * @param runnable
	 * @return {@link #when(DeferredFutureTask)}
	 * @see #when(DeferredFutureTask)
	 */
	Promise<Void, Throwable, Void> when(Runnable runnable);

	/**
	 * Wraps {@link Callable} with {@link DeferredFutureTask}
	 *
	 * @param callable
	 * @return {@link #when(DeferredFutureTask)}
	 * @see #when(DeferredFutureTask)
	 */
	<D> Promise<D, Throwable, Void> when(Callable<D> callable);

	/**
	 * Wraps {@link Future} and waits for {@link Future#get()} to return a result
	 * in the background.
	 *
	 * @param future
	 * @return {@link #when(Callable)}
	 */
	<D> Promise<D, Throwable, Void> when(Future<D> future);

	/**
	 * Wraps {@link DeferredRunnable} with {@link DeferredFutureTask}
	 *
	 * @param runnable
	 * @return {@link #when(DeferredFutureTask)}
	 * @see #when(DeferredFutureTask)
	 */
	<P> Promise<Void, Throwable, P> when(
			DeferredRunnable<P> runnable);

	/**
	 * Wraps {@link DeferredCallable} with {@link DeferredFutureTask}
	 *
	 * @param callable
	 * @return {@link #when(DeferredFutureTask)}
	 * @see #when(DeferredFutureTask)
	 */
	<D, P> Promise<D, Throwable, P> when(
			DeferredCallable<D, P> callable);

	/**
	 * May or may not submit {@link DeferredFutureTask} for execution. See
	 * implementation documentation.
	 *
	 * @param task
	 * @return {@link DeferredFutureTask#promise()}
	 */
	<D, P> Promise<D, Throwable, P> when(
			DeferredFutureTask<D, P> task);

	/**
	 * This will return a special Promise called {@link MasterDeferredObject}. In
	 * short,
	 * <ul>
	 * <li>{@link Promise#done(DoneCallback)} will be triggered if all promises
	 * resolves (i.e., all finished successfully).</li>
	 * <li>{@link Promise#fail(FailCallback)} will be triggered if any promises
	 * rejects (i.e., if any one failed).</li>
	 * <li>{@link Promise#progress(ProgressCallback)} will be triggered whenever
	 * one promise resolves or rejects, or whenever a promise was notified
	 * progress.</li>
	 * <li>{@link Promise#always(AlwaysCallback)} will be triggered whenever
	 * {@link Promise#done(DoneCallback)} or {@link Promise#fail(FailCallback)}
	 * would be triggered</li>
	 * </ul>
	 *
	 * @param promises
	 * @return {@link MasterDeferredObject}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			Promise... promises);

	/**
	 * Wraps {@link Runnable} with {@link DeferredFutureTask}
	 *
	 * @param runnables
	 * @return {@link #when(DeferredFutureTask...)}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			Runnable... runnables);

	/**
	 * Wraps {@link Callable} with {@link DeferredFutureTask}
	 *
	 * @param callables
	 * @return {@link #when(DeferredFutureTask...)}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			Callable<?>... callables);

	/**
	 * Wraps {@link DeferredRunnable} with {@link DeferredFutureTask}
	 *
	 * @param runnables
	 * @return {@link #when(DeferredFutureTask...)}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			DeferredRunnable<?>... runnables);

	/**
	 * Wraps {@link DeferredCallable} with {@link DeferredFutureTask}
	 *
	 * @param callables
	 * @return {@link #when(DeferredFutureTask...)}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			DeferredCallable<?, ?>... callables);

	/**
	 * May or may not submit {@link DeferredFutureTask} for execution. See
	 * implementation documentation.
	 *
	 * @param tasks
	 * @return {@link #when(Promise...)}
	 */
	Promise<MultipleResults, OneReject, MasterProgress> when(
			DeferredFutureTask<?, ?>... tasks);

	Promise<MultipleResults, OneReject, MasterProgress> when(
			Future<?>... futures);

	/**
	 * Executes the array of DeferredObjectTasks secuentially until one has success.
	 * A deferredObjectTasks succeeds if his onDone promise callback is called and his result
	 * filtered with DeferredObjectTask.Filter is true.
	 * If no one success or the DeferredObjectTasks array is empty the promise is
	 * going to fail.
	 * If the filter is null any call to onDone promise callback is considered a success.
	 *
	 * UseCase: We have three repositories:
	 * cacheRepository, diskRepository, networkRepository which return Promise
	 * and we want to take the first valid response but without executing all of them.
	 *
	 * @param filter
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(final PromiseObjectTask.Filter<D> filter,
			PromiseObjectTask<D, F, P>... promiseObjectTasks);

	/**
	 * Executes the list of DeferredObjectTasks secuentially until one has success.
	 * A deferredObjectTasks succeeds if his onDone promise callback is called and his result
	 * filtered with DeferredObjectTask.Filter is true.
	 * If no one success or the DeferredObjectTasks list is empty the promise is
	 * going to fail.
	 * If the filter is null any call to onDone promise callback is considered a success.
	 *
	 * UseCase: We have three repositories:
	 * cacheRepository, diskRepository, networkRepository which return Promise
	 * and we want to take the first valid response but without executing all of them.
	 *
	 * @param filter
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(final PromiseObjectTask.Filter<D> filter,
			List<PromiseObjectTask<D, F, P>> promiseObjectTasks);

	/**
	 * Executes the array of DeferredObjectTasks secuentially until one has success.
	 * A deferredObjectTasks succeeds if his onDone promise callback is called.
	 * If no one success or the DeferredObjectTasks array is empty the promise is
	 * going to fail.
	 *
	 * UseCase: We have three repositories:
	 * cacheRepository, diskRepository, networkRepository which return Promise
	 * and we want to take the first valid response but without executing all of them.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(PromiseObjectTask<D, F, P>... promiseObjectTasks);

	/**
	 * Executes the list of DeferredObjectTasks secuentially until one has success.
	 * A deferredObjectTasks succeeds if his onDone promise callback is called.
	 * If no one success or the DeferredObjectTasks list is empty the promise is
	 * going to fail.
	 *
	 * UseCase: We have three repositories:
	 * cacheRepository, diskRepository, networkRepository which return Promise
	 * and we want to take the first valid response but without executing all of them.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(List<PromiseObjectTask<D, F, P>> promiseObjectTasks);

	/**
	 * This will return a promise with a done callback equals to true if all the promiseObjectTask return true, or false
	 * if one of them return false.
	 * Task will be executed sequentially which lets to avoid running all the task when one has returned false.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<F, P> Promise<Boolean, F, P> lazyAnd(PromiseObjectTask<Boolean, F, P>... promiseObjectTasks);

	/**
	 * This will return a promise with a done callback equals to true if all the promiseObjectTask return true, or false
	 * if one of them return false.
	 * Task will be executed sequentially which lets to avoid running all the task when one has returned false.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<F, P> Promise<Boolean, F, P> lazyAnd(List<PromiseObjectTask<Boolean, F, P>> promiseObjectTasks);

	/**
	 * This will return a promise with a done callback equals to true if one of the promiseObjectTask returns true, or false
	 * if all of them return false.
	 * Task will be executed sequentially which lets to avoid running all the task when one has returned true.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<F, P> Promise<Boolean, F, P> lazyOr(PromiseObjectTask<Boolean, F, P>... promiseObjectTasks);

	/**
	 * This will return a promise with a done callback equals to true if one of the promiseObjectTask returns true, or false
	 * if all of them return false.
	 * Task will be executed sequentially which lets to avoid running all the task when one has returned true.
	 *
	 * @param promiseObjectTasks
	 * @return promise
	 */
	<F, P> Promise<Boolean, F, P> lazyOr(List<PromiseObjectTask<Boolean, F, P>> promiseObjectTasks);

	/**
	 * Retrieves a promise that returns null void objects as results of done, fail and progress.
	 *
	 */
	<V, F, P> Promise<Void, Void, Void> getVoidPromise(Promise<V, F, P> promise);


	/**
	 * Retrieves a promise that returns null void objects as results of done, fail and progress.
	 *
	 */
	<V, F, P> Promise<Void, Void, Void> getAlwaysDoneVoidPromise(Promise<V, F, P> promise);
}
