package machineprog2.kortspilgui.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import machineprog2.kortspilgui.Main;
import machineprog2.kortspilgui.controller.Controller;
import machineprog2.kortspilgui.controller.Observer;
import machineprog2.kortspilgui.model.Card;
import machineprog2.kortspilgui.util.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class View implements Observer {
    private Controller ctrl;
    private Scene scene;

    public void start(Stage stage) throws IOException {
        try {
            String path = System.getProperty("user.dir")+"\\MachineProg2.exe";
            System.out.println(path);
            ProcessBuilder processBuilder = new ProcessBuilder(path);
            processBuilder.start();
            System.out.println("Exe file started successfully.");
        }catch (IOException e) {
            System.out.println("Error while starting exe file: " + e.getMessage());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("kortscene.fxml"));
        scene = new Scene(fxmlLoader.load(), Constants.WINDOW_SIZE.width, Constants.WINDOW_SIZE.height, Color.DARKGRAY);
        stage.setTitle("Yukon");
        stage.setScene(scene);
        stage.show();
        ctrl = fxmlLoader.getController();
        ctrl.addObserver(this);
        // Handle key presses
        scene.setOnKeyPressed(event -> ctrl.keyPressed(event));
    }

    @Override
    public void onChange() {

    }


}
