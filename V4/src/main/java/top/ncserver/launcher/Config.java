package top.ncserver.launcher; /**
 * @author MakesYT
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    static Logger logger=LogManager.getLogger(Config.class);
    public static JSONObject JsonConfig;
    public static JSONObject RemoteJsonConfig;
    public static void loader() throws IOException {
        logger.info("开始初始化配置文件．．");
        File configFile = new File("config.json");
        if (!configFile.exists())
        {
            logger.info("未检测到配置文件,开始生成...");
            init();
            logger.info("配置文件生成完成");

        }else {
            logger.info("无需生成配置文件,跳过");
        }
        logger.info("加载配置文件...");
        try {

            BufferedReader C = new BufferedReader(new FileReader("config.json"));
            String allConfig="";
            String str;
            while ((str = C.readLine()) != null) {
                allConfig=allConfig+str;
            }
            JsonConfig=JSONObject.parseObject(allConfig);
            logger.info("配置文件加载完成");
        }catch (Exception e)
        {
            Info.error(Config.class,"配置文件加载失败",e);
        }
        logger.info("获取远程配置文件");
        if (Download.httpDownload("config.json","http://download.ncserver.top:8000/update/V4","temp"))
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
                Info.error(Config.class,"配置文件加载失败",e);
            }

        }

    }
    static void init() {
        LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
        map.put("firstJoin","true");
        map.put("update","false");
        map.put("LauncherVersion","4.0.0-Alpha");
        map.put("ClientVersion","null");
        map.put("java","auto");
        map.put("RAM","4096");
        map.put("autoLogin","false");
        map.put("account","null");
        map.put("password","null");
        try {
            FileWriter config=new FileWriter("config.json");
            config.write(JSON.toJSONString(map,true));
            config.flush();
            config.close();
        }catch (Exception ignored){
            Info.error(Config.class,"配置文件生成失败",ignored);
        }

    }
    public static void saveConfig()
    {
        try {
            FileWriter config=new FileWriter("config.json");
            System.out.println(JSON.toJSONString(JsonConfig,true));
            config.write(JSON.toJSONString(JsonConfig,true));
            config.flush();
            config.close();
        }catch (Exception ignored){
            Info.error(Config.class,"配置文件保存失败",ignored);
        }
    }

}
