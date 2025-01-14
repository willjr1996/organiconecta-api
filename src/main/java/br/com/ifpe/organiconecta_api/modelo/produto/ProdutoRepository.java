package br.com.ifpe.organiconecta_api.modelo.produto;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

     //Exemplo de uma busca exata
   /*@Query(value = "SELECT p FROM Produto p WHERE p.produtoCodigo = :produtoCodigo")
   List<Produto> consultarPorProdutoCodigo(String produtoCodigo);*/

   List<Produto> findByProdutoCodigoContainingIgnoreCaseOrderByProdutoCodigoAsc(String produtoCodigo);

   //Exemplo de uma busca aproximada com ordenação:
   // @Query(value = "SELECT p FROM Produto p WHERE p.titulo ilike %:titulo% ORDER BY p.titulo")
   // List<Produto> consultarPorTitulo(String titulo);
   List<Produto> findByProdutoNomeContainingIgnoreCaseOrderByProdutoNomeAsc(String produtoNome);

   List<Produto> findByProdutoCategoriaContainingIgnoreCaseOrderByProdutoCategoriaAsc(String produtoCategoria);


   //Exemplo de uma busca com mais de um atributo
   @Query(value = "SELECT p FROM Produto p WHERE p.produtoNome ilike %:produtoNome% AND p.produtoCategoria = :produtoCategoria")
   List<Produto> consultarPorProdutoNomeECategoria(String produtoNome, String produtoCategoria);

   @Query(value = "SELECT p FROM Produto p WHERE  p.produtoCodigo = :produtoCodigo AND p.produtoNome ilike %:produtoNome% AND p.produtoCategoria  = :produtoCategoria")
   List<Produto> consultarPorProdutoNomeECategoriaECodigo(String produtoCodigo, String produtoNome, String produtoCategoria);

  
}