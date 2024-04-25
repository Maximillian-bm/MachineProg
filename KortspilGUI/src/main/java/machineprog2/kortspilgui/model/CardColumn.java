package machineprog2.kortspilgui.model;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CardColumn {
    private final VBox vBox;
    private final int columnNumber;
    private final Stack<Card> cards = new Stack<>();

    public CardColumn(VBox vBox, int columnNumber) {
        this.vBox = vBox;
        this.columnNumber = columnNumber;
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

    public void resetColumn() {
        vBox.getChildren().clear();
        cards.clear();
    }

    public int getCardRow(Card card) {
        return cards.indexOf(card);
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String columnAsString() {
        return "C" + (columnNumber + 1);
    }
}
