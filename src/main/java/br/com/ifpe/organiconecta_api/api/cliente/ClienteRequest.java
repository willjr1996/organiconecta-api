package br.com.ifpe.organiconecta_api.api.cliente;
import java.time.LocalDate;
import java.util.Arrays;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    // @NotBlank(message = "O email é obrigatório.")
    // @Email(message = "O email deve ser válido.")
    // private String email;

    // @NotBlank(message = "A senha é obrigatória.")
    // private String senha;

    @NotBlank(message = "Um contato telefônico é obrigatório.")
    @Length(max = 15, message = "O campo telefone tem que ter no máximo {max} caracteres")
    private String telefone;
    
    @NotBlank(message = "O CPF é obrigatório.")
    @CPF
    private String cpf;
    
    @Past(message = "A data de nascimento deve ser uma data passada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    public Usuario buildUsuario() {
       return Usuario.builder()
           .username(email)
           .password(password)
           .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)))
           .build();
   }
   
   public Cliente build() {
    return Cliente.builder()
        .usuario(buildUsuario())
        .nome(nome)
        .telefone(telefone)
        .cpf(cpf)
        .dataNascimento(dataNascimento)
        .build();
}
}