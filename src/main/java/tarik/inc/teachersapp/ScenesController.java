package tarik.inc.teachersapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tarik.inc.teachersapp.database.Database;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.StateAward;

import java.net.URL;
import java.util.*;

public class ScenesController implements Initializable {

    @FXML
    private ComboBox<String> dropBoxOfFaculties = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfKpiAwards = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfStateAwards = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfPrognostication = new ComboBox<>();
    @FXML private TextField textFieldOfFullName;
    @FXML private TextField textFieldOfProtocol;
    @FXML private TextField textFieldOfKpiAwardYear;
    @FXML private TextField textFieldOfStateAwardYear;

    private final Database database;

    public ScenesController() {
        this.database = Database.getInstance();
    }

    public void setPrognosticationValues(ActionEvent event) {
        dropBoxOfPrognostication.getItems().clear();

        String selectedKpiAward = dropBoxOfKpiAwards.getSelectionModel().getSelectedItem();
        KPIAward award = Arrays.stream(KPIAward.values())
                .filter(kpiAward -> kpiAward.getName().equals(selectedKpiAward))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        dropBoxOfPrognostication.getItems().add(award.next());
        dropBoxOfPrognostication.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Filling dropBoxOfFaculties
        List<String> faculties = Arrays.stream(Faculty.allValues)
                                        .map(Faculty::getName)
                                        .toList();
        dropBoxOfFaculties.getItems().addAll(faculties);

//        Filling dropBoxOfKpiAwards:
        List<String> kpiAwards = Arrays.stream(KPIAward.allValues)
                                        .map(KPIAward::getName)
                                        .toList();
        dropBoxOfKpiAwards.getItems().addAll(kpiAwards);
//        Filling dropBoxOfStateAwards:
        List<String> stateAwards = Arrays.stream(StateAward.allValues)
                                        .map(StateAward::getName)
                                        .toList();
        dropBoxOfStateAwards.getItems().addAll(stateAwards);
    }
}
