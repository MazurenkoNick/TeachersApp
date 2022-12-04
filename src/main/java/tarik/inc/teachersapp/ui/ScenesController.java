package tarik.inc.teachersapp.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tarik.inc.teachersapp.HelloApplication;
import tarik.inc.teachersapp.database.Database;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.RowDTO;
import tarik.inc.teachersapp.dto.StateAward;
import tarik.inc.teachersapp.repository.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScenesController implements Initializable {

    private final Database database;
    private final Repository repository;
    @FXML
    private ComboBox<String> dropBoxOfFaculties = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfKpiAwards = new ComboBox<>();
    @FXML
    private ComboBox<String> dropBoxOfStateAwards = new ComboBox<>();
    @FXML
    private TableView<RowProperty> tableView = new TableView<>();
    @FXML
    private TextField textFieldOfFullName;
    @FXML
    private TextField textFieldOfProtocol;
    @FXML
    private TextField textFieldOfKpiAwardYear;
    @FXML
    private TextField textFieldOfStateAwardYear;
    @FXML
    private TextField textFieldFaculty;
    @FXML
    private TextField textFieldKpiAward;
    @FXML
    private TextField textFieldStateAward;


    public ScenesController() {
        this.database = Database.getInstance();
        this.repository = new Repository();
    }

    public void exportToXLSX(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            repository.exportToXLS(selectedFile);
        } else {
            errorAlert("Невдалося експортувати файл");
        }
    }

    public void importFromXLSX(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            repository.importFromXLS(selectedFile);
        } else {
            errorAlert("Невдалося імпортувати файл");
        }
        tableView.getItems().clear();
        addAllRowsToTable();
    }

    public void exportToCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            repository.exportToCSV(selectedFile);
        } else {
            errorAlert("Невдалося експортувати файл");
        }
    }

    public void importFromCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            repository.importFromCSV(selectedFile);
        } else {
            errorAlert("Невдалося імпортувати файл");
        }
        tableView.getItems().clear();
        addAllRowsToTable();
    }

    public void addRowToDatabase(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Додаваня в базу данних");

        try {
            int dbSize = database.size();
            int id = (dbSize == 0) ? 0 : database.get(dbSize - 1).getId() + 1;
            String name = textFieldOfFullName.getText();
            String protocolNum = textFieldOfProtocol.getText();
            Faculty faculty = Faculty.fromString(dropBoxOfFaculties.getSelectionModel().getSelectedItem());

            String kpiAwardText = dropBoxOfKpiAwards.getSelectionModel().getSelectedItem();
            if (kpiAwardText == null)
                kpiAwardText = " - ";
            KPIAward kpiAward = KPIAward.fromString(kpiAwardText);

            String stateAwardText = dropBoxOfStateAwards.getSelectionModel().getSelectedItem();
            if (stateAwardText == null)
                stateAwardText = " - ";
            StateAward stateAward = StateAward.fromString(stateAwardText);

            Year kpiDiplomaYear = getValidYear(textFieldOfKpiAwardYear.getText());
            Year stateDiplomaYear = getValidYear(textFieldOfStateAwardYear.getText());
            KPIAward prognostication = getPrognostication(name, faculty, kpiAward, stateAward,
                    protocolNum, kpiDiplomaYear, stateDiplomaYear);

            RowDTO newRow = new RowDTO(id, name, faculty,
                    kpiAward, stateAward, protocolNum,
                    kpiDiplomaYear, stateDiplomaYear, prognostication);

            addAndAlert(newRow);
        } catch (IllegalArgumentException e) {
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            alert.setHeaderText("Поля введені неправильно / Поля не заповнені");
            alert.showAndWait();
        }
    }

    public void showAllRows(ActionEvent event) {
        tableView.getItems().clear();
        database.stream()
                .forEach(this::addRowToTable);
    }

    public void searchAllRows(ActionEvent event) {
        try {
            String name = textFieldOfFullName.getText();
            String faculty = textFieldFaculty.getText();
            String protocolNum = textFieldOfProtocol.getText();
            String kpiAward = textFieldKpiAward.getText();
            String stateAward = textFieldStateAward.getText();
            String kpiDiplomaYear = textFieldOfKpiAwardYear.getText();
            String stateDiplomaYear = textFieldOfStateAwardYear.getText();

            tableView.getItems().clear();
            List<RowDTO> matches = findAllMatches(name, faculty, protocolNum, kpiAward,
                    stateAward, kpiDiplomaYear, stateDiplomaYear);
            addAllRowsToTable(matches);
        } catch (Exception e) {
            errorAlert("Параметри полів введені з помилками");
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

        addAllRowsToTable();
    }

    private Year getValidYear(String year) {
        if (year.isBlank())
            year = "0";
        return Year.parse(year);
    }

    private List<RowDTO> findAllMatches(String name, String faculty, String protocolNum, String kpiAward,
                                        String stateAward, String kpiDiplomaYear, String stateDiplomaYear) {
        return database.stream()
                .filter(rowDTO -> {
                    if (name.isBlank()) return true;
                    return rowDTO.getName().equalsIgnoreCase(name);
                })
                .filter(rowDTO -> {
                    if (faculty.isBlank()) return true;
                    return rowDTO.getFaculty().getName().equalsIgnoreCase(faculty);
                })
                .filter(rowDTO -> {
                    if (protocolNum.isBlank()) return true;
                    return rowDTO.getProtocolNum().equals(protocolNum);
                })
                .filter(rowDTO -> {
                    if (kpiAward.isBlank()) return true;
                    return rowDTO.getKpiDiploma().getName().trim().equalsIgnoreCase(kpiAward.trim());
                })
                .filter(rowDTO -> {
                    if (stateAward.isBlank()) return true;
                    return rowDTO.getStateDiploma().getName().trim().equalsIgnoreCase(stateAward.trim());
                })
                .filter(rowDTO -> {
                    if (kpiDiplomaYear.isBlank()) return true;
                    return rowDTO.getKpiDiplomaYear().equals(Year.parse(kpiDiplomaYear));
                })
                .filter(rowDTO -> {
                    if (stateDiplomaYear.isBlank()) return true;
                    return rowDTO.getStateDiplomaYear().equals(Year.parse(stateDiplomaYear));
                })
                .toList();
    }

    private void addAllRowsToTable() {
        tableView.getItems().addAll(database.stream().map(RowProperty::new).toList());

    }

    private void addAllRowsToTable(Iterable<RowDTO> rows) {
        for (RowDTO row : rows) {
            addRowToTable(row);
        }
    }

    private void errorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Пошук");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void addRowToTable(RowDTO rowDTO) {
        RowProperty row = new RowProperty(rowDTO);
        tableView.getItems().add(row);
    }

    public void toScene1(ActionEvent event) throws IOException {
        toScene(event, "Scene1.fxml");
    }

    public void toScene2(ActionEvent event) throws IOException {
        toScene(event, "Scene2.fxml");
    }

    private void toScene(ActionEvent event, String sceneName) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(sceneName)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean addIsPossible(RowDTO rowDTO) throws IllegalArgumentException{
        String name = rowDTO.getName();
        Faculty faculty = rowDTO.getFaculty();
        KPIAward kpiDiploma = rowDTO.getKpiDiploma();
        StateAward stateDiploma = rowDTO.getStateDiploma();
        String protocolNum = rowDTO.getProtocolNum();
        Year kpiDiplomaYear = rowDTO.getKpiDiplomaYear();
        Year stateDiplomaYear = rowDTO.getStateDiplomaYear();

        if (name.isBlank())
            throw new IllegalArgumentException("Поля ім'я має бути заповненим");
        else if (!kpiDiplomaYear.equals(Year.of(0)) && kpiDiploma.equals(KPIAward.NONE))
            throw new IllegalArgumentException("Нагорода КПІ має бути обраною");
        else if (!stateDiplomaYear.equals(Year.of(0)) && stateDiploma.equals(StateAward.NONE) ||
                protocolNum.isBlank() && !stateDiploma.equals(StateAward.NONE))
            throw new IllegalArgumentException("Державна нагорода або/i протокол мають бути обраними");

        RowDTO rowWithPrevKpiAwardYear = database.findRowWithPreviousKpiYear(name, faculty,
                kpiDiplomaYear.minusYears(1));

        if (!rowWithPrevKpiAwardYear.equals(RowDTO.EMPTY)) {
            KPIAward prognosticationKpiAward = rowWithPrevKpiAwardYear.getPrognostication();
            if (!prognosticationKpiAward.equals(KPIAward.NONE) && !prognosticationKpiAward.equals(kpiDiploma))    {
                throw new IllegalArgumentException("Неможливо додати КПІ нагороду");
            }
        }

        RowDTO rowWithPrevStateAwardYear = database.findRowWithPreviousStateAwardYear(name, faculty,
                stateDiplomaYear.minusYears(1));

        if (!rowWithPrevStateAwardYear.equals(RowDTO.EMPTY)) {
            StateAward prognosticationStateAward = rowWithPrevStateAwardYear.getStateDiploma().next().orElse(null);
            if (prognosticationStateAward != null && !prognosticationStateAward.equals(stateDiploma)) {
                throw new IllegalArgumentException("Неможливо додати державну нагороду");
            }
        }

        return !(database.rowWithKpiYearExists(name, faculty, kpiDiplomaYear) ||
                database.rowWithStateYearExists(name, faculty, stateDiplomaYear) ||
                database.rowExists(name, faculty, kpiDiploma, stateDiploma, protocolNum, kpiDiplomaYear, stateDiplomaYear));
    }

    private void addAndAlert(RowDTO newRow) throws IllegalArgumentException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Додаваня в базу данних");

        if (addIsPossible(newRow)) {
//            adding to database
            database.add(newRow);
//            adding to table
            addRowToTable(newRow);
            alert.setHeaderText("Додавання прошло успішно");
            alert.showAndWait();
        } else {
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
