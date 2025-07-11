import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CongratulationsController {
    @FXML
    public void playAgain() throws Exception {
        Stage stage = (Stage) Stage.getWindows().filtered(w -> w.isShowing()).get(0);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/resources/game.fxml")));
        stage.setScene(scene);
    }
}
