/*
 * Copyright 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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
 *
 */

package com.netflix.spinnaker.halyard.core.error.v1;

import com.netflix.spinnaker.halyard.core.problem.v1.Problem;
import com.netflix.spinnaker.halyard.core.problem.v1.ProblemSet;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the exception class that needs to be thrown by all validators.
 */
public class HalException extends RuntimeException {
  @Getter
  protected ProblemSet problems = new ProblemSet();

  @Getter
  private int responseCode = HttpServletResponse.SC_CONFLICT;

  public HalException() {
    super();
  }

  public HalException(Problem problem) {
    super();
    this.problems = new ProblemSet(new ArrayList<>(Collections.singletonList(problem)));
  }

  public HalException(List<Problem> problems) {
    super();
    this.problems = new ProblemSet(problems);
  }
}
