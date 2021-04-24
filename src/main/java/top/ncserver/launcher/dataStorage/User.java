package top.ncserver.launcher.dataStorage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.APP;
import top.ncserver.launcher.Info;
import top.ncserver.launcher.until.Login;

import java.io.*;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    String username;
    String password;
    String name="";
    String token="";
    String skinUUID="";
    String UUID="";
    static Logger logger= LogManager.getLogger(User.class);
    public User(String username,String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    /*
    @info 获取用户name\皮肤uuid\token
     */
    public void getBasic() throws IOException, InterruptedException,NullPointerException {

            JSONObject result=JSONObject.parseObject(Login.login(username,password));
            logger.debug(result.toString());
            this.token=result.getString("accessToken");
            //JSONObject uuid=result.getJSONObject("user");
            //this.UUID=uuid.getString("id");
            result=result.getJSONObject("selectedProfile");
            this.name=result.getString("name");
            this.UUID=result.getString("id");

    }
    public String getName() throws IOException, InterruptedException {
        if (!name.isEmpty())
        return name;
        else {
            getBasic();
            return name;
        }
    }
    public String getUUID() throws IOException, InterruptedException {
        if (!UUID.isEmpty())
            return UUID;
        else {
            getBasic();
            return UUID;
        }
    }
    public String getToken() throws IOException, InterruptedException {
        if (!token.isEmpty())
        {
            return token;
        }else if (!checkToken()){
            getBasic();
            return token;
        }
        else {
            getBasic();
            return token;
        }
    }
    public String getSkinUUID() throws IOException, InterruptedException {
        if (!skinUUID.isEmpty())
            return skinUUID;
        else {
            getBasic();
            return skinUUID;
        }
    }
    Boolean checkToken(){
        try {
            return Login.checkToken(token);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    @refreshToken 基本无用
     */
    public void refreshToken(){
        try {
            JSONObject result=JSONObject.parseObject(Login.refreshToken(token));
            this.token=result.getString("accessToken");
        } catch (IOException | InterruptedException e) {
            logger.error("刷新用户Token时出错\n"+e);
            Info.error(User.class,"刷新用户Token时出错",e);
        }
    }
    public void saveToFile() throws IOException, InterruptedException {

            logger.info("开始保存用户数据...("+this.getName()+")");
            File data=new File("data");
            if (!data.exists())
                data.mkdir();
            File path=new File("");
            //System.out.println(path.getPath()+getName()+".usr");
            FileOutputStream os = new FileOutputStream("data/"+getUUID()+".usr");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            logger.info("用户数据保存成功");


    }
}
