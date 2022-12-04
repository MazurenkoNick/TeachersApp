package tarik.inc.teachersapp.dto;

import java.util.Arrays;

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

    public static Faculty fromString(String name) {
        return Arrays.stream(values())
                .filter(faculty -> faculty.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
