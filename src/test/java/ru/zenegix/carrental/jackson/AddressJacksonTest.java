package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.address.dto.AddressCreateData;
import ru.zenegix.carrental.type.JacksonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JacksonTest
public class AddressJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConstruct_createData() throws Exception {
        var json = """
                {
                  "address_create_data": {
                    "city": "Piter"
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, AddressCreateData.class);

        assertThat(data)
                .extracting("city")
                .isEqualTo("Piter");
    }

}
