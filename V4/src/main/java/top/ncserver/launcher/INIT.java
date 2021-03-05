package top.ncserver.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
/**
 * @author MakesYT
 */
public class INIT extends Application{

    static Logger logger=LogManager.getLogger(INIT.class);
    public static void main(String[] args) throws IOException {
      Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("开始初始化...");
        File temp = new File("temp");
        temp.mkdirs();
        Config.loader();
        Update.checker();
        //autoLogin.getUser();
        //MC_start.MC_start();
        //Info.infoArea("test","1111111111111111111111");

        if (Config.JsonConfig.getBoolean("autoLogin"))//如果没有开启自动登录
        { logger.info("开始自动登录");
            Login.Login();

        }else {
            logger.info("没有开启自动登录");
            LoginUi.loginUi();

        }

    }
}
