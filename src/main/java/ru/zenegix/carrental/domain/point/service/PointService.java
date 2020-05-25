package ru.zenegix.carrental.domain.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.point.PointRepository;
import ru.zenegix.carrental.domain.point.dto.PointCreateData;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public Point create(PointCreateData createData) {
        return this.pointRepository.save(Point.fromCreateData(createData));
    }

}
