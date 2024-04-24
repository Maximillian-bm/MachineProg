package machineprog2.kortspilgui;

import javafx.stage.Stage;
import machineprog2.kortspilgui.view.View;

import java.io.IOException;

public class Main extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        new View().start(stage);
    }
}
