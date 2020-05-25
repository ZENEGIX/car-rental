package ru.zenegix.carrental;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.zenegix.carrental.domain.address.Address;
import ru.zenegix.carrental.domain.address.AddressRepository;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.car.CarRepository;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.history.HistoryRepository;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.point.PointRepository;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.domain.renter.RenterRepository;
import ru.zenegix.carrental.type.DatabaseTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DatabaseTest
public class EntityRelationshipsTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        var car = new Car();
        car.setModel("AUDI");
        car.setNumber("A123BC");

        this.entityManager.persist(car);

        var point = new Point();
        point.setName("Точка в Питере");
        var address = new Address();
        address.setCity("Санкт-Петербург");
        point.setAddress(address);

        this.entityManager.persist(point);

        var renter = new Renter();
        renter.setLastName("Иванов");
        renter.setFirstName("Иван");
        renter.setMiddleName("Иванович");

        this.entityManager.persist(renter);

        this.entityManager.flush();
        this.entityManager.clear(); // clear the cache
    }

    @Test
    public void testSetup() {
        assertThat(carRepository.count()).isEqualTo(1);
        assertThat(pointRepository.count()).isEqualTo(1);
        assertThat(renterRepository.count()).isEqualTo(1);
        assertThat(addressRepository.count()).isEqualTo(1);

        Point point = pointRepository.findByName("Точка в Питере");

        assertThat(point.getAddress().getCity()).isEqualTo("Санкт-Петербург");
    }

    @Test
    public void testCreateHistory() {
        var nowTime = LocalDateTime.now();
        var takeTime = nowTime.minusHours(1);

        var car = this.carRepository.getOne(1L);
        var point = this.pointRepository.getOne(1L);
        var renter = this.renterRepository.getOne(1L);
        var history = new History();
        history.setCar(car);
        history.setStartPoint(point);
        history.setEndPoint(point);
        history.setRenter(renter);
        history.setTakeDate(takeTime);
        history.setReturnDate(nowTime);

        this.entityManager.persist(history);
        this.entityManager.flush();
        this.entityManager.clear();

        history = this.historyRepository.getFirstByOrderByIdDesc();

        assertThat(history.getCar().getNumber()).isEqualTo("A123BC");
        assertThat(history.getStartPoint().getId())
                .isEqualTo(history.getEndPoint().getId());
        assertThat(history.getStartPoint().getName()).isEqualTo("Точка в Питере");
        assertThat(history.getRenter().getFirstName()).isEqualTo("Иван");
        assertThat(history.getRenter().getLastName()).isEqualTo("Иванов");
        assertThat(history.getRenter().getMiddleName()).isEqualTo("Иванович");
        assertThat(history.getTakeDate()).isCloseTo(takeTime, Assertions.within(1, ChronoUnit.MILLIS));
        assertThat(history.getReturnDate()).isCloseTo(nowTime, Assertions.within(1, ChronoUnit.MILLIS));
    }

}
