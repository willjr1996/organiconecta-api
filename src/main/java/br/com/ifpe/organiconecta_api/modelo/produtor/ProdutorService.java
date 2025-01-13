package br.com.ifpe.organiconecta_api.modelo.produtor;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ProdutorService {

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LojasRepository lojasRepository;

    // Criar Produtor com associação a uma Loja
    @Transactional
    public Produtor save(Produtor produtor) {

        // Associa o cliente ao produtor
        if (produtor.getCliente() != null) {
            produtor.setCliente(clienteRepository.findById(produtor.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        }

        // Associa a loja ao produtor
        if (produtor.getLoja() != null && produtor.getLoja().getId() != null) {
            Lojas loja = lojasRepository.findById(produtor.getLoja().getId())
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
            produtor.setLoja(loja);
        } else {
            throw new RuntimeException("Loja não associada ao produtor");
        } 

        produtor.setHabilitado(Boolean.TRUE);
        return produtorRepository.save(produtor);
    }

    // Listar todos os produtores
    public List<Produtor> listarTodos() {
        return produtorRepository.findAll();
    }

    // Obter Produtor por ID
    public Produtor obterPorID(Long id) {
        return produtorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));
    }

    // Atualizar Produtor e a Loja associada
    @Transactional
public void update(Long id, Produtor produtorAlterado) {
    // Encontrar o produtor existente no banco de dados
    Produtor produtorExistente = produtorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));

    // Atualizar Cliente associado
    Cliente clienteExistente = produtorExistente.getCliente();
    if (clienteExistente == null) {
        throw new RuntimeException("Cliente associado ao produtor não encontrado");
    }
    // Atualiza os dados do cliente conforme o pedido
    clienteExistente.setNome(produtorAlterado.getCliente().getNome());
    clienteExistente.setTelefone(produtorAlterado.getCliente().getTelefone());
    clienteExistente.setCpf(produtorAlterado.getCliente().getCpf());
    clienteExistente.setDataNascimento(produtorAlterado.getCliente().getDataNascimento());
    clienteRepository.save(clienteExistente);

    // Atualizar Loja associada se a Loja estiver presente no objeto produtorAlterado
    if (produtorAlterado.getLoja() != null) {
        Lojas loja = produtorAlterado.getLoja();
        if (loja.getId() != null) {
            // Verifica se a Loja associada ao produtorAlterado existe
            loja = lojasRepository.findById(loja.getId())
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
            produtorExistente.setLoja(loja);
        } else {
            throw new RuntimeException("Loja não associada ao produtor");
        }
    }

    // Salva as alterações no produtor
    produtorRepository.save(produtorExistente);
}


    // Deletar Produtor
    @Transactional
    public void delete(Long id) {
        Produtor produtor = produtorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));

        produtorRepository.delete(produtor);
    }
}
