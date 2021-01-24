package top.ncserver.launcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
/**
 * @author MakesYT
 */

public class Ui extends Application{
    static Logger logger= LogManager.getLogger(Ui.class);
    static Stage Stage;
    static Font font =Font.loadFont(
            Ui.class.getResource("/font1.ttf").toExternalForm(),
            20
    );
    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("开始初始化...");
        File temp=new File("temp");
        temp.mkdirs();
        Config.loader();
        Update.checker();
                primaryStage.setTitle("NCharge启动器");
                primaryStage.initStyle(StageStyle.TRANSPARENT);

                primaryStage.getIcons().add(new Image(String.valueOf(Ui.class.getResource("/server-icon.png"))));

                VBox vb = new VBox();
                vb.setPadding(new Insets(10, 50, 50, 50));
                vb.setSpacing(10);

                Label lbl = new Label("NCharge");
                lbl.setFont(Font.loadFont(
                        Ui.class.getResource("/font1.ttf").toExternalForm(),
                        50
                ));
                vb.getChildren().add(lbl);

                // Buttons
                Button btn1 = new Button();
                btn1.setFont(font);
                btn1.setText("选择java");
                btn1.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            FileChooser fileChooser = new FileChooser();
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAVA files", "javaw.exe");
                            fileChooser.getExtensionFilters().add(extFilter);
                            File file = fileChooser.showOpenDialog(primaryStage);
                            if (file.exists())
                            { Config.JsonConfig.replace("java",file);
                                Config.saveConfig();
                            }else
                                logger.info("你没有选择java");

                        }catch (Exception e)
                        {
                            logger.info("你没有选择java");
                        }
                    }
                });


                vb.getChildren().add(btn1);

                Button btn2 = new Button();
                btn2.setText("Button2");
                vb.getChildren().add(btn2);

                Button btn3 = new Button();
                btn3.setText("Button3");
                vb.getChildren().add(btn3);

                Button btn4 = new Button();
                btn4.setText("Button4");
                vb.getChildren().add(btn4);

                // Adding VBox to the scene
                Scene scene = new Scene(vb);
                primaryStage.setScene(scene);
                primaryStage.show();
                }
    public static void main(String[] args) {
        launch(args);
    }
}

