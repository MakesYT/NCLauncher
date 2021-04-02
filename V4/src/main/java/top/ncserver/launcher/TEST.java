package top.ncserver.launcher;

import java.io.IOException;

public class TEST {
    public static void main(String[] args) throws IOException, InterruptedException {
        Config.loader();
        if (Config.checkValue("ClientName"))
        {
            System.out.println("111");
            Config.JsonConfig.put("ClinetName","getClientName()") ;
            Config.saveConfig();
        }
    }
}
