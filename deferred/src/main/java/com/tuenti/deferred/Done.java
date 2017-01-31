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

public interface Done {

	interface Immediately<D> extends  DoneCallback<D> {

	}

	/**
	 * Please use UIDoneContextualCallback if your callback depends on a view
	 */
	interface UI<D> extends DoneCallback<D>, UICallback {

	}

	/**
	 *  Done callback that gets executed always on a UI thread
	 */
	abstract class UIContextual<D, V> implements Done.UI<D> {

		private final ViewContextHolder<V> viewContextHolder;

		public UIContextual(V view) {
			this.viewContextHolder = ViewContextHolder.of(view);
		}

		@Override
		public final void onDone(final D result) {
			if (viewContextHolder.isValid()) {
				onDoneWithContext(result);
			} else {
				onDoneWithInvalidContext(result);
			}
		}

		public V view() {
			return viewContextHolder.get();
		}

		public abstract void onDoneWithContext(final D result);

		public void onDoneWithInvalidContext(final D result) {
			/* To be overridden by classes that needs special handling when view is not valid */
		}
	}

	interface Computation<D> extends DoneCallback<D>, ComputationCallback {

	}

	interface Disk<D> extends DoneCallback<D>, DiskCallback {

	}

	interface Network<D> extends DoneCallback<D>, NetworkCallback {

	}

	interface Pipe {
		interface Immediately<D, D_OUT, F_OUT, P_OUT> extends DonePipe<D, D_OUT, F_OUT, P_OUT> {

		}

		interface Computation<D, D_OUT, F_OUT, P_OUT> extends DonePipe<D, D_OUT, F_OUT, P_OUT>, ComputationCallback {

		}

		interface Disk<D, D_OUT, F_OUT, P_OUT> extends DonePipe<D, D_OUT, F_OUT, P_OUT>, DiskCallback {

		}

		interface Network<D, D_OUT, F_OUT, P_OUT> extends DonePipe<D, D_OUT, F_OUT, P_OUT>, NetworkCallback {

		}
	}

	interface Filter {
		interface Immediately<D, D_OUT> extends DoneFilter<D, D_OUT> {

		}

		interface Computation<D, D_OUT> extends DoneFilter<D, D_OUT>, ComputationCallback {

		}

		interface Disk<D, D_OUT> extends DoneFilter<D, D_OUT>, DiskCallback {

		}

		interface Network<D, D_OUT> extends DoneFilter<D, D_OUT>, NetworkCallback {

		}
	}
}
