package cn.silently9527.download.support;

import top.ncserver.launcher.Download;
import top.ncserver.launcher.progressBar;

public class SimpleDownloadProgressPrinter implements DownloadProgressPrinter {
    private long contentLength;
    private long alreadyDownloadLength;

    @Override
    public void print(String task, long contentLength, long alreadyDownloadLength, long speed) {
        this.contentLength = contentLength;
        this.alreadyDownloadLength = alreadyDownloadLength;
        Download.contentLength = contentLength;
        Download.alreadyDownloadLength=alreadyDownloadLength;
        Download.speed=speed;
        //System.out.println(alreadyDownloadLength);
       // System.out.println(task + " 文件总大小: " + contentLength + "KB, 已下载："
          //      + (alreadyDownloadLength / 1024) + "KB, 下载速度：" + (speed / 1000) + "KB");

    }

    @Override
    public long getContentLength() {
        return this.contentLength;
    }

    @Override
    public long getAlreadyDownloadLength() {
        return this.alreadyDownloadLength;
    }
}
