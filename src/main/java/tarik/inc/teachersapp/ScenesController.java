package tarik.inc.teachersapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ScenesController {

    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextField textField3;

    public void onSubmit(ActionEvent event) {
        System.out.printf("%s, %s, %s",
                textField1.getText(),
                textField2.getText(),
                textField3.getText());
    }
}
