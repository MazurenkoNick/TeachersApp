package tarik.inc.teachersapp.dto;

public enum Faculty {
    IAT("ІАТ"),
    IATE("IATE"),
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


    private final String name;

    Faculty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
