package top.ncserver.launcher;

import cn.silently9527.download.download.Downloader;
import cn.silently9527.download.download.FileDownloader;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.until.FormatFileFize;
import top.ncserver.launcher.until.Ui;

import java.io.IOException;

/**
 * @author MakesYT
 */
public class Download {
    public static long contentLength;
    public static long alreadyDownloadLength;
    public static long speed;
    static Logger logger = LogManager.getLogger(Download.class);
    public static void downloadFile(String fileURL,String savePath) throws IOException {
        logger.info("开始下载:"+fileURL+"的文件,保存到:"+savePath);
        Thread thread = new Thread(() -> {
            while (true)
            {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String formattedContentLength= FormatFileFize.getPrintSize(contentLength*1024);
                String formattedAlreadyDownloadLength=FormatFileFize.getPrintSize(alreadyDownloadLength);
                String formattedSpeed=FormatFileFize.getPrintSize(speed);

                Platform.runLater(() -> {
                    Ui.progress.setText(formattedAlreadyDownloadLength+"/"+formattedContentLength+",速度:"+formattedSpeed+"/s");
                });

            }
            });

        thread.setName("下载进度条");
        thread.start();
        Downloader downloader = new FileDownloader();

            downloader.download(fileURL, savePath);
            thread.stop();
            Platform.runLater(() -> {
                Ui.progress.setText("");
            });


        logger.info("下载完成");
    }

    /*

        */
}
