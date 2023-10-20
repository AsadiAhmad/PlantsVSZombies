package ProjectFinal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.List;
import java.io.IOException;

public class RecordingPageController {

    String menuBarFXMLFileName = "myFXMLFile/Menu_bar.fxml";
    String logInFileName = "src/ProjectFinal/myFile/LogIn.txt";

    FileAndTools myTool = new FileAndTools ();

    public Label game_records_label;
    public Button back_to_main_menu_button_2;
    public ListView list_view;

    public void setList (List<String> myList) {
        for (String s : myList) {
            list_view.getItems().add(s);
        }
    }

    public void onClickBackToMainMenuButton2 () throws IOException {
        myTool.createMenuPage (game_records_label, menuBarFXMLFileName, myTool.gettingLastUser(logInFileName));
    }
}
