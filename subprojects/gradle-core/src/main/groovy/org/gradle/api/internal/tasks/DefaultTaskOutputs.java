/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.api.internal.tasks;

import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.internal.file.PathResolvingFileCollection;
import org.gradle.api.tasks.TaskOutputs;

public class DefaultTaskOutputs implements TaskOutputs {
    private final PathResolvingFileCollection outputFiles;

    public DefaultTaskOutputs(FileResolver resolver) {
        outputFiles = new PathResolvingFileCollection(resolver, null);
    }

    public boolean getHasOutputFiles() {
        return !outputFiles.getSources().isEmpty();
    }

    public FileCollection getOutputFiles() {
        return outputFiles;
    }

    public TaskOutputs outputFiles(Object... paths) {
        outputFiles.from(paths);
        return this;
    }
}
