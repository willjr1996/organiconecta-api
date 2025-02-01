package br.com.ifpe.organiconecta_api.modelo.lojas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class LojasService {

    @Autowired
    private LojasRepository repository;

    // @Autowired
    // private ClienteRepository clienteRepository;

    @Transactional
    public Lojas save(Lojas loja) {

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
    public void update(Long id, Lojas lojaAlterada) {

         Lojas loja = repository.findById(id).get();
         loja.setCliente(lojaAlterada.getCliente());
         loja.setNomeLoja(lojaAlterada.getNomeLoja());
        loja.setRegistroPropriedade(lojaAlterada.getRegistroPropriedade());
        loja.setCertificacao(lojaAlterada.getCertificacao());
        loja.setPerfilLojaImagem(lojaAlterada.getPerfilLojaImagem());
        loja.setCapaLojaImagem(lojaAlterada.getCapaLojaImagem());

        // Atualizar cliente associado
        // if (clienteId != null) {
        //     Cliente cliente = clienteRepository.findById(clienteId)
        //             .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        //     lojaExistente.setCliente(cliente);
        // }

        repository.save(loja);
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
