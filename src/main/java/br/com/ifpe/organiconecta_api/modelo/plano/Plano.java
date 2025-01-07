package br.com.ifpe.organiconecta_api.modelo.plano;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "plano")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plano extends EntidadeAuditavel {

    @Column (nullable = false)
   private String tipoPlano;

   @Column(precision = 7, scale = 2)
   private BigDecimal planoPreco;
   
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column (nullable = false)
   private LocalDate validade;
   
    
}
