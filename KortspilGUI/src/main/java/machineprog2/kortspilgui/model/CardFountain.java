package machineprog2.kortspilgui.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Stack;

public class CardFountain extends CardContainer {
    private final Suit fountainSuit;

    public CardFountain(VBox vBox, int index) {
        super(vBox, index);
        this.fountainSuit = switch (index) {
            case 0 -> Suit.Spades;
            case 1 -> Suit.Hearts;
            case 2 -> Suit.Clubs;
            case 3 -> Suit.Diamonds;
            default -> Suit.Spades;
        };
        this.backgroundImage = getBackgroundImage();
    }

    public VBox getVBox() {
        return vBox;
    }

    public void addCard(Card card) {
        vBox.getChildren().add(card.getStackPane());
        cards.addLast(card);
        card.setCardContainer(this);
        card.setIsInFountain(true);
        card.getStackPane().setMinSize(Region.USE_COMPUTED_SIZE, 1);
        card.getStackPane().setPrefSize(Region.USE_COMPUTED_SIZE, 1);
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public Card getTopCard() {
        return cards.getLast();
    }

    public Suit getFountainSuit() {
        return fountainSuit;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "F" + (index + 1);
    }

    @Override
    public void reset() {
        reset(1);
    }

    @Override
    protected Image getBackgroundImage() {
        String imagePath = "/machineprog2/kortspilgui/art/Fountain" + fountainSuit + ".png";
        try {
            return new Image(Objects.requireNonNull(CardFountain.class.getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Error importing card image with path: " + imagePath);
            return null;
        }
    }
}
