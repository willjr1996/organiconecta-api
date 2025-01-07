package br.com.ifpe.organiconecta_api.api.endereco;
import br.com.ifpe.organiconecta_api.modelo.endereco.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {

    @NotBlank(message = "O nome do logradouro é obrigatório.")
    private String enderecoRua;
    
    @NotBlank(message = "O número é obrigatório, ou digite S/N. ")
    private String enderecoNumero;

    private String enderecoComplemento;

    @NotBlank(message = "O bairro é obrigatório.")
    private String enderecoBairro;
    
    @NotBlank(message = "A cidade é obrigatório.")
    private String enderecoCidade;
    
    @NotBlank(message = "O estado é obrigatório.")
    private String enderecoEstado;

    @NotBlank(message = "O CEP é obrigatório.")
    private String enderecoCep;

    public Endereco build() {

        return Endereco.builder()
                .enderecoRua(enderecoRua)
                .enderecoNumero(enderecoNumero)
                .enderecoComplemento(enderecoComplemento)
                .enderecoBairro(enderecoBairro)
                .enderecoCidade(enderecoCidade)
                .enderecoEstado(enderecoEstado)
                .enderecoCep(enderecoCep)
                
                .build();
    }
}