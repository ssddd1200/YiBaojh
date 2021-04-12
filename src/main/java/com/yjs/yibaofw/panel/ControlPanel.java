package com.yjs.yibaofw.panel;

import net.sf.image4j.codec.ico.ICODecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ControlPanel extends JFrame {

    public ControlPanel(){}

    public void drawTray() throws AWTException{
        //判断当前系统支不支持系统托盘
        if(SystemTray.isSupported()){
            List<BufferedImage> images = null;
            ImageIcon icon = null;
            try {
                InputStream is = this.getClass().getResource("/images/GOVERM.ico").openStream();
                images = ICODecoder.read(is);
                if (images != null){
                    icon = new ImageIcon(images.get(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //创建弹出式菜单
            PopupMenu pop = new PopupMenu();

            MenuItem item = new MenuItem("退出");
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });

            MenuItem item1 = new MenuItem("医保日志");
            item1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new LogPanel();
                }
            });

            pop.add(item1);
            pop.add(item);
            TrayIcon trayIcon = new TrayIcon(icon.getImage(),"医保通讯服务", pop);

            trayIcon.setImageAutoSize(true);
            SystemTray tray = SystemTray.getSystemTray();
            tray.add(trayIcon);

        }
    }
}
