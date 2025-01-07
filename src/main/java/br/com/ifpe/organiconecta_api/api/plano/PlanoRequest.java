package br.com.ifpe.organiconecta_api.api.plano;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.ifpe.organiconecta_api.modelo.plano.Plano;
import jakarta.validation.constraints.Future;
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
public class PlanoRequest {
   
    @NotBlank(message = "A escolha do tipo de assinatura é obrigatória.")
    private String tipoPlano;
    
    @NotNull(message = "O preço do plano é obrigatório.")
    private BigDecimal planoPreco;
    
     @Future(message = "A data de validade deve ser uma data futura.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate validade;

    public Plano build() {

        return Plano.builder()
                .tipoPlano(tipoPlano)
                .planoPreco(planoPreco)
                .validade(validade)
                .build();
    }
}