package br.com.ifpe.organiconecta_api.modelo.produtor;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
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

    // Criar Produtor
    @Transactional
    public Produtor save(Produtor produtor) {
        
        // Associa o cliente ao produtor
        produtor.setCliente(clienteRepository.findById(produtor.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

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

    // Atualizar Produtor
    public void update(Long id, Produtor produtorAlterado) {
        // Busca o produtor
        Produtor produtorExistente = produtorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtor não encontrado"));
        
        // Verifica se o cliente já está associado ao produtor
        Cliente clienteExistente = produtorExistente.getCliente();
        
        // Se o cliente não existir, lança erro
        if (clienteExistente == null) {
            throw new RuntimeException("Cliente associado ao produtor não encontrado");
        }

        // Atualiza os dados do cliente associado ao produtor
        clienteExistente.setNome(produtorAlterado.getCliente().getNome());
        clienteExistente.setTelefone(produtorAlterado.getCliente().getTelefone());
        clienteExistente.setCpf(produtorAlterado.getCliente().getCpf());
        clienteExistente.setDataNascimento(produtorAlterado.getCliente().getDataNascimento());

        // Salva as alterações no cliente
        clienteRepository.save(clienteExistente);

        // Salva o produtor com as alterações
        produtorExistente.setCliente(clienteExistente); // Garante que o cliente associado ao produtor seja atualizado
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