package ProjectFinal;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.Random;
import java.util.Timer;

public class Sun {

    public static int amountOfSun = 50;

    Random rand = new Random ();
    Label sunLabel = new Label();

    TranslateTransition translate = new TranslateTransition();
    FadeTransition fade = new FadeTransition();
    FileAndTools myTool = new FileAndTools ();
    Sunflower sunflower = new Sunflower ();

    String sunGif = "myPicture/gif/SunGif.gif";
    String sunImage = "myPicture/Sun.png";

    private void setFadeRectangle (Rectangle rectangle) {
        fade.setDuration(Duration.millis(5000));
        fade.setFromValue (10);
        fade.setToValue (0.1);
        fade.setCycleCount (1);
        fade.setAutoReverse (true);
        fade.setNode (rectangle);
        fade.play ();
        Timer timer = new java.util.Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        fade.stop ();
                        timer.cancel();
                    }
                },
                2500
        );
    }

    public void addSunRectangle (int xSize, int ySize, Color rectangleColor) {
        Rectangle rectangle = new Rectangle();
        rectangle.setTranslateX (xSize);
        rectangle.setTranslateY (ySize);
        rectangle.setWidth (100);
        rectangle.setHeight (30);
        rectangle.setArcWidth (40.0);
        rectangle.setArcHeight (40.0);
        rectangle.setFill (rectangleColor);
        setFadeRectangle (rectangle);
        GamePlay.root.getChildren().add (rectangle);
    }

    public void setSunLabelPicture () {
        addSunRectangle(-410, -275, Color.CYAN);
        ImageView myImageView = new ImageView ();
        myTool.setImageView (myImageView, sunImage, 75, 75, -460, -275, true);
        GamePlay.root.getChildren().add (myImageView);
    }

    private void transition (Button sunButton, int X, int Y) {
        sunButton.setTranslateX (X);
        sunButton.setTranslateY (-400);
        translate.setByY(Y + 115);
        translate.setDuration(Duration.millis((Y/Plant.squareSizeY())*1000));
        translate.setCycleCount(1);
        translate.setAutoReverse(true);
        translate.setNode(sunButton);
        translate.play();
    }

    private void linearMoveSun (Button sunButton, int difficulty) {
        int X = 0,Y = 0;
        switch (difficulty) {
            case 1:
                X = rand.nextInt (10);
                Y = rand.nextInt (6);
                break;
            case 2:
                X = rand.nextInt (9);
                Y = rand.nextInt (5);
                break;
            case 3:
                X = rand.nextInt (8);
                Y = rand.nextInt (4);
                break;
        }
        X = (X * Plant.squareSizeX())-212;
        Y = ((Y + 1) * Plant.squareSizeY ());
        transition (sunButton, X, Y);
    }

    private void moveSun (Button sunButton, int difficulty, boolean isSunForSunflower, int XSunflower, int YSunflower) {
        if (isSunForSunflower) {
            sunflower.rotateMoveSun (sunButton, XSunflower, YSunflower);
        } else {
            linearMoveSun(sunButton, difficulty);
        }
    }

    private void updateSun () {
        sunLabel.setText(String.valueOf(amountOfSun));
    }

    private void sunLine (Button sunButton, int difficulty, boolean isSunForSunflower, int XSunflowerBlock, int YSunflowerBlock, int sunDuration) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.seconds(sunDuration),
                (event) -> moveSun(sunButton, difficulty, isSunForSunflower, XSunflowerBlock, YSunflowerBlock)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        if (isSunForSunflower) {
            myTool.plantThreadDeleter (myTimeline, XSunflowerBlock, YSunflowerBlock);
        }
    }

    private void sunUpdater () {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> updateSun()));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    public void setSunLabel (StackPane root) {
        root.getChildren().addAll (sunLabel);
        sunLabel.setTranslateX (-400);
        sunLabel.setTranslateY (-275);
        sunLabel.setFont (new Font("Arial", 22));
    }

    public void addSun (StackPane root, int difficulty, boolean isSunForSunflower, int XSunflowerBlock, int YSunflowerBlock, int sunDuration) {
        Button sunButton = new Button();
        EventHandler<ActionEvent> sunHandler = actionEvent -> {
            amountOfSun += 25;
            sunButton.setTranslateX(600);
            sunButton.setTranslateY(600);
        };
        ImageView imageView = new ImageView ();
        myTool.setImageView (imageView, sunGif, 75, 75, 0, 0, false);
        sunButton.setGraphic (imageView);
        sunButton.setStyle ("-fx-background-color: Transparent;");
        sunButton.setOnAction (sunHandler);
        root.getChildren().addAll (sunButton);
        moveSun(sunButton, difficulty, isSunForSunflower, XSunflowerBlock, YSunflowerBlock);
        sunLine (sunButton, difficulty, isSunForSunflower, XSunflowerBlock, YSunflowerBlock, sunDuration);
        sunUpdater();
    }

}
