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

package com.netflix.spinnaker.halyard.deploy.spinnaker.v1.service;


import com.netflix.spinnaker.halyard.config.model.v1.security.Security;
import com.netflix.spinnaker.halyard.deploy.spinnaker.v1.SpinnakerArtifact;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeckService extends SpinnakerPublicService<DeckService.Deck> {
  int port = 9000;
  // Address is how the service is looked up.
  String address = "localhost";
  String publicAddress;
  // Host is what's bound to by the service.
  String host = "0.0.0.0";
  String protocol = "http";
  String httpHealth = null;
  String name = "deck";

  @Override
  public SpinnakerArtifact getArtifact() {
    return SpinnakerArtifact.DECK;
  }

  @Override
  public Class<Deck> getEndpointClass() {
    return Deck.class;
  }

  public DeckService() {
    super();
  }

  public DeckService(Security security, String domain) {
    super(security, domain);
    publicAddress = security.getUiAddress();
  }

  public interface Deck { }
}
