package top.ncserver.launcher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import top.ncserver.launcher.until.FormatFileFize;

public class progressBar {
    public static long contentLength;
    public static long alreadyDownloadLength;
    public static long speed;
    public static Boolean finished=false;
    public static Stage stage;
    public static Text c;
    public static void progressBar()  {
        Service<String> service=new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        for (int i=1;i!=0;i++)
                        {   String formattedContentLength= FormatFileFize.getPrintSize(contentLength*1024);
                            String formattedAlreadyDownloadLength=FormatFileFize.getPrintSize(alreadyDownloadLength);
                            String formattedSpeed=FormatFileFize.getPrintSize(speed);
                            //System.out.println(contentLength);
                            updateValue(formattedAlreadyDownloadLength+"/"+formattedContentLength+",速度:"+formattedSpeed+"/s");
                            Thread.sleep(50);
                            if (formattedContentLength.equals(formattedAlreadyDownloadLength)) {

                                //System.out.println("finsh");
                                finished=false;stage.close();
                                break;
                            }
                        }

                        return "";
                    }
                };
            }
        };
        stage=new Stage();
        c=new Text(25,25,"111111111111 ");
        c.textProperty().bind(service.valueProperty());
        service.start();
        c.setFont(Font.loadFont(progressBar.class.getResource("/font1.ttf").toExternalForm(),50));
        c.setFill(Color.rgb(255,255,255,1));
        Label label = new Label();
        //label.setStyle("-fx-text-fill: goldenrod; -fx-font: italic 20 \"serif\"; -fx-padding: 0 0 20 0; -fx-text-alignment: center");
        StackPane glass = new StackPane(c);
        StackPane.setAlignment(label, Pos.BASELINE_CENTER);
        glass.getChildren().addAll(label);
        glass.setStyle("-fx-background-color: rgba(106,114,114,0.6); -fx-background-radius: 10;");
        //System.out.println("1");
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene=new Scene(glass);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
