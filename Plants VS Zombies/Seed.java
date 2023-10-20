package ProjectFinal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class Seed {

    public static int setPlant = 0;

    String sunflowerSeedImage = "myPicture/SeedPictures/SunflowerSeed.png";
    String potatoMineSeedImage = "myPicture/SeedPictures/PotatoMineSeed.png";
    String peaShooterSeedImage = "myPicture/SeedPictures/PeaShooterSeed.png";
    String repeaterSeedImage = "myPicture/SeedPictures/RepeaterSeed.png";
    String wallNutSeedImage = "myPicture/SeedPictures/WallNutSeed.png";

    String notificationAudio = "src/ProjectFinal/myAudio/notification.wav";

    Button sunflowerSeed = new Button(), potatoMineSeed = new Button();
    Button peaShooterSeed = new Button(), repeaterSeed = new Button(), wallNutSeed = new Button();

    FileAndTools myTool = new FileAndTools();
    Plant plant = new Plant();

    private void controlSeeds(int maxAmountOfSun, int plantNum) {
        if (Sun.amountOfSun >= maxAmountOfSun && GameData.isPlatformFree() == 1) {
            setPlant = plantNum;
            plant.setPictureOnDragMouse (plantNum);
        }
        else {
            setPlant = 0;
            myTool.audioPlayer (notificationAudio);
        }
    }

    EventHandler<ActionEvent> sunflowerHandler = actionEvent -> controlSeeds(50, 1);

    EventHandler<ActionEvent> potatoMineHandler = actionEvent -> controlSeeds(25, 2);

    EventHandler<ActionEvent> peaShooterHandler = actionEvent -> controlSeeds(100, 3);

    EventHandler<ActionEvent> repeaterHandler = actionEvent -> controlSeeds(200, 4);

    EventHandler<ActionEvent> wallNutHandler = actionEvent -> controlSeeds(50, 5);

    private void setActions (Button seedButton, int plant) {
        switch (plant) {
            case 1:
                seedButton.setOnAction (sunflowerHandler);
                break;
            case 2:
                seedButton.setOnAction (potatoMineHandler);
                break;
            case 3:
                seedButton.setOnAction (peaShooterHandler);
                break;
            case 4:
                seedButton.setOnAction (repeaterHandler);
                break;
            case 5:
                seedButton.setOnAction (wallNutHandler);
                break;
        }
    }

    public Button getSeed (String imageFileName, Button seedButton, int X, int Y, int plant) {
        ImageView imageView = new ImageView ();
        myTool.setImageView (imageView, imageFileName, 80, 124, 0, 0, false);
        seedButton.setGraphic (imageView);
        seedButton.setTranslateX (X);
        seedButton.setTranslateY (Y);
        seedButton.setStyle ("-fx-background-color: Transparent;");
        setActions(seedButton, plant);
        return seedButton;
    }

    private void amountLabels(Label myLabel, String text, int Y) {
        myLabel.setText (text);
        myLabel.setTranslateX (-360);
        myLabel.setTranslateY (Y);
        myLabel.setFont (new Font("Arial", 15));
    }

    private void amountOfPlants (StackPane root) {
        Label sunflowerAmount = new Label(), peaShooterAmount = new Label(), repeaterAmount = new Label();
        Label potatoMineAmount = new Label(), wallNutAmount = new Label();
        root.getChildren().addAll (sunflowerAmount, potatoMineAmount, peaShooterAmount, repeaterAmount, wallNutAmount);
        amountLabels (sunflowerAmount, "50", -148);
        amountLabels (potatoMineAmount, "25", -64);
        amountLabels (peaShooterAmount, "100", 19);
        amountLabels (repeaterAmount, "200", 102);
        amountLabels (wallNutAmount, "50", 185);
    }

    public void createSeedButtons (StackPane root) {
        sunflowerSeed = getSeed (sunflowerSeedImage, sunflowerSeed, -400, -168, 1);
        potatoMineSeed = getSeed (potatoMineSeedImage, potatoMineSeed, -400, -84, 2);
        peaShooterSeed = getSeed (peaShooterSeedImage, peaShooterSeed, -400, -1, 3);
        repeaterSeed = getSeed (repeaterSeedImage, repeaterSeed, -400, 82, 4);
        wallNutSeed = getSeed (wallNutSeedImage, wallNutSeed, -400, 165, 5);
        root.getChildren().addAll (sunflowerSeed, potatoMineSeed, peaShooterSeed, repeaterSeed, wallNutSeed);
        amountOfPlants (root);
    }

}
