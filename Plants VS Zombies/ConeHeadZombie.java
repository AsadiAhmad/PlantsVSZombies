package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class ConeHeadZombie {

    public static int coneHeadZombieHealth = 30;

    String zombieGif = "myPicture/gif/ZombieGif.gif";

    private int zombieImageChang (ImageView myImageView, int yZombieBlock, int currentZombie, int result) {
        if (GameData.zombieHealth[yZombieBlock].get (currentZombie) <= 10 && result == 1) {
            result = 0;
            InputStream input = getClass().getResourceAsStream(zombieGif);
            Image image = new Image(input);
            myImageView.setImage (image);
            myImageView.setFitHeight (120);
            myImageView.setFitWidth (80);
        }
        return result;
    }

    private void zombieImageChanger (ImageView myImageView, int yZombieBlock, int currentZombie) {
        AtomicInteger result = new AtomicInteger();
        result.set (1);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> result.set(zombieImageChang (myImageView, yZombieBlock, currentZombie, result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    public void playConeHeadZombie (ImageView myImageView, int yZombieBlock, int currentZombie) {
        zombieImageChanger (myImageView, yZombieBlock, currentZombie);
    }

}
