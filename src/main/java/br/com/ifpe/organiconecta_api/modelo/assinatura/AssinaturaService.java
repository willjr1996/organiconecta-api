package br.com.ifpe.organiconecta_api.modelo.assinatura;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioService;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
//import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteRepository;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

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

        // cliente.getUsuario().getRoles().remove(new Perfil(Perfil.ROLE_CLIENTE));
        // for (Perfil perfilRemover : cliente.getUsuario().getRoles()) {
        // perfilRemover.setHabilitado(Boolean.TRUE);
        // perfilUsuarioRepository.save(perfilRemover);
        // }
        // cliente.getUsuario().getRoles().add(new
        // Perfil(Perfil.ROLE_CLIENTE_PRODUTOR));

        cliente.getUsuario().getRoles().removeIf(perfil -> Perfil.ROLE_CLIENTE.equals(perfil.getAuthority()));

        cliente.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_CLIENTE_PRODUTOR));

        for (Perfil perfilAdd : cliente.getUsuario().getRoles()) {
            perfilAdd.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfilAdd);
        }

        clienteRepository.save(cliente);

    }

    @Transactional
    public void atualizarPlanoParaGratis(Long assinaturaId) {
        Assinatura assinatura = repository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        assinatura.setValidade(LocalDate.of(2099, 12, 31));
        assinatura.setStatusAssinatura(false);
        repository.save(assinatura);

        Cliente cliente = assinatura.getCliente();

        TipoCliente tipoCliente = tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE);

        cliente.setTipoCliente(tipoCliente);

        cliente.getUsuario().getRoles().removeIf(perfil -> Perfil.ROLE_CLIENTE_PRODUTOR.equals(perfil.getAuthority()));

        cliente.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_CLIENTE));

        for (Perfil perfilAdd : cliente.getUsuario().getRoles()) {
            perfilAdd.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfilAdd);
        }

        // usuarioService.save(cliente.getUsuario());
        clienteRepository.save(cliente);

    }

}
