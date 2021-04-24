package top.ncserver.launcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Info {
    static Logger logger = LogManager.getLogger(Info.class);
    static Stage stage;
    public static void infoArea(String title, String Content) {
        Application.setUserAgentStylesheet(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.initOwner(LoginUi.primaryStage);
        alert.setTitle(title);
        alert.setHeaderText("请展开详细信息查看");
        //alert.setContentText(Content);

        Label label = new Label("这里是详细信息:");

        TextArea textArea = new TextArea(Content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();

    }

    public static void info(int time, String content, boolean always) {
        logger.info(content);
        Text c=new Text(25,25,content);
        c.setFont(Font.loadFont(Info.class.getResource("/font1.ttf").toExternalForm(),50));
        c.setFill(Color.rgb(255,255,255,1));
        Label label = new Label();
        //label.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 20 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center");
        StackPane glass = new StackPane(c);
        StackPane.setAlignment(label, Pos.BASELINE_CENTER);
        glass.getChildren().addAll(label);
        glass.setStyle("-fx-background-color: rgba(106,114,114,0.6); -fx-background-radius: 10;");
        //System.out.println("1");

         stage = new Stage();

        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene=new Scene(glass);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
        if (!always)
        {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(stage::close);


        });
        thread.setName("info:" + content);
        thread.start();}
    }
    public static void info(int time, String content) {
        logger.info(content);
        Text c=new Text(25,25,content);
        c.setFont(Font.loadFont(Info.class.getResource("/font1.ttf").toExternalForm(),50));
        c.setFill(Color.rgb(255,255,255,1));
        Label label = new Label();
        //label.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 20 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center");
        StackPane glass = new StackPane(c);
        StackPane.setAlignment(label, Pos.BASELINE_CENTER);
        glass.getChildren().addAll(label);
        glass.setStyle("-fx-background-color: rgba(106,114,114,0.6); -fx-background-radius: 10;");
        //System.out.println("1");
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene=new Scene(glass);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(stage::close);


            });
            thread.setName("info:" + content);
            thread.start();
    }
    public static void infoClose()
    {
        Platform.runLater(() -> {
            stage.close();
        });
    }

    public static void error(Class form, String Content, Exception e) {
        Application.setUserAgentStylesheet(null);
        Logger logger = LogManager.getLogger(form);
        logger.error(Content);
        logger.error(e);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.initOwner(LoginUi.primaryStage);
        alert.setTitle("错误");
        alert.setHeaderText("在运行时发生了一个错误,程序将自动退出");
        alert.setContentText(Content);

        Label label = new Label("这里是详细信息:");

        TextArea textArea = new TextArea(e.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
        System.exit(-1);

    }

}
