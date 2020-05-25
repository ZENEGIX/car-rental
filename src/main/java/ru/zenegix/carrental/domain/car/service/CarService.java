package ru.zenegix.carrental.domain.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.car.dto.CarCreateData;
import ru.zenegix.carrental.domain.car.CarRepository;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car create(CarCreateData createData) {
        return this.carRepository.save(Car.fromCreateData(createData));
    }

}
