package top.ncserver.launcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.ncserver.launcher.dataStorage.User;
import top.ncserver.launcher.until.LoadData;
import top.ncserver.launcher.until.Login;

import java.io.IOException;
import java.util.LinkedList;

public class TEST extends Application {
    static Logger logger = LogManager.getLogger(TEST.class);

    @Override
    public void start(Stage stage) throws IOException, InterruptedException, ClassNotFoundException {
        Thread thread = new Thread(() -> {
            Platform.runLater(progressBar::progressBar);
        });
        thread.start();
        Thread thread1 = new Thread(() -> {
            int i=0;
            while (true)
            {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                progressBar.c.setText(String.valueOf("10101010101010 "));
            }
        });
        thread1.start();
    }
}
