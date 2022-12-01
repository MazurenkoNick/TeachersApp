package tarik.inc.teachersapp.dto;

public enum StateAward {
    NONE(" - "),
    PodyakaMON("Подяка МОН України"),
    GramotaMON("Грамота МОН України"),
    PochGramotaMON("Почесна грамота МОН України"),
    NagrZnakVidmOsv("Нагрудний знак 'Відмінник освіти'"),
    PZZaslDiyachNaukiITexniki("Почесне звання 'Заслуженний діяч науки і техніки України'"),
    PodyakaKM("Подяка КМ України"),
    GramotaKM("Грамота КМ України");

    private final String name;
    public static final StateAward[] allValues = values();


    StateAward(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
