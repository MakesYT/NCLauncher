package top.ncserver.launcher.MC;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.Config;
import top.ncserver.launcher.Info;
import top.ncserver.launcher.Login;

import java.io.*;
import java.util.Arrays;


/**
 * @author MakesYT
 */
public class MC_start {
    private static String path;//启动器目录
    private static String libPath;//lib目录
    private static String java;//主程序
    private static String Dminecraft;//-Dminecraft.client.jar=.minecraft\versions\EnigTech2-1.5.0\EnigTech2-1.5.0.jar
    private static String cp=null;//最长/麻烦的参数
    private static String javaagent;
    private static String userAgent;
    private static String start;
    static Logger logger = LogManager.getLogger(MC_start.class);
    public static void MC_start() {
        logger.info("开始生成启动参数");
        File l=new File("");
        path=l.getAbsoluteFile()+"\\.minecraft\\";
        libPath=path+"libraries\\";
        //System.out.println(path);
        //System.out.println(libPath);
        if (Config.JsonConfig.getString("java").equals("auto"))
        {
            java="\""+System.getProperty("java.home") + "\\bin\\java.exe\"";
        }else{
            java="\""+Config.JsonConfig.getString("java")+"\"";
        }java=java.replace("\\","\\\\");
        //主参数生成
        Dminecraft="\"-Dminecraft.client.jar=.minecraft\\versions\\"+getClientName()+"\\"+getClientName()+".jar\" " +
                "-XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=16M -XX:-UseAdaptiveSizePolicy -XX:-OmitStackTraceInFastThrow " +
                "-Xmn128m -Xmx"+Config.JsonConfig.getInteger("RAM")+"m " +
                "-Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump " +
                "\"-Djava.library.path="+path+"versions/"+getClientName()+"\\natives\" " +
                "-Dminecraft.launcher.brand=NchargeLauncher " + "-Dminecraft.launcher.version="+Config.JsonConfig.getString("LauncherVersion");
        cp="-cp \""+cpGenerate()+"\"";
        javaagent="-javaagent:"+path+"authlib-injector.jar=https://www.ncserver.top:666/api/yggdrasil/ -Dauthlibinjector.side=client " +
                "-Dauthlibinjector.yggdrasil.prefetched=" +
                "eyJtZXRhIjp7InNlcnZlck5hbWUiOiJOX2NoYXJnZSBNQ1x1NTkxNlx1N2Y2ZVx1NzY3Ylx1NWY1NSIsImltcGxlbWVudGF0aW9uTmFtZSI6IllnZ2RyYXNpbCBBUEkgZm9yIEJsZXNzaW5nIFNraW4iLCJpbXBsZW1lbnRhdGlvblZlcnNpb24iOiI0LjExLjAiLCJsaW5rcyI6eyJob21lcGFnZSI6Imh0dHBzOlwvXC93d3cubmNzZXJ2ZXIudG9wOjY2NiIsInJlZ2lzdGVyIjoiaHR0cHM6XC9cL3d3dy5uY3NlcnZlci50b3A6NjY2XC9hdXRoXC9yZWdpc3RlciJ9LCJmZWF0dXJlLm5vbl9lbWFpbF9sb2dpbiI6dHJ1ZX0sInNraW5Eb21haW5zIjpbInd3dy5uY3NlcnZlci50b3AiXSwic2lnbmF0dXJlUHVibGlja2V5IjoiLS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS1cbk1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBeWZyNjRHT2JxdmREQ0VyOEVPUkFcblBpODlWTGJQNU5XclJob0FsNzJnakttNG9SamluWnZYUytHNmdEK1pMYm92eUxWaDdKS0pJRzVCUjlIclZMY0tcbklvcCswU243SVBRSjhcL1gyS3VSSGpic2JUQVNES0tXVGRMc0JwMzNxNHlIQjAwUmk3MVNuTGEra1B0VnhQTmRwXG56UTROeTRzNTRzYkJ6N05aYzQ4cld1aHhEbWtlOHlqcjJxbFdDQXBLVkdWeDFhQms1VjFveWh4XC94UWdVS1pSZFxud1h4NWFWa2RjY0N3Tjl5ZzZJOUxjSE9rZ3VjU0JQdjg1NlJTZlNOdkdsdVhXU3RWUVdNWEs1VVwvblhTalhRN0dcbjlsV2I1QnhPUmpjeHRMMUhZcGdib1FqdVU1b2hNZSt2ZExHK2ZSanhMTWZUN0tSWDNPNlF6WTI4Z2VPT3p3WXpcbkxIbDJXTEhISGVNd2JEMm1ieDkyVkJjS2xmTDBQNHV4ZXF4b2ZhamU5RHJZVUhjVW83ZkZtQXZUc3RRTTRUM0hcbkZcLzlhNnV6bEdEdXUwenhGOWNaQnpncnJcL3YwNFE4ZnhOSHROOVlGOXYwZlJrOTRvRzhBVytJTkJCYWdNYVNuQ1xuVDhcL1dhS1o5S1JFK0EyTlhYWG9nUTU1amk5TXZ0UHo2Uk1qUFA1a2VHSE1lbzFtc1Y3ZU9MTFdkZFphK3FYTk5cblpnRlRxeWlzelhGeFFNZlFVNENERzJkRWx1Rnoyd1N6azFjTFU3elh6ZTBWT2V1WitCcm9WbmlaZnV6elJMUFNcbk9sQ1NJQlhDKzV0ZWd3eVdZNUJpTXNKV2FaZ295SGlWamlYekVpUnhpb3F6UkZuSitzUUlIWlhacjZRWnJVcGpcbkxqUG9BS0E5azlCRnd3QWFuSXlqMXprQ0F3RUFBUT09XG4tLS0tLUVORCBQVUJMSUMgS0VZLS0tLS1cbiJ9 net.minecraft.launchwrapper.Launch";
        userAgent="--username "+ Login.userName+" " +
                "--version "+Config.JsonConfig.getString("LauncherVersion")+" " +
                "--gameDir \""+path+"versions\\"+getClientName()+"\" "+
                "--assetsDir "+path+"assets " +
                "--assetIndex 1.12 " +
                "--uuid "+MC_Login.uuid+" " +
                "--accessToken "+MC_Login.MC_accessToken+" --userType mojang " +
                "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker " +
                "--versionType Forge --width 854 --height 480";
        start=java+" "+Dminecraft+" "+cp+" "+javaagent+" "+userAgent;
        logger.info("生成完成");
        logger.debug(start);
        logger.info("将启动参数写入bat文件");
        try{
            FileOutputStream fos= new FileOutputStream(System.getProperty("user.dir")+"\\temp\\start.bat");
            OutputStreamWriter os= new OutputStreamWriter(fos);
            BufferedWriter w= new BufferedWriter(os);
            w.write("set APPDATA= "+l.getAbsoluteFile()+"\r\ncd \""+path+"versions\\"+getClientName()+"\"\r\n"+start);
            w.close();
        }catch (Exception e)
        {
            logger.error("写入失败");
            Info.error(MC_start.class,"游戏启动时,命令写入失败",e);
        }
        logger.info("写入完成,开始执行");
        try {
            Runtime.getRuntime().exec("cmd /c start "+System.getProperty("user.dir")+"\\temp\\start.bat");
        }catch (IOException e)
        {
            logger.error("执行失败");
            Info.error(MC_start.class,"游戏启动时,执行失败",e);
        }
        logger.info("成功执行");

    }
    public static String getClientName()
    {
        File ClientPaths = new File(".minecraft\\versions\\");
        java.lang.String ClientName= Arrays.toString(ClientPaths.list());
        ClientName=ClientName.substring(1,ClientName.length()-1);
        //System.out.println(ClientName);
        return ClientName;

    }
    public static String cpGenerate() {
        String cp_temp="";

        try {
            BufferedReader C = new BufferedReader(new FileReader(".minecraft\\versions\\"+getClientName()+"\\"+getClientName()+".json"));
            String allConfig="";
            String str;
            while ((str = C.readLine()) != null) {
                allConfig=allConfig+str;
            }
            C.close();
            JSONObject Config= JSONObject.parseObject(allConfig);
            JSONArray lib = Config.getJSONArray("libraries");
            JSONObject forge = lib.getJSONObject(0);
            cp_temp=libPath+"net\\minecraftforge\\forge\\1.12.2-14.23.5.2854\\forge-1.12.2-14.23.5.2854.jar;";
            for (int i=1;i<=lib.size();i++)
            {try {
                JSONObject obj = lib.getJSONObject(i);
                obj=obj.getJSONObject("downloads");
                //System.out.println(obj.toString());

                //System.out.println(obj.toString());

                    obj=obj.getJSONObject("artifact");

                    File check=new File(libPath+obj.getString("path"));
                    if (check.exists())
                    {
                        //System.out.println(libPath+obj.getString("path")+";");
                        cp_temp=cp_temp+libPath+obj.getString("path")+";";

                    }
                }catch (IndexOutOfBoundsException|NullPointerException|JSONException e)
                {
                    //System.out.println("pass");
                }
            }
        }catch (IOException e){
            logger.error("文件读取失败");
            Info.error(MC_start.class,"参数生成时,文件读取失败",e);
        }
        cp_temp=cp_temp+path+"versions\\"+getClientName()+"\\"+getClientName()+".jar";
        cp_temp=cp_temp.replace("/","\\");
        return cp_temp;
    }

}
