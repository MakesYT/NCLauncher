package top.ncserver.launcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.MC.McClientDownload;

import java.io.File;
import java.util.Optional;

import static top.ncserver.launcher.GMLmincraftStart.start.startMC;

/**
 * @author MakesYT
 */

public class Ui  {
    static Logger logger = LogManager.getLogger(Ui.class);
    static Stage Stage;
    static Font font = Font.loadFont(
            Ui.class.getResource("/font1.ttf").toExternalForm(),
            20
    );
    public static void Ui()
    {
        logger.info("加载Ui");
        Stage primaryStage=new Stage();
        Application.setUserAgentStylesheet(Ui.class.getResource("/root.css")
                .toExternalForm());
        primaryStage.setTitle("NCharge启动器");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image(String.valueOf(Ui.class.getResource("/server-icon.png"))));

        VBox vb = new VBox();
        vb.setId("box");
        vb.setPadding(new Insets(10, 50, 50, 50));
        vb.setSpacing(10);
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        vb.setMinSize(screenRectangle.getWidth()/2,screenRectangle.getHeight()/2);
        vb.getStyleClass().add("default-theme");
        HBox titleRect = new HBox();
        titleRect.setSpacing(0);
        titleRect.setPadding(new Insets(0, -80, 0, 380));
        Button close=new Button();
        close.getStyleClass().add("window-btn-basic");
        Image btnImg = new Image(String.valueOf(LoginUi.class.getResource("/closeButton1.png")),25,25,false,false);
        ImageView imageView = new ImageView(btnImg);
        close.setGraphic(imageView);
        close.setOnAction(arg0 -> {
            System.exit(1);
        });
        //close.relocate(450,180);
        //close.resizeRelocate(screenRectangle.getWidth()/5,screenRectangle.getHeight()/5,100,0);
        titleRect.getChildren().add(close);

        HBox helloBox = new HBox();
        ImageView via=new ImageView(new Image("https://www.ncserver.top:666/avatar/player/"+Login.userName+"?png"));
        helloBox.getChildren().add(via);


        Label lbl = new Label(Login.userName+"\n欢迎游玩本服务器");
        StackPane.setAlignment(lbl, Pos.CENTER);
        lbl.setFont(Font.loadFont(
                Ui.class.getResource("/font1.ttf").toExternalForm(),
                50
        ));
        helloBox.getChildren().add(lbl);
        helloBox.getChildren().add(titleRect);
        vb.getChildren().add(helloBox);
        // Buttons

        Button mcStart = new Button();
        mcStart.setText("启动游戏");
        mcStart.getStyleClass().add("btn-basic");
        mcStart.setFont(font);
        mcStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                try {
                    logger.info("开始启动游戏");
                    startMC();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        vb.getChildren().add(mcStart);
        Button downloadClient = new Button();
        downloadClient.setText("下载/修复客户端");
        downloadClient.setFont(font);
        downloadClient.getStyleClass().add("btn-basic");
        downloadClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                logger.info("开始下载/修复客户端");
                McClientDownload startDownload=new McClientDownload();
                new Thread(startDownload).start();
                logger.info("进程已启动");
            }
        });

        vb.getChildren().add(downloadClient);


        Button java = new Button();
        java.setFont(font);
        java.getStyleClass().add("btn-basic");
        java.setText("选择java");
        java.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                logger.info("开始选择java");
                try {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAVA files", "javaw.exe");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file.exists()) {
                        Config.JsonConfig.replace("java", file);
                        Config.saveConfig();
                        logger.info("java更改成功,目录为:"+file);
                        Info.info(3000,"java更改成功\n目录为:"+file,false);

                    } else
                    {
                        Info.info(3000,"java未更改",false);
                        logger.info("你没有选择java");
                    }


                } catch (Exception e) {
                    Info.info(3000,"java未更改",false);
                    logger.info("你没有选择java");
                }
            }
        });
        vb.getChildren().add(java);

        Button RAM=new Button();
        RAM.setFont(font);
        RAM.getStyleClass().add("btn-basic");
        RAM.setText("设置内存");
        RAM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                logger.info("开始修改内存");
                try {
                    Platform.runLater(() -> {
                        Application.setUserAgentStylesheet(null);
                    TextInputDialog dialog = new TextInputDialog("walter");
                    dialog.setTitle("修改RAM");
                    dialog.setHeaderText("只能输入数字");
                    dialog.setContentText("无需添加单位,单位MB");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        Config.JsonConfig.replace("RAM",dialog.getResult());
                        Config.saveConfig();
                        Info.info(3000,"RAM更改为:"+dialog.getResult(),false);
                        logger.info("RAM更改"+dialog.getResult());
                    }else
                    {
                        Info.info(3000,"RAM未更改",false);
                        logger.info("RAM未更改");
                    }
                        Application.setUserAgentStylesheet(Ui.class.getResource("/root.css")
                                .toExternalForm());
                });

                } catch (Exception e) {
                    Info.info(3000,"RAM未更改",false);
                    logger.info("RAM未更改");
                }
            }
        });
        vb.getChildren().add(RAM);

        Scene scene = new Scene(vb);
        //logger.debug(INIT.class.getResource("/root.css"));
        //String css = INIT.class.getResource("/root.css").toExternalForm();
        //scene.getStylesheets().add(css);
        scene.setFill( Color.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);

        primaryStage.show();
    logger.info("Ui加载完成");
        //Info.infoClose();
    }

}

