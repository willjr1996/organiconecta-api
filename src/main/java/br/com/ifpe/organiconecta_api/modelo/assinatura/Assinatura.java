package br.com.ifpe.organiconecta_api.modelo.assinatura;


import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "assinatura")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assinatura extends EntidadeAuditavel {


   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column (nullable = false)
   private LocalDate dataInicio;
   
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column (nullable = false)
   private LocalDate validade;


   @Column (nullable = false)
   private Boolean statusAssinatura;

    //@Column(nullable = false)
   // private String tipoPlano;
 
   //@Column(precision = 7, scale = 2)
   //private BigDecimal planoPreco;

   @OneToOne
   private Cliente cliente;





     

   


   
}
