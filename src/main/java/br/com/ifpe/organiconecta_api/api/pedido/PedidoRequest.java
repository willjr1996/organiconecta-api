package br.com.ifpe.organiconecta_api.api.pedido;

import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private BigDecimal valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEmissao;

    public Pedido build() {

       return Pedido.builder()
           .valorTotal(valorTotal)
           .dataEmissao(dataEmissao)
         
           .build();
   }

}
