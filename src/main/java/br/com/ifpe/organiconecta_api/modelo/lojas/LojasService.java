package br.com.ifpe.organiconecta_api.modelo.lojas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class LojasService {

    @Autowired
    private LojasRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Lojas save(Lojas loja, Long clienteId) {


        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("cliente não encontrado"));
        loja.setCliente(cliente);

        loja.setHabilitado(Boolean.TRUE);
        return repository.save(loja);
    }

    // Listar todas as lojas
    public List<Lojas> listarTodos() {
        return repository.findAll();
    }

    // Obter Loja por ID
    public Lojas obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
    }

    // Atualizar Loja e o cliente associado
    @Transactional
    public void update(Long id, Lojas lojaAlterada, Long clienteId) {
        Lojas lojaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        lojaExistente.setNomeLoja(lojaAlterada.getNomeLoja());
        lojaExistente.setRegistroPropriedade(lojaAlterada.getRegistroPropriedade());
        lojaExistente.setCertificacao(lojaAlterada.getCertificacao());

        // Atualizar cliente associado
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            lojaExistente.setCliente(cliente);
        }

        repository.save(lojaExistente);
    }

    // Deletar Loja
    @Transactional
    public void delete(Long id) {
        Lojas loja = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        loja.setHabilitado(Boolean.FALSE);
        repository.save(loja);
    }
}
