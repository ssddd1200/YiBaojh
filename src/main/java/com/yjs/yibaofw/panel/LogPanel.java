package com.yjs.yibaofw.panel;

import com.yjs.yibaofw.entity.DBModelPO;
import com.yjs.yibaofw.entity.ReturnPageVO;
import com.yjs.yibaofw.entity.ReturnVO;
import com.yjs.yibaofw.helper.SQLiteConfigHelper;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class LogPanel extends JFrame {

    final private JXDatePicker riqi1 = new JXDatePicker();
    final private JXDatePicker riqi2 = new JXDatePicker();
    // 确认按钮
    private JButton sure;
    // Log 日志结果
    private JScrollPane scroll;
    private JTextArea logs;

    public LogPanel(){
        sure = new JButton();
        scroll = new JScrollPane();
        logs = new JTextArea();

        // 获取屏幕大小
        Dimension screanSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screanWidth = (int) screanSize.getWidth();
        int screanHeight = (int) screanSize.getHeight();
        setBounds((screanWidth - 1200)/2,(screanHeight - 600)/2,1200,600);

        //设置布局 BorderLayout
        setLayout(new BorderLayout());
        setTitle("医保日志查询");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label1 = new JLabel("查询日期：");
        label1.setFont(new Font("宋体", Font.PLAIN, 14));

        riqi1.setDate(new Date());
        riqi1.setTimeZone(TimeZone.getDefault());
        riqi1.setFormats("yyyyMMdd");
        riqi1.setSize(new Dimension(200,100));

        riqi2.setDate(new Date());
        riqi2.setTimeZone(TimeZone.getDefault());
        riqi2.setFormats("yyyyMMdd");
        riqi2.setSize(new Dimension(200,100));


        sure.setText("查询日志");
        sure.setFont(new Font("宋体", Font.PLAIN, 14));
        sure.setPreferredSize(new Dimension(100,35));
        sure.setBorder(BorderFactory.createLineBorder(new Color(255,255,255),2));
        sure.setBackground(new Color(39, 154, 177));
        sure.setForeground(Color.white);
        sure.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String r1 = new SimpleDateFormat("yyyyMMdd").format(riqi1.getDate());
                String r2 = new SimpleDateFormat("yyyyMMdd").format(riqi2.getDate());
                logs.setText("");
                try {
                    ReturnPageVO vo = SQLiteConfigHelper.getsLogInfo(r1, r2, 1, 99);
                    if (vo.getAppcode().equals("0")){
                        List<DBModelPO> l = (List<DBModelPO>) vo.getResultlist();
                        for (int i = 0;i<l.size();i++){
                            String result = String.format("%s  %s --- [%s]，体检交易结果：%s\n", formateDate(l.get(i).getTianbiaorq()), formateDate2(l.get(i).getTianbiaosj()),l.get(i).getPostIP(),l.get(i).getResult());
                            logs.append(result);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        panel.add(label1);
        panel.add(riqi1);
        panel.add(riqi2);
        panel.add(sure);

        // 组件之间的距离
        FlowLayout f = (FlowLayout) panel.getLayout();
        f.setHgap(10);

        scroll.setPreferredSize(new Dimension(400,400));
        scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        scroll.setViewportView(logs);
        scroll.getVerticalScrollBar().setUI(new ScrollBarUI());

        logs.setFont(new Font("宋体", Font.PLAIN, 14));
        logs.setLineWrap(true);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }

    private String formateDate(String riqi){
        return riqi.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
    }

    private String formateDate2(String riqi){
        return riqi.replaceAll("(\\d{2})(\\d{2})(\\d{2})", "$1:$2:$3");
    }
}
