package top.ncserver.selfupdate;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author MakesYT
 */
public class SecondDetection {
    static Logger logger= LogManager.getLogger(SecondDetection.class);
    public static JSONObject JsonConfig;
    public static JSONObject RemoteJsonConfig;
    public static  void init() throws IOException {
        BufferedReader C = new BufferedReader(new FileReader("config.json"));
        String allConfig="";
        String str;
        while ((str = C.readLine()) != null) {
            allConfig=allConfig+str;
        }
        RemoteJsonConfig= JSONObject.parseObject(allConfig);
        logger.info("配置文件加载完成");
        BufferedReader config = new BufferedReader(new FileReader("temp/config.json"));
        String allConfig2="";
        String str2;
        while ((str2 = C.readLine()) != null) {
            allConfig2=allConfig2+str2;
        }
        JsonConfig=JSONObject.parseObject(allConfig2);
        logger.info("配置文件加载完成");
    }
}
