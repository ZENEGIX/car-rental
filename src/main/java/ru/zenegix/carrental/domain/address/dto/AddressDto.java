package ru.zenegix.carrental.domain.address.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private final String city;

}
