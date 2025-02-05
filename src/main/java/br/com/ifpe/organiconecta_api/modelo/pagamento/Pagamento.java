package br.com.ifpe.organiconecta_api.modelo.pagamento;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagamento")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento extends EntidadeAuditavel {

    @OneToOne
    private Pedido pedido;

//     @Column (nullable = false)
//    private String tipoPagamento;

//    @Column (nullable = false)
//    private String modalidade;
   
//    @Column (nullable = false)
//    private String cartao;

@Column (nullable = false)
private Boolean pagamentoFeito;
   
    
}