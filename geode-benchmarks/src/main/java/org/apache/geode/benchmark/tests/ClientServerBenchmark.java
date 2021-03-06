/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.geode.benchmark.tests;

import static org.apache.geode.benchmark.topology.ClientServerTopologyWithRouterAndSniProxy.WITH_ROUTER_PROPERTY;
import static org.apache.geode.benchmark.topology.ClientServerTopologyWithSniProxy.WITH_SNI_PROXY_PROPERTY;

import java.util.Properties;

import org.apache.geode.benchmark.topology.ClientServerTopology;
import org.apache.geode.benchmark.topology.ClientServerTopologyWithRouterAndSniProxy;
import org.apache.geode.benchmark.topology.ClientServerTopologyWithSniProxy;
import org.apache.geode.perftest.TestConfig;

public class ClientServerBenchmark extends GeodeBenchmark {
  public static TestConfig createConfig() {
    final TestConfig config = GeodeBenchmark.createConfig();

    final Properties properties = System.getProperties();
    if (properties.containsKey(WITH_ROUTER_PROPERTY)) {
      ClientServerTopologyWithRouterAndSniProxy.configure(config);
    } else if (properties.containsKey(WITH_SNI_PROXY_PROPERTY)) {
      ClientServerTopologyWithSniProxy.configure(config);
    } else {
      ClientServerTopology.configure(config);
    }

    return config;
  }

}
