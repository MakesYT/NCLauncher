package cn.silently9527.download.download;

import java.io.IOException;

public interface Downloader {

    void download(String fileURL, String dir) throws IOException;

}
