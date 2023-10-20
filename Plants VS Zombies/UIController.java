package ProjectFinal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import java.io.IOException;

public class UIController {

    @FXML
    private Label user_name_label, password_label;
    @FXML
    private Line user_name_line, password_line;
    @FXML
    private Button sign_in_button, log_in_button;
    @FXML
    private TextField user_name_text_field, password_text_field;
    @FXML
    public Label error_user_name_label, error_password_label;

    String signInFileName = "src/ProjectFinal/myFile/SignIn.txt";
    String logInFileName = "src/ProjectFinal/myFile/LogIn.txt";
    String gameScoreFileName = "src/ProjectFinal/myFile/GameScore.txt";
    String menuBarFXMLFileName = "myFXMLFile/Menu_bar.fxml";

    String notificationAudio = "src/ProjectFinal/myAudio/notification.wav";
    String accessAudio = "src/ProjectFinal/myAudio/access.wav";
    String tikAudio = "src/ProjectFinal/myAudio/tik.wav";

    String colorBlue = "-fx-stroke: blue;";
    String colorBlack = "-fx-stroke: black;";
    String colorOrange = "-fx-background-color: orange;";
    String colorDarkOrange = "-fx-background-color: #ff8c00;";

    FileAndTools myTool = new FileAndTools ();

    private void textFieldEvent (Label myLabel, Label myLabel2, Line myLine, String label, String color, int changeX, int changeY) {
        myLabel.setText (label);
        myLabel2.setLayoutY (myLabel2.getLayoutY () + changeY);
        myLine.setEndX (myLine.getEndX () + changeX);
        myLine.setEndY (myLine.getEndY () + changeY);
        myLine.setStartY (myLine.getStartY () + changeY);
        myLine.setStyle (color);
    }

    private void buttonEventIn (Button myButton, String color, String audioFileName) {
        myButton.setStyle (color);
        myTool.audioPlayer (audioFileName);
    }

    private void buttonEventOut (Button myButton, String color) {
        myButton.setStyle (color);
    }

    @FXML
    private void onMouseEnteredUserName () {
        textFieldEvent (user_name_label, error_user_name_label, user_name_line, "User Name", colorBlue, 10, 10);
    }

    @FXML
    private void onMouseExitedUserName () {
        textFieldEvent (user_name_label, error_user_name_label, user_name_line, "", colorBlack, -10, -10);
    }

    @FXML
    private void onMouseEnteredPassword () {
        textFieldEvent (password_label, error_password_label, password_line, "Password", colorBlue, 10, 10);
    }

    @FXML
    private void onMouseExitedPassword () {
        textFieldEvent (password_label, error_password_label, password_line, "", colorBlack, -10, -10);
    }

    @FXML
    private void onMouseEnteredSignIn () {
        buttonEventIn (sign_in_button, colorDarkOrange, tikAudio);
    }

    @FXML
    private void onMouseExitedSignIn () {
        buttonEventOut (sign_in_button, colorOrange);
    }

    @FXML
    private void onMouseEnteredLogIn () {
        buttonEventIn (log_in_button, colorDarkOrange, tikAudio);
    }

    @FXML
    private void onMouseExitedLogIn () {
        buttonEventOut (log_in_button, colorOrange);
    }

    @FXML
    private void onClickSignInButton () throws IOException {
        myTool.signIn (signInFileName, gameScoreFileName, user_name_text_field.getText (), password_text_field.getText ()
        , error_user_name_label, error_password_label, notificationAudio, accessAudio);
    }

    @FXML
    private void onClickLogInButton () throws IOException {
        myTool.logIn (signInFileName, logInFileName, menuBarFXMLFileName,user_name_text_field.getText (), password_text_field.getText ()
        , error_user_name_label, error_password_label, notificationAudio, accessAudio);
    }
}
