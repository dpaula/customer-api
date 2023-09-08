package com.lima.customerapi.specification;

import com.lima.customerapi.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecs {

    private static final String PERCENT = "%";

    public static Specification<Customer> nome(final String nome) {
        return (root, query, builder) -> builder
                .like(builder.upper(root.get("nome")), likeAll(nome.trim().toUpperCase()));
    }

    public static Specification<Customer> email(final String email) {
        return (root, query, builder) -> builder
                .like(builder.upper(root.get("email")), likeAll(email.trim().toUpperCase()));
    }

    private static String likeAll(final String field) {
        return PERCENT + field + PERCENT;
    }
}
