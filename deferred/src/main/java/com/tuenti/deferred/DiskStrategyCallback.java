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

import java.util.concurrent.Executor;

import com.tuenti.deferred.Promise.State;

class DiskStrategyCallback implements CallbackStrategy {

	public <D, F, P> void triggerDone(AbstractPromise<D, F, P> abstractPromise, DoneCallback<D> callback, D resolved) {

		abstractPromise.triggerDoneOnExecutor(callback, resolved, getExecutor(abstractPromise));
	}

	@Override
	public <D, F, P> void triggerFail(AbstractPromise<D, F, P> abstractPromise, FailCallback<F> callback, F rejected) {

		abstractPromise.triggerFailOnExecutor(callback, rejected, getExecutor(abstractPromise));
	}

	@Override
	public <D, F, P> void triggerProgress(AbstractPromise<D, F, P> abstractPromise, ProgressCallback<P> callback, P progress) {

		abstractPromise.triggerProgressOnExecutor(callback, progress, getExecutor(abstractPromise));
	}

	@Override
	public <D, F, P> void triggerAlways(AbstractPromise<D, F, P> abstractPromise, AlwaysCallback<D, F> callback, State state, D resolve, F reject) {

		abstractPromise.triggerAlwaysOnExecutor(callback, state, resolve, reject, getExecutor(abstractPromise));
	}

	private <D, F, P> Executor getExecutor(AbstractPromise<D, F, P> abstractPromise) {
		return abstractPromise.getExecutorProvider().getDiskExecutor();
	}
}
