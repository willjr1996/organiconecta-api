package br.com.ifpe.organiconecta_api.api.pagamento;

import br.com.ifpe.organiconecta_api.modelo.pagamento.Pagamento;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {
   
    @NotBlank(message = "A escolha do tipo de pagamento é obrigatória.")
    private String tipoPagamento;
    
    @NotBlank(message = "A escolha do tipo de modalidade é obrigatória.")
    private String modalidade;
    
    @NotBlank(message = "A escolha do cartão é obrigatória.")
    private String cartao;

    public Pagamento build() {

        return Pagamento.builder()
                .tipoPagamento(tipoPagamento)
                .modalidade(modalidade)
                .cartao(cartao)
                .build();
    }
}
