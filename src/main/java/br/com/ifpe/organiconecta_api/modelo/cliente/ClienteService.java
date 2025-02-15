package br.com.ifpe.organiconecta_api.modelo.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// import java.util.NoSuchElementException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioService;
import br.com.ifpe.organiconecta_api.modelo.assinatura.Assinatura;
import br.com.ifpe.organiconecta_api.modelo.assinatura.AssinaturaService;
import br.com.ifpe.organiconecta_api.modelo.mensagens.EmailService;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteRepository;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteService;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

    // @Autowired
    // private PedidoRepository pedidoRepository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Cliente save(Cliente cliente) {

        // Salva o usuário associado ao cliente
        usuarioService.save(cliente.getUsuario());

        // Salva os perfis associados ao usuário
        for (Perfil perfil : cliente.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        TipoCliente tipoCliente = tipoClienteRepository.findByTipo(TipoCliente.TIPO_CLIENTE);
        if (tipoCliente == null) {
            throw new RuntimeException("TipoCliente padrão não encontrado no banco de dados.");
        }
        cliente.setTipoCliente(tipoCliente);

        cliente.setHabilitado(Boolean.TRUE);

        // Salva o cliente no banco para gerar o ID
        cliente = repository.save(cliente);

        // Cria e associa uma assinatura ao cliente
        Assinatura assinatura = new Assinatura();
        assinatura.setCliente(cliente);
        assinatura.setDataInicio(LocalDate.now());
        assinatura.setValidade(LocalDate.of(2099, 12, 31));
        assinatura.setStatusAssinatura(false);
        // assinatura.setPlanoPreco(BigDecimal.ZERO);
        assinatura.setHabilitado(Boolean.TRUE);
        assinaturaService.save(assinatura);
        
        //enviar email após cadastro do cliente
        emailService.enviarEmailConfirmacaoCadastroCliente(cliente);
        return cliente;
    }

    public List<Cliente> listarTodos() {

        return repository.findAll();

    }

    public Cliente obterPorID(Long id) {

        return repository.findById(id).get();

    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {

        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setTelefone(clienteAlterado.getTelefone());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setClienteImagem(clienteAlterado.getClienteImagem());

        // if (cliente.getAssinatura() != null && cliente.getAssinatura().getTipoPlano()
        // != null) {
        // TipoCliente tipoCliente = cliente.getTipoCliente();
        // if (cliente.getAssinatura().getTipoPlano() == Assinatura.TipoPlanoEnum.PAGO)
        // {
        // tipoCliente.setTipoUsuario(TipoCliente.TipoClienteEnum.CLIENTEPRODUTOR);
        // } else {
        // tipoCliente.setTipoUsuario(TipoCliente.TipoClienteEnum.CLIENTE);
        // }
        // tipoClienteService.save(tipoCliente); // Atualizar o tipo de cliente
        // }

        repository.save(cliente); // Salvar as alterações no cliente
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = repository.findById(id).get();
        
        cliente.setHabilitado(Boolean.FALSE);
        repository.save(cliente);

    }

    // Endereço
    public EnderecoCliente obterEnderecoPorID(Long id) {
        return enderecoClienteRepository.findById(id).get();
    }

    @Transactional
    public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {

        Cliente cliente = this.obterPorID(clienteId);

        // Primeiro salva o EnderecoCliente:
        endereco.setCliente(cliente);
        endereco.setHabilitado(Boolean.TRUE);
        enderecoClienteRepository.save(endereco);

        // Depois acrescenta o endereço criado ao cliente e atualiza o cliente:
        List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();

        if (listaEnderecoCliente == null) {
            listaEnderecoCliente = new ArrayList<EnderecoCliente>();
        }

        listaEnderecoCliente.add(endereco);
        cliente.setEnderecos(listaEnderecoCliente);
        repository.save(cliente);

        return endereco;
    }

    @Transactional
    public EnderecoCliente atualizarEnderecoCliente(Long id, EnderecoCliente enderecoAlterado) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(id).get();
        endereco.setRua(enderecoAlterado.getRua());
        endereco.setNumero(enderecoAlterado.getNumero());
        endereco.setBairro(enderecoAlterado.getBairro());
        endereco.setCep(enderecoAlterado.getCep());
        endereco.setCidade(enderecoAlterado.getCidade());
        endereco.setEstado(enderecoAlterado.getEstado());
        endereco.setComplemento(enderecoAlterado.getComplemento());

        return enderecoClienteRepository.save(endereco);
    }

    @Transactional
    public void removerEnderecoCliente(Long idEndereco) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(idEndereco).get();
        endereco.setHabilitado(Boolean.FALSE);
        enderecoClienteRepository.save(endereco);

        Cliente cliente = this.obterPorID(endereco.getCliente().getId());
        cliente.getEnderecos().remove(endereco);
        repository.save(cliente);
    }

}
