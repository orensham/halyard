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

package com.netflix.spinnaker.halyard.cli.command.v1.config.providers.bakery;

import com.netflix.spinnaker.halyard.cli.services.v1.Daemon;
import com.netflix.spinnaker.halyard.cli.services.v1.OperationHandler;
import com.netflix.spinnaker.halyard.cli.ui.v1.AnsiFormatUtils;
import com.netflix.spinnaker.halyard.config.model.v1.node.BaseImage;
import lombok.Getter;

abstract class AbstractGetBaseImageCommand extends AbstractHasBaseImageCommand {
  public String getDescription() {
    return "Get the specified base image details for the " + getProviderName() + " provider.";
  }

  @Getter
  private String commandName = "get";

  @Override
  protected void executeThis() {
    String currentDeployment = getCurrentDeployment();
    String providerName = getProviderName();
    String baseImageId = getBaseImageId();
    new OperationHandler<BaseImage>()
        .setFailureMesssage("Failed to get base image " + baseImageId + " in" + providerName + "'s bakery.")
        .setSuccessMessage("Settings for " + baseImageId + " in" + providerName + "'s bakery:")
        .setFormat(AnsiFormatUtils.Format.STRING)
        .setOperation(Daemon.getBaseImage(currentDeployment, providerName, baseImageId, false))
        .get();
  }
}
