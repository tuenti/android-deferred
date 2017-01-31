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

public class ProgressFilterCallback extends CallbacksTestBase {

	@Test
	public void testFilterComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Progress.Filter.Computation<Void, Void> callback = givenAComputationFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testFilterDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Progress.Filter.Disk<Void, Void> callback = givenADiskFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}


	@Test
	public void testFilterNetworkIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Progress.Filter.Network<Void, Void> callback = givenANetworkFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsResolvedAndFiltered(ProgressFilter<Void, Void> callback) {
		deferred.then(null, null, callback);
		deferred.notify(null);
	}

	private Progress.Filter.Computation<Void, Void> givenAComputationFilter() {
		return new Progress.Filter.Computation<Void, Void>() {
			@Override
			public Void filterProgress(Void result) {
				return null;
			}
		};
	}

	private Progress.Filter.Disk<Void, Void> givenADiskFilter() {
		return new Progress.Filter.Disk<Void, Void>() {
			@Override
			public Void filterProgress(Void result) {
				return null;
			}
		};
	}

	private Progress.Filter.Network<Void, Void> givenANetworkFilter() {
		return new Progress.Filter.Network<Void, Void>() {
			@Override
			public Void filterProgress(Void result) {
				return null;
			}
		};
	}
}
