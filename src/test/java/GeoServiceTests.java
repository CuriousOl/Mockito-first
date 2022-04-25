import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.mockito.Mockito.doThrow;

public class GeoServiceTests {
    private static GeoService sut = Mockito.spy(GeoServiceImpl.class);

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.", new Location("New York", Country.USA, null, 0))
        );
    }

    @Test
    @ParameterizedTest
    @MethodSource("source")
    public void test_with_params_byIp(String ip, Location expected) {
        //act
        Location result = sut.byIp(ip);

        //assert
        Assertions.assertEquals(result.getCountry(), expected.getCountry());
        Assertions.assertEquals(result.getStreet(), expected.getStreet());
        Assertions.assertEquals(result.getCity(), expected.getCity());
        Assertions.assertEquals(result.getBuiling(), expected.getBuiling());
    }

    @Test
    public void test_by_Ip_throw_exception() {
        //act
        Location result = sut.byIp("132.");
        //assert
        Assertions.assertEquals(result, null);
    }

    @Test
    public void test_byCoordinates_throws_exception() {
        //act
        try {
            Location result = sut.byCoordinates(10.0, 10.0);
            Assertions.fail("Expected RuntimeException");
        } catch (RuntimeException thrown) {
            Assertions.assertEquals("Not implemented", thrown.getMessage());
        }
    }

}
