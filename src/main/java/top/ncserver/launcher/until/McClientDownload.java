package top.ncserver.launcher.until;

import javafx.application.Platform;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.*;
import top.ncserver.launcher.dataStorage.Client;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author MakesYT
 */
public class McClientDownload implements Runnable{
    static Logger logger = LogManager.getLogger(McClientDownload.class);
    public  McClientDownload() throws IOException {

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
            File data=new File("data");
            for (File file : Objects.requireNonNull(data.listFiles())) {

            if (FilenameUtils.getExtension(file.getName()).equals("clt")){
                file.delete();
            }}
            if (!INIT.clientList.isEmpty())
                INIT.clientList.remove();
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
        logger.info("开始存储客户端配置文件");
        try {
            Client c=new Client(getClientName());
            c.saveToFile();
            INIT.clientList.add(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("存储客户端配置文件完成");
    }
    public static String getClientVersion() throws IOException {
        return checkUpdate.RemoteJsonConfig.getString("ClientVersion");
    }
    public static String getClientName() throws IOException {
        return checkUpdate.RemoteJsonConfig.getString("ClientName");
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
