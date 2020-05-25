package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.point.dto.PointCreateData;
import ru.zenegix.carrental.domain.point.request.PointCreateRequest;
import ru.zenegix.carrental.type.JacksonTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JacksonTest
public class PointJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConstruct_createData() throws Exception {
        var json = """
                {
                  "point_create_data": {
                    "name": "Test",
                    "address": {
                      "city": "Piter"
                    }
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, PointCreateData.class);

        assertThat(data)
                .extracting("address.city", "name")
                .isEqualTo(tuple("Piter", "Test").toList());
    }

    @Test
    public void shouldConstruct_createRequest() throws Exception {
        var json = """
                {
                  "point_create_request": {
                    "data": {
                      "name": "Test",
                      "address": {
                        "city": "Piter"
                      }
                    }
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, PointCreateRequest.class);

        assertThat(data).extracting("data")
                .extracting("address.city", "name")
                .isEqualTo(tuple("Piter", "Test").toList());
    }

}
