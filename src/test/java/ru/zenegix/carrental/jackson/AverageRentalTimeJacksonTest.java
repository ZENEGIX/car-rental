package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.statistics.response.AverageRentalTimeResponse;
import ru.zenegix.carrental.type.JacksonTest;

import java.util.Map;

@JacksonTest
public class AverageRentalTimeJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSerialize() throws Exception {
        Map<String, Double> firstPoint = Map.of(
                "AUDI", 10.,
                "BMW", 20.
        );
        Map<String, Double> secondPoint = Map.of(
                "AUDI", 40.,
                "BMW", 60.
        );
        Map<Long, Map<String, Double>> entryMap = Map.of(
                1L, firstPoint,
                2L, secondPoint
        );
        AverageRentalTimeResponse response = new AverageRentalTimeResponse(entryMap);

        System.out.println(objectMapper.writeValueAsString(response));
    }
}
