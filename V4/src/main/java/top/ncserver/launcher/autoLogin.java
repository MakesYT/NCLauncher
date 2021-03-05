package top.ncserver.launcher;

import java.io.IOException;

public class autoLogin {
    public static boolean autoLogin=false;
    public static String account;
    public static String password;

    public static void save(String account,String password)
    {
        Config.JsonConfig.replace("autoLogin",true);
        Config.JsonConfig.replace("account",account);
        Config.JsonConfig.replace("password",password);
        Config.saveConfig();

    }
    public static void getUser() throws IOException {
        account=Config.JsonConfig.getString("account");
        password=Config.JsonConfig.getString("password");
    }
}
