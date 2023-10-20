package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class Plant {

    String sunflowerImage = "myPicture/Sunflower2.png";
    String potatoMineImage = "myPicture/PotatoMine.png";
    String peaShooterImage = "myPicture/PeaShooter.png";
    String repeaterImage = "myPicture/Repeater.png";
    String wallNutImage = "myPicture/WallNut.png";
    String potatoMineUnderImage = "myPicture/PotatoMineUnder.png";

    String sunflowerGif = "myPicture/gif/SunflowerGif.gif";
    String peaShooterGif = "myPicture/gif/PeaShooterGif.gif";
    String repeaterGif = "myPicture/gif/RepeaterGif.gif";
    String wallNutGif = "myPicture/gif/WallNutGif.gif";

    public static ImageView imageView = new ImageView ();

    Sun sun = new Sun ();
    PotatoMine potatoMine = new PotatoMine ();
    PeaShooter peaShooter = new PeaShooter ();
    Repeater repeater = new Repeater ();
    FileAndTools myTool = new FileAndTools ();

    private double XMouse = 0, YMouse = 0;

    private final EventHandler<MouseEvent> setXY = event -> {
        imageView.setTranslateX(event.getX() - 512);
        imageView.setTranslateY(event.getY() - 335);
        XMouse = event.getX();
        YMouse = event.getY();
    };

    private String backFileName (int plantNum, String sunflower, String potatoMine, String peaShooter
            , String repeater, String wallNut) {
        String plantPicture = sunflower;
        switch (plantNum) {
            case 1:
                plantPicture = sunflower;
                break;
            case 2:
                plantPicture = potatoMine;
                break;
            case 3:
                plantPicture = peaShooter;
                break;
            case 4:
                plantPicture = repeater;
                break;
            case 5:
                plantPicture = wallNut;
                break;
        }
        return plantPicture;
    }

    public static int squareSizeX () {
        int result = 80;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = 72;
                break;
            case 2:
                result = 80;
                break;
            case 3:
                result = 90;
                break;
        }
        return result;
    }

    public static int squareSizeY () {
        int result = 96;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = 80;
                break;
            case 2:
                result = 96;
                break;
            case 3:
                result = 120;
                break;
        }
        return result;
    }

    public static int sizeXForDifficulty () {
        int result = 0;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = 10;
                break;
            case 2:
                result = 9;
                break;
            case 3:
                result = 8;
                break;
        }
        return result;
    }

    public static int sizeYForDifficulty () {
        int result = 0;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = 6;
                break;
            case 2:
                result = 5;
                break;
            case 3:
                result = 4;
                break;
        }
        return result;
    }

    public static int getWhereIsPlantXBlock (double X, int startX) {
        for (int counter = 0; counter < sizeXForDifficulty(); counter++) {
            if ((counter * squareSizeX()) + startX < X && X < ((counter + 1) * squareSizeX()) + startX) {
                return counter;
            }
        }
        return -1;
    }

    private int getWhereIsPlantYBlock (double Y) {
        for (int counter = 0; counter < sizeYForDifficulty(); counter++) {
            if ((counter * squareSizeY()) + 123 < Y && Y < ((counter + 1) * squareSizeY()) + 123) {
                return counter;
            }
        }
        return -1;
    }

    private int placeForPlantX (int X) {
        return (X * squareSizeX ()) - 210;
    }

    private int placeForPlantY (int Y) {
        return (Y * squareSizeY ()) - 170;
    }

    private int plantCost (int plantNum) {
        int result = 0;
        switch (plantNum) {
            case 1:
            case 5:
                result = 50;
                break;
            case 2:
                result = 25;
                break;
            case 3:
                result = 100;
                break;
            case 4:
                result = 200;
                break;
        }
        return result;
    }

    private void plantChoose (int plantNum, int xPlantBlock, int yPlantBlock) {
        switch (plantNum) {
            case 1:
                sun.addSun (GamePlay.root, GamePlay.gameDifficulty, true, xPlantBlock
                        , yPlantBlock, GamePlay.sunDurationTime(GamePlay.isItDay));
                break;
            case 2:
                potatoMine.potatoMinePlay (xPlantBlock, yPlantBlock);
                break;
            case 3:
                peaShooter.peashooterPlay (xPlantBlock, yPlantBlock);
                break;
            case 4:
                repeater.repeaterPlay (xPlantBlock, yPlantBlock);
                break;
        }
    }

    private int getDecreaseHealth () {
        int result = 0;
        switch (GamePlay.gameDifficulty) {
            case 1:
                result = -1;
                break;
            case 2:
                result = 0;
                break;
            case 3:
                result = 1;
                break;
        }
        return result;
    }

    private int getPlantHealth (int plantNum) {
        int result = 0;
        switch (plantNum) {
            case 1:
                result = Sunflower.health - getDecreaseHealth();
                break;
            case 2:
                result = PotatoMine.health - getDecreaseHealth();
                break;
            case 3:
                result = PeaShooter.health - getDecreaseHealth();
                break;
            case 4:
                result = Repeater.health - getDecreaseHealth();
                break;
            case 5:
                result = WallNut.health - (getDecreaseHealth() * 5);
                break;
        }
        return result;
    }

    private int deadPlant (ImageView myImageView, int xPlantBlock, int yPlantBlock, int result) {
        if (GameData.plant[yPlantBlock][xPlantBlock] == 0 && result == 0) {
            GamePlay.root.getChildren().removeAll (myImageView);
            result = 1;
        }
        return result;
    }

    private void diePlant (ImageView myImageView, int xPlantBlock, int yPlantBlock) {
        AtomicInteger result = new AtomicInteger();
        result.set(0);
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> result.set(deadPlant(myImageView, xPlantBlock, yPlantBlock, result.get()))));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private int plantImageSize (int plantNum) {
        int size;
        if (plantNum == 5) {
            size = 70;
        }
        else if (plantNum == 2) {
            size = 30;
        }
        else {
            size = 90;
        }
        return size;
    }

    private void setPlantImage (ImageView myImageView, int xPlantBlock, int yPlantBlock, int plantNum) {
        int size = plantImageSize(plantNum);
        GamePlay.root.getChildren().addAll (myImageView);
        myTool.setImageView (myImageView, backFileName (plantNum, sunflowerGif, potatoMineUnderImage, peaShooterGif
                , repeaterGif, wallNutGif), size, size, 0, 0, false);
        potatoMine.setPotatoMineImage (myImageView, plantNum);
        myImageView.setTranslateX (placeForPlantX(xPlantBlock));
        myImageView.setTranslateY (placeForPlantY(yPlantBlock));
    }

    private void successfullyPlanting (int xPlantBlock, int yPlantBlock, int plantNum) {
        ImageView myImageView = new ImageView();
        setPlantImage (myImageView, xPlantBlock, yPlantBlock, plantNum);
        GameData.plant[yPlantBlock][xPlantBlock] = plantNum;
        GameData.plantHealth[yPlantBlock][xPlantBlock] = getPlantHealth (plantNum);
        Sun.amountOfSun -= plantCost (plantNum);
        plantChoose (plantNum, xPlantBlock, yPlantBlock);
        diePlant (myImageView, xPlantBlock, yPlantBlock);
    }

    private void plantingAPlant (int plantNum) {
        int xPlantBlock = getWhereIsPlantXBlock (XMouse, 261);
        int yPlantBlock = getWhereIsPlantYBlock (YMouse);
        imageView.setTranslateX (500);
        imageView.setTranslateY (500);
        GamePlay.root.removeEventHandler(MouseEvent.MOUSE_MOVED, setXY);
        if (xPlantBlock != -1 &&  yPlantBlock != -1 && GameData.plant[yPlantBlock][xPlantBlock] == 0) {
            successfullyPlanting (xPlantBlock, yPlantBlock, plantNum);
        }
    }

    public void setPictureOnDragMouse (int plantNum) {
        myTool.setImageView (imageView, backFileName (plantNum, sunflowerImage, potatoMineImage, peaShooterImage, repeaterImage, wallNutImage), 50, 50, 0, 0, false);
        GamePlay.root.addEventHandler (MouseEvent.MOUSE_MOVED, setXY);
        imageView.setOnMouseClicked (mouseEvent -> plantingAPlant(plantNum));
    }
}
