package br.com.ifpe.organiconecta_api.modelo.produto;

import java.math.BigDecimal;
import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "produto")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends EntidadeAuditavel {
    
   @Column (nullable = false, length = 100)
   private String ProdutoNome;

   @Column (nullable = false)
   private String produtoDescricao;

   @Column (precision = 7, scale = 2)
   private BigDecimal produtoPreco;
   
   @Column (nullable = false)
   private String produtoImagem;
   
   @Column (nullable = false)
   private String produtoCategoria;

   
}
