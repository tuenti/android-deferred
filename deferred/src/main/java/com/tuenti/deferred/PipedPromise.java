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

import java.util.concurrent.Executor;

class PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> extends DeferredObject<D_OUT, F_OUT, P_OUT> implements Promise<D_OUT, F_OUT, P_OUT>{

	private static final PipeStrategy immediatelyPipeStrategy = new ImmediatelyPipeStrategy();
	private static final PipeStrategy computationPipeStrategyCallback = new ComputationPipeStrategy();
	private static final PipeStrategy diskPipeStrategyCallback = new DiskPipeStrategy();
	private static final PipeStrategy networkPipeStrategyCallback = new NetworkPipeStrategy();
	private static final PipeStrategy directlyPipeStrategy = new DirectlyPipeStrategy();

	public PipedPromise(ExecutorProvider executorProvider,
			final Promise<D, F, P> promise,
			final DonePipe<D, D_OUT, F_OUT, P_OUT> donePipe,
			final FailPipe<F, D_OUT, F_OUT, P_OUT> failPipe,
			final ProgressPipe<P, D_OUT, F_OUT, P_OUT> progressPipe) {

		super(executorProvider);

		promise.done(new DoneCallback<D>() {
			@Override
			public void onDone(D result) {
				getStrategyForPipe(donePipe).onDone(PipedPromise.this, donePipe, result);
			}
		}).progress(new ProgressCallback<P>() {
			@Override
			public void onProgress(P progress) {
				getStrategyForPipe(progressPipe).onProgress(PipedPromise.this, progressPipe, progress);
			}
		}).fail(new FailCallback<F>() {
			@Override
			public void onFail(F result) {
				getStrategyForPipe(failPipe).onFail(PipedPromise.this, failPipe, result);
			}
		});
	}

	private PipeStrategy getStrategyForPipe(Object pipe) {
		PipeStrategy result;

		if (pipe == null) {
			result = directlyPipeStrategy;
		} else if (pipe instanceof ComputationCallback) {
			result = computationPipeStrategyCallback;
		} else if (pipe instanceof DiskCallback) {
			result = diskPipeStrategyCallback;
		} else if (pipe instanceof NetworkCallback) {
			result = networkPipeStrategyCallback;
		} else {
			result = immediatelyPipeStrategy;
		}

		return result;
	}

	protected void executeDonePipeOnPool(final DonePipe<D, D_OUT, F_OUT, P_OUT> donePipe,
			final D result, Executor executor) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				pipe(donePipe.pipeDone(result));
			}
		});
	}

	protected void executeProgressPipeOnPool(final ProgressPipe<P, D_OUT, F_OUT, P_OUT> progressPipe,
			final P progress, Executor executor) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				pipe(progressPipe.pipeProgress(progress));
			}
		});
	}


	protected void executeFailPipeOnPool(final FailPipe<F, D_OUT, F_OUT, P_OUT> failPipe,
			final F result, Executor executor) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				pipe(failPipe.pipeFail(result));
			}
		});
	}

	protected Promise<D_OUT, F_OUT, P_OUT> pipe(Promise<D_OUT, F_OUT, P_OUT> promise) {
		promise.done(new DoneCallback<D_OUT>() {
			@Override
			public void onDone(D_OUT result) {
				PipedPromise.this.resolve(result);
			}
		}).fail(new FailCallback<F_OUT>() {
			@Override
			public void onFail(F_OUT result) {
				PipedPromise.this.reject(result);
			}
		}).progress(new ProgressCallback<P_OUT>() {
			@Override
			public void onProgress(P_OUT progress) {
				PipedPromise.this.notify(progress);
			}
		});

		return promise;
	}
}