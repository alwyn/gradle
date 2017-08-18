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

package org.gradle.internal.sources;

import org.gradle.api.Action;
import org.gradle.api.artifacts.DependencySubstitutions;
import org.gradle.api.initialization.ConfigurableIncludedBuild;
import org.gradle.api.initialization.sources.ScmBuild;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.SettingsInternal;
import org.gradle.api.internal.file.delete.Deleter;
import org.gradle.initialization.SettingsLoader;
import org.gradle.process.internal.ExecActionFactory;

public class ScmBuildSettingsLoader implements SettingsLoader {
    private final SettingsLoader delegate;
    private final ScmLifecycleHandler scmLifecycleHandler;

    public ScmBuildSettingsLoader(SettingsLoader delegate, ExecActionFactory execActionFactory, Deleter deleter) {
        this.delegate = delegate;
        this.scmLifecycleHandler = new DefaultScmLifecycleHandler(execActionFactory, deleter);
    }

    @Override
    public SettingsInternal findAndLoadSettings(GradleInternal gradle) {
        SettingsInternal settings = delegate.findAndLoadSettings(gradle);
        // Go checkout SCM projects
        for (final ScmBuild scmBuild : settings.getScmBuilds()) {
            ScmCheckout checkout = scmLifecycleHandler.initialize(gradle.getGradleUserHomeDir(), scmBuild);
            settings.includeBuild(checkout.getWorkingDir(), new Action<ConfigurableIncludedBuild>() {
                @Override
                public void execute(ConfigurableIncludedBuild configurableIncludedBuild) {
                    for (Action<? super DependencySubstitutions> action : scmBuild.getSubstitutions()) {
                        configurableIncludedBuild.dependencySubstitution(action);
                    }
                }
            });
        }
        return settings;
    }
}
