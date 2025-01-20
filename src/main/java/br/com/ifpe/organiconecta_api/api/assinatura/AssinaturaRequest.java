package br.com.ifpe.organiconecta_api.api.assinatura;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura.TipoPlanoEnum;
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
public class AssinaturaRequest {

    private Long clienteId;
    
    @NotNull(message = "A escolha da data inicial é obrigatória.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
     @Future(message = "A data final deve ser uma data futura.")
     @NotNull(message = "A escolha da data final é obrigatória.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate validade;

    @NotNull(message = "A escolha do status da assinatura é obrigatória.")
    private Boolean status;

    @NotNull(message = "A escolha do tipo do plano é obrigatória.")
    private TipoPlanoEnum tipoPlano;

    @NotBlank(message = "A escolha do preço do plano é obrigatória.")
    private BigDecimal planoPreco;

    public Assinatura build() {

        return Assinatura.builder()
                .dataInicio(dataInicio)
                .validade(validade)
                .status(status)
                .tipoPlano(tipoPlano)
                .planoPreco(planoPreco)
                .build();
    }
}