package br.com.ifpe.organiconecta_api.api.pedido;

import br.com.ifpe.organiconecta_api.api.itemPedido.ItemPedidoRequest;
import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Ele é  a junção do get e o set
@Builder
@NoArgsConstructor
@AllArgsConstructor
    

public class PedidoRequest {

    @NotNull
    private Long idCliente;

    @NotNull
    private List<ItemPedidoRequest> itens;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCompra;


}
