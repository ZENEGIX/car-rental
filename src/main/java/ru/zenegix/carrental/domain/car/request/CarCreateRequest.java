package ru.zenegix.carrental.domain.car.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import ru.zenegix.carrental.domain.car.dto.CarCreateData;

@Data
@JsonRootName("car_create_request")
public class CarCreateRequest {

    private final CarCreateData data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarCreateRequest(@JsonProperty("data") CarCreateData data) {
        this.data = data;
    }
}
