package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class JackInTheBoxZombie {

    public static int jackInTheBoxZombieHealth = 7;

    String bombAudio = "src/ProjectFinal/myAudio/bomb.wav";

    PotatoMine potatoMine = new PotatoMine ();
    FileAndTools myTool = new FileAndTools ();

    private int zombieBombCheck (ImageView myImageView, int yZombieBlock, int currentZombie, int result) {
        int zombieXRowBlock = Plant.getWhereIsPlantXBlock (myImageView.getTranslateX (), -250);
        int zombieHealth = GameData.zombieHealth[yZombieBlock].get (currentZombie);
        if (zombieXRowBlock >= 0) {
            if (zombieHealth > 0 && GameData.plant[yZombieBlock][zombieXRowBlock] != 0 && result == 1) {
                result = 0;
                myTool.audioPlayer (bombAudio);
                potatoMine.deleteSquareOfPlants (zombieXRowBlock, yZombieBlock);
                potatoMine.deleteSquareOfZombies (zombieXRowBlock, yZombieBlock);
            }
        }

        return result;
    }

    private void zombieBombChecker (ImageView myImageView, int yZombieBlock, int currentZombie) {
        AtomicInteger result = new AtomicInteger();
        result.set (1);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> result.set(zombieBombCheck (myImageView, yZombieBlock, currentZombie, result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    public void playJackInTheBoxZombie (ImageView myImageView, int yZombieBlock, int currentZombie) {
        zombieBombChecker (myImageView, yZombieBlock, currentZombie);
    }
}
