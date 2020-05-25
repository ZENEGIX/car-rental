package ru.zenegix.carrental.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.history.HistoryView;
import ru.zenegix.carrental.domain.history.dto.HistoryDto;
import ru.zenegix.carrental.domain.history.request.HistoryCreateRequest;
import ru.zenegix.carrental.domain.history.service.HistoryService;
import ru.zenegix.carrental.dto.PageDto;

@RequestMapping("/api/history")
@RestController
@RequiredArgsConstructor
public class HistoryRestController {

    private final HistoryService historyService;

    @GetMapping
    @JsonView(HistoryView.Common.class)
    public PageDto<HistoryDto> getHistory(
            @RequestParam(value = "car_id", required = false) Long carId,
            Pageable pageable
    ) {
        return PageDto.fromPage(this.historyService.getPage(carId, pageable).map(History::toDto));
    }

    @PostMapping
    @JsonView(HistoryView.Common.class)
    public HistoryDto addHistory(@RequestBody HistoryCreateRequest createRequest) {
        return this.historyService.create(createRequest.getData()).toDto();
    }

}
