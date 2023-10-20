package ProjectFinal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class GameOverController {

    String menuBarFXMLFileName = "myFXMLFile/Menu_bar.fxml";
    String logInFileName = "src/ProjectFinal/myFile/LogIn.txt";

    public Button back_to_main_menu_button;
    public Label your_score_label;
    public Label your_high_score_label;

    FileAndTools myTool = new FileAndTools ();

    public void onClickBackToMainMenuButton () throws IOException {
        myTool.createMenuPage (your_score_label, menuBarFXMLFileName, myTool.gettingLastUser(logInFileName));
    }

    public void setGameScoreLabel (String text) {
        your_score_label.setText (text);
    }

    public void setGameHighScoreLabel (String text) {
        your_high_score_label.setText (text);
    }
}
