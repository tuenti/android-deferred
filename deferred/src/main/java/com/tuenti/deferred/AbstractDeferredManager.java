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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.tuenti.deferred.PromiseObjectTask.Filter;


@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractDeferredManager implements DeferredManager {

	private enum Operation {
		AND,
		OR
	}

	private final ExecutorProvider executorProvider;
	private final DeferredFactory deferredFactory;

	public AbstractDeferredManager(ExecutorProvider executorProvider,
			DeferredFactory deferredFactory) {
		this.executorProvider = executorProvider;
		this.deferredFactory = deferredFactory;
	}

	protected abstract void submit(Runnable runnable);

	protected abstract void submit(Callable callable);

	/**
	 * Should {@link Runnable} or {@link Callable} be submitted for execution automatically
	 * when any of the following are called
	 * <ul>
	 * <li>{@link #when(Runnable...)}</li>
	 * <li>{@link #when(Callable...)}</li>
	 * <li>{@link #when(DeferredFutureTask...))}</li>
	 * <li>{@link #when(DeferredCallable)}</li>
	 * <li>{@link #when(DeferredRunnable)}</li>
	 * <li>{@link #when(DeferredFutureTask))}</li>
	 * </ul>
	 *
	 * @return true if should {@link Runnable} or {@link Callable} be submitted for execution automatically
	 * when any of the following are called, false otherwise
	 */
	public abstract boolean isAutoSubmit();

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(Runnable... runnables) {
		assertNotEmpty(runnables);

		Promise[] promises = new Promise[runnables.length];

		for (int i = 0; i < runnables.length; i++) {
			if (runnables[i] instanceof DeferredRunnable) {
				promises[i] = when((DeferredRunnable) runnables[i]);
			} else {
				promises[i] = when(runnables[i]);
			}
		}

		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(Callable<?>... callables) {
		assertNotEmpty(callables);

		Promise[] promises = new Promise[callables.length];

		for (int i = 0; i < callables.length; i++) {
			if (callables[i] instanceof DeferredCallable) {
				promises[i] = when((DeferredCallable) callables[i]);
			} else {
				promises[i] = when(callables[i]);
			}
		}

		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredRunnable<?>... runnables) {
		assertNotEmpty(runnables);

		Promise[] promises = new Promise[runnables.length];

		for (int i = 0; i < runnables.length; i++) {
			promises[i] = when(runnables[i]);
		}

		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredCallable<?, ?>... callables) {
		assertNotEmpty(callables);

		Promise[] promises = new Promise[callables.length];

		for (int i = 0; i < callables.length; i++) {
			promises[i] = when(callables[i]);
		}

		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredFutureTask<?, ?>... tasks) {
		assertNotEmpty(tasks);

		Promise[] promises = new Promise[tasks.length];

		for (int i = 0; i < tasks.length; i++) {
			promises[i] = when(tasks[i]);
		}
		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(Future<?>... futures) {
		assertNotEmpty(futures);

		Promise[] promises = new Promise[futures.length];

		for (int i = 0; i < futures.length; i++) {
			promises[i] = when(futures[i]);
		}
		return when(promises);
	}

	@Override
	public Promise<MultipleResults, OneReject, MasterProgress> when(Promise... promises) {
		assertNotEmpty(promises);
		return new MasterDeferredObject(executorProvider, promises).promise();
	}

	@Override
	public <D, F, P> Promise<D, F, P> when(Promise<D, F, P> promise) {
		return promise;
	}

	@Override
	public <P> Promise<Void, Throwable, P> when(DeferredRunnable<P> runnable) {
		return when(new DeferredFutureTask<Void, P>(runnable));
	}

	@Override
	public <D, P> Promise<D, Throwable, P> when(DeferredCallable<D, P> runnable) {
		return when(new DeferredFutureTask<>(runnable));
	}

	@Override
	public Promise<Void, Throwable, Void> when(Runnable runnable) {
		return when(new DeferredFutureTask<Void, Void>(executorProvider, runnable));
	}

	@Override
	public <D> Promise<D, Throwable, Void> when(Callable<D> callable) {
		return when(new DeferredFutureTask<D, Void>(executorProvider, callable));
	}

	/**
	 * This method is delegated by at least the following methods
	 * <ul>
	 * <li>{@link #when(Callable)}</li>
	 * <li>{@link #when(Callable...)}</li>
	 * <li>{@link #when(Runnable)}</li>
	 * <li>{@link #when(Runnable...)}</li>
	 * <li>{@link #when(Future)}</li>
	 * <li>{@link #when(Future...)}</li>
	 * <li>{@link #when(com.tuenti.deferred.DeferredRunnable...)}</li>
	 * <li>{@link #when(com.tuenti.deferred.DeferredRunnable)}</li>
	 * <li>{@link #when(com.tuenti.deferred.DeferredCallable...)}</li>
	 * <li>{@link #when(com.tuenti.deferred.DeferredCallable)}</li>
	 * <li>{@link #when(DeferredFutureTask...)}</li>
	 * </ul>
	 */
	@Override
	public <D, P> Promise<D, Throwable, P> when(
			DeferredFutureTask<D, P> task) {
		if (task.getStartPolicy() == StartPolicy.AUTO
				|| task.getStartPolicy() == StartPolicy.DEFAULT && isAutoSubmit()) {
			submit(task);
		}
		return task.promise();
	}

	@Override
	public <D> Promise<D, Throwable, Void> when(final Future<D> future) {
		// make sure the task is automatically started

		return when(new DeferredCallable<D, Void>(executorProvider, StartPolicy.AUTO) {
			@Override
			public D call() throws Exception {
				try {
					return future.get();
				} catch (InterruptedException e) {
					throw e;
				} catch (ExecutionException e) {
					if (e.getCause() instanceof Exception) {
						throw (Exception) e.getCause();
					} else {
						throw e;
					}
				}
			}
		});
	}

	@Override
	public <D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(final PromiseObjectTask.Filter<D> filter,
			final PromiseObjectTask<D, F, P>... promiseObjectTasks) {

		Promise<D, F, P> promise = null;

		for (final PromiseObjectTask<D, F, P> promiseObjectTask : promiseObjectTasks) {
			if (promise == null) {
				promise = promiseObjectTask.run();
			} else {
				Done.Pipe.Immediately<D, D, F, P> donePipe = null;
				if (filter != null) {
					donePipe = new Done.Pipe.Immediately<D, D, F, P>() {
						@Override
						public Promise<D, F, P> pipeDone(D result) {
							if (!filter.success(result)) {
								return promiseObjectTask.run();
							} else {
								Deferred<D, F, P> deferredObjectResult = new DeferredObject<>(executorProvider);
								deferredObjectResult.resolve(result);
								return deferredObjectResult;
							}
						}
					};
				}

				promise = promise.then(donePipe,
						new Fail.Pipe.Immediately<F, D, F, P>() {
							@Override
							public Promise<D, F, P> pipeFail(F result) {
								return promiseObjectTask.run();
							}
						}
				);
			}
		}

		if (promise == null) {
			Deferred<D, F, P> deferredObject = new DeferredObject<>(executorProvider);
			deferredObject.reject(null);
			promise = deferredObject;
		}

		return promise;
	}

	@Override
	public <D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(Filter<D> filter, List<PromiseObjectTask<D, F, P>> promiseObjectTasks) {
		return sequentiallyRunUntilFirstDone(filter, promiseObjectTasks.toArray(new PromiseObjectTask[promiseObjectTasks.size()]));
	}

	@Override
	public <D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(List<PromiseObjectTask<D, F, P>> promiseObjectTasks) {
		return sequentiallyRunUntilFirstDone(null, promiseObjectTasks.toArray(new PromiseObjectTask[promiseObjectTasks.size()]));
	}

	@Override
	public <D, F, P> Promise<D, F, P> sequentiallyRunUntilFirstDone(PromiseObjectTask<D, F, P>... promiseObjectTasks) {
		return sequentiallyRunUntilFirstDone(null, promiseObjectTasks);
	}


	/**
	 * @see com.tuenti.deferred.DeferredManager#lazyAnd(PromiseObjectTask[])
	 */
	@Override
	public <F, P> Promise<Boolean, F, P> lazyAnd(final PromiseObjectTask<Boolean, F, P>... promiseObjectTasks) {
		return conditionalOperation(Operation.AND, promiseObjectTasks);
	}

	/**
	 * @see com.tuenti.deferred.DeferredManager#lazyAnd(List)
	 */
	@Override
	public <F, P> Promise<Boolean, F, P> lazyAnd(List<PromiseObjectTask<Boolean, F, P>> promiseObjectTasks) {
		return lazyAnd(promiseObjectTasks.toArray(new PromiseObjectTask[promiseObjectTasks.size()]));
	}

	/**
	 * @see com.tuenti.deferred.DeferredManager#lazyOr(PromiseObjectTask[])
	 */
	@Override
	public <F, P> Promise<Boolean, F, P> lazyOr(PromiseObjectTask<Boolean, F, P>... promiseObjectTasks) {
		return conditionalOperation(Operation.OR, promiseObjectTasks);
	}

	/**
	 * @see com.tuenti.deferred.DeferredManager#lazyOr(List)
	 */
	@Override
	public <F, P> Promise<Boolean, F, P> lazyOr(List<PromiseObjectTask<Boolean, F, P>> promiseObjectTasks) {
		return lazyOr(promiseObjectTasks.toArray(new PromiseObjectTask[promiseObjectTasks.size()]));
	}

	private <F, P> Promise<Boolean, F, P> conditionalOperation(final Operation operation, PromiseObjectTask<Boolean, F, P>... promiseObjectTasks) {
		Promise<Boolean, F, P> promise = null;

		for (final PromiseObjectTask<Boolean, F, P> promiseObjectTask : promiseObjectTasks) {
			if (promise == null) {
				promise = promiseObjectTask.run();
			} else {
				promise = promise.then(new Done.Pipe.Immediately<Boolean, Boolean, F, P>() {
					@Override
					public Promise<Boolean, F, P> pipeDone(Boolean result) {
						if (operation == Operation.OR && result || operation == Operation.AND && !result) {
							Deferred<Boolean, F, P> deferredObject = new DeferredObject<>(executorProvider);
							deferredObject.resolve(operation == Operation.OR);
							return deferredObject;
						} else {
							return promiseObjectTask.run();
						}
					}
				});
			}
		}

		if (promise == null) {
			Deferred<Boolean, F, P> deferredObject = new DeferredObject<>(executorProvider);
			deferredObject.reject(null);
			promise = deferredObject;
		}

		return promise;
	}

	@Override
	public <V, F, P> Promise<Void, Void, Void> getVoidPromise(Promise<V, F, P> promise) {
		return setPromiseToVoid(promise);
	}

	@Override
	public <V, F, P> Promise<Void, Void, Void> getAlwaysDoneVoidPromise(Promise<V, F, P> promise) {
		return setPromiseToVoid(promise)
				.then(new Fail.Pipe.Immediately<Void, Void, Void, Void>() {
					@Override
					public Promise<Void, Void, Void> pipeFail(Void result) {
						return deferredFactory.<Void, Void, Void>build().resolve(null);
					}
				});
	}

	private <V, F, P> Promise<Void, Void, Void> setPromiseToVoid(Promise<V, F, P> promise) {
		return promise
				.then(new Done.Filter.Immediately<V, Void>() {
						@Override
						public Void filterDone(V result) {
							return null;
						}
					}, new Fail.Filter.Immediately<F, Void>() {
						@Override
						public Void filterFail(F result) {
							return null;
						}
					}, new Progress.Filter.Immediately<P, Void>() {
						@Override
						public Void filterProgress(P progress) {
							return null;
						}
					}
				);
	}

	protected void assertNotEmpty(Object[] objects) {
		if (objects == null || objects.length == 0) {
			throw new IllegalArgumentException(
					"Arguments is null or its length is empty");
		}
	}
}
