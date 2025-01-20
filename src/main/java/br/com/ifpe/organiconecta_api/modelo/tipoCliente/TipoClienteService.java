package br.com.ifpe.organiconecta_api.modelo.tipoCliente;


import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.util.List;


@Service
public class TipoClienteService {


    @Autowired
    private TipoClienteRepository tipoClienteRepository;


    @Autowired
    private ClienteRepository clienteRepository;


    @Transactional
    public TipoCliente save(TipoCliente tipoCliente) {

        if (tipoCliente.getCliente() == null) {
            throw new RuntimeException("O TipoCliente deve ter um Cliente associado.");
        }
    
        Cliente cliente = tipoCliente.getCliente();
        cliente.setTipoCliente(tipoCliente); // Configura o relacionamento bidirecional
        tipoCliente.setHabilitado(Boolean.TRUE);
        return tipoClienteRepository.save(tipoCliente);
    }


    public List<TipoCliente> listarTodos() {
        return tipoClienteRepository.findAll();
    }


    public TipoCliente obterPorID(Long id) {
        return tipoClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo do Cliente não identificado"));
    }


    @Transactional
public void update(Long id, TipoCliente tipoClienteAlterado) {
    // Encontrar o tipoCliente existente no banco de dados
    TipoCliente tipoClienteExistente = tipoClienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo do Cliente não identificado"));


    // Atualizar Cliente associado
    Cliente clienteExistente = tipoClienteExistente.getCliente();
    if (clienteExistente == null) {
        throw new RuntimeException("Cliente associado ao tipoCliente não identificado");
    }


    clienteExistente.setNome(tipoClienteAlterado.getCliente().getNome());
    clienteExistente.setTelefone(tipoClienteAlterado.getCliente().getTelefone());
    clienteExistente.setCpf(tipoClienteAlterado.getCliente().getCpf());
    clienteExistente.setDataNascimento(tipoClienteAlterado.getCliente().getDataNascimento());
    clienteRepository.save(clienteExistente);


    tipoClienteRepository.save(tipoClienteExistente);
}




    @Transactional
    public void delete(Long id) {
        TipoCliente tipoCliente = tipoClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo do Cliente não identificado"));


        tipoClienteRepository.delete(tipoCliente);
    }
}
