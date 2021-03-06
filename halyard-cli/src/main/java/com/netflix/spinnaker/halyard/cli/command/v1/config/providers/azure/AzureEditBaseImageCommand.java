/*
 * Copyright 2017 Microsoft, Inc.
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
 */

package com.netflix.spinnaker.halyard.cli.command.v1.config.providers.azure;

import com.beust.jcommander.Parameter;
import com.netflix.spinnaker.halyard.cli.command.v1.config.providers.bakery.AbstractEditBaseImageCommand;
import com.netflix.spinnaker.halyard.config.model.v1.node.BaseImage;
import com.netflix.spinnaker.halyard.config.model.v1.providers.azure.AzureBaseImage;

public class AzureEditBaseImageCommand extends AbstractEditBaseImageCommand<AzureBaseImage> {
  @Override
  protected String getProviderName() {
    return "azure";
  }

  @Parameter(
      names = "--publisher",
      description = AzureCommandProperties.IMAGE_PUBLISHER_DESCRIPTION
  )
  private String publisher;

  @Parameter(
          names = "--offer",
          description = AzureCommandProperties.IMAGE_OFFER_DESCRIPTION
  )
  private String offer;

  @Parameter(
          names = "--sku",
          description = AzureCommandProperties.IMAGE_SKU_DESCRIPTION
  )
  private String sku;

  @Parameter(
          names = "--image-version", // just using '--version' would conflict with the global parameter
          description = AzureCommandProperties.IMAGE_VERSION_DESCRIPTION
  )
  private String version;

  @Override
  protected BaseImage editBaseImage(AzureBaseImage baseImage) {
    AzureBaseImage.AzureOperatingSystemSettings imageSettings = baseImage.getBaseImage();
    imageSettings = imageSettings != null ? imageSettings : new AzureBaseImage.AzureOperatingSystemSettings();
    
    imageSettings.setPublisher(isSet(publisher) ? publisher : imageSettings.getPublisher());
    imageSettings.setOffer(isSet(offer) ? offer : imageSettings.getOffer());
    imageSettings.setSku(isSet(sku) ? sku : imageSettings.getSku());
    imageSettings.setVersion(isSet(version) ? version : imageSettings.getVersion());
    baseImage.setBaseImage(imageSettings);

    return baseImage;
  }
}
