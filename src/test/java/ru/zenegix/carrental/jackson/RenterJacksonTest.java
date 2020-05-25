package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.renter.dto.RenterCreateData;
import ru.zenegix.carrental.domain.renter.request.RenterCreateRequest;
import ru.zenegix.carrental.type.JacksonTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JacksonTest
public class RenterJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConstruct_createData() throws Exception {
        var json = """
                {
                  "renter_create_data": {
                    "first_name": "Иван",
                    "last_name": "Иванов",
                    "middle_name": "Иванович"
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, RenterCreateData.class);

        assertThat(data)
                .extracting("firstName", "lastName", "middleName")
                .isEqualTo(tuple("Иван", "Иванов", "Иванович").toList());
    }

    @Test
    public void shouldConstruct_createRequest() throws Exception {
        var json = """
                {
                  "renter_create_request": {
                    "data": {
                      "first_name": "Иван",
                      "last_name": "Иванов",
                      "middle_name": "Иванович"
                    }
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, RenterCreateRequest.class);

        assertThat(data).extracting("data")
                .extracting("firstName", "lastName", "middleName")
                .isEqualTo(tuple("Иван", "Иванов", "Иванович").toList());
    }

}
