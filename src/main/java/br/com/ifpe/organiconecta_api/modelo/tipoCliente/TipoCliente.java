package br.com.ifpe.organiconecta_api.modelo.tipoCliente;


import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tipoCliente")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoCliente extends EntidadeAuditavel {


    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private Cliente cliente;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoClienteEnum tipoUsuario;
   
    public enum TipoClienteEnum {
        CLIENTE,
        CLIENTEPRODUTOR
    }
}
