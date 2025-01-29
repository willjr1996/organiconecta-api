package br.com.ifpe.organiconecta_api.modelo.itemPedido;


import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_pedido")
@SQLRestriction("habilitado = true")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido extends EntidadeAuditavel{

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double valorUnitario;

}
