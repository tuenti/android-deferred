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

public class FailFilterCallback extends CallbacksTestBase {

	@Test
	public void testFilterComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Fail.Filter.Computation<Void, Void> callback = givenAComputationFilter();

		whenTheDeferredIsFailedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testFilterDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Fail.Filter.Disk<Void, Void> callback = givenADiskFilter();

		whenTheDeferredIsFailedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}


	@Test
	public void testFilterNetworkIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Fail.Filter.Network<Void, Void> callback = givenANetworkFilter();

		whenTheDeferredIsFailedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsFailedAndFiltered(FailFilter<Void, Void> callback) {
		deferred.reject(null).then(null, callback, null);
	}

	private Fail.Filter.Computation<Void, Void> givenAComputationFilter() {
		return new Fail.Filter.Computation<Void, Void>() {
			@Override
			public Void filterFail(Void result) {
				return null;
			}
		};
	}

	private Fail.Filter.Disk<Void, Void> givenADiskFilter() {
		return new Fail.Filter.Disk<Void, Void>() {
			@Override
			public Void filterFail(Void result) {
				return null;
			}
		};
	}

	private Fail.Filter.Network<Void, Void> givenANetworkFilter() {
		return new Fail.Filter.Network<Void, Void>() {
			@Override
			public Void filterFail(Void result) {
				return null;
			}
		};
	}
}
