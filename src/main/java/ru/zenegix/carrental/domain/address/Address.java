package ru.zenegix.carrental.domain.address;

import lombok.Data;
import ru.zenegix.carrental.domain.address.dto.AddressCreateData;
import ru.zenegix.carrental.domain.address.dto.AddressDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(generator = "address_generator")
    private long id;

    private String city;

    public AddressDto toDto() {
        return AddressDto.builder()
                .city(this.city)
                .build();
    }

    public static Address fromCreateData(AddressCreateData addressCreateData) {
        var address = new Address();
        address.setCity(addressCreateData.getCity());

        return address;
    }

}
