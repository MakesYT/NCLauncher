package top.ncserver.selfupdate;

import top.ncserver.launcher.Config;

public class update {
    public static void main(String[] args) {
        if (!SecondDetection.JsonConfig.getString("LauncherVersion").equals(SecondDetection.RemoteJsonConfig.getString("LauncherVersion")))
        {

        }
    }
}
