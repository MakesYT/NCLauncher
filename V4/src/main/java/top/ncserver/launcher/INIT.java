package top.ncserver.launcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.io.File;

/**
 * @author MakesYT
 */
public class INIT {

    public static JFrame alwaysOnTop =new JFrame("AlwaysOnTop");
    static Logger logger=LogManager.getLogger(INIT.class);
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        logger.info("开始初始化...");
        File temp=new File("temp");
        temp.mkdirs();
        Config.loader();
        Update.checker();

    }
}
