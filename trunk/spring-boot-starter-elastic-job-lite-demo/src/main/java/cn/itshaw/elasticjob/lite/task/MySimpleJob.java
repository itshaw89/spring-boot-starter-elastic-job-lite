package cn.itshaw.elasticjob.lite.task;

import cn.itshaw.elasticjob.lite.annotation.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

@ElasticSimpleJob(cron = "0 * * * * ?",shardingTotalCount = 3,shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou")
@Component
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                break;
            case 1:
                // do something by sharding item 1
                break;
            case 2:
                // do something by sharding item 2
                break;
            // case n: ...
        }
    }
}
