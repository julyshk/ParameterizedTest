package guru.qa.data;

public enum Tabs {
    NEWS("Новости"),
    MAPS("Карты"),
    INFO("Информеры"),
    APP("Приложения");

    public final String tab;
    Tabs(String tab) {
        this.tab = tab;
    }
}

