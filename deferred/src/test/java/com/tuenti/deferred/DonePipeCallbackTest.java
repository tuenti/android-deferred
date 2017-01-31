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

public class DonePipeCallbackTest extends CallbacksTestBase {

	@Test
	public void testPipeComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Done.Pipe.Computation<Void, Void, Void, Void> callback = givenAComputationProgressPipe();

		whenTheDeferredIsResolvedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testPipeDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Done.Pipe.Disk<Void, Void, Void, Void> callback = givenADiskProgressPipe();

		whenTheDeferredIsResolvedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testPipeNetworkIsExecutedInNetworkmamaExecutor() {
		givenADeferredObject();
		Done.Pipe.Network<Void, Void, Void, Void> callback = givenANetworkProgressPipe();

		whenTheDeferredIsResolvedWithAPipeCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private Done.Pipe.Computation<Void, Void, Void, Void> givenAComputationProgressPipe() {
		return new Done.Pipe.Computation<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeDone(Void result) {
				return null;
			}
		};
	}

	private Done.Pipe.Disk<Void, Void, Void, Void> givenADiskProgressPipe() {
		return new Done.Pipe.Disk<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeDone(Void result) {
				return null;
			}
		};
	}

	private Done.Pipe.Network<Void, Void, Void, Void> givenANetworkProgressPipe() {
		return new Done.Pipe.Network<Void, Void, Void, Void>() {
			@Override
			public Promise<Void, Void, Void> pipeDone(Void result) {
				return null;
			}
		};
	}

	private void whenTheDeferredIsResolvedWithAPipeCallback(DonePipe<Void, Void, Void, Void> donePipe) {
		deferred.resolve(null).then(donePipe);
	}

}
