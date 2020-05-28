package ru.zenegix.carrental.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zenegix.carrental.domain.car.Car;
import ru.zenegix.carrental.domain.car.controller.CarRestController;
import ru.zenegix.carrental.domain.car.service.CarService;
import ru.zenegix.carrental.type.IntegrationTest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarRestController.class)
@IntegrationTest
public class CarRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @Test
    public void shouldCreateCar() throws Exception {
        var request = """
                 {
                   "car_create_request": {
                     "data": {
                       "model": "test_model",
                       "number": "a123bc"
                     }
                   }
                 }
                """;
        when(this.carService.create(any())).thenAnswer(invocation -> {
            var result = Car.fromCreateData(invocation.getArgument(0));
            result.setId(333);

            return result;
        });

        this.mvc.perform(post("/api/cars")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("333"))
                .andExpect(jsonPath("model").value("test_model"))
                .andExpect(jsonPath("number").value("a123bc"));
    }

}
