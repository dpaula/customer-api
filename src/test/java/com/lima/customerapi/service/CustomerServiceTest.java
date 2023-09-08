package com.lima.customerapi.service;

import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.repository.CustomerRepository;
import com.lima.customerapi.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Test
    void createClienteSucesso() {

        final var cliente = Customer.builder()
                .nome("Fernando de Lima")
                .email("fernando.lima@gmail.com")
                .dataInclusao(Util.getDataAtualDateTime())
                .ativo(true)
                .build();

        service.create(cliente);

        verify(repository, times(1)).saveAndFlush(cliente);
    }

    @Test
    void findAllByFilters() {

        final String nome = "Fernando de Lima";
        final String email = "fernando.lima@gmail.com";

        service.findAllByFilters(nome, email, null);

        final ArgumentCaptor<Specification<Customer>> filterCaptor = ArgumentCaptor.forClass(Specification.class);
        final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(repository, times(1)).findAll(filterCaptor.capture(), pageableCaptor.capture());
    }

    @Test
    void alterar() {

        final var cliente = getClienteId();

        when(repository.findById(anyInt()))
                .thenReturn(java.util.Optional.of(cliente));

        service.alterar(cliente);

        verify(repository, times(1)).save(cliente);
    }

    @Test
    void alterarClienteEmailDiferente() {

        final var cliente = getClienteId();

        final var clienteSet = Customer.builder()
                .id(1)
                .nome("Fernando de Lima")
                .email("fernando.lima.novo@gmail.com")
                .build();

        when(repository.findById(anyInt()))
                .thenReturn(java.util.Optional.of(cliente));

        service.alterar(clienteSet);

        verify(repository, times(1)).save(cliente);
    }

    @Test
    void buscar() {
        final var cliente = getClienteId();

        when(repository.findById(anyInt()))
                .thenReturn(java.util.Optional.of(cliente));

        service.buscar(cliente.getId());

        verify(repository, times(1)).findById(cliente.getId());
    }

    @Test
    void delete() {

        final var cliente = getClienteId();

        when(repository.findById(anyInt()))
                .thenReturn(java.util.Optional.of(cliente));

        service.delete(cliente.getId());

        verify(repository, times(1)).delete(cliente);
    }

    private static Customer getClienteId() {
        return Customer.builder()
                .id(1)
                .nome("Fernando de Lima")
                .email("fernando.lima@gmail.com")
                .dataInclusao(Util.getDataAtualDateTime())
                .ativo(true)
                .build();
    }
}