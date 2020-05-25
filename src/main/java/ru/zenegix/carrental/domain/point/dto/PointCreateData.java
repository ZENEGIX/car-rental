package ru.zenegix.carrental.domain.point.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import ru.zenegix.carrental.domain.address.dto.AddressCreateData;

@Data
@JsonRootName("point_create_data")
public class PointCreateData {

    private final String name;
    private final AddressCreateData address;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PointCreateData(@JsonProperty("name") String name, @JsonProperty("address") AddressCreateData address) {
        this.name = name;
        this.address = address;
    }

}
