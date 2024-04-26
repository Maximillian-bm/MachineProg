package machineprog2.kortspilgui.util;

import machineprog2.kortspilgui.model.Card;
import machineprog2.kortspilgui.model.CardColumn;
import machineprog2.kortspilgui.model.CardContainer;
import machineprog2.kortspilgui.model.CardFountain;

import java.util.ArrayList;
import java.util.List;

public class StringUtility {

    static public void updateBoardFromString(List<CardColumn> columns, List<CardFountain> fountains, String input) {

        String[] tokens = input.split(" "); // Split by "  ". (Two spaces)
        for (int i = 0; i < 7; i++) {
            List<Card> columnCards = getCardsFromString(tokens[i]);
            for (Card card : columnCards) {
                columns.get(i).addCard(card);
            }
        }
        for (int i = 7; i < 11; i++) {
            List<Card> fountainsCards = getCardsFromString(tokens[i]);
            for (Card card : fountainsCards) {
                fountains.get(i - 7).addCard(card);
            }
        }
    }

    static private List<Card> getCardsFromString(String input) {
        input = input.substring(0, input.length() - 1);
        List<Card> cardsFromString = new ArrayList<>();
        String[] tokens = input.split("(?<=\\G.{2})"); // Split by " ". (One space)
        for (String token : tokens) {
            if(!token.isEmpty()) cardsFromString.add(getCardFromString(token));
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

    static public String formatMoveCommand(Card cardToMove, CardContainer fromContainer, CardContainer toContainer) {
        return fromContainer.toString() + ":" + cardToMove + "->" + toContainer.toString();
    }
}
