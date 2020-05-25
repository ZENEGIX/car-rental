package ru.zenegix.carrental.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zenegix.carrental.domain.car.CarRepository;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.history.HistoryRepository;
import ru.zenegix.carrental.domain.history.dto.HistoryCreateData;
import ru.zenegix.carrental.domain.point.PointRepository;
import ru.zenegix.carrental.domain.renter.RenterRepository;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final CarRepository carRepository;
    private final RenterRepository renterRepository;
    private final PointRepository pointRepository;
    private final HistoryRepository historyRepository;

    public Page<History> getPage(Long carId, Pageable pageable) {
        return carId == null
                ? this.historyRepository.findAll(pageable)
                : this.historyRepository.findAllByCarId(carId, pageable);
    }

    public History create(HistoryCreateData createData) {
        var history = new History();
        var car = this.carRepository.getOne(createData.getCarId());
        var renter = this.renterRepository.getOne(createData.getRenterId());
        var startPoint = this.pointRepository.getOne(createData.getStartPointId());
        var endPoint = this.pointRepository.getOne(createData.getEndPointId());

        history.setCar(car);
        history.setRenter(renter);
        history.setStartPoint(startPoint);
        history.setEndPoint(endPoint);
        history.setTakeDate(createData.getTakeDate());
        history.setReturnDate(createData.getReturnDate());

        return this.historyRepository.save(history);
    }

}
