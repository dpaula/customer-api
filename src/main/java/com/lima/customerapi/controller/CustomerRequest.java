package com.lima.customerapi.controller;

import com.lima.customerapi.entity.Customer;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record CustomerRequest(Integer id,
                              @NotEmpty String nome,
                              @NotEmpty String email,
                              LocalDateTime dataInclusao,
                              LocalDateTime dataAlteracao,
                              boolean ativo) {

    public static CustomerRequest parse(final Customer customer) {

        return new CustomerRequest(customer.getId(),
                customer.getNome(),
                customer.getEmail(),
                customer.getDataInclusao(),
                customer.getDataAlteracao(),
                customer.isAtivo());
    }
}
