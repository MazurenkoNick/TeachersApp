package tarik.inc.teachersapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.StateAward;

import java.net.URL;
import java.util.*;

public class ScenesController implements Initializable {

    @FXML
    ComboBox<String> dropBoxOfFaculties = new ComboBox<>();
    @FXML
    ComboBox<String> dropBoxOfKpiAwards = new ComboBox<>();
    @FXML
    ComboBox<String> dropBoxOfStateAwards = new ComboBox<>();
    @FXML
    ComboBox<String> dropBoxOfPrognostication = new ComboBox<>();
    @FXML TextField textFieldOfFullName;
    @FXML TextField textFieldOfProtocol;
    @FXML TextField textFieldOfKpiAwardYear;
    @FXML TextField textFieldOfStateAwardYear;

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
