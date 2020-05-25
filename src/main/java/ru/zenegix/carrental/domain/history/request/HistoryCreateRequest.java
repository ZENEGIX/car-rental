package ru.zenegix.carrental.domain.history.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import ru.zenegix.carrental.domain.history.dto.HistoryCreateData;

@Data
@JsonRootName("history_create_request")
public class HistoryCreateRequest {

    private final HistoryCreateData data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public HistoryCreateRequest(@JsonProperty("data") HistoryCreateData data) {
        this.data = data;
    }

}
