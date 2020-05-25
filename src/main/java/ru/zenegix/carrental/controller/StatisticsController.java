package ru.zenegix.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zenegix.carrental.domain.statistics.StatisticsService;
import ru.zenegix.carrental.domain.statistics.response.AverageRentalTimeResponse;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/averageRentalTime")
    public AverageRentalTimeResponse averageRentalTime(
            @RequestParam(value = "param_id", required = false) Long pointId,
            @RequestParam(value = "car_model", required = false) String carModel
    ) {
        return this.statisticsService.calculateAverageRentalTime(pointId, carModel);
    }

}
