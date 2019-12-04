package org.youi.datacenter.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.youi.framework.context.ModulesRunnerBuilder;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={org.youi.framework.jpa.ModuleConfig.class,
        ModuleConfig.class,
        TestConfig.class})
public class BatchRunnerTest {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JobLauncher jobLauncher;

    @BeforeClass
    public static void setup(){
        new ModulesRunnerBuilder(ModuleConfig.class);
    }
//
    @Test
    public void testBatchRunner() throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{
                        "classpath:META-INF/job/job-config.xml",
                        "classpath:META-INF/job/job-youi_user.xml"
                });

        Job job = context.getBean("odsToDwjob", Job.class);
        try {
            Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
            //根据参数的不同生成不同的运行实例
            parameters.put("random", new JobParameter((long) (Math.random() * 1000000)));
            JobParameters jobParameters = new JobParameters(parameters);
            //启动任务
            JobExecution jobExecution = jobLauncher.run(job,jobParameters);
            //监听状态
            while (jobExecution.isRunning()){
                Thread.sleep(1000);
                logger.info("status: "+jobExecution.getStatus().name());
            }
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        //关闭上下文
        context.close();
    }
}