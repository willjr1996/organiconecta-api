package br.com.ifpe.organiconecta_api.modelo.assinatura;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteService;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    @Autowired
    private TipoClienteService tipoClienteService;

    @Transactional
public Assinatura save(Assinatura assinatura) {
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
        assinatura.setStatus(assinaturaAlterado.getStatus());
        assinatura.setTipoPlano(assinaturaAlterado.getTipoPlano());
        assinatura.setPlanoPreco(assinaturaAlterado.getPlanoPreco());

        repository.save(assinatura);
    }

    @Transactional
    public void delete(Long assinaturaId) {
    Assinatura assinatura = repository.findById(assinaturaId)
            .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

    // Reverter alterações no tipo do cliente
    Cliente cliente = assinatura.getCliente();
    TipoCliente tipoCliente = cliente.getTipoCliente();
    tipoCliente.setTipoUsuario(TipoCliente.TipoClienteEnum.CLIENTE);
    tipoClienteService.save(tipoCliente);

    // Reverter alterações na assinatura
    assinatura.setTipoPlano(Assinatura.TipoPlanoEnum.GRATIS);
    assinatura.setStatus(false);
    repository.save(assinatura);

    repository.deleteById(assinaturaId);
}

    @Transactional
    public void atualizarPlanoParaPago(Long assinaturaId) {
        Assinatura assinatura = repository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        // Atualizar tipo de plano
        assinatura.setTipoPlano(Assinatura.TipoPlanoEnum.PAGO);
        assinatura.setStatus(true); 
        repository.save(assinatura);

        // Atualizar tipo de cliente para ClienteProdutor
        Cliente cliente = assinatura.getCliente();
        TipoCliente tipoCliente = cliente.getTipoCliente();
        tipoCliente.setTipoUsuario(TipoCliente.TipoClienteEnum.CLIENTEPRODUTOR);
        tipoClienteService.save(tipoCliente);
    }

}
