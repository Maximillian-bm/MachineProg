package machineprog2.kortspilgui.util;

import machineprog2.kortspilgui.model.Card;
import machineprog2.kortspilgui.model.CardColumn;
import machineprog2.kortspilgui.model.Suit;

import java.util.ArrayList;
import java.util.List;

public class StringUtility {

    static public void updateColumnsFromString(List<CardColumn> columns, String input) {
        String[] tokens = input.split("\\s{2,}"); // Split by "  ". (Two spaces)

        for (int i = 0; i < 7; i++) {
            columns.get(i).resetColumn();
            List<Card> columnCards = getColumnCardsFromString(tokens[i]);

            for (Card card : columnCards) {
                columns.get(i).addCard(card);
            }
        }
    }

    static private List<Card> getColumnCardsFromString(String input) {
        List<Card> cardsFromString = new ArrayList<>();
        String[] tokens = input.split("(?<=\\G.{2})"); // Split by " ". (One space)

        for (String token : tokens) {
            cardsFromString.add(getCardFromString(token));
        }

        return cardsFromString;
    }

    static private Card getCardFromString(String input) {
        if (input.equals("[]")) {
            return new Card(0, null, false);
        } else {
            return new Card(Card.cardValueFromChar(input.charAt(0)), Card.cardSuitFromChar(input.charAt(1)), true);
        }
    }

    static public String formatMoveCommand(Card cardToMove, CardColumn toColumn) {
        return cardToMove.getColumn().columnAsString() + ":" + cardToMove + "->" + toColumn.columnAsString();
    }
}
