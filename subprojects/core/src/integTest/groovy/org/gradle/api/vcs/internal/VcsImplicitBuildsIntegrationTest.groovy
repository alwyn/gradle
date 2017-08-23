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

package org.gradle.api.vcs.internal

import org.gradle.integtests.fixtures.AbstractIntegrationSpec

class VcsImplicitBuildsIntegrationTest extends AbstractIntegrationSpec {
    def "can implicitly include builds from VCS"() {
        buildTestFixture.withBuildInSubDir()
        singleProjectBuild("vcs-build") {
            buildFile << "apply plugin: 'java'"
        }
        settingsFile << """
            sourceControl {
                repositories {
                    vcsBuild(VcsRepository) {
                        dir = file("vcs-build")
                    }
                }
                vcsMappings {
                    add("vcsBuild", maven("org.test", "vcs-build"))
                }
            }
        """
        buildFile << """
            apply plugin: 'java'
            
            dependencies {
                compile 'org.test:vcs-build:1.0'
            }
        """
        file("src/main/java/Foobar.java") << """
            public class Foobar {
                private Dummy dummy = null;
            }
        """

        expect:
        succeeds("assemble")
    }
}
