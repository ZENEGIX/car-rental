package ru.zenegix.carrental.domain.car;

import lombok.Data;
import ru.zenegix.carrental.domain.car.dto.CarCreateData;
import ru.zenegix.carrental.domain.car.dto.CarDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(generator = "car_generator")
    private long id;

    private String model;

    private String number;

    public CarDto toDto() {
        return CarDto.builder()
                .id(this.id)
                .model(this.model)
                .number(this.number)
                .build();
    }

    public static Car fromCreateData(CarCreateData carCreateData) {
        var car = new Car();
        car.setNumber(carCreateData.getNumber());
        car.setModel(carCreateData.getModel());

        return car;
    }

}
