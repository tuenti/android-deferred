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

import com.tuenti.deferred.Promise.State;
import com.tuenti.deferred.context.ViewContextHolder;

/**
 * @author Rayco Araña <rayco@tuenti.com>
 */
public interface Always {


	interface Immediately<D, R> extends AlwaysCallback<D, R> {

	}

	/**
	 * Please use UIAlwaysContextualCallback if your callback depends on a view
	 */
	interface UI<D, R> extends AlwaysCallback<D, R>, UICallback {

	}

	abstract class UIContextual<D, R, V> implements Always.UI<D, R> {

		private final ViewContextHolder<V> viewContextHolder;

		public UIContextual(V view) {
			this.viewContextHolder = ViewContextHolder.of(view);
		}

		public final void onAlways(final State state, final D resolved, final R rejected) {
			if (viewContextHolder.isValid()) {
				onAlwaysWithContext(state, resolved, rejected);
			}
		}

		public V view() {
			return viewContextHolder.get();
		}

		public abstract void onAlwaysWithContext(final State state, final D resolved, final R rejected);

	}

	interface Computation<D, R> extends AlwaysCallback<D, R>, ComputationCallback {

	}

	interface Disk<D, R> extends AlwaysCallback<D, R>, DiskCallback {

	}

	interface Network<D, R> extends AlwaysCallback<D, R>, NetworkCallback {

	}
}
