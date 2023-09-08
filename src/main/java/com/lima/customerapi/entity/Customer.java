package com.lima.customerapi.entity;

import com.lima.customerapi.controller.CustomerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Fernando de Lima on 08/09/23
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cli_clientes")
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(name = "data_inclusao", nullable = false)
    private LocalDateTime dataInclusao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    private boolean ativo;

    public static Customer parse(final CustomerRequest clienteInput) {

        return Customer.builder()
                .id(clienteInput.id())
                .nome(clienteInput.nome())
                .email(clienteInput.email())
                .ativo(clienteInput.ativo())
                .build();
    }
}
