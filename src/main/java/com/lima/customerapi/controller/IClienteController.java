package com.lima.customerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/v1/clientes")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Serviços para gerenciamento de Clientes")
public interface IClienteController {

    @Operation(summary = "Buscar Cliente(s)", description = "Get para buscar um ou vários Clientes")
    @GetMapping()
    ResponseEntity<Page<CustomerRequest>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "email", required = false) String email,
            @PageableDefault(sort = "dataInclusao", direction = Sort.Direction.DESC, size = 20) @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Incluir Cliente", description = "Post para incluir um novo cliente")
    @Transactional
    @PostMapping
    ResponseEntity<CustomerRequest> post(@NotNull @Valid @RequestBody CustomerRequest customerRequest,
                                         UriComponentsBuilder builder);

    @Operation(summary = "Alterar Cliente", description = "Put para alterar um cliente")
    @Transactional
    @PutMapping
    ResponseEntity<CustomerRequest> put(@NotNull @Valid @RequestBody CustomerRequest customerRequest);

    @Operation(summary = "Buscar Cliente", description = "Get para buscar um Cliente pelo Id")
    @GetMapping(value = "/{id}")
    ResponseEntity<CustomerRequest> get(@PathVariable Integer id);

    @Operation(summary = "Remover Cliente", description = "Delete para remover um Cliente pelo Id")
    @DeleteMapping(value = "/{id}")
    @Transactional
    void delete(@PathVariable Integer id);
}
