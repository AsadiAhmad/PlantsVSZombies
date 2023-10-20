package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Pea {

    String peaImage = "myPicture/Pea.png";
    FileAndTools myTool = new FileAndTools ();

    private void peaThreadDelete (Timeline timeline, ImageView myImageView, int yPeaBlock, int nowWhatPea) {
        if (myImageView.getTranslateX() > 600 || GameData.pea[yPeaBlock].get (nowWhatPea) == 700) {
            GamePlay.root.getChildren().remove(myImageView);
            timeline.stop();
        }
    }

    private void peaThreadDeleter (Timeline peaTimeline, ImageView myImageView, int yPeaBlock, int nowWhatPea) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(1),
                (event) -> peaThreadDelete (peaTimeline, myImageView, yPeaBlock, nowWhatPea)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    public boolean isThereZombieInARow (int xPlantBlock, int yPlantBlock) {
        for (int counter = 0; counter < GameData.zombies[yPlantBlock].size(); counter++) {
            if (GameData.zombies[yPlantBlock].get (counter) > (Plant.squareSizeX() * xPlantBlock) - 180) {
                return true;
            }
        }
        return false;
    }

    private void movePea (ImageView myImageView, int peaYRowBlock, int nowWhatPea) {
        if (GameData.pea[peaYRowBlock].get (nowWhatPea) != 700) {
            GameData.pea[peaYRowBlock].set (nowWhatPea, (int) Math.round(myImageView.getTranslateX ()));
        }
        myImageView.setTranslateX (myImageView.getTranslateX () + 3);
    }

    private void peaThread (ImageView myImageView, int peaYRowBlock) {
        int nowWhatPea = GameData.peaNumber[peaYRowBlock];
        GameData.pea[peaYRowBlock].add (nowWhatPea, (int) Math.round(myImageView.getTranslateX ()));
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> movePea(myImageView, peaYRowBlock, nowWhatPea)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
        peaThreadDeleter(myTimeline, myImageView, peaYRowBlock, nowWhatPea);
        GameData.peaNumber[peaYRowBlock]++;
    }

    public void addPea (int xPlantBlock, int yPlantBlock) {
        int xPlantSize = (Plant.squareSizeX() * xPlantBlock) - 180;
        int yPlantSize = (Plant.squareSizeY() * yPlantBlock) - 180;
        ImageView imageView = new ImageView ();
        myTool.setImageView (imageView, peaImage, 25, 25, xPlantSize, yPlantSize, true);
        peaThread (imageView, yPlantBlock);
        GamePlay.root.getChildren().addAll (imageView);
    }
}
