package cn.silently9527.download.download;

import cn.silently9527.download.support.DownloadProgressPrinter;
import cn.silently9527.download.utils.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import top.ncserver.launcher.AutoReporter;
import top.ncserver.launcher.progressBar;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

public abstract class AbstractDownloader implements Downloader {
    protected RestTemplate restTemplate;
    protected DownloadProgressPrinter downloadProgressPrinter;

    public AbstractDownloader(DownloadProgressPrinter downloadProgressPrinter) {
        this.restTemplate = RestTemplateBuilder.builder().build();
        this.downloadProgressPrinter = downloadProgressPrinter;
    }

    @Override
    public void download(String fileURL, String dir)  {
        try{
            long start = System.currentTimeMillis();

            String decodeFileURL = URLDecoder.decode(fileURL, "UTF-8");

            //通过Http协议的Head方法获取到文件的总大小
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            ResponseEntity<String> entity = restTemplate.exchange(decodeFileURL, HttpMethod.HEAD, requestEntity, String.class);
            String fileName = this.getFileName(decodeFileURL, entity.getHeaders());
            if (dir.length()<=1)
            {
                File directory = new File("");
                dir=directory.getCanonicalPath()+"/"+dir;
            }
            doDownload(decodeFileURL, dir, fileName, entity.getHeaders());
            //System.out.println("1:"+progressBar.finished);
            progressBar.finished=true;
            //System.out.println("2:"+progressBar.finished);
            //System.out.println("总共下载文件耗时:" + (System.currentTimeMillis() - start) / 1000 + "s");
        }catch (IOException| HttpClientErrorException e)
        {
            AutoReporter.e=e;
            AutoReporter.detection=false;
            AutoReporter.need=true;

        }

    }

    protected abstract void doDownload(String decodeFileURL, String dir, String fileName, HttpHeaders headers) throws IOException;

    /**
     * 获取文件的名称
     *
     * @param fileURL
     * @return
     */
    private String getFileName(String fileURL, HttpHeaders headers) {
        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        if (fileName.contains(".")) {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (suffix.length() > 4 || suffix.contains("?")) {
                fileName = getFileNameFromHeader(headers);
            }
        } else {
            fileName = getFileNameFromHeader(headers);
        }
        return fileName;
    }

    private String getFileNameFromHeader(HttpHeaders headers) {
        String fileName = headers.getContentDisposition().getFilename();
        if (StringUtils.isEmpty(fileName)) {
            return UUID.randomUUID().toString();
        }
        return fileName;
    }

}
