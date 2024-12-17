package br.com.ifpe.organiconecta_api.modelo.endereco;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository repository;

    @Transactional
    public Endereco save(Endereco endereco) {
        
        endereco.setHabilitado(Boolean.TRUE);
        return repository.save(endereco);

    }

    public List<Endereco> listarTodos() {
        
        return repository.findAll();

    }

    public Endereco obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Endereco enderecoAlterado) {
        
        Endereco endereco = repository.findById(id).get();
        endereco.setEnderecoRua(enderecoAlterado.getEnderecoRua());
        endereco.setEnderecoNumero(enderecoAlterado.getEnderecoNumero());
        endereco.setEnderecoComplemento(enderecoAlterado.getEnderecoComplemento());
        endereco.setEnderecoBairro(enderecoAlterado.getEnderecoBairro());
        endereco.setEnderecoCidade(enderecoAlterado.getEnderecoCidade());
        endereco.setEnderecoEstado(enderecoAlterado.getEnderecoEstado());
        endereco.setEnderecoCep(enderecoAlterado.getEnderecoCep());

        repository.save(endereco);
    }

    @Transactional  
    public void delete(Long id) {
        Endereco endereco = repository.findById(id).get();
        endereco.setHabilitado(Boolean.FALSE);
        repository.save(endereco);

   }
}
