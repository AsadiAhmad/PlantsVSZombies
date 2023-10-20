package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LawnMover {

    String lawnMowerImage = "myPicture/LawnMower.png";
    FileAndTools myTool = new FileAndTools ();

    public boolean isAllLawnMowerDead () {
        int result;
        for (int counter = 0; counter < 6; counter ++) {
            result = GameData.lawnMover[counter];
            if (result == 1) {
                return false;
            }
        }
        return true;
    }

    private String backStringAliveLawnMower () {
        StringBuilder result = new StringBuilder();
        for (int counter = 0; counter < 6; counter++) {
            if (GameData.lawnMover[counter] == 1) {
                result.append(counter);
            }
        }
        return result.toString();
    }

    public int randomRowAdvance() {
        String aliveLawnMover = backStringAliveLawnMower();
        Random rand = new Random ();
        return Character.getNumericValue(aliveLawnMover.charAt (rand.nextInt(aliveLawnMover.length())));
    }

    private int lawnMowerDelete (Timeline timeline, ImageView myImageView, int currentLawnMower, int result) {
        if (GameData.lawnMover[currentLawnMower] == 2 && result != 0) {
            GamePlay.root.getChildren().remove (myImageView);
            timeline.stop();
            result = 0;
        }
        return result;
    }

    private void lawnMowerDeleter (Timeline lawnMowerTimeline, ImageView myImageView,  int currentLawnMower) {
        AtomicInteger result = new AtomicInteger();
        result.set(1);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> result.set(lawnMowerDelete (lawnMowerTimeline, myImageView, currentLawnMower, result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private void lawnMoverMove (ImageView myImageView, int currentLawnMower) {
        if (myImageView.getTranslateX () > 600) {
            GameData.lawnMover[currentLawnMower] = 2;
        }
        int zombieCount = GameData.zombies[currentLawnMower].size();
        int zombieCoordinateX;
        if (GameData.lawnMover[currentLawnMower] == 0) {
            myImageView.setTranslateX (myImageView.getTranslateX () + 3);
            for (int counter = 0; counter < zombieCount; counter++) {
                zombieCoordinateX = GameData.zombies[currentLawnMower].get(counter);
                if (myImageView.getTranslateX() <= zombieCoordinateX && zombieCoordinateX <= myImageView.getTranslateX() + 20) {
                    GameData.zombieHealth[currentLawnMower].set(counter, 0);
                }
            }
        }
    }

    private void lawnMowerMover (ImageView myImageView, int currentLawnMower) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> lawnMoverMove (myImageView, currentLawnMower)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        lawnMowerDeleter (myTimeline, myImageView, currentLawnMower);
    }

    private void addLawnMower (int yBlock) {
        ImageView myImageView = new ImageView ();
        int translateY = (yBlock * Plant.squareSizeY()) - 200;
        myTool.setImageView (myImageView, lawnMowerImage, 50, 50, -300, translateY,true);
        GamePlay.root.getChildren().add (myImageView);
        lawnMowerMover (myImageView, yBlock);
    }

    private void lawnMowerAdder (int count) {
        for (int counter = 0; counter < count; counter++) {
            addLawnMower(counter);
        }

    }

    public void lawnMowerSwitch () {
        switch (GamePlay.gameDifficulty) {
            case 1:
                lawnMowerAdder (6);
                break;
            case 2:
                lawnMowerAdder (5);
                break;
            case 3:
                lawnMowerAdder (4);
                break;
        }
    }

}
