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

public class DoneCallbackTest extends CallbacksTestBase {

	@Test
	public void testUIDoneCallbackIsExecutedInUIExecutor() {
		givenADeferredObject();
		Done.UI<Void> callback = givenAUIDoneCallback();

		whenTheDeferredIsResolvedWithADoneCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(uiExecutor);
	}

	@Test
	public void testComputationDoneCallbackIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Done.Computation<Void> callback = givenAComputationDoneCallback();

		whenTheDeferredIsResolvedWithADoneCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testDiskDoneCallbackIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Done.Disk<Void> callback = givenADiskDoneCallback();

		whenTheDeferredIsResolvedWithADoneCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testUIDoneCallbackIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Done.Network<Void> callback = givenANetworkDoneCallback();

		whenTheDeferredIsResolvedWithADoneCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsResolvedWithADoneCallback(DoneCallback<Void> callback) {
		deferred.resolve(null).done(callback);
	}

	private Done.UI<Void> givenAUIDoneCallback() {
		return new Done.UI<Void>() {
			@Override
			public void onDone(Void result) {
			}
		};
	}

	private Done.Computation<Void> givenAComputationDoneCallback() {
		return new Done.Computation<Void>() {
			@Override
			public void onDone(Void result) {
			}
		};
	}

	private Done.Disk<Void> givenADiskDoneCallback() {
		return new Done.Disk<Void>() {
			@Override
			public void onDone(Void result) {
			}
		};
	}

	private Done.Network<Void> givenANetworkDoneCallback() {
		return new Done.Network<Void>() {
			@Override
			public void onDone(Void result) {
			}
		};
	}
}
