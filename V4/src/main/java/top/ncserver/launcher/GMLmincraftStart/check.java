package top.ncserver.launcher.GMLmincraftStart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.Config;
import top.ncserver.launcher.Info;

public class check {
    static Logger logger = LogManager.getLogger(check.class);
    public static String check()
    {    String name = "12";name=Config.JsonConfig.getString("ClientName");
        try
        {

        }catch (NullPointerException e) {
            logger.info(name);
            Info.info(3000, "当前客户端不支持启动", false);
            logger.info("当前客户端不支持启动");
            return "null";
        }
            logger.info(name);
            return name;


    }

}
