package br.com.ifpe.organiconecta_api.modelo.usuario;
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
@Table(name = "usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadeAuditavel  {

   @Column (nullable = false, length = 100)
   private String nome;

   @Column (nullable = false, unique = true)
   private String email;
   
   @Column (nullable = false)
   private String telefone;
   
   @Column (unique = true, nullable = false)
   private String cpf;

   @Column (nullable = false)
   private LocalDate dataNascimento;

}