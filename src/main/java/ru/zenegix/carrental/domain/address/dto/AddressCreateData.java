package ru.zenegix.carrental.domain.address.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("address_create_data")
public class AddressCreateData {

    private final String city;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddressCreateData(@JsonProperty("city") String city) {
        this.city = city;
    }

}
