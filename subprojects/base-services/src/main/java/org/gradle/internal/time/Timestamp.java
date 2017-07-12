/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.time;

public final class Timestamp {

    public final long normalized;
    public final Long actual;
    public final int ordinal;

    public Timestamp(long normalized, int ordinal) {
        this.normalized = normalized;
        this.actual = null;
        this.ordinal = ordinal;
    }

    public Timestamp(long normalized, long actual, int ordinal) {
        this.normalized = normalized;
        this.actual = actual;
        this.ordinal = ordinal;
    }

}
