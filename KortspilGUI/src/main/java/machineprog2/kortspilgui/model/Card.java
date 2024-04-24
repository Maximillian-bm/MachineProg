package machineprog2.kortspilgui.model;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Card {
    private final int value;
    private final Suit suit;
    private final CardJavaFX cardJavaFX;
    private final static Image image_cardBack = new Image(Objects.requireNonNull(Card.class.getResourceAsStream("/machineprog2/kortspilgui/art/cardback.png")));
    private final Image image_card;

    private boolean visible;
    private CardColumn column;

    public Card(int value, Suit suit, boolean visible) {
        //System.out.println("Making new card with suit " + suit + " and value " + value);
        this.suit = suit;
        this.value = value;
        this.visible = visible;

        image_card = getCardImage();
        cardJavaFX = new CardJavaFX();

        updateImageFromVisibility();
    }

    public void setCardColumn(CardColumn column) {
        this.column = column;
    }

    private Image getCardImage() {
        if (suit == null) {
            return image_cardBack;
        }
        String imagePath = "/machineprog2/kortspilgui/art/" + suit + value + ".png";
        try {
            return new Image(Objects.requireNonNull(Card.class.getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Error importing card image with path: " + imagePath);
            return null;
        }
    }

    public StackPane getStackPane() {
        return cardJavaFX.stackPane;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        updateImageFromVisibility();
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardColumn getColumn() {
        return column;
    }

    private void updateImageFromVisibility() {
        if (visible) {
            cardJavaFX.imageView.setImage(image_card);
        } else {
            cardJavaFX.imageView.setImage(image_cardBack);
        }
    }

    @Override
    public String toString() {
        return "{" + value + " of " + suit + "}";
    }
}
