package ru.zenegix.carrental.domain.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    History getFirstByOrderByIdDesc();

    Page<History> findAllByCarId(long carId, Pageable pageable);

}
