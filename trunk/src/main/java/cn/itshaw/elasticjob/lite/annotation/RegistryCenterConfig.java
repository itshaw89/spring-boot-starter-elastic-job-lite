/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package cn.itshaw.elasticjob.lite.annotation;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class RegistryCenterConfig {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${regCenter.serverList}") final String serverList, @Value("${regCenter.namespace}") final String namespace,
                                             @Value("${regCenter.baseSleepTimeMilliseconds}") final Integer baseSleepTimeMilliseconds, @Value("${regCenter.maxSleepTimeMilliseconds}") final Integer maxSleepTimeMilliseconds,
                                             @Value("${regCenter.maxRetries}") final Integer maxRetries
    ) {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverList, namespace);
        if (baseSleepTimeMilliseconds != null) zookeeperConfiguration.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
        if (maxSleepTimeMilliseconds != null) zookeeperConfiguration.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
        if (maxRetries != null) zookeeperConfiguration.setMaxRetries(maxRetries);
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}
