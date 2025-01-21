package br.com.ifpe.organiconecta_api.modelo.assinatura;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteRepository;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoClienteRepository tipoClienteRepository;


    @Transactional
public Assinatura save(Assinatura assinatura) {
    assinatura.setHabilitado(Boolean.TRUE);
    return repository.save(assinatura);
}


    public List<Assinatura> listarTodos() {

        return repository.findAll();

    }

    public Assinatura obterPorID(Long id) {

        return repository.findById(id).get();

    }

    @Transactional
    public void update(Long id, Assinatura assinaturaAlterado) {

        Assinatura assinatura = repository.findById(id).get();
        assinatura.setDataInicio(assinaturaAlterado.getDataInicio());
        assinatura.setValidade(assinaturaAlterado.getValidade());
        assinatura.setStatusAssinatura(assinaturaAlterado.getStatusAssinatura());
        // assinatura.setTipoPlano(assinaturaAlterado.getTipoPlano());
        // assinatura.setPlanoPreco(assinaturaAlterado.getPlanoPreco());

        repository.save(assinatura);
    }

    @Transactional
    public void delete(Long assinaturaId) {
    Assinatura assinatura = repository.findById(assinaturaId)
            .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

    repository.save(assinatura);

    repository.deleteById(assinaturaId);
}

    @Transactional
    public void atualizarPlanoParaPago(Long assinaturaId) {
        Assinatura assinatura = repository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        assinatura.setValidade(LocalDate.now().plusMonths(12));
        assinatura.setStatusAssinatura(true); 
        repository.save(assinatura);

        Cliente cliente = assinatura.getCliente();

        TipoCliente tipoClienteProdutor = tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE_PRODUTOR);
   
        cliente.setTipoCliente(tipoClienteProdutor);

    // Salva as alterações no cliente
         clienteRepository.save(cliente);
}

@Transactional
    public void atualizarPlanoParaGratis(Long assinaturaId) {
        Assinatura assinatura = repository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        // Atualizar tipo de plano
        assinatura.setValidade(LocalDate.of(2099, 12, 31));
        assinatura.setStatusAssinatura(false); 
        repository.save(assinatura);

        Cliente cliente = assinatura.getCliente();

        TipoCliente tipoCliente = tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE);
   
        cliente.setTipoCliente(tipoCliente);

    // Salva as alterações no cliente
         clienteRepository.save(cliente);
}

}
