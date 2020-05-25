package ru.zenegix.carrental.domain.statistics.response;

import lombok.Data;

import java.util.Map;

@Data
public class AverageRentalTimeResponse {

    private final Map<Long, Map<String, Double>> items;

}
