package ru.zenegix.carrental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import ru.zenegix.carrental.domain.address.Address;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.car.CarRepository;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.history.HistoryRepository;
import ru.zenegix.carrental.domain.history.dto.HistoryCreateData;
import ru.zenegix.carrental.domain.history.service.HistoryService;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.point.PointRepository;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.domain.renter.RenterRepository;
import ru.zenegix.carrental.type.DatabaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DatabaseTest
@Import(HistoryService.class)
public class HistoryServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;

    @BeforeEach
    public void setup() {
        var car = new Car();
        car.setModel("AUDI");
        car.setNumber("123");

        var car0 = new Car();
        car0.setModel("AUDI");
        car0.setNumber("124");

        var car1 = new Car();
        car1.setModel("BMW");
        car1.setNumber("125");

        var point = new Point();
        Address address = new Address();
        address.setCity("Piter");
        point.setAddress(address);
        point.setName("FirstPoint");

        var renter = new Renter();
        renter.setLastName("Иванов");
        renter.setFirstName("Иван");
        renter.setMiddleName("Иванович");

        this.entityManager.persist(car);
        this.entityManager.persist(car0);
        this.entityManager.persist(car1);
        this.entityManager.persist(point);
        this.entityManager.persist(renter);
        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void shouldCreateHistory() {
        var data = new HistoryCreateData(
                1, 1, 1, 1,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now()
        );
        var history = this.historyService.create(data);

        assertThat(history.getId()).isEqualTo(1);
        assertThat(history).extracting("car")
                .extracting("id", "number", "model")
                .isEqualTo(tuple(1L, "123", "AUDI").toList());
        assertThat(history.getRenter())
                .extracting("id", "lastName", "firstName", "middleName")
                .isEqualTo(tuple(1L, "Иванов", "Иван", "Иванович").toList());

        for (var point : new Point[]{ history.getStartPoint(), history.getEndPoint() }) {
            assertThat(point)
                    .extracting("id", "name", "address.city")
                    .isEqualTo(tuple(1L, "FirstPoint", "Piter").toList());
        }
    }

    @Test
    public void shouldReturnPage_withTotalElements3_whenCarIdIsNull() {
        this.createHistory(1, 1, 3);
        var page = this.historyService.getPage(null, PageRequest.of(0, 2));

        assertThat(page.getTotalPages())
                .isEqualTo(2);
        assertThat(page.getTotalElements())
                .isEqualTo(3);
    }

    @Test
    public void shouldReturnPage_withTotalElements2_whenCarIdIs1() {
        this.createHistory(1, 1, 3);
        var page = this.historyService.getPage(1L, PageRequest.of(0, 2));

        assertThat(page.getTotalPages())
                .isEqualTo(1);
        assertThat(page.getTotalElements())
                .isEqualTo(2);
    }

    private void createHistory(long... carIds) {
        for (var carId : carIds) {
            var history = new History();
            history.setCar(this.carRepository.getOne(carId));
            history.setStartPoint(this.pointRepository.getOne(1L));
            history.setEndPoint(this.pointRepository.getOne(1L));
            history.setRenter(this.renterRepository.getOne(1L));
            history.setTakeDate(LocalDateTime.now().minusHours(1));
            history.setReturnDate(LocalDateTime.now());

            this.historyRepository.save(history);
        }
    }

}
