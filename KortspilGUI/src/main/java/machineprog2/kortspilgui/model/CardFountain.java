package machineprog2.kortspilgui.model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CardFountain {
    private final VBox vBox;
    private final int fountainNumber;
    private final Suit fountainSuit;
    private final Image fountainImage;
    private final StackPane fountainRootStackPane;
    private final Stack<Card> cards = new Stack<>();

    public CardFountain(VBox vBox, int fountainNumber) {
        this.vBox = vBox;
        this.fountainNumber = fountainNumber;
        this.fountainSuit = switch (fountainNumber) {
            case 0 -> Suit.Spades;
            case 1 -> Suit.Hearts;
            case 2 -> Suit.Clubs;
            case 3 -> Suit.Diamonds;
            default -> Suit.Spades;
        };
        this.fountainImage = getFountainImage();
        this.fountainRootStackPane = findStackPane(vBox);
    }

    public VBox getVBox() {
        return vBox;
    }

    public void addCard(Card card) {
        vBox.getChildren().add(card.getStackPane());
        cards.addLast(card);
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

    public void resetFountain() {
        vBox.getChildren().clear();
        cards.clear();
    }

    public int getFountainNumber() {
        return fountainNumber;
    }

    public String fountainAsString() {
        return "F" + (fountainNumber + 1);
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

    private StackPane findStackPane(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof StackPane) {
                return (StackPane) node;
            } else if (node instanceof Parent) {
                StackPane stackPane = findStackPane((Parent) node);
                if (stackPane != null) {
                    return stackPane;
                }
            }
        }
        return null;
    }
}
