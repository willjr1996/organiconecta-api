package br.com.ifpe.organiconecta_api.modelo.produto;

import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
   private String produtoNome;

   @Column (nullable = false)
   private String produtoDescricao;

   @Column
   private double produtoPreco;

   @Column (nullable = false)
   private Integer produtoQuantidade;
   
   // @Column (nullable = false)
   // private String produtoImagem;
   
   @Column (nullable = false)
   private String produtoCategoria;

   @Column (nullable = false, unique = true)
   private String produtoCodigo;

   @ElementCollection
   @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_id"))
   @Column(name = "imagem_url", nullable = false)
   private List<String> produtoImagens;
  
}