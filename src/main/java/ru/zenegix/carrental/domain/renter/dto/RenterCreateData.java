package ru.zenegix.carrental.domain.renter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("renter_create_data")
public class RenterCreateData {

    private final String firstName, middleName, lastName;

    public RenterCreateData(
            @JsonProperty("first_name")String firstName,
            @JsonProperty("middle_name")String middleName,
            @JsonProperty("last_name")String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

}
