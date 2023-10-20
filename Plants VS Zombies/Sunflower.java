package ProjectFinal;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Sunflower {

    public static int health = 10;

    public void rotateMoveSun (Button sunButton, int XSunflower, int YSunflower) {
        int XSize = (XSunflower * Plant.squareSizeX()) - 222;
        int YSize = (YSunflower * Plant.squareSizeY()) - 175;
        sunButton.setTranslateX (XSize);
        sunButton.setTranslateY (YSize);
        RotateTransition rotate = new RotateTransition(Duration.millis(500), sunButton);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);
        rotate.setAutoReverse(true);
        rotate.play();
    }


}
