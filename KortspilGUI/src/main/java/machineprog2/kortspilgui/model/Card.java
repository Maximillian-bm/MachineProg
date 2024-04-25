package machineprog2.kortspilgui.model;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Card {
    private final int value;
    private final Suit suit;
    private final CardJavaFX cardJavaFX;
    private final static Image image_cardBack = new Image(Objects.requireNonNull(Card.class.getResourceAsStream("/machineprog2/kortspilgui/art/cardback.png")));
    private final Image image_card;

    private boolean visible;
    private CardColumn column;
    private boolean isInFountain;

    public Card(int value, Suit suit, boolean visible) {
        //System.out.println("Making new card with suit " + suit + " and value " + value);
        this.suit = suit;
        this.value = value;
        this.visible = visible;

        image_card = getCardImage();
        cardJavaFX = new CardJavaFX();

        updateImageFromVisibility();
    }

    public void setCardColumn(CardColumn column) {
        this.column = column;
    }

    private Image getCardImage() {
        if (suit == null) {
            return image_cardBack;
        }
        String imagePath = "/machineprog2/kortspilgui/art/" + suit + value + ".png";
        try {
            return new Image(Objects.requireNonNull(Card.class.getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.out.println("Error importing card image with path: " + imagePath);
            return null;
        }
    }

    public StackPane getStackPane() {
        return cardJavaFX.stackPane;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        updateImageFromVisibility();
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardColumn getColumn() {
        return column;
    }

    private void updateImageFromVisibility() {
        if (visible) {
            cardJavaFX.imageView.setImage(image_card);
        } else {
            cardJavaFX.imageView.setImage(image_cardBack);
        }
    }

    public static int cardValueFromChar(char character) {
        return switch (character) {
            case 'A' -> 1;
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            default -> Character.getNumericValue(character);
        };
    }

    public static char cardValueToChar(int value) {
        return switch (value) {
            case 1 -> 'A';
            case 10 -> 'T';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            default -> Character.forDigit(value, 10);
        };
    }

    public static Suit cardSuitFromChar(char character) {
        return switch (character) {
            case 'S' -> Suit.Spades;
            case 'H' -> Suit.Hearts;
            case 'C' -> Suit.Clubs;
            case 'D' -> Suit.Diamonds;
            default -> Suit.Spades;
        };
    }

    public static char cardSuitToChar(Suit suit) {
        return suit.name().charAt(0);
    }

    @Override
    public String toString() {
        return Character.toString(cardValueToChar(value)) + cardSuitToChar(suit);
    }
    public String getPositionAsString() {
        return String.valueOf(column.getIndex() + 1) + column.getCardRow(this);
    }

    public boolean hasOtherColor(Card otherCard) {
        return (this.suit == Suit.Clubs || this.suit == Suit.Spades) && (otherCard.suit == Suit.Hearts || otherCard.suit == Suit.Diamonds) ||
                (this.suit == Suit.Hearts || this.suit == Suit.Diamonds) && (otherCard.suit == Suit.Clubs || otherCard.suit == Suit.Spades);
    }

    public void setIsInFountain(boolean isInFountain) {
        this.isInFountain = isInFountain;
    }

    public boolean getIsInFountain() {
        return isInFountain;
    }
}
