package machineprog2.kortspilgui.model;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class CardJavaFX {
    public StackPane stackPane;
    public ImageView imageView;
    public CardJavaFX() {
        createCardJavaFX();
    }
    public CardJavaFX(double height) {
        createCardJavaFX();
        stackPane.setMinSize(Region.USE_COMPUTED_SIZE, height);
        stackPane.setPrefSize(Region.USE_COMPUTED_SIZE, height);
        stackPane.setMaxSize(Region.USE_COMPUTED_SIZE, height);
    }
    public void createCardJavaFX() {
        imageView = new ImageView();
        imageView.setFitWidth(81);
        imageView.setFitHeight(126);
        stackPane = new StackPane(imageView);
        stackPane.setAlignment(Pos.TOP_CENTER);
        stackPane.setMinSize(Region.USE_COMPUTED_SIZE, 10);
        stackPane.setPrefSize(Region.USE_COMPUTED_SIZE, 25);
    }
}
