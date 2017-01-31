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

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * @author rayco@tuenti.com
 */
public class ViewContextHolder<V> {

	public static final ViewContextHolder NOP = new ViewContextHolder<>(new Object());

	private final WeakReference<V> reference;

	@SuppressWarnings("unchecked")
	public static <V> ViewContextHolder<V> of(V viewContext) {
		ViewContextHolder viewContextHolder;
		if (viewContext instanceof Activity) {
			viewContextHolder = new ActivityContextHolder<>((AppCompatActivity) viewContext);
		} else if (viewContext instanceof Fragment) {
			viewContextHolder = new FragmentContextHolder<>((Fragment) viewContext);
		} else {
			viewContextHolder = new ViewContextHolder(viewContext);
		}
		return viewContextHolder;
	}

	protected ViewContextHolder(V viewContext) {
		reference = new WeakReference<>(viewContext);
	}

	public boolean isValid() {
		return reference.get() != null;
	}

	public V get() {
		return reference.get();
	}

}