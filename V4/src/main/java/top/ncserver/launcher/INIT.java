package top.ncserver.launcher;

import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
/**
 * @author MakesYT
 */
public class INIT {

    static Logger logger=LogManager.getLogger(INIT.class);
    public static <Display> void main(String[] args) throws IOException {
        Application.launch(Ui.class,args);


    }
}
