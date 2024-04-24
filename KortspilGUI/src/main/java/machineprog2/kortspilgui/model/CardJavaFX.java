package machineprog2.kortspilgui.model;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class CardJavaFX {
    public final StackPane stackPane;
    public final ImageView imageView;
    public CardJavaFX() {
        imageView = new ImageView();
        imageView.setFitWidth(81);
        imageView.setFitHeight(126);
        stackPane = new StackPane(imageView);
        stackPane.setAlignment(Pos.TOP_CENTER);
        stackPane.setMinSize(Region.USE_COMPUTED_SIZE, 10);
        stackPane.setPrefSize(Region.USE_COMPUTED_SIZE, 25);
    }
}
