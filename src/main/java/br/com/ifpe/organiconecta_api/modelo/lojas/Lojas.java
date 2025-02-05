package br.com.ifpe.organiconecta_api.modelo.lojas;

import org.hibernate.annotations.SQLRestriction;


import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "lojas")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Lojas extends EntidadeAuditavel {
   
   @Column (nullable = false, length = 100)
   private String nomeLoja;

   @Column (nullable = false, unique = true)
   private String registroPropriedade;

    
   @Column (nullable = false)
   private String certificacao;

   @OneToOne 
   @JoinColumn
   private Cliente cliente;

   @Column 
   private String perfilLojaImagem;

   @Column 
   private String capaLojaImagem;
   
  
}
