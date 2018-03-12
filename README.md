# spring-boot-starter-elastic-job-lite
spring boot starter for Elastic-Job

1、add pom

        <dependency>
        
            <groupId>cn.itshaw</groupId>
            
            <artifactId>spring-boot-starter-elastic-job-lite</artifactId>
            
            <version>${spring-boot-starter-elastic-job-lite.version}</version>
            
        </dependency>
  
2、regsiter center config


    elaticjob:

      zookeeper:
  
        server-lists: ip1:2181,ip2:2181,ip3:2181
    
        namespace: elastic-job-lite-demo
    
        baseSleepTimeMilliseconds:      ##The initial value of the interval of time waiting for retrial (milliseconds)
    
        maxSleepTimeMilliseconds:       ##The maximum of the interval of time waiting for retrial (milliseconds)
    
        maxRetries:                     ##max retries
        
        sessionTimeoutMilliseconds:     ##Session timeout time  (milliseconds)
        
        connectionTimeoutMilliseconds:  ##Connection timeout time  (milliseconds)
        
        digest:                         ##Permission token to connect to Zookeeper. Default is not required to verify permission
        
  
3、cread job classes

SimpleJob Demo：
       
        @ElasticJobLite(cron="0 * * * * ?",shardingTotalCount = 3,shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou")
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

DataflowJob Demo：

        @ElasticJobLite(cron="0 * * * * ?",shardingTotalCount = 3,shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou")
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
        
4、@ElasticJobLite config lists


    value 作业启动时间的cron表达式(优先使用cron配置);
  
    cron 作业启动时间的cron表达式;

    shardingTotalCount 作业分片总数(默认为1);

    shardingItemParameters 分片序列号和个性化参数对照表;

    dataSource 作业数据源;

    disabled 设置作业是否启动时禁止;

    failover 是否开启失效转移,只有对monitorExecution的情况下才可以开启失效转移（默认为false）;

    monitorExecution 设置监控作业执行时状态（默认true）; 每次作业执行时间和间隔时间均非常短的情况, 建议不监控作业运行时状态以提升效率, 因为是瞬时        状态, 所以无必要监控. 请用户自行增加数据堆积监控. 并且不能保证数据重复选取, 应在作业中实现幂等性. 也无法实现作业失效转移.
       每次作业执行时间和间隔时间均较长短的情况, 建议监控作业运行时状态, 可保证数据不会重复选取.
    
    overwritedefault  设置本地配置是否可覆盖注册中心配置（默认为false）;如果可覆盖, 每次启动作业都以本地配置为准.

    jobName 作业名称（默认为任务的全类名）;

    streamingProcess 作业是否流式处理（只对Dataflowjob类型有效）（默认为true

    maxTimeDiffSeconds   设置最大容忍的本机与注册中心的时间误差秒数（默认为-1);如果时间误差超过配置秒数则作业启动时将抛异常.配置为-1表示不检查时间     误差.

    monitorPort 设置作业辅助监控端口(默认为-1）;配置为-1表示不配置监控端口.

    reconcileIntervalMinutes 设置修复作业服务器不一致状态服务执行间隔分钟数(默认为10分钟), 每隔一段时间监视作业服务器的状态，如果不正确则重新分片.

    jobShardingStrategyClass 设置作业分片策略实现类全路径（默认为com.dangdang.ddframe.job.plugin.sharding.strategy.AverageAllocationJobShardingStrategy）
        
        

Ref:

   http://www.infoq.com/cn/articles/dangdang-distributed-work-framework-elastic-job
        
   https://github.com/elasticjob/elastic-job-lite
