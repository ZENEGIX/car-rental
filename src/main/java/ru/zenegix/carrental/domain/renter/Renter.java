package ru.zenegix.carrental.domain.renter;

import lombok.Data;
import ru.zenegix.carrental.domain.renter.dto.RenterCreateData;
import ru.zenegix.carrental.domain.renter.dto.RenterDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "renters")
public class Renter {

    @Id
    @GeneratedValue(generator = "renter_generator")
    private long id;

    private String firstName, middleName, lastName;

    public RenterDto toDto() {
        return RenterDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .middleName(this.middleName)
                .lastName(this.lastName)
                .build();
    }

    public static Renter fromCreateData(RenterCreateData createData) {
        var entity = new Renter();
        entity.setFirstName(createData.getFirstName());
        entity.setMiddleName(createData.getMiddleName());
        entity.setLastName(createData.getLastName());

        return entity;
    }

}
