package machineprog2.kortspilgui.controller;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import machineprog2.kortspilgui.model.Card;
import machineprog2.kortspilgui.model.CardColumn;
import machineprog2.kortspilgui.model.CardContainer;
import machineprog2.kortspilgui.model.CardFountain;
import machineprog2.kortspilgui.util.Effects;
import machineprog2.kortspilgui.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class DragController {
    private static Point2D dragOffset;
    private static VBox dragVBox;
    private static Card topMostHoveredCard = null;
    private static InnerShadow innerShadowEffect;
    private static Card draggedCard;
    private static AnchorPane rootPane;
    private static Controller ctrl;


    public static void initializeDragController(AnchorPane rootPane_, Controller ctrl_) {
        dragVBox = new VBox();
        rootPane = rootPane_;
        rootPane.getChildren().add(dragVBox);
        innerShadowEffect = Effects.getInnerShadowEffect();
        ctrl = ctrl_;
    }

    public static void initializeCardEventListeners(Card card) {
        card.getStackPane().setOnMouseEntered(event -> {
            topMostHoveredCard = card;
            if (!card.getIsInFountain()) {
                if (card.getVisible()) {
                    for (Card cardInStack : card.getContainer().getCardsOnTop(card)) {
                        cardInStack.getStackPane().setEffect(innerShadowEffect);
                    }
                }
            } else {
                card.getContainer().getTopCard().getStackPane().setEffect(innerShadowEffect);
            }
        });
        card.getStackPane().setOnMouseExited(event -> {
            if (topMostHoveredCard == card) {
                topMostHoveredCard = null;
                if (!card.getIsInFountain()) {
                    for (Card cardInStack : card.getContainer().getCardsOnTop(card)) {
                        cardInStack.getStackPane().setEffect(null);
                    }
                } else {
                    card.getContainer().getTopCard().getStackPane().setEffect(null);
                }
            }
        });
    }

    public static void initializeRootPaneEventListeners() { // Checking for all cards individually in scene. If not done like this, .setOnMouseDragged on cards would only get the top node at the mouse position
        rootPane.setOnMousePressed(event -> {
            if (topMostHoveredCard == null) return;

            if (!topMostHoveredCard.getIsInFountain()) {
                if (topMostHoveredCard.getVisible()) {
                    draggedCard = topMostHoveredCard;
                }
            } else {
                draggedCard = getFountainAtMouse(event).getTopCard();
            }

            //System.out.println("Picked up : " + draggedCard);
            StackPane cardStackPane = draggedCard.getStackPane();
            CardContainer cardContainer = draggedCard.getContainer();

            // Get the offset from the card to the mouse
            Bounds localBounds = cardStackPane.getBoundsInLocal();
            Bounds sceneBounds = cardStackPane.localToScene(localBounds);
            dragOffset = new Point2D(event.getSceneX() - sceneBounds.getMinX(), event.getSceneY() - sceneBounds.getMinY());

            // Stack cards
            if (!draggedCard.getIsInFountain()) {
                List<Card> cardStack = cardContainer.getCardsOnTop(draggedCard); // Includes this card as index 0
                for (Card cardInStack : cardStack) {
                    StackPane stackPane = cardInStack.getStackPane();
                    cardContainer.getVBox().getChildren().remove(stackPane);
                    dragVBox.getChildren().add(stackPane);
                }
            } else {
                cardContainer.getVBox().getChildren().remove(cardStackPane);
                dragVBox.getChildren().add(cardStackPane);
            }
            updateDragVBoxPosition(event); // Call update position, since .setOnMouseDragged only gets called when the mouse is moved
        });

        rootPane.setOnMouseDragged(event -> {
            if (draggedCard == null) return; // When dragging
            // Update dragged cards position
            updateDragVBoxPosition(event);
            // Clear effects
            for (Card cardInScene : Controller.getCards()) {
                cardInScene.getStackPane().setEffect(null);
            }
            for (CardFountain fountainInScene : Controller.getFountains()) {
                fountainInScene.getStackPane().setEffect(null);
                for (Card cardInFountain : Controller.getCards()) {
                    cardInFountain.getStackPane().setEffect(null);
                }
            }
            for (CardColumn columnInScene : Controller.getColumns()) {
                columnInScene.getStackPane().setEffect(null);
            }

            // Highlight fountain if the card can be dropped there
            CardFountain fountainAtMouse = getFountainAtMouse(event);
            if (fountainAtMouse != null) {
                switch (canDropOn(fountainAtMouse)) {
                    case 1:
                        fountainAtMouse.getStackPane().setEffect(innerShadowEffect);
                        break;
                    case 2:
                        fountainAtMouse.getTopCard().getStackPane().setEffect(innerShadowEffect);
                        break;
                }
            }

            // Highlight column if the card can be dropped there
            CardColumn columnAtMouse = getColumnAtMouse(event);
            if (columnAtMouse != null) {
                switch (canDropOn(columnAtMouse)) {
                    case 1:
                        columnAtMouse.getStackPane().setEffect(innerShadowEffect);
                        columnAtMouse.getVBox().setEffect(innerShadowEffect);
                        break;
                    case 2:
                        columnAtMouse.getTopCard().getStackPane().setEffect(innerShadowEffect);
                        break;
                }
            }
        });

        rootPane.setOnMouseReleased(event -> {
            // Return if we're not holding a card
            if (draggedCard == null) return;

            // Highlight fountain if the card can be dropped there
            CardFountain fountainAtMouse = getFountainAtMouse(event);
            if (fountainAtMouse != null) {
                switch (canDropOn(fountainAtMouse)) {
                    case 0:
                        moveDraggedCardsBack();
                        break;
                    case 1, 2:
                        List<Card> cardList = new ArrayList<>();
                        cardList.add(draggedCard);
                        moveCardsToContainer(cardList, draggedCard.getContainer(), fountainAtMouse);
                        break;
                }
                draggedCard = null;
                return;
            }

            // Highlight column if the card can be dropped there
            CardColumn columnAtMouse = getColumnAtMouse(event);
            if (columnAtMouse != null) {
                switch (canDropOn(columnAtMouse)) {
                    case 0:
                        moveDraggedCardsBack();
                        break;
                    case 1, 2:
                        moveCardsToContainer(draggedCard.getContainer().getCardsOnTop(draggedCard), draggedCard.getContainer(), columnAtMouse);
                        break;
                }
                draggedCard = null;
                return;
            }

            moveDraggedCardsBack();
            draggedCard = null;
        });
    }

    private static int canDropOn(CardFountain fountain) {
        if (fountain != null) {
            if (fountain.getFountainSuit() == draggedCard.getSuit()) {
                if (draggedCard.getIsInFountain() || draggedCard.getContainer().getTopCard() == draggedCard) {
                    if (fountain.getCards().isEmpty()) {
                        if (draggedCard.getValue() == 1) {
                            return 1;
                        }
                    } else {
                        if (fountain.getTopCard().getValue() + 1 == draggedCard.getValue()) {
                            return 2;
                        }
                    }
                }
            }
        }
        return 0;
    }
    private static int canDropOn(CardColumn column) {
        if (column != null) {
            List<Card> cardsInColumn = column.getCards();
            if (cardsInColumn.isEmpty()) {
                return 1;
            } else {
                Card topCardOfHoveredColumn = column.getTopCard();
                // If it's a legal move
                if ((topCardOfHoveredColumn.getSuit() != draggedCard.getSuit()) && (topCardOfHoveredColumn.getValue() - 1 == draggedCard.getValue())) {
                    return 2;
                }
            }
        }
        return 0;
    }

    private static void moveDraggedCardsBack() {
        List<Card> cardsToMove = draggedCard.getContainer().getCardsOnTop(draggedCard);
        for (Card cardInStack : cardsToMove) {
            StackPane stackPane = cardInStack.getStackPane();
            // Remove stackPane from dragVBox
            dragVBox.getChildren().remove(stackPane);
            // Add card back to column
            cardInStack.getContainer().getVBox().getChildren().add(stackPane);
        }
    }

    private static void moveCardsToContainer(List<Card> cardsToMove, CardContainer fromContainer, CardContainer toContainer) {
        for (Card cardInStack : cardsToMove) {
            StackPane stackPane = cardInStack.getStackPane();
            // Remove stackPane from dragVBox
            dragVBox.getChildren().remove(stackPane);
            // Remove card from previous cardColumn
            fromContainer.removeCard(cardInStack);
            // Add card to new cardColumn
            toContainer.addCard(cardInStack);
        }

        if (ctrl.WITH_BACKEND) {
            // Send message to backend
            ctrl.sendMessageToClient(StringUtility.formatMoveCommand(cardsToMove.getFirst(), fromContainer, toContainer));
        }
    }

    private static List<Card> getCardsAtMouse(MouseEvent event) {
        List<Card> cardsAtMouse = new ArrayList<>();
        for (Card cardInScene : Controller.getCards()) {
            StackPane stackPane = cardInScene.getStackPane();
            Bounds localBounds = stackPane.getBoundsInLocal();
            Bounds sceneBounds = stackPane.localToScene(localBounds);

            if (sceneBounds.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
                // If mouse is within bounds of a card
                cardsAtMouse.add(cardInScene);
            }
        }
        return cardsAtMouse;
    }

    private static CardContainer getCardContainerAtMouse(MouseEvent event) {
        List<CardContainer> containers = new ArrayList<>();
        containers.addAll(Controller.getColumns());
        containers.addAll(Controller.getFountains());

        for (CardContainer containerInScene : containers) {
            StackPane stackPane = containerInScene.getStackPane();
            Bounds localBounds = stackPane.getBoundsInLocal();
            Bounds sceneBounds = stackPane.localToScene(localBounds);

            if (sceneBounds.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
                // If mouse is within bounds of a card
                return containerInScene;
            }
        }
        return null;
    }

    private static CardColumn getColumnAtMouse(MouseEvent event) {
        for (CardColumn columnInScene : Controller.getColumns()) {
            VBox vBox = columnInScene.getVBox();
            Bounds localBounds = vBox.getBoundsInLocal();
            Bounds sceneBounds = vBox.localToScene(localBounds);

            if (sceneBounds.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
                // If mouse is within bounds of column
                return columnInScene;
            }
        }
        return null;
    }

    private static CardFountain getFountainAtMouse(MouseEvent event) {
        for (CardFountain fountainInScene : Controller.getFountains()) {
            StackPane stackPane = fountainInScene.getStackPane();
            Bounds localBounds = stackPane.getBoundsInLocal();
            Bounds sceneBounds = stackPane.localToScene(localBounds);

            if (sceneBounds.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
                // If mouse is within bounds of fountain
                return fountainInScene;
            }
        }
        return null;
    }

    private static void updateDragVBoxPosition(MouseEvent event) {
        double cardX = event.getSceneX() - dragOffset.getX();
        double cardY = event.getSceneY() - dragOffset.getY();
        dragVBox.setLayoutX(cardX);
        dragVBox.setLayoutY(cardY);
    }
}
