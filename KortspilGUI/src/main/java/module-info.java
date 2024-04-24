module machineprog2.kortspilgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;


    opens machineprog2.kortspilgui to javafx.fxml;
    exports machineprog2.kortspilgui;
    exports machineprog2.kortspilgui.view;
    opens machineprog2.kortspilgui.view to javafx.fxml;
    exports machineprog2.kortspilgui.controller;
    exports machineprog2.kortspilgui.util;
    opens machineprog2.kortspilgui.util to javafx.fxml;
    opens machineprog2.kortspilgui.model;
    exports machineprog2.kortspilgui.model to javafx.fxml;
    opens machineprog2.kortspilgui.controller;
}
