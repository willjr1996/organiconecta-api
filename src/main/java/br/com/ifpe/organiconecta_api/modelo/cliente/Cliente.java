package br.com.ifpe.organiconecta_api.modelo.cliente;
import java.time.LocalDate;
// import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.SQLRestriction;
// import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel  {

   @OneToOne
   @JoinColumn(nullable = false)
   private Usuario usuario;

   @Column (nullable = false, length = 100)
   private String nome;

   // @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
   // @Fetch(FetchMode.SUBSELECT)
   // private List<EnderecoCliente> enderecos;


   // @Column (nullable = false, unique = true)
   // private String email;

   // @JsonIgnore
   // @Column (nullable = false)
   // private String senha;
   
   @Column (nullable = false)
   private String telefone;
   
   @Column (unique = true, nullable = false)
   private String cpf;

   @Column (nullable = false)
   private LocalDate dataNascimento;

}