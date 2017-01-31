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

public class FailCallbacksTest extends CallbacksTestBase {

	@Test
	public void testUIFailCallbackIsExecutedInUIExecutor() {
		givenADeferredObject();
		Fail.UI<Void> callback = givenAUIFailCallback();

		whenTheDeferredIsRejectedWithAFailCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(uiExecutor);
	}

	@Test
	public void testComputationFailCallbackIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Fail.Computation<Void> callback = givenAComputationFailCallback();

		whenTheDeferredIsRejectedWithAFailCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testDiskFailCallbackIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Fail.Disk<Void> callback = givenADiskFailCallback();

		whenTheDeferredIsRejectedWithAFailCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testNetworkFailCallbackIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Fail.Network<Void> callback = givenANetworkFailCallback();

		whenTheDeferredIsRejectedWithAFailCallback(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private Fail.UI<Void> givenAUIFailCallback() {
		return new Fail.UI<Void>() {
			@Override
			public void onFail(Void result) {

			}
		};
	}

	private Fail.Computation<Void> givenAComputationFailCallback() {
		return new Fail.Computation<Void>() {
			@Override
			public void onFail(Void result) {

			}
		};
	}

	private Fail.Disk<Void> givenADiskFailCallback() {
		return new Fail.Disk<Void>() {
			@Override
			public void onFail(Void result) {

			}
		};
	}

	private Fail.Network<Void> givenANetworkFailCallback() {
		return new Fail.Network<Void>() {
			@Override
			public void onFail(Void result) {

			}
		};
	}

	private void whenTheDeferredIsRejectedWithAFailCallback(FailCallback<Void> callback) {
		deferred.reject(null).fail(callback);
	}

}
