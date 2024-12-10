package br.com.ifpe.organiconecta_api.api.usuario;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.ifpe.organiconecta_api.modelo.usuario.Usuario;
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
public class UsuarioRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Length(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @NotBlank(message = "Um contato telefônico é obrigatório.")
    @Length(max = 11, message = "O campo telefone tem que ter no máximo {max} caracteres")
    private String telefone;
    
    @NotBlank(message = "O CPF é obrigatório.")
    @CPF
    private String cpf;
    
    @Past(message = "A data de nascimento deve ser uma data passada.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public Usuario build() {

        return Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .telefone(telefone)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .build();
    }
}