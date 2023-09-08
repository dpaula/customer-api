package com.lima.customerapi.filtro;

import com.lima.customerapi.entity.Customer;
import com.lima.customerapi.specification.ClienteSpecs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClienteFiltro {

    private String nome;
    private String email;
    private Integer idade;
    private LocalDate dataNascimento;

    public static ClienteFiltro montarFiltro(final String nome, final String email) {
        return ClienteFiltro.builder()
                .nome(nome)
                .email(email)
                .build();
    }

    public Specification<Customer> toSpec() {

        final var specNome = Optional.ofNullable(nome)
                .map(ClienteSpecs::nome)
                .orElse(Specification.where(null));

        final var specEmail = Optional.ofNullable(email)
                .map(ClienteSpecs::email)
                .orElse(Specification.where(null));


        return specNome.and(specEmail);
    }
}
