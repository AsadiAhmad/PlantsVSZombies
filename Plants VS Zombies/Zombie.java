package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Zombie {

    public static int normalZombieHealth = 10;

    String zombieGif = "myPicture/gif/ZombieGif.gif";
    String coneHeadZombieGif = "myPicture/gif/ConeHeadZombieGif.gif";
    String jackInTheBoxZombieImage = "myPicture/JackInTheBoxZombie.png";

    String eatAudio = "src/ProjectFinal/myAudio/eat.wav";

    ConeHeadZombie coneHeadZombie = new ConeHeadZombie ();
    JackInTheBoxZombie jackInTheBoxZombie = new JackInTheBoxZombie ();
    FileAndTools myTool = new FileAndTools ();
    LawnMover lawnMover = new LawnMover ();
    Random rand = new Random ();

    private int getZombieHealth (int zombieHealth) {
        int result = 0;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = zombieHealth - 3;
                break;
            case 2:
                result = zombieHealth;
                break;
            case 3:
                result = zombieHealth + 3;
                break;
        }
        return result;
    }

    private int getZombiesHealth (int zombieNum) {
        int result = 0;
        switch (zombieNum) {
            case 1:
                result = getZombieHealth (normalZombieHealth);
                break;
            case 2:
                result = getZombieHealth (ConeHeadZombie.coneHeadZombieHealth);
                break;
            case 3:
                result = getZombieHealth (JackInTheBoxZombie.jackInTheBoxZombieHealth);
                break;
        }
        return result;
    }

    private void initializingZombieHealth (int health, int zombieYRowBlock, int nowWhatZombie) {
        GameData.zombieHealth[zombieYRowBlock].add(nowWhatZombie, health);
    }

    private void decreaseZombieHealth(ImageView myImageView, int zombieYRowBlock, int nowWhatZombie, int result) {
        int decreasedHealth = GameData.zombieHealth[zombieYRowBlock].get(nowWhatZombie);
        decreasedHealth--;
        if (result != 0) {
            if (zombieCrashedPea(myImageView, zombieYRowBlock)) {
                GameData.zombieHealth[zombieYRowBlock].set(nowWhatZombie, decreasedHealth);
            }
        }
    }

    private boolean zombieCrashedPea(ImageView myImageView, int zombieYRowBlock) {
        int zombieXSize = (int) Math.round(myImageView.getTranslateX());
        int startFor = zombieXSize - (60 / 2);
        int endFor = zombieXSize + (60 / 2);
        int peaCoordinates;
        for (int counter = startFor; counter < endFor; counter += 4) {
            for (int counter2 = 0; counter2 < GameData.pea[zombieYRowBlock].size(); counter2++) {
                peaCoordinates = GameData.pea[zombieYRowBlock].get(counter2);
                if ((peaCoordinates - 13) < counter && (peaCoordinates + 13) > counter) {
                    GameData.pea[zombieYRowBlock].set(counter2, 700);
                    return true;
                }
            }
        }
        return false;
    }

    private int zombieTimelineDelete (Timeline timeline, ImageView myImageView, int yZombieBlock, int nowWhatZombie, int result) {
        decreaseZombieHealth (myImageView, yZombieBlock, nowWhatZombie, result);
        int zombieHealth = GameData.zombieHealth[yZombieBlock].get (nowWhatZombie);
        if (zombieHealth <= 0 && result != 0) {
            GameData.score += 10;
            GamePlay.root.getChildren().remove (myImageView);
            GameData.zombies[yZombieBlock].set (nowWhatZombie, -350);
            timeline.stop();
            result = 0;
        }
        return result;
    }

    public void zombieTimelineDeleter (Timeline zombieTimeline, ImageView myImageView, int yZombieBlock, int nowWhatZombie) {
        AtomicInteger result = new AtomicInteger();
        result.set(1);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(5),
                (event) -> result.set(zombieTimelineDelete (zombieTimeline, myImageView, yZombieBlock, nowWhatZombie, result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private void zombieChoose (ImageView myImageView, int zombieNum, int yZombieBlock, int currentZombie) {
        switch (zombieNum) {
            case 2:
                coneHeadZombie.playConeHeadZombie (myImageView, yZombieBlock, currentZombie);
                break;
            case 3:
                jackInTheBoxZombie.playJackInTheBoxZombie (myImageView, yZombieBlock, currentZombie);
                break;
        }
    }

    private int moveZombie (ImageView myImageView, int counter, int zombieYRowBlock, int nowWhatZombie) {
        counter++;
        int zombieXRowBlock = Plant.getWhereIsPlantXBlock (myImageView.getTranslateX (), -250);
        GameData.zombies[zombieYRowBlock].set (nowWhatZombie, (int) Math.round(myImageView.getTranslateX ()));
        if (zombieXRowBlock >= 0) {
            if (GameData.plant[zombieYRowBlock][zombieXRowBlock] == 0) { // moving zombie
                myImageView.setTranslateX (myImageView.getTranslateX () - 0.2);
            }
            else if (counter % 30 == 0) { // if there is plant on the way
                GameData.plantHealth[zombieYRowBlock][zombieXRowBlock]--;
                myTool.audioPlayer (eatAudio);
            }
        }
        else {
            GameData.lawnMover[zombieYRowBlock] = 0;
        }
        return counter;
    }

    private void zombieTimeline (ImageView myImageView, int zombieNum, int zombieYRowBlock) {
        int nowWhatZombie = GameData.zombieNumber[zombieYRowBlock];
        initializingZombieHealth (getZombiesHealth(zombieNum), zombieYRowBlock, nowWhatZombie);
        GameData.zombies[zombieYRowBlock].add (nowWhatZombie, (int) Math.round(myImageView.getTranslateX ()));
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> counter.set(moveZombie(myImageView, counter.get(), zombieYRowBlock, nowWhatZombie))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        zombieChoose (myImageView, zombieNum, zombieYRowBlock, nowWhatZombie);
        zombieTimelineDeleter (myTimeline, myImageView, zombieYRowBlock, nowWhatZombie);
        GameData.zombieNumber[zombieYRowBlock]++;
    }

    private void setZombieImage (ImageView imageView, int zombieNum, int whereZombieGrow) {
        switch (zombieNum) {
            case 1:
                myTool.setImageView (imageView, zombieGif, 120, 80
                        , 400, whereZombieGrow, true);
                break;
            case 2:
                myTool.setImageView (imageView, coneHeadZombieGif, 150, 100
                        , 400, whereZombieGrow - 10, true);
                break;
            case 3:
                myTool.setImageView (imageView, jackInTheBoxZombieImage, 120, 80
                        , 400, whereZombieGrow, true);
                break;
        }
    }

    public void addZombie (int zombieNum) {
        ImageView imageView = new ImageView ();
        int randomYRowBlock = lawnMover.randomRowAdvance();
        int whereZombieGrow = (randomYRowBlock * Plant.squareSizeY()) - 185;
        setZombieImage (imageView, zombieNum, whereZombieGrow);
        zombieTimeline (imageView, zombieNum, randomYRowBlock);
        GamePlay.root.getChildren().addAll (imageView);
    }

    private int zombieLine (int result) {
        if (!lawnMover.isAllLawnMowerDead()) {
            result++;
            for (int counter = 0; counter < result; counter++) {
                addZombie(rand.nextInt(3) + 1);
            }
        }
        return result;
    }

    private void zombieLinear () {
        AtomicInteger result = new AtomicInteger();
        result.set(1);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.seconds(15),
                (event) -> result.set(zombieLine (result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    public void zombieAdder () {
        zombieLinear();
    }
}
