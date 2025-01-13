package br.com.ifpe.organiconecta_api.api.lojas;

import org.hibernate.validator.constraints.Length;
import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.produtor.Produtor;
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

    private Long produtorId; // ID do produtor associado à loja

    // Método para construir uma instância de Lojas
    public Lojas build(Produtor produtor) {
        return Lojas.builder()
                .nomeLoja(nomeLoja)
                .registroPropriedade(registroPropriedade)
                .certificacao(certificacao)
                .produtor(produtor) // Associa o produtor
                .build();
    }
}
