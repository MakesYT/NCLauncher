package cn.silently9527.download.download;

import cn.silently9527.download.ext.FileResponseExtractor;
import cn.silently9527.download.support.DownloadProgressPrinter;
import javafx.application.Platform;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.IOException;

public class FileDownloader extends AbstractDownloader {
    public FileDownloader(DownloadProgressPrinter downloadProgressPrinter) {
        super(downloadProgressPrinter);
    }

    public FileDownloader() {
        super(DownloadProgressPrinter.defaultDownloadProgressPrinter());
    }

    @Override
    protected void doDownload(String fileURL, String dir, String fileName, HttpHeaders headers) throws IOException {
        try{
            if (dir.length()<=1)
            {
                File directory = new File("");
                dir=directory.getCanonicalPath()+"/"+dir;
            }
            String filePath = dir + File.separator + fileName;
            FileResponseExtractor extractor = new FileResponseExtractor(filePath + ".download", downloadProgressPrinter); //创建临时下载文件
            File tmpFile = restTemplate.execute(fileURL, HttpMethod.GET, null, extractor);
            tmpFile.renameTo(new File(filePath)); //修改临时下载文件名称
        }catch (IOException e)
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                   // Info.error(FileDownloader.class,"文件:"+fileName+"下载失败",e);
                }
            });

        }

    }

}
