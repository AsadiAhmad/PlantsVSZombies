package ProjectFinal;

import javafx.scene.layout.StackPane;

public class GamePlay {

    Sun sun = new Sun ();
    Zombie zombie = new Zombie ();
    GameData data = new GameData ();
    LawnMover lawnMover = new LawnMover ();
    final public static StackPane root = new StackPane();
    public static int gameDifficulty = 0;
    public static boolean isItDay;

    private void switchDifficulty (int difficulty) {
        switch (difficulty) {
            case 1:
                GameData.fillPlatformForEasyGame();
                break;
            case 2:
                GameData.fillPlatformForMediumGame();
                break;
            case 3:
                GameData.fillPlatformForExtremeGame();
                break;
        }
    }

    public static int sunDurationTime (boolean isDay) {
        int sunDuration;
        if (isDay) {
            sunDuration = 10;
        }
        else {
            sunDuration = 20;
        }
        return sunDuration;
    }

    public void startGame (StackPane root, int difficulty, boolean isDay) {
        gameDifficulty = difficulty;
        isItDay = isDay;
        data.plantAliveUpdater();
        data.initializer();
        sun.addSun (root, difficulty, false,0 ,0, sunDurationTime (isDay));
        sun.setSunLabelPicture();
        sun.setSunLabel (root);
        root.getChildren().addAll (Plant.imageView);
        data.addScore();
        lawnMover.lawnMowerSwitch();
        zombie.zombieAdder();
        data.gameOverTimeline();
        switchDifficulty (difficulty);
    }
}
