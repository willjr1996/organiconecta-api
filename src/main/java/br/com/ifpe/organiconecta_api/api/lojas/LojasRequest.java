package br.com.ifpe.organiconecta_api.api.lojas;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LojasRequest {

    @NotBlank(message = "O nome da loja é obrigatório.")
    @Length(max = 100, message = "O nome da loja deve ter no máximo 100 caracteres.")
    private String nomeLoja;

    @NotBlank(message = "O registro de propriedade é obrigatório.")
    private String registroPropriedade;

    @NotBlank(message = "A certificação é obrigatória.")
    private String certificacao;

    private Long clienteId;

    // Método para construir uma instância de Lojas
    public Lojas build(Cliente cliente) {
        return Lojas.builder()
                .nomeLoja(nomeLoja)
                .registroPropriedade(registroPropriedade)
                .certificacao(certificacao)
                .cliente(cliente) 
                .build();
    }
}
