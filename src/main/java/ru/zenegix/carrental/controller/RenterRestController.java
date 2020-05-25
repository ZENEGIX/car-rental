package ru.zenegix.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zenegix.carrental.domain.renter.dto.RenterDto;
import ru.zenegix.carrental.domain.renter.request.RenterCreateRequest;
import ru.zenegix.carrental.domain.renter.service.RenterService;

@RestController
@RequestMapping("/api/renters")
@RequiredArgsConstructor
public class RenterRestController {

    private final RenterService renterService;

    @PostMapping
    public RenterDto create(@RequestBody RenterCreateRequest createRequest) {
        return this.renterService.create(createRequest.getData()).toDto();
    }

}
