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

class ComputationPipeStrategy implements PipeStrategy {

	@Override
	public <D, F, P, D_OUT, F_OUT, P_OUT> void onDone(PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> pipedPromise,
			DonePipe<D, D_OUT, F_OUT, P_OUT> donePipe, D result) {

		pipedPromise.executeDonePipeOnPool(donePipe, result, getExecutor(pipedPromise));
	}

	@Override
	public <D, F, P, D_OUT, F_OUT, P_OUT> void onProgress(PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> pipedPromise,
			ProgressPipe<P, D_OUT, F_OUT, P_OUT> progressPipe, P progress) {

		pipedPromise.executeProgressPipeOnPool(progressPipe, progress, getExecutor(pipedPromise));
	}

	@Override
	public <D, F, P, D_OUT, F_OUT, P_OUT> void onFail(PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> pipedPromise,
			FailPipe<F, D_OUT, F_OUT, P_OUT> failPipe, F fail) {

		pipedPromise.executeFailPipeOnPool(failPipe, fail, getExecutor(pipedPromise));
	}

	private <D, F, P, D_OUT, F_OUT, P_OUT> Executor getExecutor(PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> pipedPromise) {
		return pipedPromise.getExecutorProvider().getComputationExecutor();
	}
}
