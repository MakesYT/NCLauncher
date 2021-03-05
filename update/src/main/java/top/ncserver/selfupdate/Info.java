package top.ncserver.selfupdate;

import javax.swing.*;
import java.awt.*;

public class Info implements Runnable{
    static String msg;
    static JFrame info=new JFrame();
    static JTextField msgText= new JTextField(msg);
    public static int type=0;
    public static void init()
    {
        info.setLocationRelativeTo(null);
        info.setSize(500,50);
        info.setLocationRelativeTo(info.getOwner());
        info.setUndecorated(true);
        info.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        info.getContentPane().setBackground(Color.gray);
        info.setAlwaysOnTop(true);
       // com.sun.awt.AWTUtilities.setWindowOpacity(info, 0.95F);
        //main.setShape(new RoundRectangle2D.Double(0, 0, main.getWidth(), main.getHeight(), 50, 50));
        info.setResizable(false);
        Font font_ = new Font("宋体",Font.BOLD,30);
        msgText.setFont(font_);
        msgText.setForeground(Color.white);
        msgText.setBounds(0,0,500,50);
        msgText.setBorder(null);
        msgText.setOpaque(false);
        msgText.setHorizontalAlignment(JTextField.CENTER);
        msgText.setEditable(false);
        info.add(msgText);
    }
    public static void setVisible(boolean bool)
    {
        info.setVisible(bool);
    }
    public Info(String m)
    {
        msgText.setText(m);
    }

    @Override
    public void run() {
        info.setVisible(true);
        if (type==0)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            info.setVisible(false);
        }
        type=0;

    }
}
