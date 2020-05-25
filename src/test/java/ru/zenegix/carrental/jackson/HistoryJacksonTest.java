package ru.zenegix.carrental.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zenegix.carrental.domain.history.dto.HistoryCreateData;
import ru.zenegix.carrental.domain.history.request.HistoryCreateRequest;
import ru.zenegix.carrental.type.JacksonTest;

import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@JacksonTest
public class HistoryJacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConstruct_createData() throws Exception {
        var json = """
                {
                  "history_create_data": {
                    "car_id": "1",
                    "start_point_id": "1",
                    "end_point_id": "1",
                    "renter_id": "1",
                    "take_date": "2020-05-24T10:00:00",
                    "return_date": "2020-05-24T12:00:00"
                  }
                }
                """;
        var data = this.objectMapper.readValue(json, HistoryCreateData.class);

        assertThat(data)
                .extracting("carId", "startPointId", "endPointId", "renterId")
                .isEqualTo(tuple(1L, 1L, 1L, 1L).toList());

        assertThat(data.getTakeDate())
                .isCloseTo("2020-05-24T10:00:00", Assertions.within(1, ChronoUnit.MILLIS));
        assertThat(data.getReturnDate())
                .isCloseTo("2020-05-24T12:00:00", Assertions.within(1, ChronoUnit.MILLIS));
    }

    @Test
    public void shouldConstruct_createRequest() throws Exception {
        var json = """
                {
                  "history_create_request": {
                    "data": {
                      "car_id": "2",
                      "start_point_id": "2",
                      "end_point_id": "2",
                      "renter_id": "2",
                      "take_date": "2020-05-23T10:00:00",
                      "return_date": "2020-05-23T12:00:00"
                    }
                  }
                }
                """;
        var request = this.objectMapper.readValue(json, HistoryCreateRequest.class);

        assertThat(request).extracting("data")
                .extracting("carId", "startPointId", "endPointId", "renterId")
                .isEqualTo(tuple(2L, 2L, 2L, 2L).toList());

        assertThat(request.getData().getTakeDate())
                .isCloseTo("2020-05-23T10:00:00", Assertions.within(1, ChronoUnit.MILLIS));
        assertThat(request.getData().getReturnDate())
                .isCloseTo("2020-05-23T12:00:00", Assertions.within(1, ChronoUnit.MILLIS));
    }

}
