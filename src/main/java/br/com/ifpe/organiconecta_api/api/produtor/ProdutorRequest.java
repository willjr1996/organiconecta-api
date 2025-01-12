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
    
    private Long clienteId; // ID do cliente associado ao produtor

    // Método que constrói um Produtor a partir do request
    public Produtor build() {
        Produtor produtor = new Produtor();

        return produtor;
    }
}