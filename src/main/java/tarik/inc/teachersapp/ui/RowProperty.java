package tarik.inc.teachersapp.ui;

import javafx.beans.property.SimpleStringProperty;
import tarik.inc.teachersapp.dto.RowDTO;

public class RowProperty {

    private SimpleStringProperty name;
    private SimpleStringProperty faculty;
    private SimpleStringProperty kpiDiploma;
    private SimpleStringProperty stateDiploma;
    private SimpleStringProperty protocolNum;
    private SimpleStringProperty kpiDiplomaYear;
    private SimpleStringProperty stateDiplomaYear;
    private SimpleStringProperty prognostication;

    RowProperty(RowDTO row) {
        this.name = new SimpleStringProperty(row.getName());
        this.faculty = new SimpleStringProperty(row.getFaculty().getName());
        this.kpiDiploma = new SimpleStringProperty(row.getKpiDiploma().getName());
        this.stateDiploma = new SimpleStringProperty(row.getStateDiploma().getName());
        this.protocolNum = new SimpleStringProperty(row.getProtocolNum());
        this.kpiDiplomaYear = new SimpleStringProperty(row.getKpiDiplomaYear().toString());
        this.stateDiplomaYear = new SimpleStringProperty(row.getStateDiplomaYear().toString());
        this.prognostication = new SimpleStringProperty(row.getPrognostication().getName());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getFaculty() {
        return faculty.get();
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }

    public SimpleStringProperty facultyProperty() {
        return faculty;
    }

    public String getKpiDiploma() {
        return kpiDiploma.get();
    }

    public void setKpiDiploma(String kpiDiploma) {
        this.kpiDiploma.set(kpiDiploma);
    }

    public SimpleStringProperty kpiDiplomaProperty() {
        return kpiDiploma;
    }

    public String getStateDiploma() {
        return stateDiploma.get();
    }

    public void setStateDiploma(String stateDiploma) {
        this.stateDiploma.set(stateDiploma);
    }

    public SimpleStringProperty stateDiplomaProperty() {
        return stateDiploma;
    }

    public String getProtocolNum() {
        return protocolNum.get();
    }

    public void setProtocolNum(String protocolNum) {
        this.protocolNum.set(protocolNum);
    }

    public SimpleStringProperty protocolNumProperty() {
        return protocolNum;
    }

    public String getKpiDiplomaYear() {
        return kpiDiplomaYear.get();
    }

    public void setKpiDiplomaYear(String kpiDiplomaYear) {
        this.kpiDiplomaYear.set(kpiDiplomaYear);
    }

    public SimpleStringProperty kpiDiplomaYearProperty() {
        return kpiDiplomaYear;
    }

    public String getStateDiplomaYear() {
        return stateDiplomaYear.get();
    }

    public void setStateDiplomaYear(String stateDiplomaYear) {
        this.stateDiplomaYear.set(stateDiplomaYear);
    }

    public SimpleStringProperty stateDiplomaYearProperty() {
        return stateDiplomaYear;
    }

    public String getPrognostication() {
        return prognostication.get();
    }

    public void setPrognostication(String prognostication) {
        this.prognostication.set(prognostication);
    }

    public SimpleStringProperty prognosticationProperty() {
        return prognostication;
    }
}
