package top.ncserver.launcher.until;

import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.*;

import javax.swing.*;
import java.io.IOException;

public class McCheck {
    static Logger logger = LogManager.getLogger(McCheck.class);
    public static  void check(){
        try {if(!INIT.clientList.getFirst().getVersion().equals(checkUpdate.RemoteJsonConfig.getString("ClientVersion")))
        {
            Platform.runLater(() -> {
                Info.info(0,"正在更新客户端",true);
            });
            logger.info("开始下载客户端更新包:"+INIT.clientList.getFirst().getVersion()+"_to_"+checkUpdate.RemoteJsonConfig.getString("ClientVersion")+".zip");
            try {
                Download.downloadFile("http://download.ncserver.top:8000/update/C/"+INIT.clientList.getFirst().getVersion()+"_to_"+checkUpdate.RemoteJsonConfig.getString("ClientVersion")+".zip","");
            } catch (IOException e) {
                Platform.runLater(Info::infoClose);
                logger.info("未找到更新包，开始完整下载");
                logger.info("开始下载/修复客户端");
                McClientDownload startDownload= null;
                try {
                    startDownload = new McClientDownload();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                new Thread(startDownload).start();
                logger.info("进程已启动");
            }
            logger.info("下载完成");
            try {
                zip.decompressZip(System.getProperty("user.dir") + "/" +INIT.clientList.getFirst().getVersion()+"_to_"+checkUpdate.RemoteJsonConfig.getString("ClientVersion") + ".zip",System.getProperty("user.dir")+"/");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            INIT.clientList.getFirst().setVersion(checkUpdate.RemoteJsonConfig.getString("ClientVersion"));
            try {
                INIT.clientList.getFirst().saveToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else logger.info("客户端无需更新");

        } catch (NullPointerException e) {
            logger.error("客户端错误");
            Info.info(3000,"无法检查更新，客户端文件非最新版本");
        }

    }
}
