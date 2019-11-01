/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.geode.benchmark.tasks;

import java.io.Serializable;
import java.util.Map;

import org.openjdk.jmh.infra.Blackhole;
import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkDriverAdapter;

import org.apache.geode.StatisticDescriptor;
import org.apache.geode.Statistics;
import org.apache.geode.StatisticsType;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.distributed.DistributedSystem;

public class NoopTask extends BenchmarkDriverAdapter implements Serializable {
  private Statistics statistics;
  private  int statisticsId;

  @Override
  public void setUp(BenchmarkConfiguration cfg) throws Exception {
    super.setUp(cfg);

    final ClientCache cache = ClientCacheFactory.getAnyInstance();
    DistributedSystem distributedSystem = cache.getDistributedSystem();
    StatisticDescriptor
        statisticDescriptor = distributedSystem
        .createLongCounter("test", "test", "ops");
    StatisticsType statisticsType = distributedSystem.createType("test", "test", new StatisticDescriptor[]{statisticDescriptor});
    statistics = distributedSystem.createAtomicStatistics(statisticsType);
    statisticsId = statistics.nameToId("test");
  }

  @Override
  public boolean test(Map<Object, Object> ctx) {
    statistics.incLong(statisticsId, 1);
    Blackhole.consumeCPU(5);
    return true;
  }
}