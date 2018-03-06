package com.ft.sysEvent;

import com.ft.core.enumType.AutoTaskJobStatusEnum;
import com.ft.core.manager.QuartzManager;
import com.ft.model.expand.ScheduleJobDto;
import com.ft.service.TaskManagerService;
import com.ft.sysEvent.mq.SysEventInterface;
import com.netflix.discovery.converters.Auto;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-02-11 11:38
 */
@RestController
@RequestMapping("/sysEvent")
@EnableBinding(SysEventInterface.class)
public class SysEventController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventInterface sysEventInterface;
    @Autowired
    private TaskManagerService taskManagerService;

    @GetMapping("/sysEventSend")
    public void sysEventSend() {
        String message = "系统事件：测试 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sysEventInterface.outSysEventPublish().send(MessageBuilder.withPayload(message).build());
        logger.info("【MQ发送内容】" + message);
    }

    @GetMapping("/sysQuartz")
    public void sysQuartz() {
        ScheduleJobDto job = new ScheduleJobDto();
        job.setJobId(3);
        job.setJobGroup("test6_group");
        job.setJobName("test6_name");
        job.setCronExpression("0/2 * * * * ?");
        job.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus());
        job.setBeanClass("com.ft.autoTask.sysEvent.SysEventPublishAutoTask");
        try {
            taskManagerService.saveAutoTask(job);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScheduleJobDto job2 = new ScheduleJobDto();
        job2.setJobId(4);
        job2.setJobGroup("test6_group");
        job2.setJobName("test7_name");
        job2.setCronExpression("0/2 * * * * ?");
        job2.setJobStatus(AutoTaskJobStatusEnum.JOB_STATUS_RESUME.getStatus());
        job2.setBeanClass("com.ft.autoTask.sysEvent.SysEventProcessAutoTask");
        try {
            taskManagerService.saveAutoTask(job2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("添加完毕...");
    }
}
