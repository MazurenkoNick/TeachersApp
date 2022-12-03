package tarik.inc.teachersapp.dto;

public enum Faculty {
    IAT("ІАТ"),
    IATE("ІАТЕ"),
    IEE("ІЕЕ"),
    IMZ("ІМЗ"),
    IPSA("ІПСА"),
    ITS("ІТС"),
    MMI("ММІ"),
    FTI("ФТІ"),
    IHF("ІХФ"),
    PBF("ПБФ"),
    RTF("РТФ"),
    FBMI("ФБМІ"),
    FBT("ФБТ"),
    FEA("ФЕА"),
    FEL("ФЕЛ"),
    FIOT("ФІОТ"),
    FL("ФЛ"),
    FMM("ФММ"),
    FMF("ФМФ"),
    FPM("ФПМ"),
    FSP("ФСП"),
    HTF("ХТФ"),
    ISZZI("ІСЗЗІ");


    public static final Faculty[] allValues = values();
    private final String name;

    Faculty(String name) {
        this.name = name;
    }

    public static Faculty fromString(String text) {
        for (Faculty faculty : allValues) {
            if (faculty.name.equalsIgnoreCase(text)) {
                return faculty;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

}
