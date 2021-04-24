package top.ncserver.launcher;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public interface size {
    Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
    String size=(int)screenRectangle.getWidth()+"X"+(int)screenRectangle.getHeight();
    Screen screen=Screen.getPrimary();
    double dpi=screen.getDpi();
    double scaleX=screen.getOutputScaleX();
    double scaleY=screen.getOutputScaleY();
    double ratio=screenRectangle.getWidth()/screenRectangle.getHeight();
    double scene=(screenRectangle.getWidth()*screenRectangle.getHeight())*0.0007;
    double sceneWidth=  (int)scene/(ratio+1)*ratio;
    double sceneHeight=(int) scene/(ratio+1);
    int big = (int) ((int) sceneWidth*0.09);
    int mid = (int) big / 5 * 4;
    int small = mid / 5 * 3;
}

