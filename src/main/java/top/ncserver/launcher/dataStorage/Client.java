package top.ncserver.launcher.dataStorage;

import java.io.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 227527892858332023L;
    String clientName;
    int RAM=8000;
    String Java="auto";
    String version=null;
    public Client(String name,String version){
        this.version=version;
        this.clientName=name;
    }

    public String getVersion() {
        return version;
    }

    public String getClientName(){
        return clientName;
    }
    public int getRAM(){
        return RAM;
    }
    public String getJava(){
        return Java;
    }
    public void setJava(String java) {Java=java;}
    public void setRAM(int R){RAM=R;}

    public void setVersion(String version) {
        this.version = version;
    }

    public void saveToFile() throws IOException {
        File data=new File("data");
        if (!data.exists())
            data.mkdir();
        File path=new File("");
        System.out.println(path.getPath()+getClientName()+".clt");
        FileOutputStream os = new FileOutputStream("data/"+getClientName()+".clt");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
}
