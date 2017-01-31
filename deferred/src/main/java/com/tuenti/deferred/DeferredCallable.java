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


import java.util.concurrent.Callable;

import com.tuenti.deferred.DeferredManager.StartPolicy;

/**
 * Use this as superclass in case you need to be able to return a result and notify progress.
 * If you don't need to notify progress, you can simply use {@link Callable}
 * 
 * @see #notify(Object)
 * @author Ray Tsang
 *
 * @param <D> Type used as return type of {@link Callable#call()}, and {@link Deferred#resolve(Object)}
 * @param <P> Type used for {@link Deferred#notify(Object)}
 */
abstract class DeferredCallable<D, P> implements Callable<D> {
	private final Deferred<D, Throwable, P> deferred;
	private final StartPolicy startPolicy;
	
	public DeferredCallable(ExecutorProvider executorProvider) {
		this.startPolicy = StartPolicy.DEFAULT;
		deferred = new DeferredObject<>(executorProvider);
	}
	
	public DeferredCallable(ExecutorProvider executorProvider,
			StartPolicy startPolicy) {
		this.startPolicy = startPolicy;
		this.deferred = new DeferredObject<>(executorProvider);
	}
	
	/**
	 * @see Deferred#notify(Object)
	 * @param progress
	 */
	protected void notify(P progress) {
		deferred.notify(progress);
	}
	
	protected Deferred<D, Throwable, P> getDeferred() {
		return deferred;
	}

	public StartPolicy getStartPolicy() {
		return startPolicy;
	}
}
