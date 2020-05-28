package ru.zenegix.carrental.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.zenegix.carrental.domain.address.Address;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.history.controller.HistoryRestController;
import ru.zenegix.carrental.domain.history.dto.HistoryCreateData;
import ru.zenegix.carrental.domain.history.service.HistoryService;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.type.IntegrationTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HistoryRestController.class)
@IntegrationTest
public class HistoryRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HistoryService historyService;

    @Test
    public void shouldCreateHistory() throws Exception {
        var request = """
                 {
                   "history_create_request": {
                     "data": {
                       "car_id": "1",
                       "renter_id": "1",
                       "start_point_id": "1",
                       "end_point_id": "1",
                       "take_date": "2020-05-24T14:06:20",
                       "return_date": "2020-05-24T15:06:20"
                     }
                   }
                 }
                """;
        when(this.historyService.create(Mockito.any()))
                .thenAnswer(invocation -> createHistory(invocation.getArgument(0), 1));

        this.mvc.perform(post("/api/history")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))

                .andExpect(jsonPath("car_model").value("testModel"))
                .andExpect(jsonPath("car_number").value("#1"))

                .andExpect(jsonPath("renter_full_name").value("Иванов Иван Иванович"))
        ;
    }

    @Test
    public void shouldReturnPage_withoutCurrentCar() throws Exception {
        this.performReturnPage(null)
                .andExpect(jsonPath("page").value("0"))
                .andExpect(jsonPath("totalPages").value("2"))
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content").value(Matchers.hasSize(2)));
    }

    @Test
    public void shouldReturnPage_withCurrentCar() throws Exception {
        this.performReturnPage(1L)
                .andExpect(jsonPath("page").value("0"))
                .andExpect(jsonPath("totalPages").value("1"))
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("content").value(Matchers.hasSize(1)))
                .andExpect(jsonPath("content.[0].car_number").value("#1"));
    }

    private ResultActions performReturnPage(Long carId) throws Exception {
        var histories = new ArrayList<History>();

        // Создаем 4 истории с 4 разными автомобилями
        for (var i = 1; i < 4; i++) {
            histories.add(createHistory(new HistoryCreateData(
                    i, 1, 1, 1,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            ), i));
        }

        when(this.historyService.getPage(any(), any())).thenAnswer(invocation -> {
            var _carId = invocation.<Long>getArgument(0);
            var pageable = invocation.<Pageable>getArgument(1);
            var content = histories.stream()
                    .filter(_carId == null
                            ? ((__) -> true)
                            : (history -> history.getCar().getId() == _carId)
                    ).limit(pageable.getPageSize())
                    .skip(pageable.getOffset())
                    .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, _carId == null ? 3 : 1);
        });

        var requestBuilder = get("/api/history")
                .param("page", "0")
                .param("size", "2");

        if (carId != null) {
            requestBuilder = requestBuilder.param("car_id", carId.toString());
        }

        return this.mvc.perform(requestBuilder).andDo(print());
    }

    private static History createHistory(HistoryCreateData createData, int historyId) {
        var car = new Car();
        car.setId(createData.getCarId());
        car.setModel("testModel");
        car.setNumber("#" + createData.getCarId());

        var renter = new Renter();
        renter.setId(createData.getRenterId());
        renter.setFirstName("Иван");
        renter.setLastName("Иванов");
        renter.setMiddleName("Иванович");

        var startPoint = new Point();
        var startPointAddress = new Address();
        startPointAddress.setCity("Piter");
        startPoint.setName("StartPoint");
        startPoint.setAddress(startPointAddress);
        startPoint.setId(createData.getStartPointId());

        var endPoint = new Point();
        var endPointAddress = new Address();
        endPointAddress.setCity("Piter");
        endPoint.setName("EndPoint");
        endPoint.setAddress(endPointAddress);
        endPoint.setId(createData.getEndPointId());

        var result = new History();
        result.setId(historyId);
        result.setCar(car);
        result.setStartPoint(startPoint);
        result.setEndPoint(endPoint);
        result.setRenter(renter);
        result.setTakeDate(createData.getTakeDate());
        result.setReturnDate(createData.getReturnDate());

        return result;
    }

}
