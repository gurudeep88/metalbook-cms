package com.metalbook.cms.specification;

import com.metalbook.cms.model.Contact;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecification {
    public static Specification<Contact> searchContacts(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (query == null || query.isEmpty()) {
                return criteriaBuilder.conjunction(); // Return all if no query
            }

            String likePattern = "%" + query.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern)
            );
        };
    }
}