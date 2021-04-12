package com.yjs.yibaofw.listener;

import com.yjs.yibaofw.panel.ControlPanel;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.awt.*;

public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        ControlPanel panel = new ControlPanel();

        try {
            panel.drawTray();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
