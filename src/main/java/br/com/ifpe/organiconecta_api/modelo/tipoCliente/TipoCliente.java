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


    

    @Column(nullable = false)
    private String tipo;

    public static final String TIPO_CLIENTE = "Cliente";
    public static final String TIPO_CLIENTE_PRODUTOR = "Produtor";
   

}
