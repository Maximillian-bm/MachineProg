package machineprog2.kortspilgui.model;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Stack;

public abstract class CardContainer {
    protected final VBox vBox;
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

    public void reset() {
        vBox.getChildren().clear();
        cards.clear();
    }

}
