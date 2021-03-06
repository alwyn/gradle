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

package org.gradle.performance.regression.nativeplatform

import org.gradle.performance.AbstractCrossVersionPerformanceTest
import spock.lang.Unroll

class NativeBuildPerformanceTest extends AbstractCrossVersionPerformanceTest {

    def setup() {
        runner.targetVersions = ["4.3-20171011120745+0000"]
    }

    @Unroll
    def "clean assemble on #testProject"() {
        given:
        runner.testProject = testProject
        runner.tasksToRun = ["assemble"]
        runner.cleanTasks = ["clean"]
        runner.gradleOpts = ["-Xms$maxMemory", "-Xmx$maxMemory"]
        runner.runs = iterations
        runner.warmUpRuns = iterations

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()

        where:
        testProject                       | maxMemory | iterations
        "smallNative"                     | '256m'    | 40
        "mediumNative"                    | '256m'    | null
        "bigNative"                       | '1g'      | null
        "multiNative"                     | '256m'    | null
        "smallCppApp"                     | '256m'    | 40
        "mediumCppApp"                    | '256m'    | null
        "mediumCppAppWithMacroIncludes"   | '256m'    | null
        "bigCppApp"                       | '256m'    | null
        "smallCppMulti"                   | '256m'    | 40
        "mediumCppMulti"                  | '256m'    | null
        "mediumCppMultiWithMacroIncludes" | '256m'    | null
        "bigCppMulti"                     | '1g'      | null
    }

    def "clean assemble on manyProjectsNative"() {
        given:
        runner.testProject = "manyProjectsNative"
        runner.tasksToRun = ["assemble"]
        runner.cleanTasks = ["clean"]

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }
}
