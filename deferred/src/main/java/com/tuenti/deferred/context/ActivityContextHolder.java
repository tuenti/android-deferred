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

package com.tuenti.deferred.context;

import android.support.v7.app.AppCompatActivity;

/**
 * Copyright (c) Tuenti Technologies. All rights reserved.
 *
 * @author rayco@tuenti.com
 */
class ActivityContextHolder<V extends AppCompatActivity> extends ViewContextHolder<V> {

	public ActivityContextHolder(V viewContext) {
		super(viewContext);
	}

	@Override
	public boolean isValid() {
		AppCompatActivity activity = get();

		return activity != null &&
				// finish() method called explicitly
				!activity.isFinishing() &&
				// Trick to get Activity.isDestroyed() on any Android version
				!activity.getSupportFragmentManager().isDestroyed();
	}

}