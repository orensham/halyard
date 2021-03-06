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
 */

package com.netflix.spinnaker.halyard.deploy.deployment.v1;

import com.netflix.spinnaker.halyard.config.model.v1.node.Account;
import com.netflix.spinnaker.halyard.config.model.v1.node.DeploymentConfiguration;
import com.netflix.spinnaker.halyard.config.model.v1.node.DeploymentEnvironment;
import com.netflix.spinnaker.halyard.config.model.v1.node.Provider;
import com.netflix.spinnaker.halyard.config.problem.v1.ConfigProblemBuilder;
import com.netflix.spinnaker.halyard.config.services.v1.AccountService;
import com.netflix.spinnaker.halyard.core.error.v1.HalException;
import com.netflix.spinnaker.halyard.core.problem.v1.Problem;
import com.netflix.spinnaker.halyard.deploy.spinnaker.v1.SpinnakerEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndpointFactory {
  @Autowired
  AccountService accountService;

  public SpinnakerEndpoints create(DeploymentConfiguration deploymentConfiguration ) {
    DeploymentEnvironment.DeploymentType type = deploymentConfiguration.getDeploymentEnvironment().getType();
    switch (type) {
      case LocalhostDebian:
        return new SpinnakerEndpoints(deploymentConfiguration.getSecurity());
      case Flotilla:
        return createFlotillaEndpoints(deploymentConfiguration);
      default:
        throw new IllegalArgumentException("Unrecognized deployment type " + type);
    }
  }

  private SpinnakerEndpoints createFlotillaEndpoints(DeploymentConfiguration deploymentConfiguration) {
    DeploymentEnvironment deploymentEnvironment = deploymentConfiguration.getDeploymentEnvironment();
    String accountName = deploymentEnvironment.getAccountName();
    SpinnakerEndpoints endpoints = new SpinnakerEndpoints(deploymentConfiguration.getSecurity());

    if (accountName == null || accountName.isEmpty()) {
      throw new HalException(new ConfigProblemBuilder(Problem.Severity.FATAL, "An account name must be "
          + "specified as the desired place to run your simple clustered deployment.").build());
    }

    Account account = accountService.getAnyProviderAccount(deploymentConfiguration.getName(), accountName);
    Provider.ProviderType providerType = ((Provider) account.getParent()).providerType();

    switch (providerType) {
      case KUBERNETES:
        SpinnakerEndpoints.Services services = endpoints.getServices();

        services.getClouddriver().setAddress("spin-clouddriver.spinnaker");
        services.getClouddriverBootstrap().setAddress("spin-clouddriver-bootstrap.spinnaker");
        services.getDeck().setAddress("spin-deck.spinnaker");
        services.getEcho().setAddress("spin-echo.spinnaker");
        services.getFiat().setAddress("spin-fiat.spinnaker");
        services.getFront50().setAddress("spin-front50.spinnaker");
        services.getGate().setAddress("spin-gate.spinnaker");
        services.getIgor().setAddress("spin-igor.spinnaker");
        services.getOrca().setAddress("spin-orca.spinnaker");
        services.getOrcaBootstrap().setAddress("spin-orca-bootstrap.spinnaker");
        services.getRosco().setAddress("spin-rosco.spinnaker");
        services.getRedis().setAddress("spin-redis.spinnaker");
        services.getRedisBootstrap().setAddress("spin-redis-bootstrap.spinnaker");

        return endpoints;
      default:
        throw new IllegalArgumentException("No Clustered Simple Deployment for " + providerType.getId());
    }
  }
}
