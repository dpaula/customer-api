package com.lima.customerapi.repository;

import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void existsByEmailIsTrue() {

        final var cliente = Customer.builder()
                .id(1)
                .nome("Fernando de Lima")
                .email("fernando.lima@gmail.com")
                .dataInclusao(Util.getDataAtualDateTime())
                .ativo(true)
                .build();

        repository.save(cliente);

        final var clienteBase = repository.existsByEmail(cliente.getEmail());
        assertThat(clienteBase).isTrue();
    }

    @Test
    void existsByEmailIsFalse() {

        final var cliente = Customer.builder()
                .id(1)
                .nome("Fernando de Lima")
                .email("fernando.lima@gmail.com")
                .dataInclusao(Util.getDataAtualDateTime())
                .ativo(true)
                .build();

        repository.save(cliente);

        final var clienteBase = repository.existsByEmail("Fernando");
        assertThat(clienteBase).isFalse();
    }
}