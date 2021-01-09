package top.ncserver.selfupdate;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;

/**
 * @author MakesYT
 */
public class update {
    static Logger logger= LogManager.getLogger(update.class);
    public static JFrame alwaysOnTop =new JFrame("AlwaysOnTop");
    public static void main(String[] args) throws IOException {
        SecondDetection.init();
        if (!SecondDetection.JsonConfig.getString("LauncherVersion").equals(SecondDetection.RemoteJsonConfig.getString("LauncherVersion")))
        {
            logger.info("确认需要更新,版本:"+SecondDetection.RemoteJsonConfig.getString("LauncherVersion"));
            Download.httpDownload("Ncharge.jar","http://download.ncserver.top:8000/update/V4","");
            SecondDetection.JsonConfig.replace("LauncherVersion",SecondDetection.RemoteJsonConfig.getString("LauncherVersion"));
            SecondDetection.saveConfig();
            logger.info("更新完成");
            Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k java -jar "+System.getProperty("user.dir")+"\\Ncharge.jar");
            System.exit(1);
        }else
        {
            logger.info("无需更新");
            JOptionPane.showMessageDialog(alwaysOnTop, "不知道你为何会触发这个程序", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
