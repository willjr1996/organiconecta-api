package br.com.ifpe.organiconecta_api.modelo.produto;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FirebaseService firebaseService;

    @Transactional
    public Produto save(Produto produto, MultipartFile produtoImagem) {
        if (produtoImagem != null && !produtoImagem.isEmpty()) {
            String imageUrl = firebaseService.uploadFile(produtoImagem); // Upload da imagem
            produto.setProdutoImagem(imageUrl);
        }
        produto.setHabilitado(Boolean.TRUE);
        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        
        return repository.findAll();

    }

    public Produto obterPorId(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado, MultipartFile produtoImagem) {
        Produto produto = repository.findById(id).orElse(null);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        produto.setProdutoNome(produtoAlterado.getProdutoNome());
        produto.setProdutoDescricao(produtoAlterado.getProdutoDescricao());
        produto.setProdutoPreco(produtoAlterado.getProdutoPreco());
        produto.setProdutoCategoria(produtoAlterado.getProdutoCategoria());
        produto.setProdutoCodigo(produtoAlterado.getProdutoCodigo());

        // Se um novo arquivo foi enviado, faz upload e exclui a imagem antiga
        if (produtoImagem != null && !produtoImagem.isEmpty()) {
            if (produto.getProdutoImagem() != null) {
                firebaseService.deleteFile(produto.getProdutoImagem()); // Remove a imagem antiga
            }
            String newImageUrl = firebaseService.uploadFile(produtoImagem); // Upload da nova imagem
            produto.setProdutoImagem(newImageUrl);
        }

        repository.save(produto);
    }

    @Transactional  
    public void delete(Long id) {
        Produto produto = repository.findById(id).orElse(null);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        // Exclui a imagem do Firebase antes de desativar o produto
        if (produto.getProdutoImagem() != null) {
            firebaseService.deleteFile(produto.getProdutoImagem());
        }

        produto.setHabilitado(Boolean.FALSE);
        repository.save(produto);
    }

    public List<Produto> filtrar(String produtoCodigo, String produtoNome, String produtoCategoria) {

    List<Produto> listaProdutos = repository.findAll();

    if ((produtoCodigo != null && !"".equals(produtoCodigo)) &&
        (produtoNome == null || "".equals(produtoNome)) &&
        (produtoCategoria == null)) {
            listaProdutos = repository.findByProdutoCodigoContainingIgnoreCaseOrderByProdutoCodigoAsc(produtoCodigo);
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria == null)) {    
            listaProdutos = repository.findByProdutoNomeContainingIgnoreCaseOrderByProdutoNomeAsc(produtoNome);
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome == null || "".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.findByProdutoCategoriaContainingIgnoreCaseOrderByProdutoCategoriaAsc(produtoCategoria); 
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.consultarPorProdutoNomeECategoria(produtoNome, produtoCategoria); 
    } else if (
        (produtoCodigo != null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.consultarPorProdutoNomeECategoriaECodigo(produtoCodigo, produtoNome, produtoCategoria); 
    } 

    return listaProdutos;
    }

}