package com.hsf302.ch4.specification;

import com.hsf302.ch4.dto.StudentFilterDTO;
import com.hsf302.ch4.pojo.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterBy(StudentFilterDTO filter) {
        return (root, query, cb) -> {
            if (filter == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            // 1. Keyword: fullName chứa keyword HOẶC email chứa keyword
            if (filter.keyword() != null && !filter.keyword().isBlank()) {
                String pattern = "%" + filter.keyword().trim().toLowerCase() + "%";
                Predicate nameMatch = cb.like(cb.lower(root.get("fullName")), pattern);
                Predicate emailMatch = cb.like(cb.lower(root.get("email")), pattern);
                predicates.add(cb.or(nameMatch, emailMatch));
            }

            // 2. Department code: lọc theo quan hệ s.department.code
            if (filter.deptCode() != null && !filter.deptCode().isBlank()) {
                predicates.add(cb.equal(root.get("department").get("code"), filter.deptCode()));
            }

            // 3. GPA min
            if (filter.minGpa() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("gpa"), filter.minGpa()));
            }

            // 4. GPA max
            if (filter.maxGpa() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("gpa"), filter.maxGpa()));
            }

            // 5. Active status
            if (filter.active() != null) {
                predicates.add(cb.equal(root.get("active"), filter.active()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}