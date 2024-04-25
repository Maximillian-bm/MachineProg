package machineprog2.kortspilgui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import machineprog2.kortspilgui.model.*;
import machineprog2.kortspilgui.util.StringParser;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static machineprog2.kortspilgui.controller.DragController.*;

public class Controller implements Initializable {
    private final List<Observer> observers;
    private static final List<CardColumn> cardColumns = new ArrayList<>();
    private static ServerController serverCtrl;
    private static final Random random = new Random();

    public Controller() {
        this.observers = new ArrayList<>();
    }

    @FXML
    private Pane mainMenu;
    @FXML
    private HBox columnsHBox;
    @FXML
    private AnchorPane rootPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainMenu.setVisible(true);
        AtomicInteger columnNumber = new AtomicInteger(0);
        columnsHBox.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .map(node -> (VBox) node)
                .forEach(node -> cardColumns.addLast(new CardColumn(node, columnNumber.getAndIncrement())));

        initializeDragController(rootPane);
        initializeRootPaneEventListeners();

        // Starting server in another thread
        int port = 1312;
        serverCtrl = new ServerController(port, this);
        //Thread serverThread = new Thread(serverCtrl);
        //serverThread.start();
    }

    public void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE) {
            setMainMenuVisible(!mainMenu.isVisible()); // toggling
        }
        if (keyCode == KeyCode.G) {
            System.out.println("Setting board");
            setBoardFromString("1C  []2C3C4C5C6C  [][]7C8C9CTCJC  [][][]QCKC1H2H3H  [][][][]4H5H6H7H8H  [][][][][]9HTHJHQHKH  [][][][][][]1D2D3D4D5D");
        }
    }

    private void setMainMenuVisible(boolean isVisible) {
        mainMenu.setVisible(isVisible);
    }

    /*
     *  Example input:
     *  updateBoard("1C  []2C3C4C5C6C  [][]7C8C9CTCJC  [][][]QCKC1H2H3H  [][][][]4H5H6H7H8H  [][][][][]9HTHJHQHKH  [][][][][][]1D2D3D4D5D");
     */
    private void setBoardFromString(String boardString) {
        StringParser.updateColumnsFromString(cardColumns, boardString);
        updateCardEventListeners();
    }

    private void updateCardEventListeners() {
        for (CardColumn column : cardColumns) {
            for (Card card : column.getCards()) {
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

    @FXML
    private void event_newGame() {
        System.out.println("Start new game");
        setMainMenuVisible(false);

        addRandomCardsFromJSON();

        // TODO: Kald backend og bed om nyt spil
        serverCtrl.sendCommandToServer("Start et nyt spil!");
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
