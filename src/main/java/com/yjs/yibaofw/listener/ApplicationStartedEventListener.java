package com.yjs.yibaofw.listener;

import com.yjs.yibaofw.helper.SQLiteConfigHelper;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        SQLiteConfigHelper.createEmptyConfig();
    }
}
