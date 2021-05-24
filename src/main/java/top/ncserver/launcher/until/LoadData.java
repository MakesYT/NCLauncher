package top.ncserver.launcher.until;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.Info;
import top.ncserver.launcher.TEST;
import top.ncserver.launcher.dataStorage.Client;
import top.ncserver.launcher.dataStorage.User;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;

public class LoadData {
    static Logger logger= LogManager.getLogger(LoadData.class);
    public static LinkedList<User> loadUsers() throws IOException, ClassNotFoundException, InterruptedException {
        File data=new File("data");
        LinkedList<User> u=new LinkedList<User>();
        for (File file : Objects.requireNonNull(data.listFiles())) {
            if (FilenameUtils.getExtension(file.getName()).equals("usr"))
            {
                FileInputStream is = new FileInputStream("data/"+file.getName());
                ObjectInputStream ois = null;
                User user = null;
                String nameb=null;
                try {
                    ois = new ObjectInputStream(is);
                     user = (User) ois.readObject();
                     nameb = user.getName();
                     ois.close();
                     try {
                        user.getBasic();
                         u.add(user);
                    } catch (NullPointerException|StreamCorruptedException e) {

                        file.delete();
                        logger.error("保存的用户数据异常,已删除"+nameb+e);
                        Info.info(3000,"保存的用户数据异常,已删除"+nameb,false);
                    }
                } catch (EOFException|StreamCorruptedException e) {
                    is.close();
                    file.delete();
                    logger.error("data下存在异常文件");
                    Info.info(3000,"data下存在异常文件,请勿添加其它文件到data文件夹中",false);
                }
            }
        }
        return u;
    }
    public static LinkedList loadClients() throws IOException, ClassNotFoundException {
        File data=new File("data");
        LinkedList c=new LinkedList<Client>();
        for (File file : data.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals("clt"))
            {
                FileInputStream is = new FileInputStream("data/"+file.getName());
                ObjectInputStream ois = new ObjectInputStream(is);
                Client client = (Client) ois.readObject();
                System.out.println(client.getClientName());
                ois.close();
                c.add(client);
            }
        }
        return c;
    }
}
