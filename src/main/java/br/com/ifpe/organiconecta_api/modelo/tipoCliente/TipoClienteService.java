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


    @Transactional
public void inicializarTipos() {
    // Verifica e cria o TipoCliente padrão
    if (tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE) == null) {
        TipoCliente tipoCliente = new TipoCliente();
        tipoCliente.setTipo(TipoCliente.TIPO_CLIENTE);
        tipoCliente.setHabilitado(true); // Define habilitado como true
        tipoClienteRepository.save(tipoCliente);
    }

    // Verifica e cria o TipoCliente produtor
    if (tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE_PRODUTOR) == null) {
        TipoCliente tipoClienteProdutor = new TipoCliente();
        tipoClienteProdutor.setTipo(TipoCliente.TIPO_CLIENTE_PRODUTOR);
        tipoClienteProdutor.setHabilitado(true); // Define habilitado como true
        tipoClienteRepository.save(tipoClienteProdutor);
    }
}

    @Transactional
    public TipoCliente save(TipoCliente tipoCliente) {
        
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


//     @Transactional
// public void update(Long id, TipoCliente tipoClienteAlterado) {
//     // Encontrar o tipoCliente existente no banco de dados
//     TipoCliente tipoClienteExistente = tipoClienteRepository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Tipo do Cliente não identificado"));


//     tipoClienteRepository.save(tipoClienteExistente);
// }




    @Transactional
    public void delete(Long id) {
        TipoCliente tipoCliente = tipoClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo do Cliente não identificado"));


        tipoClienteRepository.delete(tipoCliente);
    }
}
