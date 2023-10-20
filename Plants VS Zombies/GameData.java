package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameData {

    public static int[][] plant = new int[6][10];
    public static int[][] plantHealth = new int [6][10];

    public static ArrayList<Integer>[] zombies = new ArrayList[6];
    public static ArrayList<Integer>[] zombieHealth = new ArrayList[6];
    public static ArrayList<Integer>[] pea = new ArrayList[6];

    public static int[] zombieNumber = new int[6];
    public static int[] peaNumber = new int[6];
    public static int[] lawnMover = new int[6];

    public static int score = 0;

    String coinImage = "myPicture/Coin.png";
    String gameOverFXMLFileName = "myFXMLFile/Game_over.fxml";
    String gameScoreFileName = "src/ProjectFinal/myFile/GameScore.txt";
    String logInFileName = "src/ProjectFinal/myFile/LogIn.txt";

    Label scoreLabel = new Label();

    LawnMover lawnMoverObject = new LawnMover();
    FileAndTools myTool = new FileAndTools ();

    private int gameOver (int result) {
        if (lawnMoverObject.isAllLawnMowerDead() && result != 1) {
            result = 1;
            myTool.getUserData (gameScoreFileName, myTool.gettingLastUser(logInFileName), String.valueOf(GameData.score));
            try {
                myTool.createGameOverPage(gameOverFXMLFileName, String.valueOf(GameData.score), gameScoreFileName, logInFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            stage.close();
        }
        return result;
    }

    public void gameOverTimeline () {
        AtomicInteger result = new AtomicInteger();
        result.set(0);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> result.set(gameOver (result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private void initializerArrayAndArrayList (ArrayList[] myArrayList, int[] myArrays, int fillingWith) {
        for (int counter = 0; counter < 6; counter++) {
            myArrays[counter] = fillingWith;
            myArrayList[counter] = new ArrayList<>();
        }
    }

    private void lawnMoverInitializer () {
        int lawnMoverCount = 0;
        lawnMoverCount = Plant.sizeYForDifficulty ();
        for (int counter = 0; counter < lawnMoverCount; counter++) {
            lawnMover[counter] = 1;
        }
    }

    public void initializer () {
        initializerArrayAndArrayList (zombies, zombieNumber, 0);
        initializerArrayAndArrayList (pea, peaNumber, 0);
        initializerArrayAndArrayList (zombieHealth, lawnMover, -1);
        lawnMoverInitializer();
    }

    public static int isPlatformFree () {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 10; column++) {
                if (plant[row][column] == 0) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static void squareFill (int fillingWhat[][], int maxRows, int maxColumn, int fillingWith) {
        for (int row = 0; row < maxRows; row++) {
            for (int column = 0; column < maxColumn; column++) {
                fillingWhat[row][column] = fillingWith;
            }
        }
    }

    public static void fillPlatformForEasyGame () {
        squareFill (plant, 6,10, 0);
        squareFill (plantHealth, 6,10, 0);
    }

    public static void fillPlatformForMediumGame () {
        squareFill (plant, 6,10, -1);
        squareFill (plant, 5,9, 0);
        squareFill (plantHealth, 6,10, -1);
        squareFill (plantHealth, 5,9, 0);
    }

    public static void fillPlatformForExtremeGame () {
        squareFill (plant, 6,10, -1);
        squareFill (plant, 4,8, 0);
        squareFill (plantHealth, 6,10, -1);
        squareFill (plantHealth, 4,8, 0);
    }

    private void updatePlantAlive () {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 10; column++) {
                if (plant[row][column] != 0 && plantHealth[row][column] == 0) {
                    plant[row][column] = 0;
                }
            }
        }
    }

    public void plantAliveUpdater () {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> updatePlantAlive()));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private void updateScore (Label scoreLabel) {
        scoreLabel.setText(String.valueOf(score));
    }

    private void scoreUpdater (Label scoreLabel) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> updateScore (scoreLabel)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private void setScoreLabel (Label scoreLabel) {
        GamePlay.root.getChildren().addAll (scoreLabel);
        scoreLabel.setTranslateX (400);
        scoreLabel.setTranslateY (-275);
        scoreLabel.setFont (new Font("Arial", 22));
    }

    public void addScore () {
        Sun sun = new Sun ();
        FileAndTools myTool = new FileAndTools ();
        sun.addSunRectangle (400, -275, Color.GOLD);
        ImageView myImageView = new ImageView ();
        myTool.setImageView (myImageView, coinImage, 50, 50, 460, -275, true);
        GamePlay.root.getChildren().add (myImageView);
        setScoreLabel (scoreLabel);
        scoreUpdater (scoreLabel);
    }
}
