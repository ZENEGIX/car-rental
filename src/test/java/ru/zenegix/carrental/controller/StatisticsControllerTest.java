package ru.zenegix.carrental.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zenegix.carrental.domain.statistics.StatisticsService;
import ru.zenegix.carrental.domain.statistics.response.AverageRentalTimeResponse;
import ru.zenegix.carrental.type.IntegrationTest;

import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
@IntegrationTest
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    public void testJsonResponse() throws Exception {
        when(this.statisticsService.calculateAverageRentalTime(any(), any())).thenAnswer(__ -> new AverageRentalTimeResponse(
                Map.of(1L, Map.of(
                        "AUDI", 10.,
                        "BMW", 20.
                ))
        ));

        this.mvc.perform(get("/api/statistics/averageRentalTime")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("items").isMap())
                .andExpect(jsonPath("items").value(Matchers.aMapWithSize(1)))
                .andExpect(jsonPath("items").value(Matchers.hasKey("1")))
                .andExpect(jsonPath("items.1").isMap())
                .andExpect(jsonPath("items.1").value(Matchers.aMapWithSize(2)))
                .andExpect(jsonPath("items.1").value(Matchers.hasEntry("AUDI", 10.)))
                .andExpect(jsonPath("items.1").value(Matchers.hasEntry("BMW", 20.)))
        ;
    }

}
