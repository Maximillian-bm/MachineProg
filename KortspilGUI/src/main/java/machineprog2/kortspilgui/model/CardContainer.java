package machineprog2.kortspilgui.model;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class CardContainer {
    protected final VBox vBox;
    protected Image backgroundImage;
    protected StackPane rootStackPane;
    protected final int index;
    protected final Stack<Card> cards = new Stack<>();

    public CardContainer(VBox vBox, int index) {
        this.vBox = vBox;
        this.index = index;
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

    public StackPane getStackPane() {
        return rootStackPane;
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<Card> getCardsOnTop(Card fromCard) { // Returns the same card with the cards that are above
        return new ArrayList<>(cards.subList(cards.indexOf(fromCard), cards.size()));
    }

    public int getCardRow(Card card) {
        return cards.indexOf(card);
    }

    public int getIndex() {
        return index;
    }

    public void reset() {
        reset(0);
    }

    public void reset(int height) {
        vBox.getChildren().clear();
        cards.clear();
        CardJavaFX cardJavaFX = new CardJavaFX(height);
        cardJavaFX.imageView.setImage(backgroundImage);
        vBox.getChildren().add(cardJavaFX.stackPane);
        rootStackPane = cardJavaFX.stackPane;
    }

    protected Image getBackgroundImage() {
        return null;
    }
}
