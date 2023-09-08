package com.lima.customerapi.service;

import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.error.ConflictException;
import com.lima.customerapi.error.ObjectNotFoundException;
import com.lima.customerapi.repository.CustomerRepository;
import com.lima.customerapi.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.lima.customerapi.filtro.ClienteFiltro.montarFiltro;

/**
 * @author Fernando de Lima on 08/09/23
 */
@Slf4j
@Service
public record CustomerService(CustomerRepository repository) {

    public Customer create(final Customer cliente) {

        log.info("Criando novo cliente: {}", cliente.getEmail());

        validarEmailConflito(cliente.getEmail());

        cliente.setDataInclusao(Util.getDataAtualDateTime());
        cliente.setAtivo(true);

        return repository.saveAndFlush(cliente);
    }

    public Page<Customer> findAllByFilters(final String nome, final String email, final Pageable pageable) {

        final var filtro = montarFiltro(nome, email);

        log.info("Buscando clientes para o filtro {}", filtro);

        final var clienteSpec = filtro.toSpec();

        return repository.findAll(clienteSpec, pageable);
    }

    public Customer alterar(final Customer cliente) {
        log.info("Alterando cliente id: {}", cliente.getId());

        final var clienteBase = findClienteByIdOrThrow(cliente.getId());

        if (!cliente.getEmail().equalsIgnoreCase(clienteBase.getEmail())) {
            validarEmailConflito(cliente.getEmail());
            clienteBase.setEmail(cliente.getEmail());
        }

        clienteBase.setNome(cliente.getNome());
        clienteBase.setAtivo(cliente.isAtivo());
        clienteBase.setDataAlteracao(Util.getDataAtualDateTime());

        return repository.save(clienteBase);
    }

    private Customer findClienteByIdOrThrow(final Integer id) {
        return repository.findById(id)
                .orElseThrow(ObjectNotFoundException.with(Customer.class, id, "id"));
    }

    private void validarEmailConflito(final String email) {
        if (repository.existsByEmail(email)) {
            throw new ConflictException("Já existe Cliente cadastrado para email: " + email);
        }
    }

    public Customer buscar(final Integer id) {
        log.info("Buscando cliente id: {}", id);
        return findClienteByIdOrThrow(id);
    }

    public void delete(final Integer id) {
        log.info("Removendo cliente id: {}", id);

        final var cliente = findClienteByIdOrThrow(id);
        repository.delete(cliente);
    }
}
