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

import com.tuenti.deferred.Promise.State;

public class AlwaysCallbacksTest extends CallbacksTestBase {

	@Test
	public void testAlwaysUIIsExecutedInUIExecutor() {
		givenADeferredObject();
		Always.UI<Void, Void> callback = givenAUICallback();

		whenTheDeferredIsFinishedAndAnAlwaysCallbackIsSet(callback);

		thenTheCallbackIsExecutedOnTheExecutor(uiExecutor);
	}

	@Test
	public void testAlwaysComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Always.Computation<Void, Void> callback = givenAComputationCallback();

		whenTheDeferredIsFinishedAndAnAlwaysCallbackIsSet(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testAlwaysDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Always.Disk<Void, Void> callback = givenADiskCallback();

		whenTheDeferredIsFinishedAndAnAlwaysCallbackIsSet(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}

	@Test
	public void testAlwaysNetworkIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Always.Network<Void, Void> callback = givenANetworkCallback();

		whenTheDeferredIsFinishedAndAnAlwaysCallbackIsSet(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsFinishedAndAnAlwaysCallbackIsSet(AlwaysCallback<Void, Void> callback) {
		deferred.resolve(null).always(callback);
	}

	private Always.UI<Void, Void> givenAUICallback() {
		return new Always.UI<Void, Void>() {
			@Override
			public void onAlways(State state, Void resolved, Void rejected) {
			}
		};
	}

	private Always.Computation<Void, Void> givenAComputationCallback() {
		return new Always.Computation<Void, Void>() {
			@Override
			public void onAlways(State state, Void resolved, Void rejected) {
			}
		};
	}

	private Always.Disk<Void, Void> givenADiskCallback() {
		return new Always.Disk<Void, Void>() {
			@Override
			public void onAlways(State state, Void resolved, Void rejected) {
			}
		};
	}

	private Always.Network<Void, Void> givenANetworkCallback() {
		return new Always.Network<Void, Void>() {
			@Override
			public void onAlways(State state, Void resolved, Void rejected) {
			}
		};
	}
}
