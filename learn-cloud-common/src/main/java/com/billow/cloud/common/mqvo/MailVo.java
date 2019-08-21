package com.billow.cloud.common.mqvo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件
 *
 * @author liuyongtao
 * @create 2019-08-20 17:23
 */
@Data
public class MailVo implements Serializable {

    public MailVo() {
    }

    /**
     * 普通构造器
     *
     * @param toEmails 收件人邮箱，多个邮箱以“;”分隔
     * @param subject  邮件主题
     * @param subject  邮件Code
     */
    public MailVo(String toEmails, String subject, String mailCode) {
        this.toEmails = toEmails;
        this.subject = subject;
        this.mailCode = mailCode;
    }

    //收件人邮箱，多个邮箱以“;”分隔
    private String toEmails;
    //邮件主题
    private String subject;
    // 邮件标识，唯一
    private String mailCode;
    // 参数设置
    private Map<String, String> param = new HashMap<>();
}