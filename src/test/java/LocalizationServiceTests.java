
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceTests {
    private static LocalizationService sut = Mockito.spy(LocalizationServiceImpl.class);

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("source")
    public void test_locale(Country country, String expected) {
        //act
        String result = sut.locale(country);
        //assert
        assertEquals(result, expected);
    }

}
