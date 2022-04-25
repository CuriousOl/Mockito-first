import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.Mockito;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTests {
    MessageSender sut;
    final Map<String, String> headers = new HashMap<>();

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19", "Добро пожаловать"),
                Arguments.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19", "Welcome"));
    }

    @AfterEach
    public void clear() {
        headers.clear();
        System.out.println();
    }

    @ParameterizedTest
    @MethodSource("source")
    public void test_send_with_params(String header, String ip, String expected) {
        //arrange
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        sut = new MessageSenderImpl(geoService, localizationService);
        headers.put(header, ip);

        //act
        String result = sut.send(headers);

        //assert
        Assertions.assertEquals(result, expected);
    }
}
