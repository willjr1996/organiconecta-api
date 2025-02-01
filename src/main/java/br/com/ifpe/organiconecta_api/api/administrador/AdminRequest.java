package br.com.ifpe.organiconecta_api.api.administrador;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.modelo.administrador.Admin;
import br.com.ifpe.organiconecta_api.modelo.administrador.TipoFuncionario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoFuncionario tipo;

    @NotBlank
    private String nome;

    private String cpf;

    private String rg;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String foneCelular;

    private String foneFixo;

    private Double salario;

    private String enderecoRua;

    private String enderecoNumero;

    private String enderecoBairro;

    private String enderecoCidade;

    private String enderecoCep;

    private String enderecoUf;

    private String enderecoComplemento;

    public Admin build() {

        return Admin.builder()  
                .usuario(buildUsuario())
                .tipo(tipo)
                .nome(nome)
                .cpf(cpf)
                .rg(rg)
                .dataNascimento(dataNascimento)
                .foneCelular(foneCelular)
                .foneFixo(foneFixo)
                .salario(salario)
                .enderecoRua(enderecoRua)
                .enderecoNumero(enderecoNumero)
                .enderecoBairro(enderecoBairro)
                .enderecoCidade(enderecoCidade)
                .enderecoCep(enderecoCep)
                .enderecoUf(enderecoUf)
                .enderecoComplemento(enderecoComplemento)
                .build();
    }

    public Usuario buildUsuario() {

        return Usuario.builder()
                .username(email)
                .password(password)
                .build();
    }


}
