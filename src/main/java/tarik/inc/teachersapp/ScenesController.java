package tarik.inc.teachersapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tarik.inc.teachersapp.database.Database;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.RowDTO;
import tarik.inc.teachersapp.dto.StateAward;

import java.net.URL;
import java.time.Year;
import java.util.*;

public class ScenesController implements Initializable {

    @FXML
    private ComboBox<String> dropBoxOfFaculties = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfKpiAwards = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfStateAwards = new ComboBox<>();
    @FXML private TextField textFieldOfFullName;
    @FXML private TextField textFieldOfProtocol;
    @FXML private TextField textFieldOfKpiAwardYear;
    @FXML private TextField textFieldOfStateAwardYear;
    @FXML private Button addRowToDbButton;

    private final Database database;

    public ScenesController() {
        this.database = Database.getInstance();
    }

    public void addRowToDatabase(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Додаваня в базу данних");

        try {
            int dbSize = database.size();
            int id = (dbSize == 0) ? 0 : database.get(dbSize-1).getId()+1;
            String name = textFieldOfFullName.getText();
            String protocolNum = textFieldOfProtocol.getText();
            Faculty faculty = Faculty.fromString(dropBoxOfFaculties.getSelectionModel().getSelectedItem());
            KPIAward kpiAward = KPIAward.fromString(dropBoxOfKpiAwards.getSelectionModel().getSelectedItem());
            StateAward stateAward = StateAward.fromString(dropBoxOfStateAwards.getSelectionModel().getSelectedItem());
            Year kpiDiplomaYear = Year.parse(textFieldOfKpiAwardYear.getText());
            Year stateDiplomaYear = Year.parse(textFieldOfStateAwardYear.getText());
            KPIAward prognostication = getPrognostication(name, faculty, kpiAward, stateAward,
                    protocolNum, kpiDiplomaYear, stateDiplomaYear);

            RowDTO newRow = new RowDTO(id, name, faculty,
                    kpiAward, stateAward, protocolNum,
                    kpiDiplomaYear, stateDiplomaYear, prognostication);

            addAndAlert(newRow);
        }
        catch (Exception e) {
            alert.setHeaderText("Параметри були введені з помилками");
            alert.showAndWait();
        }
        System.out.println("---");
        database.stream().forEach(System.out::println);
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

    private void addAndAlert(RowDTO newRow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Додаваня в базу данних");

        if (database.rowExists(newRow.getName(), newRow.getFaculty(), newRow.getKpiDiploma(), newRow.getStateDiploma(),
                newRow.getProtocolNum(), newRow.getKpiDiplomaYear(), newRow.getStateDiplomaYear())) {
            alert.setHeaderText("Таке поле вже існує");
            alert.showAndWait();

        }
        else {
            database.add(newRow);
            alert.setHeaderText("Додавання прошло успішно");
            alert.showAndWait();
        }
    }

    private boolean prognosticationIsPossible(String name, Faculty faculty,
                                              KPIAward kpiAward, StateAward stateAward,
                                              String protocolNum, Year kpiDiplomaYear, Year stateDiplomaYear) {

        return !database.rowExists(name, faculty, kpiAward, stateAward,
                                    protocolNum, kpiDiplomaYear, stateDiplomaYear);
    }

    private KPIAward getPrognostication(String name, Faculty faculty,
                                        KPIAward kpiAward, StateAward stateAward,
                                        String protocolNum, Year kpiDiplomaYear, Year stateDiplomaYear) {

        if (prognosticationIsPossible(name, faculty, kpiAward, stateAward, protocolNum, kpiDiplomaYear, stateDiplomaYear)) {
            return KPIAward.fromString(kpiAward.getName())
                           .next()
                           .orElse(KPIAward.NONE);
        }
        return KPIAward.NONE;
    }
}
