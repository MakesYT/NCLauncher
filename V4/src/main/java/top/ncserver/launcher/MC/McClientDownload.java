package top.ncserver.launcher.MC;

import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author MakesYT
 */
public class McClientDownload implements Runnable{
    static Logger logger = LogManager.getLogger(McClientDownload.class);
    public  McClientDownload()  {

         }
    @Override
    public void run() {
        File client = new File(System.getProperty("user.dir")+"//.minecraft");
        if (client.exists())
        {
            //System.out.println(client);
            logger.info("正在删除旧客户端");
            Platform.runLater(() -> {
                Info.info(0,"正在删除旧客户端",true);
            });
            Config.JsonConfig.replace("ClientVersion","null");
            Config.saveConfig();

            //Info msg=new Info("正在删除旧客户端");
            //new Thread(msg).start();
            deleteAll(client);
            logger.info("客户端删除完成");
            Platform.runLater(Info::infoClose);


            //deleteFile(client);
        }


        try {
            logger.info("开始下载最新客户端:"+getClientVersion()+".zip");
            Download.downloadFile("http://download.ncserver.top:8000/update/C/"+getClientVersion()+".zip","");
            logger.info("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            logger.info("开始解压客户端");
            zip.decompressZip(System.getProperty("user.dir") + "/" + getClientVersion() + ".zip",System.getProperty("user.dir")+"/");
            logger.info("解压客户端成功");
        } catch (IOException | ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            logger.info("开始改写config中的客户端数值");
            Config.JsonConfig.replace("ClientVersion",getClientVersion());

            if (Config.checkValue("ClientName"))
            {
              Config.JsonConfig.put("ClinetName",getClientName()) ;
              Config.saveConfig();
            }
            Config.JsonConfig.replace("ClientName",getClientName());
            Config.saveConfig();
            logger.info("改写config中的客户端数值完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getClientVersion() throws IOException {
        return Config.RemoteJsonConfig.getString("ClientVersion");
    }
    public static String getClientName() throws IOException {
        return Config.RemoteJsonConfig.getString("ClientName");
    }
    public static void deleteAll(File file) {

        if (file.isFile() || Objects.requireNonNull(file.list()).length == 0) {
            if (!file.delete())
            logger.info(file+"文件删除失败");
        } else {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                deleteAll(f); // 递归删除每一个文件

            }
            if (!file.delete())
            logger.info(file+"文件夹删除失败");
        }
    }
}
