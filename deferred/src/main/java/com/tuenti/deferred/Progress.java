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


public interface Progress {

	interface Immediately<P> extends ProgressCallback<P> {

	}
	
	/**
	 * Please use Progress.UIContextual if your callback depends on a view
	 */
	interface UI<P> extends ProgressCallback<P>, UICallback {

	}

	abstract class UIContextual<P, V> implements Progress.UI<P> {

		private final ViewContextHolder<V> viewContextHolder;

		public UIContextual(V view) {
			this.viewContextHolder = ViewContextHolder.of(view);
		}

		@Override
		public final void onProgress(final P progress) {
			if (viewContextHolder.isValid()) {
				onProgressWithContext(progress);
			}
		}

		public V view() {
			return viewContextHolder.get();
		}

		public abstract void onProgressWithContext(final P progress);

	}

	interface Computation<P> extends ProgressCallback<P>, ComputationCallback {

	}
	interface Disk<P> extends ProgressCallback<P>, DiskCallback {

	}
	interface Network<P> extends ProgressCallback<P>, NetworkCallback {

	}

	interface Pipe {
		interface Immediately<P> extends ProgressCallback<P> {

		}

		interface Computation<P> extends ProgressCallback<P>, ComputationCallback {

		}

		interface Disk<P> extends ProgressCallback<P>, DiskCallback {

		}

		interface Network<P> extends ProgressCallback<P>, NetworkCallback {

		}
	}

	interface Filter {
		interface Immediately<P, P_OUT> extends ProgressFilter<P, P_OUT> {

		}

		interface Computation<P, P_OUT> extends ProgressFilter<P, P_OUT>, ComputationCallback {

		}

		interface Disk<P, P_OUT> extends ProgressFilter<P, P_OUT>, DiskCallback {

		}

		interface Network<P, P_OUT> extends ProgressFilter<P, P_OUT>, NetworkCallback {

		}
	}
}
