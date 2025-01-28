package br.com.ifpe.organiconecta_api.api.assinatura;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
import jakarta.validation.constraints.Future;
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
    private Boolean statusAssinatura;


    // @NotNull(message = "A escolha do tipo do plano é obrigatória.")
    // private TipoPlanoEnum tipoPlano;

    // @NotBlank(message = "A escolha do preço do plano é obrigatória.")
    // private BigDecimal planoPreco;

    public Assinatura build() {

        return Assinatura.builder()
                .dataInicio(dataInicio)
                .validade(validade)
                .statusAssinatura(statusAssinatura)
                // .tipoPlano(tipoPlano)
                // .planoPreco(planoPreco)
                .build();
    }


}