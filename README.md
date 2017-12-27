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
  
        server-lists: test-zookeeper01.biostime.it:2181,test-zookeeper02.biostime.it:2181,test-zookeeper03.biostime.it:2181
    
        namespace: elastic-job-lite-demo
    
        baseSleepTimeMilliseconds:      ##等待重试的间隔时间的初始值(毫秒)
    
        maxSleepTimeMilliseconds:       ##等待重试的间隔时间的最大值(毫秒)
    
        maxRetries:           ##最大重试次数
  
3、cread job classes

SimpleJob Demo：
        @ElasticSimpleJob("0 * * * * ?")
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

        @ElasticDataflowJob("0 * * * * ?")
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
