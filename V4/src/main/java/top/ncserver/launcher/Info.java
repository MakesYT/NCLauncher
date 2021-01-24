package top.ncserver.launcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Info  {

    public static void error(Class form,String Content,Exception e)
    {

        Logger logger= LogManager.getLogger(form);
        logger.error(Content);
        logger.error(e);


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(Ui.Stage);
                alert.setTitle("错误");
                alert.setHeaderText("在运行时发生了一个错误");
                alert.setContentText(Content);

                Label label = new Label("The exception stacktrace was:");

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

                alert.show();

            }

}
