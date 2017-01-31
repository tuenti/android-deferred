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

import java.util.concurrent.Executor;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


public class CallbacksTestBase {
	@Mock
	Executor uiExecutor;
	@Mock
	Executor computationExecutor;
	@Mock
	Executor diskExecutor;
	@Mock
	Executor networkExecutor;

	ExecutorProvider executorProvider;

	Deferred<Void, Void, Void> deferred;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		executorProvider = new ExecutorProvider() {
			@Override
			public Executor getUiExecutor() {
				return uiExecutor;
			}

			@Override
			public Executor getComputationExecutor() {
				return computationExecutor;
			}

			@Override
			public Executor getDiskExecutor() {
				return diskExecutor;
			}

			@Override
			public Executor getNetworkExecutor() {
				return networkExecutor;
			}
		};
	}

	void givenADeferredObject() {
		deferred = new DeferredObject<>(executorProvider);
	}

	void thenTheCallbackIsExecutedOnTheExecutor(Executor executor) {
		verify(executor).execute(any(Runnable.class));
	}

}
