package br.com.ifpe.organiconecta_api.api.tipoCliente;


import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente.TipoClienteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoClienteRequest {


    private Long clienteId;
    private TipoClienteEnum tipoUsuario;  


   
    public TipoCliente build() {


        return TipoCliente.builder()
        .tipoUsuario(tipoUsuario)
        .build();
       
    }
}
