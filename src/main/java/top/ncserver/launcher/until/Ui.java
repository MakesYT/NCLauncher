package top.ncserver.launcher.until;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.*;
import top.ncserver.launcher.dataStorage.User;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Ui {
    static Logger logger = LogManager.getLogger(Ui.class);
    public static Label progress;
    static Font font(int size)
    {
        return Font.loadFont(
                INIT.class.getResource("/font1.ttf").toExternalForm(),
                size
        );
    }
    public static VBox login(Stage stage){
        VBox vb = new VBox();
        vb.setId("box");
        vb.setPadding(new Insets(10, 10, 15, 50));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        vb.getStyleClass().add("default-theme");
        vb.setMaxSize(size.sceneWidth/2,size.sceneHeight/2);

        HBox titleRect = new HBox();
        titleRect.setSpacing(10);
        titleRect.setPadding(new Insets(0, -100, 0, 0));
        Label lbl = new Label("NCharge登录");
        StackPane.setAlignment(lbl, Pos.CENTER);
        lbl.setFont(font(50));
        titleRect.getChildren().add(lbl);
        vb.getChildren().add(titleRect);
        //close.setLayoutX(screenRectangle.getWidth()/5-100);

        TextField account=new TextField();
        account.setPromptText("账号");
        account.setFont(font(20));
        account.getStyleClass().add("input-group");
        vb.getChildren().add(account);

        PasswordField password=new PasswordField();
        password.setPromptText("密码");
        password.setFont(font(20));
        password.getStyleClass().add("input-group");
        vb.getChildren().add(password);

        HBox hBox =new HBox();
        hBox.setPadding(new Insets(10, 0, 0, 0));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        CheckBox autoLoginCheckBox=new CheckBox("自动登录");
        //autoLoginCheckBox.setMinSize(50,50);
        autoLoginCheckBox.setFont(font(20));
        autoLoginCheckBox.getStyleClass().add("check-box");
        autoLoginCheckBox.getStyleClass().add("big-check-box");


        Button login=new Button("登录");
        login.getStyleClass().add("btn-basic");
        login.setFont(font(20));
        login.setOnAction(arg0 -> {
            try {
                User u=new User(account.getText(), password.getText());
                u.getBasic();
                INIT.userList.add(u);
                INIT.bp1.setCenter(ui(stage));
                if (autoLoginCheckBox.isSelected()) {
                    u.saveToFile();
                }
            } catch (Exception e) {

                Info.info(3000,"登陆失败，账号或密码错误",false);
            }
        });
        hBox.getChildren().add(login);

        Button register=new Button("注册");
        register.getStyleClass().add("btn-basic");
        register.setFont(font(20));
        register.setOnAction(arg0 ->{
            String command = "cmd /c start https://www.ncserver.top:666/auth/register";
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        hBox.getChildren().add(register);


        hBox.getChildren().add(autoLoginCheckBox);

        vb.getChildren().add(hBox);
        return vb;
    }
    public static VBox ui(Stage stage) throws IOException, InterruptedException {
        VBox vb=new VBox(8);
        vb.setPadding(new Insets(10, 10, 15, 50));
        Label lbl = new Label(INIT.userList.get(0).getName()+"\n欢迎游玩本服务器");
        StackPane.setAlignment(lbl, Pos.CENTER);
        lbl.setFont(font(50));
        vb.getChildren().add(lbl);
        Button down=new Button("下载客户端");
        down.getStyleClass().add("btn-basic");
        down.setFont(font(20));
        down.setOnAction(arg0 -> {
            logger.info("开始下载/修复客户端");
            McClientDownload startDownload= null;
            try {
                startDownload = new McClientDownload();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(startDownload).start();
            logger.info("进程已启动");
        });
        vb.getChildren().add(down);
        Button start=new Button("启动游戏");
        start.getStyleClass().add("btn-basic");
        start.setFont(font(20));
        start.setOnAction(arg0 -> {
            logger.info("开启动游戏");
            try {
                Mcstart.startMC();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("进程已启动");
        });
        vb.getChildren().add(start);
        Button java = new Button();
        java.setFont(font(20));
        java.getStyleClass().add("btn-basic");
        java.setText("选择java");
        java.setOnAction(arg0 -> {
                logger.info("开始选择java");
                try {
                    if (!INIT.clientList.isEmpty())
                    {
                        FileChooser fileChooser = new FileChooser();
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAVA files", "javaw.exe");
                        fileChooser.getExtensionFilters().add(extFilter);
                        File file = fileChooser.showOpenDialog(stage);
                        if (file.exists()) {
                            INIT.clientList.getFirst().setJava(file.toString());
                            INIT.clientList.getFirst().saveToFile();
                            logger.info("java更改成功,目录为:"+file);
                            Info.info(3000,"java更改成功\n目录为:"+file,false);

                        } else
                        {
                            Info.info(3000,"java未更改",false);
                            logger.info("你没有选择java");
                        }
                    }else {
                        Info.info(3000,"java未更改,未安装客户端",false);
                        logger.info("java未更改,未安装客户端");
                    }

                } catch (Exception e) {
                    Info.info(3000,"java未更改,未安装客户端",false);
                    logger.info("java未更改,未安装客户端");
                }
        });
        vb.getChildren().add(java);

        Button RAM=new Button();
        RAM.setFont(font(20));
        RAM.getStyleClass().add("btn-basic");
        RAM.setText("设置内存");
        RAM.setOnAction(arg0 -> {
                logger.info("开始修改内存");
                try {
                    if (!INIT.clientList.isEmpty())
                    Platform.runLater(() -> {
                        Application.setUserAgentStylesheet(null);
                        TextInputDialog dialog = new TextInputDialog("walter");
                        dialog.setTitle("修改RAM");
                        dialog.setHeaderText("只能输入数字");
                        dialog.setContentText("无需添加单位,单位MB");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            INIT.clientList.getFirst().setRAM(Integer.parseInt(dialog.getResult()));
                            try {
                                INIT.clientList.getFirst().saveToFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Info.info(3000,"RAM更改为:"+dialog.getResult());
                            logger.info("RAM更改"+dialog.getResult());
                        }else
                        {
                            Info.info(3000,"RAM未更改");
                            logger.info("RAM未更改");
                        }
                        Application.setUserAgentStylesheet(Ui.class.getResource("/root.css")
                                .toExternalForm());
                    });
                    else {
                        Info.info(3000,"RAM未更改,未安装客户端",false);
                        logger.info("RAM未更改,未安装客户端");
                    }
                } catch (Exception e) {
                    Info.info(3000,"RAM未更改",false);
                    logger.info("RAM未更改");
                }

        });
        vb.getChildren().add(RAM);

        progress = new Label();
        progress.setFont(font(50));
        vb.getChildren().add(progress);
        return vb;
    }
}
