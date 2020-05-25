package ru.zenegix.carrental.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.domain.renter.service.RenterService;
import ru.zenegix.carrental.type.IntegrationTest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RenterRestController.class)
@IntegrationTest
public class RenterRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RenterService renterService;

    @Test
    public void shouldCreatePoint() throws Exception {
        var request = """
                 {
                   "renter_create_request": {
                     "data": {
                       "first_name": "Иван",
                       "middle_name": "Иванович",
                       "last_name": "Иванов"
                     }
                   }
                 }
                """;
        when(this.renterService.create(any())).thenAnswer(invocation -> {
            var result = Renter.fromCreateData(invocation.getArgument(0));
            result.setId(111);

            return result;
        });

        this.mvc.perform(post("/api/renters")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("111"))
                .andExpect(jsonPath("first_name").value("Иван"))
                .andExpect(jsonPath("middle_name").value("Иванович"))
                .andExpect(jsonPath("last_name").value("Иванов"));
    }

}
