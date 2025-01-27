package br.com.ifpe.organiconecta_api.api.itemPedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequest {
    
    @NotNull
    private Long idProduto;

    @NotNull
    @Positive
    private Integer quantidade;

    @Positive
    private double valorUnitario;
}