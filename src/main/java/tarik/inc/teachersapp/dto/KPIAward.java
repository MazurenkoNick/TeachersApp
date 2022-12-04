package tarik.inc.teachersapp.dto;

import java.util.Arrays;
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

    public static final KPIAward[] allValues = values();
    private final String name;

    KPIAward(String name) {
        this.name = name;
    }

    public static KPIAward fromString(String name) {
        return Arrays.stream(values())
                .filter(kpiAward -> kpiAward.getName().equals(name))
                .findFirst()
                .orElse(null);
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

    @Override
    public String toString() {
        return this.name;
    }
}

