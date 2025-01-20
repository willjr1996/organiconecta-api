package br.com.ifpe.organiconecta_api.modelo.cliente;


import java.time.LocalDate;
import java.util.List;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Table(name = "cliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel {


   @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @JsonManagedReference
   private TipoCliente tipoCliente;

   @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) 
   @JsonManagedReference
   private Assinatura assinatura;


   //Muitos endereços para um cliente
   @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
   @Fetch(FetchMode.SUBSELECT)
   private List<EnderecoCliente> enderecos;


   //Muitos pedidos para um cliente
   @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
   @Fetch(FetchMode.SUBSELECT)
   private List<Pedido> pedidos;


   @OneToOne
   @JoinColumn(nullable = false)
   private Usuario usuario;


   @Column(nullable = false, length = 100)
   private String nome;


   @Column(nullable = false)
   private String telefone;


   @Column(unique = true, nullable = false)
   private String cpf;


   @Column(nullable = false)
   private LocalDate dataNascimento;


}
