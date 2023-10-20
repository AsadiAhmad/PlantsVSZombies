package ProjectFinal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import java.io.IOException;


public class MenuBarController {

    @FXML
    private Label user_name_label;
    @FXML
    private Button play_button, records_button, log_out_button, exit_button;
    @FXML
    private RadioButton easy_radio_button, medium_radio_button, extreme_radio_button, day_radio_button, night_radio_button;

    String uIFXMLFileName = "myFXMLFile/UI.fxml";
    String recordingFXMLFileName = "myFXMLFile/recording_page.fxml";
    String CSSFileNameEasyDay = "myCssFile/BackgroundDayEasy.css";
    String CSSFileNameMediumDay = "myCssFile/BackgroundDayMedium.css";
    String CSSFileNameExtremeDay = "myCssFile/BackgroundDayExtreme.css";
    String CSSFileNameEasyNight = "myCssFile/BackgroundNightEasy.css";
    String CSSFileNameMediumNight = "myCssFile/BackgroundNightMedium.css";
    String CSSFileNameExtremeNight = "myCssFile/BackgroundNightExtreme.css";
    String signInFileName = "src/ProjectFinal/myFile/SignIn.txt";
    String gameScoreFileName = "src/ProjectFinal/myFile/GameScore.txt";

    String tikAudio = "src/ProjectFinal/myAudio/tik.wav";

    String linearGradient1 = "-fx-background-color: linear-gradient(to right top, #2cc17e, #506e53)";
    String linearGradient2 = "-fx-background-color:  linear-gradient(to right top, #636e67, #425f44)";

    FileAndTools myTool = new FileAndTools();
    GamePlay myGamePanel = new GamePlay ();
    Seed seed = new Seed ();


    private void buttonEventIn (Button myButton, String color, String audioFileName) {
        myButton.setStyle (color);
        myTool.audioPlayer (audioFileName);
    }

    private void buttonEventOut (Button myButton, String color) {
        myButton.setStyle (color);
    }

    public void setUserNameLabel (String text) {
        user_name_label.setText (text);
    }

    @FXML
    private void onMouseEnteredPlayButton () {
        buttonEventIn (play_button, linearGradient1, tikAudio);
    }

    @FXML
    private void onMouseExitedPlayButton () {
        buttonEventOut (play_button, linearGradient2);
    }

    @FXML
    private void onClickPlayButton () {
        seed.createSeedButtons (GamePlay.root);
        if (easy_radio_button.isSelected() && day_radio_button.isSelected()) {
            myGamePanel.startGame (GamePlay.root, 1, true);
            myTool.createGamePage (user_name_label, CSSFileNameEasyDay, GamePlay.root);
        }
        else if (medium_radio_button.isSelected() && day_radio_button.isSelected()) {
            myGamePanel.startGame(GamePlay.root, 2,true);
            myTool.createGamePage (user_name_label, CSSFileNameMediumDay, GamePlay.root);
        }
        else if (extreme_radio_button.isSelected() && day_radio_button.isSelected()) {
            myGamePanel.startGame(GamePlay.root, 3,true);
            myTool.createGamePage (user_name_label, CSSFileNameExtremeDay, GamePlay.root);
        }
        else if (easy_radio_button.isSelected() && night_radio_button.isSelected()) {
            myGamePanel.startGame(GamePlay.root, 1, false);
            myTool.createGamePage (user_name_label, CSSFileNameEasyNight, GamePlay.root);
        }
        else if (medium_radio_button.isSelected() && night_radio_button.isSelected()) {
            myGamePanel.startGame(GamePlay.root, 2, false);
            myTool.createGamePage (user_name_label, CSSFileNameMediumNight, GamePlay.root);
        }
        else if (extreme_radio_button.isSelected() && night_radio_button.isSelected()) {
            myGamePanel.startGame(GamePlay.root, 3, false);
            myTool.createGamePage (user_name_label, CSSFileNameExtremeNight, GamePlay.root);
        }

    }

    @FXML
    private void onMouseEnteredRecordsButton () {
        buttonEventIn (records_button, linearGradient1, tikAudio);
    }

    @FXML
    private void onMouseExitedRecordsButton () {
        buttonEventOut (records_button, linearGradient2);
    }

    @FXML
    private void onClickRecordsButton () throws IOException {
        myTool.createRecordingPage (user_name_label, recordingFXMLFileName, signInFileName, gameScoreFileName);
    }

    @FXML
    private void onMouseEnteredLogOutButton () {
        buttonEventIn (log_out_button, linearGradient1, tikAudio);
    }

    @FXML
    private void onMouseExitedLogOutButton () {
        buttonEventOut (log_out_button, linearGradient2);

    }
    @FXML
    private void onClickLogOutButton () throws IOException {
        myTool.createPage(user_name_label, uIFXMLFileName);
    }

    @FXML
    private void onMouseEnteredExitButton () {
        buttonEventIn (exit_button, linearGradient1, tikAudio);
    }

    @FXML
    private void onMouseExitedExitButton () {
        buttonEventOut (exit_button, linearGradient2);
    }

    @FXML
    private void onClickExitButton () {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }

}
