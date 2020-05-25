package ru.zenegix.carrental.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import ru.zenegix.carrental.domain.history.History;
import ru.zenegix.carrental.domain.statistics.response.AverageRentalTimeResponse;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final EntityManager entityManager;

    public AverageRentalTimeResponse calculateAverageRentalTime(Long startPointId, String model) {
        var cb = this.entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Data.class);
        var root = criteria.from(History.class);
        var timeDiff = cb.function(
                "DATEDIFF",
                Integer.class,
                cb.literal("SECOND"),
                root.get("takeDate"),
                root.get("returnDate"));
        criteria.multiselect(
                root.get("car").get("model").alias("carModel"),
                root.get("startPoint").get("id").alias("pointId"),
                cb.avg(timeDiff).alias("avgTime")
        );
        criteria.groupBy(root.get("car").get("model"), root.get("startPoint").get("id"));

        if (startPointId != null) {
            criteria.where(cb.equal(root.get("startPoint").get("id"), startPointId));
        }

        if (model != null) {
            criteria.where(cb.equal(root.get("car").get("model"), model));
        }

        var list = this.entityManager.createQuery(criteria).getResultList();
        var map = list.stream()
                .collect(Collectors.groupingBy(Data::getPointId, Collectors.groupingBy(Data::getCarModel,
                        Collectors.mapping(Data::getAvgTime, Collectors.reducing(.0, Double::sum))
                )));

        return new AverageRentalTimeResponse(map);
    }

    @ToString
    @lombok.Data
    @AllArgsConstructor
    public static class Data {

        private String carModel;
        private long pointId;
        private double avgTime;

    }

}
