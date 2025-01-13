package br.com.ifpe.organiconecta_api.api.produtor;

import br.com.ifpe.organiconecta_api.modelo.produtor.Produtor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutorRequest {

    private Long clienteId; 
    private Long idLoja;   

    
    public Produtor build() {

        return Produtor.builder()
        
        .build();
        
    }
}
