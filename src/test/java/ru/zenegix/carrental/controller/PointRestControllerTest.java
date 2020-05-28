package ru.zenegix.carrental.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zenegix.carrental.domain.point.Point;
import ru.zenegix.carrental.domain.point.controller.PointRestController;
import ru.zenegix.carrental.domain.point.service.PointService;
import ru.zenegix.carrental.type.IntegrationTest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PointRestController.class)
@IntegrationTest
public class PointRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointService pointService;

    @Test
    public void shouldCreatePoint() throws Exception {
        var request = """
                 {
                   "point_create_request": {
                     "data": {
                       "name": "test",
                       "address": {
                         "city": "Piter"
                       }
                     }
                   }
                 }
                """;
        when(this.pointService.create(any())).thenAnswer(invocation -> {
            var result = Point.fromCreateData(invocation.getArgument(0));
            result.setId(666);

            return result;
        });

        this.mvc.perform(post("/api/points")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("666"))
                .andExpect(jsonPath("name").value("test"))
                .andExpect(jsonPath("address.city").value("Piter"));
    }

}
