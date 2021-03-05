package top.ncserver.launcher;

import cn.silently9527.download.download.Downloader;
import cn.silently9527.download.download.FileDownloader;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author MakesYT
 */
public class Download {
    static Logger logger = LogManager.getLogger(Download.class);
    public static void downloadFile(String fileURL,String savePath)
    {
        logger.info("开始下载:"+fileURL+"的文件,保存到:"+savePath);
        Thread thread = new Thread(() -> {
            Platform.runLater(progressBar::progressBar);
        });
        thread.setName("下载进度条");
        thread.start();
        Downloader downloader = new FileDownloader();
        try {
            downloader.download(fileURL, savePath);
            AutoReporter.need=true;
            Platform.runLater(() -> {
                progressBar.stage.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("下载完成");
    }

    /*

        */
}
