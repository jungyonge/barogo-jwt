package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.delivery;

import app.test.barogojwt.domain.deliveryservice.domain.delivery.Delivery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

public class DeliverySpecification {

    public static Specification<Delivery> equalParam(String param, Object value) {
        return new Specification<Delivery>() {
            @Override
            public Predicate toPredicate(Root<Delivery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) equal
                return criteriaBuilder.equal(root.get(param), value);
            }
        };
    }

    public static Specification<Delivery> likeContents(String contents) {
        return new Specification<Delivery>() {
            @Override
            public Predicate toPredicate(Root<Delivery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("contents"), "%" + contents + "%");
            }
        };
    }

    public static Specification<Delivery> betweenCreatedDatetime(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return new Specification<Delivery>() {
            @Override
            public Predicate toPredicate(Root<Delivery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 3) between
                return criteriaBuilder.between(root.get("updated"), startDatetime, endDatetime);
            }
        };
    }
}