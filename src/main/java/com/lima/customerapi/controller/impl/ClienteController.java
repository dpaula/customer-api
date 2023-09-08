package com.lima.customerapi.controller.impl;

import com.lima.customerapi.controller.CustomerRequest;
import com.lima.customerapi.controller.IClienteController;
import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController implements IClienteController {

    private final CustomerService service;

    @Override
    public ResponseEntity<Page<CustomerRequest>> listar(final String nome, final String email, final Pageable pageable) {
        final Page<CustomerRequest> clientesDTO = service
                .findAllByFilters(nome, email, pageable)
                .map(CustomerRequest::parse);

        return ResponseEntity.ok(clientesDTO);
    }

    @Override
    public ResponseEntity<CustomerRequest> post(final CustomerRequest clienteInput, final UriComponentsBuilder builder) {

        final var cliente = Customer.parse(clienteInput);
        final var clienteCriado = CustomerRequest.parse(service.create(cliente));

        final var location = builder.replacePath("/v1/clientes/{id}")
                .buildAndExpand(clienteCriado.id()
                        .toString())
                .toUri();
        return ResponseEntity.created(location)
                .body(clienteCriado);
    }

    @Override
    public ResponseEntity<CustomerRequest> put(final CustomerRequest customerRequest) {
        final var cliente = Customer.parse(customerRequest);
        return ResponseEntity.ok(CustomerRequest.parse(service.alterar(cliente)));
    }

    @Override
    public ResponseEntity<CustomerRequest> get(final Integer id) {
        return ResponseEntity.ok(CustomerRequest.parse(service.buscar(id)));
    }

    @Override
    public void delete(final Integer id) {
        service.delete(id);
    }
}
