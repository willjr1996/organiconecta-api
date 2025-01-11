package br.com.ifpe.organiconecta_api.api.cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.EnderecoCliente;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoClienteRequest {
   
   @NotBlank(message = "A rua é de preenchimento obrigatório")
   private String rua;

   @NotBlank(message = "o número da residência é de preenchimento obrigatório")
   private String numero;

   @NotBlank(message = "O bairro é de preenchimento obrigatório")
   private String bairro;

   @NotBlank(message = "O CEP da residência é de preenchimento obrigatório")
   private String cep;

   @NotBlank(message = "A cidade é de preenchimento obrigatório")
   private String cidade;

   @NotBlank(message = "O estado da residência é de preenchimento obrigatório")
   private String estado;

   private String complemento;

   public EnderecoCliente build() {

       return EnderecoCliente.builder()
               .rua(rua)
               .numero(numero)
               .bairro(bairro)
               .cep(cep)
               .cidade(cidade)
               .estado(estado)
               .complemento(complemento)
               .build();
   }

}
