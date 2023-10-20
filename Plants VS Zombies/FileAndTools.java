package ProjectFinal;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileAndTools {

    public synchronized void audioPlayer (String fileName) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    File myFile = new File(fileName);
                    AudioInputStream myAudio = AudioSystem.getAudioInputStream(myFile);
                    Clip myClip = AudioSystem.getClip();
                    myClip.open(myAudio);
                    myClip.start();
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException error) {
                    error.printStackTrace();
                }
            }
        }).start();
    }

    public void createGameOverPage (String fileName, String score, String gameScoreFileName, String logInFileName) throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle (StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = loader.load ();
        GameOverController myController = loader.getController();
        myController.setGameScoreLabel (score);
        myController.setGameHighScoreLabel (getBestScoreOfUser(gameScoreFileName, gettingLastUser(logInFileName)));
        Scene myScene = new Scene (root);
        myScene.setFill (Color.TRANSPARENT);
        primaryStage.setScene (myScene);
        myScene.getWindow().centerOnScreen();
        primaryStage.show();
    }

    public void createMenuPage (Label myLabel, String fileName, String userName) throws IOException {
        Stage primaryStage = (Stage) myLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = loader.load ();
        MenuBarController myController = loader.getController();
        myController.setUserNameLabel (userName);
        Scene myScene = new Scene (root);
        myScene.setFill (Color.TRANSPARENT);
        primaryStage.setScene (myScene);
        primaryStage.show();
    }

    public void createRecordingPage (Label myLabel, String fileName, String signINFileName, String gameScoreFileName) throws IOException {
        Stage primaryStage = (Stage) myLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = loader.load ();
        RecordingPageController myController = loader.getController();
        List<String> myList = new ArrayList<>();
        getFinalScoreList (myList, signINFileName, gameScoreFileName);
        myController.setList (myList);
        Scene myScene = new Scene (root);
        myScene.setFill (Color.TRANSPARENT);
        primaryStage.setScene (myScene);
        primaryStage.show();
    }

    public void createPage (Label myLabel, String fileName) throws IOException {
        Stage primaryStage = (Stage) myLabel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene myScene = new Scene (root);
        myScene.setFill (Color.TRANSPARENT);
        primaryStage.setScene (myScene);
        primaryStage.show();
    }

    public void createGamePage (Label myLabel, String CSSFileName, StackPane root) {
        root.setId("pane");
        Stage primaryStage = (Stage) myLabel.getScene().getWindow();
        Scene myScene = new Scene (root,1024,670);
        myScene.getStylesheets().addAll(this.getClass().getResource(CSSFileName).toExternalForm());
        primaryStage.setTitle ("Exercise 2");
        primaryStage.setScene (myScene);
        myScene.getWindow().centerOnScreen();
        primaryStage.show ();
    }

    private void checkEmptyTextField (String userName, String password, Label error_user_name_label, Label error_password_label
        , String notificationAudio) {
        if (userName.length() == 0) {
            error_user_name_label.setText ("User Name text field is empty !");
        }
        if (password.length() == 0) {
            error_password_label.setText ("Password text field is empty !");
        }
        if (userName.length() == 0 || password.length() == 0) {
            audioPlayer (notificationAudio);
        }
    }

    private void writeFirstUser (String fileName) {
        try {
            File myFile = new File(fileName);
            FileOutputStream myStream = new FileOutputStream(myFile);
            OutputStreamWriter myWriter = new OutputStreamWriter(myStream);

            myWriter.write("Ahmad" + "\n");
            myWriter.write("Asadi" + "\n");

            myWriter.close();
            myStream.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }

    private int checkUserName (String fileName, String userName) {
        int result = -1;
        int counter = 0;
        try {
            File myFile = new File(fileName);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine ()) {
                counter++;
                String data = myScanner.nextLine ();
                if (data.compareTo (userName) == 0 && counter % 2 == 1) {
                    result = 1; // userName exist
                    break;
                }
                else {
                    result = 0; // userName not exist
                }
            }
            myScanner.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        return result;
    }

    private void writeUserData (String fileName, List<String> list1) {
        try {
            FileWriter myWriter = new FileWriter (fileName, true);
            BufferedWriter myBuffer = new BufferedWriter (myWriter);
            for (String str: list1) {
                myBuffer.write (str + System.lineSeparator());
            }
            myBuffer.close();
            myWriter.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void getUserData (String fileName, String userName, String field) {
        List<String> data = new ArrayList<>();
        data.add (userName);
        data.add (field);
        writeUserData (fileName, data);
    }

    private void createFile (String fileName) throws IOException {
        File myFile = new File (fileName);
        boolean fileCreated = true;
        if (!myFile.exists ()) {
            fileCreated = myFile.createNewFile ();
            writeFirstUser (fileName);
        }
        if (!fileCreated) {
            throw new IOException("Unable to create file at specified path. It already exists");
        }
    }

    public void signIn (String fileName, String gameScoreFileName, String userName, String password, Label error_user_name_label, Label error_password_label
    , String notificationAudio, String accessAudio) throws IOException {
        error_user_name_label.setText ("");
        error_password_label.setText ("");
        createFile(fileName);
        checkEmptyTextField (userName, password, error_user_name_label, error_password_label, notificationAudio);
        if (userName.length() != 0 && password.length() != 0) {
            if (checkUserName (fileName, userName) == 0) {
                getUserData (fileName, userName, password);
                audioPlayer (accessAudio);
                getUserData (gameScoreFileName, userName, String.valueOf(0));
            }
            else {
                error_user_name_label.setText ("User Name is already exist !");
                audioPlayer (notificationAudio);
            }
        }
    }

    private int checkUserNameAndPassword (String fileName, String userName, String Password) {
        int result = -1, result1 = -1, counter = 0, numLine = 0;
        try {
            File myFile = new File(fileName);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine ()) {
                counter++;
                String data = myScanner.nextLine ();
                if (data.compareTo (userName) == 0 && counter % 2 == 1) {
                    result1 = 0;
                    result = 0; // userName exist
                    numLine = counter + 1;
                }
                else if (result == 0 && numLine == counter && data.compareTo (Password) == 0) {
                    result = 1; // userName and password exist
                    break;
                }
                else {
                    result = 2; // userName not exist
                }
            }
            myScanner.close();
            if (result1 == 0 && result != 1) {
                result = 0;
            }
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        return result;
    }

    private void settingLastUser (String fileName, String userName) throws IOException {
        File myFile = new File (fileName);
        boolean fileCreated = true;
        if (!myFile.exists ()) {
            fileCreated = myFile.createNewFile ();
        }
        if (!fileCreated) {
            throw new IOException("Unable to create file at specified path. It already exists");
        }
        try {
            FileWriter myWriter = new FileWriter (myFile);
            myWriter.write (userName);
            myWriter.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }

    public String gettingLastUser (String fileName) {
        String result = "null";
        try {
            File myFile = new File(fileName);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine()) {
                result = myScanner.nextLine();
            }
            myScanner.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        return result;
    }

    public void logIn (String fileName, String logInFileName, String fXMLFileName, String userName, String password, Label error_user_name_label, Label error_password_label
    , String notificationAudio, String accessAudio) throws IOException {
        error_user_name_label.setText ("");
        error_password_label.setText ("");
        checkEmptyTextField (userName, password, error_user_name_label, error_password_label, notificationAudio);
        int result = checkUserNameAndPassword (fileName, userName, password);
        if (userName.length() != 0 && password.length() != 0) {
            if (result == 0) {
                error_user_name_label.setText ("User Name exist");
                error_password_label.setText ("Wrong Password !");
                audioPlayer (notificationAudio);
            }
            else if (result == 2) {
                error_user_name_label.setText ("User Name not exist !");
                audioPlayer (notificationAudio);
            }
            else {
                audioPlayer (accessAudio);
                createMenuPage (error_user_name_label, fXMLFileName, userName);
                settingLastUser (logInFileName, userName);
            }
        }
    }

    public void setImageView (ImageView myImageView, String fileName, int FitHeight, int FitWidth, int translateX, int translateY, boolean isItMove) {
        InputStream input = getClass().getResourceAsStream(fileName);
        Image image = new Image(input);
        myImageView.setImage (image);
        myImageView.setFitHeight(FitHeight);
        myImageView.setFitWidth (FitWidth);
        if (isItMove) {
            myImageView.setTranslateX (translateX);
            myImageView.setTranslateY (translateY);
        }
    }

    private void plantThreadDelete (Timeline timeline, int xPlantBlock, int yPlantBlock) {
        if (GameData.plant[yPlantBlock][xPlantBlock] == 0) {
            timeline.stop();
        }
    }

    public void plantThreadDeleter (Timeline plantTimeline, int xPlantBlock, int yPlantBlock) {
        Timeline myTimeline = new Timeline (new KeyFrame(Duration.millis(10),
                (event) -> plantThreadDelete (plantTimeline, xPlantBlock, yPlantBlock)));
        myTimeline.setCycleCount (Animation.INDEFINITE);
        myTimeline.play();
    }

    private String getBestScoreOfUser (String fileName, String userName) {
        int counter = 0, highScore = 0, count = 0;
        try {
            File myFile = new File(fileName);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine ()) {
                counter++;
                String data = myScanner.nextLine ();
                if (data.equals(userName)) {
                    count = counter + 1;
                }
                if (count == counter && Integer.parseInt(data) > highScore) {
                    highScore = Integer.parseInt(data);
                }
            }
            myScanner.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        return String.valueOf(highScore);
    }

    private void setBestUsersScoreList (List<String> myList, String signInFileName, String gameScoreFileName) {
        int counter = 0;
        try {
            File myFile = new File(signInFileName);
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine ()) {
                counter++;
                String data = myScanner.nextLine ();
                if ((counter % 2) == 1) {
                    myList.add (getBestScoreOfUser (gameScoreFileName, data));
                    myList.add (data);
                }
            }
            myScanner.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }

    private int foundHighScoreFromList (List<String> myList) {
        int highScore = 0;
        for (int counter = 0; counter < myList.size(); counter += 2) {
            if (highScore < Integer.parseInt(myList.get(counter))) {
                highScore = Integer.parseInt(myList.get(counter));
            }
        }
        return highScore;
    }

    private int whichRowHaveHighScore (List<String> myList, int highScore) {
        int result = 0;
        for (int counter = 0; counter < myList.size(); counter += 2) {
            if (Integer.parseInt(myList.get(counter)) == highScore) {
                result = counter;
            }
        }
        return result;
    }

    public void getBestScoreList (List<String> bestList, String signInFileName, String gameScoreFileName) {
        List<String> myList = new ArrayList<>();
        setBestUsersScoreList (myList, signInFileName, gameScoreFileName);
        int row;
        int size = myList.size();
        for (int counter = 0; counter < size; counter += 2) {
            row = whichRowHaveHighScore (myList, foundHighScoreFromList(myList));
            bestList.add (myList.get(row));
            bestList.add (myList.get(row + 1));
            myList.remove (row);
            myList.remove (row);
        }
    }

    public void getWorstScoreList (List<String> badList, String signInFileName, String gameScoreFileName) {
        List<String> bestList = new ArrayList<>();
        getBestScoreList (bestList, signInFileName, gameScoreFileName);
        int size = bestList.size();
        for (int counter = size - 1; counter >= 0; counter--) {
            badList.add (bestList.get(counter));
        }
    }

    public void getFinalScoreList (List<String> finalList, String signInFileName, String gameScoreFileName) {
        List<String> badList = new ArrayList<>();
        getWorstScoreList (badList, signInFileName, gameScoreFileName);
        String result;
        int size = badList.size();
        for (int counter = 0; counter < size; counter += 2) {
            result = badList.get(counter);
            result = result + " : " + badList.get(counter + 1);
            finalList.add (result);
        }
    }



}
