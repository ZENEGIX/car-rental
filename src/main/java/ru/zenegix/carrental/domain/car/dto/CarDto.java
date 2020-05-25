package ru.zenegix.carrental.domain.car.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {

    private final long id;
    private final String model;
    private final String number;

}
