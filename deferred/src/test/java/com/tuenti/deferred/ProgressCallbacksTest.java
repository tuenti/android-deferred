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

public class ProgressCallbacksTest extends CallbacksTestBase {

	@Test
	public void testUIProgressCallbackIsExecutedInUIExecutor() {
		givenADeferredObject();
		Progress.UI<Void> callback = givenUIProgressCallback();

		whenTheDeferredIsNotifiedWithAProgressCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(uiExecutor);
	}

	@Test
	public void testComputationProgressCallbackIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Progress.Computation<Void> callback = givenAComputationProgressCallback();

		whenTheDeferredIsNotifiedWithAProgressCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testDiskProgressCallbackIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Progress.Disk<Void> callback = givenADiskProgressCallback();

		whenTheDeferredIsNotifiedWithAProgressCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testNetworkProgressCallbackIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Progress.Network<Void> callback = givenANetworkProgressCallback();

		whenTheDeferredIsNotifiedWithAProgressCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsNotifiedWithAProgressCallback(ProgressCallback<Void> callback) {
		((Deferred<Void, Void, Void>)deferred.progress(callback)).notify(null);
	}


	private Progress.UI<Void> givenUIProgressCallback() {
		return new Progress.UI<Void>() {
			@Override
			public void onProgress(Void progress) {
			}
		};
	}

	private Progress.Computation<Void> givenAComputationProgressCallback() {
		return new Progress.Computation<Void>() {
			@Override
			public void onProgress(Void progress) {
			}
		};
	}

	private Progress.Disk<Void> givenADiskProgressCallback() {
		return new Progress.Disk<Void>() {
			@Override
			public void onProgress(Void progress) {
			}
		};
	}

	private Progress.Network<Void> givenANetworkProgressCallback() {
		return new Progress.Network<Void>() {
			@Override
			public void onProgress(Void progress) {
			}
		};
	}
}
