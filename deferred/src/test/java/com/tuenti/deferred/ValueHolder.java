/*
 * Copyright 2013 Ray Tsang
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuenti.deferred;

import org.junit.Assert;

public class ValueHolder<T> {
	private T value;
	
	public ValueHolder() {
	}
	
	public ValueHolder(T value) {
		this.value = value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	public T get() {
		return this.value;
	}
	
	public void clear() {
		this.value = null;
	}
	
	public void assertEquals(T other) {
		Assert.assertEquals(other, value);
	}
}
