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

public class LoggerProvider {

	private static class DefaultLogger implements Logger {

		@Override
		public void error(String s, Exception e) {
			System.err.println(s);
			e.printStackTrace();
		}
	}

	private static Logger logger = new DefaultLogger();

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LoggerProvider.logger = logger;
	}

}
