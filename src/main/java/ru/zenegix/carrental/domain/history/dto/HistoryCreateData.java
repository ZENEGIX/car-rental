package ru.zenegix.carrental.domain.history.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonRootName("history_create_data")
public class HistoryCreateData {

    private final long carId;
    private final long startPointId;
    private final long endPointId;
    private final long renterId;
    private final LocalDateTime takeDate;
    private final LocalDateTime returnDate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public HistoryCreateData(
            @JsonProperty("car_id") long carId,
            @JsonProperty("start_point_id") long startPointId,
            @JsonProperty("end_point_id") long endPointId,
            @JsonProperty("renter_id") long renterId,
            @JsonProperty("take_date") LocalDateTime takeDate,
            @JsonProperty("return_date") LocalDateTime returnDate) {
        this.carId = carId;
        this.startPointId = startPointId;
        this.endPointId = endPointId;
        this.renterId = renterId;
        this.takeDate = takeDate;
        this.returnDate = returnDate;
    }
}
