package br.com.ifpe.organiconecta_api.modelo.endereco;
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
@Table(name = "endereco")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco extends EntidadeAuditavel{
    
   @Column (nullable = false)
   private String enderecoRua;

   @Column (nullable = false)
   private String enderecoNumero;

   @Column
   private String enderecoComplemento;
    
   @Column (nullable = false)
   private String enderecoBairro;
   
   @Column (nullable = false)
   private String enderecoCidade;

   @Column (nullable = false)
   private String enderecoEstado;

   @Column (nullable = false)
   private String enderecoCep;
}
