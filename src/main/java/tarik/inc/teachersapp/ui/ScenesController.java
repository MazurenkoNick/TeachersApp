package tarik.inc.teachersapp.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<RowProperty> tableView = new TableView<>();
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
//        Filling tableView;
        TableColumn<RowProperty, String> facultyColumn = new TableColumn<>("Факультет/ННІ");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("faculty"));
        TableColumn<RowProperty, String> nameColumn = new TableColumn<>("ПІБ");
        nameColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("name"));
        TableColumn<RowProperty, String> kpiAwardColumn = new TableColumn<>("Нагорода КПІ");
        kpiAwardColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("kpiDiploma"));
        TableColumn<RowProperty, String> stateAwardColumn = new TableColumn<>("Державна нагорода");
        stateAwardColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("stateDiploma"));
        TableColumn<RowProperty, String> protocolColumn = new TableColumn<>("№ протоколу ВР");
        protocolColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("protocolNum"));
        TableColumn<RowProperty, String> kpiDiplomaYearColumn = new TableColumn<>("Рік відзначення КПІ");
        kpiDiplomaYearColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("kpiDiplomaYear"));
        TableColumn<RowProperty, String> stateDiplomaYearColumn = new TableColumn<>("Рік відзначення Державою");
        stateDiplomaYearColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("stateDiplomaYear"));
        TableColumn<RowProperty, String> prognosticationColumn = new TableColumn<>("Прогнозування");
        prognosticationColumn.setCellValueFactory(new PropertyValueFactory<RowProperty, String>("prognostication"));

        tableView.getColumns().addAll(List.of(facultyColumn, nameColumn, kpiAwardColumn,
                                              stateAwardColumn, protocolColumn, kpiDiplomaYearColumn,
                                              stateDiplomaYearColumn, prognosticationColumn));
    }

    private void addRowToTable(RowDTO rowDTO) {
        RowProperty row = new RowProperty(rowDTO);
        tableView.getItems().add(row);
    }

    private boolean addIsPossible(RowDTO rowDTO) {
        String name = rowDTO.getName();
        Faculty faculty = rowDTO.getFaculty();
        KPIAward kpiDiploma = rowDTO.getKpiDiploma();
        StateAward stateDiploma = rowDTO.getStateDiploma();
        String protocolNum = rowDTO.getProtocolNum();
        Year kpiDiplomaYear = rowDTO.getKpiDiplomaYear();
        Year stateDiplomaYear = rowDTO.getStateDiplomaYear();

        return !(database.rowWithKpiYearExists(name, faculty, kpiDiplomaYear) ||
                database.rowWithStateYearExists(name, faculty, stateDiplomaYear) ||
                database.rowExists(name, faculty, kpiDiploma, stateDiploma, protocolNum, kpiDiplomaYear, stateDiplomaYear));
    }

    private void addAndAlert(RowDTO newRow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Додаваня в базу данних");

        if (addIsPossible(newRow)) {
//            adding to database
            database.add(newRow);
//            adding to table
            addRowToTable(newRow);
            alert.setHeaderText("Додавання прошло успішно");
            alert.showAndWait();

        }
        else {
            alert.setHeaderText("Таке поле вже існує");
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
