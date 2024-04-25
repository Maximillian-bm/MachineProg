package machineprog2.kortspilgui.model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Stack;

public class CardFountain extends CardContainer {
    private final Suit fountainSuit;
    private final Image fountainImage;
    private StackPane fountainRootStackPane;

    public CardFountain(VBox vBox, int index) {
        super(vBox, index);
        this.fountainSuit = switch (index) {
            case 0 -> Suit.Spades;
            case 1 -> Suit.Hearts;
            case 2 -> Suit.Clubs;
            case 3 -> Suit.Diamonds;
            default -> Suit.Spades;
        };
        this.fountainImage = getFountainImage();
    }

    public VBox getVBox() {
        return vBox;
    }

    public void addCard(Card card) {
        vBox.getChildren().add(card.getStackPane());
        cards.addLast(card);
        card.setIsInFountain(true);
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
        super.reset();
        CardJavaFX cardJavaFX = new CardJavaFX(1);
        cardJavaFX.imageView.setImage(fountainImage);
        vBox.getChildren().add(cardJavaFX.stackPane);
        fountainRootStackPane = cardJavaFX.stackPane;
    }

    public StackPane getStackPane() {
        return fountainRootStackPane;
    }

    private Image getFountainImage() {
        String imagePath = "/machineprog2/kortspilgui/art/Fountain" + fountainSuit + ".png";
        try {
            return new Image(Objects.requireNonNull(CardFountain.class.getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Error importing card image with path: " + imagePath);
            return null;
        }
    }
}
