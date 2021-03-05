package cn.silently9527.download;


import cn.silently9527.download.download.Downloader;
import cn.silently9527.download.download.MultiThreadFileDownloader;
import cn.silently9527.download.support.MultiThreadDownloadProgressPrinter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FastDownloadApplication {


    public static void main(String[] args) throws IOException {

        String fileURL = "https://download.jetbrains.8686c.com/idea/ideaIU-2020.3.dm1g";
//        String fileURL = "http://img.doutula.com/production/uploads/image/2017/10/19/20171019627498_uQtkcl.jpg";
//        String fileURL = "https://downloads.gradle-dn.com/distributions/gradle-6.7.1-bin.zip";
//        String fileURL = "https://qdall01.baidupcs.com/file/247e2485451c71a822da9322e60017d7?bkt=en-6f7dc9883530f8c91c65d3adfc2e2dbf5550692ffcccd79fc9c9ef060b82b490f8f31abfa33936827998e228ace1221cbafcfa1d55fd5a26c525dc067a1ff132&fid=1125129068-250528-36201672963011&time=1609051986&sign=FDTAXUGERLQlBHSKfWqi-DCb740ccc5511e5e8fedcff06b081203-gstB/JbOhvpqXUIxa2LBofScuxw=&to=92&size=48391950&sta_dx=48391950&sta_cs=16877&sta_ft=pdf&sta_ct=7&sta_mt=7&fm2=MH,Qingdao,Anywhere,,sichuan,ct&ctime=1460442229&mtime=1460442229&resv0=-1&resv1=0&resv2=rlim&resv3=5&resv4=48391950&vuk=1125129068&iv=-2&htype=&randtype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=en-54e79ac697f941b14e1c4b130c5946430be2bf0b51eeb8cba93d4e2e6f54dbdd5fbe5d3ab3b9a3742251a570cdc7df65f4ad4a1cf980582a305a5e1275657320&sl=80740430&expires=8h&rt=pr&r=513317955&vbdid=2772727088&fin=精通Spring(清晰书签版).pdf&fn=精通Spring(清晰书签版).pdf&rtype=1&dp-logid=8386993395993523035&dp-callid=0.1&hps=1&tsl=15&csl=78&fsl=-1&csign=aVjjun9SLC84bAhDk0fPRT1byu4=&so=0&ut=8&uter=4&serv=0&uc=3280231489&ti=497b2742088ef3a3b3f50543554178d0ee1147b897d856c4&hflag=30&adg=c_26fc4f6159ca9bace5d32fb47d377ef9&reqlabel=250528_f_8abc2560c1cc516dddaf004143d444f0_-1_6dd3a59f3dd2d9780f54b6b05c7df4e9&by=themis";

        //通过内存下载
//        Downloader downloader = new SimpleDownloader();
//        downloader.download(fileURL, "/Users/huaan9527/Desktop");


        //单线程下载
        //Downloader downloader = new FileDownloader();
        //downloader.download(fileURL, "/Users/huaan9527/Desktop");
        //downloader.download(fileURL, "/Users/huaan9527/Desktop/ideaIU-2020.3.dmg");

        //多线程下载
        //fileDownloader.multiThreadDownload(fileURL, "/Users/huaan9527/Desktop/精通Spring(清晰书签版).pdf", 50);

        MultiThreadDownloadProgressPrinter downloadProgressPrinter = new MultiThreadDownloadProgressPrinter(30);

        CompletableFuture.runAsync(() -> {
            while (true) {
                long alreadyDownloadLength = downloadProgressPrinter.getAlreadyDownloadLength();
                long contentLength = downloadProgressPrinter.getContentLength();
                System.out.println(contentLength + "  =>  " + alreadyDownloadLength);
                if (alreadyDownloadLength != 0 && alreadyDownloadLength > contentLength) {
                    break;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Downloader downloader = new MultiThreadFileDownloader(30, downloadProgressPrinter);
        File directory = new File("");
        downloader.download(fileURL, directory.getCanonicalPath());
        //fileDownloader.multiThreadDownload(fileURL, "/Users/huaan9527/Desktop", 10);

    }

}
