package ProjectFinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("myFXMLFile/UI.fxml"));
        primaryStage.initStyle (StageStyle.TRANSPARENT);
        Scene myScene = new Scene (root);
        myScene.setFill (Color.TRANSPARENT);
        primaryStage.setScene (myScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
