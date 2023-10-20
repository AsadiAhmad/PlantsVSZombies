package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PeaShooter extends Pea{


    FileAndTools myTool = new FileAndTools ();

    public static int health = 10;

    private void peaVolley (int xPlantBlock, int yPlantBlock) {
        if (isThereZombieInARow(xPlantBlock, yPlantBlock)) {
            addPea (xPlantBlock, yPlantBlock);
        }
    }

    private void peaVolleyThread (int xPlantBlock, int yPlantBlock) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(2000),
                (event) -> peaVolley(xPlantBlock, yPlantBlock)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        myTool.plantThreadDeleter (myTimeline, xPlantBlock, yPlantBlock);
    }

    public void peashooterPlay (int xPlantBlock, int yPlantBlock) {
        peaVolleyThread (xPlantBlock, yPlantBlock);
    }
}
