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

import com.tuenti.deferred.context.ViewContextHolder;

public interface Fail {

	interface Immediately<F> extends FailCallback<F> {

	}

	/**
	 *Please use Fail.UIContextual if your callback depends on a view
	 */
	interface UI<F> extends FailCallback<F>, UICallback {

	}

	/**
	 *  Fail callback that gets executed always on a UI thread
	 */
	abstract class UIContextual<F, V> implements Fail.UI<F> {

		private final ViewContextHolder<V> viewContextHolder;

		public UIContextual(V view) {
			this.viewContextHolder = ViewContextHolder.of(view);
		}

		@Override
		public final void onFail(final F result) {
			if (viewContextHolder.isValid()) {
				onFailWithContext(result);
			}
		}

		public V view() {
			return viewContextHolder.get();
		}

		public abstract void onFailWithContext(final F result);
	}

	interface Computation<F> extends FailCallback<F>, ComputationCallback {

	}
	interface Disk<F> extends FailCallback<F>, DiskCallback {

	}
	interface Network<F> extends FailCallback<F>, NetworkCallback {

	}

	interface Pipe {

		interface Immediately<F, D_OUT, F_OUT, P_OUT> extends FailPipe<F, D_OUT, F_OUT, P_OUT> {

		}
		interface Computation<F, D_OUT, F_OUT, P_OUT> extends FailPipe<F, D_OUT, F_OUT, P_OUT>, ComputationCallback {

		}

		interface Disk<F, D_OUT, F_OUT, P_OUT> extends FailPipe<F, D_OUT, F_OUT, P_OUT>, DiskCallback {

		}

		interface Network<F, D_OUT, F_OUT, P_OUT> extends FailPipe<F, D_OUT, F_OUT, P_OUT>, NetworkCallback {

		}
	}

	interface Filter {

		interface Immediately<F, F_OUT> extends FailFilter<F, F_OUT> {

		}

		interface Computation<F, F_OUT> extends FailFilter<F, F_OUT>, ComputationCallback {

		}

		interface Disk<F, F_OUT> extends FailFilter<F, F_OUT>, DiskCallback {

		}

		interface Network<F, F_OUT> extends FailFilter<F, F_OUT>, NetworkCallback {

		}
	}
}
