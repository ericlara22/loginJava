module ericlara_ev03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ericlara_ev03 to javafx.fxml;
    exports ericlara_ev03;
}
