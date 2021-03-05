package top.ncserver.launcher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoginUi {
    static Logger logger = LogManager.getLogger(LoginUi.class);
    static Font font = Font.loadFont(
            LoginUi.class.getResource("/font1.ttf").toExternalForm(),
            20
    );
    static  TextField account;
    static PasswordField password;
    static Stage primaryStage;
    static CheckBox autoLoginCheckBox;
    public static void loginUi() throws IOException {
        primaryStage=new Stage();
        Application.setUserAgentStylesheet(LoginUi.class.getResource("/root.css")
                .toExternalForm());
        primaryStage.setTitle("登录");
        //primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image(String.valueOf(Ui.class.getResource("/server-icon.png"))));
        VBox vb = new VBox();
        vb.setId("box");
        vb.setPadding(new Insets(10, 50, 15, 50));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        vb.setMinSize(screenRectangle.getWidth()/5,screenRectangle.getHeight()/5);
        vb.getStyleClass().add("default-theme");

        HBox titleRect = new HBox();
        titleRect.setSpacing(10);
        titleRect.setPadding(new Insets(0, -80, 0, 0));
        Label lbl = new Label("NCharge登录");
        StackPane.setAlignment(lbl, Pos.CENTER);
        lbl.setFont(Font.loadFont(
                Ui.class.getResource("/font1.ttf").toExternalForm(),
                50
        ));
        titleRect.getChildren().add(lbl);


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
        vb.getChildren().add(titleRect);



        //close.setLayoutX(screenRectangle.getWidth()/5-100);

         account=new TextField();
        account.setPromptText("账号");
        account.setFont(font);
        account.getStyleClass().add("input-group");
        vb.getChildren().add(account);

         password=new PasswordField();
        password.setPromptText("密码");
        password.setFont(font);
        password.getStyleClass().add("input-group");
        vb.getChildren().add(password);

        HBox hBox =new HBox();
        hBox.setPadding(new Insets(10, 0, 0, 0));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Button login=new Button("登录");
        login.getStyleClass().add("btn-basic");
        login.setFont(font);
        login.setOnAction(arg0 -> {
            try {
                Login.Login();
            } catch (Exception e) {

                Info.info(3000,"登陆失败，账号或密码错误",false);
            }
        });
        hBox.getChildren().add(login);

        Button register=new Button("注册");
        register.getStyleClass().add("btn-basic");
        register.setFont(font);
        register.setOnAction(arg0 ->{
            String command = "cmd /c start https://www.ncserver.top:666/auth/register";
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        hBox.getChildren().add(register);

        autoLoginCheckBox=new CheckBox("自动登录");
        //autoLoginCheckBox.setMinSize(50,50);
        autoLoginCheckBox.setFont(font);
        autoLoginCheckBox.getStyleClass().add("check-box");
        autoLoginCheckBox.getStyleClass().add("big-check-box");
        hBox.getChildren().add(autoLoginCheckBox);

        vb.getChildren().add(hBox);
        Scene scene = new Scene(vb);

        scene.setFill( Color.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);

        primaryStage.show();


        //Info.infoClose();
    }
}
