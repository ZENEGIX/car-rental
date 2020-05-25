package ru.zenegix.carrental.domain.point.dto;

import lombok.Builder;
import lombok.Data;
import ru.zenegix.carrental.domain.address.dto.AddressDto;

@Data
@Builder
public class PointDto {

    private final long id;
    private final String name;
    private final AddressDto address;

}
