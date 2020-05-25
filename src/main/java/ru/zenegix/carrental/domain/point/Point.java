package ru.zenegix.carrental.domain.point;

import lombok.Data;
import ru.zenegix.carrental.domain.address.Address;
import ru.zenegix.carrental.domain.point.dto.PointCreateData;
import ru.zenegix.carrental.domain.point.dto.PointDto;

import javax.persistence.*;

@Data
@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(generator = "point_generator")
    private long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public PointDto toDto() {
        return PointDto.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address.toDto())
                .build();
    }

    public static Point fromCreateData(PointCreateData createData) {
        var entity = new Point();
        entity.setName(createData.getName());
        entity.setAddress(Address.fromCreateData(createData.getAddress()));

        return entity;
    }

}
