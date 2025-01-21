package br.com.ifpe.organiconecta_api.modelo.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pedido")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Pedido extends EntidadeAuditavel {


    // Construíundo o relacionamente de pedidos com cliente
    @JsonIgnore
    @ManyToOne // muitos pedidos para um cliente
    private Cliente cliente;


    @Column(precision = 7, scale = 2)
    private BigDecimal valorTotal;


    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataEmissao;


 
}
