package ProjectFinal;

import java.util.Timer;

public class Repeater extends PeaShooter{

    public static int health = 10;

    public void repeaterPlay (int xPlantBlock, int yPlantBlock) {
        peashooterPlay (xPlantBlock, yPlantBlock);
        Timer timer = new java.util.Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        peashooterPlay (xPlantBlock, yPlantBlock);
                        timer.cancel();
                    }
                },
                300
        );
    }
}
