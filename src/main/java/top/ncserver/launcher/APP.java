package top.ncserver.launcher;

import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APP {
    static Logger logger= LogManager.getLogger(APP.class);
    public static void main(String[] args) {
        logger.info("开始初始化...");
        Application.launch(INIT.class);
    }
}
