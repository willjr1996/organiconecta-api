package br.com.ifpe.organiconecta_api.modelo.cliente;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "enderecoCliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCliente extends EntidadeAuditavel {
   
   //relação muitos endereços para um cliente
   @JsonIgnore
   @ManyToOne
   private Cliente cliente;

   @Column (nullable = false)
   private String rua;

   @Column (nullable = false)
   private String numero;

   @Column (nullable = false)
   private String bairro;

   @Column (nullable = false)
   private String cep;

   @Column (nullable = false)
   private String cidade;

   @Column (nullable = false)
   private String estado;

   @Column
   private String complemento;

}