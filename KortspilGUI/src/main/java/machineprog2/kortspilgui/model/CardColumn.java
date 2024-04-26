package machineprog2.kortspilgui.model;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class CardColumn extends CardContainer {
    public CardColumn(VBox vBox, int index) {
        super(vBox, index);
        backgroundImage = getBackgroundImage();
    }

    public VBox getVBox() {
        return vBox;
    }

    public void addCard(Card card) {
        vBox.getChildren().add(card.getStackPane());
        cards.addLast(card);
        card.setCardContainer(this);
        card.setIsInFountain(false);
    }

    @Override
    public String toString() {
        return "C" + (index + 1);
    }

    @Override
    protected Image getBackgroundImage() {
        String imagePath = "/machineprog2/kortspilgui/art/ColumnDummyCard.png";
        try {
            return new Image(Objects.requireNonNull(CardFountain.class.getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Error importing card image with path: " + imagePath);
            return null;
        }
    }
}
