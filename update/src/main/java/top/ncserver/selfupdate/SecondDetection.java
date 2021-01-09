package top.ncserver.selfupdate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author MakesYT
 */
public class SecondDetection {
    static Logger logger= LogManager.getLogger(SecondDetection.class);
    public static JSONObject JsonConfig;
    public static JSONObject RemoteJsonConfig;
    public static  void init() throws IOException {
        try{
        BufferedReader C = new BufferedReader(new FileReader("config.json"));
        String allConfig="";
        String str;
        while ((str = C.readLine()) != null) {
            allConfig=allConfig+str;
        }
        JsonConfig= JSONObject.parseObject(allConfig);
        logger.info("配置文件加载完成");
        BufferedReader config = new BufferedReader(new FileReader("temp/config.json"));
        String allConfig2="";
        String str2;
        while ((str2 = config.readLine()) != null) {
            allConfig2=allConfig2+str2;
        }
        RemoteJsonConfig=JSONObject.parseObject(allConfig2);
        logger.info("配置文件加载完成");}catch (IOException e)
        {
            logger.error("发生错误,请不要手动运行此程序");
            JOptionPane.showMessageDialog(update.alwaysOnTop, "发生错误,请不要手动运行此程序", "错误", JOptionPane.ERROR_MESSAGE);

        }
    }
    public static void saveConfig()
    {
        try {
            FileWriter config=new FileWriter("config.json");
            JSON.writeJSONString(config,JsonConfig);
        }catch (Exception ignored){
            JOptionPane.showMessageDialog(update.alwaysOnTop, "配置文件生成失败\n"+ignored+"\n", "严重错误", JOptionPane.ERROR_MESSAGE);
            logger.error("配置文件生成失败");
            logger.error(ignored);
            System.exit(-1);
        }
    }
}
