package ru.zenegix.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zenegix.carrental.domain.car.service.CarService;
import ru.zenegix.carrental.domain.car.dto.CarDto;
import ru.zenegix.carrental.domain.car.request.CarCreateRequest;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarRestController {

    private final CarService carService;

    @PostMapping
    public CarDto createCar(@RequestBody CarCreateRequest carCreateRequest) {
        return this.carService.create(carCreateRequest.getData()).toDto();
    }

}
