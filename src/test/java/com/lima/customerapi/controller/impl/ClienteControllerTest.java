package com.lima.customerapi.controller.impl;

import com.lima.customerapi.controller.CustomerRequest;
import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.service.CustomerService;
import com.lima.customerapi.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.JsonUtilsTest;

import java.net.URI;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClienteController.class)
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private static final URI URI_CLIENTES = URI.create("/v1/clientes");
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Test
    void posCriarNovoCliente() throws Exception {

        final var cliente = getClienteId();

        when(service.create(any()))
                .thenReturn(cliente);

        final var nome = "Fernando de Lima";
        final var email = "fernando.lima@gmail.com";
        final var clienteRequest = new CustomerRequest(null, nome, email, null, null, true);

        mockMvc.perform(MockMvcRequestBuilders.post(URI_CLIENTES)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtilsTest.toJson(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value(nome))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.dataInclusao").isNotEmpty())
                .andExpect(jsonPath("$.ativo").value(true));
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