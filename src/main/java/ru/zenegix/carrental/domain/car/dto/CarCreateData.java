package ru.zenegix.carrental.domain.car.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("car_create_data")
public class CarCreateData {

    private final String model;
    private final String number;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarCreateData(@JsonProperty("model") String model, @JsonProperty("number") String number) {
        this.model = model;
        this.number = number;
    }

}
