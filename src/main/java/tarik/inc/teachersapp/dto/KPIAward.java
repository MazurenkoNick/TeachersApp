package tarik.inc.teachersapp.dto;

public enum KPIAward {
    GVchenRada("Грамота Вченої ради"),
    PGVchenRada("Почесна грамота Вченої ради"),
    PVVchenRada("Почесна відзнака Вченої ради"),
    PZZaslViklKPI("Почесне звання 'Заслуженний викладач КПІ'"),
    PZZaslProfKPI("Почесне звання 'Заслуженний професор КПІ'"),
    PZZaslNaukKPI("Почесне звання 'Заслуженний науковець КПІ'"),
    PZZaslPracKPI("Почесне звання 'Заслуженний працівник КПІ'"),
    PVVidatDiyachKPI("Почесна відзнака 'Видатний діяч КПІ'"),
    PZPochDocKPI("Почесне звання 'Почесний доктор КПІ'"),
    PVSlujVidKPI("Почесна відзнака 'За служіння та відданість КПІ'"),
    PVZaslPeredKPI("Почесна відзнака 'За заслуги перед КПІ'");

    private final String name;

    KPIAward(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

