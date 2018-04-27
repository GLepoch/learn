package com.ft.TxManager;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * tx-manager的访问地址设置
 *
 * @author liuyongtao
 * @create 2018-04-27 14:28
 */
@Component
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {


    @Value("${config.public.tx.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
//        System.out.println("config.public.tx.manager.url >>>>>>>>> " + url);
        return url;
    }
}

