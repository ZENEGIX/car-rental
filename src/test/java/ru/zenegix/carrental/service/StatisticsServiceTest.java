package ru.zenegix.carrental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.zenegix.carrental.domain.address.Address;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.domain.statistics.StatisticsService;
import ru.zenegix.carrental.type.DatabaseTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DatabaseTest
@Import(StatisticsService.class)
public class StatisticsServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StatisticsService statisticsService;

    @BeforeEach
    public void setup() {
        var entityManager = this.entityManager.getEntityManager();

        var renter = new Renter();
        renter.setLastName("Иванов");
        renter.setFirstName("Иван");
        renter.setMiddleName("Иванович");

        var car1 = new Car();
        car1.setNumber("#1");
        car1.setModel("AUDI");

        var car2 = new Car();
        car2.setNumber("#2");
        car2.setModel("AUDI");

        var car3 = new Car();
        car3.setNumber("#3");
        car3.setModel("BMW");

        var point = new Point();
        point.setName("test");
        var address = new Address();
        address.setCity("Piter");
        point.setAddress(address);

        entityManager.persist(renter);
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.persist(point);

        for (var car : new Car[]{car1, car2, car3}) {
            var history = new History();
            history.setRenter(renter);
            history.setCar(car);
            history.setTakeDate(LocalDateTime.now().minusHours(2));
            history.setReturnDate(LocalDateTime.now());
            history.setStartPoint(point);
            history.setEndPoint(point);

            entityManager.persist(history);
        }

        entityManager.flush();
    }

    @Test
    public void test() {
        var response = this.statisticsService.calculateAverageRentalTime(null, null);

        assertThat(response.getItems())
                .hasSize(1)
                .containsKey(1L);
        assertThat(response.getItems().get(1L))
                .hasSize(2)
                .containsKey("AUDI")
                .containsKey("BMW");
        assertThat(response.getItems().get(1L))
                .extracting("AUDI", "BMW")
                .isEqualTo(tuple(7200.0, 7200.0).toList());
    }

    @Test
    public void test0() {
        var response = this.statisticsService.calculateAverageRentalTime(1L, null);

        assertThat(response.getItems())
                .hasSize(1)
                .containsKey(1L);
        assertThat(response.getItems().get(1L))
                .hasSize(2)
                .containsKey("AUDI")
                .containsKey("BMW");
        assertThat(response.getItems().get(1L))
                .extracting("AUDI", "BMW")
                .isEqualTo(tuple(7200.0, 7200.0).toList());
    }

    @Test
    public void test2() {
        var response = this.statisticsService.calculateAverageRentalTime(2L, null);

        assertThat(response.getItems()).hasSize(0);
    }

    @Test
    public void test3() {
        var response = this.statisticsService.calculateAverageRentalTime(null, "QWERTY");

        assertThat(response.getItems()).hasSize(0);
    }

    @Test
    public void test1() {
        var response = this.statisticsService.calculateAverageRentalTime(null, "AUDI");

        assertThat(response.getItems())
                .hasSize(1)
                .containsKey(1L);
        assertThat(response.getItems().get(1L))
                .hasSize(1)
                .containsKey("AUDI");
        assertThat(response.getItems().get(1L))
                .extracting("AUDI")
                .isEqualTo(7200.0);
    }

}
