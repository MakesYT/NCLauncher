package top.ncserver.selfupdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author MakesYT
 */
public class Download {
    static Logger logger= LogManager.getLogger(Download.class);
    public static boolean httpDownload(String fileName,String httpUrl,String saveFile) throws IOException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;
        logger.info("开始下载:"+fileName);
        httpUrl=httpUrl+"/"+fileName;
        if (saveFile.length()>1) {
            saveFile=saveFile+"/"+fileName;
        }else
        {
            File directory = new File("");
            saveFile= directory.getCanonicalPath()+"\\"+fileName;
        }
        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(update.alwaysOnTop, "下载地址错误,此错误非玩家触发,请尝试更新启动器解决\n"+e1+"\n", "错误", JOptionPane.ERROR_MESSAGE);
            logger.error("下载地址错误");
            logger.error(e1);
            System.exit(-1);
            return false;
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                //System.out.println(FormatFileFize.getPrintSize(bytesum));
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            logger.info("下载完成");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(update.alwaysOnTop, "下载"+fileName+"时发生错误\n"+e+"\n", "错误", JOptionPane.ERROR_MESSAGE);
            logger.error("下载错误");
            logger.error(e);
            System.exit(-1);
            return false;
        }
    }
}
