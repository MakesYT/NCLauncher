import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public interface size {
    Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
    String size=(int)screenRectangle.getWidth()+"X"+(int)screenRectangle.getHeight();
    Screen screen=Screen.getPrimary();
    double dpi=screen.getDpi();
    double scaleX=screen.getOutputScaleX();
    double scaleY=screen.getOutputScaleY();
    int big = (int) (screenRectangle.getWidth()/4*screenRectangle.getHeight()/4/1800*scaleX);
    int mid = (int) big / 5 * 4;
    int small = mid / 5 * 3;
}

