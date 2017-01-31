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

import com.tuenti.deferred.Promise.State;

interface CallbackStrategy {

	<D, F, P> void triggerDone(AbstractPromise<D, F, P> promise, DoneCallback<D> callback, D resolved);

	<D, F, P> void triggerFail(AbstractPromise<D, F, P> promise, FailCallback<F> callback, F rejected);

	<D, F, P> void triggerProgress(AbstractPromise<D, F, P> promise, ProgressCallback<P> callback, P progress);

	<D, F, P> void triggerAlways(AbstractPromise<D, F, P> promise, AlwaysCallback<D, F> callback, State state, D resolve, F reject);

}