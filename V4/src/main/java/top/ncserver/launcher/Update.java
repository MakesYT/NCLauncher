package top.ncserver.launcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * @author MakesYT
 */

public class Update {
    static Logger logger= LogManager.getLogger(Update.class);
    public static void checker() throws IOException {
        logger.info("开始检查更新..");
        if (!Config.JsonConfig.getString("LauncherVersion").equals(Config.RemoteJsonConfig.getString("LauncherVersion")))
        {
            logger.info("检测到更新,开始更新..");
            File update = new File("update.jar");
            update.delete();
            logger.info("开始获取更新器....");
            Download.downloadFile("http://download.ncserver.top:8000/update/V4/update.jar","");
            logger.info("下载更新器完成");
            logger.info("开始更新..");
            Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k java -jar "+System.getProperty("user.dir")+"\\update.jar");
            System.exit(1);
        }else
        {
            logger.info("无需更新,跳过");
        }

    }

}
