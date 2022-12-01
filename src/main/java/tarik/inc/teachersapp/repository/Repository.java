package tarik.inc.teachersapp.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tarik.inc.teachersapp.config.ApplicationConfig;
import tarik.inc.teachersapp.database.Database;
import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.RowDTO;
import tarik.inc.teachersapp.dto.StateAward;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;


public class Repository {
    private final File DATABASE_FILE = new File("database.xls");

    private final Database database;

    private final CsvMapper mapper;

    private final CsvSchema schema;

    public Repository() {
        database = Database.getInstance();
        mapper = ApplicationConfig.getCsvMapper();
        schema = ApplicationConfig.getCsvSchema();
    }

    public void load() {
        importFromXLS(DATABASE_FILE);
    }

    public void save() {
        exportToXLS(DATABASE_FILE);
    }

    public void exportToCSV(File file) {
        try (SequenceWriter writer = mapper.writer(schema).writeValues(file)) {
            database.forEach(value -> {
                try {
                    writer.write(value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importFromCSV(File file) {
        try (MappingIterator<RowDTO> iterator = mapper.readerFor(RowDTO.class).with(schema).readValues(file)) {
            database.importAll(iterator.readAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importFromXLS(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            Iterable<Row> sheet = workbook.getSheetAt(0);
            List<RowDTO> rows = StreamSupport.stream(sheet.spliterator(), false)
                    .map(this::toRowIfCorrect)
                    .filter(RowDTO::isNotEmpty)
                    .toList();

            database.importAll(rows);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportToXLS(File file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("1");
            fillSheet(sheet);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillSheet(XSSFSheet sheet) {
        AtomicInteger index = new AtomicInteger(0);
        database.stream().forEach(rowDTO -> fillRow(rowDTO, sheet, index.getAndIncrement()));
    }

    private void fillRow(RowDTO rowDTO, XSSFSheet sheet, int index) {
        Row topRow = sheet.createRow(index);
        Field[] rowFields = Arrays.stream(rowDTO.getClass().getDeclaredFields())
                .filter(field -> !field.getName().equals("EMPTY"))
                .toArray(Field[]::new);

        for (int i = 0; i < rowFields.length; i++) {
            Field field = rowFields[i];
            field.setAccessible(true);
            try {
                topRow.createCell(i).setCellValue(field.get(rowDTO).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private RowDTO toRowIfCorrect(Row row) {
        try {
            Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
            String name = row.getCell(1).getStringCellValue();
            Faculty faculty = Faculty.valueOf(row.getCell(2).getStringCellValue());
            KPIAward kpiDiploma = KPIAward.valueOf(checkIfKpiDiplomaIsCorrect(row.getCell(3).getStringCellValue()));
            StateAward stateDiploma = StateAward.valueOf(checkIfStateDiplomaIsCorrect(row.getCell(4).getStringCellValue()));
            String protocolNum = row.getCell(5).getStringCellValue();
            Year kpiDiplomaYear = Year.parse(row.getCell(6).getStringCellValue());
            Year stateDiplomaYear = Year.parse(row.getCell(7).getStringCellValue());
            KPIAward prognostication = KPIAward.valueOf(checkIfKpiDiplomaIsCorrect((row.getCell(8).getStringCellValue())));

            return new RowDTO(id, name, faculty,
                    kpiDiploma, stateDiploma, protocolNum,
                    kpiDiplomaYear, stateDiplomaYear, prognostication);
        } catch (Exception e) {
            return RowDTO.EMPTY;
        }
    }

    private String checkIfStateDiplomaIsCorrect(String stateDiploma) {
        for (var name :
                StateAward.values()) {
            if (name.getName().equals(stateDiploma)) {
                return stateDiploma;
            }
        }
        throw new RuntimeException("State award was not found");
    }

    private String checkIfKpiDiplomaIsCorrect(String kpiDiploma) {
        for (var name :
                KPIAward.values()) {
            if (name.getName().equals(kpiDiploma)) {
                return kpiDiploma;
            }
        }
        throw new RuntimeException("KPI award was not found");
    }


}
