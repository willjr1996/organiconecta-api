package br.com.ifpe.organiconecta_api.modelo.cliente;

import java.util.ArrayList;
import java.util.List;
// import java.util.NoSuchElementException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.acesso.Perfil;
import br.com.ifpe.organiconecta_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioService;
import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.modelo.pedido.PedidoRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Transactional
    public Cliente save(Cliente cliente) {

        usuarioService.save(cliente.getUsuario());

        for (Perfil perfil : cliente.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        cliente.setHabilitado(Boolean.TRUE);
        return repository.save(cliente);

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
        // cliente.setEmail(clienteAlterado.getEmail());
        cliente.setTelefone(clienteAlterado.getTelefone());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        // cliente.setSenha(clienteAlterado.getSenha());

        repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);
        repository.save(cliente);

    }

    // @Transactional
    // public String Login (String email, String senha){
    // cliente cliente = repository.findByEmailAndSenha(email, senha);

    // if(cliente != null){
    // return cliente.getNome();
    // }
    // return null;
    // }

    // método para aparecer id do usuário quando ele logar
    // @Transactional
    // public ClienteId getIdByCredenciais(Credenciais credenciais) {
    // Long userId = repository.findIdByEmailAndSenha(credenciais.getEmail(),
    // credenciais.getSenha());

    // if (userId == null) {
    // throw new NoSuchElementException("Usuário não encontrado com as credenciais
    // fornecidas.");
    // }
    // return new ClienteId(userId);
    // }

    // método para editar email e senha
    // @Transactional
    // public Cliente atualizarEmailESenha(Long id, Credenciais credenciais) {
    // Cliente cliente = repository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não
    // encontrado"));

    // cliente.setEmail(credenciais.getEmail());
    // cliente.setSenha(credenciais.getSenha());

    // return repository.save(cliente);
    // }

    @Transactional
    public Pedido adicionarPedidoCliente(Long clienteId, Pedido pedido) {

        Cliente cliente = this.obterPorID(clienteId);

        // Primeiro salva o pedido:

        pedido.setCliente(cliente);
        pedido.setHabilitado(Boolean.TRUE);
        pedidoRepository.save(pedido);

        // Depois acrescenta o pedido criado ao cliente e atualiza o cliente:

        List<Pedido> listaPedidoCliente = cliente.getPedidos();

        if (listaPedidoCliente == null) {
            listaPedidoCliente = new ArrayList<Pedido>();
        }

        listaPedidoCliente.add(pedido);
        cliente.setPedidos(listaPedidoCliente);
        repository.save(cliente);

        return pedido;
    }

    @Transactional
    public void removerPedidoCliente(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido).get();
        pedido.setHabilitado(Boolean.FALSE);
        pedidoRepository.save(pedido);

        Cliente cliente = this.obterPorID(pedido.getCliente().getId());
        cliente.getPedidos().remove(pedido);
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