package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.car.dto.CarCreateData;
import ru.zenegix.carrental.domain.car.request.CarCreateRequest;
import ru.zenegix.carrental.type.JacksonTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JacksonTest
public class CarJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConstruct_createData() throws Exception {
        var json = """
                {
                  "car_create_data": {
                    "model": "test",
                    "number": "a000cv"
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, CarCreateData.class);

        assertThat(data).extracting("model", "number")
                .isEqualTo(tuple("test", "a000cv").toList());
    }

    @Test
    public void shouldConstruct_createRequest() throws Exception {
        var json = """
                {
                  "car_create_request": {
                    "data": {
                      "model": "test",
                      "number": "a000cv"
                    }
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, CarCreateRequest.class);

        assertThat(data).extracting("data").extracting("model", "number")
                .isEqualTo(tuple("test", "a000cv").toList());
    }

}
