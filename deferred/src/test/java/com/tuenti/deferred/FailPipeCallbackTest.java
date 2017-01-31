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

import org.junit.Test;

public class FailPipeCallbackTest extends CallbacksTestBase {

	@Test
	public void testPipeComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Fail.Pipe.Computation<Void, Void, Void, Void> callback = givenAComputationProgressPipe();

		whenTheDeferredIsFailedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testPipeDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Fail.Pipe.Disk<Void, Void, Void, Void> callback = givenADiskProgressPipe();

		whenTheDeferredIsFailedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testPipeNetworkIsExecutedInNetworkmamaExecutor() {
		givenADeferredObject();
		Fail.Pipe.Network<Void, Void, Void, Void> callback = givenANetworkProgressPipe();

		whenTheDeferredIsFailedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private Fail.Pipe.Computation<Void, Void, Void, Void> givenAComputationProgressPipe() {
		return new Fail.Pipe.Computation<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeFail(Void result) {
				return null;
			}
		};
	}

	private Fail.Pipe.Disk<Void, Void, Void, Void> givenADiskProgressPipe() {
		return new Fail.Pipe.Disk<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeFail(Void result) {
				return null;
			}
		};
	}

	private Fail.Pipe.Network<Void, Void, Void, Void> givenANetworkProgressPipe() {
		return new Fail.Pipe.Network<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeFail(Void result) {
				return null;
			}
		};
	}

	private void whenTheDeferredIsFailedWithAPipeCallback(FailPipe<Void, Void, Void, Void> failPipe) {
		deferred.reject(null).then(null, failPipe);
	}

}
