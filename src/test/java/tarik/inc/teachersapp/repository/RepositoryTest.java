package tarik.inc.teachersapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tarik.inc.teachersapp.database.Database;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.RowDTO;
import tarik.inc.teachersapp.dto.StateAward;

import java.io.File;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {

    private final File TEST_FILE_XLS = new File("test_db.xls");

    private final File TEST_FILE_CSV = new File("test_db.csv");

    private Repository repository;

    private Database database;

    private List<RowDTO> data;

    @BeforeEach
    void setUp() {
        database = Database.getInstance();
        repository = new Repository();
        data = List.of(
                new RowDTO(1, "1", Faculty.FL,
                        KPIAward.NONE, StateAward.NONE, "1",
                        Year.of(2022), Year.of(2022), KPIAward.NONE),
                new RowDTO(2, "2", Faculty.FL,
                        KPIAward.values()[1], StateAward.NONE, "2",
                        Year.of(2022), Year.of(2022), KPIAward.NONE)
        );
        database.importAll(data);

    }

    @Test
    void importFromXlsx() {
        repository.exportToXLS(TEST_FILE_XLS);
        repository.importFromXLS(TEST_FILE_XLS);
        assertEquals(data, database.stream().toList());
    }

    @Test
    void exportToXlsx() {
        repository.exportToXLS(TEST_FILE_XLS);
    }

    @Test
    void exportToCSV() {
        repository.exportToCSV(TEST_FILE_CSV);
    }

    @Test
    void importFromCSV() {
        repository.exportToCSV(TEST_FILE_CSV);
        repository.importFromCSV(TEST_FILE_CSV);
        assertEquals(data, database.stream().toList());
    }
}