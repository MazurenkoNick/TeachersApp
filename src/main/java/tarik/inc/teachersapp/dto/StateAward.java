package tarik.inc.teachersapp.dto;

import java.util.Optional;

public enum StateAward {
    NONE(" - "),
    PodyakaMON("Подяка МОН України"),
    GramotaMON("Грамота МОН України"),
    PochGramotaMON("Почесна грамота МОН України"),
    NagrZnakVidmOsv("Нагрудний знак 'Відмінник освіти'"),
    PZZaslDiyachNaukiITexniki("Почесне звання 'Заслуженний діяч науки і техніки України'"),
    PodyakaKM("Подяка КМ України"),
    GramotaKM("Грамота КМ України");

    public static final StateAward[] allValues = values();
    private final String name;


    StateAward(String name) {
        this.name = name;
    }

    public static StateAward fromString(String text) {
        for (StateAward award : allValues) {
            if (award.name.equalsIgnoreCase(text)) {
                return award;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public Optional<StateAward> next() {
        int nextId = this.ordinal() + 1;
        if (nextId < allValues.length)
            return Optional.of(allValues[nextId]);
        return Optional.empty();
    }
}
