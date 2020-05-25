package ru.zenegix.carrental.domain.renter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zenegix.carrental.domain.renter.Renter;
import ru.zenegix.carrental.domain.renter.RenterRepository;
import ru.zenegix.carrental.domain.renter.dto.RenterCreateData;

@Service
@RequiredArgsConstructor
public class RenterService {

    private final RenterRepository renterRepository;

    public Renter create(RenterCreateData createData) {
        return this.renterRepository.save(Renter.fromCreateData(createData));
    }

}
