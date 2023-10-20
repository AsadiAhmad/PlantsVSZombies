package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Timer;

public class PotatoMine {

    public static int health = 10;

    String potatoMineGif = "myPicture/gif/PotatoMineGif.gif";

    String bombAudio = "src/ProjectFinal/myAudio/bomb.wav";

    FileAndTools myTool = new FileAndTools ();

    public void setPotatoMineImage (ImageView myImageView, int plantNum) {
        if (plantNum == 2) {
            Timer timer = new java.util.Timer();
            timer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            myTool.setImageView (myImageView, potatoMineGif, 90, 90, 0, 0, false);
                            timer.cancel();
                        }
                    },
                    15000
            );
        }
    }

    public void deleteSquareOfPlants (int xPlantBlock, int yPlantBlock) {
        for (int row = yPlantBlock - 1; row < yPlantBlock + 2; row++) {
            for (int column = xPlantBlock - 1; column < xPlantBlock + 2; column++) {
                if (column >= 0 && row >= 0 && column <= 6 && row <= 10) {
                    GameData.plantHealth[row][column] = 0;
                }
            }
        }
    }

    public void deleteSquareOfZombies (int xPlantBlock, int yPlantBlock) {
        int zombieCount;
        int startX = (Plant.squareSizeX() * (xPlantBlock - 1)) - 249;
        int endX = (Plant.squareSizeX() * (xPlantBlock + 2)) - 249;
        int zombieCoordinateX;
        for (int row = yPlantBlock - 1; row < yPlantBlock + 2; row++) {
            if (row >= 0 && row <= 6) {
                zombieCount = GameData.zombies[row].size();
                for (int counter = 0; counter < zombieCount; counter++) {
                    zombieCoordinateX = GameData.zombies[row].get(counter);
                    if (startX <= zombieCoordinateX && zombieCoordinateX <= endX) {
                        GameData.zombieHealth[row].set(counter, 0);
                    }
                }
            }

        }
    }

    private void potatoMineChecker (int xPlantBlock, int yPlantBlock) {
        int zombieCount = GameData.zombies[yPlantBlock].size();
        int startFor = (Plant.squareSizeX() * xPlantBlock) - 249;
        int endFor = (Plant.squareSizeX() * (xPlantBlock + 1)) - 249;
        int zombieCoordinate;
        for (int counter = 0; counter < zombieCount; counter++) {
            zombieCoordinate = GameData.zombies[yPlantBlock].get(counter);
            if (startFor < zombieCoordinate && endFor > zombieCoordinate) {
                myTool.audioPlayer (bombAudio);
                GameData.zombieHealth[yPlantBlock].set(counter, 0);
                deleteSquareOfPlants (xPlantBlock, yPlantBlock);
                deleteSquareOfZombies (xPlantBlock, yPlantBlock);
            }
        }

    }

    public void potatoMineCheck (int xPlantBlock, int yPlantBlock) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> potatoMineChecker (xPlantBlock, yPlantBlock)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        myTool.plantThreadDeleter (myTimeline, xPlantBlock, yPlantBlock);
    }

    public void potatoMinePlay (int xPlantBlock, int yPlantBlock) {
        Timer timer = new java.util.Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        potatoMineCheck (xPlantBlock, yPlantBlock);
                        timer.cancel();
                    }
                },
                15000
        );
    }
}
