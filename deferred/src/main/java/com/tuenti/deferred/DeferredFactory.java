/*
 * Copyright (c) Tuenti Technologies S.L. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuenti.deferred;

import javax.inject.Inject;

/**
 * @author Rayco Araña <rayco@tuenti.com>
 */
public class DeferredFactory {

	private final ExecutorProvider executorProvider;

	@Inject
	public DeferredFactory(ExecutorProvider executorProvider) {
		this.executorProvider = executorProvider;
	}

	public <D, F, P> Deferred<D, F, P> build() {
		return new DeferredObject<>(executorProvider);
	}

	public <D, F, P> Deferred<D, F, P> build(Promise<D, F, P> promise) {
		final Deferred<D, F, P> deferred = new DeferredObject<>(executorProvider);

		promise.done(new DoneCallback<D>() {
			@Override
			public void onDone(D result) {
				deferred.resolve(result);
			}
		}).progress(new ProgressCallback<P>() {
			@Override
			public void onProgress(P progress) {
				deferred.notify(progress);
			}
		}).fail(new FailCallback<F>() {
			@Override
			public void onFail(F result) {
				deferred.reject(result);
			}
		});
		return deferred;
	}
}
