package ru.zenegix.carrental.domain.renter.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import ru.zenegix.carrental.domain.renter.dto.RenterCreateData;

@Data
@JsonRootName("renter_create_request")
public class RenterCreateRequest {

    private final RenterCreateData data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RenterCreateRequest(@JsonProperty("data") RenterCreateData data) {
        this.data = data;
    }

}
