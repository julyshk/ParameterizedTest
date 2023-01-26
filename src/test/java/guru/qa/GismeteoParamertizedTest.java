package guru.qa;

import com.codeborne.selenide.Configuration;
import guru.qa.data.Tabs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;


public class GismeteoParamertizedTest {

    @BeforeEach
    void beforeTest() {
        Configuration.browserSize = "1920x1080";
        open("https://www.gismeteo.ru");
    }

    @CsvSource({
            "Москва, Погода в Москве сегодня",
            "Санкт-Петербург, Погода в Санкт-Петербурге сегодня"
    })

    @ParameterizedTest (name = "При поиске города {0} должен быть заголовок {1}")
    @Tags({@Tag("CRITICAL"), @Tag("UI_TEST")})
    public void Calc(String city, String header) {
        $(".input").setValue(city);
        sleep(1000);
        $(".input").pressEnter();
        $(".page-title").shouldHave(text(header));
    }

    static Stream<Arguments> submenuForTab() {
        return Stream.of(
                Arguments.of(Tabs.MAPS, Arrays.asList("Осадки", "Температура", "Ветер", "Облачность")),
                Arguments.of(Tabs.APP, Arrays.asList("Для мобильных устройств", "Компьютеров", "Телевизоров"))
        );
    }

    @MethodSource("submenuForTab")
    @ParameterizedTest(name = "Для вкладки {0} отображаются пункты подменю {1}")
    @Tags({@Tag("CRITICAL"), @Tag("UI_TEST")})
    void submenuForTab(Tabs tabs, List<String> buttons) {
        $$(".menu-main a").find(text(tabs.tab)).click();
        $$(".header-subnav-menu .header-subnav-link").filter(visible).shouldHave(texts(buttons));
    }

}
