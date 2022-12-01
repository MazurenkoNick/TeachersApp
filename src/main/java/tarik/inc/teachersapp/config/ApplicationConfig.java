package tarik.inc.teachersapp.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ApplicationConfig {

    public static CsvMapper getCsvMapper() {
        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static CsvSchema getCsvSchema() {
        return CsvSchema.builder().setUseHeader(true)
                .addColumn("ID")
                .addColumn("NAME")
                .addColumn("FACULTY")
                .addColumn("KPI DIPLOMA")
                .addColumn("STATE DIPLOMA")
                .addColumn("PROTOCOL NUMBER")
                .addColumn("KPI DIPLOMA YEAR")
                .addColumn("STATE DIPLOMA YEAR")
                .build();
    }

}
