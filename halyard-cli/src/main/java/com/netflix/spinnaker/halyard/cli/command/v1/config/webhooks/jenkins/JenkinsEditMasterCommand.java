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

package com.netflix.spinnaker.halyard.cli.command.v1.config.webhooks.jenkins;

import com.beust.jcommander.Parameter;
import com.netflix.spinnaker.halyard.cli.command.v1.config.webhooks.master.AbstractEditMasterCommand;
import com.netflix.spinnaker.halyard.config.model.v1.node.Master;
import com.netflix.spinnaker.halyard.config.model.v1.webhooks.jenkins.JenkinsMaster;

public class JenkinsEditMasterCommand extends AbstractEditMasterCommand<JenkinsMaster> {
  protected String getWebhookName() {
    return "jenkins";
  }

  @Parameter(
      names = "--address",
      description = JenkinsCommandProperties.ADDRESS_DESCRIPTION
  )
  private String address;

  @Parameter(
      names = "--username",
      description = JenkinsCommandProperties.USERNAME_DESCRIPTION
  )
  public String username;

  @Parameter(
      names = "--password",
      password = true,
      description = JenkinsCommandProperties.PASSWORD_DESCRIPTION
  )
  public String password;

  @Override
  protected Master editMaster(JenkinsMaster master) {
    master.setAddress(isSet(address) ? address : master.getAddress());
    master.setUsername(isSet(username) ? username : master.getUsername());
    master.setPassword(isSet(password) ? password : master.getPassword());

    return master;
  }

}
