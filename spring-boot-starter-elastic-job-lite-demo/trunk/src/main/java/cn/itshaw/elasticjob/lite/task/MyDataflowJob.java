package cn.itshaw.elasticjob.lite.task;

import cn.itshaw.elasticjob.lite.annotation.ElasticDataflowJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ElasticDataflowJob(cron="0 * * * * ?",shardingTotalCount = 3,shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou")
@Component
public class MyDataflowJob implements DataflowJob {

    @Override
    public List fetchData(ShardingContext shardingContext) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW FETCH"));
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List data) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW PROCESS"));

    }
}
