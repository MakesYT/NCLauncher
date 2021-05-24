package top.ncserver.launcher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.dataStorage.Client;
import top.ncserver.launcher.dataStorage.User;
import top.ncserver.launcher.until.LoadData;
import top.ncserver.launcher.until.McClientDownload;
import top.ncserver.launcher.until.Ui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class INIT extends Application {
    static Logger logger= LogManager.getLogger(INIT.class);
    Font font(int size)
    {
        return Font.loadFont(
                INIT.class.getResource("/font1.ttf").toExternalForm(),
                size
        );
    }
    public static BorderPane bp1;
    public static LinkedList<User> userList;
    public static LinkedList<Client> clientList;


    @Override
    public void start(Stage stage) throws IOException, InterruptedException, ClassNotFoundException {
        logger.info("屏幕分辨率:"+size.size);
        logger.info("系统缩放率:"+size.scaleX*100+"%");
        logger.info("大号字体尺寸"+size.big);
        logger.info("中号字体尺寸"+size.mid);
        logger.info("小号字体尺寸"+size.small);
        logger.info("长宽比:"+size.ratio);
        logger.info("长"+size.sceneWidth);
        logger.info("宽"+size.sceneHeight);
        File temp=new File("temp");
        if (!temp.exists()){
            logger.info("temp创建");
            temp.mkdirs();
        }

        else{

            McClientDownload.deleteAll(new File("temp"));
            temp.mkdirs();
        }


        File data=new File("data");
        data.mkdirs();
        checkUpdate.loader();
         {
            try {
                userList = LoadData.loadUsers();
                clientList=LoadData.loadClients();
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        Application.setUserAgentStylesheet(INIT.class.getResource("/root.css")
                .toExternalForm());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(String.valueOf(INIT.class.getResource("/server-icon.png"))));
        stage.setTitle("初始化....");

        BorderPane bp = new BorderPane();
        //bp.setPadding(new Insets(10, 0, 20, 20));
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(1));
        vBox.setPadding(new Insets(10, 0, 10, 20));
        Text t1=new Text("初始化............\nInitializing....");
        t1.setFont(font(size.big));
        Text t2=new Text("初始化............\nInitializing....");
        t2.setFont(font(size.mid));
        Text t3=new Text("初始化............\nInitializing....");
        t3.setFont(font(size.small));
        vBox.getChildren().add(t1);
        vBox.getChildren().add(t2);
        vBox.getChildren().add(t3);
        bp.setCenter(vBox);

        Button close=new Button();
        close.getStyleClass().add("window-btn-basic");
        Image btnImg = new Image(String.valueOf(INIT.class.getResource("/closeButton.png")),size.sceneWidth/(size.ratio*12),size.sceneWidth/(size.ratio*12),false,false);
        ImageView imageView = new ImageView(btnImg);
        close.setGraphic(imageView);
        close.setOnAction(arg0 -> {
            System.exit(1);
        });
        bp.setRight(close);

        Scene scene = new Scene(bp,size.sceneWidth,size.sceneHeight);
        scene.setFill( Color.TRANSPARENT);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        logger.info("自适应缩放参数生成完成");
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////
        stage.close();

        Stage primaryStage=new Stage();

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image(String.valueOf(INIT.class.getResource("/server-icon.png"))));
        primaryStage.setTitle("Ncharge登录");
         bp1 = new BorderPane();
        bp1.setRight(close);
        if (userList.isEmpty())
        bp1.setCenter(Ui.login(primaryStage));
        else bp1.setCenter(Ui.ui(primaryStage));
        Label version=new Label(uniformVariable.launcherVersion);
        version.setFont(font(size.small));
        bp1.setBottom(version);
        Scene scene1 = new Scene(bp1,size.sceneWidth,size.sceneHeight);
        scene1.setFill( Color.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
