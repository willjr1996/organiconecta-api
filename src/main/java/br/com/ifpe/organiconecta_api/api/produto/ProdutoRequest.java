package br.com.ifpe.organiconecta_api.api.produto;

import br.com.ifpe.organiconecta_api.modelo.produto.Produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    //@NotNull(message = "A escolha do nome do produto é obrigatória.")
    private String produtoNome;
    
    private String produtoDescricao;

    @NotNull(message = "A inclusão do preço é obrigatória.")
    private double produtoPreco;

    @NotBlank(message = "A inclusão da imagem é obrigatória.")
    private String produtoImagem;

    @NotBlank(message = "A inclusão da categoria é obrigatória.")
    private String produtoCategoria;

    @NotBlank(message = "A inclusão do código é obrigatória.")
    private String produtoCodigo;

    public Produto build() {

        return Produto.builder()
                .produtoNome(produtoNome)
                .produtoDescricao(produtoDescricao)
                .produtoPreco(produtoPreco)
                .produtoImagem(produtoImagem)
                .produtoCategoria(produtoCategoria)
                .produtoCodigo(produtoCodigo)
                .build();
    }
}