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


public class DoneFilterCallbackTest extends CallbacksTestBase {

	@Test
	public void testFilterComputationIsExecutedInComputationExecutor() {
		givenADeferredObject();
		Done.Filter.Computation<Void, Void> callback = givenAComputationFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(computationExecutor);
	}

	@Test
	public void testFilterDiskIsExecutedInDiskExecutor() {
		givenADeferredObject();
		Done.Filter.Disk<Void, Void> callback = givenADiskFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(diskExecutor);
	}


	@Test
	public void testFilterNetworkIsExecutedInNetworkExecutor() {
		givenADeferredObject();
		Done.Filter.Network<Void, Void> callback = givenANetworkFilter();

		whenTheDeferredIsResolvedAndFiltered(callback);

		thenTheCallbackIsExecutedOnTheExecutor(networkExecutor);
	}

	private void whenTheDeferredIsResolvedAndFiltered(DoneFilter<Void, Void> callback) {
		deferred.resolve(null).then(callback);
	}

	private Done.Filter.Computation<Void, Void> givenAComputationFilter() {
		return new Done.Filter.Computation<Void, Void>() {
			@Override
			public Void filterDone(Void result) {
				return null;
			}
		};
	}

	private Done.Filter.Disk<Void, Void> givenADiskFilter() {
		return new Done.Filter.Disk<Void, Void>() {
			@Override
			public Void filterDone(Void result) {
				return null;
			}
		};
	}

	private Done.Filter.Network<Void, Void> givenANetworkFilter() {
		return new Done.Filter.Network<Void, Void>() {
			@Override
			public Void filterDone(Void result) {
				return null;
			}
		};
	}
}
