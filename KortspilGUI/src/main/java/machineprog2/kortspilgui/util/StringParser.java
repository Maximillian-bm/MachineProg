package machineprog2.kortspilgui.util;

import machineprog2.kortspilgui.model.Card;
import machineprog2.kortspilgui.model.CardColumn;
import machineprog2.kortspilgui.model.Suit;

import java.util.ArrayList;
import java.util.List;

public class StringParser {

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
            int value = switch (input.substring(0, 1)) {
                case "A" -> 1;
                case "T" -> 10;
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                default -> Integer.parseInt(input.substring(0, 1));
            };

            Suit suit = switch (input.substring(1)) {
                case "S" -> Suit.Spades;
                case "H" -> Suit.Hearts;
                case "C" -> Suit.Clubs;
                case "D" -> Suit.Diamonds;
                default -> Suit.Spades;
            };
            return new Card(value, suit, true);
        }
    }
}
