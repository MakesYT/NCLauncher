package top.ncserver.launcher.MC;

import cn.silently9527.download.download.Downloader;
import cn.silently9527.download.download.FileDownloader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.Config;
import top.ncserver.launcher.Info;
import top.ncserver.launcher.Login;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author MakesYT
 */
public class MC_Login {
    public static final String USER_AGENT = "Ncharge client/4.0.0-Alpha";
    static Logger logger = LogManager.getLogger(MC_Login.class);
    public static String MC_accessToken;
    public static String uuid;
    public static void MC_init() throws Exception {
        if (DetectClient()) {
            authlib_injector();
            if (MC_Login())
            MC_start.MC_start();
            else {
                logger.info("游戏启动时登录失败");
                Info.info(3000, "游戏启动时登录失败,这个错误不应该存在", false);
            }
        }else
        {
            logger.info("尚未安装客户端,请先安装客户端");
            Info.info(3000,"尚未安装客户端,请先安装客户端",false);
        }

    }
    public static boolean DetectClient()
    {
        logger.info("开始检测是否存在游戏客户端");
        if (!Config.JsonConfig.getString("ClientVersion").equals("null")) {
            return true;
        }
        return false;
    }
    public static void authlib_injector() throws IOException {
        logger.info("确认authlib_injector");
        File authlib_file=new File(System.getProperty("user.dir")+"\\.minecraft\\authlib-injector.jar");
        if (!authlib_file.exists())
        {
            logger.info("不存在authlib_injector,开始下载");
            Downloader downloader = new FileDownloader();
            downloader.download("http://download.ncserver.top:8000/update/authlib-injector.jar", ".minecraft");
            logger.info("authlib_injector下载完成");
            //Download.httpDownload("authlib-injector.jar", "http://download.ncserver.top:8000/update", "");
        }
        logger.info("authlib_injector确认完成");
    }

    public static boolean MC_Login() throws Exception {
        logger.info("开始游戏账号登录");
        String url = "https://www.ncserver.top:666/api/yggdrasil/authserver/authenticate/";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json");

        String jsonString="{\"username\":\""+ Login.user_email+"\",\"password\":\""+Login.password+"\",\"requestUser\":false,\"agent\":{\"name\":\"Minecraft\",\"version\":1}}";
        JSONObject json = new JSONObject();
        json= JSON.parseObject(jsonString);
        StringEntity s = new StringEntity(json.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        post.setEntity(s);
        //post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String rd1=rd.readLine();
        JSONObject result=new JSONObject();
        result=JSON.parseObject(rd1);
        try{
            MC_accessToken=result.getString("accessToken");
        }catch (JSONException e)
        {
            logger.error(e);
            logger.error(result.getString("errorMessage"));
            Info.error(MC_Login.class,result.getString("errorMessage"),new Exception("无详细信息"));
        }



        JSONArray uuidJSON=result.getJSONArray("availableProfiles");
        String uuidString=uuidJSON.toString();
        uuidString=uuidString.substring(1, uuidString.length()-1);
        JSONObject uuidJSON2;
        uuidJSON2=JSON.parseObject(uuidString);
        uuid=uuidJSON2.getString("id");
        //System.out.println(rd.readLine());
        if (MC_accessToken.length()>=2) {
            logger.info("游戏登录成功");
            return true;
        } else {
            logger.info("游戏登录失败,这个错误不应该存在");
            return false;
        }

    }
}
