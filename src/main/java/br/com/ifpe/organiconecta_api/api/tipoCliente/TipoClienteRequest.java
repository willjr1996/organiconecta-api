package br.com.ifpe.organiconecta_api.api.tipoCliente;


import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoClienteRequest {


    //private Long clienteId;
    
    private String tipo;  
   
    public TipoCliente build() {


        return TipoCliente.builder()
        .tipo(tipo)
        .build();
       
    }
}
