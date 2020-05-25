package ru.zenegix.carrental.domain.renter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenterDto {

    private final long id;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("middle_name")
    private final String middleName;

    @JsonProperty("last_name")
    private final String lastName;

}
