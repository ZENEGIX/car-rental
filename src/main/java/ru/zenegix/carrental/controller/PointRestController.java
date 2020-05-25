package ru.zenegix.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zenegix.carrental.domain.point.dto.PointDto;
import ru.zenegix.carrental.domain.point.request.PointCreateRequest;
import ru.zenegix.carrental.domain.point.service.PointService;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointRestController {

    private final PointService pointService;

    @PostMapping
    public PointDto create(@RequestBody PointCreateRequest pointCreateRequest) {
        return this.pointService.create(pointCreateRequest.getData()).toDto();
    }

}
