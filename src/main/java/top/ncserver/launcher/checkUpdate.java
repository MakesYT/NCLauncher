package top.ncserver.launcher; /**
 * @author MakesYT
 */

import cn.silently9527.download.download.Downloader;
import cn.silently9527.download.download.FileDownloader;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class checkUpdate {
    static Logger logger=LogManager.getLogger(checkUpdate.class);
    public static JSONObject RemoteJsonConfig;
    public static void loader() throws IOException, InterruptedException {

        logger.info("获取远程配置文件");
        File c=new File("temp/config.json");
        c.delete();
        Downloader downloader = new FileDownloader();
        downloader.download("http://download.ncserver.top:8000/update/V5/config.json", "temp");
        if (AutoReporter.detection)
        {
            logger.info("配置文件获取成功,开始加载");
            try {
                BufferedReader C = new BufferedReader(new FileReader("temp/config.json"));
                String allConfig="";
                String str;
                while ((str = C.readLine()) != null) {
                    allConfig=allConfig+str;
                }
                RemoteJsonConfig=JSONObject.parseObject(allConfig);
                logger.info("配置文件加载完成");
            }catch (IOException e)
            {
                Info.error(checkUpdate.class,"配置文件加载失败",e);
            }

        }else{
            Info.error(AutoReporter.class,"出现错误",AutoReporter.e);
        }

        ////////////////////////////////////////////////////
        //检测更新
        if (uniformVariable.launcherVersion.equals(RemoteJsonConfig.getString("LauncherVersion")))
        {
            logger.info("无需更新,pass");
        }else {
            logger.info("需要更新,启动更新器");
            Downloader downloader1 = new FileDownloader();
            downloader1.download("http://download.ncserver.top:8000/update/V5/update.jar", "");
            if (AutoReporter.detection) {
                Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k" + System.getProperty("java.home") + "\\bin\\java -jar " + System.getProperty("user.dir") + "\\update.jar");
                System.exit(1);
            }else{
                Info.error(AutoReporter.class,"出现错误",AutoReporter.e);
            }
        }
    }



}
