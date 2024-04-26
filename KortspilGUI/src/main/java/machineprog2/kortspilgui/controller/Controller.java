package machineprog2.kortspilgui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import machineprog2.kortspilgui.model.*;
import machineprog2.kortspilgui.util.StringUtility;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static machineprog2.kortspilgui.controller.DragController.*;
import javafx.scene.input.KeyEvent;

public class Controller implements Initializable {
    private final List<Observer> observers;
    private static final List<CardColumn> cardColumns = new ArrayList<>();
    private static final List<CardFountain> cardFountains = new ArrayList<>();
    private static ServerController serverCtrl;
    private static final Random random = new Random();
    public final boolean WITH_BACKEND;
    private static final KeyCombination undoCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);


    public Controller() {
        this.observers = new ArrayList<>();
        this.WITH_BACKEND = true;
    }

    @FXML
    private Pane mainMenu;
    @FXML
    private HBox columnsHBox;
    @FXML
    public VBox fountainsVBox;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox dummyDeck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainMenu.setVisible(true);
        AtomicInteger columnNumber = new AtomicInteger(0);
        columnsHBox.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .map(node -> (VBox) node)
                .forEach(node -> cardColumns.addLast(new CardColumn(node, columnNumber.getAndIncrement())));

        AtomicInteger fountainNumber = new AtomicInteger(0);
        fountainsVBox.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .map(node -> (VBox) node)
                .forEach(node -> cardFountains.addLast(new CardFountain(node, fountainNumber.getAndIncrement())));

        initializeDragController(rootPane, this);
        initializeRootPaneEventListeners();

        if (WITH_BACKEND) {
            // Start the server
            int port = 1312;
            serverCtrl = new ServerController(port);
        }
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            setMainMenuVisible(!mainMenu.isVisible()); // toggling
        }
        if (!WITH_BACKEND) {
            if (event.getCode() == KeyCode.G) {
                System.out.println("Setting board");
                setBoardFromString("1C  []2C3C4C5C6C  [][]7C8C9CTCJC  [][][]QCKC1H2H3H  [][][][]4H5H6H7H8H  [][][][][]9HTHJHQHKH  [][][][][][]1D2D3D4D5D");
            }
        } else {
            if (undoCombination.match(event)) {
                sendMessageToClient("U");
            }
        }
    }

    private void setMainMenuVisible(boolean isVisible) {
        mainMenu.setVisible(isVisible);
    }

    /*
     *  Example input:
     *  updateBoard("1C  []2C3C4C5C6C  [][]7C8C9CTCJC  [][][]QCKC1H2H3H  [][][][]4H5H6H7H8H  [][][][][]9HTHJHQHKH  [][][][][][]1D2D3D4D5D");
     */
    public void setBoardFromString(String boardString) {
        resetBoard();
        StringUtility.updateBoardFromString(cardColumns, cardFountains, boardString);
        updateCardEventListeners();
    }

    private void resetBoard() {
        for (int i = 0; i < 7; i++) {
            cardColumns.get(i).reset();
        }
        for (int i = 0; i < 4; i++) {
            cardFountains.get(i).reset();
        }
    }

    private void updateCardEventListeners() {
        for (CardColumn column : cardColumns) {
            for (Card card : column.getCards()) {
                initializeCardEventListeners(card);
            }
        }
        for (CardFountain fountain : cardFountains) {
            for (Card card : fountain.getCards()) {
                initializeCardEventListeners(card);
            }
        }
    }

    public static List<Card> getCards() {
        List<Card> cardList = new ArrayList<>();
        for (CardColumn column : cardColumns) {
            cardList.addAll(column.getCards());
        }
        return cardList;
    }

    public static List<CardColumn> getColumns() {
        return cardColumns;
    }

    public static List<CardFountain> getFountains() {
        return cardFountains;
    }

    public Controller addObserver(Observer observer) {
        observers.add(observer);
        return this;
    }

    public void notifyChange() {
        for (Observer observer : observers) {
            observer.onChange();
        }
    }

    /*
     *  From StackOverflow.com - https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     *  By user "eldelshell" - https://stackoverflow.com/users/48869/eldelshell
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> classType) {
        int x = random.nextInt(classType.getEnumConstants().length);
        return classType.getEnumConstants()[x];
    }

    private void addRandomCardsFromJSON() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/machineprog2/kortspilgui/card_columns_structure.json");
            List<CardColumnsWrapper> cardColumnsData = new ObjectMapper().readValue(inputStream, new TypeReference<>() {
            });
            assert inputStream != null;
            inputStream.close();

            for (int i = 0; i < cardColumns.size(); i++) {
                for (int j = 0; j < cardColumnsData.get(i).face_down; j++) {
                    Card newCard = new Card(random.nextInt(1, 14), randomEnum(Suit.class), false);
                    cardColumns.get(i).addCard(newCard);
                }
                for (int j = 0; j < cardColumnsData.get(i).face_up; j++) {
                    Card newCard = new Card(random.nextInt(1, 14), randomEnum(Suit.class), true);
                    cardColumns.get(i).addCard(newCard);
                }
            }

        } catch (Exception e) {
            System.out.println("Error in importing .json. Output: " + e.getMessage());
        }

        updateCardEventListeners();
    }

    public void sendMessageToClient(String message) {
        serverCtrl.addMessageToClient(message);
        handleMessageFromClient();
    }

    private void handleMessageFromClient() {
        try {
            String receivedMessage = serverCtrl.getReceiveMessageQueue().take();
            char messageCode = receivedMessage.charAt(0);
            switch (messageCode) {
                case 'B':
                    System.out.println("[Client]: board data");
                    setBoardFromString(receivedMessage.substring(1));
                    ServerController.setClientReadyToReceive();
                    break;
                case 'Z': // Adding case, since we know this message.
                    System.out.println("[Client]: OK");
                    ServerController.setClientReadyToReceive();
                    break;
                default:
                    System.out.println("No handling for messageCode '" + messageCode + "' from server. Full message: \n" + receivedMessage);
                    break;
            }
        } catch (InterruptedException e) {
            System.out.println("Can't get message from client. Exception message: " + e.getMessage());
        }
    }

    @FXML
    private void event_newGame() {
        setMainMenuVisible(false);
        dummyDeck.setVisible(false);
        System.out.println("Starting new game...");
        resetBoard();

        if (WITH_BACKEND) {
            sendMessageToClient("Q");
            sendMessageToClient("LD");
            sendMessageToClient("SR");
            sendMessageToClient("SW");
            sendMessageToClient("SR");
            sendMessageToClient("P");
        } else {
            addRandomCardsFromJSON();
        }
    }

    @FXML
    private void event_loadGame() {
        System.out.println("Load a game");
    }

    @FXML
    private void event_save() {
        System.out.println("Save game");
        // If save-file, override it.
        // No save-file? Call event_saveAs();
    }

    @FXML
    private void event_saveAs() {
        System.out.println("Start new game");
    }

    @FXML
    private void event_quitAndSave() {
        System.out.println("Start new game");
    }
}
