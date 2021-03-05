package cn.silently9527.download.download;

import cn.silently9527.download.ext.ByteArrayResponseExtractor;
import cn.silently9527.download.support.DownloadProgressPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import top.ncserver.launcher.Info;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class SimpleDownloader extends AbstractDownloader {

    public SimpleDownloader(DownloadProgressPrinter downloadProgressPrinter) {
        super(downloadProgressPrinter);
    }

    public SimpleDownloader() {
        super(DownloadProgressPrinter.defaultDownloadProgressPrinter());
    }

    @Override
    protected void doDownload(String fileURL, String dir, String fileName, HttpHeaders headers) throws IOException {
        try
        {
            if (dir.length()<=1)
            {
                File directory = new File("");
                dir=directory.getCanonicalPath()+"/"+dir;
            }
            String filePath = dir + File.separator + fileName;
            byte[] body = restTemplate.execute(fileURL, HttpMethod.GET, null,
                    new ByteArrayResponseExtractor(downloadProgressPrinter));
            Files.write(Paths.get(filePath), Objects.requireNonNull(body));
        }catch (IOException e){
            Info.error(FileDownloader.class,"文件:"+fileName+"下载失败",e);
        }


    }

}
