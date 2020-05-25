package ru.zenegix.carrental.domain.history;

import lombok.Data;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.history.dto.HistoryDto;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.renter.Renter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(generator = "history_generator")
    private long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "start_point_id")
    private Point startPoint;

    @ManyToOne
    @JoinColumn(name = "end_point_id")
    private Point endPoint;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private Renter renter;

    @JoinColumn(name = "take_date")
    private LocalDateTime takeDate;

    @JoinColumn(name = "return_date")
    private LocalDateTime returnDate;

    public HistoryDto toDto() {
        return HistoryDto.builder()
                .id(this.id)
                .car(this.car.toDto())
                .renter(this.renter.toDto())
                .returnDate(this.returnDate)
                .takeDate(this.takeDate)
                .endPoint(this.endPoint.toDto())
                .startPoint(this.startPoint.toDto())
                .build();
    }

}
