package br.com.ifpe.organiconecta_api.modelo.assinatura;

import java.time.LocalDate;
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
@Table(name = "assinatura")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assinatura extends EntidadeAuditavel {

   @Column (nullable = false)
   private LocalDate dataInicio;
   
   @Column (nullable = false)
   private LocalDate dataFinal;

   @Column (nullable = false)
   private String status;
    
}


