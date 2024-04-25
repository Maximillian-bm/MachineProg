package machineprog2.kortspilgui.model;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CardColumn extends CardContainer {
    public CardColumn(VBox vBox, int index) {
        super(vBox, index);
    }

    public VBox getVBox() {
        return vBox;
    }

    public void addCard(Card card) {
        vBox.getChildren().add(card.getStackPane());
        cards.addLast(card);
        card.setCardColumn(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<Card> getCardsOnTop(Card fromCard) { // Returns the same card with the cards that are above
        return new ArrayList<>(cards.subList(cards.indexOf(fromCard), cards.size()));
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public Card getTopCard() {
        return cards.getLast();
    }

    public int getCardRow(Card card) {
        return cards.indexOf(card);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "C" + (index + 1);
    }
}
