package top.ncserver.launcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            Download.httpDownload("update.jar","http://download.ncserver.top:8000/update/V4","temp");
        }else
        {
            logger.info("无需更新,跳过");
        }

    }

}
