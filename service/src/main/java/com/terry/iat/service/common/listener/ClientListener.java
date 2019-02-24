package com.terry.iat.service.common.listener;

import com.terry.iat.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @author terry          
 * @Date 2019/2/24 10:34
 * @Param 
 * @return 
 **/
@Component
public class ClientListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ClientService clientService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            clientService.initClientCache();
        }
    }
}
