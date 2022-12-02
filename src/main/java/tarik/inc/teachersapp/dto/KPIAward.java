package tarik.inc.teachersapp.dto;

import java.util.Optional;

public enum KPIAward {
    NONE(" - "),
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
    public static final KPIAward[] allValues = values();

    KPIAward(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Optional<KPIAward> next() {
        int nextId = this.ordinal() + 1;
        if (nextId < allValues.length)
            return Optional.of(allValues[nextId]);
        return Optional.empty();
    }

    public static KPIAward fromString(String text) {
        for (KPIAward award : allValues) {
            if (award.name.equalsIgnoreCase(text)) {
                return award;
            }
        }
        return null;
    }
}

