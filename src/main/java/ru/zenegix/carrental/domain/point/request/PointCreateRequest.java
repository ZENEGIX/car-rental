package ru.zenegix.carrental.domain.point.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import ru.zenegix.carrental.domain.point.dto.PointCreateData;

@Data
@JsonRootName("point_create_request")
public class PointCreateRequest {

    private final PointCreateData data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PointCreateRequest(@JsonProperty("data") PointCreateData data) {
        this.data = data;
    }

}
