package ru.zenegix.carrental.domain.history.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import ru.zenegix.carrental.domain.car.dto.CarDto;
import ru.zenegix.carrental.domain.history.HistoryView;
import ru.zenegix.carrental.domain.point.dto.PointDto;
import ru.zenegix.carrental.domain.renter.dto.RenterDto;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoryDto {

    private final long id;

    private final CarDto car;

    private final PointDto startPoint;

    private final PointDto endPoint;

    private final RenterDto renter;

    private final LocalDateTime takeDate;

    private final LocalDateTime returnDate;

    @JsonView(HistoryView.Common.class)
    public long getId() {
        return this.id;
    }

    @JsonView(HistoryView.Common.class)
    @JsonGetter("car_model")
    public String getCarModel() {
        return this.car == null ? null : this.car.getModel();
    }

    @JsonView(HistoryView.Common.class)
    @JsonGetter("car_number")
    public String getCarNumber() {
        return this.car == null ? null : this.car.getNumber();
    }

    @JsonView(HistoryView.Common.class)
    @JsonGetter("renter_full_name")
    public String getRenterFullName() {
        if (this.renter == null) {
            return null;
        }

        return String.format("%s %s %s", this.renter.getLastName(), this.renter.getFirstName(), this.renter.getMiddleName());
    }

    @JsonView(HistoryView.Common.class)
    public LocalDateTime getTakeDate() {
        return this.takeDate;
    }

    @JsonView(HistoryView.Common.class)
    public LocalDateTime getReturnDate() {
        return this.returnDate;
    }

}
