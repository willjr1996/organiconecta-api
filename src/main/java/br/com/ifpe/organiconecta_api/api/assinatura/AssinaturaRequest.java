package br.com.ifpe.organiconecta_api.api.assinatura;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
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
    
    @NotNull(message = "A escolha da data inicial é obrigatória.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
     @Future(message = "A data final deve ser uma data futura.")
     @NotNull(message = "A escolha da data final é obrigatória.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;

    @NotBlank(message = "A escolha do status da assinatura é obrigatória.")
    private String status;

    public Assinatura build() {

        return Assinatura.builder()
                .dataInicio(dataInicio)
                .dataFinal(dataFinal)
                .status(status)
                .build();
    }
}